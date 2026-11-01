/* Graphs: BFS/DFS, topological order, union-find, shortest paths and searches over grids. */
import { A, DIRS, DSU, G, Heap, M, Q, chars, clone, matrix, ptrs, register, tones } from "./engine.js";

const nodesOf = (n, base = 0) => Array.from({ length: n }, (_, i) => ({ id: i + base }));
const cellTones = (cells, tone) => Object.fromEntries(cells.map((c) => [c, tone]));
const DSU_VIEW = (d, hl = {}) => A("parent", [...d.parent], { hl });

register("clone-graph", {
  input: "adjList = [[2, 4], [1, 3], [2, 4], [1, 3]]",
  expect: [[2, 4], [1, 3], [2, 4], [1, 3]],
  code: `copies = {}
dfs(node):
  if node in copies: return copies[node]
  copy = new Node(node.val); copies[node] = copy
  for nb in node.neighbors:
    copy.neighbors.add(dfs(nb))
  return copy`,
  run({ adjList }, T) {
    const n = adjList.length, nodes = nodesOf(n, 1), copies = new Map(), out = adjList.map(() => []);
    const edges = adjList.flatMap((ns, i) => ns.filter((j) => j > i + 1).map((j) => ({ a: i + 1, b: j })));
    const view = (line, cur, msg) => T.step({ line, graphs: [{ label: "đồ thị gốc", nodes, edges, hl: { ...tones([...copies.keys()], "done"), [cur]: "cur" } }, { label: "bản sao", nodes: [...copies.keys()].map((id) => ({ id })), edges: out.flatMap((ns, i) => ns.filter((j) => j > i + 1 || !out[j - 1].includes(i + 1)).map((j) => ({ a: i + 1, b: j }))), hl: { [cur]: "ok" } }], maps: [M("copies", [...copies.keys()].map((k) => [k, `copy ${k}`]))], msg });
    const dfs = (id) => {
      if (copies.has(id)) { view(3, id, `${id} đã có bản sao, dùng lại.`); return; }
      copies.set(id, true);
      view(4, id, `Tạo bản sao của ${id}, ghi vào map trước khi đi tiếp để tránh lặp.`);
      for (const nb of adjList[id - 1]) {
        dfs(nb);
        out[id - 1].push(nb);
        view(6, id, `Nối bản sao ${id} với bản sao ${nb}.`);
      }
    };
    dfs(1);
    return out;
  },
});

function topo(slug, input, expect, code, finish) {
  register(slug, {
    input, expect, code,
    run({ numCourses, prerequisites }, T) {
      const n = numCourses, indeg = Array(n).fill(0), adj = Array.from({ length: n }, () => []);
      for (const [a, b] of prerequisites) { adj[b].push(a); indeg[a]++; }
      const q = [], order = [], hl = {};
      indeg.forEach((d, i) => { if (!d) q.push(i); });
      const graph = (cur) => [{ label: "môn học (mũi tên: học trước → sau)", directed: true, layout: "layered", nodes: nodesOf(n), edges: prerequisites.map(([a, b]) => ({ a: b, b: a, tone: order.includes(b) ? "dim" : undefined })), hl: { ...hl, ...(cur !== undefined ? { [cur]: "cur" } : {}), ...tones(q, "mark") } }];
      T.step({ line: 2, graphs: graph(), arrays: [A("bậc vào", [...indeg])], stacks: [Q("queue", [...q])], msg: "Các môn không có điều kiện tiên quyết vào hàng đợi." });
      while (q.length) {
        const u = q.shift();
        order.push(u);
        hl[u] = "ok";
        T.step({ line: 4, graphs: graph(u), arrays: [A("bậc vào", [...indeg]), A("thứ tự", [...order], { small: true, noIndex: true })], stacks: [Q("queue", [...q])], msg: `Học môn ${u}.` });
        for (const v of adj[u]) {
          if (--indeg[v] === 0) q.push(v);
          T.step({ line: 6, graphs: graph(u), arrays: [A("bậc vào", [...indeg], { hl: { [v]: indeg[v] ? "mark" : "ok" } }), A("thứ tự", [...order], { small: true, noIndex: true })], stacks: [Q("queue", [...q])], msg: indeg[v] ? `Môn ${v} còn thiếu ${indeg[v]} môn.` : `Môn ${v} đủ điều kiện.` });
        }
      }
      const ok = order.length === n;
      T.step({ line: 7, key: true, graphs: graph(), arrays: [A("thứ tự", order, { small: true, noIndex: true })], msg: ok ? "Học được hết." : "Còn môn kẹt trong chu trình." });
      return finish(ok, order);
    },
  });
}

const KAHN = (last) => `build graph and indegree
queue = courses with indegree 0
while queue not empty:
  u = queue.pop(); order.add(u)
  for v in adj[u]:
    if --indegree[v] == 0: queue.add(v)
${last}`;

topo("course-schedule", "numCourses = 4\nprerequisites = [[1, 0], [2, 0], [3, 1], [3, 2]]", true, KAHN("return len(order) == numCourses"), (ok) => ok);
topo("course-schedule-ii", "numCourses = 4\nprerequisites = [[1, 0], [2, 0], [3, 1], [3, 2]]", [0, 1, 2, 3], KAHN("return len(order) == n ? order : []"), (ok, order) => (ok ? order : []));

