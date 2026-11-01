/* Stacks, monotonic stacks and the "design a data structure" problems. Those take ops = [["name", args...], ...]. */
import { A, G, Heap, L, M, Q, S, chars, ptrs, register, tones } from "./engine.js";

const range = (a, b) => Array.from({ length: Math.max(0, b - a + 1) }, (_, i) => a + i);

register("longest-valid-parentheses", {
  input: 's = ")()())"',
  expect: 4,
  code: `stack = [-1], best = 0
for i in 0..n-1:
  if s[i] == '(': stack.push(i)
  else:
    stack.pop()
    if stack empty: stack.push(i)
    else: best = max(best, i - stack.top)
return best`,
  run({ s }, T) {
    const a = chars(s), st = [-1];
    let best = 0;
    a.forEach((c, i) => {
      if (c === "(") { st.push(i); T.step({ line: 3, arrays: [A("s", a, { hl: { [i]: "cur" }, ptr: ptrs({ i }) })], stacks: [S("stack (vị trí)", [...st])], vars: { best }, msg: "Mở ngoặc: đẩy vị trí." }); return; }
      st.pop();
      if (!st.length) { st.push(i); T.step({ line: 6, arrays: [A("s", a, { hl: { [i]: "bad" }, ptr: ptrs({ i }) })], stacks: [S("stack (vị trí)", [...st])], vars: { best }, msg: "Đóng ngoặc thừa: đây là mốc chặn mới." }); return; }
      const len = i - st[st.length - 1], up = len > best;
      best = Math.max(best, len);
      T.step({ line: 7, key: up, arrays: [A("s", a, { range: [st[st.length - 1] + 1, i], hl: { [i]: "ok" }, ptr: ptrs({ i }) })], stacks: [S("stack (vị trí)", [...st])], vars: { best }, msg: `Đoạn hợp lệ dài ${len}.` });
    });
    return best;
  },
});

register("simplify-path", {
  input: 'path = "/a/./b/../../c/"',
  expect: "/c",
  code: `stack = []
for part in path.split("/"):
  if part == "..": stack.pop()
  else if part not in ("", "."): stack.push(part)
return "/" + join(stack, "/")`,
  run({ path }, T) {
    const parts = path.split("/"), st = [];
    parts.forEach((p, i) => {
      const line = p === ".." ? 3 : 4;
      if (p === "..") st.pop();
      else if (p !== "" && p !== ".") st.push(p);
      T.step({ line, arrays: [A("các phần", parts.map((x) => x || "∅"), { small: true, hl: { [i]: "cur" } })], stacks: [S("stack", [...st])], msg: p === ".." ? "`..` → lên một cấp." : p === "." || p === "" ? "Bỏ qua." : `Vào thư mục ${p}.` });
    });
    return `/${st.join("/")}`;
  },
});

register("evaluate-reverse-polish-notation", {
  input: 'tokens = ["2", "1", "+", "3", "*"]',
  expect: 9,
  code: `stack = []
for t in tokens:
  if t is operator:
    b = stack.pop(); a = stack.pop()
    stack.push(a op b)
  else: stack.push(int(t))
return stack.top`,
  run({ tokens }, T) {
    const st = [], ops = { "+": (a, b) => a + b, "-": (a, b) => a - b, "*": (a, b) => a * b, "/": (a, b) => Math.trunc(a / b) };
    tokens.forEach((t, i) => {
      if (ops[t]) {
        const b = st.pop(), a = st.pop(), r = ops[t](a, b);
        st.push(r);
        T.step({ line: 5, arrays: [A("tokens", tokens, { hl: { [i]: "mark" }, ptr: ptrs({ i }) })], stacks: [S("stack", [...st], { hl: { [st.length - 1]: "ok" } })], msg: `${a} ${t} ${b} = ${r}.` });
      } else {
        st.push(Number(t));
        T.step({ line: 6, arrays: [A("tokens", tokens, { hl: { [i]: "cur" }, ptr: ptrs({ i }) })], stacks: [S("stack", [...st], { hl: { [st.length - 1]: "cur" } })], msg: `Đẩy ${t}.` });
      }
    });
    return st[0];
  },
});

register("min-stack", {
  input: 'ops = [["push", -2], ["push", 0], ["push", -3], ["getMin"], ["pop"], ["top"], ["getMin"]]',
  expect: [null, null, null, -3, null, 0, -2],
  code: `push(x):
  stack.push(x)
  mins.push(min(x, mins.top))
pop(): stack.pop(); mins.pop()
top(): return stack.top
getMin(): return mins.top`,
  run({ ops }, T) {
    const st = [], mins = [], out = [];
    for (const [op, x] of ops) {
      let r = null, line = 2;
      if (op === "push") { st.push(x); mins.push(mins.length ? Math.min(x, mins[mins.length - 1]) : x); line = 3; }
      else if (op === "pop") { st.pop(); mins.pop(); line = 4; }
      else if (op === "top") { r = st[st.length - 1]; line = 5; }
      else { r = mins[mins.length - 1]; line = 6; }
      out.push(r);
      T.step({ line, stacks: [S("stack", [...st], { hl: { [st.length - 1]: "cur" } }), S("mins", [...mins], { hl: { [mins.length - 1]: op === "getMin" ? "ok" : "mark" } })], msg: `${op}(${x ?? ""})${r !== null ? ` → ${r}` : ""}. Mins luôn giữ giá trị nhỏ nhất ứng với mỗi tầng.` });
    }
    return out;
  },
});

register("basic-calculator", {
  input: 's = "(1+(4+5+2)-3)+(6+8)"',
  expect: 23,
  code: `res = 0, sign = 1, stack = []
for c in s:
  if digit: read number x; res += sign * x
  if c in "+-": sign = c == '+' ? 1 : -1
  if c == '(': stack.push(res, sign); res = 0; sign = 1
  if c == ')': res = stack.pop() + stack.pop() * res ...
return res`,
  run({ s }, T) {
    const a = chars(s), st = [];
    let res = 0, sign = 1;
    for (let i = 0; i < a.length; i++) {
      const c = a[i];
      if (c === " ") continue;
      let line, msg;
      if (c >= "0" && c <= "9") {
        const start = i;
        let x = 0;
        while (i < a.length && a[i] >= "0" && a[i] <= "9") x = x * 10 + Number(a[i++]);
        i--;
        res += sign * x;
        line = 3; msg = `Đọc số ${x} (từ vị trí ${start}), res = ${res}.`;
      } else if (c === "+" || c === "-") { sign = c === "+" ? 1 : -1; line = 4; msg = `Dấu ${c}.`; }
      else if (c === "(") { st.push(res, sign); res = 0; sign = 1; line = 5; msg = "Vào ngoặc: cất kết quả tạm và dấu."; }
      else { const sg = st.pop(), prev = st.pop(); res = prev + sg * res; line = 6; msg = `Ra ngoặc: ${prev} ${sg > 0 ? "+" : "−"} giá trị trong ngoặc = ${res}.`; }
      T.step({ line, arrays: [A("s", a, { small: true, hl: { [i]: "cur" }, ptr: ptrs({ i }) })], stacks: [S("stack", [...st])], vars: { res, sign }, msg });
    }
    return res;
  },
});

