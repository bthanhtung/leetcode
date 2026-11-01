import { useSyncExternalStore } from "react";

const CHANGE = "routechange";

// Links shared before the move to clean URLs look like /#/p/two-sum.
if (location.hash.startsWith("#/")) history.replaceState(history.state, "", location.hash.slice(1));

const subscribe = (l: () => void) => {
  window.addEventListener("popstate", l);
  window.addEventListener(CHANGE, l);
  return () => {
    window.removeEventListener("popstate", l);
    window.removeEventListener(CHANGE, l);
  };
};

/** Current route, e.g. "/", "/topics" or "/p/two-sum". The old gallery at /viz lands on the topics page. */
export function useRoute() {
  const pathname = useSyncExternalStore(subscribe, () => location.pathname);
  const path = decodeURIComponent(pathname).replace(/(.)\/$/, "$1");
  return path === "/viz" ? "/topics" : path;
}

export function go(path: string) {
  if (path === location.pathname) return;
  history.pushState({ depth: (history.state?.depth ?? 0) + 1 }, "", path);
  window.dispatchEvent(new Event(CHANGE));
}

/** Back never leaves the site for the page the reader came from. */
export function goBack() {
  if (history.state?.depth > 0) history.back();
  else go("/");
}

// Internal links navigate without a page load; new-tab clicks stay with the browser.
document.addEventListener("click", (e) => {
  if (e.defaultPrevented || e.button !== 0 || e.metaKey || e.ctrlKey || e.shiftKey || e.altKey) return;
  const link = (e.target as Partial<Element>).closest?.("a[href^='/']");
  if (!link || link.getAttribute("target")) return;
  e.preventDefault();
  go(link.getAttribute("href")!);
});
