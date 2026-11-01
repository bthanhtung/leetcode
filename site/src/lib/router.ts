import { useSyncExternalStore } from "react";

const subscribe = (l: () => void) => {
  window.addEventListener("hashchange", l);
  return () => window.removeEventListener("hashchange", l);
};

/** Current route, e.g. "/", "/topics" or "/p/two-sum". Old links to #/viz land on the topics page. */
export function useRoute() {
  const hash = useSyncExternalStore(subscribe, () => location.hash);
  const path = decodeURIComponent(hash.replace(/^#/, "")) || "/";
  return path === "/viz" ? "/topics" : path;
}

export const go = (path: string) => {
  location.hash = path;
};