register("implement-stack-using-queues", {
  input: 'ops = [["push", 1], ["push", 2], ["top"], ["pop"], ["empty"]]',
  expect: [null, null, 2, 2, false],
  code: `push(x):
  q.add(x)
  repeat size - 1 times: q.add(q.remove())
pop(): return q.remove()
top(): return q.front
empty(): return q is empty`,
  run({ ops }, T) {
    const q = [], out = [];
    for (const [op, x] of ops) {
      let r = null, line = 2;
      if (op === "push") {
        q.push(x);
        T.step({ line: 2, stacks: [Q("queue (đầu bên trái)", [...q], { hl: { [q.length - 1]: "cur" } })], msg: `Thêm ${x} vào cuối.` });
        for (let i = 0; i < q.length - 1; i++) { q.push(q.shift()); T.step({ line: 3, stacks: [Q("queue (đầu bên trái)", [...q])], msg: "Xoay phần tử đầu ra cuối để số mới nhất lên đầu." }); }
        out.push(null);
        continue;
      }
      if (op === "pop") { r = q.shift(); line = 4; } else if (op === "top") { r = q[0]; line = 5; } else { r = !q.length; line = 6; }
      out.push(r);
      T.step({ line, stacks: [Q("queue (đầu bên trái)", [...q], { hl: { 0: "ok" } })], msg: `${op}() → ${r}.` });
    }
    return out;
  },
});

register("implement-queue-using-stacks", {
  input: 'ops = [["push", 1], ["push", 2], ["peek"], ["pop"], ["empty"]]',
  expect: [null, null, 1, 1, false],
  code: `push(x): in.push(x)
move(): if out empty:
  while in not empty: out.push(in.pop())
pop(): move(); return out.pop()
peek(): move(); return out.top
empty(): return in and out are empty`,
  run({ ops }, T) {
    const a = [], b = [], res = [];
    const view = (line, msg) => T.step({ line, stacks: [S("in", [...a]), S("out", [...b], { hl: { [b.length - 1]: "ok" } })], msg });
    const move = () => { if (!b.length) while (a.length) { b.push(a.pop()); view(3, "Dồn từ in sang out, thứ tự đảo lại thành hàng đợi."); } };
    for (const [op, x] of ops) {
      let r = null;
      if (op === "push") { a.push(x); view(1, `Đẩy ${x} vào in.`); }
      else if (op === "pop") { move(); r = b.pop(); view(4, `pop() → ${r}.`); }
      else if (op === "peek") { move(); r = b[b.length - 1]; view(5, `peek() → ${r}.`); }
      else { r = !a.length && !b.length; view(6, `empty() → ${r}.`); }
      res.push(r);
    }
    return res;
  },
});

register("decode-string", {
  input: 's = "3[a2[c]]"',
  expect: "accaccacc",
  code: `counts = [], strs = [], cur = "", k = 0
for c in s:
  if digit: k = k * 10 + c
  else if c == '[': counts.push(k); strs.push(cur); k = 0; cur = ""
  else if c == ']': cur = strs.pop() + cur * counts.pop()
  else: cur += c
return cur`,
  run({ s }, T) {
    const a = chars(s), counts = [], strs = [];
    let cur = "", k = 0;
    a.forEach((c, i) => {
      let line, msg;
      if (c >= "0" && c <= "9") { k = k * 10 + Number(c); line = 3; msg = `Số lần lặp k = ${k}.`; }
      else if (c === "[") { counts.push(k); strs.push(cur); k = 0; cur = ""; line = 4; msg = "Vào ngoặc: cất số lần và chuỗi đang có."; }
      else if (c === "]") { const n = counts.pop(); cur = strs.pop() + cur.repeat(n); line = 5; msg = `Ra ngoặc: lặp ${n} lần → "${cur}".`; }
      else { cur += c; line = 6; msg = `Nối '${c}'.`; }
      T.step({ line, key: c === "]", arrays: [A("s", a, { hl: { [i]: "cur" }, ptr: ptrs({ i }) })], stacks: [S("counts", [...counts]), S("strs", strs.map((x) => x || '""'))], vars: { cur, k }, msg });
    });
    return cur;
  },
});

register("next-greater-element-i", {
  input: "nums1 = [4, 1, 2]\nnums2 = [1, 3, 4, 2]",
  expect: [-1, 3, -1],
  code: `stack = [], next = {}
for v in nums2:
  while stack and stack.top < v:
    next[stack.pop()] = v
  stack.push(v)
return [next.get(x, -1) for x in nums1]`,
  run({ nums1, nums2 }, T) {
    const st = [], next = new Map();
    nums2.forEach((v, i) => {
      while (st.length && st[st.length - 1] < v) {
        const x = st.pop();
        next.set(x, v);
        T.step({ line: 4, arrays: [A("nums2", nums2, { hl: { [i]: "cur" }, ptr: ptrs({ i }) })], stacks: [S("stack (giảm dần)", [...st])], maps: [M("số lớn hơn kế tiếp", next, { hl: { [x]: "ok" } })], msg: `${v} là số lớn hơn đầu tiên bên phải ${x}.` });
      }
      st.push(v);
      T.step({ line: 5, arrays: [A("nums2", nums2, { hl: { [i]: "cur" }, ptr: ptrs({ i }) })], stacks: [S("stack (giảm dần)", [...st], { hl: { [st.length - 1]: "cur" } })], maps: [M("số lớn hơn kế tiếp", next)], msg: `Đẩy ${v} chờ số lớn hơn.` });
    });
    const out = nums1.map((x) => next.get(x) ?? -1);
    T.step({ line: 6, key: true, arrays: [A("nums1", nums1), A("kết quả", out)], maps: [M("số lớn hơn kế tiếp", next)], msg: "Tra bảng cho từng số của nums1." });
    return out;
  },
});

