/* The classic interview set: the problems most readers open first. */
import { A, DIRS, G, Heap, L, M, Q, RecTree, S, buildTree, chars, clone, matrix, ptrs, register, tones, treeToArray } from "./engine.js";

const range = (a, b) => Array.from({ length: Math.max(0, b - a + 1) }, (_, i) => a + i);
const T_ = (t, o = {}) => ({ ...t, ...o });

register("two-sum", {
  input: "nums = [2, 7, 11, 15]\ntarget = 9",
  expect: [0, 1],
  presets: [{ label: "Cặp ở cuối", input: "nums = [3, 2, 4]\ntarget = 6", expect: [1, 2] }, { label: "Số trùng", input: "nums = [3, 3]\ntarget = 6", expect: [0, 1] }],
  code: `map = {}
for i in 0..n-1:
  need = target - nums[i]
  if need in map: return [map[need], i]
  map[nums[i]] = i
return []`,
  run({ nums, target }, T) {
    const map = new Map();
    for (let i = 0; i < nums.length; i++) {
      const need = target - nums[i], hit = map.has(need);
      T.step({ line: 3, arrays: [A("nums", nums, { hl: { [i]: "cur" }, ptr: ptrs({ i }) })], maps: [M("giá trị → vị trí", map, { hl: { [need]: "mark" } })], vars: { need }, msg: `Cần ${target} − ${nums[i]} = ${need}. Đã gặp số này chưa?` });
      if (hit) {
        const j = map.get(need);
        T.step({ line: 4, key: true, arrays: [A("nums", nums, { hl: { [i]: "ok", [j]: "ok" }, ptr: ptrs({ i, j }) })], maps: [M("giá trị → vị trí", map, { hl: { [need]: "ok" } })], vars: { need }, msg: `Có, ${need} ở vị trí ${j} → đáp án [${j}, ${i}].` });
        return [j, i];
      }
      map.set(nums[i], i);
      T.step({ line: 5, arrays: [A("nums", nums, { hl: { [i]: "done" }, ptr: ptrs({ i }) })], maps: [M("giá trị → vị trí", map, { hl: { [nums[i]]: "ok" } })], vars: { need }, msg: `Chưa. Lưu ${nums[i]} → ${i} để các số sau tra lại.` });
    }
    return [];
  },
});

register("binary-search", {
  input: "nums = [-1, 0, 3, 5, 9, 12, 15, 20]\ntarget = 12",
  expect: 5,
  presets: [{ label: "Không có", input: "nums = [-1, 0, 3, 5, 9, 12]\ntarget = 2", expect: -1 }],
  code: `lo = 0, hi = n - 1
while lo <= hi:
  mid = (lo + hi) / 2
  if nums[mid] == target: return mid
  if nums[mid] < target: lo = mid + 1
  else: hi = mid - 1
return -1`,
  run({ nums, target }, T) {
    let lo = 0, hi = nums.length - 1;
    while (lo <= hi) {
      const mid = (lo + hi) >> 1, hit = nums[mid] === target;
      T.step({ line: hit ? 4 : nums[mid] < target ? 5 : 6, key: hit, arrays: [A("nums", nums, { range: [lo, hi], dimOutside: true, hl: { [mid]: hit ? "ok" : "cur" }, ptr: ptrs({ lo, mid, hi }) })], vars: { lo, mid, hi }, msg: hit ? `nums[${mid}] = ${target}: tìm thấy.` : nums[mid] < target ? `${nums[mid]} < ${target} → bỏ nửa trái.` : `${nums[mid]} > ${target} → bỏ nửa phải.` });
      if (hit) return mid;
      if (nums[mid] < target) lo = mid + 1; else hi = mid - 1;
    }
    T.step({ line: 7, arrays: [A("nums", nums, { dimOutside: true, range: [lo, hi] })], msg: "Khoảng tìm rỗng → không có." });
    return -1;
  },
});

register("valid-palindrome", {
  input: 's = "A man, a plan, a canal: Panama"',
  expect: true,
  code: `l = 0, r = n - 1
while l < r:
  while l < r and s[l] not alnum: l++
  while l < r and s[r] not alnum: r--
  if lower(s[l]) != lower(s[r]): return false
  l++, r--
return true`,
  run({ s }, T) {
    const c = chars(s), ok = (x) => /[a-z0-9]/i.test(x), hl = {};
    let l = 0, r = c.length - 1;
    while (l < r) {
      if (!ok(c[l])) { hl[l++] = "dim"; continue; }
      if (!ok(c[r])) { hl[r--] = "dim"; continue; }
      const same = c[l].toLowerCase() === c[r].toLowerCase();
      T.step({ line: 5, key: !same, arrays: [A("s", c, { small: true, hl: { ...hl, [l]: same ? "ok" : "bad", [r]: same ? "ok" : "bad" }, ptr: ptrs({ l, r }) })], msg: same ? `'${c[l]}' và '${c[r]}' giống nhau.` : `'${c[l]}' ≠ '${c[r]}'.` });
      if (!same) return false;
      hl[l] = hl[r] = "done";
      l++; r--;
    }
    T.step({ line: 7, key: true, arrays: [A("s", c, { small: true, hl })], msg: "Hai con trỏ gặp nhau → đối xứng." });
    return true;
  },
});

register("best-time-to-buy-and-sell-stock", {
  input: "prices = [7, 1, 5, 3, 6, 4]",
  expect: 5,
  code: `low = prices[0], best = 0
for p in prices:
  low = min(low, p)
  best = max(best, p - low)
return best`,
  run({ prices }, T) {
    let low = 0, best = 0, pair = null;
    for (let i = 1; i < prices.length; i++) {
      const profit = prices[i] - prices[low], up = profit > best;
      if (up) { best = profit; pair = [low, i]; }
      T.step({ line: 4, key: up, arrays: [A("prices", prices, { bars: true, hl: { [low]: "ok", [i]: profit > 0 ? "cur" : "bad" }, ptr: ptrs({ mua: low, bán: i }) })], vars: { "giá thấp nhất": prices[low], "lời nếu bán": profit, best }, msg: `Bán ngày ${i} giá ${prices[i]}, mua ở đáy ${prices[low]} → lời ${profit}.` });
      if (prices[i] < prices[low]) { low = i; T.step({ line: 3, arrays: [A("prices", prices, { bars: true, hl: { [i]: "ok" }, ptr: ptrs({ "đáy mới": i }) })], vars: { "giá thấp nhất": prices[low], best }, msg: `${prices[i]} là đáy mới.` }); }
    }
    if (pair) T.step({ line: 5, key: true, arrays: [A("prices", prices, { bars: true, hl: tones(pair, "ok"), ptr: ptrs({ mua: pair[0], bán: pair[1] }) })], vars: { best }, msg: `Lời lớn nhất ${best}.` });
    return best;
  },
});

register("container-with-most-water", {
  input: "height = [1, 8, 6, 2, 5, 4, 8, 3, 7]",
  expect: 49,
  code: `l = 0, r = n - 1, best = 0
while l < r:
  best = max(best, min(h[l], h[r]) * (r - l))
  if h[l] < h[r]: l++
  else: r--
return best`,
  run({ height }, T) {
    let l = 0, r = height.length - 1, best = 0;
    while (l < r) {
      const area = Math.min(height[l], height[r]) * (r - l), up = area > best;
      best = Math.max(best, area);
      const left = height[l] < height[r];
      T.step({ line: 3, key: up, arrays: [A("height", height, { bars: true, range: [l, r], hl: { [l]: "cur", [r]: "cur" }, ptr: ptrs({ l, r }) })], vars: { "diện tích": `min(${height[l]}, ${height[r]}) × ${r - l} = ${area}`, best }, msg: `Cột thấp hơn quyết định mực nước, dời nó thì mới có cơ hội tốt hơn → dời ${left ? "l" : "r"}.` });
      if (left) l++; else r--;
    }
    return best;
  },
});

register("3sum", {
  input: "nums = [-1, 0, 1, 2, -1, -4]",
  expect: [[-1, -1, 2], [-1, 0, 1]],
  code: `sort(nums)
for i in 0..n-3:
  if i > 0 and nums[i] == nums[i-1]: continue
  l = i + 1, r = n - 1
  while l < r:
    sum = nums[i] + nums[l] + nums[r]
    if sum == 0: add; skip duplicates; l++, r--
    else if sum < 0: l++
    else: r--`,
  run({ nums }, T) {
    const a = [...nums].sort((x, y) => x - y), res = [];
    T.step({ line: 1, arrays: [A("nums (đã sắp xếp)", a)], msg: "Sắp xếp trước để dùng hai con trỏ và bỏ trùng dễ dàng." });
    for (let i = 0; i < a.length - 2; i++) {
      if (i && a[i] === a[i - 1]) { T.step({ line: 3, arrays: [A("nums", a, { hl: { [i]: "dim" }, ptr: ptrs({ i }) })], vars: { "kết quả": res }, msg: `${a[i]} trùng số trước → bỏ qua.` }); continue; }
      let l = i + 1, r = a.length - 1;
      while (l < r) {
        const s = a[i] + a[l] + a[r], hit = s === 0;
        T.step({ line: hit ? 7 : s < 0 ? 8 : 9, key: hit, arrays: [A("nums", a, { range: [l, r], hl: { [i]: "mark", [l]: hit ? "ok" : "cur", [r]: hit ? "ok" : "cur" }, ptr: ptrs({ i, l, r }) })], vars: { sum: s, "kết quả": res }, msg: hit ? `${a[i]} + ${a[l]} + ${a[r]} = 0 ✓` : s < 0 ? `Tổng ${s} < 0 → tăng l.` : `Tổng ${s} > 0 → giảm r.` });
        if (hit) {
          res.push([a[i], a[l], a[r]]);
          while (l < r && a[l] === a[l + 1]) l++;
          while (l < r && a[r] === a[r - 1]) r--;
          l++; r--;
        } else if (s < 0) l++; else r--;
      }
    }
    return res;
  },
});

