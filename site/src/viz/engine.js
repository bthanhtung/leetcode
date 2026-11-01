/*
 * Step-by-step algorithm tracer. Pure data: no DOM, so `node site/test-viz.mjs` can run every visualizer.
 *
 * register(slug, { input, code, run, expect?, presets? })
 *   input   default input, one "name = JSON" per line (the reader can edit it)
 *   code    pseudo-code shown beside the stage
 *   run     (args, T) => result; T.step(frame) records one frame
 *   expect  result for the default input
 *   presets optional [{ label, input }] of other interesting inputs
 *
 * A frame holds any of the views below plus `line` (1-based line of `code`), `msg`, `vars`, and `key: true` for a
 * milestone worth marking on the timeline. Tones: cur ok bad mark done win dim c1..c6.
 *   arrays    [{ label, values, hl:{i:tone}, ptr:{i:name|[names]}, range:[l,r], dimOutside, bars, water, small, noIndex, indexLabels }]
 *   grids     [{ label, cells, hl:{"r,c":tone}, rowHead, colHead }]
 *   lists     [{ label, vals, next:[i|-1], hl, ptr }]            (also `list`)
 *   trees     [{ label, nodes:[{v,l,r}], root, hl:{id:tone}, tag:{id:text} }]  (also `tree`)
 *   nary      [{ label, nodes:[{v,ch:[ids]}], root, hl, tag }]   tries, recursion trees
 *   stacks    [{ label, values, hl, horizontal }]                stacks, queues, call stacks
 *   maps      [{ label, entries:[[k,v]], hl:{k:tone} }]          hash maps and sets
 *   graphs    [{ label, nodes:[{id,label?,x?,y?}], edges:[{a,b,w?,tone?}], directed, hl:{id:tone}, tag:{id:text} }]
 *   intervals [{ label, items:[[s,e]], hl:{i:tone}, names? }]
 *   points    [{ label, pts:[[x,y]], hl:{i:tone}, path:[i...] }]
 *   texts     [{ label, value, tone? }]                          a line of output
 */
export const registry = {};

export function register(slug, def) {
  if (registry[slug]) throw new Error(`duplicate visualizer: ${slug}`);
  registry[slug] = def;
}

export const has = (slug) => !!registry[slug];
export const slugs = () => Object.keys(registry);

export function parseInput(text) {
  const args = {};
  for (const raw of text.split("\n")) {
    const line = raw.trim();
    if (!line || line.startsWith("#")) continue;
    const eq = line.indexOf("=");
    if (eq < 0) throw new Error(`Dòng "${line}" thiếu dấu =`);
    const name = line.slice(0, eq).trim();
    const value = line.slice(eq + 1).trim();
    try {
      args[name] = JSON.parse(value);
    } catch {
      throw new Error(`Giá trị của ${name} không phải JSON hợp lệ: ${value}`);
    }
  }
  return args;
}

export const MAX_FRAMES = 3000;

export function trace(def, args) {
  const frames = [];
  const T = {
    step(frame) {
      if (frames.length >= MAX_FRAMES) throw new Error(`Quá nhiều bước (> ${MAX_FRAMES}), hãy thử dữ liệu nhỏ hơn`);
      frames.push(JSON.parse(JSON.stringify(frame)));
    },
  };
  const result = def.run(args, T);
  return { frames, result };
}

/* ---------- view builders ---------- */

export const A = (label, values, o = {}) => ({ label, values, ...o });
export const G = (label, cells, o = {}) => ({ label, cells, ...o });
export const S = (label, values, o = {}) => ({ label, values, ...o });
export const Q = (label, values, o = {}) => ({ label, values, horizontal: true, ...o });
export const M = (label, source, o = {}) => ({
  label,
  entries: source instanceof Map ? [...source] : source instanceof Set ? [...source].map((k) => [k]) : Array.isArray(source) ? source : Object.entries(source),
  ...o,
});
export const chars = (s) => s.split("");
export const clone = (g) => g.map((r) => [...r]);
export const matrix = (m, n, v = 0) => Array.from({ length: m }, () => Array(n).fill(v));
export const DIRS = [[1, 0], [-1, 0], [0, 1], [0, -1]];

/** Linked list view from a plain array, or from { vals, next }. */
export const L = (label, list, o = {}) => (Array.isArray(list) ? { label, vals: list, next: list.map((_, i) => (i + 1 < list.length ? i + 1 : -1)), ...o } : { label, vals: list.vals, next: list.next, ...o });

