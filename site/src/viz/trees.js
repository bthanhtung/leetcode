/* Binary trees and BSTs. Most walks show the tree, the call stack and the result built so far. */
import { A, Q, S, buildTree, ptrs, register, tones, treeToArray } from "./engine.js";

const T_ = (t, o = {}) => ({ ...t, ...o });

/** Shared traversal view: colours visited nodes, shows the output list and optional call stack. */
function walker(t, T) {
  const seen = {};
  return {
    seen,
    show(line, cur, msg, { out, stack, tag, tone = "cur", key, vars } = {}) {
      T.step({ line, key, tree: T_(t, { hl: { ...seen, ...(cur >= 0 ? { [cur]: tone } : {}) }, tag }), arrays: out ? [A("kết quả", [...out], { small: true, noIndex: true })] : undefined, stacks: stack ? [S("ngăn xếp lời gọi", stack.map((id) => t.nodes[id].v))] : undefined, vars, msg });
    },
  };
}

function traversal(slug, input, expect, code, order, lines) {
  register(slug, {
    input, expect, code,
    run({ root }, T) {
      const t = buildTree(root), out = [], stack = [], w = walker(t, T);
      const dfs = (id) => {
        if (id === -1) return;
        stack.push(id);
        w.show(lines.enter, id, `Vào nút ${t.nodes[id].v}.`, { out, stack });
        for (const part of order) {
          if (part === "L") dfs(t.nodes[id].l);
          else if (part === "R") dfs(t.nodes[id].r);
          else {
            out.push(t.nodes[id].v);
            w.seen[id] = "ok";
            w.show(lines.visit, id, `Ghi ${t.nodes[id].v} vào kết quả.`, { out, stack, tone: "ok", key: true });
          }
        }
        stack.pop();
      };
      dfs(t.root);
      if (!root.length) T.step({ line: 1, tree: t, msg: "Cây rỗng." });
      return out;
    },
  });
}

traversal("binary-tree-inorder-traversal", "root = [1, 2, 3, 4, 5, null, 6]", [4, 2, 5, 1, 3, 6], `inorder(node):
  if node == null: return
  inorder(node.left)
  visit(node)
  inorder(node.right)`, ["L", "V", "R"], { enter: 3, visit: 4 });

traversal("binary-tree-preorder-traversal", "root = [1, 2, 3, 4, 5, null, 6]", [1, 2, 4, 5, 3, 6], `preorder(node):
  if node == null: return
  visit(node)
  preorder(node.left)
  preorder(node.right)`, ["V", "L", "R"], { enter: 2, visit: 3 });

traversal("binary-tree-postorder-traversal", "root = [1, 2, 3, 4, 5, null, 6]", [4, 5, 2, 6, 3, 1], `postorder(node):
  if node == null: return
  postorder(node.left)
  postorder(node.right)
  visit(node)`, ["L", "R", "V"], { enter: 3, visit: 5 });

register("unique-binary-search-trees", {
  input: "n = 4",
  expect: 14,
  code: `dp[0] = 1, dp[1] = 1
for k in 2..n:
  for root in 1..k:
    dp[k] += dp[root-1] * dp[k-root]
return dp[n]`,
  run({ n }, T) {
    const dp = Array(n + 1).fill(0);
    dp[0] = 1;
    if (n >= 1) dp[1] = 1;
    for (let k = 2; k <= n; k++) for (let r = 1; r <= k; r++) {
      dp[k] += dp[r - 1] * dp[k - r];
      T.step({ line: 4, arrays: [A("dp", dp, { hl: { [k]: "cur", [r - 1]: "mark", [k - r]: "mark" } })], vars: { k, "gốc": r }, msg: `${k} nút, gốc là ${r}: trái ${r - 1} nút, phải ${k - r} nút → thêm ${dp[r - 1]} × ${dp[k - r]}.` });
    }
    return dp[n];
  },
});

register("validate-binary-search-tree", {
  input: "root = [5, 3, 8, 1, 4, 7, 9]",
  expect: true,
  code: `check(node, lo, hi):
  if node == null: return true
  if node.val <= lo or node.val >= hi: return false
  return check(node.left, lo, node.val)
     and check(node.right, node.val, hi)`,
  run({ root }, T) {
    const t = buildTree(root), w = walker(t, T);
    const go = (id, lo, hi) => {
      if (id === -1) return true;
      const v = t.nodes[id].v, ok = v > lo && v < hi;
      const show = (x) => (Math.abs(x) === Infinity ? (x < 0 ? "−∞" : "+∞") : x);
      w.seen[id] = ok ? "ok" : "bad";
      w.show(3, id, ok ? `${v} nằm trong (${show(lo)}, ${show(hi)}).` : `${v} vượt khoảng (${show(lo)}, ${show(hi)}).`, { tone: ok ? "ok" : "bad", key: !ok, tag: { [id]: `(${show(lo)}, ${show(hi)})` } });
      return ok && go(t.nodes[id].l, lo, v) && go(t.nodes[id].r, v, hi);
    };
    return go(t.root, -Infinity, Infinity);
  },
});

register("recover-binary-search-tree", {
  input: "root = [3, 1, 4, null, null, 2]",
  expect: [2, 1, 4, null, null, 3],
  code: `prev = null, first = null, second = null
inorder(node):
  inorder(node.left)
  if prev and prev.val > node.val:
    if first == null: first = prev
    second = node
  prev = node
  inorder(node.right)
swap(first.val, second.val)`,
  run({ root }, T) {
    const t = buildTree(root), w = walker(t, T);
    let prev = -1, first = -1, second = -1;
    const dfs = (id) => {
      if (id === -1) return;
      dfs(t.nodes[id].l);
      const bad = prev !== -1 && t.nodes[prev].v > t.nodes[id].v;
      if (bad) { if (first === -1) first = prev; second = id; }
      w.show(bad ? 6 : 4, id, bad ? `${t.nodes[prev].v} > ${t.nodes[id].v}: thứ tự trung tố bị lệch.` : "Trung tố tăng dần, ổn.", { key: bad, vars: { first: first === -1 ? "-" : t.nodes[first].v, second: second === -1 ? "-" : t.nodes[second].v } });
      w.seen[id] = "done";
      prev = id;
      dfs(t.nodes[id].r);
    };
    dfs(t.root);
    [t.nodes[first].v, t.nodes[second].v] = [t.nodes[second].v, t.nodes[first].v];
    T.step({ line: 9, key: true, tree: T_(t, { hl: { [first]: "ok", [second]: "ok" } }), msg: "Đổi giá trị hai nút sai chỗ." });
    return treeToArray(t);
  },
});