register("longest-substring-without-repeating-characters", {
  input: 's = "abcabcbb"',
  expect: 3,
  presets: [{ label: "Toàn một ký tự", input: 's = "bbbbb"', expect: 1 }, { label: "pwwkew", input: 's = "pwwkew"', expect: 3 }],
  code: `last = {}, l = 0, best = 0
for r in 0..n-1:
  if s[r] in last and last[s[r]] >= l:
    l = last[s[r]] + 1
  last[s[r]] = r
  best = max(best, r - l + 1)
return best`,
  run({ s }, T) {
    const c = chars(s), last = new Map();
    let l = 0, best = 0, range0 = [0, -1];
    for (let r = 0; r < c.length; r++) {
      if (last.has(c[r]) && last.get(c[r]) >= l) {
        const p = last.get(c[r]);
        T.step({ line: 4, arrays: [A("s", c, { range: [l, r - 1], hl: { [r]: "bad", [p]: "bad" }, ptr: ptrs({ l, r }) })], maps: [M("vị trí cuối", last, { hl: { [c[r]]: "bad" } })], vars: { best }, msg: `'${c[r]}' đã có trong cửa sổ ở ${p} → nhảy l tới ${p + 1}.` });
        l = p + 1;
      }
      last.set(c[r], r);
      const up = r - l + 1 > best;
      if (up) { best = r - l + 1; range0 = [l, r]; }
      T.step({ line: 6, key: up, arrays: [A("s", c, { range: [l, r], hl: { [r]: "cur" }, ptr: ptrs({ l, r }) })], maps: [M("vị trí cuối", last, { hl: { [c[r]]: "ok" } })], vars: { "cửa sổ": c.slice(l, r + 1).join(""), best }, msg: `Cửa sổ dài ${r - l + 1}.` });
    }
    T.step({ line: 7, arrays: [A("s", c, { range: range0 })], vars: { best }, msg: `Dài nhất ${best}.` });
    return best;
  },
});

register("minimum-window-substring", {
  input: 's = "ADOBECODEBANC"\nt = "ABC"',
  expect: "BANC",
  code: `need = frequency(t), missing = len(t), l = 0
for r in 0..n-1:
  if need[s[r]] > 0: missing--
  need[s[r]]--
  while missing == 0:
    update best with [l, r]
    need[s[l]]++
    if need[s[l]] > 0: missing++
    l++
return best`,
  run({ s, t }, T) {
    const c = chars(s), need = new Map();
    for (const ch of t) need.set(ch, (need.get(ch) ?? 0) + 1);
    const show = () => M("cần (âm = dư)", [...need].filter(([k]) => t.includes(k)));
    let missing = t.length, l = 0, best = [0, Infinity];
    for (let r = 0; r < c.length; r++) {
      if ((need.get(c[r]) ?? 0) > 0) missing--;
      need.set(c[r], (need.get(c[r]) ?? 0) - 1);
      T.step({ line: 4, arrays: [A("s", c, { small: true, range: [l, r], hl: t.includes(c[r]) ? { [r]: "cur" } : {}, ptr: ptrs({ l, r }) })], maps: [show()], vars: { "còn thiếu": missing }, msg: `Mở rộng, thêm '${c[r]}'.` });
      while (missing === 0) {
        const up = r - l < best[1] - best[0];
        if (up) best = [l, r];
        T.step({ line: 6, key: up, arrays: [A("s", c, { small: true, range: [l, r], hl: { [l]: "ok", [r]: "ok" }, ptr: ptrs({ l, r }) })], maps: [show()], vars: { "tốt nhất": s.slice(best[0], best[1] + 1) }, msg: `"${s.slice(l, r + 1)}" đủ chữ của t → thu hẹp từ trái.` });
        need.set(c[l], need.get(c[l]) + 1);
        if (need.get(c[l]) > 0) missing++;
        l++;
      }
    }
    return best[1] === Infinity ? "" : s.slice(best[0], best[1] + 1);
  },
});

register("maximum-subarray", {
  input: "nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4]",
  expect: 6,
  code: `cur = best = nums[0]
for i in 1..n-1:
  cur = max(nums[i], cur + nums[i])
  best = max(best, cur)
return best`,
  run({ nums }, T) {
    let cur = nums[0], best = nums[0], st = 0, bestR = [0, 0];
    T.step({ line: 1, arrays: [A("nums", nums, { range: [0, 0], ptr: ptrs({ i: 0 }) })], vars: { cur, best }, msg: "Bắt đầu với phần tử đầu." });
    for (let i = 1; i < nums.length; i++) {
      const restart = nums[i] > cur + nums[i];
      if (restart) { cur = nums[i]; st = i; } else cur += nums[i];
      const up = cur > best;
      if (up) { best = cur; bestR = [st, i]; }
      T.step({ line: up ? 4 : 3, key: up, arrays: [A("nums", nums, { range: [st, i], hl: { [i]: "cur" }, ptr: ptrs({ i }) })], vars: { cur, best }, msg: restart ? `Tổng cũ âm chỉ kéo xuống → bắt đầu lại tại ${nums[i]}.` : `Nối ${nums[i]}, cur = ${cur}.` });
    }
    T.step({ line: 5, arrays: [A("nums", nums, { hl: tones(range(bestR[0], bestR[1]), "ok") })], vars: { best }, msg: `Đoạn tốt nhất có tổng ${best}.` });
    return best;
  },
});

register("trapping-rain-water", {
  input: "height = [0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1]",
  expect: 6,
  code: `l = 0, r = n - 1, lmax = rmax = 0, total = 0
while l < r:
  if h[l] < h[r]:
    lmax = max(lmax, h[l]); total += lmax - h[l]; l++
  else:
    rmax = max(rmax, h[r]); total += rmax - h[r]; r--
return total`,
  run({ height }, T) {
    const water = height.map(() => 0);
    let l = 0, r = height.length - 1, lmax = 0, rmax = 0, total = 0;
    while (l < r) {
      const left = height[l] < height[r], i = left ? l : r;
      if (left) lmax = Math.max(lmax, height[l]); else rmax = Math.max(rmax, height[r]);
      water[i] = (left ? lmax : rmax) - height[i];
      total += water[i];
      T.step({ line: left ? 4 : 6, key: water[i] > 0, arrays: [A("height", height, { bars: true, water: [...water], hl: { [i]: "cur" }, ptr: ptrs({ l, r }) })], vars: { leftMax: lmax, rightMax: rmax, total }, msg: `Bên ${left ? "trái" : "phải"} thấp hơn nên mực nước ở ${i} chỉ phụ thuộc ${left ? "leftMax" : "rightMax"}: chứa ${water[i]}.` });
      if (left) l++; else r--;
    }
    return total;
  },
});

register("valid-parentheses", {
  input: 's = "{[()]}()"',
  expect: true,
  presets: [{ label: "Sai thứ tự", input: 's = "([)]"', expect: false }],
  code: `stack = []
for c in s:
  if c is opening: stack.push(c)
  else if stack empty or stack.pop() != match(c): return false
return stack empty`,
  run({ s }, T) {
    const c = chars(s), pair = { ")": "(", "]": "[", "}": "{" }, st = [];
    for (let i = 0; i < c.length; i++) {
      if (!pair[c[i]]) { st.push(c[i]); T.step({ line: 3, arrays: [A("s", c, { hl: { [i]: "cur" }, ptr: ptrs({ i }) })], stacks: [S("stack", [...st], { hl: { [st.length - 1]: "cur" } })], msg: `Ngoặc mở '${c[i]}' → đẩy vào.` }); continue; }
      const top = st.pop(), ok = top === pair[c[i]];
      T.step({ line: 4, key: !ok, arrays: [A("s", c, { hl: { [i]: ok ? "ok" : "bad" }, ptr: ptrs({ i }) })], stacks: [S("stack", [...st])], msg: ok ? `'${c[i]}' khớp '${top}'.` : `'${c[i]}' không khớp đỉnh ${top ?? "rỗng"}.` });
      if (!ok) return false;
    }
    T.step({ line: 5, key: true, arrays: [A("s", c)], stacks: [S("stack", st)], msg: st.length ? "Còn ngoặc mở chưa đóng." : "Stack rỗng → hợp lệ." });
    return !st.length;
  },
});

