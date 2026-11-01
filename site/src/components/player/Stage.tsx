/* Draws one frame. Elements keep stable keys between frames so colours and positions animate instead of flickering. */
import { useCallback, useEffect, useId, useLayoutEffect, useRef, useState } from "react";
import type { Frame } from "@/hooks/use-viz";
import { cn } from "@/lib/utils";

// eslint-disable-next-line @typescript-eslint/no-explicit-any
type Any = any;

const fmt = (v: unknown) => (v === null || v === undefined ? "" : typeof v === "string" ? v : JSON.stringify(v));

function Label({ children }: { children?: string }) {
  if (!children) return null;
  return <div className="mb-1.5 font-mono text-[11px] font-medium tracking-wide text-muted-foreground">{children}</div>;
}

function Value({ v }: { v: unknown }) {
  const text = fmt(v);
  return <span key={text} className="pop">{text}</span>;
}

/** SVGs keep their natural size so labels stay readable; only very wide drawings shrink, then the stage scrolls. */
const svgWidth = (w: number) => (w > 880 ? Math.max(w * 0.6, 880) : w);

function ArrayView({ a }: { a: Any }) {
  const values: unknown[] = a.values ?? [];
  const n = values.length;
  const hl = a.hl ?? {};
  const [rl, rr] = a.range ?? [-1, -2];
  const longest = Math.max(1, ...values.map((v) => fmt(v).length));
  const compact = n > 22 && longest <= 2;
  const cell = a.bars ? Math.max(24, Math.min(44, Math.floor(680 / Math.max(1, n)) - 4)) : compact ? 22 : Math.max(a.small ? 30 : 40, longest * 8 + 14);
  const gap = compact ? 2 : 4;
  const nums = values.filter((v): v is number => typeof v === "number");
  const max = Math.max(1, ...nums.map(Math.abs), ...(a.water ?? []).map((w: number, i: number) => w + Math.max(0, Number(values[i]) || 0)));
  const toneOf = (i: number) => {
    if (hl[i]) return hl[i];
    if (i >= rl && i <= rr) return "win";
    if (a.dimOutside && (i < rl || i > rr)) return "dim";
    return "base";
  };
  const pills: { name: string; i: number; row: number }[] = [];
  for (const [i, raw] of Object.entries(a.ptr ?? {})) {
    (Array.isArray(raw) ? raw : [raw]).forEach((name: string, row: number) => pills.push({ name, i: Number(i), row }));
  }
  const rows = pills.reduce((m, p) => Math.max(m, p.row + 1), 0);
  return (
    <div className="max-w-full">
      <Label>{a.label}</Label>
      <div className="relative w-max" style={{ paddingBottom: rows ? rows * 19 + 4 : 0 }}>
        <div className="flex items-end" style={{ gap }}>
          {!n && <div className="text-sm text-muted-foreground">∅</div>}
          {values.map((v, i) => (
            <div key={i} data-tone={toneOf(i)} className="flex flex-col items-center" style={{ width: cell }}>
              {!a.noIndex && <div className="mb-0.5 font-mono text-[10px] text-muted-foreground">{a.indexLabels ? a.indexLabels[i] : i}</div>}
              {a.bars ? (
                <>
                  <div className="flex h-32 w-full flex-col justify-end">
                    {a.water?.[i] > 0 && <div className="w-full bg-sky-400/40 transition-[height] duration-300" style={{ height: (a.water[i] / max) * 128 }} />}
                    <div className="tone-box w-full rounded-t-sm transition-[height] duration-300" style={{ height: (Math.max(0, Number(v) || 0) / max) * 128 }} />
                  </div>
                  <div className="mt-1 font-mono text-[11px]"><Value v={v} /></div>
                </>
              ) : (
                <div className={cn("tone-box flex items-center justify-center rounded-md font-mono", compact ? "text-xs" : "text-sm")} style={{ width: cell, height: compact ? 26 : a.small ? 30 : 40 }}>
                  <Value v={v} />
                </div>
              )}
            </div>
          ))}
        </div>
        {pills.map((p) => (
          <div key={p.name} className="ptr-pill absolute -translate-x-1/2 whitespace-nowrap rounded bg-foreground px-1 font-mono text-[10px] leading-4 text-background" style={{ left: p.i * (cell + gap) + cell / 2, bottom: (rows - 1 - p.row) * 19 }}>
            {p.name}
          </div>
        ))}
      </div>
    </div>
  );
}