function pairWalk(slug, input, expect, code, labelA, labelB, mirror) {
  register(slug, {
    input, expect, code,
    run(args, T) {
      const vals = Object.values(args);
      const a = buildTree(vals[0]), b = mirror ? a : buildTree(vals[1]);
      const hlA = {}, hlB = {};
      const view = (line, msg, key) => T.step({ line, key, trees: mirror ? [T_(a, { label: labelA, hl: { ...hlA, ...hlB } })] : [T_(a, { label: labelA, hl: { ...hlA } }), T_(b, { label: labelB, hl: { ...hlB } })], msg });
      const go = (x, y) => {
        if (x === -1 && y === -1) return true;
        if (x === -1 || y === -1) { if (x !== -1) hlA[x] = "bad"; if (y !== -1) hlB[y] = "bad"; view(3, "Một bên có nút, bên kia không.", true); return false; }
        const same = a.nodes[x].v === b.nodes[y].v;
        hlA[x] = hlB[y] = same ? "ok" : "bad";
        view(4, same ? `Hai nút cùng là ${a.nodes[x].v}.` : `${a.nodes[x].v} ≠ ${b.nodes[y].v}.`, !same);
        if (!same) return false;
        return mirror ? go(a.nodes[x].l, a.nodes[y].r) && go(a.nodes[x].r, a.nodes[y].l) : go(a.nodes[x].l, b.nodes[y].l) && go(a.nodes[x].r, b.nodes[y].r);
      };
      return mirror ? go(a.nodes[a.root].l, a.nodes[a.root].r) : go(a.root, b.root);
    },
  });
}

pairWalk("same-tree", "p = [1, 2, 3]\nq = [1, 2, 3]", true, `same(p, q):
  if !p and !q: return true
  if !p or !q: return false
  if p.val != q.val: return false
  return same(p.left, q.left) and same(p.right, q.right)`, "p", "q", false);

pairWalk("symmetric-tree", "root = [1, 2, 2, 3, 4, 4, 3]", true, `mirror(a, b):
  if !a and !b: return true
  if !a or !b: return false
  if a.val != b.val: return false
  return mirror(a.left, b.right) and mirror(a.right, b.left)`, "root", "", true);

function levels(t) {
  const out = [];
  let cur = t.root === -1 ? [] : [t.root];
  while (cur.length) {
    out.push(cur);
    cur = cur.flatMap((id) => [t.nodes[id].l, t.nodes[id].r].filter((c) => c !== -1));
  }
  return out;
}

register("binary-tree-zigzag-level-order-traversal", {
  input: "root = [3, 9, 20, null, null, 15, 7]",
  expect: [[3], [20, 9], [15, 7]],
  code: `queue = [root], leftToRight = true
while queue not empty:
  level = pop all nodes in queue
  if !leftToRight: reverse(level)
  result.add(level)
  push their children
  leftToRight = !leftToRight`,
  run({ root }, T) {
    const t = buildTree(root), out = [], hl = {};
    levels(t).forEach((ids, d) => {
      const vals = ids.map((id) => t.nodes[id].v);
      if (d % 2) vals.reverse();
      out.push(vals);
      ids.forEach((id) => (hl[id] = "done"));
      T.step({ line: d % 2 ? 4 : 5, tree: T_(t, { hl: { ...hl, ...tones(ids, "cur") } }), arrays: [A(`mức ${d}`, vals, { noIndex: true })], vars: { "kết quả": out }, msg: d % 2 ? "Mức lẻ: đọc từ phải sang." : "Mức chẵn: đọc từ trái sang." });
    });
    return out;
  },
});

register("construct-binary-tree-from-preorder-and-inorder-traversal", {
  input: "preorder = [3, 9, 20, 15, 7]\ninorder = [9, 3, 15, 20, 7]",
  expect: [3, 9, 20, null, null, 15, 7],
  code: `idx = value -> position in inorder
build(lo, hi):
  if lo > hi: return null
  node = new Node(preorder[p++])
  node.left = build(lo, idx[node.val] - 1)
  node.right = build(idx[node.val] + 1, hi)
  return node`,
  run({ preorder, inorder }, T) {
    const idx = new Map(inorder.map((v, i) => [v, i]));
    const t = { nodes: [], root: -1 };
    let p = 0;
    const build = (lo, hi) => {
      if (lo > hi) return -1;
      const v = preorder[p], m = idx.get(v), id = t.nodes.length;
      t.nodes.push({ v, l: -1, r: -1 });
      if (t.root === -1) t.root = id;
      T.step({ line: 4, key: true, tree: T_(t, { hl: { [id]: "ok" } }), arrays: [A("preorder", preorder, { hl: { [p]: "ok" }, ptr: ptrs({ p }) }), A("inorder", inorder, { range: [lo, hi], dimOutside: true, hl: { [m]: "ok" }, ptr: ptrs({ gốc: m }) })], msg: `Phần tử kế của preorder là gốc: ${v}. Bên trái ${v} trong inorder là cây con trái.` });
      p++;
      t.nodes[id].l = build(lo, m - 1);
      t.nodes[id].r = build(m + 1, hi);
      T.step({ line: 7, tree: T_(t, { hl: { [id]: "cur" } }), arrays: [A("inorder", inorder, { range: [lo, hi], dimOutside: true })], msg: `Xong cây con gốc ${v}.` });
      return id;
    };
    build(0, inorder.length - 1);
    return treeToArray(t);
  },
});

