import hljs from "highlight.js/lib/core";
import java from "highlight.js/lib/languages/java";
import { ChevronFirst, ChevronLast, CircleAlert, Expand, Pause, Play, RotateCcw, Shrink, SkipBack, SkipForward, SlidersHorizontal } from "lucide-react";
import { useCallback, useEffect, useMemo, useRef, useState } from "react";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { Kbd } from "@/components/ui/kbd";
import { Popover, PopoverContent, PopoverTrigger } from "@/components/ui/popover";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Slider } from "@/components/ui/slider";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Textarea } from "@/components/ui/textarea";
import { Tooltip, TooltipContent, TooltipTrigger } from "@/components/ui/tooltip";
import type { Frame, VizApi, VizDef } from "@/hooks/use-viz";
import { cn } from "@/lib/utils";
import { Stage } from "./Stage";

hljs.registerLanguage("java", java);

const SPEEDS = [
  { value: "1600", label: "0.5×" },
  { value: "900", label: "1×" },
  { value: "450", label: "2×" },
  { value: "200", label: "4×" },
];

const LEGEND = [
  ["cur", "đang xét"],
  ["ok", "đúng / đã chốt"],
  ["bad", "sai / loại"],
  ["mark", "tham chiếu"],
  ["win", "vùng đang xét"],
];

const fmt = (v: unknown) => (typeof v === "string" ? v : JSON.stringify(v));

