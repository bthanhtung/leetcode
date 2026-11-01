/* Greedy, heaps, intervals and merge-sort counting. */
import { A, G, Heap, M, Q, S, ptrs, register, tones } from "./engine.js";

const range = (a, b) => Array.from({ length: Math.max(0, b - a + 1) }, (_, i) => a + i);

register("jump-game-ii", {
  input: "nums = [2, 3, 1, 1, 4]",
  expect: 2,
  code: `jumps = 0, end = 0, far = 0
for i in 0..n-2:
  far = max(far, i + nums[i])
  if i == end:
    jumps++; end = far
return jumps`,
  run({ nums }, T) {
    let jumps = 0, end = 0, far = 0;
    for (let i = 0; i < nums.length - 1; i++) {
      far = Math.max(far, i + nums[i]);
      const hit = i === end;
      T.step({ line: 3, arrays: [A("nums", nums, { range: [i, end], hl: { [i]: "cur" }, ptr: ptrs({ i, end, far }, nums.length) })], vars: { jumps, far }, msg: `Từ ${i} với tới xa nhất ${far}.` });
      if (hit) { jumps++; end = far; T.step({ line: 5, key: true, arrays: [A("nums", nums, { range: [i + 1, end], hl: { [i]: "ok" }, ptr: ptrs({ i, end }, nums.length) })], vars: { jumps, far }, msg: `Hết tầm của lần nhảy trước → nhảy lần ${jumps}, tầm mới tới ${end}.` }); }
    }
    return jumps;
  },
});

register("merge-intervals", {
  input: "intervals = [[1, 3], [2, 6], [8, 10], [15, 18]]",
  expect: [[1, 6], [8, 10], [15, 18]],
  code: `sort by start
out = [first]
for (s, e) in rest:
  if s <= out.last.end: out.last.end = max(out.last.end, e)
  else: out.add([s, e])
return out`,
  run({ intervals }, T) {
    const a = [...intervals].sort((x, y) => x[0] - y[0]), out = [[...a[0]]];
    T.step({ line: 2, intervals: [{ label: "đã sắp xếp", items: a, hl: { 0: "cur" } }, { label: "kết quả", items: out.map((x) => [...x]) }], msg: "Sắp xếp theo điểm bắt đầu." });
    for (let i = 1; i < a.length; i++) {
      const [s, e] = a[i], last = out[out.length - 1], overlap = s <= last[1];
      if (overlap) last[1] = Math.max(last[1], e); else out.push([s, e]);
      T.step({ line: overlap ? 4 : 5, key: overlap, intervals: [{ label: "đã sắp xếp", items: a, hl: { [i]: "cur" } }, { label: "kết quả", items: out.map((x) => [...x]), hl: { [out.length - 1]: overlap ? "ok" : "mark" } }], msg: overlap ? `[${s}, ${e}] chồng lên khoảng trước → gộp.` : `[${s}, ${e}] tách biệt → khoảng mới.` });
    }
    return out;
  },
});

register("insert-interval", {
  input: "intervals = [[1, 2], [3, 5], [6, 7], [8, 10], [12, 16]]\nnewInterval = [4, 8]",
  expect: [[1, 2], [3, 10], [12, 16]],
  code: `for (s, e) in intervals:
  if e < new.start: out.add([s, e])
  else if s > new.end: out.add(new); new = [s, e]
  else: new = [min(s, new.start), max(e, new.end)]
out.add(new)
return out`,
  run({ intervals, newInterval }, T) {
    const out = [];
    let nw = [...newInterval];
    intervals.forEach(([s, e], i) => {
      let line;
      if (e < nw[0]) { out.push([s, e]); line = 2; }
      else if (s > nw[1]) { out.push(nw); nw = [s, e]; line = 3; }
      else { nw = [Math.min(s, nw[0]), Math.max(e, nw[1])]; line = 4; }
      T.step({ line, key: line === 4, intervals: [{ label: "intervals", items: intervals, hl: { [i]: "cur" } }, { label: "kết quả + khoảng đang chèn", items: [...out, nw].map((x) => [...x]), hl: { [out.length]: "ok" } }], msg: line === 4 ? `[${s}, ${e}] chồng → nới thành [${nw}].` : line === 2 ? `[${s}, ${e}] nằm hẳn bên trái.` : "Đã qua vị trí chèn." });
    });
    out.push(nw);
    return out;
  },
});