register("convert-sorted-array-to-binary-search-tree", {
  input: "nums = [-10, -3, 0, 5, 9]",
  expect: [0, -10, 5, null, -3, null, 9],
  code: `build(lo, hi):
  if lo > hi: return null
  mid = (lo + hi) / 2
  node = new Node(nums[mid])
  node.left = build(lo, mid - 1)
  node.right = build(mid + 1, hi)
  return node`,
  run({ nums }, T) {
    const t = { nodes: [], root: -1 };
    const build = (lo, hi) => {
      if (lo > hi) return -1;
      const mid = (lo + hi) >> 1, id = t.nodes.length;
      t.nodes.push({ v: nums[mid], l: -1, r: -1 });
      if (t.root === -1) t.root = id;
      T.step({ line: 4, tree: T_(t, { hl: { [id]: "ok" } }), arrays: [A("nums", nums, { range: [lo, hi], dimOutside: true, hl: { [mid]: "ok" }, ptr: ptrs({ mid }) })], msg: `Chọn giữa đoạn [${lo}, ${hi}]: ${nums[mid]} làm gốc để cây cân bằng.` });
      t.nodes[id].l = build(lo, mid - 1);
      t.nodes[id].r = build(mid + 1, hi);
      T.step({ line: 7, tree: T_(t, { hl: { [id]: "cur" } }), arrays: [A("nums", nums, { range: [lo, hi], dimOutside: true })], msg: `Gắn xong hai con của ${nums[mid]}.` });
      return id;
    };
    build(0, nums.length - 1);
    return treeToArray(t);
  },
});

/** A post-order computation: `calc(node, leftResult, rightResult)` returns { value, msg, tone?, key? }. */
function postOrder(slug, input, expect, code, line, empty, calc, finish = (v) => v) {
  register(slug, {
    input, expect, code,
    run({ root }, T) {
      const t = buildTree(root), w = walker(t, T), state = {};
      const tag = {};
      const dfs = (id) => {
        if (id === -1) return empty;
        const l = dfs(t.nodes[id].l), r = dfs(t.nodes[id].r);
        const res = calc(t.nodes[id].v, l, r, state);
        tag[id] = res.tag ?? res.value;
        w.seen[id] = res.tone ?? "done";
        w.show(line, id, res.msg, { tag: { ...tag }, tone: res.tone ?? "cur", key: res.key, vars: { ...state } });
        return res.value;
      };
      const v = dfs(t.root);
      return finish(v, state);
    },
  });
}

postOrder("balanced-binary-tree", "root = [3, 9, 20, null, null, 15, 7]", true, `height(node):       // -1 nghĩa là lệch
  if node == null: return 0
  l = height(node.left), r = height(node.right)
  if l == -1 or r == -1 or |l - r| > 1: return -1
  return 1 + max(l, r)`, 4, 0, (v, l, r) => {
  const bad = l === -1 || r === -1 || Math.abs(l - r) > 1;
  return bad ? { value: -1, tag: "lệch", tone: "bad", key: true, msg: `Nút ${v}: hai bên chênh ${Math.abs(l - r)}.` } : { value: 1 + Math.max(l, r), msg: `Nút ${v}: trái ${l}, phải ${r} → cao ${1 + Math.max(l, r)}.` };
}, (v) => v !== -1);

register("minimum-depth-of-binary-tree", {
  input: "root = [3, 9, 20, null, null, 15, 7]",
  expect: 2,
  code: `queue = [root], depth = 1
while queue not empty:
  for each node in current level:
    if node is a leaf: return depth
    push its children
  depth++`,
  run({ root }, T) {
    const t = buildTree(root), hl = {};
    let depth = 1;
    for (const ids of levels(t)) {
      for (const id of ids) {
        const leaf = t.nodes[id].l === -1 && t.nodes[id].r === -1;
        hl[id] = leaf ? "ok" : "done";
        T.step({ line: leaf ? 4 : 5, key: leaf, tree: T_(t, { hl: { ...hl, [id]: leaf ? "ok" : "cur" } }), vars: { depth }, msg: leaf ? `${t.nodes[id].v} là lá đầu tiên BFS gặp → độ sâu ${depth}.` : `${t.nodes[id].v} chưa phải lá.` });
        if (leaf) return depth;
      }
      depth++;
    }
    return 0;
  },
});

function rootToLeaf(slug, input, expect, code, run) {
  register(slug, { input, expect, code, run });
}

rootToLeaf("path-sum", "root = [5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1]\ntargetSum = 22", true, `has(node, left):
  if node == null: return false
  left -= node.val
  if node is a leaf: return left == 0
  return has(node.left, left) or has(node.right, left)`, ({ root, targetSum }, T) => {
  const t = buildTree(root), path = [];
  const dfs = (id, left) => {
    if (id === -1) return false;
    left -= t.nodes[id].v;
    path.push(id);
    const leaf = t.nodes[id].l === -1 && t.nodes[id].r === -1, hit = leaf && left === 0;
    T.step({ line: leaf ? 4 : 3, key: hit, tree: T_(t, { hl: { ...tones(path, "win"), [id]: hit ? "ok" : leaf ? "bad" : "cur" } }), vars: { "còn thiếu": left }, msg: hit ? "Tới lá và vừa đủ tổng." : leaf ? `Tới lá nhưng còn thiếu ${left}.` : `Trừ ${t.nodes[id].v}, còn thiếu ${left}.` });
    const ok = hit || (!leaf && (dfs(t.nodes[id].l, left) || dfs(t.nodes[id].r, left)));
    path.pop();
    return ok;
  };
  return dfs(t.root, targetSum);
});