register("parallel-courses-iii", {
  input: "n = 5\nrelations = [[1, 5], [2, 5], [3, 5], [3, 4], [4, 5]]\ntime = [1, 2, 3, 4, 5]",
  expect: 12,
  code: `finish[u] = time[u] for sources
process in topological order:
  for v in adj[u]:
    finish[v] = max(finish[v], finish[u] + time[v])
return max(finish)`,
  run({ n, relations, time }, T) {
    const adj = Array.from({ length: n + 1 }, () => []), indeg = Array(n + 1).fill(0), finish = [0, ...time];
    for (const [a, b] of relations) { adj[a].push(b); indeg[b]++; }
    const q = [], hl = {}, tag = {};
    for (let i = 1; i <= n; i++) if (!indeg[i]) q.push(i);
    const view = (line, msg, cur, key) => { for (let i = 1; i <= n; i++) tag[i] = finish[i]; T.step({ line, key, graphs: [{ label: "tháng hoàn thành sớm nhất", directed: true, layout: "layered", nodes: nodesOf(n, 1), edges: relations.map(([a, b]) => ({ a, b })), hl: { ...hl, ...(cur ? { [cur]: "cur" } : {}) }, tag: { ...tag } }], stacks: [Q("queue", [...q])], msg }); };
    view(1, "Môn không phụ thuộc xong sau đúng thời gian của nó.");
    while (q.length) {
      const u = q.shift();
      hl[u] = "ok";
      for (const v of adj[u]) {
        finish[v] = Math.max(finish[v], finish[u] + time[v - 1]);
        view(4, `Môn ${v} chỉ bắt đầu sau khi ${u} xong → sớm nhất ${finish[v]}.`, v);
        if (--indeg[v] === 0) q.push(v);
      }
    }
    const best = Math.max(...finish);
    view(5, `Môn xong muộn nhất: tháng ${best}.`, 0, true);
    return best;
  },
});

function wordBfs(beginWord, endWord, wordList) {
  const words = [beginWord, ...wordList.filter((w) => w !== beginWord)];
  const near = (a, b) => [...a].filter((c, i) => c !== b[i]).length === 1;
  const edges = [];
  words.forEach((a, i) => words.forEach((b, j) => { if (j > i && near(a, b)) edges.push({ a, b }); }));
  return { words, near, edges };
}

register("word-ladder", {
  input: 'beginWord = "hit"\nendWord = "cog"\nwordList = ["hot", "dot", "dog", "lot", "log", "cog"]',
  expect: 5,
  code: `queue = [(beginWord, 1)], seen = {beginWord}
while queue not empty:
  (word, d) = queue.pop()
  if word == endWord: return d
  for each word differing by one letter:
    if not seen: seen.add; queue.add((next, d + 1))
return 0`,
  run({ beginWord, endWord, wordList }, T) {
    const { words, near, edges } = wordBfs(beginWord, endWord, wordList);
    const dist = { [beginWord]: 1 }, q = [beginWord], hl = {};
    const view = (line, msg, cur, key) => T.step({ line, key, graphs: [{ label: "các từ (nối nếu khác 1 chữ)", nodes: words.map((id) => ({ id })), edges, hl: { ...hl, ...tones(q, "mark"), ...(cur ? { [cur]: "cur" } : {}) }, tag: { ...dist } }], stacks: [Q("queue", [...q])], msg });
    view(1, "BFS từ từ đầu: lần đầu chạm từ nào cũng là đường ngắn nhất.");
    while (q.length) {
      const w = q.shift();
      hl[w] = "done";
      if (w === endWord) { hl[w] = "ok"; view(4, `Tới "${endWord}" sau ${dist[w]} từ.`, w, true); return dist[w]; }
      view(3, `Lấy "${w}" ra.`, w);
      for (const x of words) if (dist[x] === undefined && near(w, x)) {
        dist[x] = dist[w] + 1;
        q.push(x);
        view(6, `"${x}" cách "${w}" một chữ.`, w);
      }
    }
    return 0;
  },
});

register("word-ladder-ii", {
  input: 'beginWord = "hit"\nendWord = "cog"\nwordList = ["hot", "dot", "dog", "lot", "log", "cog"]',
  expect: [["hit", "hot", "dot", "dog", "cog"], ["hit", "hot", "lot", "log", "cog"]],
  code: `BFS by levels from beginWord:
  dist[word] = level, parents[word] = words one level up
if endWord unreachable: return []
dfs(word, path):
  if word == beginWord: result.add(reverse(path))
  for p in parents[word]: dfs(p, path + [p])`,
  run({ beginWord, endWord, wordList }, T) {
    const { words, near, edges } = wordBfs(beginWord, endWord, wordList);
    const dist = { [beginWord]: 0 }, parents = {};
    let level = [beginWord];
    const graph = (hl) => [{ label: "các từ", nodes: words.map((id) => ({ id })), edges, hl, tag: { ...dist } }];
    while (level.length && dist[endWord] === undefined) {
      const next = new Set();
      for (const w of level) for (const x of words) {
        if (near(w, x) && (dist[x] === undefined || dist[x] === dist[w] + 1)) {
          dist[x] = dist[w] + 1;
          (parents[x] ??= []).push(w);
          next.add(x);
        }
      }
      T.step({ line: 2, graphs: graph({ ...tones(level, "done"), ...tones([...next], "cur") }), maps: [M("cha", Object.entries(parents))], msg: `Mức tiếp theo: ${[...next].join(", ")}.` });
      level = [...next];
    }
    const res = [];
    if (dist[endWord] === undefined) return res;
    const dfs = (w, path) => {
      if (w === beginWord) {
        res.push([...path].reverse());
        T.step({ line: 5, key: true, graphs: graph(tones(path, "ok")), vars: { "số đường": res.length }, msg: `Một đường ngắn nhất: ${[...path].reverse().join(" → ")}.` });
        return;
      }
      T.step({ line: 6, graphs: graph(tones(path, "cur")), msg: `Đi ngược từ "${w}" theo cha.` });
      for (const p of parents[w]) dfs(p, [...path, p]);
    };
    dfs(endWord, [endWord]);
    return res.sort((x, y) => (x.join() < y.join() ? -1 : 1));
  },
});