register("daily-temperatures", {
  input: "temperatures = [73, 74, 75, 71, 69, 72, 76, 73]",
  expect: [1, 1, 4, 2, 1, 1, 0, 0],
  code: `stack = []            // chỉ số, nhiệt độ giảm dần
for i in 0..n-1:
  while stack and t[stack.top] < t[i]:
    j = stack.pop(); ans[j] = i - j
  stack.push(i)
return ans`,
  run({ temperatures: t }, T) {
    const ans = t.map(() => 0), st = [];
    for (let i = 0; i < t.length; i++) {
      while (st.length && t[st[st.length - 1]] < t[i]) {
        const j = st.pop();
        ans[j] = i - j;
        T.step({ line: 4, arrays: [A("t", t, { bars: true, hl: { [i]: "cur", [j]: "ok" }, ptr: ptrs({ i, j }) }), A("ans", [...ans], { hl: { [j]: "ok" } })], stacks: [S("stack", st.map((k) => `${k}: ${t[k]}`))], msg: `${t[i]} > ${t[j]} → ngày ${j} chờ ${i - j} ngày.` });
      }
      st.push(i);
      T.step({ line: 5, arrays: [A("t", t, { bars: true, hl: { [i]: "cur" }, ptr: ptrs({ i }) }), A("ans", [...ans])], stacks: [S("stack", st.map((k) => `${k}: ${t[k]}`), { hl: { [st.length - 1]: "cur" } })], msg: `Ngày ${i} vào stack, chờ ngày ấm hơn.` });
    }
    return ans;
  },
});

register("largest-rectangle-in-histogram", {
  input: "heights = [2, 1, 5, 6, 2, 3]",
  expect: 10,
  code: `stack = [], best = 0
for i in 0..n:           // h[n] = 0
  while stack and h[stack.top] >= h[i]:
    top = stack.pop()
    left = stack empty ? 0 : stack.top + 1
    best = max(best, h[top] * (i - left))
  stack.push(i)
return best`,
  run({ heights }, T) {
    const h = [...heights, 0], st = [];
    let best = 0, bestR = null;
    for (let i = 0; i < h.length; i++) {
      while (st.length && h[st[st.length - 1]] >= h[i]) {
        const top = st.pop(), left = st.length ? st[st.length - 1] + 1 : 0, area = h[top] * (i - left), up = area > best;
        if (up) { best = area; bestR = [left, i - 1]; }
        T.step({ line: 6, key: up, arrays: [A("heights", heights, { bars: true, range: [left, i - 1], hl: { [top]: "cur" } })], stacks: [S("stack", st.map((k) => heights[k]))], vars: { "diện tích": `${h[top]} × ${i - left} = ${area}`, best }, msg: `Cột ${h[top]} không kéo dài được nữa → chốt hình chữ nhật cao ${h[top]}.` });
      }
      st.push(i);
      if (i < heights.length) T.step({ line: 7, arrays: [A("heights", heights, { bars: true, hl: { [i]: "mark" }, ptr: ptrs({ i }) })], stacks: [S("stack", st.map((k) => heights[k]))], vars: { best }, msg: `Đẩy cột ${heights[i]}: stack giữ các cột tăng dần.` });
    }
    T.step({ line: 8, arrays: [A("heights", heights, { bars: true, hl: bestR ? tones(range(bestR[0], bestR[1]), "ok") : {} })], vars: { best }, msg: `Lớn nhất: ${best}.` });
    return best;
  },
});

register("sliding-window-maximum", {
  input: "nums = [1, 3, -1, -3, 5, 3, 6, 7]\nk = 3",
  expect: [3, 3, 5, 5, 6, 7],
  code: `dq = deque of indices, values decreasing
for i in 0..n-1:
  if dq.front <= i - k: dq.popFront()
  while dq and nums[dq.back] <= nums[i]: dq.popBack()
  dq.pushBack(i)
  if i >= k - 1: out.add(nums[dq.front])
return out`,
  run({ nums, k }, T) {
    const dq = [], out = [];
    for (let i = 0; i < nums.length; i++) {
      if (dq.length && dq[0] <= i - k) dq.shift();
      while (dq.length && nums[dq[dq.length - 1]] <= nums[i]) dq.pop();
      dq.push(i);
      const full = i >= k - 1;
      if (full) out.push(nums[dq[0]]);
      T.step({ line: full ? 6 : 5, key: full, arrays: [A("nums", nums, { range: [Math.max(0, i - k + 1), i], hl: { [dq[0]]: "ok" }, ptr: ptrs({ i }) }), A("max", [...out], { small: true, noIndex: true })], stacks: [Q("deque", dq.map((j) => nums[j]), { hl: { 0: "ok" } })], msg: full ? `Đầu deque là max cửa sổ: ${nums[dq[0]]}.` : "Chưa đủ k phần tử." });
    }
    return out;
  },
});

register("search-in-rotated-sorted-array", {
  input: "nums = [4, 5, 6, 7, 0, 1, 2]\ntarget = 0",
  expect: 4,
  code: `lo = 0, hi = n - 1
while lo <= hi:
  mid = (lo + hi) / 2
  if nums[mid] == target: return mid
  if nums[lo] <= nums[mid]:     // nửa trái đã sắp xếp
    if nums[lo] <= target < nums[mid]: hi = mid - 1 else lo = mid + 1
  else:                         // nửa phải đã sắp xếp
    if nums[mid] < target <= nums[hi]: lo = mid + 1 else hi = mid - 1
return -1`,
  run({ nums, target }, T) {
    let lo = 0, hi = nums.length - 1;
    while (lo <= hi) {
      const mid = (lo + hi) >> 1, hit = nums[mid] === target;
      const left = nums[lo] <= nums[mid];
      const hl = tones(left ? range(lo, mid) : range(mid, hi), "mark");
      hl[mid] = hit ? "ok" : "cur";
      const goLeft = left ? nums[lo] <= target && target < nums[mid] : !(nums[mid] < target && target <= nums[hi]);
      T.step({ line: hit ? 4 : left ? 6 : 8, key: hit, arrays: [A("nums", nums, { range: [lo, hi], dimOutside: true, hl, ptr: ptrs({ lo, mid, hi }) })], msg: hit ? `Tìm thấy ở ${mid}.` : `Nửa ${left ? "trái" : "phải"} (vàng) đã sắp xếp → tìm bên ${goLeft ? "trái" : "phải"}.` });
      if (hit) return mid;
      if (goLeft) hi = mid - 1; else lo = mid + 1;
    }
    return -1;
  },
});

register("koko-eating-bananas", {
  input: "piles = [3, 6, 7, 11]\nh = 8",
  expect: 4,
  code: `lo = 1, hi = max(piles)
while lo < hi:
  k = (lo + hi) / 2
  hours = sum(ceil(p / k) for p in piles)
  if hours <= h: hi = k
  else: lo = k + 1
return lo`,
  run({ piles, h }, T) {
    let lo = 1, hi = Math.max(...piles);
    while (lo < hi) {
      const k = (lo + hi) >> 1, hours = piles.map((p) => Math.ceil(p / k)), total = hours.reduce((a, b) => a + b, 0), ok = total <= h;
      T.step({ line: ok ? 5 : 6, key: ok, arrays: [A("piles", piles, { bars: true }), A(`giờ ở tốc độ ${k}`, hours, { hl: tones(hours.map((_, i) => i), ok ? "ok" : "bad") })], vars: { lo, hi, k, "tổng giờ": total, h }, msg: ok ? `Tốc độ ${k} kịp → thử chậm hơn.` : `Tốc độ ${k} không kịp → phải nhanh hơn.` });
      if (ok) hi = k; else lo = k + 1;
    }
    return lo;
  },
});

register("jump-game", {
  input: "nums = [2, 3, 1, 1, 4]",
  expect: true,
  presets: [{ label: "Kẹt ở số 0", input: "nums = [3, 2, 1, 0, 4]", expect: false }],
  code: `reach = 0
for i in 0..n-1:
  if i > reach: return false
  reach = max(reach, i + nums[i])
return true`,
  run({ nums }, T) {
    let reach = 0;
    for (let i = 0; i < nums.length; i++) {
      if (i > reach) { T.step({ line: 3, key: true, arrays: [A("nums", nums, { range: [0, reach], hl: { [i]: "bad" }, ptr: ptrs({ i }) })], vars: { reach }, msg: `Ô ${i} ngoài tầm với ${reach} → kẹt.` }); return false; }
      reach = Math.max(reach, i + nums[i]);
      T.step({ line: 4, arrays: [A("nums", nums, { range: [0, Math.min(reach, nums.length - 1)], hl: { [i]: "cur" }, ptr: ptrs({ i }) })], vars: { reach }, msg: `Từ ô ${i} nhảy xa nhất tới ${i + nums[i]}.` });
      if (reach >= nums.length - 1) { T.step({ line: 5, key: true, arrays: [A("nums", nums, { hl: { [nums.length - 1]: "ok" } })], vars: { reach }, msg: "Tầm với đã chạm ô cuối." }); return true; }
    }
    return true;
  },
});

