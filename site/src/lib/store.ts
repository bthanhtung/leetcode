/* Reader conveniences kept in localStorage. Every access is guarded: storage can be missing or blocked. */
import { useSyncExternalStore } from "react";

export function read<T>(key: string, fallback: T): T {
  try {
    const raw = localStorage.getItem(key);
    return raw === null ? fallback : (JSON.parse(raw) as T);
  } catch {
    return fallback;
  }
}

export function write(key: string, value: unknown) {
  try {
    localStorage.setItem(key, JSON.stringify(value));
  } catch {
    /* the app works without it */
  }
}

function createStore<T>(key: string, fallback: T) {
  let value = read(key, fallback);
  const listeners = new Set<() => void>();
  return {
    get: () => value,
    set(next: T) {
      value = next;
      write(key, next);
      listeners.forEach((l) => l());
    },
    subscribe(l: () => void) {
      listeners.add(l);
      return () => listeners.delete(l);
    },
  };
}

const learned = createStore<number[]>("learned", []);

export function useLearned() {
  const ids = useSyncExternalStore(learned.subscribe, learned.get);
  return {
    ids,
    isLearned: (id: number) => ids.includes(id),
    toggle: (id: number) => learned.set(ids.includes(id) ? ids.filter((x) => x !== id) : [...ids, id]),
  };
}

export type Theme = "light" | "dark";
const theme = createStore<Theme>("theme", matchMedia("(prefers-color-scheme: dark)").matches ? "dark" : "light");

function applyTheme(t: Theme) {
  document.documentElement.classList.toggle("dark", t === "dark");
}
applyTheme(theme.get());

export function useTheme() {
  const value = useSyncExternalStore(theme.subscribe, theme.get);
  return {
    theme: value,
    toggle() {
      const next = value === "dark" ? "light" : "dark";
      applyTheme(next);
      theme.set(next);
    },
  };
}

export interface Filters {
  q: string;
  level: string;
  topic: string;
  status: string;
  sort: string;
}

const filters = createStore<Filters>("filters-v2", { q: "", level: "all", topic: "all", status: "all", sort: "id" });

export function useFilters() {
  const value = useSyncExternalStore(filters.subscribe, filters.get);
  return { filters: value, setFilters: (patch: Partial<Filters>) => filters.set({ ...filters.get(), ...patch }) };
}

export const setTopicFilter = (topic: string) => filters.set({ ...filters.get(), q: "", level: "all", status: "all", topic });