rootToLeaf("path-sum-ii", "root = [5, 4, 8, 11, null, 13, 4, 7, 2, null, null, 5, 1]\ntargetSum = 22", [[5, 4, 11, 2], [5, 8, 4, 5]], `dfs(node, left):
  if node == null: return
  path.add(node.val); left -= node.val
  if leaf and left == 0: result.add(copy of path)
  dfs(node.left, left); dfs(node.right, left)
  path.removeLast()`, ({ root, targetSum }, T) => {
  const t = buildTree(root), path = [], out = [];
  const dfs = (id, left) => {
    if (id === -1) return;
    left -= t.nodes[id].v;
    path.push(id);
    const leaf = t.nodes[id].l === -1 && t.nodes[id].r === -1, hit = leaf && left === 0;
    if (hit) out.push(path.map((x) => t.nodes[x].v));
    T.step({ line: hit ? 4 : 3, key: hit, tree: T_(t, { hl: { ...tones(path, "win"), [id]: hit ? "ok" : "cur" } }), arrays: [A("path", path.map((x) => t.nodes[x].v), { small: true, noIndex: true })], vars: { "còn thiếu": left, "kết quả": out }, msg: hit ? "Đường này đủ tổng, lưu lại." : `Đi xuống ${t.nodes[id].v}.` });
    dfs(t.nodes[id].l, left);
    dfs(t.nodes[id].r, left);
    path.pop();
  };
  dfs(t.root, targetSum);
  return out;
});

register("flatten-binary-tree-to-linked-list", {
  input: "root = [1, 2, 5, 3, 4, null, 6]",
  expect: [1, null, 2, null, 3, null, 4, null, 5, null, 6],
  code: `cur = root
while cur:
  if cur.left:
    p = rightmost of cur.left
    p.right = cur.right
    cur.right = cur.left
    cur.left = null
  cur = cur.right`,
  run({ root }, T) {
    const t = buildTree(root);
    let cur = t.root;
    while (cur !== -1) {
      const n = t.nodes[cur];
      if (n.l !== -1) {
        let p = n.l;
        while (t.nodes[p].r !== -1) p = t.nodes[p].r;
        T.step({ line: 4, tree: T_(t, { hl: { [cur]: "cur", [p]: "mark" } }), msg: `Nút phải nhất của nhánh trái là ${t.nodes[p].v}.` });
        t.nodes[p].r = n.r;
        n.r = n.l;
        n.l = -1;
        T.step({ line: 7, key: true, tree: T_(t, { hl: { [cur]: "ok", [p]: "mark" } }), msg: "Nối nhánh phải vào sau đó, chuyển nhánh trái sang phải." });
      }
      cur = n.r;
    }
    return treeToArray(t);
  },
});

register("populating-next-right-pointers-in-each-node", {
  input: "root = [1, 2, 3, 4, 5, 6, 7]",
  expect: [1, "#", 2, 3, "#", 4, 5, 6, 7, "#"],
  code: `start = root
while start and start.left:
  cur = start
  while cur:
    cur.left.next = cur.right
    if cur.next: cur.right.next = cur.next.left
    cur = cur.next
  start = start.left`,
  run({ root }, T) {
    const t = buildTree(root), out = [], tag = {};
    levels(t).forEach((ids) => {
      ids.forEach((id, i) => {
        tag[id] = i + 1 < ids.length ? `→${t.nodes[ids[i + 1]].v}` : "→#";
        T.step({ line: i % 2 ? 6 : 5, tree: T_(t, { hl: { [id]: "cur", ...(i + 1 < ids.length ? { [ids[i + 1]]: "mark" } : {}) }, tag: { ...tag } }), msg: i + 1 < ids.length ? `${t.nodes[id].v}.next = ${t.nodes[ids[i + 1]].v}.` : `${t.nodes[id].v} ở cuối mức, next = null.` });
      });
      out.push(...ids.map((id) => t.nodes[id].v), "#");
    });
    return out;
  },
});

postOrder("binary-tree-maximum-path-sum", "root = [-10, 9, 20, null, null, 15, 7]", 42, `best = -inf
gain(node):
  if node == null: return 0
  l = max(gain(node.left), 0), r = max(gain(node.right), 0)
  best = max(best, node.val + l + r)   // đường cong tại node
  return node.val + max(l, r)          // chỉ đi tiếp một nhánh`, 5, 0, (v, l, r, s) => {
  const L_ = Math.max(l, 0), R_ = Math.max(r, 0), through = v + L_ + R_;
  const up = s.best === undefined || through > s.best;
  s.best = Math.max(s.best ?? -Infinity, through);
  return { value: v + Math.max(L_, R_), key: up, tone: up ? "ok" : undefined, msg: `Đường qua ${v}: ${L_} + ${v} + ${R_} = ${through}. Trả lên cha ${v + Math.max(L_, R_)}.` };
}, (_v, s) => s.best);

register("sum-root-to-leaf-numbers", {
  input: "root = [4, 9, 0, 5, 1]",
  expect: 1026,
  code: `dfs(node, x):
  if node == null: return 0
  x = x * 10 + node.val
  if leaf: return x
  return dfs(node.left, x) + dfs(node.right, x)`,
  run({ root }, T) {
    const t = buildTree(root), path = [];
    let total = 0;
    const dfs = (id, x) => {
      if (id === -1) return;
      x = x * 10 + t.nodes[id].v;
      path.push(id);
      const leaf = t.nodes[id].l === -1 && t.nodes[id].r === -1;
      if (leaf) total += x;
      T.step({ line: leaf ? 4 : 3, key: leaf, tree: T_(t, { hl: { ...tones(path, "win"), [id]: leaf ? "ok" : "cur" }, tag: { [id]: x } }), vars: { "tổng": total }, msg: leaf ? `Tới lá: số ${x}, tổng ${total}.` : `Số đang ghép: ${x}.` });
      dfs(t.nodes[id].l, x);
      dfs(t.nodes[id].r, x);
      path.pop();
    };
    dfs(t.root, 0);
    return total;
  },
});

