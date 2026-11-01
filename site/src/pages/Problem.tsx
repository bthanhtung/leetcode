import hljs from "highlight.js/lib/core";
import java from "highlight.js/lib/languages/java";
import { Check, ChevronLeft, ChevronRight, ExternalLink } from "lucide-react";
import { marked } from "marked";
import { useCallback, useEffect, useMemo, useRef, useState, useSyncExternalStore } from "react";
import type { PanelImperativeHandle } from "react-resizable-panels";
import { ErrorBoundary } from "@/components/ErrorBoundary";
import { Player } from "@/components/player/Player";
import { Button } from "@/components/ui/button";
import { ResizableHandle, ResizablePanel, ResizablePanelGroup } from "@/components/ui/resizable";
import { Skeleton } from "@/components/ui/skeleton";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { useViz } from "@/hooks/use-viz";
import { LEVEL_TEXT, type Problem, type ProblemDetail } from "@/lib/data";
import { read, useLearned, useTheme, write } from "@/lib/store";
import { cn } from "@/lib/utils";

hljs.registerLanguage("java", java);

function Markdown({ source, folder }: { source: string; folder: string }) {
  const ref = useRef<HTMLDivElement>(null);
  const { theme } = useTheme();
  // READMEs link to their Java files by relative path; on the site those live on GitHub.
  const html = useMemo(() => (marked.parse(source, { async: false }) as string).replace(/href="([\w-]+\.java)"/g, `href="https://github.com/bthanhtung/leetcode/blob/master/${folder}/$1" target="_blank" rel="noopener"`), [source, folder]);
  useEffect(() => {
    const root = ref.current;
    if (!root) return;
    root.innerHTML = html;
    root.querySelectorAll<HTMLElement>("pre code.language-java").forEach((el) => hljs.highlightElement(el));
    const blocks = [...root.querySelectorAll("pre code.language-mermaid")];
    if (!blocks.length) return;
    const nodes = blocks.map((code) => {
      const div = document.createElement("div");
      div.className = "mermaid not-prose my-4 flex justify-center overflow-x-auto";
      div.textContent = code.textContent;
      code.parentElement!.replaceWith(div);
      return div;
    });
    let cancelled = false;
    import("mermaid").then(({ default: mermaid }) => {
      if (cancelled) return;
      mermaid.initialize({ startOnLoad: false, theme: theme === "dark" ? "dark" : "neutral", securityLevel: "strict" });
      mermaid.run({ nodes }).catch((e: unknown) => console.warn(e));
    });
    return () => { cancelled = true; };
  }, [html, theme]);
  return <div ref={ref} className="prose prose-sm prose-neutral max-w-none dark:prose-invert prose-pre:bg-muted prose-pre:text-foreground prose-code:before:content-none prose-code:after:content-none prose-table:text-xs prose-blockquote:font-normal prose-blockquote:not-italic" />;
}

/** Cuts highlighted HTML into lines, closing and reopening spans that cross a line such as a block comment. */
function htmlLines(html: string) {
  const open: string[] = [];
  return html.split("\n").map((line) => {
    const out = open.join("") + line;
    for (const [tag] of line.matchAll(/<span[^>]*>|<\/span>/g)) {
      if (tag === "</span>") open.pop();
      else open.push(tag);
    }
    return out + "</span>".repeat(open.length);
  });
}

/** Long lines wrap instead of scrolling; a wrapped line hangs under its own indent so the structure stays readable. */
function JavaCode({ source }: { source: string }) {
  const lines = useMemo(() => {
    const text = source.replace(/\t/g, "    ").replace(/\s+$/, "");
    const plain = text.split("\n");
    return htmlLines(hljs.highlight(text, { language: "java", ignoreIllegals: true }).value).map((html, i) => ({ html, indent: Math.min(plain[i].search(/\S|$/), 16) + 2 }));
  }, [source]);
  return (
    <pre className="rounded-lg bg-muted p-4 font-mono text-[12.5px] leading-5">
      <code className="block">
        {lines.map(({ html, indent }, i) => (
          <div key={i} className="min-h-5 break-words whitespace-pre-wrap" style={{ paddingLeft: `${indent}ch`, textIndent: `-${indent}ch` }} dangerouslySetInnerHTML={{ __html: html }} />
        ))}
      </code>
    </pre>
  );
}

/** The README's related line: "167 Two Sum II · 15 3Sum · …". Items naming a problem we have become links. */
function Related({ text, problems }: { text: string; problems: Problem[] }) {
  const items = text.replace(/\.$/, "").split("·").map((s) => s.trim()).filter(Boolean);
  return (
    <div className="mt-8 border-t pt-4">
      <h3 className="mb-2 text-sm font-medium">Bài liên quan</h3>
      <div className="flex flex-wrap gap-1.5 text-xs">
        {items.map((item) => {
          const target = problems.find((p) => p.id === Number(item.match(/^(\d{1,4})\b/)?.[1]));
          return target
            ? <a key={item} href={`/p/${target.slug}`} className="rounded-md border px-2 py-1 hover:bg-muted">{item}</a>
            : <span key={item} className="rounded-md bg-muted px-2 py-1 text-muted-foreground">{item}</span>;
        })}
      </div>
    </div>
  );
}

const WIDE = "(min-width: 1024px)";
const subscribeWide = (l: () => void) => {
  const q = matchMedia(WIDE);
  q.addEventListener("change", l);
  return () => q.removeEventListener("change", l);
};