register("surrounded-regions", {
  input: 'board = [["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]',
  expect: [["X", "X", "X", "X"], ["X", "X", "X", "X"], ["X", "X", "X", "X"], ["X", "O", "X", "X"]],
  code: `for each 'O' on the border: dfs mark as safe
dfs(r, c): mark '#', recurse on 'O' neighbours
for each cell:
  'O' -> 'X'    // bị vây
  '#' -> 'O'    // nối ra biên`,
  run({ board }, T) {
    const b = clone(board), m = b.length, n = b[0].length;
    const view = (line, msg, cur, key) => T.step({ line, key, grids: [G("board", clone(b), { hl: { ...cellTones(b.flatMap((row, r) => row.flatMap((v, c) => (v === "#" ? [r + "," + c] : []))), "ok"), ...(cur ? { [cur]: "cur" } : {}) } })], msg });
    const dfs = (r, c) => {
      if (r < 0 || c < 0 || r >= m || c >= n || b[r][c] !== "O") return;
      b[r][c] = "#";
      view(2, `(${r}, ${c}) nối được ra biên → an toàn.`, r + "," + c);
      for (const [x, y] of DIRS) dfs(r + x, c + y);
    };
    for (let r = 0; r < m; r++) for (let c = 0; c < n; c++) if ((!r || !c || r === m - 1 || c === n - 1) && b[r][c] === "O") dfs(r, c);
    for (let r = 0; r < m; r++) for (let c = 0; c < n; c++) {
      if (b[r][c] === "O") { b[r][c] = "X"; view(4, `(${r}, ${c}) bị vây → lật thành X.`, r + "," + c); } else if (b[r][c] === "#") b[r][c] = "O";
    }
    view(5, "Khôi phục các ô an toàn.", null, true);
    return b;
  },
});

register("longest-increasing-path-in-a-matrix", {
  input: "matrix = [[9, 9, 4], [6, 6, 8], [2, 1, 1]]",
  expect: 4,
  code: `memo = zeros
dfs(r, c):
  if memo[r][c]: return memo[r][c]
  best = 1
  for each neighbour with larger value:
    best = max(best, 1 + dfs(neighbour))
  return memo[r][c] = best`,
  run({ matrix: a }, T) {
    const m = a.length, n = a[0].length, memo = matrix(m, n, null);
    const dfs = (r, c) => {
      if (memo[r][c]) return memo[r][c];
      let best = 1;
      for (const [x, y] of DIRS) { const i = r + x, j = c + y; if (a[i]?.[j] > a[r][c]) best = Math.max(best, 1 + dfs(i, j)); }
      memo[r][c] = best;
      T.step({ line: 7, grids: [G("matrix", a, { hl: { [r + "," + c]: "cur" } }), G("memo", clone(memo), { hl: { [r + "," + c]: "ok" } })], msg: `Đường tăng dài nhất từ ${a[r][c]}: ${best}.` });
      return best;
    };
    let ans = 0;
    for (let r = 0; r < m; r++) for (let c = 0; c < n; c++) ans = Math.max(ans, dfs(r, c));
    return ans;
  },
});

register("reconstruct-itinerary", {
  input: 'tickets = [["JFK", "SFO"], ["JFK", "ATL"], ["SFO", "ATL"], ["ATL", "JFK"], ["ATL", "SFO"]]',
  expect: ["JFK", "ATL", "JFK", "SFO", "ATL", "SFO"],
  code: `adj[from] = sorted destinations
dfs(u):                    // Hierholzer
  while adj[u] not empty:
    dfs(adj[u].removeFirst())
  route.addFirst(u)
dfs("JFK"); return route`,
  run({ tickets }, T) {
    const adj = {}, names = [...new Set(tickets.flat())];
    for (const [a, b] of [...tickets].sort()) (adj[a] ??= []).push(b);
    const route = [], used = tickets.map(() => false);
    const edges = () => tickets.map(([a, b], i) => ({ a, b, tone: used[i] ? "ok" : undefined }));
    const dfs = (u) => {
      while (adj[u]?.length) {
        const v = adj[u].shift();
        used[tickets.findIndex(([a, b], i) => a === u && b === v && !used[i])] = true;
        T.step({ line: 4, graphs: [{ label: "vé đã dùng", directed: true, nodes: names.map((id) => ({ id })), edges: edges(), hl: { [u]: "mark", [v]: "cur" } }], arrays: [A("hành trình (điền từ cuối)", [...route], { small: true, noIndex: true })], msg: `Dùng vé ${u} → ${v} (nhỏ nhất theo từ điển).` });
        dfs(v);
      }
      route.unshift(u);
      T.step({ line: 5, key: true, graphs: [{ label: "vé đã dùng", directed: true, nodes: names.map((id) => ({ id })), edges: edges(), hl: { [u]: "ok" } }], arrays: [A("hành trình (điền từ cuối)", [...route], { small: true, noIndex: true, hl: { 0: "ok" } })], msg: `${u} hết vé đi tiếp → ghi vào đầu hành trình.` });
    };
    dfs("JFK");
    return route;
  },
});

register("trapping-rain-water-ii", {
  input: "heightMap = [[1, 4, 3, 1, 3, 2], [3, 2, 1, 3, 2, 4], [2, 3, 3, 2, 3, 1]]",
  expect: 4,
  code: `heap = border cells, seen = border
water = 0, level = 0
while heap not empty:
  (h, r, c) = heap.pop()
  level = max(level, h)
  for unseen neighbour:
    water += max(0, level - height)
    seen; heap.push(neighbour)
return water`,
  run({ heightMap: a }, T) {
    const m = a.length, n = a[0].length, seen = matrix(m, n, false), heap = new Heap((x, y) => x[0] < y[0]), filled = clone(a);
    for (let r = 0; r < m; r++) for (let c = 0; c < n; c++) if (!r || !c || r === m - 1 || c === n - 1) { seen[r][c] = true; heap.push([a[r][c], r, c]); }
    let water = 0, level = 0;
    const hl = {};
    const view = (line, msg, cur, key) => T.step({ line, key, grids: [G("độ cao (mực nước tính vào)", clone(filled), { hl: { ...hl, ...(cur ? { [cur]: "cur" } : {}) } })], vars: { "mực nước": level, "tổng nước": water }, msg });
    view(1, "Biên là tường ngoài, đưa hết vào min-heap.");
    while (heap.size) {
      const [h, r, c] = heap.pop();
      level = Math.max(level, h);
      hl[r + "," + c] = "done";
      view(5, `Ô thấp nhất của biên: cao ${h}. Mực nước ${level}.`, r + "," + c);
      for (const [x, y] of DIRS) {
        const i = r + x, j = c + y;
        if (i < 0 || j < 0 || i >= m || j >= n || seen[i][j]) continue;
        seen[i][j] = true;
        const w = Math.max(0, level - a[i][j]);
        if (w) { water += w; filled[i][j] = level; hl[i + "," + j] = "c1"; view(7, `Ô (${i}, ${j}) giữ được ${w} đơn vị nước.`, i + "," + j, true); }
        heap.push([a[i][j], i, j]);
      }
    }
    return water;
  },
});

