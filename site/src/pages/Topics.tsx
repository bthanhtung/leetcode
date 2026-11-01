import { useMemo } from "react";
import type { Problem } from "@/lib/data";
import { go } from "@/lib/router";
import { setTopicFilter, useLearned } from "@/lib/store";

export function Topics({ problems }: { problems: Problem[] }) {
  const { ids } = useLearned();
  const topics = useMemo(() => {
    const map = new Map<string, Problem[]>();
    for (const p of problems) for (const t of p.topics) map.set(t, [...(map.get(t) ?? []), p]);
    return [...map].sort((a, b) => b[1].length - a[1].length);
  }, [problems]);
  return (
    <div className="mx-auto w-full max-w-6xl px-4 pt-10 pb-16">
      <h1 className="text-3xl font-semibold tracking-tight">Học theo chủ đề</h1>
      <p className="mt-2 text-muted-foreground">Chọn một kỹ thuật rồi làm liền nhiều bài cùng dạng, nhận ra khuôn mẫu sẽ nhanh hơn làm rải rác.</p>
      <div className="mt-8 grid gap-3 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
        {topics.map(([topic, list]) => {
          const part = (l: string) => (list.filter((p) => p.level === l).length / list.length) * 100;
          const learned = list.filter((p) => ids.includes(p.id)).length;
          return (
            <button key={topic} onClick={() => { setTopicFilter(topic); go("/"); }} className="rounded-xl border bg-card p-4 text-left transition-colors hover:border-foreground/30">
              <div className="flex items-baseline justify-between gap-2">
                <span className="truncate font-medium">{topic}</span>
                <span className="font-mono text-sm text-muted-foreground">{list.length}</span>
              </div>
              <div className="mt-3 flex h-1.5 overflow-hidden rounded-full bg-muted">
                <span className="bg-[var(--level-easy)]" style={{ width: `${part("Easy")}%` }} />
                <span className="bg-[var(--level-medium)]" style={{ width: `${part("Medium")}%` }} />
                <span className="bg-[var(--level-hard)]" style={{ width: `${part("Hard")}%` }} />
              </div>
              <div className="mt-2 text-xs text-muted-foreground">Đã học {learned}/{list.length}</div>
            </button>
          );
        })}
      </div>
    </div>
  );
}