/** Renders `code` spans inside a message. */
function Rich({ text }: { text: string }) {
  return <>{text.split(/`([^`]+)`/).map((part, i) => (i % 2 ? <code key={i} className="rounded bg-muted px-1 font-mono text-[0.9em]">{part}</code> : part))}</>;
}

/** Keeps `child` visible by scrolling only its own panel, never the page. */
function keepVisible(panel: HTMLElement | null, child: HTMLElement | null) {
  if (!panel || !child) return;
  const top = child.offsetTop - panel.offsetTop;
  if (top < panel.scrollTop) panel.scrollTop = top - 8;
  else if (top + child.offsetHeight > panel.scrollTop + panel.clientHeight) panel.scrollTop = top + child.offsetHeight - panel.clientHeight + 8;
}

function CodePanel({ code, line }: { code: string; line?: number }) {
  const lines = useMemo(() => code.replace(/\n$/, "").split("\n").map((l) => hljs.highlight(l || " ", { language: "java", ignoreIllegals: true }).value), [code]);
  const panel = useRef<HTMLDivElement>(null);
  const active = useRef<HTMLDivElement>(null);
  useEffect(() => keepVisible(panel.current, active.current), [line]);
  return (
    <div ref={panel} className="relative h-full overflow-auto py-1.5 font-mono text-[12.5px] leading-5">
      {lines.map((html, i) => (
        <div key={i} ref={i + 1 === line ? active : undefined} className={cn("flex min-w-max border-l-2 border-transparent pr-3 transition-colors", i + 1 === line && "border-[var(--viz-cur)] bg-[var(--viz-cur-soft)]")}>
          <span className="w-8 shrink-0 select-none pr-2 text-right text-muted-foreground/60">{i + 1}</span>
          <span className="whitespace-pre" dangerouslySetInnerHTML={{ __html: html }} />
        </div>
      ))}
    </div>
  );
}

function VarsPanel({ vars, prev }: { vars?: Record<string, unknown>; prev?: Record<string, unknown> }) {
  const entries = Object.entries(vars ?? {});
  if (!entries.length) return <p className="p-3 text-sm text-muted-foreground">Bước này không có biến nào đáng chú ý.</p>;
  return (
    <dl className="grid grid-cols-[auto_minmax(0,1fr)] gap-x-3 gap-y-1 p-3 font-mono text-[12.5px]">
      {entries.map(([k, v]) => {
        const changed = prev !== undefined && fmt(prev[k]) !== fmt(v);
        return (
          <div key={k} className="contents">
            <dt className="text-muted-foreground">{k}</dt>
            <dd className={cn("break-all", changed && "font-semibold text-[var(--viz-cur)]")}><span key={fmt(v)} className="pop">{fmt(v)}</span></dd>
          </div>
        );
      })}
    </dl>
  );
}

function LogPanel({ frames, index, onPick }: { frames: Frame[]; index: number; onPick: (i: number) => void }) {
  const panel = useRef<HTMLDivElement>(null);
  const active = useRef<HTMLButtonElement>(null);
  useEffect(() => keepVisible(panel.current, active.current), [index]);
  return (
    <div ref={panel} className="relative h-full overflow-auto p-1">
      {frames.slice(0, index + 1).map((f, i) => (
        <button key={i} ref={i === index ? active : undefined} onClick={() => onPick(i)} className={cn("flex w-full gap-2 rounded px-2 py-1 text-left text-[12.5px] hover:bg-muted", i === index && "bg-muted font-medium")}>
          <span className="w-7 shrink-0 text-right font-mono text-muted-foreground">{i + 1}</span>
          <span className="min-w-0"><Rich text={f.msg ?? "—"} /></span>
        </button>
      ))}
    </div>
  );
}

function InputPopover({ def, text, error, onRun }: { def: VizDef; text: string; error: string; onRun: (text: string) => void }) {
  const [draft, setDraft] = useState(text);
  const [open, setOpen] = useState(false);
  useEffect(() => setDraft(text), [text]);
  useEffect(() => { if (error) setOpen(true); }, [error]);
  return (
    <Popover open={open} onOpenChange={setOpen}>
      <PopoverTrigger asChild>
        <Button variant="outline" size="sm"><SlidersHorizontal /> Dữ liệu</Button>
      </PopoverTrigger>
      <PopoverContent align="end" className="w-[min(26rem,calc(100vw-2rem))] space-y-3">
        <div>
          <div className="text-sm font-medium">Dữ liệu vào</div>
          <p className="text-xs text-muted-foreground">Mỗi dòng một biến: <code>tên = giá trị JSON</code></p>
        </div>
        <Textarea value={draft} onChange={(e) => setDraft(e.target.value)} spellCheck={false} rows={Math.min(8, draft.split("\n").length + 1)} className="font-mono text-[13px]" />
        {error && <p className="flex gap-1.5 text-xs text-destructive"><CircleAlert className="mt-px size-3.5 shrink-0" />{error}</p>}
        {def.presets?.length ? (
          <div className="flex flex-wrap gap-1">
            {def.presets.map((p) => <Button key={p.label} size="xs" variant="secondary" onClick={() => { setDraft(p.input); onRun(p.input); }}>{p.label}</Button>)}
          </div>
        ) : null}
        <div className="flex gap-2">
          <Button size="sm" onClick={() => { onRun(draft); }}>Chạy lại</Button>
          <Button size="sm" variant="ghost" onClick={() => { setDraft(def.input); onRun(def.input); }}><RotateCcw /> Ví dụ gốc</Button>
        </div>
      </PopoverContent>
    </Popover>
  );
}

function Icon({ tip, shortcut, ...props }: React.ComponentProps<typeof Button> & { tip: string; shortcut?: string }) {
  return (
    <Tooltip>
      <TooltipTrigger asChild><Button variant="ghost" size="icon-sm" aria-label={tip} {...props} /></TooltipTrigger>
      <TooltipContent className="flex items-center gap-2">{tip}{shortcut && <Kbd>{shortcut}</Kbd>}</TooltipContent>
    </Tooltip>
  );
}

export function Player({ viz, slug, focus, onFocus }: { viz: VizApi; slug: string; focus: boolean; onFocus: () => void }) {
  const def = viz.registry[slug];
  const [text, setText] = useState(def.input);
  const [error, setError] = useState("");
  const [trace, setTrace] = useState<{ frames: Frame[]; result: unknown }>({ frames: [], result: undefined });
  const [index, setIndex] = useState(0);
  const [playing, setPlaying] = useState(false);
  const [speed, setSpeed] = useState("900");

  const run = useCallback((input: string) => {
    setText(input);
    setPlaying(false);
    try {
      const t = viz.trace(def, viz.parseInput(input));
      setError("");
      setTrace(t.frames.length ? t : { frames: [{ msg: "Không có bước nào." }], result: t.result });
      setIndex(0);
    } catch (e) {
      setError(e instanceof Error ? e.message : String(e));
    }
  }, [viz, def]);

  useEffect(() => run(def.input), [run, def.input]);

  const { frames, result } = trace;
  const last = Math.max(0, frames.length - 1);
  const frame = frames[index] ?? {};
  const atEnd = index >= last;

  useEffect(() => {
    if (!playing) return;
    if (atEnd) { setPlaying(false); return; }
    const t = setTimeout(() => setIndex((i) => i + 1), Number(speed));
    return () => clearTimeout(t);
  }, [playing, index, speed, atEnd]);

  const jump = useCallback((i: number) => { setPlaying(false); setIndex(Math.max(0, Math.min(last, i))); }, [last]);
  const toggle = useCallback(() => {
    setPlaying((p) => {
      if (!p && atEnd) setIndex(0);
      return !p;
    });
  }, [atEnd]);

  useEffect(() => {
    const onKey = (e: KeyboardEvent) => {
      const el = e.target as Partial<HTMLElement>;
      if (el.closest?.("input, textarea, select, [role=dialog], [role=listbox], [role=slider]") || e.metaKey || e.ctrlKey) return;
      if (e.key === "ArrowRight") { e.preventDefault(); setPlaying(false); setIndex((i) => Math.min(last, i + 1)); }
      else if (e.key === "ArrowLeft") { e.preventDefault(); setPlaying(false); setIndex((i) => Math.max(0, i - 1)); }
      else if (e.key === " ") { e.preventDefault(); toggle(); }
      else if (e.key === "f") onFocus();
    };
    window.addEventListener("keydown", onKey);
    return () => window.removeEventListener("keydown", onKey);
  }, [last, toggle, onFocus]);

  const marks = useMemo(() => frames.flatMap((f, i) => (f.key ? [i] : [])), [frames]);

  return (
    <div className="flex h-full min-h-0 flex-col bg-card">
      <div className="flex h-11 shrink-0 items-center gap-2 border-b px-3">
        <span className="text-sm font-medium">Mô phỏng</span>
        <div className="hidden min-w-0 items-center gap-2.5 overflow-hidden pl-2 text-[11px] text-muted-foreground xl:flex">
          {LEGEND.map(([tone, label]) => (
            <span key={tone} className="flex items-center gap-1 whitespace-nowrap"><span data-tone={tone} className="tone-box size-2.5 rounded-[3px]" />{label}</span>
          ))}
        </div>
        <div className="ml-auto flex items-center gap-1">
          <InputPopover def={def} text={text} error={error} onRun={run} />
          <div className="hidden lg:block">
            <Icon tip={focus ? "Hiện lại đề bài" : "Chỉ xem mô phỏng"} shortcut="F" onClick={onFocus}>{focus ? <Shrink /> : <Expand />}</Icon>
          </div>
        </div>
      </div>

      <div className="stage-bg relative min-h-[280px] flex-1 overflow-auto p-5">
        <Stage frame={frame} />
      </div>

      <div className="flex min-h-14 shrink-0 items-center gap-3 border-t bg-card px-4 py-2 text-sm">
        <Badge variant="secondary" className="shrink-0 font-mono tabular-nums">{index + 1}/{frames.length}</Badge>
        <div className="min-w-0 leading-snug" aria-live="polite"><Rich text={frame.msg ?? ""} /></div>
        {atEnd && result !== undefined && (
          <div className="ml-auto max-w-[45%] shrink-0 truncate rounded-md border border-[var(--viz-ok)] bg-[var(--viz-ok-soft)] px-2 py-1 font-mono text-xs" title={fmt(result)}>= {fmt(result)}</div>
        )}
      </div>

      {frame.vars && (
        <div className="flex shrink-0 gap-3 overflow-x-auto border-t px-4 py-1.5 font-mono text-xs whitespace-nowrap md:hidden">
          {Object.entries(frame.vars).map(([k, v]) => <span key={k}><span className="text-muted-foreground">{k}</span> {fmt(v)}</span>)}
        </div>
      )}

      <div className="grid h-40 shrink-0 grid-cols-1 border-t md:h-52 md:grid-cols-[minmax(0,3fr)_minmax(0,2fr)]">
        <div className="min-h-0 md:border-r">
          {def.code ? <CodePanel code={def.code} line={frame.line} /> : <div className="p-3 text-sm text-muted-foreground">Chưa có mã giả.</div>}
        </div>
        <Tabs defaultValue="vars" className="hidden min-h-0 gap-0 md:flex">
          <TabsList variant="line" className="h-9 w-full shrink-0 justify-start border-b px-2">
            <TabsTrigger value="vars" className="flex-none">Biến</TabsTrigger>
            <TabsTrigger value="log" className="flex-none">Nhật ký</TabsTrigger>
          </TabsList>
          <TabsContent value="vars" className="min-h-0 overflow-auto"><VarsPanel vars={frame.vars} prev={frames[index - 1]?.vars} /></TabsContent>
          <TabsContent value="log" className="min-h-0"><LogPanel frames={frames} index={index} onPick={jump} /></TabsContent>
        </Tabs>
      </div>

      <div className="flex h-14 shrink-0 items-center gap-1 border-t px-2">
        <Icon tip="Về đầu" onClick={() => jump(0)} disabled={index === 0}><ChevronFirst /></Icon>
        <Icon tip="Lùi một bước" shortcut="←" onClick={() => jump(index - 1)} disabled={index === 0}><SkipBack /></Icon>
        <Button size="icon" aria-label={playing ? "Dừng" : "Chạy"} onClick={toggle}>{playing ? <Pause /> : <Play />}</Button>
        <Icon tip="Tiến một bước" shortcut="→" onClick={() => jump(index + 1)} disabled={atEnd}><SkipForward /></Icon>
        <Icon tip="Tới cuối" onClick={() => jump(last)} disabled={atEnd}><ChevronLast /></Icon>
        <div className="relative mx-3 flex-1">
          <Slider aria-label="Tiến trình" min={0} max={Math.max(1, last)} step={1} value={[index]} onValueChange={([v]) => jump(v)} />
          {marks.map((m) => <span key={m} className="pointer-events-none absolute top-1/2 size-1 -translate-x-1/2 -translate-y-1/2 rounded-full bg-[var(--viz-mark)]" style={{ left: `${(m / Math.max(1, last)) * 100}%` }} />)}
        </div>
        <Select value={speed} onValueChange={setSpeed}>
          <SelectTrigger size="sm" aria-label="Tốc độ" className="w-[4.5rem]"><SelectValue /></SelectTrigger>
          <SelectContent>{SPEEDS.map((s) => <SelectItem key={s.value} value={s.value}>{s.label}</SelectItem>)}</SelectContent>
        </Select>
      </div>
    </div>
  );
}