register("pacific-atlantic-water-flow", {
  input: "heights = [[1, 2, 2, 3, 5], [3, 2, 3, 4, 4], [2, 4, 5, 3, 1], [6, 7, 1, 4, 5], [5, 1, 1, 2, 4]]",
  expect: [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]],
  code: `pacific = cells reachable going uphill from top/left
atlantic = cells reachable going uphill from bottom/right
return cells in both`,
  run({ heights: a }, T) {
    const m = a.length, n = a[0].length;
    const flood = (starts, label, tone, line) => {
      const seen = new Set(), st = [...starts];
      starts.forEach((s) => seen.add(s.join()));
      while (st.length) {
        const [r, c] = st.pop();
        for (const [x, y] of DIRS) {
          const i = r + x, j = c + y;
          if (a[i]?.[j] === undefined || seen.has(i + "," + j) || a[i][j] < a[r][c]) continue;
          seen.add(i + "," + j);
          st.push([i, j]);
        }
      }
      T.step({ line, key: true, grids: [G("heights", a, { hl: cellTones([...seen], tone) })], msg: `${label}: leo ngược từ biển, được ${seen.size} ô.` });
      return seen;
    };
    const top = [], bot = [];
    for (let i = 0; i < m; i++) { top.push([i, 0]); bot.push([i, n - 1]); }
    for (let j = 0; j < n; j++) { top.push([0, j]); bot.push([m - 1, j]); }
    const p = flood(top, "Thái Bình Dương", "c1", 1), q = flood(bot, "Đại Tây Dương", "c3", 2);
    const both = [...p].filter((x) => q.has(x));
    T.step({ line: 3, key: true, grids: [G("heights", a, { hl: { ...cellTones([...p], "c1"), ...cellTones([...q], "c3"), ...cellTones(both, "ok") } })], msg: `${both.length} ô chảy được ra cả hai biển.` });
    return both.map((x) => x.split(",").map(Number)).sort((x, y) => x[0] - y[0] || x[1] - y[1]);
  },
});

function redundant(slug, input, expect, code, finder) {
  register(slug, { input, expect, code, run: finder });
}

redundant("redundant-connection", "edges = [[1, 2], [1, 3], [2, 3]]", [2, 3], `dsu = new DSU(n)
for (a, b) in edges:
  if find(a) == find(b): return [a, b]
  union(a, b)`, ({ edges }, T) => {
  const n = edges.length, d = new DSU(n + 1), done = [];
  for (const [a, b] of edges) {
    const same = d.find(a) === d.find(b);
    T.step({ line: 3, key: same, graphs: [{ label: "đồ thị", nodes: nodesOf(n, 1), edges: [...done.map(([x, y]) => ({ a: x, b: y })), { a, b, tone: same ? "bad" : "cur" }], hl: { [a]: "cur", [b]: "cur" } }], arrays: [DSU_VIEW(d)], msg: same ? `${a} và ${b} đã thông nhau → cạnh này tạo chu trình.` : `Nối ${a} và ${b}.` });
    if (same) return [a, b];
    d.union(a, b);
    done.push([a, b]);
  }
  return [];
});

redundant("redundant-connection-ii", "edges = [[1, 2], [1, 3], [2, 3]]", [2, 3], `find node with two parents -> candidates c1, c2
union all edges except c2:
  if a cycle forms: return c1 or the cycling edge
return c2`, ({ edges }, T) => {
  const n = edges.length, parent = Array(n + 1).fill(0);
  let c1 = null, c2 = null;
  for (const [a, b] of edges) {
    if (parent[b]) { c1 = [parent[b], b]; c2 = [a, b]; } else parent[b] = a;
  }
  const graph = (hl) => [{ label: "đồ thị có hướng", directed: true, nodes: nodesOf(n, 1), edges: edges.map(([a, b]) => ({ a, b, tone: c2 && a === c2[0] && b === c2[1] ? "mark" : c1 && a === c1[0] && b === c1[1] ? "cur" : undefined })), hl }];
  T.step({ line: 1, graphs: graph({}), vars: { c1: c1 ?? "-", c2: c2 ?? "-" }, msg: c1 ? `Nút ${c2[1]} có hai cha: hai cạnh ứng viên.` : "Không nút nào có hai cha." });
  const d = new DSU(n + 1);
  for (const [a, b] of edges) {
    if (c2 && a === c2[0] && b === c2[1]) continue;
    const merged = d.union(a, b);
    T.step({ line: 2, key: !merged, graphs: graph({ [a]: "cur", [b]: "cur" }), arrays: [DSU_VIEW(d)], msg: merged ? `Gộp ${a} với ${b}.` : `Cạnh ${a}→${b} tạo chu trình.` });
    if (!merged) return c1 ?? [a, b];
  }
  T.step({ line: 3, key: true, graphs: graph({}), msg: "Bỏ c2 thì không còn chu trình → c2 là cạnh thừa." });
  return c2;
});

