// Runs every visualizer on its default input and on each preset, and checks that it behaves:
//   node site/test-viz.mjs [--missing]
// A visualizer fails if its answer differs from `expect`, or a frame points outside the pseudo-code, or it has no
// code, no messages, a frame that draws nothing, or too many frames to watch on the default input. --missing lists problems with no visualizer.
import { readFileSync } from "node:fs";
import * as Viz from "./src/viz/all.js";

const here = new URL(".", import.meta.url);
const problems = JSON.parse(readFileSync(new URL("public/data/index.json", here), "utf8"));
const known = new Set(problems.map((p) => p.slug));
const MAX_DEFAULT_FRAMES = 400;
const VIEWS = ["arrays", "grids", "list", "lists", "tree", "trees", "nary", "stacks", "maps", "graphs", "intervals", "points", "texts"];
let failed = 0;
let total = 0;

function check(slug, def) {
  if (!known.has(slug)) throw new Error("no such problem in data/index.json");
  if (!("expect" in def)) throw new Error("no expected result");
  if (!def.code) throw new Error("no pseudo-code");
  const lines = def.code.replace(/\n$/, "").split("\n").length;
  const { frames, result } = Viz.trace(def, Viz.parseInput(def.input));
  if (!frames.length) throw new Error("no frames");
  if (frames.length > MAX_DEFAULT_FRAMES) throw new Error(`${frames.length} frames on the default input`);
  if (JSON.stringify(result) !== JSON.stringify(def.expect)) throw new Error(`expected ${JSON.stringify(def.expect)}, got ${JSON.stringify(result)}`);
  frames.forEach((f, i) => {
    if (!f.msg) throw new Error(`frame ${i} has no msg`);
    if (!Number.isInteger(f.line) || f.line < 1 || f.line > lines) throw new Error(`frame ${i}: line ${f.line} outside 1..${lines}`);
    if (!VIEWS.some((v) => (Array.isArray(f[v]) ? f[v].length : f[v]))) throw new Error(`frame ${i} draws nothing`);
  });
  for (const p of def.presets ?? []) {
    const got = Viz.trace(def, Viz.parseInput(p.input));
    if (!got.frames.length) throw new Error(`preset ${p.label}: no frames`);
    if ("expect" in p && JSON.stringify(got.result) !== JSON.stringify(p.expect)) throw new Error(`preset ${p.label}: expected ${JSON.stringify(p.expect)}, got ${JSON.stringify(got.result)}`);
  }
  return frames.length;
}

for (const slug of Viz.slugs()) {
  total++;
  try {
    check(slug, Viz.registry[slug]);
  } catch (e) {
    failed++;
    console.log(`FAIL ${slug}: ${e.message}`);
  }
}
console.log(`${total - failed}/${total} passed, ${total}/${problems.length} problems covered`);
if (process.argv.includes("--missing")) console.log(problems.filter((p) => !Viz.has(p.slug)).map((p) => `${p.id} ${p.slug}`).join("\n"));
process.exit(failed ? 1 : 0);