register("product-of-array-except-self", {
  input: "nums = [1, 2, 3, 4]",
  expect: [24, 12, 8, 6],
  code: `p = 1
for i in 0..n-1: out[i] = p; p *= nums[i]
p = 1
for i from n-1 down to 0: out[i] *= p; p *= nums[i]
return out`,
  run({ nums }, T) {
    const n = nums.length, out = Array(n).fill(null);
    let p = 1;
    for (let i = 0; i < n; i++) {
      out[i] = p;
      T.step({ line: 2, arrays: [A("nums", nums, { range: [0, i - 1], hl: { [i]: "cur" }, ptr: ptrs({ i }) }), A("out", [...out], { hl: { [i]: "cur" } })], vars: { "tích trái": p }, msg: `Tích mọi số bên trái ${i} là ${p}.` });
      p *= nums[i];
    }
    p = 1;
    for (let i = n - 1; i >= 0; i--) {
      out[i] *= p;
      T.step({ line: 4, arrays: [A("nums", nums, { range: [i + 1, n - 1], hl: { [i]: "cur" }, ptr: ptrs({ i }) }), A("out", [...out], { hl: { [i]: "ok" } })], vars: { "tích phải": p }, msg: `Nhân thêm tích bên phải ${p}.` });
      p *= nums[i];
    }
    return out;
  },
});

register("move-zeroes", {
  input: "nums = [0, 1, 0, 3, 12]",
  expect: [1, 3, 12, 0, 0],
  code: `slow = 0
for fast in 0..n-1:
  if nums[fast] != 0:
    swap(nums[slow], nums[fast]); slow++`,
  run({ nums }, T) {
    const a = [...nums];
    let slow = 0;
    for (let fast = 0; fast < a.length; fast++) {
      if (a[fast] !== 0) {
        [a[slow], a[fast]] = [a[fast], a[slow]];
        T.step({ line: 4, arrays: [A("nums", a, { range: [0, slow], hl: { [slow]: "ok", [fast]: "cur" }, ptr: ptrs({ slow, fast }) })], msg: `${a[slow]} khác 0 → đưa về vị trí ${slow}.` });
        slow++;
      } else T.step({ line: 3, arrays: [A("nums", a, { range: [0, slow - 1], hl: { [fast]: "dim" }, ptr: ptrs({ slow, fast }) })], msg: "Gặp 0 → chỉ tiến fast." });
    }
    return a;
  },
});

register("sort-colors", {
  input: "nums = [2, 0, 2, 1, 1, 0]",
  expect: [0, 0, 1, 1, 2, 2],
  code: `low = 0, i = 0, high = n - 1
while i <= high:
  if nums[i] == 0: swap(low++, i++)
  else if nums[i] == 2: swap(i, high--)
  else: i++`,
  run({ nums }, T) {
    const a = [...nums];
    let low = 0, i = 0, high = a.length - 1;
    const colors = () => tones(range(0, a.length - 1), "base");
    const view = (line, msg) => T.step({ line, arrays: [A("nums", a, { hl: { ...colors(), ...tones(range(0, low - 1), "c4"), ...tones(range(high + 1, a.length - 1), "c1"), ...(i <= high ? { [i]: "cur" } : {}) }, ptr: ptrs({ low, i, high }, a.length) })], msg });
    while (i <= high) {
      if (a[i] === 0) { view(3, "0 → đổi về vùng đầu."); [a[low], a[i]] = [a[i], a[low]]; low++; i++; }
      else if (a[i] === 2) { view(4, "2 → đổi về vùng cuối, chưa tiến i vì số đổi tới chưa xét."); [a[i], a[high]] = [a[high], a[i]]; high--; }
      else { view(5, "1 → để yên."); i++; }
    }
    T.step({ line: 2, key: true, arrays: [A("nums", a)], msg: "Ba vùng 0, 1, 2 đã tách xong." });
    return a;
  },
});

register("subarray-sum-equals-k", {
  input: "nums = [1, 2, 3]\nk = 3",
  expect: 2,
  code: `seen = {0: 1}, sum = 0, count = 0
for v in nums:
  sum += v
  count += seen[sum - k]
  seen[sum]++
return count`,
  run({ nums, k }, T) {
    const seen = new Map([[0, 1]]);
    let sum = 0, count = 0;
    nums.forEach((v, i) => {
      sum += v;
      const add = seen.get(sum - k) ?? 0;
      count += add;
      T.step({ line: 4, key: add > 0, arrays: [A("nums", nums, { hl: { [i]: "cur" }, ptr: ptrs({ i }) })], maps: [M("số lần gặp tổng tiền tố", seen, { hl: { [sum - k]: "ok" } })], vars: { sum, "cần": sum - k, count }, msg: add ? `Tiền tố ${sum - k} đã gặp ${add} lần → thêm ${add} đoạn.` : `Chưa gặp tiền tố ${sum - k}.` });
      seen.set(sum, (seen.get(sum) ?? 0) + 1);
    });
    return count;
  },
});

register("majority-element", {
  input: "nums = [2, 2, 1, 1, 1, 2, 2]",
  expect: 2,
  code: `count = 0, cand = null
for v in nums:
  if count == 0: cand = v
  count += v == cand ? 1 : -1
return cand`,
  run({ nums }, T) {
    let count = 0, cand = null;
    nums.forEach((v, i) => {
      const nw = count === 0;
      if (nw) cand = v;
      count += v === cand ? 1 : -1;
      T.step({ line: nw ? 3 : 4, key: nw, arrays: [A("nums", nums, { hl: { [i]: v === cand ? "ok" : "bad" }, ptr: ptrs({ i }) })], vars: { cand, count }, msg: nw ? `Hết phiếu → ứng viên mới ${v}.` : v === cand ? `Phiếu cho ${cand}.` : `${v} triệt tiêu một phiếu.` });
    });
    return cand;
  },
});

register("merge-sorted-array", {
  input: "nums1 = [1, 2, 3, 0, 0, 0]\nm = 3\nnums2 = [2, 5, 6]\nn = 3",
  expect: [1, 2, 2, 3, 5, 6],
  code: `i = m - 1, j = n - 1, k = m + n - 1
while j >= 0:
  if i >= 0 and nums1[i] > nums2[j]: nums1[k--] = nums1[i--]
  else: nums1[k--] = nums2[j--]`,
  run({ nums1, m, nums2, n }, T) {
    const a = [...nums1];
    let i = m - 1, j = n - 1, k = m + n - 1;
    while (j >= 0) {
      const first = i >= 0 && a[i] > nums2[j];
      const v = first ? a[i] : nums2[j];
      a[k] = v;
      T.step({ line: first ? 3 : 4, arrays: [A("nums1", a, { hl: { [k]: "ok", ...(first ? { [i]: "cur" } : {}) }, ptr: ptrs({ i, k }) }), A("nums2", nums2, { hl: first ? {} : { [j]: "cur" }, ptr: ptrs({ j }) })], msg: `${v} là số lớn nhất còn lại → ghi vào cuối vùng trống.` });
      if (first) i--; else j--;
      k--;
    }
    return a;
  },
});

register("rotate-array", {
  input: "nums = [1, 2, 3, 4, 5, 6, 7]\nk = 3",
  expect: [5, 6, 7, 1, 2, 3, 4],
  code: `k %= n
reverse(nums, 0, n - 1)
reverse(nums, 0, k - 1)
reverse(nums, k, n - 1)`,
  run({ nums, k }, T) {
    const a = [...nums], n = a.length;
    k %= n;
    const rev = (l, r, line, msg) => {
      const x = l, y = r;
      while (l < r) { [a[l], a[r]] = [a[r], a[l]]; l++; r--; }
      T.step({ line, key: true, arrays: [A("nums", [...a], { range: [x, y] })], msg });
    };
    T.step({ line: 1, arrays: [A("nums", nums)], vars: { k }, msg: `Xoay ${k} bước bằng ba lần đảo.` });
    rev(0, n - 1, 2, "Đảo cả mảng: k số cuối đã lên đầu nhưng ngược thứ tự.");
    rev(0, k - 1, 3, "Đảo lại k số đầu.");
    rev(k, n - 1, 4, "Đảo lại phần còn lại.");
    return a;
  },
});