register("max-area-of-island", {
  input: "grid = [[0, 0, 1, 0, 0], [1, 1, 1, 0, 0], [0, 1, 0, 0, 1], [0, 0, 0, 1, 1]]",
  expect: 5,
  code: `best = 0
for each land cell:
  area = dfs(cell)            // đánh dấu đã thăm
  best = max(best, area)
return best`,
  run({ grid }, T) {
    const m = grid.length, n = grid[0].length, id = matrix(m, n, 0), colors = ["c1", "c2", "c3", "c4", "c5", "c6"];
    let best = 0, k = 0;
    for (let r = 0; r < m; r++) for (let c = 0; c < n; c++) {
      if (!grid[r][c] || id[r][c]) continue;
      k++;
      let area = 0;
      const st = [[r, c]];
      id[r][c] = k;
      const hl = () => Object.fromEntries(id.flatMap((row, i) => row.flatMap((v, j) => (v ? [[i + "," + j, colors[(v - 1) % 6]]] : []))));
      while (st.length) {
        const [i, j] = st.pop();
        area++;
        T.step({ line: 3, grids: [G("grid", grid, { hl: { ...hl(), [i + "," + j]: "cur" } })], vars: { area, best }, msg: `Đảo thứ ${k}: diện tích ${area}.` });
        for (const [x, y] of DIRS) if (grid[i + x]?.[j + y] && !id[i + x][j + y]) { id[i + x][j + y] = k; st.push([i + x, j + y]); }
      }
      if (area > best) { best = area; T.step({ line: 4, key: true, grids: [G("grid", grid, { hl: hl() })], vars: { best }, msg: `Đảo lớn nhất mới: ${best}.` }); }
    }
    return best;
  },
});

register("accounts-merge", {
  input: 'accounts = [["John", "a@x", "b@x"], ["John", "c@x"], ["John", "a@x", "d@x"], ["Mary", "m@x"]]',
  expect: [["John", "a@x", "b@x", "d@x"], ["John", "c@x"], ["Mary", "m@x"]],
  code: `dsu over accounts, owner = {}
for i, account:
  for email in account:
    if email in owner: union(i, owner[email])
    else: owner[email] = i
group emails by find(owner[email])
return sorted groups with names`,
  run({ accounts }, T) {
    const d = new DSU(accounts.length), owner = new Map();
    accounts.forEach((acc, i) => acc.slice(1).forEach((e) => {
      const seen = owner.has(e);
      if (seen) d.union(i, owner.get(e)); else owner.set(e, i);
      T.step({ line: seen ? 4 : 5, key: seen, arrays: [A("tài khoản", accounts.map((a, k) => `${k}: ${a[0]}`), { small: true, noIndex: true, hl: { [i]: "cur" } }), DSU_VIEW(d)], maps: [M("email → tài khoản", owner, { hl: { [e]: seen ? "ok" : "cur" } })], msg: seen ? `${e} đã thuộc tài khoản ${owner.get(e)} → gộp với ${i}.` : `${e} thuộc tài khoản ${i}.` });
    }));
    const groups = new Map();
    for (const [e, i] of owner) { const r = d.find(i); groups.set(r, [...(groups.get(r) ?? []), e]); }
    const out = [...groups].map(([r, es]) => [accounts[r][0], ...es.sort()]).sort((x, y) => (x.join() < y.join() ? -1 : 1));
    T.step({ line: 7, key: true, maps: out.map((g) => M(g[0], g.slice(1).map((e) => [e]))), arrays: [DSU_VIEW(d)], msg: `Còn ${out.length} tài khoản sau khi gộp.` });
    return out;
  },
});

register("network-delay-time", {
  input: "times = [[2, 1, 1], [2, 3, 1], [3, 4, 1]]\nn = 4\nk = 2",
  expect: 2,
  code: `dist = {k: 0}, heap = [(0, k)]
while heap not empty:
  (d, u) = heap.pop()
  if d > dist[u]: continue
  for (v, w) in adj[u]:
    if d + w < dist[v]: dist[v] = d + w; heap.push
return max(dist) or -1 if unreachable`,
  run({ times, n, k }, T) {
    const adj = {}, dist = { [k]: 0 }, heap = new Heap((a, b) => a[0] < b[0]), hl = {};
    for (const [u, v, w] of times) (adj[u] ??= []).push([v, w]);
    heap.push([0, k]);
    const view = (line, msg, cur, key) => T.step({ line, key, graphs: [{ label: "mạng (số trên nút: thời gian tới)", directed: true, nodes: nodesOf(n, 1), edges: times.map(([a, b, w]) => ({ a, b, w })), hl: { ...hl, ...(cur ? { [cur]: "cur" } : {}) }, tag: { ...dist } }], stacks: [Q("heap", heap.a.map((x) => `${x[0]}:${x[1]}`))], msg });
    view(1, `Bắt đầu từ nút ${k}.`);
    while (heap.size) {
      const [d, u] = heap.pop();
      if (d > dist[u]) continue;
      hl[u] = "ok";
      view(3, `Chốt nút ${u} với thời gian ${d}.`, u);
      for (const [v, w] of adj[u] ?? []) if (d + w < (dist[v] ?? Infinity)) { dist[v] = d + w; heap.push([d + w, v]); view(6, `Tới ${v} qua ${u} mất ${d + w}.`, v); }
    }
    const all = Object.values(dist), r = all.length === n ? Math.max(...all) : -1;
    view(7, r < 0 ? "Có nút không nhận được tín hiệu." : `Nút xa nhất nhận sau ${r}.`, null, true);
    return r;
  },
});