register("candy", {
  input: "ratings = [1, 0, 2]",
  expect: 5,
  code: `candies = [1] * n
for i in 1..n-1:           // trái sang phải
  if ratings[i] > ratings[i-1]: candies[i] = candies[i-1] + 1
for i from n-2 down to 0:  // phải sang trái
  if ratings[i] > ratings[i+1]:
    candies[i] = max(candies[i], candies[i+1] + 1)
return sum(candies)`,
  run({ ratings }, T) {
    const n = ratings.length, c = Array(n).fill(1);
    const view = (line, i, msg) => T.step({ line, arrays: [A("ratings", ratings, { hl: { [i]: "cur" } }), A("candies", [...c], { hl: { [i]: "ok" } })], msg });
    for (let i = 1; i < n; i++) if (ratings[i] > ratings[i - 1]) { c[i] = c[i - 1] + 1; view(3, i, `Điểm cao hơn người bên trái → ${c[i]} kẹo.`); }
    for (let i = n - 2; i >= 0; i--) if (ratings[i] > ratings[i + 1]) { c[i] = Math.max(c[i], c[i + 1] + 1); view(6, i, `Điểm cao hơn người bên phải → ít nhất ${c[i]} kẹo.`); }
    const sum = c.reduce((x, y) => x + y, 0);
    T.step({ line: 7, key: true, arrays: [A("candies", c)], vars: { "tổng": sum }, msg: `Tổng ${sum} kẹo.` });
    return sum;
  },
});

register("largest-number", {
  input: "nums = [3, 30, 34, 5, 9]",
  expect: "9534330",
  code: `sort strings so that a + b > b + a puts a first
if first is "0": return "0"
return join(sorted)`,
  run({ nums }, T) {
    const a = nums.map(String);
    for (let i = 1; i < a.length; i++) {
      let j = i;
      while (j > 0 && a[j] + a[j - 1] > a[j - 1] + a[j]) {
        T.step({ line: 1, arrays: [A("nums", [...a], { small: true, hl: { [j]: "cur", [j - 1]: "mark" } })], msg: `"${a[j]}${a[j - 1]}" > "${a[j - 1]}${a[j]}" → đưa ${a[j]} lên trước.` });
        [a[j], a[j - 1]] = [a[j - 1], a[j]];
        j--;
      }
    }
    const out = a[0] === "0" ? "0" : a.join("");
    T.step({ line: 3, key: true, arrays: [A("đã sắp", a, { small: true })], texts: [{ label: "kết quả", value: out }], msg: `Ghép lại: ${out}.` });
    return out;
  },
});

register("the-skyline-problem", {
  input: "buildings = [[2, 9, 10], [3, 7, 15], [5, 12, 12], [15, 20, 10], [19, 24, 8]]",
  expect: [[2, 10], [3, 15], [7, 12], [12, 0], [15, 10], [20, 8], [24, 0]],
  code: `events = (left, -h) and (right, h), sorted
active = {0}, prev = 0
for (x, h) in events:
  if h < 0: active.add(-h) else active.remove(h)
  cur = max(active)
  if cur != prev: result.add((x, cur)); prev = cur
return result`,
  run({ buildings }, T) {
    const ev = buildings.flatMap(([l, r, h]) => [[l, -h], [r, h]]).sort((a, b) => a[0] - b[0] || a[1] - b[1]), active = [0], out = [];
    let prev = 0;
    for (const [x, h] of ev) {
      if (h < 0) active.push(-h); else active.splice(active.indexOf(h), 1);
      const cur = Math.max(...active), change = cur !== prev;
      if (change) { out.push([x, cur]); prev = cur; }
      T.step({ line: change ? 6 : 4, key: change, intervals: [{ label: "các toà nhà", items: buildings.map((b) => [b[0], b[1]]), names: buildings.map((b) => `cao ${b[2]}`), cursor: x, hl: tones(buildings.map((_, i) => i).filter((i) => buildings[i][0] <= x && x < buildings[i][1]), "cur") }], stacks: [Q("chiều cao đang mở", active.filter((v) => v))], vars: { "kết quả": out }, msg: change ? `Tại x = ${x} chiều cao đổi thành ${cur}.` : `Tại x = ${x} chiều cao vẫn là ${cur}.` });
    }
    return out;
  },
});

