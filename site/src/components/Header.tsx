import { Moon, Search, Sun } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Kbd } from "@/components/ui/kbd";
import { useRoute } from "@/lib/router";
import { useTheme } from "@/lib/store";
import { cn } from "@/lib/utils";

export const openSearch = () => window.dispatchEvent(new Event("open-search"));

function GitHub() {
  return (
    <svg viewBox="0 0 16 16" fill="currentColor" aria-hidden="true">
      <path d="M8 0C3.58 0 0 3.58 0 8c0 3.54 2.29 6.53 5.47 7.59.4.07.55-.17.55-.38v-1.33c-2.23.48-2.7-1.08-2.7-1.08-.36-.92-.89-1.17-.89-1.17-.73-.5.05-.49.05-.49.8.06 1.23.82 1.23.82.72 1.22 1.87.87 2.33.66.07-.52.28-.87.5-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82a7.6 7.6 0 0 1 4 0c1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48v2.2c0 .21.15.46.55.38A8 8 0 0 0 16 8c0-4.42-3.58-8-8-8Z" />
    </svg>
  );
}

export function Header() {
  const route = useRoute();
  const { theme, toggle } = useTheme();
  const link = (href: string, text: string, on: boolean) => (
    <a href={`#${href}`} className={cn("rounded-md px-2.5 py-1 text-sm transition-colors hover:text-foreground", on ? "font-medium text-foreground" : "text-muted-foreground")}>{text}</a>
  );
  return (
    <header className="sticky top-0 z-40 flex h-14 shrink-0 items-center gap-2 border-b bg-background/85 px-4 backdrop-blur">
      <a href="#/" className="mr-3 flex items-center gap-2 font-semibold tracking-tight">
        <svg viewBox="0 0 32 32" className="size-6" aria-hidden="true">
          <rect width="32" height="32" rx="8" className="fill-foreground" />
          <rect x="6" y="16" width="4" height="10" rx="1" className="fill-background" />
          <rect x="12" y="10" width="4" height="16" rx="1" className="fill-background" />
          <rect x="18" y="6" width="4" height="20" rx="1" fill="#fbbf24" />
          <rect x="24" y="13" width="4" height="13" rx="1" className="fill-background" />
        </svg>
        <span className="hidden sm:inline">Algorithm Visualizer</span>
      </a>
      <nav className="flex items-center">
        {link("/", "Bài tập", route === "/" || route.startsWith("/p/"))}
        {link("/topics", "Chủ đề", route === "/topics")}
      </nav>
      <div className="ml-auto flex items-center gap-1">
        <Button variant="outline" size="sm" className="w-9 justify-center text-muted-foreground sm:w-56 sm:justify-between" onClick={openSearch} aria-label="Tìm bài">
          <span className="flex items-center gap-1.5"><Search /><span className="hidden sm:inline">Tìm bài…</span></span>
          <Kbd className="hidden sm:inline-flex">⌘K</Kbd>
        </Button>
        <Button asChild variant="ghost" size="icon-sm"><a href="https://github.com/bthanhtung/leetcode" target="_blank" rel="noopener" aria-label="Mã nguồn trên GitHub"><GitHub /></a></Button>
        <Button variant="ghost" size="icon-sm" onClick={toggle} aria-label="Đổi giao diện sáng/tối">{theme === "dark" ? <Sun /> : <Moon />}</Button>
      </div>
    </header>
  );
}
