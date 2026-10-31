// Runs every visualizer on its default input and checks the result and that it produced frames.
//   node site/test-viz.mjs
import { readFileSync } from "node:fs";
import vm from "node:vm";

const here = new URL(".", import.meta.url);
const ctx = vm.createContext({});
for (const f of ["viz-engine.js", "viz-array.js", "viz-structures.js"]) {
  vm.runInContext(readFileSync(new URL("assets/" + f, here), "utf8"), ctx, { filename: f });
}
const Viz = ctx.Viz;
const slugs = JSON.parse(readFileSync(new URL("data/index.json", here), "utf8")).map((p) => p.slug);
let failed = 0;
for (const slug of Viz.slugs()) {
  const def = Viz.registry[slug];
  try {
    if (!slugs.includes(slug)) throw new Error("no such problem in data/index.json");
    const { frames, result } = Viz.trace(def, Viz.parseInput(def.input));
    if (!frames.length) throw new Error("no frames");
    if ("expect" in def && JSON.stringify(result) !== JSON.stringify(def.expect))
      throw new Error(`expected ${JSON.stringify(def.expect)}, got ${JSON.stringify(result)}`);
    console.log(`ok   ${slug} (${frames.length} frames)`);
  } catch (e) {
    failed++;
    console.log(`FAIL ${slug}: ${e.message}`);
  }
}
console.log(`${Viz.slugs().length - failed}/${Viz.slugs().length} passed`);
process.exit(failed ? 1 : 0);