function mergeCount(slug, input, expect, code, run) {
  register(slug, { input, expect, code, run });
}

mergeCount("count-of-smaller-numbers-after-self", "nums = [5, 2, 6, 1]", [2, 1, 1, 0], `sort(idx):                 // merge sort trên chỉ số
  split, sort both halves
  when taking left item i:
    counts[i] += number of right items already taken
return counts`, ({ nums }, T) => {
  const counts = nums.map(() => 0);
  const sort = (idx) => {
    if (idx.length < 2) return idx;
    const m = idx.length >> 1, a = sort(idx.slice(0, m)), b = sort(idx.slice(m)), out = [];
    let i = 0, j = 0;
    while (i < a.length) {
      while (j < b.length && nums[b[j]] < nums[a[i]]) out.push(b[j++]);
      counts[a[i]] += j;
      T.step({ line: 4, arrays: [A("nums", nums, { hl: { [a[i]]: "cur", ...tones(b.slice(0, j), "mark") } }), A("counts", [...counts], { hl: { [a[i]]: "ok" } })], msg: `${nums[a[i]]}: ${j} số bên phải nhỏ hơn nó.` });
      out.push(a[i++]);
    }
    return [...out, ...b.slice(j)];
  };
  sort(nums.map((_, i) => i));
  return counts;
});

mergeCount("count-of-range-sum", "nums = [-2, 5, -1]\nlower = -2\nupper = 2", 3, `prefix = prefix sums
sort(lo, hi):              // merge sort trên prefix
  for each left p: move two pointers over right
    count += right items in [p + lower, p + upper]
  merge
return count`, ({ nums, lower, upper }, T) => {
  const p = [0];
  nums.forEach((v) => p.push(p[p.length - 1] + v));
  let count = 0;
  const sort = (a) => {
    if (a.length < 2) return a;
    const m = a.length >> 1, l = sort(a.slice(0, m)), r = sort(a.slice(m));
    let lo = 0, hi = 0;
    for (const x of l) {
      while (lo < r.length && r[lo] - x < lower) lo++;
      while (hi < r.length && r[hi] - x <= upper) hi++;
      count += hi - lo;
      T.step({ line: 4, arrays: [A("trái đã sắp", l, { hl: { [l.indexOf(x)]: "cur" } }), A("phải đã sắp", r, { range: [lo, hi - 1] })], vars: { count }, msg: `Với prefix ${x}: ${hi - lo} prefix bên phải cho tổng trong [${lower}, ${upper}].` });
    }
    return [...l, ...r].sort((x, y) => x - y);
  };
  sort(p);
  return count;
});

mergeCount("reverse-pairs", "nums = [1, 3, 2, 3, 1]", 2, `sort(a):                   // merge sort
  split, sort both halves
  for each left x: advance j while x > 2 * right[j]
    count += j
  merge
return count`, ({ nums }, T) => {
  let count = 0;
  const sort = (a) => {
    if (a.length < 2) return a;
    const m = a.length >> 1, l = sort(a.slice(0, m)), r = sort(a.slice(m));
    let j = 0;
    l.forEach((x, i) => {
      while (j < r.length && x > 2 * r[j]) j++;
      count += j;
      T.step({ line: 4, arrays: [A("trái đã sắp", l, { hl: { [i]: "cur" } }), A("phải đã sắp", r, { hl: tones(range(0, j - 1), "mark") })], vars: { count }, msg: `${x} lớn hơn gấp đôi ${j} số bên phải.` });
    });
    return [...l, ...r].sort((x, y) => x - y);
  };
  sort(nums);
  return count;
});

register("patching-array", {
  input: "nums = [1, 5, 10]\nn = 20",
  expect: 2,
  code: `miss = 1, i = 0, patches = 0
while miss <= n:
  if i < len(nums) and nums[i] <= miss:
    miss += nums[i++]
  else:
    miss += miss; patches++
return patches`,
  run({ nums, n }, T) {
    let miss = 1, i = 0, patches = 0;
    while (miss <= n) {
      if (i < nums.length && nums[i] <= miss) {
        miss += nums[i];
        T.step({ line: 4, arrays: [A("nums", nums, { hl: { [i]: "ok" }, ptr: ptrs({ i }) })], vars: { "đã phủ tới": miss - 1, patches }, msg: `Dùng ${nums[i]}: phủ được 1..${miss - 1}.` });
        i++;
      } else {
        T.step({ line: 6, key: true, arrays: [A("nums", nums, { ptr: ptrs({ i }, nums.length) })], vars: { "đã phủ tới": 2 * miss - 1, patches: patches + 1 }, msg: `Thiếu ${miss}: vá thêm số ${miss}, gấp đôi vùng phủ.` });
        miss += miss;
        patches++;
      }
    }
    return patches;
  },
});