function GridView({ g }: { g: Any }) {
  const hl = g.hl ?? {};
  const longest = Math.max(1, ...g.cells.flat().map((v: unknown) => fmt(v).length));
  const size = Math.max(34, longest * 8 + 12);
  return (
    <div className="max-w-full">
      <Label>{g.label}</Label>
      <table className="border-separate border-spacing-[3px] font-mono text-[13px]">
        <tbody>
          {g.colHead && (
            <tr>
              {g.rowHead && <th />}
              {g.colHead.map((h: unknown, i: number) => <th key={i} className="font-normal text-muted-foreground">{fmt(h)}</th>)}
            </tr>
          )}
          {g.cells.map((row: unknown[], r: number) => (
            <tr key={r}>
              {g.rowHead && <th className="pr-1 font-normal text-muted-foreground">{fmt(g.rowHead[r])}</th>}
              {row.map((v, c) => (
                <td key={c} data-tone={hl[`${r},${c}`] ?? "base"} className="tone-box rounded text-center" style={{ minWidth: size, height: 34 }}>
                  <Value v={v} />
                </td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

function Arrow({ id }: { id: string }) {
  return (
    <defs>
      <marker id={id} viewBox="0 0 10 10" refX="9" refY="5" markerWidth="7" markerHeight="7" orient="auto-start-reverse">
        <path d="M 0 0 L 10 5 L 0 10 z" className="viz-arrow" />
      </marker>
    </defs>
  );
}

function ListView({ L }: { L: Any }) {
  const id = useId();
  const n = L.vals.length;
  const gap = 84;
  const nodeW = 46;
  const width = Math.max(1, n) * gap;
  const y = 52;
  const hl = L.hl ?? {};
  const ptr = L.ptr ?? {};
  const x = (i: number) => 10 + i * gap;
  return (
    <div className="max-w-full">
      <Label>{L.label ?? "list"}</Label>
      <svg viewBox={`0 0 ${width} 124`} width={svgWidth(width)} className="max-w-none overflow-visible">
        <Arrow id={id} />
        {L.vals.map((_: unknown, i: number) => {
          const j = L.next[i];
          if (j === -1 || j === undefined || j === null) return null;
          const a = x(i), b = x(j);
          const d = j === i + 1 ? `M ${a + nodeW} ${y} L ${b - 3} ${y}`
            : j === i - 1 ? `M ${a} ${y - 8} Q ${(a + b + nodeW) / 2} ${y - 44} ${b + nodeW} ${y - 8}`
            : `M ${a + nodeW / 2} ${y + 18} C ${a + nodeW / 2} ${y + 66}, ${b + nodeW / 2} ${y + 66}, ${b + nodeW / 2} ${y + 22}`;
          return <path key={i} d={d} className="viz-edge" markerEnd={`url(#${id})`} />;
        })}
        {L.vals.map((v: unknown, i: number) => {
          const names = ptr[i] ? (Array.isArray(ptr[i]) ? ptr[i] : [ptr[i]]).join(" ") : "";
          return (
            <g key={i} data-tone={hl[i] ?? "base"} className="tone-node">
              <rect x={x(i)} y={y - 18} width={nodeW} height={36} rx={8} />
              <text x={x(i) + nodeW / 2} y={y + 5} fontSize={14}>{fmt(v)}</text>
              {names && <text x={x(i) + nodeW / 2} y={y - 26} fontSize={11} style={{ fill: "var(--muted-foreground)" }}>{names}</text>}
            </g>
          );
        })}
      </svg>
    </div>
  );
}

function TreeView({ t }: { t: Any }) {
  if (t.root === -1) return <div><Label>{t.label}</Label><div className="text-sm text-muted-foreground">cây rỗng</div></div>;
  const pos: Record<number, { x: number; y: number }> = {};
  let order = 0;
  let depth = 0;
  const walk = (id: number, d: number) => {
    if (id === -1) return;
    walk(t.nodes[id].l, d + 1);
    pos[id] = { x: order++, y: d };
    depth = Math.max(depth, d);
    walk(t.nodes[id].r, d + 1);
  };
  walk(t.root, 0);
  const gx = 50, gy = 64;
  const width = order * gx + 44, height = (depth + 1) * gy + 12;
  const P = (id: number) => ({ x: 34 + pos[id].x * gx, y: 26 + pos[id].y * gy });
  const hl = t.hl ?? {};
  const tag = t.tag ?? t.label2 ?? {};
  const ids = Object.keys(pos).map(Number);
  return (
    <div className="max-w-full">
      <Label>{typeof t.label === "string" ? t.label : undefined}</Label>
      <svg viewBox={`0 0 ${width} ${height}`} width={svgWidth(width)} className="max-w-none overflow-visible">
        {ids.flatMap((id) => [t.nodes[id].l, t.nodes[id].r].filter((c: number) => c !== -1).map((c: number) => {
          const a = P(id), b = P(c);
          return <line key={`${id}-${c}`} x1={a.x} y1={a.y} x2={b.x} y2={b.y} className="viz-edge" data-tone={hl[`${id}-${c}`]} />;
        }))}
        {ids.map((id) => {
          const p = P(id);
          return (
            <g key={id} data-tone={hl[id] ?? "base"} className="tone-node" style={{ transform: `translate(${p.x}px,${p.y}px)` }}>
              <circle r={18} />
              <text y={5} fontSize={fmt(t.nodes[id].v).length > 3 ? 10 : 13}>{fmt(t.nodes[id].v)}</text>
              {tag[id] !== undefined && <text x={24} y={-14} fontSize={10} style={{ fill: "var(--viz-cur)", textAnchor: "start" }}>{fmt(tag[id])}</text>}
            </g>
          );
        })}
      </svg>
    </div>
  );
}

function NaryView({ t }: { t: Any }) {
  if (t.root === -1) return null;
  const pos: Record<number, { x: number; y: number }> = {};
  let leaf = 0, depth = 0;
  const longest = Math.max(1, ...t.nodes.map((n: Any) => fmt(n.v).length));
  const w = Math.max(32, Math.min(longest, 22) * 7.2 + 14);
  const walk = (id: number, d: number): number => {
    depth = Math.max(depth, d);
    const ch: number[] = t.nodes[id].ch;
    const x = ch.length ? ch.map((c) => walk(c, d + 1)).reduce((s, v, _i, all) => s + v / all.length, 0) : leaf++;
    pos[id] = { x, y: d };
    return x;
  };
  walk(t.root, 0);
  const gx = w + 12, gy = 56;
  const width = Math.max(1, leaf) * gx + 8, height = (depth + 1) * gy;
  const P = (id: number) => ({ x: 4 + w / 2 + pos[id].x * gx, y: 20 + pos[id].y * gy });
  const hl = t.hl ?? {};
  const ids = Object.keys(pos).map(Number);
  return (
    <div className="max-w-full">
      <Label>{t.label}</Label>
      <svg viewBox={`0 0 ${width} ${height}`} width={svgWidth(width)} className="max-w-none overflow-visible">
        {ids.flatMap((id) => t.nodes[id].ch.map((c: number) => {
          const a = P(id), b = P(c);
          return <path key={`${id}-${c}`} d={`M ${a.x} ${a.y + 14} C ${a.x} ${a.y + 34}, ${b.x} ${b.y - 34}, ${b.x} ${b.y - 14}`} className="viz-edge" />;
        }))}
        {ids.map((id) => {
          const p = P(id);
          return (
            <g key={id} data-tone={hl[id] ?? "base"} className="tone-node" style={{ transform: `translate(${p.x}px,${p.y}px)` }}>
              <rect x={-w / 2} y={-14} width={w} height={28} rx={7} />
              <text y={4} fontSize={12}>{fmt(t.nodes[id].v)}</text>
            </g>
          );
        })}
      </svg>
    </div>
  );
}

function layered(nodes: Any[], edges: Any[]) {
  const depth: Record<string, number> = {};
  nodes.forEach((n) => (depth[n.id] = 0));
  for (let pass = 0; pass < nodes.length; pass++) {
    for (const e of edges) depth[e.b] = Math.max(depth[e.b], Math.min(depth[e.a] + 1, nodes.length));
  }
  const columns: Record<number, Any[]> = {};
  nodes.forEach((n) => (columns[depth[n.id]] ??= []).push(n));
  const out: Record<string, { x: number; y: number }> = {};
  const height = Math.max(...Object.values(columns).map((c) => c.length));
  for (const [d, col] of Object.entries(columns)) {
    col.forEach((n, i) => (out[n.id] = { x: 40 + Number(d) * 120, y: 36 + (i + (height - col.length) / 2) * 74 }));
  }
  return out;
}

function GraphView({ g }: { g: Any }) {
  const id = useId();
  const nodes: Any[] = g.nodes;
  const n = nodes.length;
  let pos: Record<string, { x: number; y: number }>;
  if (n && nodes[0].x !== undefined) {
    pos = Object.fromEntries(nodes.map((v) => [v.id, { x: 36 + v.x * 74, y: 36 + v.y * 74 }]));
  } else if (g.layout === "layered") {
    pos = layered(nodes, g.edges);
  } else {
    const r = Math.max(84, n * 19);
    pos = Object.fromEntries(nodes.map((v, i) => {
      const t = (2 * Math.PI * i) / n - Math.PI / 2;
      return [v.id, { x: r + 36 + r * Math.cos(t), y: r + 36 + r * Math.sin(t) }];
    }));
  }
  const width = Math.max(120, ...Object.values(pos).map((p) => p.x + 44));
  const height = Math.max(90, ...Object.values(pos).map((p) => p.y + 40));
  const hl = g.hl ?? {};
  const tag = g.tag ?? {};
  return (
    <div className="max-w-full">
      <Label>{g.label}</Label>
      <svg viewBox={`0 0 ${width} ${height}`} width={svgWidth(width)} className="max-w-none overflow-visible">
        <Arrow id={id} />
        {g.edges.map((e: Any, i: number) => {
          const a = pos[e.a], b = pos[e.b];
          if (!a || !b) return null;
          const dx = b.x - a.x, dy = b.y - a.y, len = Math.hypot(dx, dy) || 1;
          const ux = dx / len, uy = dy / len;
          const back = g.directed && g.edges.some((o: Any) => o.a === e.b && o.b === e.a);
          const bend = back ? 16 : 0;
          const mx = (a.x + b.x) / 2 - uy * bend, my = (a.y + b.y) / 2 + ux * bend;
          return (
            <g key={i}>
              <path d={`M ${a.x + ux * 19} ${a.y + uy * 19} Q ${mx} ${my} ${b.x - ux * 21} ${b.y - uy * 21}`} className="viz-edge" data-tone={e.tone} markerEnd={g.directed ? `url(#${id})` : undefined} />
              {e.w !== undefined && <text x={mx - uy * 10} y={my + ux * 10 + 3} fontSize={11} textAnchor="middle" className="fill-muted-foreground font-mono">{fmt(e.w)}</text>}
            </g>
          );
        })}
        {nodes.map((v) => (
          <g key={v.id} data-tone={hl[v.id] ?? "base"} className="tone-node" style={{ transform: `translate(${pos[v.id].x}px,${pos[v.id].y}px)` }}>
            <circle r={18} />
            <text y={5} fontSize={fmt(v.label ?? v.id).length > 3 ? 10 : 13}>{fmt(v.label ?? v.id)}</text>
            {tag[v.id] !== undefined && <text x={23} y={-14} fontSize={10} style={{ fill: "var(--viz-cur)", textAnchor: "start" }}>{fmt(tag[v.id])}</text>}
          </g>
        ))}
      </svg>
    </div>
  );
}

function StackView({ s }: { s: Any }) {
  const values: unknown[] = s.values ?? [];
  const hl = s.hl ?? {};
  const horizontal = s.horizontal;
  return (
    <div>
      <Label>{s.label}</Label>
      <div className={cn("flex min-h-9 min-w-14 gap-1 rounded-lg border border-dashed p-1", horizontal ? "flex-row flex-wrap items-center" : "flex-col-reverse items-stretch justify-end")}>
        {!values.length && <span className="self-center px-2 text-xs text-muted-foreground">rỗng</span>}
        {values.map((v, i) => (
          <div key={i} data-tone={hl[i] ?? "base"} className="tone-box pop rounded px-2 py-0.5 text-center font-mono text-[13px]">{fmt(v)}</div>
        ))}
      </div>
    </div>
  );
}

function MapView({ m }: { m: Any }) {
  const hl = m.hl ?? {};
  return (
    <div className="min-w-0 max-w-full">
      <Label>{m.label}</Label>
      <div className="flex min-h-9 max-w-xl min-w-14 flex-wrap gap-1 rounded-lg border border-dashed p-1">
        {!m.entries.length && <span className="self-center px-2 text-xs text-muted-foreground">rỗng</span>}
        {m.entries.map(([k, v]: [unknown, unknown], i: number) => (
          <div key={fmt(k) + i} data-tone={hl[fmt(k)] ?? "base"} className="tone-box flex overflow-hidden rounded font-mono text-[13px]">
            <span className="px-1.5 py-0.5">{fmt(k)}</span>
            {v !== undefined && <span className="border-l border-[var(--t-bd)] px-1.5 py-0.5 opacity-80"><Value v={v} /></span>}
          </div>
        ))}
      </div>
    </div>
  );
}

function IntervalView({ g }: { g: Any }) {
  const items: [number, number][] = g.items;
  const hl = g.hl ?? {};
  const lo = Math.min(0, ...items.map((x) => x[0]), ...(g.axis ?? []));
  const hi = Math.max(1, ...items.map((x) => x[1]), ...(g.axis ?? []));
  const span = hi - lo || 1;
  const pct = (v: number) => ((v - lo) / span) * 100;
  return (
    <div className="w-[640px] max-w-full">
      <Label>{g.label}</Label>
      <div className="relative space-y-1 rounded-lg border border-dashed p-2">
        {g.cursor !== undefined && <div className="absolute inset-y-1 w-px bg-[var(--viz-cur)] transition-[left] duration-300" style={{ left: `calc(${pct(g.cursor)}% )` }} />}
        {items.map(([s, e], i) => (
          <div key={i} className="relative h-6">
            <div data-tone={hl[i] ?? "c1"} className="tone-box absolute inset-y-0 flex min-w-1 items-center justify-center overflow-hidden rounded whitespace-nowrap px-1 font-mono text-[11px] transition-[left,width] duration-300" style={{ left: `${pct(s)}%`, width: `${Math.max(pct(e) - pct(s), 1)}%` }}>
              {g.names ? g.names[i] : `${s}–${e}`}
            </div>
          </div>
        ))}
        {!items.length && <div className="text-center text-xs text-muted-foreground">rỗng</div>}
      </div>
    </div>
  );
}

function PointsView({ p }: { p: Any }) {
  const pts: [number, number][] = p.pts;
  const xs = pts.map((v) => v[0]), ys = pts.map((v) => v[1]);
  const minX = Math.min(...xs), maxX = Math.max(...xs), minY = Math.min(...ys), maxY = Math.max(...ys);
  const scale = Math.min(300 / (maxX - minX || 1), 220 / (maxY - minY || 1));
  const X = (v: number) => 24 + (v - minX) * scale;
  const Y = (v: number) => 24 + (maxY - v) * scale;
  const width = 48 + (maxX - minX) * scale, height = 48 + (maxY - minY) * scale;
  const hl = p.hl ?? {};
  return (
    <div>
      <Label>{p.label}</Label>
      <svg viewBox={`0 0 ${width} ${height}`} width={width} className="overflow-visible">
        {p.origin && <circle cx={X(0)} cy={Y(0)} r={3} className="fill-muted-foreground" />}
        {p.path?.length > 1 && <polyline points={p.path.map((i: number) => `${X(pts[i][0])},${Y(pts[i][1])}`).join(" ")} className="viz-edge" data-tone="cur" />}
        {pts.map(([x, y], i) => (
          <g key={i} data-tone={hl[i] ?? "base"} className="tone-node" style={{ transform: `translate(${X(x)}px,${Y(y)}px)` }}>
            <circle r={7} />
          </g>
        ))}
      </svg>
    </div>
  );
}

const many = (one: unknown, list: unknown): Any[] => [...(one ? [one] : []), ...((list as Any[]) ?? [])];

const MIN_ZOOM = 0.55;

/**
 * Scales the drawing down to fit its pane, then lets the pane scroll past MIN_ZOOM. During a run the zoom only
 * shrinks, so the drawing does not pulse as a stack grows and empties; a new run or a resized pane starts over.
 */
export function FitStage({ frame, run }: { frame: Frame; run: unknown }) {
  const outer = useRef<HTMLDivElement>(null);
  const inner = useRef<HTMLDivElement>(null);
  const [zoom, setZoom] = useState(1);
  const zoomRef = useRef(1);

  const fit = useCallback(() => {
    const o = outer.current, i = inner.current;
    if (!o || !i) return 1;
    const style = getComputedStyle(o);
    const width = o.clientWidth - parseFloat(style.paddingLeft) - parseFloat(style.paddingRight);
    const height = o.clientHeight - parseFloat(style.paddingTop) - parseFloat(style.paddingBottom);
    const box = i.getBoundingClientRect();
    return Math.max(MIN_ZOOM, Math.min(1, (width * zoomRef.current) / box.width, (height * zoomRef.current) / box.height));
  }, []);

  const apply = useCallback((value: number) => {
    zoomRef.current = value;
    setZoom(value);
  }, []);

  useLayoutEffect(() => {
    const next = fit();
    if (next < zoomRef.current) apply(next);
  }, [frame, fit, apply]);

  useLayoutEffect(() => {
    apply(1);
  }, [run, apply]);

  useEffect(() => {
    const o = outer.current;
    if (!o) return;
    const seen = { width: o.clientWidth, height: o.clientHeight };
    const observer = new ResizeObserver(() => {
      if (o.clientWidth === seen.width && o.clientHeight === seen.height) return;
      seen.width = o.clientWidth;
      seen.height = o.clientHeight;
      zoomRef.current = 1;
      if (inner.current) inner.current.style.zoom = "1";
      apply(fit());
    });
    observer.observe(o);
    return () => observer.disconnect();
  }, [fit, apply]);

  return (
    <div ref={outer} className="stage-bg relative min-h-[240px] flex-1 overflow-auto p-5">
      <div ref={inner} className="mx-auto w-fit" style={{ zoom }}>
        <Stage frame={frame} />
      </div>
    </div>
  );
}

export function Stage({ frame }: { frame: Frame }) {
  const side = [...(frame.stacks ?? []).map((s: Any, i: number) => <StackView key={`s${i}`} s={s} />), ...(frame.maps ?? []).map((m: Any, i: number) => <MapView key={`m${i}`} m={m} />)];
  return (
    <div className="flex w-fit min-w-max flex-col items-center gap-5">
      {(frame.arrays ?? []).map((a: Any, i: number) => <ArrayView key={i} a={a} />)}
      {many(frame.list, frame.lists).map((L, i) => <ListView key={i} L={L} />)}
      {many(frame.tree, frame.trees).length > 0 && <div className="flex flex-wrap gap-8">{many(frame.tree, frame.trees).map((t, i) => <TreeView key={i} t={t} />)}</div>}
      {(frame.nary ?? []).map((t: Any, i: number) => <NaryView key={i} t={t} />)}
      {(frame.graphs ?? []).map((g: Any, i: number) => <GraphView key={i} g={g} />)}
      {(frame.points ?? []).map((p: Any, i: number) => <PointsView key={i} p={p} />)}
      {(frame.intervals ?? []).map((g: Any, i: number) => <IntervalView key={i} g={g} />)}
      {(frame.grids?.length ?? 0) > 0 && <div className="flex flex-wrap gap-6">{frame.grids.map((g: Any, i: number) => <GridView key={i} g={g} />)}</div>}
      {side.length > 0 && <div className="flex flex-wrap items-start gap-4">{side}</div>}
      {(frame.texts ?? []).map((t: Any, i: number) => (
        <div key={i} className="font-mono text-sm"><span className="text-muted-foreground">{t.label}: </span><Value v={t.value} /></div>
      ))}
    </div>
  );
}