register("binary-search-tree-iterator", {
  input: "root = [7, 3, 15, null, null, 9, 20]",
  expect: [3, 7, 9, 15, 20],
  code: `pushLeft(node): while node: stack.push(node); node = node.left
init: pushLeft(root)
next():
  node = stack.pop()
  pushLeft(node.right)
  return node.val
hasNext(): return stack not empty`,
  run({ root }, T) {
    const t = buildTree(root), st = [], out = [], seen = {};
    const view = (line, msg, cur, key) => T.step({ line, key, tree: T_(t, { hl: { ...seen, ...tones(st, "mark"), ...(cur >= 0 ? { [cur]: "ok" } : {}) } }), stacks: [S("stack", st.map((i) => t.nodes[i].v))], arrays: [A("next() đã trả", [...out], { small: true, noIndex: true })], msg });
    const pushLeft = (id, line) => { while (id !== -1) { st.push(id); view(line, `Đẩy ${t.nodes[id].v}, đi tiếp sang trái.`, -1); id = t.nodes[id].l; } };
    pushLeft(t.root, 2);
    while (st.length) {
      const id = st.pop();
      out.push(t.nodes[id].v);
      seen[id] = "done";
      view(4, `next() trả về ${t.nodes[id].v}.`, id, true);
      pushLeft(t.nodes[id].r, 5);
    }
    return out;
  },
});

register("binary-tree-right-side-view", {
  input: "root = [1, 2, 3, null, 5, null, 4]",
  expect: [1, 3, 4],
  code: `queue = [root]
while queue not empty:
  size = len(queue)
  for i in 0..size-1:
    node = queue.pop()
    if i == size - 1: result.add(node.val)
    push children`,
  run({ root }, T) {
    const t = buildTree(root), out = [], hl = {};
    for (const ids of levels(t)) {
      ids.forEach((id, i) => {
        const last = i === ids.length - 1;
        if (last) { out.push(t.nodes[id].v); hl[id] = "ok"; } else hl[id] = "dim";
        T.step({ line: last ? 6 : 5, key: last, tree: T_(t, { hl: { ...hl, ...(last ? {} : { [id]: "cur" }) } }), stacks: [Q("hàng đợi của mức", ids.slice(i).map((x) => t.nodes[x].v))], arrays: [A("nhìn từ phải", [...out], { small: true, noIndex: true })], msg: last ? `${t.nodes[id].v} là nút cuối của mức → nhìn thấy từ bên phải.` : `${t.nodes[id].v} bị che.` });
      });
    }
    return out;
  },
});

register("count-complete-tree-nodes", {
  input: "root = [1, 2, 3, 4, 5, 6]",
  expect: 6,
  code: `count(node):
  if node == null: return 0
  hl = height via left edges, hr = height via right edges
  if hl == hr: return 2^hl - 1     // cây đầy
  return 1 + count(node.left) + count(node.right)`,
  run({ root }, T) {
    const t = buildTree(root), done = {};
    const go = (id) => {
      if (id === -1) return 0;
      let hl = 0, hr = 0;
      for (let x = id; x !== -1; x = t.nodes[x].l) hl++;
      for (let x = id; x !== -1; x = t.nodes[x].r) hr++;
      if (hl === hr) {
        const c = 2 ** hl - 1;
        const mark = (x) => { if (x !== -1) { done[x] = "ok"; mark(t.nodes[x].l); mark(t.nodes[x].r); } };
        mark(id);
        T.step({ line: 4, key: true, tree: T_(t, { hl: { ...done }, tag: { [id]: c } }), vars: { hl, hr }, msg: `Hai biên cao bằng nhau → cây con đầy, có ${c} nút mà không cần duyệt.` });
        return c;
      }
      T.step({ line: 5, tree: T_(t, { hl: { ...done, [id]: "cur" } }), vars: { hl, hr }, msg: "Hai biên lệch nhau → xét hai cây con." });
      const r = 1 + go(t.nodes[id].l) + go(t.nodes[id].r);
      done[id] = "done";
      return r;
    };
    return go(t.root);
  },
});

register("kth-smallest-element-in-a-bst", {
  input: "root = [5, 3, 6, 2, 4, null, null, 1]\nk = 3",
  expect: 3,
  code: `inorder(node):
  if node == null: return
  inorder(node.left)
  if --k == 0: answer = node.val; return
  inorder(node.right)`,
  run({ root, k }, T) {
    const t = buildTree(root), w = walker(t, T);
    let left = k, ans = null;
    const dfs = (id) => {
      if (id === -1 || ans !== null) return;
      dfs(t.nodes[id].l);
      if (ans !== null) return;
      left--;
      w.seen[id] = left === 0 ? "ok" : "done";
      w.show(4, id, left === 0 ? `Đây là phần tử nhỏ thứ ${k}.` : `Nhỏ thứ ${k - left}: ${t.nodes[id].v}.`, { tone: left === 0 ? "ok" : "cur", key: left === 0, vars: { "k còn lại": left } });
      if (left === 0) { ans = t.nodes[id].v; return; }
      dfs(t.nodes[id].r);
    };
    dfs(t.root);
    return ans;
  },
});

