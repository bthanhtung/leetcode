/*
 * Step-by-step algorithm player.
 *
 * A visualizer is registered with Viz.register(slug, { input, run, expect? }):
 *   input  - default input text, one "name = JSON" per line (the user can edit it)
 *   run    - (args, T) => result; calls T.step(frame) for every frame to show
 *   expect - result for the default input (checked by site/test-viz.mjs)
 *
 * A frame may contain any of: arrays, grids, list, tree, vars, msg. See render() for the shapes.
 */
(function (global) {
  const registry = {};

  function register(slug, def) {
    registry[slug] = def;
  }

  function parseInput(text) {
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
      } catch (e) {
        throw new Error(`Giá trị của ${name} không phải JSON hợp lệ: ${value}`);
      }
    }
    return args;
  }

  function trace(def, args) {
    const frames = [];
    const T = {
      step(frame) {
        frames.push(JSON.parse(JSON.stringify(frame)));
        if (frames.length > 5000) throw new Error("Quá nhiều bước (> 5000) — hãy thử dữ liệu nhỏ hơn");
      },
    };
    const result = def.run(args, T);
    return { frames, result };
  }

  /* ---------- tree helpers shared by visualizers ---------- */

  /** Level-order array with nulls -> { nodes: [{v, l, r}], root } (l/r are node ids or -1). */
  function buildTree(arr) {
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

  function treeToArray(tree) {
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

  /* ---------- rendering ---------- */

  const esc = (s) => String(s).replace(/[&<>"]/g, (c) => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;" })[c]);
  const fmt = (v) => (typeof v === "string" ? v : JSON.stringify(v));

  function renderArray(a) {
    const values = a.values || [];
    const n = values.length;
    const hl = a.hl || {};
    const ptr = a.ptr || {};
    const [rl, rr] = a.range || [-1, -2];
    const nums = values.filter((v) => typeof v === "number");
    const max = Math.max(1, ...nums.map(Math.abs), ...(a.water || []).map((w, i) => w + Math.max(0, values[i] || 0)));
    let cells = "";
    for (let i = 0; i < n; i++) {
      let cls = hl[i] || "";
      if (!cls && i >= rl && i <= rr) cls = "win";
      if (!cls && a.dimOutside && (i < rl || i > rr)) cls = "dim";
      const names = ptr[i] ? (Array.isArray(ptr[i]) ? ptr[i] : [ptr[i]]).join(" ") : "";
      const v = values[i];
      let box;
      if (a.bars) {
        const h = Math.round((Math.max(0, v) / max) * 120);
        const w = a.water ? Math.round((a.water[i] / max) * 120) : 0;
        box = `<div class="bar-wrap"><div class="water" style="height:${w}px"></div><div class="bar" style="height:${h}px"></div></div><div class="bar-val">${esc(fmt(v))}</div>`;
      } else {
        box = `<div class="box">${v === null || v === undefined ? "" : esc(fmt(v))}</div>`;
      }
      cells += `<div class="cell ${cls}">${a.noIndex ? "" : `<div class="idx">${a.indexLabels ? esc(a.indexLabels[i]) : i}</div>`}${box}<div class="ptr">${esc(names)}</div></div>`;
    }
    if (!n) cells = `<div class="empty">∅</div>`;
    return `<div class="row ${a.small ? "small" : ""} ${a.bars ? "bars" : ""}"><div class="row-label">${esc(a.label || "")}</div><div class="cells">${cells}</div></div>`;
  }

  function renderGrid(g) {
    const hl = g.hl || {};
    const cells = g.cells;
    let html = `<table class="grid">`;
    if (g.colHead) html += `<tr><th></th>${g.colHead.map((h) => `<th>${esc(h)}</th>`).join("")}</tr>`;
    cells.forEach((row, r) => {
      html += "<tr>";
      if (g.rowHead) html += `<th>${esc(g.rowHead[r])}</th>`;
      row.forEach((v, c) => {
        html += `<td class="${hl[r + "," + c] || ""}">${v === null || v === undefined ? "" : esc(fmt(v))}</td>`;
      });
      html += "</tr>";
    });
    return `<div class="grid-wrap"><div class="row-label">${esc(g.label || "")}</div>${html}</table></div>`;
  }

  function renderList(L) {
    const n = L.vals.length;
    const gap = 86;
    const width = Math.max(1, n) * gap + 20;
    const y = 50;
    const hl = L.hl || {};
    const ptr = L.ptr || {};
    let edges = "";
    let nodes = "";
    for (let i = 0; i < n; i++) {
      const x = 20 + i * gap;
      const j = L.next[i];
      if (j !== -1 && j !== undefined) {
        const x2 = 20 + j * gap;
        if (j === i + 1) edges += `<line x1="${x + 46}" y1="${y}" x2="${x2 - 4}" y2="${y}" class="edge" marker-end="url(#arrow)"/>`;
        else if (j === i - 1) edges += `<path d="M ${x} ${y - 10} Q ${(x + x2 + 46) / 2} ${y - 42} ${x2 + 46} ${y - 10}" class="edge" marker-end="url(#arrow)"/>`;
        else edges += `<path d="M ${x + 23} ${y + 20} C ${x + 23} ${y + 70}, ${x2 + 23} ${y + 70}, ${x2 + 23} ${y + 24}" class="edge" marker-end="url(#arrow)"/>`;
      }
      const names = ptr[i] ? (Array.isArray(ptr[i]) ? ptr[i] : [ptr[i]]).join(" ") : "";
      nodes += `<g class="lnode ${hl[i] || ""}"><rect x="${x}" y="${y - 18}" width="46" height="36" rx="8"/><text x="${x + 23}" y="${y + 5}">${esc(L.vals[i])}</text><text class="lptr" x="${x + 23}" y="${y + 40}">${esc(names)}</text></g>`;
    }
    return `<div class="list-wrap"><div class="row-label">${esc(L.label || "list")}</div><svg viewBox="0 0 ${width} 110" style="max-width:${width}px">
      <defs><marker id="arrow" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="7" markerHeight="7" orient="auto-start-reverse"><path d="M 0 0 L 10 5 L 0 10 z" class="arrowhead"/></marker></defs>
      ${edges}${nodes}</svg></div>`;
  }

  function renderTree(T) {
    if (T.root === -1) return `<div class="empty">cây rỗng</div>`;
    const pos = {};
    let order = 0;
    let depthMax = 0;
    (function walk(id, d) {
      if (id === -1) return;
      walk(T.nodes[id].l, d + 1);
      pos[id] = { x: order++, y: d };
      depthMax = Math.max(depthMax, d);
      walk(T.nodes[id].r, d + 1);
    })(T.root, 0);
    const gx = 52;
    const gy = 64;
    const width = Math.max(order, 1) * gx + 20;
    const height = (depthMax + 1) * gy + 30;
    const P = (id) => ({ x: 30 + pos[id].x * gx, y: 28 + pos[id].y * gy });
    const hl = T.hl || {};
    const label = T.label || {};
    let edges = "";
    let nodes = "";
    for (const id of Object.keys(pos)) {
      const n = T.nodes[id];
      const a = P(id);
      for (const c of [n.l, n.r]) {
        if (c !== -1) {
          const b = P(c);
          edges += `<line x1="${a.x}" y1="${a.y}" x2="${b.x}" y2="${b.y}" class="edge"/>`;
        }
      }
      nodes += `<g class="tnode ${hl[id] || ""}"><circle cx="${a.x}" cy="${a.y}" r="18"/><text x="${a.x}" y="${a.y + 5}">${esc(n.v)}</text>${label[id] !== undefined ? `<text class="tlabel" x="${a.x + 22}" y="${a.y - 14}">${esc(label[id])}</text>` : ""}</g>`;
    }
    return `<div class="tree-wrap"><svg viewBox="0 0 ${width} ${height}" style="max-width:${width}px">${edges}${nodes}</svg></div>`;
  }

  function render(frame) {
    let html = "";
    for (const a of frame.arrays || []) html += renderArray(a);
    for (const g of frame.grids || []) html += renderGrid(g);
    if (frame.list) html += renderList(frame.list);
    if (frame.tree) html += renderTree(frame.tree);
    return `<div class="stage-inner">${html}</div>`;
  }

  function renderVars(vars) {
    if (!vars) return "";
    return Object.entries(vars)
      .map(([k, v]) => `<span class="var"><b>${esc(k)}</b> ${esc(fmt(v))}</span>`)
      .join("");
  }

  /* ---------- player ---------- */

  function mount(container, slug) {
    const def = registry[slug];
    if (!def) return false;
    container.innerHTML = `
      <div class="viz">
        <div class="viz-input">
          <label>Dữ liệu vào <small>(mỗi dòng: tên = giá trị JSON)</small></label>
          <textarea spellcheck="false" rows="${def.input.split("\n").length}">${esc(def.input)}</textarea>
          <div class="viz-input-actions"><button class="btn run">Chạy lại</button><button class="btn ghost reset">Ví dụ gốc</button><span class="err"></span></div>
        </div>
        <div class="stage" aria-live="polite"></div>
        <div class="msg"></div>
        <div class="vars"></div>
        <div class="controls">
          <button class="btn icon first" title="Về đầu">⏮</button>
          <button class="btn icon prev" title="Lùi (←)">◀</button>
          <button class="btn play" title="Chạy / dừng (Space)">▶ Chạy</button>
          <button class="btn icon next" title="Tiến (→)">▶</button>
          <button class="btn icon last" title="Tới cuối">⏭</button>
          <input type="range" class="scrub" min="0" value="0">
          <span class="counter"></span>
          <select class="speed" title="Tốc độ">
            <option value="1600">0.5×</option><option value="900" selected>1×</option><option value="450">2×</option><option value="200">4×</option>
          </select>
        </div>
        <div class="result"></div>
      </div>`;
    const $ = (s) => container.querySelector(s);
    const stage = $(".stage");
    const msg = $(".msg");
    const vars = $(".vars");
    const scrub = $(".scrub");
    const counter = $(".counter");
    const playBtn = $(".play");
    const resultBox = $(".result");
    const textarea = $("textarea");
    let frames = [];
    let index = 0;
    let timer = null;

    function show(i) {
      index = Math.max(0, Math.min(frames.length - 1, i));
      const f = frames[index] || {};
      stage.innerHTML = render(f);
      msg.innerHTML = f.msg ? esc(f.msg).replace(/`([^`]+)`/g, "<code>$1</code>") : "";
      vars.innerHTML = renderVars(f.vars);
      scrub.value = index;
      counter.textContent = `${index + 1} / ${frames.length}`;
    }

    function stop() {
      clearInterval(timer);
      timer = null;
      playBtn.textContent = "▶ Chạy";
    }

    function play() {
      if (index >= frames.length - 1) show(0);
      playBtn.textContent = "⏸ Dừng";
      timer = setInterval(() => {
        if (index >= frames.length - 1) return stop();
        show(index + 1);
      }, Number($(".speed").value));
    }

    function load() {
      stop();
      $(".err").textContent = "";
      try {
        const { frames: fr, result } = trace(def, parseInput(textarea.value));
        frames = fr.length ? fr : [{ msg: "Không có bước nào." }];
        resultBox.innerHTML = `Kết quả: <code>${esc(fmt(result))}</code>`;
      } catch (e) {
        $(".err").textContent = e.message;
        return;
      }
      scrub.max = frames.length - 1;
      show(0);
    }

    $(".run").onclick = load;
    $(".reset").onclick = () => {
      textarea.value = def.input;
      load();
    };
    $(".first").onclick = () => (stop(), show(0));
    $(".last").onclick = () => (stop(), show(frames.length - 1));
    $(".prev").onclick = () => (stop(), show(index - 1));
    $(".next").onclick = () => (stop(), show(index + 1));
    playBtn.onclick = () => (timer ? stop() : play());
    scrub.oninput = () => (stop(), show(Number(scrub.value)));
    $(".speed").onchange = () => {
      if (timer) {
        stop();
        play();
      }
    };
    container.tabIndex = 0;
    container.addEventListener("keydown", (e) => {
      if (e.target === textarea) return;
      if (e.key === "ArrowRight") (stop(), show(index + 1), e.preventDefault());
      else if (e.key === "ArrowLeft") (stop(), show(index - 1), e.preventDefault());
      else if (e.key === " ") (timer ? stop() : play(), e.preventDefault());
    });
    load();
    return true;
  }

  global.Viz = { register, mount, has: (s) => !!registry[s], slugs: () => Object.keys(registry), registry, parseInput, trace, buildTree, treeToArray };
})(typeof window !== "undefined" ? window : globalThis);