register("cracking-the-safe", {
  input: "n = 2\nk = 2",
  expect: "01100",
  code: `seen = {}, out = ""
dfs(node):                    // node: n-1 chữ số cuối
  for x in 0..k-1:
    edge = node + x
    if edge not in seen:
      seen.add(edge); dfs(edge[1:]); out += x
return out + "0" * (n - 1)`,
  run({ n, k }, T) {
    const seen = new Set(), out = [];
    const dfs = (node) => {
      for (let x = 0; x < k; x++) {
        const e = node + x;
        if (seen.has(e)) continue;
        seen.add(e);
        T.step({ line: 6, maps: [M("mã đã phủ", seen, { hl: { [e]: "cur" } })], arrays: [A("đuôi kết quả", [...out], { noIndex: true })], msg: `Đi cạnh ${e}: phủ mã này.` });
        dfs(e.slice(1));
        out.push(x);
        T.step({ line: 6, arrays: [A("đuôi kết quả", [...out], { noIndex: true, hl: { [out.length - 1]: "ok" } })], maps: [M("mã đã phủ", seen)], msg: `Quay lui, ghi ${x} (chu trình Euler dựng ngược).` });
      }
    };
    const start = "0".repeat(n - 1);
    dfs(start);
    const res = out.join("") + start;
    T.step({ line: 7, key: true, texts: [{ label: "chuỗi mở khoá", value: res }], maps: [M("mã đã phủ", seen)], msg: `Chuỗi dài ${res.length} chứa mọi mã.` });
    return res;
  },
});

register("couples-holding-hands", {
  input: "row = [0, 2, 1, 3]",
  expect: 1,
  code: `dsu over couples
for i = 0; i < n; i += 2:
  union(row[i] / 2, row[i+1] / 2)
return couples - number of components`,
  run({ row }, T) {
    const c = row.length / 2, d = new DSU(c);
    for (let i = 0; i < row.length; i += 2) {
      const a = row[i] >> 1, b = row[i + 1] >> 1, merged = d.union(a, b);
      T.step({ line: 3, key: merged, arrays: [A("row", row, { hl: { [i]: merged ? "bad" : "ok", [i + 1]: merged ? "bad" : "ok" } }), DSU_VIEW(d)], vars: { "số thành phần": d.count }, msg: merged ? `Cặp ${a} và ${b} ngồi lẫn → nằm cùng một vòng hoán đổi.` : `Ghế ${i}, ${i + 1} đã là một cặp.` });
    }
    const r = c - d.count;
    T.step({ line: 4, key: true, arrays: [A("row", row), DSU_VIEW(d)], vars: { "hoán đổi": r }, msg: `Mỗi vòng k cặp cần k − 1 lần đổi: tổng ${r}.` });
    return r;
  },
});

register("sliding-puzzle", {
  input: "board = [[1, 2, 3], [4, 0, 5]]",
  expect: 1,
  code: `BFS over board states (as strings):
  start = board, target = "123450"
  next states: swap 0 with a neighbour
  first time target is popped: return moves
return -1`,
  run({ board }, T) {
    const nb = [[1, 3], [0, 2, 4], [1, 5], [0, 4], [1, 3, 5], [2, 4]];
    const start = board.flat().join(""), target = "123450", seen = new Set([start]);
    let level = [start], d = 0;
    const show = (s) => [chars(s.slice(0, 3)).map((c) => (c === "0" ? null : c)), chars(s.slice(3)).map((c) => (c === "0" ? null : c))];
    while (level.length) {
      for (const s of level) {
        const hit = s === target;
        T.step({ line: hit ? 4 : 1, key: hit, grids: [G("trạng thái", show(s), { hl: hit ? Object.fromEntries([0, 1].flatMap((r) => [0, 1, 2].map((c) => [r + "," + c, "ok"]))) : { [Math.floor(s.indexOf("0") / 3) + "," + (s.indexOf("0") % 3)]: "mark" } })], vars: { "số bước": d, "đã thăm": seen.size }, msg: hit ? `Về đúng vị trí sau ${d} bước.` : `Xét trạng thái ${s}.` });
        if (hit) return d;
      }
      const next = [];
      for (const s of level) {
        const z = s.indexOf("0");
        for (const j of nb[z]) {
          const a = [...s];
          [a[z], a[j]] = [a[j], a[z]];
          const t = a.join("");
          if (!seen.has(t)) { seen.add(t); next.push(t); }
        }
      }
      level = next;
      d++;
    }
    return -1;
  },
});

register("swim-in-rising-water", {
  input: "grid = [[0, 2], [1, 3]]",
  expect: 3,
  code: `heap = [(grid[0][0], 0, 0)], seen, t = 0
while heap not empty:
  (h, r, c) = heap.pop()
  t = max(t, h)
  if (r, c) is the goal: return t
  push unseen neighbours`,
  run({ grid }, T) {
    const n = grid.length, heap = new Heap((a, b) => a[0] < b[0]), seen = new Set(["0,0"]);
    heap.push([grid[0][0], 0, 0]);
    let t = 0;
    const hl = {};
    while (heap.size) {
      const [h, r, c] = heap.pop();
      t = Math.max(t, h);
      const done = r === n - 1 && c === n - 1;
      hl[r + "," + c] = "done";
      T.step({ line: done ? 5 : 4, key: done, grids: [G("grid", grid, { hl: { ...hl, [r + "," + c]: done ? "ok" : "cur" } })], vars: { "mực nước": t }, msg: done ? `Tới đích ở thời điểm ${t}.` : `Đi vào ô thấp nhất kề vùng đã tới: ${h}.` });
      if (done) return t;
      for (const [x, y] of DIRS) if (grid[r + x]?.[c + y] !== undefined && !seen.has(r + x + "," + (c + y))) { seen.add(r + x + "," + (c + y)); heap.push([grid[r + x][c + y], r + x, c + y]); }
    }
    return t;
  },
});

