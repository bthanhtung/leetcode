import { Check, Play, Search, Shuffle, X } from "lucide-react";
import { useMemo } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { ToggleGroup, ToggleGroupItem } from "@/components/ui/toggle-group";
import { useViz } from "@/hooks/use-viz";
import { LEVEL_TEXT, type Problem } from "@/lib/data";
import { go } from "@/lib/router";
import { useFilters, useLearned } from "@/lib/store";
import { cn } from "@/lib/utils";

const LEVEL_ORDER = { Easy: 0, Medium: 1, Hard: 2 };

export function Home({ problems }: { problems: Problem[] }) {
  const { filters, setFilters } = useFilters();
  const { ids, isLearned, toggle } = useLearned();
  const viz = useViz();
  const vizCount = viz?.slugs().length;
  const topics = useMemo(() => [...new Set(problems.flatMap((p) => p.topics))].sort(), [problems]);
  const shown = useMemo(() => {
    const q = filters.q.trim().toLowerCase();
    const out = problems.filter((p) =>
      (filters.level === "all" || p.level === filters.level) &&
      (filters.topic === "all" || p.topics.includes(filters.topic)) &&
      (filters.status === "all" || ids.includes(p.id) === (filters.status === "learned")) &&
      (!q || `${p.id} ${p.title} ${p.approaches} ${p.topics.join(" ")}`.toLowerCase().includes(q)));
    if (filters.sort === "level") out.sort((a, b) => LEVEL_ORDER[a.level] - LEVEL_ORDER[b.level] || a.id - b.id);
    if (filters.sort === "ac") out.sort((a, b) => b.acRate - a.acRate);
    if (filters.sort === "hard") out.sort((a, b) => a.acRate - b.acRate);
    return out;
  }, [problems, filters, ids]);
  const count = (l: string) => problems.filter((p) => p.level === l).length;
  const filtered = filters.q || filters.level !== "all" || filters.topic !== "all" || filters.status !== "all";

  return (
    <div className="mx-auto w-full max-w-6xl px-4 pb-16">
      <section className="flex flex-col gap-6 pt-10 pb-8 md:flex-row md:items-end md:justify-between">
        <div className="max-w-2xl">
          <h1 className="text-3xl font-semibold tracking-tight md:text-4xl">Make LeetCode easy.</h1>
          <p className="mt-3 leading-relaxed text-muted-foreground">Xem từng bước thuật toán chạy để hiểu vì sao nó đúng, chứ không học thuộc lời giải. Mỗi bài có giải thích tiếng Việt, code Java đã kiểm thử và mô phỏng sửa được dữ liệu.</p>
          <div className="mt-5 flex gap-2">
            <Button onClick={() => go(`/p/${problems[Math.floor(Math.random() * problems.length)].slug}`)}><Shuffle /> Bài ngẫu nhiên</Button>
            <Button variant="outline" onClick={() => go("/topics")}>Học theo chủ đề</Button>
          </div>
        </div>
        <dl className="grid grid-cols-4 divide-x rounded-xl border bg-card text-center">
          {[["Easy", count("Easy"), LEVEL_TEXT.Easy], ["Medium", count("Medium"), LEVEL_TEXT.Medium], ["Hard", count("Hard"), LEVEL_TEXT.Hard], ["Mô phỏng", vizCount ?? "…", ""]].map(([label, value, cls]) => (
            <div key={label as string} className="px-4 py-3 sm:px-5">
              <dd className={cn("text-2xl font-semibold tabular-nums", cls as string)}>{value}</dd>
              <dt className="text-xs text-muted-foreground">{label}</dt>
            </div>
          ))}
        </dl>
      </section>

      <div className="z-30 -mx-4 flex flex-wrap md:sticky md:top-14 items-center gap-2 border-b bg-background/90 px-4 py-2.5 backdrop-blur">
        <div className="relative min-w-52 flex-1">
          <Search className="absolute top-1/2 left-2.5 size-4 -translate-y-1/2 text-muted-foreground" />
          <Input value={filters.q} onChange={(e) => setFilters({ q: e.target.value })} placeholder="Tìm theo tên, số bài, kỹ thuật…" aria-label="Tìm bài" className="pl-8" />
        </div>
        <ToggleGroup type="single" variant="outline" value={filters.level} onValueChange={(v) => v && setFilters({ level: v })} aria-label="Độ khó">
          {["all", "Easy", "Medium", "Hard"].map((l) => <ToggleGroupItem key={l} value={l}>{l === "all" ? "Tất cả" : l}</ToggleGroupItem>)}
        </ToggleGroup>
        <Select value={filters.topic} onValueChange={(v) => setFilters({ topic: v })}>
          <SelectTrigger aria-label="Chủ đề" className="w-44"><SelectValue /></SelectTrigger>
          <SelectContent><SelectItem value="all">Mọi chủ đề</SelectItem>{topics.map((t) => <SelectItem key={t} value={t}>{t}</SelectItem>)}</SelectContent>
        </Select>
        <Select value={filters.status} onValueChange={(v) => setFilters({ status: v })}>
          <SelectTrigger aria-label="Trạng thái" className="w-32"><SelectValue /></SelectTrigger>
          <SelectContent>
            <SelectItem value="all">Mọi bài</SelectItem>
            <SelectItem value="todo">Chưa học</SelectItem>
            <SelectItem value="learned">Đã học</SelectItem>
          </SelectContent>
        </Select>
        <Select value={filters.sort} onValueChange={(v) => setFilters({ sort: v })}>
          <SelectTrigger aria-label="Sắp xếp" className="w-36"><SelectValue /></SelectTrigger>
          <SelectContent>
            <SelectItem value="id">Theo số bài</SelectItem>
            <SelectItem value="level">Theo độ khó</SelectItem>
            <SelectItem value="ac">Dễ làm trước</SelectItem>
            <SelectItem value="hard">Khó làm trước</SelectItem>
          </SelectContent>
        </Select>
      </div>

      <div className="flex items-center justify-between py-3 text-sm text-muted-foreground">
        <span>{shown.length} bài · đã học {ids.length}/{problems.length}</span>
        {filtered && <Button variant="ghost" size="sm" onClick={() => setFilters({ q: "", level: "all", topic: "all", status: "all" })}><X /> Xoá bộ lọc</Button>}
      </div>

      <div className="divide-y overflow-hidden rounded-xl border bg-card">
        {shown.map((p) => (
          <div key={p.slug} className="group flex items-center gap-3 px-3 transition-colors hover:bg-muted/60">
            <button onClick={() => toggle(p.id)} aria-label={isLearned(p.id) ? "Bỏ đánh dấu đã học" : "Đánh dấu đã học"} aria-pressed={isLearned(p.id)} className={cn("flex size-[18px] shrink-0 items-center justify-center rounded-[5px] border transition-colors", isLearned(p.id) ? "border-[var(--viz-ok)] bg-[var(--viz-ok)] text-white" : "opacity-60 hover:opacity-100")}>
              {isLearned(p.id) && <Check className="size-3" strokeWidth={3} />}
            </button>
            <a href={`#/p/${p.slug}`} className="flex min-w-0 flex-1 items-center gap-3 py-2.5">
              <span className="w-11 shrink-0 font-mono text-xs text-muted-foreground">{p.id}</span>
              <span className="min-w-0 flex-1">
                <span className="block truncate text-sm font-medium">{p.title}</span>
                <span className="block truncate text-xs text-muted-foreground">{p.approaches || p.topics.join(" · ")}</span>
              </span>
              <span className="hidden shrink-0 gap-1 lg:flex">
                {p.topics.slice(0, 2).map((t) => <span key={t} className="rounded-md bg-muted px-1.5 py-0.5 text-[11px] text-muted-foreground">{t}</span>)}
              </span>
              <span className="hidden w-11 shrink-0 text-right font-mono text-xs text-muted-foreground tabular-nums sm:block">{Math.round(p.acRate)}%</span>
              <span className={cn("w-14 shrink-0 text-right text-xs font-medium", LEVEL_TEXT[p.level])}>{p.level}</span>
              <span className="w-4 shrink-0">{viz?.has(p.slug) && <Play aria-label="Có mô phỏng" className="size-3.5 text-muted-foreground group-hover:text-foreground" />}</span>
            </a>
          </div>
        ))}
        {!shown.length && <p className="p-8 text-center text-sm text-muted-foreground">Không có bài nào khớp.</p>}
      </div>
    </div>
  );
}