register("lowest-common-ancestor-of-a-binary-tree", {
  input: "root = [3, 5, 1, 6, 2, 0, 8, null, null, 7, 4]\np = 5\nq = 1",
  expect: 3,
  code: `lca(node):
  if node == null or node == p or node == q: return node
  l = lca(node.left), r = lca(node.right)
  if l and r: return node
  return l or r`,
  run({ root, p, q }, T) {
    const t = buildTree(root), w = walker(t, T), tag = {};
    const go = (id) => {
      if (id === -1) return -1;
      const v = t.nodes[id].v;
      if (v === p || v === q) { w.seen[id] = "mark"; tag[id] = "tìm thấy"; w.show(2, id, `${v} là một trong hai nút cần tìm → trả về nó.`, { tag: { ...tag }, tone: "mark" }); return id; }
      w.show(3, id, `Tìm trong hai cây con của ${v}.`, { tag: { ...tag } });
      const l = go(t.nodes[id].l), r = go(t.nodes[id].r);
      if (l !== -1 && r !== -1) { w.seen[id] = "ok"; w.show(4, id, `Hai bên đều tìm thấy → ${v} là tổ tiên chung thấp nhất.`, { tag: { ...tag }, tone: "ok", key: true }); return id; }
      return l !== -1 ? l : r;
    };
    return t.nodes[go(t.root)].v;
  },
});

register("binary-tree-paths", {
  input: "root = [1, 2, 3, null, 5]",
  expect: ["1->2->5", "1->3"],
  code: `dfs(node, path):
  path += node.val
  if leaf: result.add(path); return
  if node.left: dfs(node.left, path + "->")
  if node.right: dfs(node.right, path + "->")`,
  run({ root }, T) {
    const t = buildTree(root), out = [], path = [];
    const dfs = (id) => {
      path.push(id);
      const leaf = t.nodes[id].l === -1 && t.nodes[id].r === -1;
      if (leaf) out.push(path.map((x) => t.nodes[x].v).join("->"));
      T.step({ line: leaf ? 3 : 2, key: leaf, tree: T_(t, { hl: { ...tones(path, "win"), [id]: leaf ? "ok" : "cur" } }), arrays: [A("kết quả", [...out], { small: true, noIndex: true })], msg: leaf ? `Tới lá: "${out[out.length - 1]}".` : `Đi qua ${t.nodes[id].v}.` });
      if (t.nodes[id].l !== -1) dfs(t.nodes[id].l);
      if (t.nodes[id].r !== -1) dfs(t.nodes[id].r);
      path.pop();
    };
    dfs(t.root);
    return out;
  },
});

register("serialize-and-deserialize-binary-tree", {
  input: "root = [1, 2, 3, null, null, 4, 5]",
  expect: "1,2,#,#,3,4,#,#,5,#,#",
  code: `serialize(node):
  if node == null: out.add("#"); return
  out.add(node.val)
  serialize(node.left); serialize(node.right)
deserialize(tokens):
  t = tokens.next()
  if t == "#": return null
  node = new Node(t)
  node.left = deserialize(); node.right = deserialize()`,
  run({ root }, T) {
    const t = buildTree(root), out = [], w = walker(t, T);
    const ser = (id) => {
      if (id === -1) { out.push("#"); w.show(2, -1, 'Nút null → ghi "#".', { out }); return; }
      out.push(t.nodes[id].v);
      w.seen[id] = "ok";
      w.show(3, id, `Ghi ${t.nodes[id].v}.`, { out });
      ser(t.nodes[id].l);
      ser(t.nodes[id].r);
    };
    ser(t.root);
    let p = 0;
    const back = { nodes: [], root: -1 };
    const des = (attach) => {
      const tok = out[p];
      T.step({ line: tok === "#" ? 7 : 8, tree: T_(back), arrays: [A("tokens", out, { small: true, hl: { [p]: "cur" }, ptr: ptrs({ p }) })], msg: tok === "#" ? "Gặp # → nhánh này rỗng." : `Dựng lại nút ${tok}.` });
      p++;
      if (tok === "#") return;
      const id = back.nodes.length;
      back.nodes.push({ v: tok, l: -1, r: -1 });
      if (back.root === -1) back.root = id;
      attach(id);
      des((c) => (back.nodes[id].l = c));
      des((c) => (back.nodes[id].r = c));
    };
    des(() => {});
    T.step({ line: 9, key: true, tree: T_(back), msg: "Cây dựng lại giống hệt cây ban đầu." });
    return out.join(",");
  },
});

register("house-robber-iii", {
  input: "root = [3, 2, 3, null, 3, null, 1]",
  expect: 7,
  code: `rob(node): returns (take, skip)
  if node == null: return (0, 0)
  (lt, ls) = rob(node.left); (rt, rs) = rob(node.right)
  take = node.val + ls + rs
  skip = max(lt, ls) + max(rt, rs)
  return (take, skip)`,
  run({ root }, T) {
    const t = buildTree(root), w = walker(t, T), tag = {};
    const go = (id) => {
      if (id === -1) return [0, 0];
      const [lt, ls] = go(t.nodes[id].l), [rt, rs] = go(t.nodes[id].r);
      const take = t.nodes[id].v + ls + rs, skip = Math.max(lt, ls) + Math.max(rt, rs);
      tag[id] = `${take}/${skip}`;
      w.seen[id] = "done";
      w.show(5, id, `Nút ${t.nodes[id].v}: cướp = ${take}, bỏ qua = ${skip}.`, { tag: { ...tag } });
      return [take, skip];
    };
    const [a, b] = go(t.root);
    return Math.max(a, b);
  },
});

register("sum-of-left-leaves", {
  input: "root = [3, 9, 20, null, null, 15, 7]",
  expect: 24,
  code: `dfs(node, isLeft):
  if node == null: return 0
  if leaf: return isLeft ? node.val : 0
  return dfs(node.left, true) + dfs(node.right, false)`,
  run({ root }, T) {
    const t = buildTree(root), w = walker(t, T);
    let sum = 0;
    const dfs = (id, left) => {
      if (id === -1) return;
      const leaf = t.nodes[id].l === -1 && t.nodes[id].r === -1;
      if (leaf && left) sum += t.nodes[id].v;
      w.seen[id] = leaf && left ? "ok" : "done";
      w.show(leaf ? 3 : 4, id, leaf ? (left ? `${t.nodes[id].v} là lá trái, tổng ${sum}.` : `${t.nodes[id].v} là lá phải, bỏ qua.`) : `Duyệt qua ${t.nodes[id].v}.`, { tone: leaf && left ? "ok" : "cur", key: leaf && left, vars: { sum } });
      dfs(t.nodes[id].l, true);
      dfs(t.nodes[id].r, false);
    };
    dfs(t.root, false);
    return sum;
  },
});

