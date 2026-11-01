import { useEffect, useState } from "react";

export interface VizApi {
  has(slug: string): boolean;
  slugs(): string[];
  registry: Record<string, VizDef>;
  parseInput(text: string): Record<string, unknown>;
  trace(def: VizDef, args: Record<string, unknown>): { frames: Frame[]; result: unknown };
}

export interface VizDef {
  input: string;
  code?: string;
  presets?: { label: string; input: string }[];
}

// Frames are plain data produced by src/viz; their shapes are documented in engine.js.
// eslint-disable-next-line @typescript-eslint/no-explicit-any
export type Frame = Record<string, any>;

let loaded: Promise<VizApi> | null = null;
export const loadViz = () => (loaded ??= import("@/viz/all.js") as unknown as Promise<VizApi>);

export function useViz() {
  const [viz, setViz] = useState<VizApi | null>(null);
  useEffect(() => {
    loadViz().then(setViz);
  }, []);
  return viz;
}
