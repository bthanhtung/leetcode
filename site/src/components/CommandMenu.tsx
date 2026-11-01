import { Moon, Network, Shuffle } from "lucide-react";
import { useEffect, useMemo, useState } from "react";
import { Command, CommandDialog, CommandEmpty, CommandGroup, CommandInput, CommandItem, CommandList, CommandSeparator } from "@/components/ui/command";
import { LEVEL_TEXT, type Problem } from "@/lib/data";
import { go } from "@/lib/router";
import { useTheme } from "@/lib/store";

/** Plain substring search: cmdk's default fuzzy match turns "trie" into unrelated titles. */
const matchAllWords = (value: string, search: string) => {
  const text = value.toLowerCase();
  return search.toLowerCase().split(/\s+/).every((word) => text.includes(word)) ? 1 : 0;
};

export function CommandMenu({ problems }: { problems: Problem[] }) {
  const [open, setOpen] = useState(false);
  const { toggle } = useTheme();
  useEffect(() => {
    const onKey = (e: KeyboardEvent) => {
      const typing = (e.target as Partial<HTMLElement>).closest?.("input, textarea");
      if (((e.metaKey || e.ctrlKey) && e.key.toLowerCase() === "k") || (e.key === "/" && !typing)) {
        e.preventDefault();
        setOpen((o) => !o);
      }
    };
    const onOpen = () => setOpen(true);
    window.addEventListener("keydown", onKey);
    window.addEventListener("open-search", onOpen);
    return () => {
      window.removeEventListener("keydown", onKey);
      window.removeEventListener("open-search", onOpen);
    };
  }, []);
  const pick = (fn: () => void) => { setOpen(false); fn(); };
  const items = useMemo(() => problems.map((p) => (
    <CommandItem key={p.slug} value={`${p.id} ${p.title} ${p.topics.join(" ")} ${p.approaches}`} onSelect={() => pick(() => go(`/p/${p.slug}`))}>
      <span className="w-10 shrink-0 font-mono text-xs text-muted-foreground">{p.id}</span>
      <span className="min-w-0 flex-1 truncate">{p.title}</span>
      <span className={`shrink-0 text-xs ${LEVEL_TEXT[p.level]}`}>{p.level}</span>
    </CommandItem>
  )), [problems]);
  return (
    <CommandDialog open={open} onOpenChange={setOpen} title="Tìm bài" description="Tìm theo tên, số bài, chủ đề hoặc kỹ thuật">
      <Command filter={matchAllWords}>
      <CommandInput placeholder="Tên bài, số bài, kỹ thuật…" />
      <CommandList>
        <CommandEmpty>Không có bài nào khớp.</CommandEmpty>
        <CommandGroup heading="Thao tác">
          <CommandItem onSelect={() => pick(() => go(`/p/${problems[Math.floor(Math.random() * problems.length)].slug}`))}><Shuffle /> Bài ngẫu nhiên</CommandItem>
          <CommandItem onSelect={() => pick(() => go("/topics"))}><Network /> Xem theo chủ đề</CommandItem>
          <CommandItem onSelect={() => pick(toggle)}><Moon /> Đổi giao diện sáng/tối</CommandItem>
        </CommandGroup>
        <CommandSeparator />
        <CommandGroup heading="Bài tập">{items}</CommandGroup>
      </CommandList>
      </Command>
    </CommandDialog>
  );
}