register("top-k-frequent-elements", {
  input: "nums = [1, 1, 1, 2, 2, 3]\nk = 2",
  expect: [1, 2],
  code: `count = frequency(nums)
buckets[f] = numbers that occur f times
for f from n down to 1:
  result.add(buckets[f]) until size k
return result`,
  run({ nums, k }, T) {
    const count = new Map();
    nums.forEach((v) => count.set(v, (count.get(v) ?? 0) + 1));
    const n = nums.length, buckets = Array.from({ length: n + 1 }, () => []);
    for (const [v, c] of count) buckets[c].push(v);
    const view = (line, f, res, msg) => T.step({ line, maps: [M("tần suất", count)], arrays: [A("buckets[tần suất]", buckets.map((b) => b.join(",") || "·"), { small: true, hl: { [f]: "cur" } }), A("kết quả", [...res], { noIndex: true })], msg });
    view(2, -1, [], "Xếp số vào bucket theo tần suất.");
    const res = [];
    for (let f = n; f >= 1 && res.length < k; f--) for (const v of buckets[f]) {
      res.push(v);
      view(4, f, res, `Lấy ${v} (xuất hiện ${f} lần).`);
      if (res.length === k) break;
    }
    return res;
  },
});

register("non-overlapping-intervals", {
  input: "intervals = [[1, 2], [2, 3], [3, 4], [1, 3]]",
  expect: 1,
  code: `sort by end
end = -inf, removed = 0
for (s, e) in intervals:
  if s >= end: end = e         // giữ, kết thúc sớm nhất
  else: removed++
return removed`,
  run({ intervals }, T) {
    const a = [...intervals].sort((x, y) => x[1] - y[1]), hl = {};
    let end = -Infinity, removed = 0;
    a.forEach(([s, e], i) => {
      const keep = s >= end;
      if (keep) end = e; else removed++;
      hl[i] = keep ? "ok" : "bad";
      T.step({ line: keep ? 4 : 5, intervals: [{ label: "sắp theo điểm kết thúc", items: a, hl: { ...hl } }], vars: { removed }, msg: keep ? `Giữ [${s}, ${e}].` : `[${s}, ${e}] chồng với khoảng đã giữ → bỏ.` });
    });
    return removed;
  },
});

register("ipo", {
  input: "k = 2\nw = 0\nprofits = [1, 2, 3]\ncapital = [0, 1, 1]",
  expect: 4,
  code: `sort projects by capital
heap = max-heap of profits
repeat k times:
  push all projects with capital <= w
  if heap empty: break
  w += heap.pop()
return w`,
  run({ k, w, profits, capital }, T) {
    const p = profits.map((x, i) => [capital[i], x]).sort((a, b) => a[0] - b[0]), heap = new Heap((a, b) => a > b);
    let i = 0;
    for (let t = 0; t < k; t++) {
      while (i < p.length && p[i][0] <= w) heap.push(p[i++][1]);
      T.step({ line: 4, arrays: [A("vốn cần", p.map((x) => x[0]), { hl: tones(range(0, i - 1), "done") }), A("lợi nhuận", p.map((x) => x[1]), { hl: tones(range(0, i - 1), "done") })], tree: heap.tree("max-heap lợi nhuận", undefined, { 0: "ok" }), vars: { "vốn": w }, msg: `Với vốn ${w}, các dự án có thể làm đã vào heap.` });
      if (!heap.size) break;
      const g = heap.pop();
      w += g;
      T.step({ line: 6, key: true, tree: heap.tree("max-heap lợi nhuận"), vars: { "vốn": w }, msg: `Chọn dự án lời ${g}, vốn thành ${w}.` });
    }
    return w;
  },
});