register("next-permutation", {
  input: "nums = [1, 2, 3, 6, 5, 4]",
  expect: [1, 2, 4, 3, 5, 6],
  code: `i = n - 2
while i >= 0 and nums[i] >= nums[i+1]: i--
if i >= 0:
  j = n - 1
  while nums[j] <= nums[i]: j--
  swap(nums[i], nums[j])
reverse(nums, i + 1, n - 1)`,
  run({ nums }, T) {
    const a = [...nums], n = a.length;
    let i = n - 2;
    while (i >= 0 && a[i] >= a[i + 1]) i--;
    T.step({ line: 2, arrays: [A("nums", a, { hl: { ...tones(range(i + 1, n - 1), "win"), ...(i >= 0 ? { [i]: "cur" } : {}) }, ptr: ptrs({ i }) })], msg: i >= 0 ? `Đuôi giảm dần từ ${i + 1}; ${a[i]} là số cần tăng.` : "Cả mảng giảm dần: đây là hoán vị lớn nhất." });
    if (i >= 0) {
      let j = n - 1;
      while (a[j] <= a[i]) j--;
      T.step({ line: 5, arrays: [A("nums", a, { hl: { [i]: "cur", [j]: "ok" }, ptr: ptrs({ i, j }) })], msg: `${a[j]} là số nhỏ nhất lớn hơn ${a[i]} trong đuôi.` });
      [a[i], a[j]] = [a[j], a[i]];
      T.step({ line: 6, arrays: [A("nums", a, { hl: { [i]: "ok", [j]: "ok" }, ptr: ptrs({ i, j }) })], msg: "Đổi chỗ hai số." });
    }
    for (let l = i + 1, r = n - 1; l < r; l++, r--) [a[l], a[r]] = [a[r], a[l]];
    T.step({ line: 7, key: true, arrays: [A("nums", a, { hl: tones(range(i + 1, n - 1), "ok") })], msg: "Đảo đuôi thành tăng dần để số nhỏ nhất có thể." });
    return a;
  },
});

register("find-peak-element", {
  input: "nums = [1, 2, 1, 3, 5, 6, 4]",
  expect: 5,
  code: `lo = 0, hi = n - 1
while lo < hi:
  mid = (lo + hi) / 2
  if nums[mid] < nums[mid+1]: lo = mid + 1
  else: hi = mid
return lo`,
  run({ nums }, T) {
    let lo = 0, hi = nums.length - 1;
    while (lo < hi) {
      const mid = (lo + hi) >> 1, up = nums[mid] < nums[mid + 1];
      T.step({ line: up ? 4 : 5, arrays: [A("nums", nums, { bars: true, range: [lo, hi], hl: { [mid]: "cur", [mid + 1]: "mark" }, ptr: ptrs({ lo, mid, hi }) })], msg: up ? "Đang leo dốc → đỉnh ở bên phải." : "Đang xuống dốc → đỉnh ở mid hoặc bên trái." });
      if (up) lo = mid + 1; else hi = mid;
    }
    T.step({ line: 6, key: true, arrays: [A("nums", nums, { bars: true, hl: { [lo]: "ok" }, ptr: ptrs({ đỉnh: lo }) })], msg: `Đỉnh ở vị trí ${lo}.` });
    return lo;
  },
});

register("gas-station", {
  input: "gas = [1, 2, 3, 4, 5]\ncost = [3, 4, 5, 1, 2]",
  expect: 3,
  code: `total = 0, tank = 0, start = 0
for i in 0..n-1:
  total += gas[i] - cost[i]
  tank += gas[i] - cost[i]
  if tank < 0: start = i + 1; tank = 0
return total >= 0 ? start : -1`,
  run({ gas, cost }, T) {
    let total = 0, tank = 0, start = 0;
    gas.forEach((g, i) => {
      const d = g - cost[i];
      total += d;
      tank += d;
      const fail = tank < 0;
      T.step({ line: fail ? 5 : 4, key: fail, arrays: [A("gas − cost", gas.map((x, k) => x - cost[k]), { range: [start, i], hl: { [i]: fail ? "bad" : "cur" }, ptr: ptrs({ start }) })], vars: { tank, total }, msg: fail ? `Hết xăng ở ${i}: mọi trạm từ ${start} tới ${i} đều không thể xuất phát → thử từ ${i + 1}.` : `Tới trạm ${i + 1}, bình còn ${tank}.` });
      if (fail) { start = i + 1; tank = 0; }
    });
    return total >= 0 ? start : -1;
  },
});

register("asteroid-collision", {
  input: "asteroids = [5, 10, -5, 8, -9]",
  expect: [5, 10],
  code: `stack = []
for a in asteroids:
  alive = true
  while alive and a < 0 and stack.top > 0:
    if stack.top < -a: stack.pop()
    else if stack.top == -a: stack.pop(); alive = false
    else: alive = false
  if alive: stack.push(a)`,
  run({ asteroids }, T) {
    const st = [];
    asteroids.forEach((a, i) => {
      let alive = true;
      while (alive && a < 0 && st.length && st[st.length - 1] > 0) {
        const top = st[st.length - 1], line = top < -a ? 5 : top === -a ? 6 : 7;
        T.step({ line, key: true, arrays: [A("asteroids", asteroids, { hl: { [i]: "cur" }, ptr: ptrs({ i }) })], stacks: [S("stack", [...st], { hl: { [st.length - 1]: "bad" } })], msg: top < -a ? `${a} phá ${top}.` : top === -a ? `${a} và ${top} cùng nổ.` : `${top} phá ${a}.` });
        if (top < -a) st.pop();
        else { if (top === -a) st.pop(); alive = false; }
      }
      if (alive) { st.push(a); T.step({ line: 8, arrays: [A("asteroids", asteroids, { hl: { [i]: "cur" }, ptr: ptrs({ i }) })], stacks: [S("stack", [...st], { hl: { [st.length - 1]: "ok" } })], msg: `${a} an toàn.` }); }
    });
    return st;
  },
});

register("kth-largest-element-in-an-array", {
  input: "nums = [3, 2, 1, 5, 6, 4]\nk = 2",
  expect: 5,
  code: `heap = min-heap
for v in nums:
  heap.push(v)
  if len(heap) > k: heap.pop()
return heap.top`,
  run({ nums, k }, T) {
    const h = new Heap();
    nums.forEach((v, i) => {
      h.push(v);
      const over = h.size > k;
      T.step({ line: 3, arrays: [A("nums", nums, { hl: { [i]: "cur" }, ptr: ptrs({ i }) })], tree: h.tree("min-heap", undefined, over ? { 0: "bad" } : { 0: "mark" }), msg: over ? `Quá ${k} phần tử → sắp loại đỉnh ${h.peek()}.` : `Thêm ${v}.` });
      if (over) h.pop();
    });
    T.step({ line: 5, key: true, tree: h.tree("min-heap", undefined, { 0: "ok" }), arrays: [A("nums", nums)], msg: `Heap giữ ${k} số lớn nhất, đỉnh là đáp án: ${h.peek()}.` });
    return h.peek();
  },
});

/* ---------- 1-D DP ---------- */

register("climbing-stairs", {
  input: "n = 6",
  expect: 13,
  code: `dp[0] = 1, dp[1] = 1
for i in 2..n:
  dp[i] = dp[i-1] + dp[i-2]
return dp[n]`,
  run({ n }, T) {
    const dp = Array(n + 1).fill(null);
    dp[0] = dp[1] = 1;
    T.step({ line: 1, arrays: [A("dp", [...dp])], msg: "Một cách đứng yên, một cách lên bậc 1." });
    for (let i = 2; i <= n; i++) {
      dp[i] = dp[i - 1] + dp[i - 2];
      T.step({ line: 3, arrays: [A("dp", [...dp], { hl: { [i]: "cur", [i - 1]: "mark", [i - 2]: "mark" }, ptr: ptrs({ i }) })], msg: `Tới bậc ${i} từ bậc ${i - 1} hoặc ${i - 2}: ${dp[i - 1]} + ${dp[i - 2]} = ${dp[i]}.` });
    }
    return dp[n];
  },
});

register("house-robber", {
  input: "nums = [2, 7, 9, 3, 1]",
  expect: 12,
  code: `dp[0] = nums[0], dp[1] = max(nums[0], nums[1])
for i in 2..n-1:
  dp[i] = max(dp[i-1], dp[i-2] + nums[i])
return dp[n-1]`,
  run({ nums }, T) {
    const n = nums.length, dp = Array(n).fill(null);
    dp[0] = nums[0];
    if (n > 1) dp[1] = Math.max(nums[0], nums[1]);
    T.step({ line: 1, arrays: [A("nums", nums), A("dp", [...dp])], msg: "Hai nhà đầu." });
    for (let i = 2; i < n; i++) {
      dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
      const take = dp[i] === dp[i - 2] + nums[i];
      T.step({ line: 3, arrays: [A("nums", nums, { hl: { [i]: take ? "ok" : "dim" }, ptr: ptrs({ i }) }), A("dp", [...dp], { hl: { [i]: "cur", [i - 1]: "mark", [i - 2]: "mark" } })], msg: take ? `Cướp nhà ${i}: ${dp[i - 2]} + ${nums[i]} = ${dp[i]}.` : `Bỏ nhà ${i}, giữ ${dp[i]}.` });
    }
    return dp[n - 1];
  },
});