register("number-of-atoms", {
  input: 'formula = "Mg(OH)2"',
  expect: "H2MgO2",
  code: `stack = [{}]
scan formula:
  '(' : stack.push({})
  ')' : read k; top = stack.pop(); merge top * k into stack.top
  atom: read name and count; stack.top[name] += count
return sorted atoms of stack[0]`,
  run({ formula }, T) {
    const a = chars(formula), st = [new Map()];
    let i = 0;
    const num = () => { let x = 0; while (a[i] >= "0" && a[i] <= "9") x = x * 10 + Number(a[i++]); return x || 1; };
    const view = (line, msg, at) => T.step({ line, arrays: [A("formula", a, { hl: { [at]: "cur" }, ptr: ptrs({ i: at }) })], maps: st.map((m, d) => M(`tầng ${d}`, m)), msg });
    while (i < a.length) {
      const at = i;
      if (a[i] === "(") { st.push(new Map()); i++; view(3, "Mở ngoặc: tạo tầng đếm mới.", at); }
      else if (a[i] === ")") {
        i++;
        const k = num(), top = st.pop(), base = st[st.length - 1];
        for (const [n, c] of top) base.set(n, (base.get(n) ?? 0) + c * k);
        view(4, `Đóng ngoặc: nhân tầng trên với ${k} rồi gộp xuống.`, at);
      } else {
        let name = a[i++];
        while (a[i] >= "a" && a[i] <= "z") name += a[i++];
        const c = num(), top = st[st.length - 1];
        top.set(name, (top.get(name) ?? 0) + c);
        view(5, `Nguyên tử ${name} × ${c}.`, at);
      }
    }
    return [...st[0]].sort(([x], [y]) => (x < y ? -1 : 1)).map(([n, c]) => n + (c > 1 ? c : "")).join("");
  },
});

register("max-chunks-to-make-sorted-ii", {
  input: "arr = [2, 1, 3, 4, 4]",
  expect: 4,
  code: `stack = []                 // max của mỗi khúc, tăng dần
for v in arr:
  top = max(v, stack.top)
  while stack and stack.top > v: stack.pop()
  stack.push(top)
return len(stack)`,
  run({ arr }, T) {
    const st = [];
    arr.forEach((v, i) => {
      const top = st.length ? Math.max(v, st[st.length - 1]) : v;
      const merged = st.length && st[st.length - 1] > v;
      while (st.length && st[st.length - 1] > v) st.pop();
      st.push(top);
      T.step({ line: 5, key: !merged, arrays: [A("arr", arr, { hl: { [i]: merged ? "mark" : "ok" }, ptr: ptrs({ i }) })], stacks: [S("max các khúc", [...st])], vars: { "số khúc": st.length }, msg: merged ? `${v} nhỏ hơn max khúc trước → gộp các khúc lại.` : `${v} ≥ mọi số trước → mở khúc mới.` });
    });
    return st.length;
  },
});

register("car-fleet", {
  input: "target = 12\nposition = [10, 8, 0, 5, 3]\nspeed = [2, 4, 1, 1, 3]",
  expect: 3,
  code: `sort cars by position desc
fleets = 0, slowest = 0
for car in cars:
  t = (target - car.pos) / car.speed
  if t > slowest:
    fleets++; slowest = t
return fleets`,
  run({ target, position, speed }, T) {
    const cars = position.map((p, i) => [p, speed[i]]).sort((a, b) => b[0] - a[0]);
    let fleets = 0, slow = 0;
    const times = [];
    cars.forEach(([p, s], i) => {
      const t = (target - p) / s, nw = t > slow;
      times.push(Number(t.toFixed(2)));
      if (nw) { fleets++; slow = t; }
      T.step({ line: nw ? 6 : 5, key: nw, arrays: [A("vị trí (gần đích trước)", cars.map((c) => c[0]), { hl: { [i]: nw ? "ok" : "cur" } }), A("thời gian tới đích", [...times], { hl: { [i]: nw ? "ok" : "cur" } })], vars: { fleets, "chậm nhất": Number(slow.toFixed(2)) }, msg: nw ? `Xe tại ${p} tới sau ${t.toFixed(2)}, không kịp nhập đoàn trước → đoàn mới.` : `Xe tại ${p} đuổi kịp đoàn phía trước.` });
    });
    return fleets;
  },
});

register("maximal-rectangle", {
  input: 'matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]',
  expect: 6,
  code: `heights = [0] * n
for row in matrix:
  update heights (cell '1' adds, '0' resets)
  best = max(best, largestRectangle(heights))
return best`,
  run({ matrix }, T) {
    const h = Array(matrix[0].length).fill(0);
    let best = 0;
    const largest = (bars) => {
      const st = [];
      let area = 0, at = null;
      for (let i = 0; i <= bars.length; i++) {
        const cur = i === bars.length ? 0 : bars[i];
        while (st.length && bars[st[st.length - 1]] >= cur) {
          const top = st.pop(), l = st.length ? st[st.length - 1] + 1 : 0, a = bars[top] * (i - l);
          if (a > area) { area = a; at = [l, i - 1]; }
        }
        st.push(i);
      }
      return [area, at];
    };
    matrix.forEach((row, r) => {
      row.forEach((v, c) => (h[c] = v === "1" ? h[c] + 1 : 0));
      const [area, at] = largest(h);
      const up = area > best;
      best = Math.max(best, area);
      T.step({ line: up ? 4 : 3, key: up, grids: [G("matrix", matrix, { hl: Object.fromEntries(row.map((_, c) => [r + "," + c, "cur"])) })], arrays: [A("heights", [...h], { bars: true, hl: at ? tones(range(at[0], at[1]), up ? "ok" : "mark") : {} })], vars: { "lớn nhất ở hàng này": area, best }, msg: `Hàng ${r} thành biểu đồ cột, hình chữ nhật lớn nhất: ${area}.` });
    });
    return best;
  },
});

