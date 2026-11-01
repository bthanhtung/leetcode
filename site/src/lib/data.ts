import { useEffect, useState } from "react";

export type Level = "Easy" | "Medium" | "Hard";

export interface Problem {
  id: number;
  slug: string;
  title: string;
  level: Level;
  topics: string[];
  acRate: number;
  approaches: string;
  folder: string;
}

export interface ProblemDetail {
  statement: string;
  solution: string;
  related: string;
  java: string;
}

let cache: Promise<Problem[]> | null = null;

export function loadProblems(): Promise<Problem[]> {
  cache ??= fetch("data/index.json").then((r) => {
    if (!r.ok) throw new Error(String(r.status));
    return r.json();
  });
  return cache;
}

export function useProblems() {
  const [problems, setProblems] = useState<Problem[] | null>(null);
  const [failed, setFailed] = useState(false);
  useEffect(() => {
    loadProblems().then(setProblems, () => setFailed(true));
  }, []);
  return { problems, failed };
}

export const LEVEL_TEXT: Record<Level, string> = {
  Easy: "text-[var(--level-easy)]",
  Medium: "text-[var(--level-medium)]",
  Hard: "text-[var(--level-hard)]",
};