register("task-scheduler", {
  input: 'tasks = ["A", "A", "A", "B", "B", "B"]\nn = 2',
  expect: 8,
  code: `f = max frequency
m = number of tasks with frequency f
return max(len(tasks), (f - 1) * (n + 1) + m)`,
  run({ tasks, n }, T) {
    const count = new Map();
    tasks.forEach((t) => count.set(t, (count.get(t) ?? 0) + 1));
    const f = Math.max(...count.values()), m = [...count.values()].filter((c) => c === f).length;
    T.step({ line: 2, maps: [M("tần suất", count)], vars: { f, m }, msg: `Việc nhiều nhất làm ${f} lần, có ${m} việc như vậy.` });
    const frame = Array.from({ length: f }, () => Array(n + 1).fill(null));
    const sorted = [...count].sort((a, b) => b[1] - a[1]);
    let col = 0;
    for (const [t, c] of sorted) {
      for (let r = 0; r < c; r++) frame[r][col % (n + 1)] = t;
      col++;
      T.step({ line: 3, maps: [M("tần suất", count)], grids: [G("khung thời gian", frame.map((r) => [...r]), { hl: {} })], msg: `Xếp ${c} việc ${t} vào từng hàng.` });
    }
    const r = Math.max(tasks.length, (f - 1) * (n + 1) + m);
    T.step({ line: 3, key: true, grids: [G("khung thời gian", frame)], vars: { "kết quả": r }, msg: `(${f} − 1) × ${n + 1} + ${m} = ${(f - 1) * (n + 1) + m}, so với ${tasks.length} việc → ${r}.` });
    return r;
  },
});

register("course-schedule-iii", {
  input: "courses = [[100, 200], [200, 1300], [1000, 1250], [2000, 3200]]",
  expect: 3,
  code: `sort by deadline
heap = max-heap of durations, t = 0
for (d, last) in courses:
  heap.push(d); t += d
  if t > last: t -= heap.pop()    // bỏ môn dài nhất
return len(heap)`,
  run({ courses }, T) {
    const a = [...courses].sort((x, y) => x[1] - y[1]), heap = new Heap((x, y) => x > y);
    let t = 0;
    a.forEach(([d, last], i) => {
      heap.push(d);
      t += d;
      const over = t > last;
      T.step({ line: 4, arrays: [A("hạn chót", a.map((x) => x[1]), { hl: { [i]: over ? "bad" : "cur" } })], tree: heap.tree("thời lượng đã chọn", undefined, over ? { 0: "bad" } : {}), vars: { t }, msg: over ? `Tổng ${t} vượt hạn ${last} → bỏ môn dài nhất.` : `Thêm môn dài ${d}, tổng ${t}.` });
      if (over) t -= heap.pop();
    });
    return heap.size;
  },
});

register("rectangle-area-ii", {
  input: "rectangles = [[0, 0, 2, 2], [1, 0, 2, 3], [1, 0, 3, 1]]",
  expect: 6,
  code: `xs = sorted distinct x coordinates
for each strip [xs[i], xs[i+1]]:
  active = rectangles covering the strip
  covered = length of union of their y ranges
  area += covered * strip width
return area`,
  run({ rectangles: r }, T) {
    const xs = [...new Set(r.flatMap((x) => [x[0], x[2]]))].sort((a, b) => a - b);
    let area = 0;
    for (let i = 0; i + 1 < xs.length; i++) {
      const a = xs[i], b = xs[i + 1];
      const act = r.filter((x) => x[0] <= a && x[2] >= b).map((x) => [x[1], x[3]]).sort((p, q) => p[0] - q[0]);
      let cov = 0, end = -Infinity;
      for (const [s, e] of act) { if (s > end) { cov += e - s; end = e; } else if (e > end) { cov += e - end; end = e; } }
      area += cov * (b - a);
      T.step({ line: 5, key: true, intervals: [{ label: "các hình chữ nhật (theo x)", items: r.map((x) => [x[0], x[2]]), hl: tones(r.map((_, k) => k).filter((k) => r[k][0] <= a && r[k][2] >= b), "cur"), cursor: a }], vars: { "dải x": `${a}..${b}`, "phủ y": cov, area }, msg: `Dải rộng ${b - a}, phủ ${cov} theo y: thêm ${cov * (b - a)}.` });
    }
    return area;
  },
});