/** Level-order array with nulls -> { nodes: [{v, l, r}], root } (l/r are node ids or -1). */
export function buildTree(arr) {
  const nodes = [];
  if (!arr.length || arr[0] === null) return { nodes, root: -1 };
  nodes.push({ v: arr[0], l: -1, r: -1 });
  const queue = [0];
  let i = 1;
  while (queue.length && i < arr.length) {
    const id = queue.shift();
    for (const side of ["l", "r"]) {
      if (i < arr.length && arr[i] !== null) {
        nodes.push({ v: arr[i], l: -1, r: -1 });
        nodes[id][side] = nodes.length - 1;
        queue.push(nodes.length - 1);
      }
      i++;
    }
  }
  return { nodes, root: 0 };
}

export function treeToArray(tree) {
  const out = [];
  const queue = [tree.root];
  while (queue.length) {
    const id = queue.shift();
    if (id === -1) {
      out.push(null);
      continue;
    }
    out.push(tree.nodes[id].v);
    queue.push(tree.nodes[id].l, tree.nodes[id].r);
  }
  while (out.length && out[out.length - 1] === null) out.pop();
  return out;
}

/** Records a recursion tree as it unfolds: enter()/leave() around each call, view() for the frame. */
export class RecTree {
  constructor(label = "cây đệ quy") {
    this.label = label;
    this.nodes = [];
    this.tone = {};
    this.path = [];
  }
  enter(text) {
    const id = this.nodes.length;
    this.nodes.push({ v: text, ch: [] });
    if (this.path.length) this.nodes[this.path[this.path.length - 1]].ch.push(id);
    this.path.push(id);
    return id;
  }
  leave(tone) {
    const id = this.path.pop();
    if (tone) this.tone[id] = tone;
    return id;
  }
  mark(tone) {
    this.tone[this.path[this.path.length - 1]] = tone;
  }
  view() {
    const hl = { ...this.tone };
    for (const id of this.path) if (!hl[id]) hl[id] = "win";
    if (this.path.length) hl[this.path[this.path.length - 1]] = "cur";
    return { label: this.label, nodes: this.nodes, root: this.nodes.length ? 0 : -1, hl };
  }
}

/** Union-find that keeps its parent array visible. */
export class DSU {
  constructor(n) {
    this.parent = Array.from({ length: n }, (_, i) => i);
    this.size = Array(n).fill(1);
    this.count = n;
  }
  find(x) {
    while (this.parent[x] !== x) {
      this.parent[x] = this.parent[this.parent[x]];
      x = this.parent[x];
    }
    return x;
  }
  union(a, b) {
    a = this.find(a);
    b = this.find(b);
    if (a === b) return false;
    if (this.size[a] < this.size[b]) [a, b] = [b, a];
    this.parent[b] = a;
    this.size[a] += this.size[b];
    this.count--;
    return true;
  }
}

/** Binary heap. `before(a, b)` says a sits above b; items() gives the backing array for an array or tree view. */
export class Heap {
  constructor(before = (a, b) => a < b) {
    this.a = [];
    this.before = before;
  }
  get size() {
    return this.a.length;
  }
  peek() {
    return this.a[0];
  }
  push(x) {
    const a = this.a;
    a.push(x);
    let i = a.length - 1;
    while (i > 0) {
      const p = (i - 1) >> 1;
      if (!this.before(a[i], a[p])) break;
      [a[i], a[p]] = [a[p], a[i]];
      i = p;
    }
  }
  pop() {
    const a = this.a;
    const top = a[0];
    const last = a.pop();
    if (a.length) {
      a[0] = last;
      let i = 0;
      for (;;) {
        let m = i;
        for (const c of [2 * i + 1, 2 * i + 2]) if (c < a.length && this.before(a[c], a[m])) m = c;
        if (m === i) break;
        [a[i], a[m]] = [a[m], a[i]];
        i = m;
      }
    }
    return top;
  }
  /** Tree view of the heap. */
  tree(label, fmt = (x) => x, hl = {}) {
    const nodes = this.a.map((x, i) => ({ v: fmt(x), l: 2 * i + 1 < this.a.length ? 2 * i + 1 : -1, r: 2 * i + 2 < this.a.length ? 2 * i + 2 : -1 }));
    return { label, nodes, root: nodes.length ? 0 : -1, hl };
  }
}

/** { i: 2, j: 2, k: -1 } -> { 2: ["i", "j"] }: pointer labels by index; names outside the array are dropped by passing n. */
export function ptrs(byName, n = Infinity) {
  const out = {};
  for (const [name, i] of Object.entries(byName)) {
    if (i === null || i === undefined || i < 0 || i >= n) continue;
    (out[i] ??= []).push(name);
  }
  return out;
}

/** Marks a list of indexes with one tone: tones([1, 2], "ok") -> { 1: "ok", 2: "ok" }. */
export const tones = (indexes, tone) => Object.fromEntries(indexes.map((i) => [i, tone]));