register("coin-change", {
  input: "coins = [1, 2, 5]\namount = 11",
  expect: 3,
  code: `dp[0] = 0, others = inf
for s in 1..amount:
  for c in coins:
    if c <= s: dp[s] = min(dp[s], dp[s-c] + 1)
return dp[amount] or -1`,
  run({ coins, amount }, T) {
    const dp = Array(amount + 1).fill(Infinity);
    dp[0] = 0;
    for (let s = 1; s <= amount; s++) {
      let from = -1;
      for (const c of coins) if (c <= s && dp[s - c] + 1 < dp[s]) { dp[s] = dp[s - c] + 1; from = s - c; }
      T.step({ line: 4, arrays: [A("dp", dp.map((v) => (v === Infinity ? "∞" : v)), { hl: { [s]: "cur", [from]: "mark" } })], msg: from >= 0 ? `Tổng ${s}: từ ${from} thêm đồng ${s - from} → ${dp[s]} đồng.` : `Không tạo được tổng ${s}.` });
    }
    return dp[amount] === Infinity ? -1 : dp[amount];
  },
});

register("longest-increasing-subsequence", {
  input: "nums = [10, 9, 2, 5, 3, 7, 101, 18]",
  expect: 4,
  code: `tails = []
for v in nums:
  i = lower_bound(tails, v)
  if i == len(tails): tails.add(v)
  else: tails[i] = v
return len(tails)`,
  run({ nums }, T) {
    const tails = [];
    nums.forEach((v, k) => {
      let lo = 0, hi = tails.length;
      while (lo < hi) { const m = (lo + hi) >> 1; if (tails[m] < v) lo = m + 1; else hi = m; }
      const grew = lo === tails.length;
      tails[lo] = v;
      T.step({ line: grew ? 4 : 5, key: grew, arrays: [A("nums", nums, { hl: { [k]: "cur" }, ptr: ptrs({ i: k }) }), A("tails", [...tails], { hl: { [lo]: grew ? "ok" : "mark" } })], msg: grew ? `${v} lớn hơn mọi đuôi → dãy dài thêm thành ${tails.length}.` : `${v} thay ô ${lo}: đuôi nhỏ hơn thì dễ nối tiếp hơn.` });
    });
    return tails.length;
  },
});

register("decode-ways", {
  input: 's = "226"',
  expect: 3,
  code: `dp[0] = 1
for i in 1..n:
  if s[i-1] != '0': dp[i] += dp[i-1]
  if i >= 2 and 10 <= int(s[i-2..i-1]) <= 26: dp[i] += dp[i-2]
return dp[n]`,
  run({ s }, T) {
    const n = s.length, dp = Array(n + 1).fill(0);
    dp[0] = 1;
    for (let i = 1; i <= n; i++) {
      const one = s[i - 1] !== "0", two = i >= 2 && Number(s.slice(i - 2, i)) >= 10 && Number(s.slice(i - 2, i)) <= 26;
      if (one) dp[i] += dp[i - 1];
      if (two) dp[i] += dp[i - 2];
      T.step({ line: two ? 4 : 3, arrays: [A("s", chars(s), { hl: { [i - 1]: "cur", ...(two ? { [i - 2]: "mark" } : {}) } }), A("dp", [...dp], { hl: { [i]: "ok" } })], msg: `${one ? `Giải mã '${s[i - 1]}' riêng.` : "'0' không đứng một mình."} ${two ? `Ghép "${s.slice(i - 2, i)}" được.` : ""}`.trim() });
    }
    return dp[n];
  },
});

register("partition-equal-subset-sum", {
  input: "nums = [1, 5, 11, 5]",
  expect: true,
  code: `if sum is odd: return false
target = sum / 2, dp[0] = true
for v in nums:
  for s from target down to v:
    dp[s] = dp[s] or dp[s-v]
return dp[target]`,
  run({ nums }, T) {
    const sum = nums.reduce((a, b) => a + b, 0);
    if (sum % 2) { T.step({ line: 1, arrays: [A("nums", nums)], vars: { sum }, msg: "Tổng lẻ, không chia đôi được." }); return false; }
    const t = sum / 2, dp = Array(t + 1).fill(false);
    dp[0] = true;
    const view = () => dp.map((x) => (x ? "T" : "·"));
    T.step({ line: 2, arrays: [A("dp", view(), { small: true, hl: { [t]: "mark" } })], vars: { target: t }, msg: `Cần chọn tập con có tổng ${t}.` });
    nums.forEach((v, i) => {
      for (let s = t; s >= v; s--) dp[s] = dp[s] || dp[s - v];
      T.step({ line: 5, arrays: [A("nums", nums, { hl: { [i]: "cur" } }), A("dp", view(), { small: true, hl: { ...tones(range(0, t).filter((x) => dp[x]), "done"), [t]: dp[t] ? "ok" : "mark" } })], msg: `Thêm ${v}: các tổng đạt được cập nhật. Đi ngược để mỗi số dùng một lần.` });
    });
    return dp[t];
  },
});

/* ---------- 2-D DP ---------- */

register("unique-paths", {
  input: "m = 3\nn = 7",
  expect: 28,
  code: `dp = ones
for i in 1..m-1:
  for j in 1..n-1:
    dp[i][j] = dp[i-1][j] + dp[i][j-1]
return dp[m-1][n-1]`,
  run({ m, n }, T) {
    const dp = matrix(m, n, null);
    for (let i = 0; i < m; i++) for (let j = 0; j < n; j++) {
      dp[i][j] = !i || !j ? 1 : dp[i - 1][j] + dp[i][j - 1];
      if (i && j) T.step({ line: 4, grids: [G("số đường", clone(dp), { hl: { [i + "," + j]: "cur", [i - 1 + "," + j]: "mark", [i + "," + (j - 1)]: "mark" } })], msg: `Từ trên ${dp[i - 1][j]} + từ trái ${dp[i][j - 1]} = ${dp[i][j]}.` });
    }
    return dp[m - 1][n - 1];
  },
});

register("maximal-square", {
  input: 'matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]',
  expect: 4,
  code: `for each cell '1':
  dp[i][j] = 1 + min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])
  best = max(best, dp[i][j])
return best * best`,
  run({ matrix: a }, T) {
    const m = a.length, n = a[0].length, dp = matrix(m, n, null);
    let best = 0, at = null;
    for (let i = 0; i < m; i++) for (let j = 0; j < n; j++) {
      dp[i][j] = 0;
      if (a[i][j] !== "1") continue;
      const u = dp[i - 1]?.[j] ?? 0, l = dp[i][j - 1] ?? 0, d = dp[i - 1]?.[j - 1] ?? 0;
      dp[i][j] = 1 + Math.min(u, l, d);
      const up = dp[i][j] > best;
      if (up) { best = dp[i][j]; at = [i, j]; }
      T.step({ line: up ? 3 : 2, key: up, grids: [G("matrix", a, { hl: { [i + "," + j]: "cur" } }), G("cạnh lớn nhất", clone(dp), { hl: { [i + "," + j]: "cur", [i - 1 + "," + j]: "mark", [i + "," + (j - 1)]: "mark", [i - 1 + "," + (j - 1)]: "mark" } })], vars: { best }, msg: `1 + min(trên ${u}, trái ${l}, chéo ${d}) = ${dp[i][j]}.` });
    }
    const hl = {};
    if (at) for (let i = at[0] - best + 1; i <= at[0]; i++) for (let j = at[1] - best + 1; j <= at[1]; j++) hl[i + "," + j] = "ok";
    T.step({ line: 4, key: true, grids: [G("matrix", a, { hl })], vars: { best }, msg: `Cạnh ${best} → diện tích ${best * best}.` });
    return best * best;
  },
});

function stringDp(slug, input, expect, code, label, cell, explain, line) {
  register(slug, {
    input, expect, code,
    run(args, T) {
      const [a, b] = Object.values(args), dp = matrix(a.length + 1, b.length + 1, null);
      const heads = { rowHead: ["∅", ...a], colHead: ["∅", ...b] };
      for (let i = 0; i <= a.length; i++) for (let j = 0; j <= b.length; j++) {
        const [v, from] = cell(dp, a, b, i, j);
        dp[i][j] = v;
        if (i && j) T.step({ line: line(a, b, i, j), grids: [G(label, clone(dp), { ...heads, hl: { [i + "," + j]: "cur", ...Object.fromEntries(from.map(([r, c]) => [r + "," + c, "mark"])) } })], msg: explain(dp, a, b, i, j) });
      }
      return dp[a.length][b.length];
    },
  });
}

stringDp("longest-common-subsequence", 'text1 = "abcde"\ntext2 = "ace"', 3, `for i in 1..m, j in 1..n:
  if a[i-1] == b[j-1]: dp[i][j] = dp[i-1][j-1] + 1
  else: dp[i][j] = max(dp[i-1][j], dp[i][j-1])
return dp[m][n]`, "LCS", (dp, a, b, i, j) => {
  if (!i || !j) return [0, []];
  if (a[i - 1] === b[j - 1]) return [dp[i - 1][j - 1] + 1, [[i - 1, j - 1]]];
  return [Math.max(dp[i - 1][j], dp[i][j - 1]), [[i - 1, j], [i, j - 1]]];
}, (dp, a, b, i, j) => (a[i - 1] === b[j - 1] ? `'${a[i - 1]}' khớp → chéo + 1 = ${dp[i][j]}.` : `'${a[i - 1]}' ≠ '${b[j - 1]}' → max(trên, trái) = ${dp[i][j]}.`), (a, b, i, j) => (a[i - 1] === b[j - 1] ? 2 : 3));