register("create-maximum-number", {
  input: "nums1 = [3, 4, 6, 5]\nnums2 = [9, 1, 2, 5, 8, 3]\nk = 5",
  expect: [9, 8, 6, 5, 3],
  code: `for i in 0..k:
  a = maxSubsequence(nums1, i)      // ngăn xếp đơn điệu
  b = maxSubsequence(nums2, k - i)
  best = max(best, merge(a, b))
return best`,
  run({ nums1, nums2, k }, T) {
    const pick = (a, c) => {
      const st = [];
      let drop = a.length - c;
      for (const v of a) {
        while (drop && st.length && st[st.length - 1] < v) { st.pop(); drop--; }
        st.push(v);
      }
      return st.slice(0, c);
    };
    const greater = (a, i, b, j) => {
      while (i < a.length && j < b.length && a[i] === b[j]) { i++; j++; }
      return j === b.length || (i < a.length && a[i] > b[j]);
    };
    const merge = (a, b) => {
      const out = [];
      let i = 0, j = 0;
      while (i < a.length || j < b.length) out.push(greater(a, i, b, j) ? a[i++] : b[j++]);
      return out;
    };
    let best = [];
    for (let i = Math.max(0, k - nums2.length); i <= Math.min(k, nums1.length); i++) {
      const x = pick(nums1, i), y = pick(nums2, k - i), m = merge(x, y);
      const up = best.join() === "" || m.join(",").localeCompare(best.join(","), undefined, { numeric: true }) > 0;
      if (up) best = m;
      T.step({ line: 4, key: up, arrays: [A(`${i} số từ nums1`, x, { noIndex: true }), A(`${k - i} số từ nums2`, y, { noIndex: true }), A("trộn lại", m, { noIndex: true, hl: up ? tones(m.map((_, q) => q), "ok") : {} }), A("tốt nhất", best, { noIndex: true })], msg: up ? "Số này lớn hơn kết quả trước." : "Không tốt hơn." });
    }
    return best;
  },
});

/* ---------- caches ---------- */

register("lru-cache", {
  input: 'capacity = 2\nops = [["put", 1, 1], ["put", 2, 2], ["get", 1], ["put", 3, 3], ["get", 2], ["put", 4, 4], ["get", 1], ["get", 3], ["get", 4]]',
  expect: [null, null, 1, null, -1, null, -1, 3, 4],
  code: `get(key):
  if key not in map: return -1
  move node to front
  return node.value
put(key, value):
  update or insert node at front
  if size > capacity: remove last node`,
  run({ capacity, ops }, T) {
    const order = [], map = new Map(), out = [];
    const view = (line, msg, hl = {}, key) => T.step({ line, key, lists: [L("mới → cũ", order.map((k) => `${k}:${map.get(k)}`), { hl })], maps: [M("map", map)], vars: { "kết quả": out }, msg });
    for (const [op, k, v] of ops) {
      if (op === "get") {
        if (!map.has(k)) { out.push(-1); view(2, `get(${k}): không có → −1.`); continue; }
        order.splice(order.indexOf(k), 1);
        order.unshift(k);
        out.push(map.get(k));
        view(3, `get(${k}) = ${map.get(k)}: chuyển lên đầu.`, { 0: "ok" });
      } else {
        if (map.has(k)) order.splice(order.indexOf(k), 1);
        map.set(k, v);
        order.unshift(k);
        out.push(null);
        view(6, `put(${k}, ${v}): đưa lên đầu.`, { 0: "cur" });
        if (order.length > capacity) {
          view(7, `Quá sức chứa ${capacity}: loại nút cuối.`, { [order.length - 1]: "bad" }, true);
          map.delete(order.pop());
          view(7, "Đã loại nút lâu nhất chưa dùng.");
        }
      }
    }
    return out;
  },
});

register("lfu-cache", {
  input: 'capacity = 2\nops = [["put", 1, 1], ["put", 2, 2], ["get", 1], ["put", 3, 3], ["get", 2], ["get", 3], ["put", 4, 4], ["get", 1], ["get", 3], ["get", 4]]',
  expect: [null, null, 1, null, -1, 3, null, -1, 3, 4],
  code: `get(key):
  if missing: return -1
  bump(key)            // chuyển sang nhóm freq + 1
  return value
put(key, value):
  if exists: update; bump(key); return
  if full: evict oldest key of minFreq group
  insert key into group 1; minFreq = 1`,
  run({ capacity, ops }, T) {
    const val = new Map(), freq = new Map(), groups = new Map(), out = [];
    let minF = 0;
    const view = (line, msg, key) => T.step({ line, key, maps: [...[...groups].filter(([, g]) => g.length).sort(([x], [y]) => x - y).map(([f, g]) => M(`nhóm tần suất ${f} (cũ → mới)`, g.map((k) => [k, val.get(k)]))), M("giá trị", val)], vars: { minFreq: minF, "kết quả": out }, msg });
    const bump = (k) => {
      const f = freq.get(k);
      groups.get(f).splice(groups.get(f).indexOf(k), 1);
      if (!groups.get(f).length && f === minF) minF++;
      freq.set(k, f + 1);
      if (!groups.has(f + 1)) groups.set(f + 1, []);
      groups.get(f + 1).push(k);
    };
    for (const [op, k, v] of ops) {
      if (op === "get") {
        if (!val.has(k)) { out.push(-1); view(2, `get(${k}): không có.`); continue; }
        bump(k);
        out.push(val.get(k));
        view(3, `get(${k}) = ${val.get(k)}: tần suất lên ${freq.get(k)}.`);
      } else {
        out.push(null);
        if (val.has(k)) { val.set(k, v); bump(k); view(6, `Cập nhật ${k}.`); continue; }
        if (val.size === capacity) {
          const e = groups.get(minF).shift();
          val.delete(e);
          freq.delete(e);
          view(7, `Đầy: loại ${e}, key cũ nhất trong nhóm tần suất ${minF}.`, true);
        }
        val.set(k, v);
        freq.set(k, 1);
        if (!groups.has(1)) groups.set(1, []);
        groups.get(1).push(k);
        minF = 1;
        view(8, `Thêm ${k} vào nhóm tần suất 1.`);
      }
    }
    return out;
  },
});

/* ---------- tries ---------- */

class Trie {
  constructor() {
    this.nodes = [{ v: "•", ch: [], next: {}, end: false }];
  }
  insert(word) {
    let id = 0;
    const path = [0];
    for (const c of word) {
      if (this.nodes[id].next[c] === undefined) {
        const nid = this.nodes.length;
        this.nodes.push({ v: c, ch: [], next: {}, end: false });
        this.nodes[id].next[c] = nid;
        this.nodes[id].ch.push(nid);
      }
      id = this.nodes[id].next[c];
      path.push(id);
    }
    this.nodes[id].end = true;
    return path;
  }
  view(label, hl = {}) {
    const ends = {};
    this.nodes.forEach((n, i) => { if (n.end) ends[i] = "done"; });
    return { label, nodes: this.nodes.map((n) => ({ v: n.v, ch: n.ch })), root: 0, hl: { ...ends, ...hl } };
  }
}