register("minimum-cost-to-hire-k-workers", {
  input: "quality = [10, 20, 5]\nwage = [70, 50, 30]\nk = 2",
  expect: 105,
  code: `sort workers by wage / quality
heap = max-heap of quality, sum = 0
for w in workers:
  heap.push(w.quality); sum += w.quality
  if size > k: sum -= heap.pop()
  if size == k: best = min(best, w.ratio * sum)
return best`,
  run({ quality, wage, k }, T) {
    const w = quality.map((q, i) => [wage[i] / q, q]).sort((a, b) => a[0] - b[0]), heap = new Heap((a, b) => a > b);
    let sum = 0, best = Infinity;
    w.forEach(([ratio, q], i) => {
      heap.push(q);
      sum += q;
      if (heap.size > k) sum -= heap.pop();
      const full = heap.size === k, cost = ratio * sum, up = full && cost < best;
      if (up) best = cost;
      T.step({ line: 6, key: up, arrays: [A("tỉ lệ lương/chất lượng", w.map((x) => Number(x[0].toFixed(2))), { hl: { [i]: "cur" } })], tree: heap.tree("chất lượng đang thuê", undefined, {}), vars: { "tổng chất lượng": sum, best: best === Infinity ? "∞" : best }, msg: full ? `Trả theo tỉ lệ ${ratio.toFixed(2)}: ${cost.toFixed(2)}.` : "Chưa đủ người." });
    });
    return best;
  },
});

register("minimum-number-of-refueling-stops", {
  input: "target = 100\nstartFuel = 10\nstations = [[10, 60], [20, 30], [30, 30], [60, 40]]",
  expect: 2,
  code: `heap = max-heap, reach = startFuel, stops = 0, i = 0
while reach < target:
  push fuel of stations within reach
  if heap empty: return -1
  reach += heap.pop(); stops++
return stops`,
  run({ target, startFuel, stations }, T) {
    const heap = new Heap((a, b) => a > b);
    let reach = startFuel, stops = 0, i = 0;
    while (reach < target) {
      while (i < stations.length && stations[i][0] <= reach) heap.push(stations[i++][1]);
      if (!heap.size) { T.step({ line: 4, key: true, stacks: [Q("xăng có thể đổ", [])], vars: { reach }, msg: "Hết trạm trong tầm." }); return -1; }
      T.step({ line: 3, intervals: [{ label: "tầm đi được", items: [[0, reach]], axis: [0, target], cursor: reach, names: [`tới ${reach}`], hl: { 0: "win" } }], tree: heap.tree("xăng ở các trạm đã qua", undefined, { 0: "ok" }), vars: { stops }, msg: "Chỉ đổ khi buộc phải đổ, và đổ ở trạm nhiều xăng nhất đã đi qua." });
      const f = heap.pop();
      reach += f;
      stops++;
      T.step({ line: 5, key: true, intervals: [{ label: "tầm đi được", items: [[0, Math.min(reach, target)]], axis: [0, target], names: [`tới ${reach}`], hl: { 0: "ok" } }], vars: { stops }, msg: `Đổ ${f} lít, tầm mới ${reach}.` });
    }
    return stops;
  },
});

register("k-closest-points-to-origin", {
  input: "points = [[3, 3], [5, -1], [-2, 4]]\nk = 2",
  expect: [[3, 3], [-2, 4]],
  code: `heap = max-heap by distance
for p in points:
  heap.push(p)
  if len(heap) > k: heap.pop()
return heap items`,
  run({ points, k }, T) {
    const d = (p) => p[0] ** 2 + p[1] ** 2, heap = new Heap((a, b) => d(a) > d(b));
    points.forEach((p) => {
      heap.push(p);
      let dropped = null;
      if (heap.size > k) dropped = heap.pop();
      const keep = heap.a.map((x) => points.indexOf(x));
      T.step({ line: 4, points: [{ label: "mặt phẳng", pts: [...points, [0, 0]], hl: { ...tones(keep, "ok"), [points.length]: "mark", ...(dropped ? { [points.indexOf(dropped)]: "bad" } : {}) } }], stacks: [S("heap (xa nhất ở đỉnh)", heap.a.map((x) => `(${x}) d²=${d(x)}`))], msg: dropped ? `Quá ${k} điểm: bỏ (${dropped}) xa nhất.` : `Giữ (${p}).` });
    });
    return [...heap.a].sort((a, b) => d(a) - d(b));
  },
});