stringDp("edit-distance", 'word1 = "horse"\nword2 = "ros"', 3, `dp[i][0] = i, dp[0][j] = j
for i in 1..m, j in 1..n:
  if a[i-1] == b[j-1]: dp[i][j] = dp[i-1][j-1]
  else: dp[i][j] = 1 + min(replace, delete, insert)
return dp[m][n]`, "khoảng cách", (dp, a, b, i, j) => {
  if (!i) return [j, []];
  if (!j) return [i, []];
  if (a[i - 1] === b[j - 1]) return [dp[i - 1][j - 1], [[i - 1, j - 1]]];
  return [1 + Math.min(dp[i - 1][j - 1], dp[i - 1][j], dp[i][j - 1]), [[i - 1, j - 1], [i - 1, j], [i, j - 1]]];
}, (dp, a, b, i, j) => (a[i - 1] === b[j - 1] ? `'${a[i - 1]}' khớp, không tốn thao tác.` : `1 + min(thay ↖, xoá ↑, chèn ←) = ${dp[i][j]}.`), (a, b, i, j) => (a[i - 1] === b[j - 1] ? 3 : 4));

/* ---------- grids and search ---------- */

register("number-of-islands", {
  input: 'grid = [["1","1","0","0","0"],["1","1","0","0","0"],["0","0","1","0","0"],["0","0","0","1","1"]]',
  expect: 3,
  code: `count = 0
for each cell '1' not visited:
  count++
  dfs(cell)      // đánh dấu cả đảo
return count`,
  run({ grid }, T) {
    const m = grid.length, n = grid[0].length, id = matrix(m, n, 0), colors = ["c1", "c2", "c3", "c4", "c5", "c6"];
    let count = 0;
    const hl = () => Object.fromEntries(id.flatMap((row, r) => row.flatMap((v, c) => (v ? [[r + "," + c, colors[(v - 1) % 6]]] : []))));
    for (let r = 0; r < m; r++) for (let c = 0; c < n; c++) {
      if (grid[r][c] !== "1" || id[r][c]) continue;
      count++;
      T.step({ line: 3, key: true, grids: [G("grid", grid, { hl: { ...hl(), [r + "," + c]: "cur" } })], vars: { "số đảo": count }, msg: `Đất chưa thăm ở (${r}, ${c}) → đảo thứ ${count}.` });
      const st = [[r, c]];
      id[r][c] = count;
      while (st.length) {
        const [i, j] = st.pop();
        T.step({ line: 4, grids: [G("grid", grid, { hl: { ...hl(), [i + "," + j]: "cur" } })], vars: { "số đảo": count }, msg: `Loang tới (${i}, ${j}).` });
        for (const [x, y] of DIRS) if (grid[i + x]?.[j + y] === "1" && !id[i + x][j + y]) { id[i + x][j + y] = count; st.push([i + x, j + y]); }
      }
    }
    return count;
  },
});

register("rotting-oranges", {
  input: "grid = [[2, 1, 1], [1, 1, 0], [0, 1, 1]]",
  expect: 4,
  code: `queue = rotten cells, fresh = count of 1
minutes = 0
while queue and fresh > 0:
  for each rotten cell in this minute:
    rot its fresh neighbours; fresh--; queue.add
  minutes++
return fresh == 0 ? minutes : -1`,
  run({ grid }, T) {
    const g = clone(grid);
    let level = [], fresh = 0, minutes = 0;
    g.forEach((row, r) => row.forEach((v, c) => { if (v === 2) level.push([r, c]); if (v === 1) fresh++; }));
    const view = (line, cur, msg, key) => {
      const hl = {};
      g.forEach((row, r) => row.forEach((v, c) => { if (v === 2) hl[r + "," + c] = "bad"; if (v === 1) hl[r + "," + c] = "c3"; }));
      cur.forEach(([r, c]) => (hl[r + "," + c] = "cur"));
      T.step({ line, key, grids: [G("grid", clone(g), { hl })], vars: { "phút": minutes, "còn tươi": fresh }, msg });
    };
    view(1, [], "Mọi quả thối cùng lan một lúc: BFS đa nguồn.");
    while (level.length && fresh) {
      const next = [];
      for (const [r, c] of level) for (const [x, y] of DIRS) if (g[r + x]?.[c + y] === 1) { g[r + x][c + y] = 2; fresh--; next.push([r + x, c + y]); }
      minutes++;
      level = next;
      view(5, next, `Phút ${minutes}: thêm ${next.length} quả thối.`, !fresh);
    }
    return fresh ? -1 : minutes;
  },
});

register("word-search", {
  input: 'board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]]\nword = "ABCCED"',
  expect: true,
  code: `dfs(r, c, i):
  if i == len(word): return true
  if out of bounds or board[r][c] != word[i]: return false
  mark cell
  found = any dfs(neighbour, i + 1)
  unmark cell
  return found`,
  run({ board, word }, T) {
    const seen = {};
    const dfs = (r, c, i) => {
      if (i === word.length) return true;
      if (board[r]?.[c] !== word[i] || seen[r + "," + c]) return false;
      seen[r + "," + c] = "ok";
      T.step({ line: 4, grids: [G("board", board, { hl: { ...seen, [r + "," + c]: "cur" } })], arrays: [A("word", chars(word), { hl: { ...tones(range(0, i - 1), "done"), [i]: "cur" } })], msg: `Khớp '${word[i]}' tại (${r}, ${c}).` });
      const found = DIRS.some(([x, y]) => dfs(r + x, c + y, i + 1));
      if (!found) { delete seen[r + "," + c]; T.step({ line: 6, grids: [G("board", board, { hl: { ...seen, [r + "," + c]: "bad" } })], arrays: [A("word", chars(word), { hl: tones(range(0, i - 1), "done") })], msg: "Ngõ cụt, quay lui." }); }
      return found;
    };
    for (let r = 0; r < board.length; r++) for (let c = 0; c < board[0].length; c++) if (dfs(r, c, 0)) {
      T.step({ line: 7, key: true, grids: [G("board", board, { hl: seen })], arrays: [A("word", chars(word), { hl: tones(range(0, word.length - 1), "ok") })], msg: "Tìm thấy." });
      return true;
    }
    return false;
  },
});

register("n-queens", {
  input: "n = 4",
  expect: [[".Q..", "...Q", "Q...", "..Q."], ["..Q.", "Q...", "...Q", ".Q.."]],
  code: `place(row):
  if row == n: result.add(board); return
  for col in 0..n-1:
    if safe(row, col):
      put queen; place(row + 1); remove queen`,
  run({ n }, T) {
    const q = [], out = [];
    const safe = (r, c) => q.every((x, i) => x !== c && Math.abs(x - c) !== r - i);
    const board = () => { const b = matrix(n, n, null); q.forEach((c, r) => (b[r][c] = "♛")); return b; };
    const place = (r) => {
      if (r === n) {
        out.push(q.map((c) => ".".repeat(c) + "Q" + ".".repeat(n - c - 1)));
        T.step({ line: 2, key: true, grids: [G("bàn cờ", board(), { hl: Object.fromEntries(q.map((c, i) => [i + "," + c, "ok"])) })], vars: { "số lời giải": out.length }, msg: `Lời giải thứ ${out.length}.` });
        return;
      }
      for (let c = 0; c < n; c++) {
        const ok = safe(r, c);
        T.step({ line: 4, grids: [G("bàn cờ", board(), { hl: { ...Object.fromEntries(q.map((x, i) => [i + "," + x, "done"])), [r + "," + c]: ok ? "cur" : "bad" } })], vars: { "số lời giải": out.length }, msg: ok ? `(${r}, ${c}) an toàn.` : `(${r}, ${c}) bị khống chế.` });
        if (ok) { q.push(c); place(r + 1); q.pop(); }
      }
    };
    place(0);
    return out;
  },
});

register("permutations", {
  input: "nums = [1, 2, 3]",
  expect: [[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]],
  code: `dfs():
  if len(path) == n: result.add(path); return
  for v in nums not used:
    path.add(v); dfs(); path.removeLast()`,
  run({ nums }, T) {
    const out = [], path = [], rec = new RecTree();
    const dfs = (label) => {
      rec.enter(label);
      const hit = path.length === nums.length;
      if (hit) out.push([...path]);
      T.step({ line: hit ? 2 : 3, key: hit, nary: [rec.view()], arrays: [A("nums", nums, { hl: tones(nums.map((_, i) => i).filter((i) => path.includes(nums[i])), "done") })], vars: { path: [...path], "số kết quả": out.length }, msg: hit ? `Hoán vị [${path}].` : `Đang có [${path}].` });
      if (!hit) for (const v of nums) if (!path.includes(v)) { path.push(v); dfs(String(v)); path.pop(); }
      rec.leave(hit ? "ok" : "done");
    };
    dfs("∅");
    return out;
  },
});