register("implement-trie-prefix-tree", {
  input: 'ops = [["insert", "apple"], ["search", "apple"], ["search", "app"], ["startsWith", "app"], ["insert", "app"], ["search", "app"]]',
  expect: [null, true, false, true, null, true],
  code: `insert(word):
  walk letters, creating missing children
  mark last node as end
search(word):
  walk letters; fail on a missing child
  return node is end
startsWith(p): walk letters; return true if all exist`,
  run({ ops }, T) {
    const t = new Trie(), out = [];
    for (const [op, w] of ops) {
      if (op === "insert") {
        const path = t.insert(w);
        out.push(null);
        T.step({ line: 2, nary: [t.view("trie", tones(path, "ok"))], msg: `Chèn "${w}".` });
        continue;
      }
      let id = 0;
      const path = [0];
      let miss = false;
      for (const c of w) {
        const nx = t.nodes[id].next[c];
        if (nx === undefined) { miss = true; break; }
        id = nx;
        path.push(id);
        T.step({ line: op === "search" ? 5 : 7, nary: [t.view("trie", tones(path, "cur"))], msg: `Đi theo '${c}'.` });
      }
      const r = !miss && (op === "startsWith" || t.nodes[id].end);
      out.push(r);
      T.step({ line: op === "search" ? 6 : 7, key: true, nary: [t.view("trie", tones(path, r ? "ok" : "bad"))], msg: `${op}("${w}") → ${r}.` });
    }
    return out;
  },
});

register("design-add-and-search-words-data-structure", {
  input: 'ops = [["addWord", "bad"], ["addWord", "dad"], ["addWord", "mad"], ["search", "pad"], ["search", "bad"], ["search", ".ad"], ["search", "b.."]]',
  expect: [null, null, null, false, true, true, true],
  code: `search(word, i, node):
  if i == len(word): return node.end
  if word[i] == '.':
    return any(search(word, i + 1, c) for c in children)
  child = node.next[word[i]]
  return child and search(word, i + 1, child)`,
  run({ ops }, T) {
    const t = new Trie(), out = [];
    for (const [op, w] of ops) {
      if (op === "addWord") { const p = t.insert(w); out.push(null); T.step({ line: 1, nary: [t.view("trie", tones(p, "ok"))], msg: `Thêm "${w}".` }); continue; }
      const path = [];
      const go = (i, id) => {
        path.push(id);
        T.step({ line: i === w.length ? 2 : w[i] === "." ? 4 : 6, nary: [t.view("trie", tones(path, "cur"))], vars: { word: w, i }, msg: i === w.length ? "Hết từ." : w[i] === "." ? "Dấu . thử mọi nhánh." : `Tìm '${w[i]}'.` });
        let ok;
        if (i === w.length) ok = t.nodes[id].end;
        else if (w[i] === ".") ok = t.nodes[id].ch.some((c) => go(i + 1, c));
        else { const nx = t.nodes[id].next[w[i]]; ok = nx !== undefined && go(i + 1, nx); }
        if (!ok) path.pop();
        return ok;
      };
      const r = go(0, 0);
      out.push(r);
      T.step({ line: 2, key: true, nary: [t.view("trie", tones(path, r ? "ok" : "bad"))], msg: `search("${w}") → ${r}.` });
    }
    return out;
  },
});

register("word-search-ii", {
  input: 'board = [["o","a","a","n"],["e","t","a","e"],["i","h","k","r"],["i","f","l","v"]]\nwords = ["oath", "pea", "eat", "rain"]',
  expect: ["oath", "eat"],
  code: `trie = build(words)
for each cell: dfs(cell, trie.root)
dfs(r, c, node):
  node = node.next[board[r][c]] or return
  if node.word: result.add(node.word); node.word = null
  mark cell, dfs 4 neighbours, unmark`,
  run({ board, words }, T) {
    const t = new Trie(), wordAt = new Map(), res = [];
    words.forEach((w) => { const p = t.insert(w); wordAt.set(p[p.length - 1], w); });
    const m = board.length, n = board[0].length, seen = {};
    T.step({ line: 1, grids: [G("board", board)], nary: [t.view("trie")], msg: "Dựng trie từ danh sách từ để cắt nhánh sớm." });
    const dfs = (r, c, id) => {
      if (r < 0 || c < 0 || r >= m || c >= n || seen[r + "," + c]) return;
      const nx = t.nodes[id].next[board[r][c]];
      if (nx === undefined) return;
      seen[r + "," + c] = "cur";
      const w = wordAt.get(nx);
      if (w) { res.push(w); wordAt.delete(nx); }
      T.step({ line: w ? 5 : 4, key: !!w, grids: [G("board", board, { hl: { ...seen } })], nary: [t.view("trie", { [nx]: w ? "ok" : "cur" })], vars: { "kết quả": res }, msg: w ? `Tìm được "${w}".` : `'${board[r][c]}' có trong trie.` });
      for (const [x, y] of [[0, 1], [1, 0], [0, -1], [-1, 0]]) dfs(r + x, c + y, nx);
      delete seen[r + "," + c];
    };
    for (let r = 0; r < m; r++) for (let c = 0; c < n; c++) dfs(r, c, 0);
    return res;
  },
});

register("palindrome-pairs", {
  input: 'words = ["abcd", "dcba", "lls", "s", "sssll"]',
  expect: [[0, 1], [1, 0], [2, 4], [3, 2]],
  code: `index = map word -> position
for each word w, for each split w = a + b:
  if b is a palindrome and reverse(a) in index: add pair
  if a is a palindrome and reverse(b) in index: add pair
return pairs`,
  run({ words }, T) {
    const idx = new Map(words.map((w, i) => [w, i])), seen = new Set(), res = [];
    const pal = (s) => s === [...s].reverse().join("");
    const add = (i, j, msg, line) => {
      const k = `${i},${j}`;
      if (i === j || seen.has(k)) return;
      seen.add(k);
      res.push([i, j]);
      T.step({ line, key: true, arrays: [A("words", words, { small: true, hl: { [i]: "ok", [j]: "ok" } })], vars: { ghép: words[i] + words[j], "kết quả": res }, msg });
    };
    words.forEach((w, i) => {
      T.step({ line: 2, arrays: [A("words", words, { small: true, hl: { [i]: "cur" } })], maps: [M("vị trí", idx)], msg: `Thử mọi cách cắt "${w}".` });
      for (let c = 0; c <= w.length; c++) {
        const a = w.slice(0, c), b = w.slice(c), ra = [...a].reverse().join(""), rb = [...b].reverse().join("");
        if (pal(b) && idx.has(ra)) add(i, idx.get(ra), `"${w}" + "${ra}" đối xứng vì phần "${b}" tự đối xứng.`, 3);
        if (c && pal(a) && idx.has(rb)) add(idx.get(rb), i, `"${rb}" + "${w}" đối xứng vì phần "${a}" tự đối xứng.`, 4);
      }
    });
    return res.sort((x, y) => x[0] - y[0] || x[1] - y[1]);
  },
});

