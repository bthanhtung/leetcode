import { useEffect } from "react";
import { CommandMenu } from "@/components/CommandMenu";
import { ErrorBoundary } from "@/components/ErrorBoundary";
import { Header } from "@/components/Header";
import { TooltipProvider } from "@/components/ui/tooltip";
import { useProblems } from "@/lib/data";
import { useRoute } from "@/lib/router";
import { Home } from "@/pages/Home";
import { ProblemPage } from "@/pages/Problem";
import { Topics } from "@/pages/Topics";

export default function App() {
  const route = useRoute();
  const { problems, failed } = useProblems();
  useEffect(() => {
    if (!route.startsWith("/p/")) document.title = "Algorithm Visualizer";
    if (route !== "/") window.scrollTo(0, 0);
  }, [route]);

  let page;
  if (failed) page = <p className="p-8 text-sm text-muted-foreground">Không tải được dữ liệu. Chạy <code>python3 site/build.py</code> trước.</p>;
  else if (!problems) page = null;
  else if (route.startsWith("/p/")) page = <ProblemPage key={route} slug={route.slice(3)} problems={problems} />;
  else if (route === "/topics") page = <Topics problems={problems} />;
  else page = <Home problems={problems} />;

  return (
    <TooltipProvider delayDuration={250}>
      <div className="flex min-h-dvh flex-col">
        <Header />
        <main className="flex min-h-0 flex-1 flex-col"><ErrorBoundary key={route}>{page}</ErrorBoundary></main>
        {problems && <ErrorBoundary><CommandMenu problems={problems} /></ErrorBoundary>}
      </div>
    </TooltipProvider>
  );
}