register("subsets", {
  input: "nums = [1, 2, 3]",
  expect: [[], [1], [1, 2], [1, 2, 3], [1, 3], [2], [2, 3], [3]],
  code: `dfs(start):
  result.add(path)
  for i in start..n-1:
    path.add(nums[i]); dfs(i + 1); path.removeLast()`,
  run({ nums }, T) {
    const out = [], path = [], rec = new RecTree();
    const dfs = (s, label) => {
      rec.enter(label);
      out.push([...path]);
      T.step({ line: 2, key: true, nary: [rec.view()], vars: { "tập con": [...path], "số kết quả": out.length }, msg: `Ghi tập con [${path}].` });
      for (let i = s; i < nums.length; i++) { path.push(nums[i]); dfs(i + 1, String(nums[i])); path.pop(); }
      rec.leave("ok");
    };
    dfs(0, "∅");
    return out;
  },
});

/* ---------- linked lists ---------- */

register("reverse-linked-list", {
  input: "head = [1, 2, 3, 4, 5]",
  expect: [5, 4, 3, 2, 1],
  code: `prev = null, cur = head
while cur:
  next = cur.next
  cur.next = prev
  prev = cur
  cur = next
return prev`,
  run({ head }, T) {
    const vals = [...head], next = vals.map((_, i) => (i + 1 < vals.length ? i + 1 : -1));
    let prev = -1, cur = 0;
    const view = (line, msg, nxt) => T.step({ line, lists: [L("list", { vals, next }, { ptr: ptrs({ prev, cur, next: nxt }, vals.length), hl: { [prev]: "ok", [cur]: "cur" } })], msg });
    while (cur !== -1) {
      const nx = next[cur];
      view(3, "Giữ lại nút kế để khỏi mất phần sau.", nx);
      next[cur] = prev;
      view(4, `Đảo mũi tên của ${vals[cur]} về phía sau.`, nx);
      prev = cur;
      cur = nx;
      view(6, "Cả hai con trỏ tiến một nút.", nx);
    }
    T.step({ line: 7, key: true, lists: [L("list", { vals, next }, { ptr: ptrs({ head: prev }), hl: { [prev]: "ok" } })], msg: "prev là đầu mới." });
    const out = [];
    for (let i = prev; i !== -1; i = next[i]) out.push(vals[i]);
    return out;
  },
});

register("linked-list-cycle", {
  input: "head = [3, 2, 0, -4]\npos = 1",
  expect: true,
  presets: [{ label: "Không có chu trình", input: "head = [1, 2, 3]\npos = -1", expect: false }],
  code: `slow = fast = head
while fast and fast.next:
  slow = slow.next
  fast = fast.next.next
  if slow == fast: return true
return false`,
  run({ head, pos }, T) {
    const vals = head, next = vals.map((_, i) => (i + 1 < vals.length ? i + 1 : -1));
    if (pos >= 0) next[vals.length - 1] = pos;
    let slow = 0, fast = 0;
    while (fast !== -1 && next[fast] !== -1) {
      slow = next[slow];
      fast = next[next[fast]];
      const meet = fast !== -1 && slow === fast;
      T.step({ line: meet ? 5 : 4, key: meet, lists: [L("list", { vals, next }, { ptr: ptrs({ slow, fast }, vals.length), hl: meet ? { [slow]: "ok" } : { [slow]: "cur", [fast]: "mark" } })], msg: meet ? "Gặp nhau → có chu trình." : "Rùa 1 bước, thỏ 2 bước." });
      if (meet) return true;
    }
    T.step({ line: 6, key: true, lists: [L("list", { vals, next })], msg: "Thỏ chạm cuối → không có chu trình." });
    return false;
  },
});

register("middle-of-the-linked-list", {
  input: "head = [1, 2, 3, 4, 5]",
  expect: [3, 4, 5],
  code: `slow = fast = head
while fast and fast.next:
  slow = slow.next
  fast = fast.next.next
return slow`,
  run({ head }, T) {
    let slow = 0, fast = 0;
    while (fast < head.length && fast + 1 < head.length) {
      slow++; fast += 2;
      T.step({ line: 4, lists: [L("list", head, { ptr: ptrs({ slow, fast }, head.length), hl: { [slow]: "cur" } })], msg: "fast đi gấp đôi slow: fast tới cuối thì slow ở giữa." });
    }
    T.step({ line: 5, key: true, lists: [L("list", head, { hl: { [slow]: "ok" }, ptr: ptrs({ slow }) })], msg: `Nút giữa: ${head[slow]}.` });
    return head.slice(slow);
  },
});

/* ---------- trees ---------- */

register("binary-tree-level-order-traversal", {
  input: "root = [3, 9, 20, null, null, 15, 7]",
  expect: [[3], [9, 20], [15, 7]],
  code: `queue = [root]
while queue not empty:
  level = []
  repeat len(queue) times:
    node = queue.pop(); level.add(node.val)
    push children
  result.add(level)`,
  run({ root }, T) {
    const t = buildTree(root), out = [], hl = {};
    let q = [t.root];
    while (q.length) {
      const level = [], next = [];
      for (const id of q) {
        level.push(t.nodes[id].v);
        hl[id] = "done";
        for (const c of [t.nodes[id].l, t.nodes[id].r]) if (c !== -1) next.push(c);
        T.step({ line: 5, tree: T_(t, { hl: { ...hl, [id]: "cur", ...tones(next, "mark") } }), stacks: [Q("queue", [...q.slice(q.indexOf(id) + 1), ...next].map((x) => t.nodes[x].v))], arrays: [A("mức hiện tại", [...level], { noIndex: true })], msg: `Lấy ${t.nodes[id].v}, thêm các con vào hàng đợi.` });
      }
      out.push(level);
      T.step({ line: 7, key: true, tree: T_(t, { hl: { ...hl } }), vars: { "kết quả": out }, msg: `Xong mức ${out.length - 1}.` });
      q = next;
    }
    return out;
  },
});

register("maximum-depth-of-binary-tree", {
  input: "root = [3, 9, 20, null, null, 15, 7]",
  expect: 3,
  code: `depth(node):
  if node == null: return 0
  return 1 + max(depth(node.left), depth(node.right))`,
  run({ root }, T) {
    const t = buildTree(root), tag = {}, hl = {};
    const go = (id) => {
      if (id === -1) return 0;
      const d = 1 + Math.max(go(t.nodes[id].l), go(t.nodes[id].r));
      tag[id] = d;
      hl[id] = "done";
      T.step({ line: 3, tree: T_(t, { hl: { ...hl, [id]: "cur" }, tag: { ...tag } }), msg: `Cây con gốc ${t.nodes[id].v} cao ${d}.` });
      return d;
    };
    return go(t.root);
  },
});

register("invert-binary-tree", {
  input: "root = [4, 2, 7, 1, 3, 6, 9]",
  expect: [4, 7, 2, 9, 6, 3, 1],
  code: `invert(node):
  if node == null: return null
  swap(node.left, node.right)
  invert(node.left)
  invert(node.right)`,
  run({ root }, T) {
    const t = buildTree(root), hl = {};
    const go = (id) => {
      if (id === -1) return;
      const n = t.nodes[id];
      [n.l, n.r] = [n.r, n.l];
      hl[id] = "done";
      T.step({ line: 3, tree: T_(t, { hl: { ...hl, [id]: "cur" } }), msg: `Đổi hai con của ${n.v}.` });
      go(n.l);
      go(n.r);
    };
    go(t.root);
    return treeToArray(t);
  },
});

register("lowest-common-ancestor-of-a-binary-search-tree", {
  input: "root = [6, 2, 8, 0, 4, 7, 9, null, null, 3, 5]\np = 2\nq = 8",
  expect: 6,
  code: `node = root
while node:
  if p, q both < node.val: node = node.left
  else if p, q both > node.val: node = node.right
  else: return node`,
  run({ root, p, q }, T) {
    const t = buildTree(root), hl = {};
    let id = t.root;
    for (;;) {
      const v = t.nodes[id].v, left = p < v && q < v, right = p > v && q > v;
      T.step({ line: left ? 3 : right ? 4 : 5, key: !left && !right, tree: T_(t, { hl: { ...hl, [id]: left || right ? "cur" : "ok" } }), msg: left ? `Cả ${p} và ${q} đều nhỏ hơn ${v} → sang trái.` : right ? `Cả hai đều lớn hơn ${v} → sang phải.` : `${p} và ${q} tách về hai phía của ${v} → đây là tổ tiên chung.` });
      if (!left && !right) return v;
      hl[id] = "dim";
      id = left ? t.nodes[id].l : t.nodes[id].r;
    }
  },
});