register("prefix-and-suffix-search", {
  input: 'words = ["apple", "ape"]\nops = [["ap", "e"], ["ap", "ple"]]',
  expect: [1, 0],
  code: `for each word at index i:
  for each prefix p, suffix s of word:
    map[p + "#" + s] = i     // chỉ số lớn hơn ghi đè
f(p, s): return map.get(p + "#" + s, -1)`,
  run({ words, ops }, T) {
    const map = new Map();
    words.forEach((w, i) => {
      for (let a = 0; a <= w.length; a++) for (let b = 0; b <= w.length; b++) map.set(`${w.slice(0, a)}#${w.slice(b)}`, i);
      T.step({ line: 3, arrays: [A("words", words, { small: true, hl: { [i]: "cur" } })], vars: { "số khoá": map.size }, msg: `Ghi mọi cặp tiền tố#hậu tố của "${w}".` });
    });
    const out = [];
    for (const [p, s] of ops) {
      const r = map.get(`${p}#${s}`) ?? -1;
      out.push(r);
      T.step({ line: 4, key: true, arrays: [A("words", words, { small: true, hl: r >= 0 ? { [r]: "ok" } : {} })], vars: { khoá: `${p}#${s}`, "kết quả": r }, msg: `f("${p}", "${s}") = ${r}.` });
    }
    return out;
  },
});

register("stream-of-characters", {
  input: 'words = ["cd", "f", "kl"]\nstream = "abcdefkl"',
  expect: [false, false, false, true, false, true, false, true],
  code: `trie = build(reversed words)
query(c):
  history.add(c)
  walk trie from newest char backwards
  return true if an end node is reached`,
  run({ words, stream }, T) {
    const t = new Trie();
    words.forEach((w) => t.insert([...w].reverse().join("")));
    const out = [], hist = [];
    T.step({ line: 1, nary: [t.view("trie từ đảo")], msg: "Chèn các từ đã đảo: ta sẽ so ngược từ ký tự mới nhất." });
    for (const c of stream) {
      hist.push(c);
      let id = 0, hit = false;
      const path = [0];
      for (let i = hist.length - 1; i >= 0; i--) {
        const nx = t.nodes[id].next[hist[i]];
        if (nx === undefined) break;
        id = nx;
        path.push(id);
        if (t.nodes[id].end) { hit = true; break; }
      }
      out.push(hit);
      T.step({ line: 4, key: hit, arrays: [A("luồng", [...hist], { small: true, hl: { [hist.length - 1]: hit ? "ok" : "cur" } })], nary: [t.view("trie từ đảo", tones(path, hit ? "ok" : "cur"))], msg: hit ? `Thêm '${c}' thì có hậu tố là một từ.` : `Thêm '${c}': không khớp từ nào.` });
    }
    return out;
  },
});

/* ---------- other designs ---------- */

register("find-median-from-data-stream", {
  input: 'ops = [["addNum", 1], ["addNum", 2], ["findMedian"], ["addNum", 3], ["findMedian"]]',
  expect: [null, null, 1.5, null, 2],
  code: `addNum(x):
  low.push(x)                 // max-heap, nửa nhỏ
  high.push(low.pop())        // min-heap, nửa lớn
  if len(high) > len(low): low.push(high.pop())
findMedian():
  return len(low) > len(high) ? low.top : (low.top + high.top) / 2`,
  run({ ops }, T) {
    const low = new Heap((a, b) => a > b), high = new Heap(), out = [];
    const view = (line, msg) => T.step({ line, trees: [low.tree("low (max-heap)", undefined, { 0: "mark" }), high.tree("high (min-heap)", undefined, { 0: "mark" })], msg });
    for (const [op, x] of ops) {
      if (op === "addNum") {
        low.push(x);
        high.push(low.pop());
        view(3, `Cho ${x} qua low rồi sang high để hai nửa không chồng nhau.`);
        if (high.size > low.size) { low.push(high.pop()); view(4, "Cân bằng lại, low được nhiều hơn tối đa một phần tử."); }
        out.push(null);
      } else {
        const m = low.size > high.size ? low.peek() : (low.peek() + high.peek()) / 2;
        out.push(m);
        view(6, `Trung vị: ${m}.`);
      }
    }
    return out;
  },
});

register("range-sum-query-immutable", {
  input: "nums = [-2, 0, 3, -5, 2, -1]\nops = [[0, 2], [2, 5], [0, 5]]",
  expect: [1, -1, -3],
  code: `prefix[0] = 0
for i in 0..n-1: prefix[i+1] = prefix[i] + nums[i]
sumRange(l, r): return prefix[r+1] - prefix[l]`,
  run({ nums, ops }, T) {
    const p = [0];
    nums.forEach((v, i) => {
      p.push(p[i] + v);
      T.step({ line: 2, arrays: [A("nums", nums, { hl: { [i]: "cur" } }), A("prefix", [...p], { hl: { [i + 1]: "ok", [i]: "mark" } })], msg: `prefix[${i + 1}] = ${p[i]} + ${v} = ${p[i + 1]}.` });
    });
    const out = [];
    for (const [l, r] of ops) {
      const s = p[r + 1] - p[l];
      out.push(s);
      T.step({ line: 3, key: true, arrays: [A("nums", nums, { range: [l, r] }), A("prefix", p, { hl: { [r + 1]: "ok", [l]: "bad" } })], vars: { "tổng": s }, msg: `sum(${l}, ${r}) = ${p[r + 1]} − ${p[l]} = ${s}.` });
    }
    return out;
  },
});