register("bricks-falling-when-hit", {
  input: "grid = [[1, 0, 0, 0], [1, 1, 1, 0]]\nhits = [[1, 0]]",
  expect: [2],
  code: `remove all hits from grid
union remaining bricks; roof = virtual node
for hit in reversed(hits):
  if there was a brick:
    before = size(roof)
    add brick back, union neighbours (and roof if row 0)
    result = max(0, size(roof) - before - 1)
return result`,
  run({ grid, hits }, T) {
    const m = grid.length, n = grid[0].length, a = clone(grid), id = (r, c) => r * n + c, roof = m * n, d = new DSU(roof + 1);
    hits.forEach(([r, c]) => (a[r][c] = 0));
    const link = (r, c) => {
      if (!r) d.union(id(r, c), roof);
      for (const [x, y] of DIRS) if (a[r + x]?.[c + y]) d.union(id(r, c), id(r + x, c + y));
    };
    T.step({ line: 1, grids: [G("sau khi đập hết", clone(a), { hl: Object.fromEntries(hits.map(([r, c]) => [r + "," + c, "bad"])) })], msg: "Đi ngược thời gian: đập hết trước, rồi ghép lại từng viên." });
    a.forEach((row, r) => row.forEach((v, c) => { if (v) link(r, c); }));
    const out = Array(hits.length).fill(0);
    for (let i = hits.length - 1; i >= 0; i--) {
      const [r, c] = hits[i];
      if (!grid[r][c]) continue;
      const before = d.size[d.find(roof)];
      a[r][c] = 1;
      link(r, c);
      out[i] = Math.max(0, d.size[d.find(roof)] - before - 1);
      T.step({ line: 7, key: true, grids: [G("ghép lại", clone(a), { hl: { [r + "," + c]: "ok" } })], vars: { "kết quả": out }, msg: `Lắp lại viên này kéo thêm ${out[i]} viên dính trần: đó là số viên rơi khi đập.` });
    }
    return out;
  },
});

register("bus-routes", {
  input: "routes = [[1, 2, 7], [3, 6, 7]]\nsource = 1\ntarget = 6",
  expect: 2,
  code: `stop -> routes that pass it
queue = routes through source, buses = 1
while queue not empty:
  for route in current level:
    if target in route: return buses
    add unused routes sharing a stop
  buses++
return -1`,
  run({ routes, source, target }, T) {
    if (source === target) { T.step({ line: 1, texts: [{ label: "kết quả", value: 0 }], msg: "Đã ở đích." }); return 0; }
    const byStop = new Map();
    routes.forEach((r, i) => r.forEach((s) => byStop.set(s, [...(byStop.get(s) ?? []), i])));
    const cells = routes.map((r) => [...r, ...Array(Math.max(...routes.map((x) => x.length)) - r.length).fill(null)]);
    let level = byStop.get(source) ?? [], buses = 1;
    const used = new Set(level);
    while (level.length) {
      const next = [];
      for (const i of level) {
        const hit = routes[i].includes(target);
        T.step({ line: 5, key: hit, grids: [G("các tuyến", cells, { rowHead: routes.map((_, k) => `tuyến ${k}`), hl: Object.fromEntries(routes[i].map((s, c) => [i + "," + c, s === target ? "ok" : "cur"])) })], vars: { "số xe": buses }, msg: hit ? `Tuyến ${i} tới đích, cần ${buses} xe.` : `Đi tuyến ${i}, tìm các tuyến nối tiếp.` });
        if (hit) return buses;
        for (const s of routes[i]) for (const j of byStop.get(s)) if (!used.has(j)) { used.add(j); next.push(j); }
      }
      level = next;
      buses++;
    }
    return -1;
  },
});

register("making-a-large-island", {
  input: "grid = [[1, 0], [0, 1]]",
  expect: 3,
  code: `label each island, area[label] = size
best = max(area)
for each 0 cell:
  labels = distinct islands around it
  best = max(best, 1 + sum(area[l]))
return best`,
  run({ grid }, T) {
    const n = grid.length, id = matrix(n, n, 0), area = {};
    let k = 1;
    const colors = ["c1", "c2", "c3", "c4", "c5", "c6"];
    const hl = () => Object.fromEntries(id.flatMap((row, r) => row.flatMap((v, c) => (v ? [[r + "," + c, colors[(v - 2) % 6]]] : []))));
    for (let r = 0; r < n; r++) for (let c = 0; c < n; c++) {
      if (!grid[r][c] || id[r][c]) continue;
      k++;
      const st = [[r, c]];
      id[r][c] = k;
      area[k] = 0;
      while (st.length) {
        const [i, j] = st.pop();
        area[k]++;
        for (const [x, y] of DIRS) if (grid[i + x]?.[j + y] && !id[i + x][j + y]) { id[i + x][j + y] = k; st.push([i + x, j + y]); }
      }
      T.step({ line: 1, grids: [G("grid", grid, { hl: hl() })], maps: [M("diện tích đảo", area)], msg: `Đảo #${k} có diện tích ${area[k]}.` });
    }
    let best = Math.max(0, ...Object.values(area));
    for (let r = 0; r < n; r++) for (let c = 0; c < n; c++) {
      if (grid[r][c]) continue;
      const around = new Set(DIRS.map(([x, y]) => id[r + x]?.[c + y]).filter(Boolean));
      const size = 1 + [...around].reduce((s, l) => s + area[l], 0), up = size > best;
      best = Math.max(best, size);
      T.step({ line: 5, key: up, grids: [G("grid", grid, { hl: { ...hl(), [r + "," + c]: up ? "ok" : "cur" } })], maps: [M("diện tích đảo", area)], vars: { best }, msg: `Lấp (${r}, ${c}) nối ${around.size} đảo thành ${size}.` });
    }
    return best;
  },
});