register("minimum-absolute-difference-in-bst", {
  input: "root = [4, 2, 6, 1, 3]",
  expect: 1,
  code: `prev = null, best = inf
inorder(node):
  inorder(node.left)
  if prev != null: best = min(best, node.val - prev)
  prev = node.val
  inorder(node.right)`,
  run({ root }, T) {
    const t = buildTree(root), w = walker(t, T);
    let prev = null, best = Infinity;
    const dfs = (id) => {
      if (id === -1) return;
      dfs(t.nodes[id].l);
      const v = t.nodes[id].v;
      if (prev !== null) best = Math.min(best, v - prev);
      w.seen[id] = "done";
      w.show(4, id, prev === null ? "Nút nhỏ nhất, chưa có gì để so." : `Trung tố tăng dần nên chỉ cần so với số liền trước: ${v} − ${prev} = ${v - prev}.`, { vars: { prev, best: best === Infinity ? "∞" : best } });
      prev = v;
      dfs(t.nodes[id].r);
    };
    dfs(t.root);
    return best;
  },
});

postOrder("diameter-of-binary-tree", "root = [1, 2, 3, 4, 5]", 3, `best = 0
depth(node):
  if node == null: return 0
  l = depth(node.left), r = depth(node.right)
  best = max(best, l + r)
  return 1 + max(l, r)`, 5, 0, (v, l, r, s) => {
  const up = l + r > (s.best ?? 0);
  s.best = Math.max(s.best ?? 0, l + r);
  return { value: 1 + Math.max(l, r), key: up, tone: up ? "ok" : undefined, msg: `Đường đi qua ${v} dài ${l} + ${r} = ${l + r}. Cao ${1 + Math.max(l, r)}.` };
}, (_v, s) => s.best);

register("subtree-of-another-tree", {
  input: "root = [3, 4, 5, 1, 2]\nsubRoot = [4, 1, 2]",
  expect: true,
  code: `isSub(node):
  if node == null: return false
  if same(node, subRoot): return true
  return isSub(node.left) or isSub(node.right)`,
  run({ root, subRoot }, T) {
    const t = buildTree(root), u = buildTree(subRoot);
    const same = (a, b) => (a === -1 || b === -1 ? a === b : t.nodes[a].v === u.nodes[b].v && same(t.nodes[a].l, u.nodes[b].l) && same(t.nodes[a].r, u.nodes[b].r));
    const go = (id) => {
      if (id === -1) return false;
      const ok = same(id, u.root);
      T.step({ line: 3, key: ok, trees: [T_(t, { label: "root", hl: { [id]: ok ? "ok" : "cur" } }), T_(u, { label: "subRoot" })], msg: ok ? `Cây con tại ${t.nodes[id].v} trùng khớp.` : `Cây con tại ${t.nodes[id].v} không khớp.` });
      return ok || go(t.nodes[id].l) || go(t.nodes[id].r);
    };
    return go(t.root);
  },
});

register("n-ary-tree-preorder-traversal", {
  input: "root = [1, [3, [5], [6]], [2], [4]]",
  expect: [1, 3, 5, 6, 2, 4],
  code: `preorder(node):
  visit(node)
  for child in node.children:
    preorder(child)`,
  run({ root }, T) {
    const nodes = [];
    const build = ([v, ...kids]) => {
      const id = nodes.length;
      nodes.push({ v, ch: [] });
      kids.forEach((k) => nodes[id].ch.push(build(k)));
      return id;
    };
    build(root);
    const out = [], hl = {};
    const dfs = (id) => {
      out.push(nodes[id].v);
      hl[id] = "ok";
      T.step({ line: 2, nary: [{ label: "cây", nodes, root: 0, hl: { ...hl, [id]: "cur" } }], arrays: [A("kết quả", [...out], { noIndex: true })], msg: `Ghi ${nodes[id].v} rồi đi lần lượt các con.` });
      nodes[id].ch.forEach(dfs);
    };
    dfs(0);
    return out;
  },
});

register("merge-two-binary-trees", {
  input: "root1 = [1, 3, 2, 5]\nroot2 = [2, 1, 3, null, 4, null, 7]",
  expect: [3, 4, 5, 5, 4, null, 7],
  code: `merge(a, b):
  if a == null: return b
  if b == null: return a
  a.val += b.val
  a.left = merge(a.left, b.left)
  a.right = merge(a.right, b.right)
  return a`,
  run({ root1, root2 }, T) {
    const a = buildTree(root1), b = buildTree(root2);
    const m = { nodes: [], root: -1 };
    const go = (x, y) => {
      if (x === -1 && y === -1) return -1;
      const id = m.nodes.length;
      const v = (x !== -1 ? a.nodes[x].v : 0) + (y !== -1 ? b.nodes[y].v : 0);
      m.nodes.push({ v, l: -1, r: -1 });
      if (m.root === -1) m.root = id;
      m.nodes[id].l = go(x !== -1 ? a.nodes[x].l : -1, y !== -1 ? b.nodes[y].l : -1);
      m.nodes[id].r = go(x !== -1 ? a.nodes[x].r : -1, y !== -1 ? b.nodes[y].r : -1);
      T.step({ line: 4, trees: [T_(a, { label: "root1", hl: x !== -1 ? { [x]: "cur" } : {} }), T_(b, { label: "root2", hl: y !== -1 ? { [y]: "cur" } : {} }), T_(m, { label: "kết quả", hl: { [id]: "ok" } })], msg: x !== -1 && y !== -1 ? `Hai cây đều có nút: ${a.nodes[x].v} + ${b.nodes[y].v} = ${v}.` : `Chỉ một cây có nút: giữ ${v}.` });
      return id;
    };
    go(a.root, b.root);
    return treeToArray(m);
  },
});