register("data-stream-as-disjoint-intervals", {
  input: 'ops = [["addNum", 1], ["addNum", 3], ["addNum", 7], ["addNum", 2], ["addNum", 6], ["getIntervals"]]',
  expect: [null, null, null, null, null, [[1, 3], [6, 7]]],
  code: `addNum(x):
  find intervals touching x - 1 and x + 1
  if both: merge them with x
  else if one: extend it
  else if x not covered: insert [x, x]`,
  run({ ops }, T) {
    const iv = [], out = [];
    for (const [op, x] of ops) {
      if (op === "getIntervals") { out.push(iv.map((v) => [...v])); T.step({ line: 1, key: true, intervals: [{ label: "các khoảng", items: iv }], msg: `Các khoảng: ${JSON.stringify(iv)}.` }); continue; }
      out.push(null);
      if (iv.some(([a, b]) => a <= x && x <= b)) { T.step({ line: 5, intervals: [{ label: "các khoảng", items: iv, cursor: x }], msg: `${x} đã nằm trong một khoảng.` }); continue; }
      const L_ = iv.findIndex((v) => v[1] === x - 1), R = iv.findIndex((v) => v[0] === x + 1);
      let line;
      if (L_ >= 0 && R >= 0) { iv[L_][1] = iv[R][1]; iv.splice(R, 1); line = 3; }
      else if (L_ >= 0) { iv[L_][1] = x; line = 4; }
      else if (R >= 0) { iv[R][0] = x; line = 4; }
      else { iv.push([x, x]); iv.sort((a, b) => a[0] - b[0]); line = 5; }
      T.step({ line, intervals: [{ label: "các khoảng", items: iv, cursor: x, hl: { [iv.findIndex((v) => v[0] <= x && x <= v[1])]: "ok" } }], msg: line === 3 ? `${x} nối liền hai khoảng.` : line === 4 ? `${x} kéo dài khoảng kề.` : `${x} tạo khoảng mới.` });
    }
    return out;
  },
});

register("insert-delete-getrandom-o1-duplicates-allowed", {
  input: 'ops = [["insert", 1], ["insert", 1], ["insert", 2], ["remove", 1], ["contains", 1]]',
  expect: [true, false, true, true, true],
  code: `insert(x):
  idx[x].add(len(list)); list.add(x)
remove(x):
  i = any index of x, last = list.last
  list[i] = last; idx[last] replace (len - 1) with i
  list.removeLast()
getRandom(): return list[random index]`,
  run({ ops }, T) {
    const list = [], idx = new Map(), out = [];
    const view = (line, msg, hl = {}) => T.step({ line, arrays: [A("list", [...list], { hl })], maps: [M("idx", [...idx].map(([k, s]) => [k, [...s]]))], vars: { "kết quả": out }, msg });
    for (const [op, x] of ops) {
      if (op === "contains") { const r = !!idx.get(x)?.size; out.push(r); view(7, `${x} ${r ? "có" : "không có"} trong tập.`); continue; }
      if (op === "insert") {
        const fresh = !idx.get(x)?.size;
        if (!idx.has(x)) idx.set(x, new Set());
        idx.get(x).add(list.length);
        list.push(x);
        out.push(fresh);
        view(2, `Thêm ${x} vào cuối list.`, { [list.length - 1]: "ok" });
      } else {
        const s = idx.get(x);
        if (!s?.size) { out.push(false); view(3, `${x} không có.`); continue; }
        const i = s.values().next().value, last = list[list.length - 1], end = list.length - 1;
        view(4, `Lấy vị trí ${i} của ${x}, đổi với phần tử cuối để xoá O(1).`, { [i]: "bad", [end]: "mark" });
        list[i] = last;
        idx.get(last).add(i);
        idx.get(last).delete(end);
        s.delete(i);
        list.pop();
        out.push(true);
        view(6, "Đã xoá ở cuối list.", i < list.length ? { [i]: "ok" } : {});
      }
    }
    return out;
  },
});

register("all-oone-data-structure", {
  input: 'ops = [["inc", "hello"], ["inc", "hello"], ["inc", "leet"], ["getMaxKey"], ["getMinKey"], ["dec", "hello"]]',
  expect: [null, null, null, "hello", "leet", null],
  code: `buckets = doubly linked list of (count, keys), sorted
inc(key): move key to bucket count + 1 (create if missing)
dec(key): move key to bucket count - 1 (drop at 0)
getMaxKey(): any key of last bucket
getMinKey(): any key of first bucket`,
  run({ ops }, T) {
    const count = new Map(), out = [];
    const buckets = () => {
      const m = new Map();
      for (const [k, c] of count) m.set(c, [...(m.get(c) ?? []), k]);
      return [...m].sort(([a], [b]) => a - b);
    };
    const view = (line, msg, hl = {}) => T.step({ line, lists: [L("các nhóm (đếm tăng dần)", buckets().map(([c, ks]) => `${c}: ${ks.join(",")}`), { hl })], vars: { "kết quả": out }, msg });
    for (const [op, k] of ops) {
      if (op === "inc") { count.set(k, (count.get(k) ?? 0) + 1); out.push(null); view(2, `${k} lên ${count.get(k)}.`); }
      else if (op === "dec") { const c = count.get(k) - 1; if (c) count.set(k, c); else count.delete(k); out.push(null); view(3, `${k} giảm còn ${c}.`); }
      else {
        const b = buckets(), max = op === "getMaxKey";
        const pick = b.length ? (max ? b[b.length - 1] : b[0])[1][0] : "";
        out.push(pick);
        view(max ? 4 : 5, `${op}() → ${pick}.`, { [max ? b.length - 1 : 0]: "ok" });
      }
    }
    return out;
  },
});

register("kth-largest-element-in-a-stream", {
  input: 'k = 3\nnums = [4, 5, 8, 2]\nops = [3, 5, 10, 9, 4]',
  expect: [4, 5, 5, 8, 8],
  code: `heap = min-heap of size k
add(x):
  heap.push(x)
  if len(heap) > k: heap.pop()
  return heap.top`,
  run({ k, nums, ops }, T) {
    const h = new Heap(), out = [];
    const add = (x) => { h.push(x); if (h.size > k) h.pop(); return h.peek(); };
    nums.forEach((x) => add(x));
    T.step({ line: 1, trees: [h.tree(`min-heap, tối đa ${k} phần tử`, undefined, { 0: "ok" })], msg: `Heap giữ ${k} số lớn nhất, đỉnh là số lớn thứ ${k}.` });
    for (const x of ops) {
      const r = add(x);
      out.push(r);
      T.step({ line: 5, tree: h.tree(`min-heap, tối đa ${k} phần tử`, undefined, { 0: "ok" }), arrays: [A("kết quả", [...out], { small: true, noIndex: true })], msg: `Thêm ${x} → lớn thứ ${k} là ${r}.` });
    }
    return out;
  },
});