register("last-stone-weight", {
  input: "stones = [2, 7, 4, 1, 8, 1]",
  expect: 1,
  code: `heap = max-heap(stones)
while len(heap) > 1:
  a = heap.pop(), b = heap.pop()
  if a != b: heap.push(a - b)
return heap.top or 0`,
  run({ stones }, T) {
    const heap = new Heap((a, b) => a > b);
    stones.forEach((s) => heap.push(s));
    T.step({ line: 1, tree: heap.tree("max-heap"), msg: "Đưa mọi viên đá vào max-heap." });
    while (heap.size > 1) {
      const a = heap.pop(), b = heap.pop();
      if (a !== b) heap.push(a - b);
      T.step({ line: 4, tree: heap.tree("max-heap"), vars: { a, b }, msg: a === b ? `${a} và ${b} cùng vỡ.` : `${a} đập ${b} còn ${a - b}.` });
    }
    return heap.peek() ?? 0;
  },
});

register("maximum-profit-in-job-scheduling", {
  input: "startTime = [1, 2, 3, 3]\nendTime = [3, 4, 5, 6]\nprofit = [50, 10, 40, 70]",
  expect: 120,
  code: `sort jobs by end
dp[i] = best profit using first i jobs
for i in 1..n:
  j = number of jobs ending <= jobs[i-1].start
  dp[i] = max(dp[i-1], dp[j] + jobs[i-1].profit)
return dp[n]`,
  run({ startTime, endTime, profit }, T) {
    const jobs = startTime.map((s, i) => [s, endTime[i], profit[i]]).sort((a, b) => a[1] - b[1]), n = jobs.length, dp = Array(n + 1).fill(0);
    jobs.forEach((job, i) => {
      let j = 0;
      while (j < i && jobs[j][1] <= job[0]) j++;
      const take = dp[j] + job[2];
      dp[i + 1] = Math.max(dp[i], take);
      T.step({ line: 5, key: take > dp[i], intervals: [{ label: "việc (sắp theo giờ xong)", items: jobs.map((x) => [x[0], x[1]]), names: jobs.map((x) => `lời ${x[2]}`), hl: { [i]: "cur", ...tones(range(0, j - 1), "mark") } }], arrays: [A("dp", [...dp], { hl: { [i + 1]: "ok", [j]: "mark" } })], msg: `Làm việc này: ${dp[j]} + ${job[2]} = ${take}. Bỏ qua: ${dp[i]}.` });
    });
    return dp[n];
  },
});

register("meeting-rooms-iii", {
  input: "n = 2\nmeetings = [[0, 10], [1, 5], [2, 7], [3, 4]]",
  expect: 0,
  code: `free = min-heap of rooms, busy = min-heap (end, room)
for (s, e) in sorted meetings:
  release rooms with end <= s
  if free not empty: room = free.pop(); end = e
  else: (t, room) = busy.pop(); end = t + (e - s)
  busy.push((end, room)); count[room]++
return room with largest count`,
  run({ n, meetings }, T) {
    const m = [...meetings].sort((a, b) => a[0] - b[0]), free = new Heap(), busy = new Heap((a, b) => a[0] < b[0] || (a[0] === b[0] && a[1] < b[1])), count = Array(n).fill(0);
    range(0, n - 1).forEach((r) => free.push(r));
    m.forEach(([s, e], i) => {
      while (busy.size && busy.peek()[0] <= s) free.push(busy.pop()[1]);
      let room, end;
      if (free.size) { room = free.pop(); end = e; } else { const [t, r] = busy.pop(); room = r; end = t + e - s; }
      busy.push([end, room]);
      count[room]++;
      T.step({ line: 6, intervals: [{ label: "các cuộc họp", items: m, hl: { [i]: "cur" } }], arrays: [A("số lần dùng phòng", [...count], { hl: { [room]: "ok" } })], stacks: [Q("đang bận (giờ xong:phòng)", busy.a.map((x) => `${x[0]}:${x[1]}`))], msg: end === e ? `Họp [${s}, ${e}) vào phòng ${room}.` : `Hết phòng: dời sang phòng ${room}, kết thúc lúc ${end}.` });
    });
    return count.indexOf(Math.max(...count));
  },
});

