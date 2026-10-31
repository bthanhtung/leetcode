/* Visualizers for grids (2-D DP, BFS/DFS on a matrix, backtracking boards), linked lists and binary trees. */
(function () {
  const { register, buildTree, treeToArray } = Viz;
  const G = (label, cells, o = {}) => ({ label, cells, ...o });
  const clone = (g) => g.map((r) => [...r]);
  const DIRS = [[1, 0], [-1, 0], [0, 1], [0, -1]];

  register("unique-paths", {
    input: "m = 3\nn = 7",
    expect: 28,
    run({ m, n }, T) {
      const dp = Array.from({ length: m }, () => Array(n).fill(null));
      for (let i = 0; i < m; i++) {
        for (let j = 0; j < n; j++) {
          dp[i][j] = i === 0 || j === 0 ? 1 : dp[i - 1][j] + dp[i][j - 1];
          const hl = { [i + "," + j]: "cur" };
          if (i > 0 && j > 0) hl[i - 1 + "," + j] = hl[i + "," + (j - 1)] = "mark";
          T.step({ grids: [G("số đường", clone(dp), { hl })], msg: i === 0 || j === 0 ? `Hàng/cột đầu chỉ có 1 đường.` : `Từ trên ${dp[i - 1][j]} + từ trái ${dp[i][j - 1]} = ${dp[i][j]}.` });
        }
      }
      return dp[m - 1][n - 1];
    },
  });

  register("maximal-square", {
    input: 'matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]',
    expect: 4,
    run({ matrix }, T) {
      const m = matrix.length, n = matrix[0].length;
      const dp = Array.from({ length: m }, () => Array(n).fill(null));
      let best = 0, at = null;
      for (let i = 0; i < m; i++) {
        for (let j = 0; j < n; j++) {
          if (matrix[i][j] === "1") {
            const up = i > 0 ? dp[i - 1][j] : 0, left = j > 0 ? dp[i][j - 1] : 0, diag = i > 0 && j > 0 ? dp[i - 1][j - 1] : 0;
            dp[i][j] = 1 + Math.min(up, left, diag);
            if (dp[i][j] > best) { best = dp[i][j]; at = [i, j]; }
            const hl = { [i + "," + j]: "cur" };
            if (i > 0) hl[i - 1 + "," + j] = "mark";
            if (j > 0) hl[i + "," + (j - 1)] = "mark";
            if (i > 0 && j > 0) hl[i - 1 + "," + (j - 1)] = "mark";
            T.step({ grids: [G("matrix", matrix, { hl: { [i + "," + j]: "cur" } }), G("cạnh hình vuông", clone(dp), { hl })], vars: { best }, msg: `1 + min(trên ${up}, trái ${left}, chéo ${diag}) = ${dp[i][j]}.` });
          } else {
            dp[i][j] = 0;
          }
        }
      }
      const hl = {};
      if (at) for (let i = at[0] - best + 1; i <= at[0]; i++) for (let j = at[1] - best + 1; j <= at[1]; j++) hl[i + "," + j] = "ok";
      T.step({ grids: [G("matrix", matrix, { hl }), G("cạnh hình vuông", clone(dp))], vars: { best }, msg: `Hình vuông lớn nhất cạnh ${best} → diện tích ${best * best}.` });
      return best * best;
    },
  });

  function stringDp(a, b, T, label, cell, explain) {
    const dp = Array.from({ length: a.length + 1 }, () => Array(b.length + 1).fill(null));
    for (let i = 0; i <= a.length; i++) {
      for (let j = 0; j <= b.length; j++) {
        const [v, from] = cell(dp, i, j);
        dp[i][j] = v;
        const hl = { [i + "," + j]: "cur" };
        for (const [r, c] of from) hl[r + "," + c] = "mark";
        if (i > 0 && j > 0) T.step({ grids: [G(label, clone(dp), { hl, rowHead: ["∅", ...a], colHead: ["∅", ...b] })], msg: explain(dp, i, j) });
      }
    }
    return dp[a.length][b.length];
  }

  register("longest-common-subsequence", {
    input: 'text1 = "abcde"\ntext2 = "ace"',
    expect: 3,
    run({ text1: a, text2: b }, T) {
      return stringDp(a, b, T, "LCS", (dp, i, j) => {
        if (!i || !j) return [0, []];
        if (a[i - 1] === b[j - 1]) return [dp[i - 1][j - 1] + 1, [[i - 1, j - 1]]];
        return [Math.max(dp[i - 1][j], dp[i][j - 1]), [[i - 1, j], [i, j - 1]]];
      }, (dp, i, j) => (a[i - 1] === b[j - 1] ? `'${a[i - 1]}' = '${b[j - 1]}' → chéo + 1 = ${dp[i][j]}.` : `'${a[i - 1]}' ≠ '${b[j - 1]}' → max(trên, trái) = ${dp[i][j]}.`));
    },
  });

  register("edit-distance", {
    input: 'word1 = "horse"\nword2 = "ros"',
    expect: 3,
    run({ word1: a, word2: b }, T) {
      return stringDp(a, b, T, "khoảng cách", (dp, i, j) => {
        if (!i) return [j, []];
        if (!j) return [i, []];
        if (a[i - 1] === b[j - 1]) return [dp[i - 1][j - 1], [[i - 1, j - 1]]];
        return [1 + Math.min(dp[i - 1][j - 1], dp[i - 1][j], dp[i][j - 1]), [[i - 1, j - 1], [i - 1, j], [i, j - 1]]];
      }, (dp, i, j) => (a[i - 1] === b[j - 1] ? `'${a[i - 1]}' = '${b[j - 1]}' → không tốn thao tác, lấy ô chéo: ${dp[i][j]}.` : `1 + min(thay ↖, xoá ↑, chèn ←) = ${dp[i][j]}.`));
    },
  });

  register("number-of-islands", {
    input: 'grid = [["1","1","0","0","0"],["1","1","0","0","0"],["0","0","1","0","0"],["0","0","0","1","1"]]',
    expect: 3,
    run({ grid }, T) {
      const m = grid.length, n = grid[0].length;
      const id = Array.from({ length: m }, () => Array(n).fill(0));
      const colors = ["c1", "c2", "c3", "c4", "c5", "c6"];
      let count = 0;
      const view = (cur, msg) => {
        const hl = {};
        for (let i = 0; i < m; i++) for (let j = 0; j < n; j++) if (id[i][j]) hl[i + "," + j] = colors[(id[i][j] - 1) % colors.length];
        if (cur) hl[cur] = "cur";
        T.step({ grids: [G("grid", grid, { hl })], vars: { "số đảo": count }, msg });
      };
      for (let i = 0; i < m; i++) {
        for (let j = 0; j < n; j++) {
          if (grid[i][j] !== "1" || id[i][j]) continue;
          count++;
          id[i][j] = count;
          view(i + "," + j, `Gặp đất chưa thăm ở (${i}, ${j}) → đảo mới #${count}, loang BFS.`);
          const q = [[i, j]];
          while (q.length) {
            const [r, c] = q.shift();
            for (const [dr, dc] of DIRS) {
              const a = r + dr, b = c + dc;
              if (a >= 0 && b >= 0 && a < m && b < n && grid[a][b] === "1" && !id[a][b]) {
                id[a][b] = count;
                q.push([a, b]);
                view(a + "," + b, `Loang sang (${a}, ${b}).`);
              }
            }
          }
        }
      }
      view(null, `Có ${count} đảo.`);
      return count;
    },
  });

  register("rotting-oranges", {
    input: "grid = [[2,1,1],[1,1,0],[0,1,1]]",
    expect: 4,
    run({ grid }, T) {
      const g = clone(grid);
      const m = g.length, n = g[0].length;
      let q = [], fresh = 0, minutes = 0;
      for (let i = 0; i < m; i++) for (let j = 0; j < n; j++) { if (g[i][j] === 2) q.push([i, j]); if (g[i][j] === 1) fresh++; }
      const show = () => g.map((r) => r.map((v) => (v === 2 ? "🟤" : v === 1 ? "🍊" : "")));
      T.step({ grids: [G("phút 0", show())], vars: { "còn tươi": fresh }, msg: "Mọi quả thối ban đầu cùng vào hàng đợi (BFS nhiều nguồn)." });
      while (q.length && fresh) {
        const next = [], hl = {};
        for (const [r, c] of q) for (const [dr, dc] of DIRS) {
          const a = r + dr, b = c + dc;
          if (a >= 0 && b >= 0 && a < m && b < n && g[a][b] === 1) { g[a][b] = 2; fresh--; next.push([a, b]); hl[a + "," + b] = "bad"; }
        }
        minutes++;
        q = next;
        T.step({ grids: [G(`phút ${minutes}`, show(), { hl })], vars: { "còn tươi": fresh }, msg: `Phút ${minutes}: ${next.length} quả bị lây.` });
      }
      const ans = fresh ? -1 : minutes;
      T.step({ grids: [G("kết thúc", show())], vars: { "còn tươi": fresh }, msg: fresh ? "Còn quả tươi không bị lây tới → −1." : `Tất cả thối sau ${minutes} phút.` });
      return ans;
    },
  });

  register("word-search", {
    input: 'board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]]\nword = "ABCCED"',
    expect: true,
    run({ board, word }, T) {
      const m = board.length, n = board[0].length;
      const path = [];
      const view = (msg, last, cls) => {
        const hl = {};
        path.forEach(([r, c]) => (hl[r + "," + c] = "ok"));
        if (last) hl[last] = cls;
        T.step({ grids: [G("board", board, { hl })], vars: { "đã khớp": word.slice(0, path.length) }, msg });
      };
      function dfs(r, c, k) {
        if (r < 0 || c < 0 || r >= m || c >= n || path.some(([a, b]) => a === r && b === c)) return false;
        if (board[r][c] !== word[k]) {
          view(`(${r}, ${c}) = '${board[r][c]}' ≠ '${word[k]}'.`, r + "," + c, "bad");
          return false;
        }
        path.push([r, c]);
        view(`(${r}, ${c}) khớp '${word[k]}'.`);
        if (k === word.length - 1) return true;
        for (const [dr, dc] of DIRS) if (dfs(r + dr, c + dc, k + 1)) return true;
        path.pop();
        view(`Hết hướng đi từ (${r}, ${c}) → quay lui.`, r + "," + c, "dim");
        return false;
      }
      for (let i = 0; i < m; i++) for (let j = 0; j < n; j++) {
        if (board[i][j] !== word[0]) continue;
        if (dfs(i, j, 0)) { view(`Tìm thấy "${word}".`); return true; }
      }
      view(`Không có "${word}".`);
      return false;
    },
  });

  register("n-queens", {
    input: "n = 4",
    expect: 2,
    run({ n }, T) {
      const cols = [];
      let solutions = 0;
      const board = (bad) => {
        const g = Array.from({ length: n }, () => Array(n).fill(""));
        cols.forEach((c, r) => (g[r][c] = "♛"));
        const hl = {};
        cols.forEach((c, r) => (hl[r + "," + c] = "ok"));
        if (bad) hl[bad] = "bad";
        return G("bàn cờ", g, { hl });
      };
      const safe = (r, c) => cols.every((cc, rr) => cc !== c && Math.abs(cc - c) !== r - rr);
      (function place(r) {
        if (r === n) {
          solutions++;
          T.step({ grids: [board()], vars: { "lời giải": solutions }, msg: `Đặt đủ ${n} hậu → lời giải #${solutions}.` });
          return;
        }
        for (let c = 0; c < n; c++) {
          if (!safe(r, c)) {
            T.step({ grids: [board(r + "," + c)], vars: { "lời giải": solutions }, msg: `(${r}, ${c}) bị tấn công.` });
            continue;
          }
          cols.push(c);
          T.step({ grids: [board()], vars: { "lời giải": solutions }, msg: `Đặt hậu ở hàng ${r}, cột ${c}.` });
          place(r + 1);
          cols.pop();
        }
      })(0);
      return solutions;
    },
  });

  /* ---------- linked lists ---------- */

  const listOf = (vals) => ({ vals, next: vals.map((_, i) => (i + 1 < vals.length ? i + 1 : -1)) });
  const ptrs = (o) => {
    const p = {};
    for (const [name, i] of Object.entries(o)) if (i !== -1 && i !== null && i !== undefined) (p[i] = p[i] || []).push(name);
    return p;
  };

  register("reverse-linked-list", {
    input: "head = [1, 2, 3, 4, 5]",
    expect: [5, 4, 3, 2, 1],
    run({ head }, T) {
      const L = listOf(head);
      let prev = -1, cur = L.vals.length ? 0 : -1;
      T.step({ list: { ...L, ptr: ptrs({ prev, cur }) }, msg: "prev = null, cur = head." });
      while (cur !== -1) {
        const nxt = L.next[cur];
        L.next[cur] = prev;
        T.step({ list: { ...L, hl: { [cur]: "cur" }, ptr: ptrs({ prev, cur, next: nxt }) }, msg: `Lưu next, quay mũi tên của ${L.vals[cur]} về ${prev === -1 ? "null" : L.vals[prev]}.` });
        prev = cur;
        cur = nxt;
        T.step({ list: { ...L, hl: { [prev]: "ok" }, ptr: ptrs({ prev, cur }) }, msg: "Tiến prev và cur một bước." });
      }
      const out = [];
      for (let i = prev; i !== -1; i = L.next[i]) out.push(L.vals[i]);
      T.step({ list: { ...L, ptr: ptrs({ "head mới": prev }) }, msg: `prev là head mới: ${out.join(" → ")}.` });
      return out;
    },
  });

  register("middle-of-the-linked-list", {
    input: "head = [1, 2, 3, 4, 5, 6]",
    expect: 4,
    run({ head }, T) {
      const L = listOf(head);
      let slow = 0, fast = 0;
      T.step({ list: { ...L, ptr: ptrs({ slow, fast }) }, msg: "slow đi 1 bước, fast đi 2 bước." });
      while (fast !== -1 && L.next[fast] !== -1) {
        slow = L.next[slow];
        fast = L.next[L.next[fast]];
        T.step({ list: { ...L, hl: { [slow]: "cur" }, ptr: ptrs({ slow, fast }) }, msg: fast === -1 ? "fast đã ra khỏi danh sách." : `slow ở ${L.vals[slow]}, fast ở ${L.vals[fast]}.` });
      }
      T.step({ list: { ...L, hl: { [slow]: "ok" }, ptr: ptrs({ "giữa": slow }) }, msg: `fast tới cuối thì slow ở giữa: ${L.vals[slow]}.` });
      return L.vals[slow];
    },
  });

  register("linked-list-cycle", {
    input: "head = [3, 2, 0, -4, 7]\npos = 1",
    expect: true,
    run({ head, pos }, T) {
      const L = listOf(head);
      if (pos >= 0) L.next[L.vals.length - 1] = pos;
      let slow = 0, fast = 0;
      T.step({ list: { ...L, ptr: ptrs({ slow, fast }) }, msg: pos >= 0 ? `Node cuối nối về vị trí ${pos} → có chu trình. Rùa và thỏ cùng xuất phát.` : "Không có chu trình." });
      while (fast !== -1 && L.next[fast] !== -1) {
        slow = L.next[slow];
        fast = L.next[L.next[fast]];
        const met = slow === fast;
        T.step({ list: { ...L, hl: met ? { [slow]: "ok" } : {}, ptr: ptrs({ slow, fast }) }, msg: met ? "Hai con trỏ gặp nhau → có chu trình." : "slow +1, fast +2." });
        if (met) return true;
      }
      T.step({ list: { ...L, ptr: ptrs({ slow }) }, msg: "fast chạm null → không có chu trình." });
      return false;
    },
  });

  /* ---------- binary trees ---------- */

  register("invert-binary-tree", {
    input: "root = [4, 2, 7, 1, 3, 6, 9]",
    expect: [4, 7, 2, 9, 6, 3, 1],
    run({ root }, T) {
      const t = buildTree(root);
      T.step({ tree: t, msg: "Đổi chỗ con trái và con phải ở mọi node." });
      (function inv(id) {
        if (id === -1) return;
        const n = t.nodes[id];
        [n.l, n.r] = [n.r, n.l];
        T.step({ tree: { ...t, hl: { [id]: "cur" } }, msg: `Đổi hai con của ${n.v}.` });
        inv(n.l);
        inv(n.r);
      })(t.root);
      T.step({ tree: t, msg: "Xong." });
      return treeToArray(t);
    },
  });

  register("maximum-depth-of-binary-tree", {
    input: "root = [3, 9, 20, null, null, 15, 7]",
    expect: 3,
    run({ root }, T) {
      const t = buildTree(root);
      const label = {}, hl = {};
      const depth = (function d(id) {
        if (id === -1) return 0;
        hl[id] = "cur";
        T.step({ tree: { ...t, hl: { ...hl }, label: { ...label } }, msg: `Vào ${t.nodes[id].v}: hỏi độ sâu hai cây con.` });
        const v = 1 + Math.max(d(t.nodes[id].l), d(t.nodes[id].r));
        label[id] = v;
        hl[id] = "ok";
        T.step({ tree: { ...t, hl: { ...hl }, label: { ...label } }, msg: `${t.nodes[id].v}: 1 + max(trái, phải) = ${v}.` });
        return v;
      })(t.root);
      return depth;
    },
  });

  register("binary-tree-level-order-traversal", {
    input: "root = [3, 9, 20, null, null, 15, 7]",
    expect: [[3], [9, 20], [15, 7]],
    run({ root }, T) {
      const t = buildTree(root);
      const out = [];
      let queue = t.root === -1 ? [] : [t.root];
      const done = {};
      while (queue.length) {
        const level = [], next = [];
        for (const id of queue) {
          level.push(t.nodes[id].v);
          done[id] = "ok";
          for (const c of [t.nodes[id].l, t.nodes[id].r]) if (c !== -1) next.push(c);
        }
        out.push(level);
        const hl = { ...done };
        queue.forEach((id) => (hl[id] = "cur"));
        T.step({ tree: { ...t, hl }, arrays: [{ label: "tầng hiện tại", values: level, small: true }, { label: "hàng đợi kế", values: next.map((id) => t.nodes[id].v), small: true }], vars: { "kết quả": out }, msg: `Lấy ra cả tầng ${out.length}: [${level.join(", ")}], đưa con của chúng vào hàng đợi.` });
        queue = next;
      }
      return out;
    },
  });

  register("lowest-common-ancestor-of-a-binary-search-tree", {
    input: "root = [6, 2, 8, 0, 4, 7, 9, null, null, 3, 5]\np = 3\nq = 5",
    expect: 4,
    run({ root, p, q }, T) {
      const t = buildTree(root);
      const mark = {};
      t.nodes.forEach((n, i) => { if (n.v === p || n.v === q) mark[i] = "mark"; });
      let id = t.root;
      while (id !== -1) {
        const v = t.nodes[id].v;
        if (p < v && q < v) {
          T.step({ tree: { ...t, hl: { ...mark, [id]: "cur" } }, msg: `${p} và ${q} đều nhỏ hơn ${v} → đi sang trái.` });
          id = t.nodes[id].l;
        } else if (p > v && q > v) {
          T.step({ tree: { ...t, hl: { ...mark, [id]: "cur" } }, msg: `${p} và ${q} đều lớn hơn ${v} → đi sang phải.` });
          id = t.nodes[id].r;
        } else {
          T.step({ tree: { ...t, hl: { ...mark, [id]: "ok" } }, msg: `${p} và ${q} tách về hai phía (hoặc trùng) ${v} → ${v} là tổ tiên chung thấp nhất.` });
          return v;
        }
      }
      return null;
    },
  });
})();