register("search-in-a-binary-search-tree", {
  input: "root = [4, 2, 7, 1, 3]\nval = 2",
  expect: [2, 1, 3],
  code: `while node and node.val != val:
  node = val < node.val ? node.left : node.right
return node`,
  run({ root, val }, T) {
    const t = buildTree(root), hl = {};
    let id = t.root;
    while (id !== -1) {
      const v = t.nodes[id].v, hit = v === val;
      T.step({ line: hit ? 3 : 2, key: hit, tree: T_(t, { hl: { ...hl, [id]: hit ? "ok" : "cur" } }), msg: hit ? "Tìm thấy." : val < v ? `${val} < ${v} → sang trái.` : `${val} > ${v} → sang phải.` });
      if (hit) return treeToArray({ nodes: t.nodes, root: id });
      hl[id] = "dim";
      id = val < v ? t.nodes[id].l : t.nodes[id].r;
    }
    T.step({ line: 3, tree: T_(t, { hl }), msg: "Không có trong cây." });
    return [];
  },
});

register("range-sum-of-bst", {
  input: "root = [10, 5, 15, 3, 7, null, 18]\nlow = 7\nhigh = 15",
  expect: 32,
  code: `dfs(node):
  if node == null: return 0
  if node.val < low: return dfs(node.right)
  if node.val > high: return dfs(node.left)
  return node.val + dfs(node.left) + dfs(node.right)`,
  run({ root, low, high }, T) {
    const t = buildTree(root), hl = {};
    let sum = 0;
    const dfs = (id) => {
      if (id === -1) return;
      const v = t.nodes[id].v;
      if (v < low) { hl[id] = "dim"; T.step({ line: 3, tree: T_(t, { hl: { ...hl } }), vars: { sum }, msg: `${v} < ${low}: nhánh trái chắc chắn nhỏ hơn nữa, bỏ.` }); dfs(t.nodes[id].r); return; }
      if (v > high) { hl[id] = "dim"; T.step({ line: 4, tree: T_(t, { hl: { ...hl } }), vars: { sum }, msg: `${v} > ${high}: bỏ nhánh phải.` }); dfs(t.nodes[id].l); return; }
      sum += v;
      hl[id] = "ok";
      T.step({ line: 5, tree: T_(t, { hl: { ...hl } }), vars: { sum }, msg: `${v} nằm trong khoảng, cộng vào tổng.` });
      dfs(t.nodes[id].l);
      dfs(t.nodes[id].r);
    };
    dfs(t.root);
    return sum;
  },
});

register("binary-tree-cameras", {
  input: "root = [0, 0, null, 0, 0]",
  expect: 1,
  code: `// 0: chưa được phủ, 1: được phủ, 2: có camera
dfs(node):
  if node == null: return 1
  l = dfs(node.left), r = dfs(node.right)
  if l == 0 or r == 0: cameras++; return 2
  if l == 2 or r == 2: return 1
  return 0
if dfs(root) == 0: cameras++`,
  run({ root }, T) {
    const t = buildTree(root), hl = {}, tag = {};
    let cams = 0;
    const view = (line, id, msg, key) => T.step({ line, key, tree: T_(t, { hl: { ...hl }, tag: { ...tag } }), vars: { cameras: cams }, msg });
    const go = (id) => {
      if (id === -1) return 1;
      const l = go(t.nodes[id].l), r = go(t.nodes[id].r);
      if (l === 0 || r === 0) { cams++; hl[id] = "ok"; tag[id] = "cam"; view(5, id, "Có con chưa được phủ → đặt camera ở đây.", true); return 2; }
      if (l === 2 || r === 2) { hl[id] = "done"; tag[id] = "phủ"; view(6, id, "Con có camera → nút này được phủ."); return 1; }
      hl[id] = "mark"; tag[id] = "chờ";
      view(7, id, "Chưa ai phủ, để cha lo.");
      return 0;
    };
    if (go(t.root) === 0) { cams++; hl[t.root] = "ok"; tag[t.root] = "cam"; view(8, t.root, "Gốc không có cha nên tự đặt camera.", true); }
    return cams;
  },
});

register("recover-a-tree-from-preorder-traversal", {
  input: 'traversal = "1-2--3--4-5--6--7"',
  expect: [1, 2, 5, 3, 4, 6, 7],
  code: `stack = []
for each (depth, value) in traversal:
  while len(stack) > depth: stack.pop()
  node = new Node(value)
  if stack not empty: attach to stack.top (left, then right)
  stack.push(node)
return stack[0]`,
  run({ traversal }, T) {
    const tokens = [...traversal.matchAll(/(-*)(\d+)/g)].map((m) => [m[1].length, Number(m[2])]);
    const t = { nodes: [], root: -1 }, st = [];
    tokens.forEach(([depth, v]) => {
      while (st.length > depth) st.pop();
      const id = t.nodes.length;
      t.nodes.push({ v, l: -1, r: -1 });
      if (t.root === -1) t.root = id;
      if (st.length) {
        const p = t.nodes[st[st.length - 1]];
        if (p.l === -1) p.l = id; else p.r = id;
      }
      st.push(id);
      T.step({ line: 5, tree: T_(t, { hl: { [id]: "cur", ...(st.length > 1 ? { [st[st.length - 2]]: "mark" } : {}) } }), stacks: [S("đường từ gốc", st.map((x) => t.nodes[x].v))], msg: `Giá trị ${v} ở độ sâu ${depth}: cha là đỉnh ngăn xếp.` });
    });
    return treeToArray(t);
  },
});

