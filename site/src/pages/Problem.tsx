import hljs from "highlight.js/lib/core";
import java from "highlight.js/lib/languages/java";
import { Check, ChevronLeft, ChevronRight, ExternalLink } from "lucide-react";
import { marked } from "marked";
import { useCallback, useEffect, useMemo, useRef, useState } from "react";
import { ErrorBoundary } from "@/components/ErrorBoundary";
import { Player } from "@/components/player/Player";
import { Button } from "@/components/ui/button";
import { Skeleton } from "@/components/ui/skeleton";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { useViz } from "@/hooks/use-viz";
import { LEVEL_TEXT, type Problem, type ProblemDetail } from "@/lib/data";
import { useLearned, useTheme } from "@/lib/store";
import { cn } from "@/lib/utils";

hljs.registerLanguage("java", java);

function Markdown({ source }: { source: string }) {
  const ref = useRef<HTMLDivElement>(null);
  const { theme } = useTheme();
  const html = useMemo(() => marked.parse(source, { async: false }) as string, [source]);
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

function JavaCode({ source }: { source: string }) {
  const html = useMemo(() => hljs.highlight(source, { language: "java", ignoreIllegals: true }).value, [source]);
  return <pre className="overflow-x-auto rounded-lg bg-muted p-4 font-mono text-[12.5px] leading-5"><code dangerouslySetInnerHTML={{ __html: html }} /></pre>;
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
            ? <a key={item} href={`#/p/${target.slug}`} className="rounded-md border px-2 py-1 hover:bg-muted">{item}</a>
            : <span key={item} className="rounded-md bg-muted px-2 py-1 text-muted-foreground">{item}</span>;
        })}
      </div>
    </div>
  );
}

export function ProblemPage({ slug, problems }: { slug: string; problems: Problem[] }) {
  const [detail, setDetail] = useState<ProblemDetail | null>(null);
  const [focus, setFocus] = useState(false);
  const toggleFocus = useCallback(() => setFocus((f) => !f), []);
  const { isLearned, toggle } = useLearned();
  const viz = useViz();
  const i = problems.findIndex((p) => p.slug === slug);
  const p = problems[i];

  useEffect(() => {
    if (!p) return;
    document.title = `${p.id}. ${p.title} · Algorithm Visualizer`;
    fetch(`data/p/${slug}.json`).then((r) => r.json()).then(setDetail);
  }, [p, slug]);

  if (!p) return <p className="p-8 text-sm">Không tìm thấy bài này. <a className="underline" href="#/">Về danh sách</a></p>;
  const prev = problems[i - 1], next = problems[i + 1];
  const hasViz = viz?.has(slug);

  return (
    <div className={cn("lg:grid lg:h-[calc(100dvh-3.5rem)]", hasViz !== false && !focus && "lg:grid-cols-[minmax(0,2fr)_minmax(0,3fr)]", focus && "lg:grid-cols-1")}>
      {!focus && (
        <div className="flex min-h-0 flex-col lg:border-r">
          <div className="border-b px-5 py-4">
            <div className="flex items-center gap-1 text-xs text-muted-foreground">
              <a href="#/" className="hover:text-foreground">Bài tập</a><span>/</span><span>{p.id}</span>
              <div className="ml-auto flex items-center">
                <Button asChild={!!prev} variant="ghost" size="icon-xs" disabled={!prev} aria-label="Bài trước">{prev ? <a href={`#/p/${prev.slug}`} title={prev.title}><ChevronLeft /></a> : <ChevronLeft />}</Button>
                <Button asChild={!!next} variant="ghost" size="icon-xs" disabled={!next} aria-label="Bài sau">{next ? <a href={`#/p/${next.slug}`} title={next.title}><ChevronRight /></a> : <ChevronRight />}</Button>
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
                  <Markdown source={detail.statement} />
                  {detail.related && <Related text={detail.related} problems={problems} />}
                </TabsContent>
                <TabsContent value="solution" className="min-h-0 overflow-y-auto p-5"><Markdown source={detail.solution} /></TabsContent>
                <TabsContent value="java" className="min-h-0 overflow-y-auto p-5">{detail.java ? <JavaCode source={detail.java} /> : <p className="text-sm text-muted-foreground">Bài này chưa có file Java.</p>}</TabsContent>
              </>
            )}
          </Tabs>
        </div>
      )}
      {viz && hasViz && (
        <div className="h-[92dvh] min-h-0 border-t lg:h-auto lg:border-t-0">
          <ErrorBoundary><Player viz={viz} slug={slug} focus={focus} onFocus={toggleFocus} /></ErrorBoundary>
        </div>
      )}
    </div>
  );
}