export function ProblemPage({ slug, problems }: { slug: string; problems: Problem[] }) {
  const [detail, setDetail] = useState<ProblemDetail | null>(null);
  const [focus, setFocus] = useState(false);
  const info = useRef<PanelImperativeHandle>(null);
  const wide = useSyncExternalStore(subscribeWide, () => matchMedia(WIDE).matches);
  const toggleFocus = useCallback(() => {
    const panel = info.current;
    if (!panel) return;
    if (panel.isCollapsed()) panel.expand();
    else panel.collapse();
  }, []);
  const { isLearned, toggle } = useLearned();
  const viz = useViz();
  const i = problems.findIndex((p) => p.slug === slug);
  const p = problems[i];

  useEffect(() => {
    if (!p) return;
    document.title = `${p.id}. ${p.title} · Algorithm Visualizer`;
    fetch(`/data/p/${slug}.json`).then((r) => r.json()).then(setDetail);
  }, [p, slug]);

  if (!p) return <p className="p-8 text-sm">Không tìm thấy bài này. <a className="underline" href="/">Về danh sách</a></p>;
  const prev = problems[i - 1], next = problems[i + 1];
  const hasViz = viz?.has(slug);

  const statement = (
    <div className="flex h-full min-h-0 flex-col">
          <div className="border-b px-5 py-4">
            <div className="flex items-center gap-1 text-xs text-muted-foreground">
              <a href="/" className="hover:text-foreground">Bài tập</a><span>/</span><span>{p.id}</span>
              <div className="ml-auto flex items-center">
                <Button asChild={!!prev} variant="ghost" size="icon-xs" disabled={!prev} aria-label="Bài trước">{prev ? <a href={`/p/${prev.slug}`} title={prev.title}><ChevronLeft /></a> : <ChevronLeft />}</Button>
                <Button asChild={!!next} variant="ghost" size="icon-xs" disabled={!next} aria-label="Bài sau">{next ? <a href={`/p/${next.slug}`} title={next.title}><ChevronRight /></a> : <ChevronRight />}</Button>
              </div>
            </div>
            <h1 className="mt-1 text-xl font-semibold tracking-tight">{p.title}</h1>
            <div className="mt-2 flex flex-wrap items-center gap-x-3 gap-y-1.5 text-xs">
              <span className={cn("font-medium", LEVEL_TEXT[p.level])}>{p.level}</span>
              <span className="text-muted-foreground">{p.topics.join(" · ")}</span>
              <span className="ml-auto flex items-center gap-1">
                <Button variant={isLearned(p.id) ? "secondary" : "outline"} size="xs" aria-pressed={isLearned(p.id)} onClick={() => toggle(p.id)}><Check /> {isLearned(p.id) ? "Đã học" : "Đánh dấu đã học"}</Button>
                <Button asChild variant="outline" size="xs"><a href={`https://leetcode.com/problems/${p.slug}/`} target="_blank" rel="noopener">LeetCode <ExternalLink /></a></Button>
              </span>
            </div>
          </div>
          <Tabs defaultValue="statement" className="min-h-0 flex-1 gap-0">
            <TabsList variant="line" className="h-10 w-full shrink-0 justify-start border-b px-3">
              <TabsTrigger value="statement" className="flex-none">Đề bài</TabsTrigger>
              <TabsTrigger value="solution" className="flex-none">Lời giải</TabsTrigger>
              <TabsTrigger value="java" className="flex-none">Java</TabsTrigger>
            </TabsList>
            {!detail ? <div className="space-y-2 p-5"><Skeleton className="h-4 w-3/4" /><Skeleton className="h-4 w-full" /><Skeleton className="h-4 w-2/3" /></div> : (
              <>
                <TabsContent value="statement" className="min-h-0 overflow-y-auto p-5">
                  <Markdown source={detail.statement} folder={p.folder} />
                  {detail.related && <Related text={detail.related} problems={problems} />}
                </TabsContent>
                <TabsContent value="solution" className="min-h-0 overflow-y-auto p-5"><Markdown source={detail.solution} folder={p.folder} /></TabsContent>
                <TabsContent value="java" className="min-h-0 overflow-y-auto p-5">{detail.java ? <JavaCode source={detail.java} /> : <p className="text-sm text-muted-foreground">Bài này chưa có file Java.</p>}</TabsContent>
              </>
            )}
          </Tabs>
        </div>
  );
  const player = viz && hasViz ? <ErrorBoundary><Player viz={viz} slug={slug} focus={focus} onFocus={toggleFocus} /></ErrorBoundary> : null;

  if (!wide) {
    return (
      <div>
        {statement}
        {player && <div className="h-[92dvh] min-h-0 border-t">{player}</div>}
      </div>
    );
  }

  return (
    <div className="h-[calc(100dvh-3.5rem)]">
      <ResizablePanelGroup
        defaultLayout={read("split", { info: 40, sim: 60 })}
        onLayoutChanged={(layout) => {
          setFocus(layout.info < 1);
          if (layout.info >= 1) write("split", layout);
        }}
      >
        <ResizablePanel id="info" panelRef={info} minSize={320} collapsible collapsedSize={0}>{statement}</ResizablePanel>
        <ResizableHandle withHandle aria-label="Kéo để đổi độ rộng hai bên" className="w-1.5 bg-transparent transition-colors after:w-2 hover:bg-border focus-visible:bg-border" />
        <ResizablePanel id="sim" minSize={400}>{player}</ResizablePanel>
      </ResizablePanelGroup>
    </div>
  );
}