function hashDesign(slug, input, expect, code, isMap) {
  register(slug, {
    input, expect, code,
    run({ ops }, T) {
      const size = 5, buckets = Array.from({ length: size }, () => []), out = [];
      const view = (line, b, msg) => T.step({ line, grids: [G("các bucket (key % 5)", buckets.map((x) => { const r = x.map((e) => (isMap ? `${e[0]}:${e[1]}` : e[0])); return [...r, ...Array(Math.max(0, 3 - r.length)).fill(null)]; }), { rowHead: range(0, size - 1), hl: Object.fromEntries(range(0, 2).map((c) => [b + "," + c, "win"])) })], vars: { "kết quả": out }, msg });
      for (const [op, k, v] of ops) {
        const b = buckets[k % size], at = b.findIndex((e) => e[0] === k);
        if (op === "add" || op === "put") { if (at >= 0) b[at][1] = v; else b.push([k, v]); out.push(null); view(3, k % size, `${op}(${k}): vào bucket ${k % size}.`); }
        else if (op === "remove") { if (at >= 0) b.splice(at, 1); out.push(null); view(4, k % size, `remove(${k}).`); }
        else { const r = op === "contains" ? at >= 0 : at >= 0 ? b[at][1] : -1; out.push(r); view(5, k % size, `${op}(${k}) → ${r}.`); }
      }
      return out;
    },
  });
}

hashDesign("design-hashset", 'ops = [["add", 1], ["add", 2], ["contains", 1], ["contains", 3], ["add", 2], ["contains", 2], ["remove", 2], ["contains", 2]]', [null, null, true, false, null, true, null, false], `bucket(key) = table[key % size]
each bucket is a list of keys
add(key): append if not in bucket
remove(key): delete from bucket
contains(key): scan bucket`, false);

hashDesign("design-hashmap", 'ops = [["put", 1, 1], ["put", 2, 2], ["get", 1], ["get", 3], ["put", 2, 1], ["get", 2], ["remove", 2], ["get", 2]]', [null, null, 1, -1, null, 1, null, -1], `bucket(key) = table[key % size]
each bucket is a list of (key, value)
put(key, value): update or append
remove(key): delete from bucket
get(key): scan bucket, -1 if missing`, true);

register("range-module", {
  input: 'ops = [["addRange", 10, 20], ["removeRange", 14, 16], ["queryRange", 10, 14], ["queryRange", 13, 15], ["queryRange", 16, 17]]',
  expect: [null, null, true, false, true],
  code: `keep sorted disjoint intervals
addRange(l, r): merge everything overlapping [l, r)
removeRange(l, r): cut [l, r) out of overlapping ones
queryRange(l, r): one interval must cover [l, r)`,
  run({ ops }, T) {
    let iv = [];
    const out = [];
    const view = (line, msg, cursor, key) => T.step({ line, key, intervals: [{ label: "vùng đang theo dõi", items: iv, cursor, axis: [0, 25] }], vars: { "kết quả": out }, msg });
    for (const [op, l, r] of ops) {
      if (op === "addRange") {
        const next = [];
        let a = l, b = r;
        for (const [s, e] of iv) { if (e < a || s > b) next.push([s, e]); else { a = Math.min(a, s); b = Math.max(b, e); } }
        iv = [...next, [a, b]].sort((x, y) => x[0] - y[0]);
        out.push(null);
        view(2, `Thêm [${l}, ${r}), gộp mọi khoảng chồng lấn.`, l);
      } else if (op === "removeRange") {
        const next = [];
        for (const [s, e] of iv) {
          if (e <= l || s >= r) { next.push([s, e]); continue; }
          if (s < l) next.push([s, l]);
          if (e > r) next.push([r, e]);
        }
        iv = next;
        out.push(null);
        view(3, `Cắt bỏ [${l}, ${r}).`, l);
      } else {
        const ok = iv.some(([s, e]) => s <= l && r <= e);
        out.push(ok);
        view(4, `Hỏi [${l}, ${r}): ${ok ? "được phủ" : "không được phủ"}.`, l, true);
      }
    }
    return out;
  },
});

register("time-based-key-value-store", {
  input: 'ops = [["set", "foo", "bar", 1], ["get", "foo", 1], ["get", "foo", 3], ["set", "foo", "bar2", 4], ["get", "foo", 4], ["get", "foo", 5]]',
  expect: [null, "bar", "bar", null, "bar2", "bar2"],
  code: `set(k, v, t): store[k].append((t, v))
get(k, t):
  list = store[k]
  i = last index with time <= t (binary search)
  return i >= 0 ? list[i].value : ""`,
  run({ ops }, T) {
    const store = new Map(), out = [];
    for (const op of ops) {
      if (op[0] === "set") {
        const [, k, v, t] = op;
        if (!store.has(k)) store.set(k, []);
        store.get(k).push([t, v]);
        out.push(null);
        T.step({ line: 1, arrays: [A(`store[${k}]`, store.get(k).map(([x, y]) => `${x}: ${y}`), { small: true })], msg: `Lưu ${v} tại thời điểm ${t}.` });
      } else {
        const [, k, t] = op, list = store.get(k) ?? [];
        let lo = 0, hi = list.length - 1, at = -1;
        while (lo <= hi) {
          const m = (lo + hi) >> 1;
          T.step({ line: 4, arrays: [A(`store[${k}]`, list.map(([x, y]) => `${x}: ${y}`), { small: true, range: [lo, hi], hl: { [m]: "cur" } })], msg: `Thời điểm ${list[m][0]} ${list[m][0] <= t ? `≤ ${t} → thử muộn hơn` : `> ${t} → thử sớm hơn`}.` });
          if (list[m][0] <= t) { at = m; lo = m + 1; } else hi = m - 1;
        }
        const r = at >= 0 ? list[at][1] : "";
        out.push(r);
        T.step({ line: 5, key: true, arrays: [A(`store[${k}]`, list.map(([x, y]) => `${x}: ${y}`), { small: true, hl: at >= 0 ? { [at]: "ok" } : {} })], msg: `get(${t}) → "${r}".` });
      }
    }
    return out;
  },
});