register("shortest-path-visiting-all-nodes", {
  input: "graph = [[1, 2, 3], [0], [0], [0]]",
  expect: 4,
  code: `queue = all (node, {node}), seen
steps = 0
BFS by levels:
  for (u, mask):
    if mask has every node: return steps
    for v in graph[u]: visit (v, mask | 1 << v)
  steps++`,
  run({ graph }, T) {
    const n = graph.length, all = (1 << n) - 1, seen = new Set();
    let level = graph.map((_, i) => [i, 1 << i]), steps = 0;
    level.forEach(([u, m]) => seen.add(`${u},${m}`));
    const edges = graph.flatMap((ns, a) => ns.filter((b) => b > a).map((b) => ({ a, b })));
    for (;;) {
      for (const [u, m] of level) {
        const hit = m === all;
        if (hit || steps <= 2) T.step({ line: hit ? 5 : 4, key: hit, graphs: [{ label: "đồ thị", nodes: nodesOf(n), edges, hl: { ...tones(graph.map((_, i) => i).filter((i) => m & (1 << i)), "done"), [u]: hit ? "ok" : "cur" } }], vars: { "số bước": steps, "đã thăm (bitmask)": m.toString(2).padStart(n, "0") }, msg: hit ? `Thăm đủ mọi nút sau ${steps} bước.` : `Đang ở ${u}, đã thăm ${m.toString(2).padStart(n, "0")}.` });
        if (hit) return steps;
      }
      const next = [];
      for (const [u, m] of level) for (const v of graph[u]) {
        const s = `${v},${m | (1 << v)}`;
        if (!seen.has(s)) { seen.add(s); next.push([v, m | (1 << v)]); }
      }
      level = next;
      steps++;
    }
  },
});

register("largest-component-size-by-common-factor", {
  input: "nums = [4, 6, 15, 35]",
  expect: 4,
  code: `dsu over numbers up to max(nums)
for v in nums:
  for each prime factor p of v:
    union(v, p)
return largest group by find(v)`,
  run({ nums }, T) {
    const d = new DSU(Math.max(...nums) + 1), groups = () => { const m = new Map(); nums.forEach((v) => { const r = d.find(v); m.set(r, [...(m.get(r) ?? []), v]); }); return m; };
    nums.forEach((v, i) => {
      let x = v;
      const ps = [];
      for (let p = 2; p * p <= x; p++) if (x % p === 0) { ps.push(p); while (x % p === 0) x /= p; }
      if (x > 1) ps.push(x);
      ps.forEach((p) => {
        d.union(v, p);
        T.step({ line: 4, arrays: [A("nums", nums, { hl: { [i]: "cur" } })], maps: [M("nhóm", [...groups().values()].map((g, k) => [k, g]))], msg: `${v} chia hết cho ${p} → cùng nhóm với thừa số ${p}.` });
      });
    });
    const best = Math.max(...[...groups().values()].map((g) => g.length));
    T.step({ line: 5, key: true, arrays: [A("nums", nums)], maps: [M("nhóm", [...groups().values()].map((g, k) => [k, g]))], msg: `Nhóm lớn nhất có ${best} số.` });
    return best;
  },
});

register("jump-game-iv", {
  input: "arr = [100, -23, -23, 404, 100, 23, 23, 23, 3, 404]",
  expect: 3,
  code: `same[value] = indices with that value
queue = [0], steps = 0
while queue not empty:
  for i in level:
    if i == n - 1: return steps
    add i - 1, i + 1, and all same[arr[i]]; clear same[arr[i]]
  steps++`,
  run({ arr }, T) {
    const n = arr.length, same = new Map();
    arr.forEach((v, i) => same.set(v, [...(same.get(v) ?? []), i]));
    const seen = new Set([0]);
    let level = [0], steps = 0;
    while (level.length) {
      const next = [];
      for (const i of level) {
        const hit = i === n - 1;
        T.step({ line: hit ? 5 : 6, key: hit, arrays: [A("arr", arr, { small: true, hl: { ...tones([...seen], "done"), [i]: hit ? "ok" : "cur" }, ptr: ptrs({ i }) })], stacks: [Q("mức hiện tại", [...level])], vars: { steps }, msg: hit ? `Tới cuối sau ${steps} bước nhảy.` : `Từ ${i} nhảy sang kề bên hoặc các ô cùng giá trị ${arr[i]}.` });
        if (hit) return steps;
        for (const j of [i - 1, i + 1, ...(same.get(arr[i]) ?? [])]) if (j >= 0 && j < n && !seen.has(j)) { seen.add(j); next.push(j); }
        same.delete(arr[i]);
      }
      level = next;
      steps++;
    }
    return -1;
  },
});

register("remove-max-number-of-edges-to-keep-graph-fully-traversable", {
  input: "n = 4\nedges = [[3, 1, 2], [3, 2, 3], [1, 1, 3], [1, 2, 4], [1, 1, 2], [2, 3, 4]]",
  expect: 2,
  code: `alice = DSU(n), bob = DSU(n), removed = 0
for type-3 edges: useful in either? keep : removed++
for type-1 edges: alice.union or removed++
for type-2 edges: bob.union or removed++
return both connected ? removed : -1`,
  run({ n, edges }, T) {
    const a = new DSU(n + 1), b = new DSU(n + 1), tone = {};
    let removed = 0;
    const view = (line, msg, key) => T.step({ line, key, graphs: [{ label: "cạnh (giữ: xanh, bỏ: đỏ)", nodes: nodesOf(n, 1), edges: edges.map(([t, u, v], i) => ({ a: u, b: v, w: t, tone: tone[i] })) }], arrays: [A("alice", a.parent.slice(1), { small: true }), A("bob", b.parent.slice(1), { small: true })], vars: { removed }, msg });
    edges.forEach(([t, u, v], i) => {
      if (t !== 3) return;
      const x = a.union(u, v), y = b.union(u, v);
      tone[i] = x || y ? "ok" : "bad";
      if (!(x || y)) removed++;
      view(2, x || y ? `Cạnh chung ${u}–${v} có ích cho cả hai, ưu tiên giữ.` : `Cạnh chung ${u}–${v} thừa.`);
    });
    for (const [type, dsu, line] of [[1, a, 3], [2, b, 4]]) edges.forEach(([t, u, v], i) => {
      if (t !== type) return;
      const keep = dsu.union(u, v);
      tone[i] = keep ? "ok" : "bad";
      if (!keep) removed++;
      view(line, keep ? `Cạnh riêng ${u}–${v}: giữ.` : `Cạnh riêng ${u}–${v}: thừa, bỏ.`, !keep);
    });
    return a.count === 2 && b.count === 2 ? removed : -1;
  },
});

