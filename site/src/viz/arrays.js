/* Arrays: two pointers, in-place rewrites, counting, prefix sums. */
import { A, M, chars, ptrs, register, tones } from "./engine.js";

register("3sum-closest", {
  input: "nums = [-1, 2, 1, -4]\ntarget = 1",
  expect: 2,
  code: `sort(nums)
best = nums[0] + nums[1] + nums[2]
for i in 0..n-3:
  l = i + 1, r = n - 1
  while l < r:
    sum = nums[i] + nums[l] + nums[r]
    if |sum - target| < |best - target|: best = sum
    if sum == target: return sum
    if sum < target: l++ else r--
return best`,
  run({ nums, target }, T) {
    const a = [...nums].sort((x, y) => x - y);
    const n = a.length;
    let best = a[0] + a[1] + a[2];
    T.step({ line: 2, arrays: [A("nums (đã sắp xếp)", a)], vars: { best }, msg: `Sắp xếp xong. Khởi tạo best bằng tổng ba số đầu: ${best}.` });
    for (let i = 0; i < n - 2; i++) {
      let l = i + 1, r = n - 1;
      while (l < r) {
        const sum = a[i] + a[l] + a[r];
        const better = Math.abs(sum - target) < Math.abs(best - target);
        if (better) best = sum;
        const view = (line, msg, key) => T.step({ line, key, arrays: [A("nums", a, { range: [l, r], hl: { [i]: "mark", [l]: "cur", [r]: "cur" }, ptr: ptrs({ i, l, r }) })], vars: { sum, "|sum − target|": Math.abs(sum - target), best }, msg });
        view(better ? 7 : 6, better ? `Tổng ${sum} gần ${target} hơn → best = ${sum}.` : `Tổng ${sum} không gần hơn best = ${best}.`, better);
        if (sum === target) { view(8, "Khớp đúng target, không thể gần hơn.", true); return sum; }
        if (sum < target) { view(9, `${sum} < ${target} → cần tổng lớn hơn, tăng l.`); l++; } else { view(9, `${sum} > ${target} → cần tổng nhỏ hơn, giảm r.`); r--; }
      }
    }
    T.step({ line: 10, arrays: [A("nums", a)], vars: { best }, msg: `Duyệt xong. Tổng gần nhất là ${best}.`, key: true });
    return best;
  },
});

register("4sum", {
  input: "nums = [1, 0, -1, 0, -2, 2]\ntarget = 0",
  expect: [[-2, -1, 1, 2], [-2, 0, 0, 2], [-1, 0, 0, 1]],
  code: `sort(nums)
for i in 0..n-4:            // bỏ qua nums[i] trùng
  for j in i+1..n-3:        // bỏ qua nums[j] trùng
    l = j + 1, r = n - 1
    while l < r:
      sum = nums[i] + nums[j] + nums[l] + nums[r]
      if sum == target:
        add [i, j, l, r]; skip dup; l++, r--
      else if sum < target: l++
      else: r--`,
  run({ nums, target }, T) {
    const a = [...nums].sort((x, y) => x - y);
    const n = a.length, res = [];
    T.step({ line: 1, arrays: [A("nums (đã sắp xếp)", a)], msg: "Sắp xếp để chặn trùng và dùng hai con trỏ." });
    for (let i = 0; i < n - 3; i++) {
      if (i && a[i] === a[i - 1]) continue;
      for (let j = i + 1; j < n - 2; j++) {
        if (j > i + 1 && a[j] === a[j - 1]) continue;
        let l = j + 1, r = n - 1;
        while (l < r) {
          const sum = a[i] + a[j] + a[l] + a[r];
          const hit = sum === target;
          T.step({ line: hit ? 8 : sum < target ? 9 : 10, key: hit, arrays: [A("nums", a, { range: [l, r], hl: { [i]: "mark", [j]: "mark", [l]: hit ? "ok" : "cur", [r]: hit ? "ok" : "cur" }, ptr: ptrs({ i, j, l, r }) })], vars: { sum, "kết quả": res }, msg: hit ? `${a[i]} + ${a[j]} + ${a[l]} + ${a[r]} = ${target} ✓ thêm vào kết quả.` : sum < target ? `Tổng ${sum} < ${target} → tăng l.` : `Tổng ${sum} > ${target} → giảm r.` });
          if (hit) {
            res.push([a[i], a[j], a[l], a[r]]);
            while (l < r && a[l] === a[l + 1]) l++;
            while (l < r && a[r] === a[r - 1]) r--;
            l++; r--;
          } else if (sum < target) l++;
          else r--;
        }
      }
    }
    T.step({ line: 2, arrays: [A("nums", a)], vars: { "kết quả": res }, msg: `Xong, tìm được ${res.length} bộ bốn.`, key: true });
    return res;
  },
});

function inPlace(slug, input, expect, code, keep, stepLine, describe) {
  register(slug, {
    input, expect, code,
    run(args, T) {
      const a = [...args.nums];
      let k = 0;
      for (let i = 0; i < a.length; i++) {
        const take = keep(a, k, i, args);
        T.step({ line: stepLine, arrays: [A("nums", a, { hl: { ...tones([...Array(k).keys()], "done"), [i]: take ? "ok" : "bad" }, ptr: ptrs({ k, i }, a.length) })], vars: { k }, msg: describe(a[i], take) });
        if (take) {
          a[k] = a[i];
          T.step({ line: stepLine + 1, arrays: [A("nums", a, { hl: { ...tones([...Array(k).keys()], "done"), [k]: "ok" }, ptr: ptrs({ k, i }, a.length) })], vars: { k: k + 1 }, msg: `Ghi ${a[k]} vào vị trí ${k}, k tăng thành ${k + 1}.` });
          k++;
        }
      }
      T.step({ line: stepLine + 2, key: true, arrays: [A("nums", a, { range: [0, k - 1], dimOutside: true })], vars: { k }, msg: `Xong. ${k} phần tử đầu là kết quả.` });
      return a.slice(0, k);
    },
  });
}

inPlace("remove-duplicates-from-sorted-array", "nums = [0, 0, 1, 1, 1, 2, 2, 3, 3, 4]", [0, 1, 2, 3, 4], `k = 0
for i in 0..n-1:
  if k == 0 or nums[i] != nums[k-1]:
    nums[k++] = nums[i]
return k`, (a, k, i) => k === 0 || a[i] !== a[k - 1], 3, (v, t) => (t ? `${v} khác phần tử cuối đã giữ → giữ lại.` : `${v} trùng với phần tử cuối đã giữ → bỏ qua.`));

inPlace("remove-element", "nums = [0, 1, 2, 2, 3, 0, 4, 2]\nval = 2", [0, 1, 3, 0, 4], `k = 0
for i in 0..n-1:
  if nums[i] != val:
    nums[k++] = nums[i]
return k`, (a, k, i, { val }) => a[i] !== val, 3, (v, t) => (t ? `${v} khác val → giữ lại.` : `${v} bằng val → bỏ qua.`));

inPlace("remove-duplicates-from-sorted-array-ii", "nums = [0, 0, 1, 1, 1, 1, 2, 3, 3]", [0, 0, 1, 1, 2, 3, 3], `k = 0
for i in 0..n-1:
  if k < 2 or nums[i] != nums[k-2]:
    nums[k++] = nums[i]
return k`, (a, k, i) => k < 2 || a[i] !== a[k - 2], 3, (v, t) => (t ? `${v} chưa xuất hiện quá 2 lần → giữ lại.` : `${v} đã có đủ 2 bản → bỏ qua.`));

register("first-missing-positive", {
  input: "nums = [3, 4, -1, 1]",
  expect: 2,
  code: `for i in 0..n-1:
  while 1 <= nums[i] <= n and nums[nums[i]-1] != nums[i]:
    swap(nums[i], nums[nums[i]-1])
for i in 0..n-1:
  if nums[i] != i + 1: return i + 1
return n + 1`,
  run({ nums }, T) {
    const a = [...nums], n = a.length;
    const labels = a.map((_, i) => `→${i + 1}`);
    for (let i = 0; i < n; i++) {
      T.step({ line: 1, arrays: [A("nums", a, { hl: { [i]: "cur" }, ptr: ptrs({ i }), indexLabels: labels })], msg: `Xét vị trí ${i}: mong muốn chứa giá trị ${i + 1}.` });
      while (a[i] >= 1 && a[i] <= n && a[a[i] - 1] !== a[i]) {
        const j = a[i] - 1, v = a[i];
        T.step({ line: 2, arrays: [A("nums", a, { hl: { [i]: "cur", [j]: "mark" }, ptr: ptrs({ i, j }), indexLabels: labels })], msg: `${v} thuộc về vị trí ${j} → đổi chỗ.` });
        [a[i], a[j]] = [a[j], a[i]];
        T.step({ line: 3, arrays: [A("nums", a, { hl: { [j]: "ok", [i]: "cur" }, ptr: ptrs({ i, j }), indexLabels: labels })], msg: `Đã đưa ${v} về đúng chỗ.` });
      }
    }
    for (let i = 0; i < n; i++) {
      const ok = a[i] === i + 1;
      T.step({ line: 5, key: !ok, arrays: [A("nums", a, { hl: { [i]: ok ? "ok" : "bad" }, ptr: ptrs({ i }), indexLabels: labels })], msg: ok ? `nums[${i}] = ${i + 1} đúng chỗ.` : `nums[${i}] ≠ ${i + 1} → ${i + 1} là số dương nhỏ nhất bị thiếu.` });
      if (!ok) return i + 1;
    }
    T.step({ line: 6, key: true, arrays: [A("nums", a)], msg: `Mọi vị trí đều đúng → đáp án ${n + 1}.` });
    return n + 1;
  },
});

register("plus-one", {
  input: "digits = [1, 2, 9, 9]",
  expect: [1, 3, 0, 0],
  code: `for i from n-1 down to 0:
  if digits[i] < 9:
    digits[i]++
    return digits
  digits[i] = 0
return [1] + digits`,
  run({ digits }, T) {
    const a = [...digits];
    for (let i = a.length - 1; i >= 0; i--) {
      T.step({ line: 2, arrays: [A("digits", a, { hl: { [i]: "cur" }, ptr: ptrs({ i }) })], msg: `Xét chữ số ${a[i]} ở vị trí ${i}.` });
      if (a[i] < 9) {
        a[i]++;
        T.step({ line: 3, key: true, arrays: [A("digits", a, { hl: { [i]: "ok" }, ptr: ptrs({ i }) })], msg: `Nhỏ hơn 9 nên cộng 1 là xong, không còn nhớ.` });
        return a;
      }
      a[i] = 0;
      T.step({ line: 5, arrays: [A("digits", a, { hl: { [i]: "mark" }, ptr: ptrs({ i }) })], msg: "9 + 1 = 10: ghi 0, nhớ 1 sang bên trái." });
    }
    const out = [1, ...a];
    T.step({ line: 6, key: true, arrays: [A("digits", out, { hl: { 0: "ok" } })], msg: "Nhớ tràn khỏi đầu số → thêm chữ số 1." });
    return out;
  },
});

register("pascals-triangle", {
  input: "numRows = 5",
  expect: [[1], [1, 1], [1, 2, 1], [1, 3, 3, 1], [1, 4, 6, 4, 1]],
  code: `rows = []
for r in 0..numRows-1:
  row = [1] * (r + 1)
  for c in 1..r-1:
    row[c] = rows[r-1][c-1] + rows[r-1][c]
  rows.add(row)
return rows`,
  run({ numRows }, T) {
    const rows = [];
    const view = (hl, line, msg, key) => T.step({ line, key, grids: [{ label: "tam giác Pascal", cells: rows.map((r) => [...r, ...Array(numRows - r.length).fill(null)]), hl }], msg });
    for (let r = 0; r < numRows; r++) {
      rows.push(Array(r + 1).fill(1));
      view({ [r + ",0"]: "ok", [r + "," + r]: "ok" }, 3, `Hàng ${r}: hai đầu luôn là 1.`);
      for (let c = 1; c < r; c++) {
        rows[r][c] = rows[r - 1][c - 1] + rows[r - 1][c];
        view({ [r + "," + c]: "cur", [r - 1 + "," + (c - 1)]: "mark", [r - 1 + "," + c]: "mark" }, 5, `${rows[r - 1][c - 1]} + ${rows[r - 1][c]} = ${rows[r][c]}.`);
      }
    }
    view({}, 7, `Đủ ${numRows} hàng.`, true);
    return rows;
  },
});

register("pascals-triangle-ii", {
  input: "rowIndex = 4",
  expect: [1, 4, 6, 4, 1],
  code: `row = [1] + [0] * rowIndex
for r in 1..rowIndex:
  for c from r down to 1:   // đi ngược để không ghi đè
    row[c] += row[c-1]
return row`,
  run({ rowIndex }, T) {
    const row = [1, ...Array(rowIndex).fill(0)];
    T.step({ line: 1, arrays: [A("row", row)], msg: "Chỉ dùng một mảng, hàng đầu là [1]." });
    for (let r = 1; r <= rowIndex; r++) {
      for (let c = r; c >= 1; c--) {
        const old = row[c];
        row[c] += row[c - 1];
        T.step({ line: 4, arrays: [A("row", row, { hl: { [c]: "cur", [c - 1]: "mark" }, ptr: ptrs({ c }) })], vars: { hàng: r }, msg: `row[${c}] = ${old} + ${row[c - 1]} = ${row[c]}. Đi từ phải sang để row[${c - 1}] còn là giá trị hàng trước.` });
      }
    }
    T.step({ line: 5, key: true, arrays: [A("row", row, { hl: tones(row.map((_, i) => i), "done") })], msg: `Hàng ${rowIndex} đã xong.` });
    return row;
  },
});

register("best-time-to-buy-and-sell-stock-ii", {
  input: "prices = [7, 1, 5, 3, 6, 4]",
  expect: 7,
  code: `profit = 0
for i in 1..n-1:
  if prices[i] > prices[i-1]:
    profit += prices[i] - prices[i-1]
return profit`,
  run({ prices }, T) {
    let profit = 0;
    for (let i = 1; i < prices.length; i++) {
      const gain = prices[i] - prices[i - 1];
      if (gain > 0) profit += gain;
      T.step({ line: gain > 0 ? 4 : 3, key: gain > 0, arrays: [A("prices", prices, { bars: true, hl: { [i - 1]: "mark", [i]: gain > 0 ? "ok" : "dim" }, ptr: ptrs({ "hôm qua": i - 1, "hôm nay": i }) })], vars: { profit }, msg: gain > 0 ? `Giá tăng ${gain}: gom hết đoạn tăng này, profit = ${profit}.` : "Giá không tăng → không giao dịch." });
    }
    T.step({ line: 5, arrays: [A("prices", prices, { bars: true })], vars: { profit }, msg: `Tổng lãi của mọi đoạn tăng: ${profit}.` });
    return profit;
  },
});

register("longest-consecutive-sequence", {
  input: "nums = [100, 4, 200, 1, 3, 2]",
  expect: 4,
  code: `set = new HashSet(nums)
best = 0
for x in set:
  if x - 1 in set: continue    // không phải đầu dãy
  y = x
  while y + 1 in set: y++
  best = max(best, y - x + 1)
return best`,
  run({ nums }, T) {
    const set = new Set(nums);
    let best = 0;
    const view = (hl, line, msg, vars, key) => T.step({ line, key, maps: [M("set", set, { hl })], vars: { best, ...vars }, msg });
    view({}, 1, `Đưa ${set.size} số vào set để tra O(1).`);
    for (const x of set) {
      if (set.has(x - 1)) { view({ [x]: "dim", [x - 1]: "mark" }, 4, `${x - 1} có trong set → ${x} không phải đầu dãy, bỏ qua.`, { x }); continue; }
      let y = x;
      const hl = { [x]: "cur" };
      view(hl, 5, `${x} là đầu dãy, bắt đầu đếm.`, { x, y });
      while (set.has(y + 1)) {
        y++;
        hl[y] = "ok";
        view(hl, 6, `Có ${y}, dãy dài ${y - x + 1}.`, { x, y });
      }
      best = Math.max(best, y - x + 1);
      view(hl, 7, `Dãy ${x}…${y} dài ${y - x + 1}. best = ${best}.`, { x, y }, true);
    }
    return best;
  },
});

const xorRow = (v) => (v >>> 0).toString(2).padStart(8, "0");

register("single-number", {
  input: "nums = [4, 1, 2, 1, 2]",
  expect: 4,
  code: `x = 0
for v in nums:
  x ^= v       // a ^ a = 0, a ^ 0 = a
return x`,
  run({ nums }, T) {
    let x = 0;
    nums.forEach((v, i) => {
      const before = x;
      x ^= v;
      T.step({ line: 3, arrays: [A("nums", nums, { hl: { [i]: "cur", ...tones([...Array(i).keys()], "done") }, ptr: ptrs({ i }) })], vars: { x: before, v, "x ^ v": x, "nhị phân": `${xorRow(before)} ^ ${xorRow(v)} = ${xorRow(x)}` }, msg: `${before} ^ ${v} = ${x}. Hai số giống nhau sẽ triệt tiêu.` });
    });
    T.step({ line: 4, key: true, arrays: [A("nums", nums)], vars: { x }, msg: `Mọi cặp đã triệt tiêu, còn lại ${x}.` });
    return x;
  },
});

register("single-number-ii", {
  input: "nums = [2, 2, 3, 2]",
  expect: 3,
  code: `ones = 0, twos = 0
for v in nums:
  ones = (ones ^ v) & ~twos
  twos = (twos ^ v) & ~ones
return ones`,
  run({ nums }, T) {
    let ones = 0, twos = 0;
    nums.forEach((v, i) => {
      ones = (ones ^ v) & ~twos;
      T.step({ line: 3, arrays: [A("nums", nums, { hl: { [i]: "cur" }, ptr: ptrs({ i }) })], vars: { v, ones: xorRow(ones), twos: xorRow(twos) }, msg: `ones giữ các bit đã xuất hiện 1 lần (mod 3).` });
      twos = (twos ^ v) & ~ones;
      T.step({ line: 4, arrays: [A("nums", nums, { hl: { [i]: "cur" }, ptr: ptrs({ i }) })], vars: { v, ones: xorRow(ones), twos: xorRow(twos) }, msg: `twos giữ các bit đã xuất hiện 2 lần. Đủ 3 lần thì cả hai về 0.` });
    });
    T.step({ line: 5, key: true, arrays: [A("nums", nums)], vars: { ones }, msg: `Bit nào còn trong ones thuộc về số chỉ xuất hiện một lần: ${ones}.` });
    return ones;
  },
});

register("two-sum-ii-input-array-is-sorted", {
  input: "numbers = [2, 7, 11, 15]\ntarget = 9",
  expect: [1, 2],
  code: `l = 0, r = n - 1
while l < r:
  sum = numbers[l] + numbers[r]
  if sum == target: return [l + 1, r + 1]
  if sum < target: l++
  else: r--`,
  run({ numbers, target }, T) {
    let l = 0, r = numbers.length - 1;
    while (l < r) {
      const sum = numbers[l] + numbers[r], hit = sum === target;
      T.step({ line: hit ? 4 : sum < target ? 5 : 6, key: hit, arrays: [A("numbers", numbers, { range: [l, r], hl: { [l]: hit ? "ok" : "cur", [r]: hit ? "ok" : "cur" }, ptr: ptrs({ l, r }) })], vars: { sum, target }, msg: hit ? `${numbers[l]} + ${numbers[r]} = ${target} ✓` : sum < target ? `Tổng ${sum} nhỏ quá → tăng l.` : `Tổng ${sum} lớn quá → giảm r.` });
      if (hit) return [l + 1, r + 1];
      if (sum < target) l++; else r--;
    }
    return [];
  },
});

register("contains-duplicate", {
  input: "nums = [1, 2, 3, 1]",
  expect: true,
  code: `seen = new HashSet()
for v in nums:
  if v in seen: return true
  seen.add(v)
return false`,
  run({ nums }, T) {
    const seen = new Set();
    for (let i = 0; i < nums.length; i++) {
      const v = nums[i], dup = seen.has(v);
      T.step({ line: 3, key: dup, arrays: [A("nums", nums, { hl: { [i]: dup ? "bad" : "cur" }, ptr: ptrs({ i }) })], maps: [M("seen", seen, { hl: dup ? { [v]: "bad" } : {} })], msg: dup ? `${v} đã có trong seen → có trùng.` : `${v} chưa gặp.` });
      if (dup) return true;
      seen.add(v);
      T.step({ line: 4, arrays: [A("nums", nums, { hl: { [i]: "done" }, ptr: ptrs({ i }) })], maps: [M("seen", seen, { hl: { [v]: "ok" } })], msg: `Thêm ${v} vào seen.` });
    }
    T.step({ line: 5, arrays: [A("nums", nums)], maps: [M("seen", seen)], msg: "Duyệt hết, không có số trùng." });
    return false;
  },
});

register("contains-duplicate-ii", {
  input: "nums = [1, 2, 3, 1]\nk = 3",
  expect: true,
  code: `last = new HashMap()
for i in 0..n-1:
  if nums[i] in last and i - last[nums[i]] <= k:
    return true
  last[nums[i]] = i
return false`,
  run({ nums, k }, T) {
    const last = new Map();
    for (let i = 0; i < nums.length; i++) {
      const v = nums[i], j = last.get(v), hit = j !== undefined && i - j <= k;
      T.step({ line: 3, key: hit, arrays: [A("nums", nums, { range: [Math.max(0, i - k), i], hl: { [i]: hit ? "ok" : "cur", ...(j !== undefined ? { [j]: hit ? "ok" : "mark" } : {}) }, ptr: ptrs({ i, j }) })], maps: [M("vị trí gần nhất", last, { hl: { [v]: "mark" } })], msg: j === undefined ? `${v} chưa gặp.` : hit ? `Lần trước ở ${j}, cách ${i - j} ≤ ${k} ✓` : `Lần trước ở ${j}, cách ${i - j} > ${k}.` });
      if (hit) return true;
      last.set(v, i);
      T.step({ line: 5, arrays: [A("nums", nums, { range: [Math.max(0, i - k), i], ptr: ptrs({ i }) })], maps: [M("vị trí gần nhất", last, { hl: { [v]: "ok" } })], msg: `Ghi nhớ ${v} ở vị trí ${i}.` });
    }
    T.step({ line: 6, arrays: [A("nums", nums)], maps: [M("vị trí gần nhất", last)], msg: "Không có cặp nào đủ gần." });
    return false;
  },
});

register("summary-ranges", {
  input: "nums = [0, 1, 2, 4, 5, 7]",
  expect: ["0->2", "4->5", "7"],
  code: `i = 0
while i < n:
  start = i
  while i + 1 < n and nums[i+1] == nums[i] + 1: i++
  add range(nums[start], nums[i])
  i++
return result`,
  run({ nums }, T) {
    const res = [], colors = ["c1", "c2", "c3", "c4", "c5", "c6"], hl = {};
    let i = 0;
    while (i < nums.length) {
      const start = i;
      hl[i] = colors[res.length % 6];
      T.step({ line: 3, arrays: [A("nums", nums, { hl: { ...hl }, ptr: ptrs({ start, i }) })], vars: { "kết quả": res }, msg: `Mở đoạn mới tại ${nums[i]}.` });
      while (i + 1 < nums.length && nums[i + 1] === nums[i] + 1) {
        i++;
        hl[i] = colors[res.length % 6];
        T.step({ line: 4, arrays: [A("nums", nums, { hl: { ...hl }, ptr: ptrs({ start, i }) })], vars: { "kết quả": res }, msg: `${nums[i]} liền sau ${nums[i - 1]} → nối vào đoạn.` });
      }
      res.push(start === i ? `${nums[i]}` : `${nums[start]}->${nums[i]}`);
      T.step({ line: 5, key: true, arrays: [A("nums", nums, { hl: { ...hl }, ptr: ptrs({ start, i }) })], vars: { "kết quả": res }, msg: `Đoạn kết thúc: "${res[res.length - 1]}".` });
      i++;
    }
    return res;
  },
});

register("missing-number", {
  input: "nums = [3, 0, 1]",
  expect: 2,
  code: `x = n
for i in 0..n-1:
  x ^= i ^ nums[i]
return x`,
  run({ nums }, T) {
    let x = nums.length;
    T.step({ line: 1, arrays: [A("nums", nums)], vars: { x }, msg: `Khởi tạo x = n = ${x}. XOR tất cả chỉ số 0..n với tất cả giá trị.` });
    nums.forEach((v, i) => {
      const before = x;
      x ^= i ^ v;
      T.step({ line: 3, arrays: [A("nums", nums, { hl: { [i]: "cur" }, ptr: ptrs({ i }) })], vars: { x }, msg: `${before} ^ ${i} ^ ${v} = ${x}.` });
    });
    T.step({ line: 4, key: true, arrays: [A("nums", nums)], vars: { x }, msg: `Mỗi số có mặt bị triệt tiêu với chỉ số của nó, còn lại số thiếu: ${x}.` });
    return x;
  },
});

register("find-the-duplicate-number", {
  input: "nums = [1, 3, 4, 2, 2]",
  expect: 2,
  code: `slow = fast = nums[0]
do:
  slow = nums[slow]
  fast = nums[nums[fast]]
while slow != fast
slow = nums[0]
while slow != fast:
  slow = nums[slow]; fast = nums[fast]
return slow`,
  run({ nums }, T) {
    let slow = nums[0], fast = nums[0];
    const view = (line, msg, key) => T.step({ line, key, arrays: [A("nums (i → nums[i])", nums, { hl: { [slow]: "cur", [fast]: "mark" }, ptr: ptrs({ slow, fast }) })], graphs: [{ label: "đồ thị i → nums[i]", directed: true, nodes: nums.map((_, i) => ({ id: i })), edges: nums.map((v, i) => ({ a: i, b: v })), hl: slow === fast ? { [slow]: "ok" } : { [slow]: "cur", [fast]: "mark" } }], vars: { slow, fast }, msg });
    view(1, "Coi mảng là đồ thị i → nums[i]. Số trùng tạo ra một chu trình.");
    do {
      slow = nums[slow];
      fast = nums[nums[fast]];
      view(slow === fast ? 5 : 4, slow === fast ? `Rùa và thỏ gặp nhau tại ${slow}.` : "Rùa đi 1 bước, thỏ đi 2 bước.", slow === fast);
    } while (slow !== fast);
    slow = nums[0];
    view(6, "Đưa slow về đầu, cả hai cùng đi 1 bước.");
    while (slow !== fast) {
      slow = nums[slow];
      fast = nums[fast];
      view(8, slow === fast ? `Gặp nhau tại ${slow}: đó là cửa vào chu trình, cũng là số trùng.` : "Mỗi con đi 1 bước.", slow === fast);
    }
    return slow;
  },
});

register("reverse-string", {
  input: 's = ["h", "e", "l", "l", "o"]',
  expect: ["o", "l", "l", "e", "h"],
  code: `l = 0, r = n - 1
while l < r:
  swap(s[l], s[r])
  l++, r--`,
  run({ s }, T) {
    const a = [...s];
    let l = 0, r = a.length - 1;
    const done = {};
    while (l < r) {
      T.step({ line: 3, arrays: [A("s", a, { hl: { ...done, [l]: "cur", [r]: "cur" }, ptr: ptrs({ l, r }) })], msg: `Đổi chỗ '${a[l]}' và '${a[r]}'.` });
      [a[l], a[r]] = [a[r], a[l]];
      done[l] = done[r] = "done";
      T.step({ line: 4, arrays: [A("s", a, { hl: { ...done }, ptr: ptrs({ l, r }) })], msg: "Hai con trỏ tiến vào giữa." });
      l++; r--;
    }
    T.step({ line: 2, key: true, arrays: [A("s", a, { hl: tones(a.map((_, i) => i), "done") })], msg: "Hai con trỏ gặp nhau, chuỗi đã đảo xong." });
    return a;
  },
});

register("reverse-vowels-of-a-string", {
  input: 's = "IceCreAm"',
  expect: "AceCreIm",
  code: `l = 0, r = n - 1
while l < r:
  while l < r and s[l] not vowel: l++
  while l < r and s[r] not vowel: r--
  swap(s[l], s[r])
  l++, r--`,
  run({ s }, T) {
    const a = chars(s), vowel = (c) => "aeiouAEIOU".includes(c);
    let l = 0, r = a.length - 1;
    const hl = {};
    while (l < r) {
      while (l < r && !vowel(a[l])) { T.step({ line: 3, arrays: [A("s", a, { hl: { ...hl, [l]: "dim" }, ptr: ptrs({ l, r }) })], msg: `'${a[l]}' không phải nguyên âm → l tiến.` }); l++; }
      while (l < r && !vowel(a[r])) { T.step({ line: 4, arrays: [A("s", a, { hl: { ...hl, [r]: "dim" }, ptr: ptrs({ l, r }) })], msg: `'${a[r]}' không phải nguyên âm → r lùi.` }); r--; }
      if (l < r) {
        T.step({ line: 5, arrays: [A("s", a, { hl: { ...hl, [l]: "cur", [r]: "cur" }, ptr: ptrs({ l, r }) })], msg: `Đổi hai nguyên âm '${a[l]}' và '${a[r]}'.` });
        [a[l], a[r]] = [a[r], a[l]];
        hl[l] = hl[r] = "done";
        l++; r--;
      }
    }
    const out = a.join("");
    T.step({ line: 2, key: true, arrays: [A("s", a, { hl })], msg: `Kết quả "${out}".` });
    return out;
  },
});

function intersection(slug, input, expect, code, multi) {
  register(slug, {
    input, expect, code,
    run({ nums1, nums2 }, T) {
      const count = new Map();
      for (const v of nums1) count.set(v, (count.get(v) ?? 0) + 1);
      const res = [];
      T.step({ line: 1, arrays: [A("nums1", nums1), A("nums2", nums2)], maps: [M("đếm từ nums1", count)], msg: "Ghi nhớ các số trong nums1." });
      nums2.forEach((v, i) => {
        const hit = (count.get(v) ?? 0) > 0;
        if (hit) { res.push(v); if (multi) count.set(v, count.get(v) - 1); else count.delete(v); }
        T.step({ line: hit ? 4 : 3, key: hit, arrays: [A("nums1", nums1), A("nums2", nums2, { hl: { [i]: hit ? "ok" : "dim" }, ptr: ptrs({ i }) })], maps: [M("đếm từ nums1", count, { hl: { [v]: "cur" } })], vars: { "kết quả": res }, msg: hit ? `${v} có trong bảng → lấy, ${multi ? "giảm số đếm" : "xoá để không lấy lần hai"}.` : `${v} không còn trong bảng.` });
      });
      T.step({ line: 5, arrays: [A("kết quả", res)], msg: `Giao có ${res.length} phần tử.` });
      return res;
    },
  });
}

intersection("intersection-of-two-arrays", "nums1 = [1, 2, 2, 1]\nnums2 = [2, 2]", [2], `set = HashSet(nums1)
for v in nums2:
  if v in set:
    result.add(v); set.remove(v)
return result`, false);

intersection("intersection-of-two-arrays-ii", "nums1 = [4, 9, 5]\nnums2 = [9, 4, 9, 8, 4]", [9, 4], `count = frequency(nums1)
for v in nums2:
  if count[v] > 0:
    result.add(v); count[v]--
return result`, true);

register("third-maximum-number", {
  input: "nums = [2, 2, 3, 1]",
  expect: 1,
  code: `a = b = c = null        // a > b > c
for v in nums:
  if v == a or v == b or v == c: continue
  if a == null or v > a: (a, b, c) = (v, a, b)
  else if b == null or v > b: (b, c) = (v, b)
  else if c == null or v > c: c = v
return c != null ? c : a`,
  run({ nums }, T) {
    let a = null, b = null, c = null;
    nums.forEach((v, i) => {
      let line, msg;
      if (v === a || v === b || v === c) { line = 3; msg = `${v} đã có trong top 3 → bỏ qua.`; }
      else if (a === null || v > a) { [a, b, c] = [v, a, b]; line = 4; msg = `${v} là lớn nhất mới, đẩy các số xuống.`; }
      else if (b === null || v > b) { [b, c] = [v, b]; line = 5; msg = `${v} thành số lớn thứ hai.`; }
      else if (c === null || v > c) { c = v; line = 6; msg = `${v} thành số lớn thứ ba.`; }
      else { line = 3; msg = `${v} quá nhỏ, không vào top 3.`; }
      T.step({ line, arrays: [A("nums", nums, { hl: { [i]: "cur" }, ptr: ptrs({ i }) }), A("top 3", [a, b, c], { noIndex: true, indexLabels: [] })], msg });
    });
    const out = c !== null ? c : a;
    T.step({ line: 7, key: true, arrays: [A("top 3", [a, b, c], { noIndex: true })], msg: c !== null ? `Có đủ ba giá trị khác nhau → ${c}.` : `Không đủ ba giá trị → trả về số lớn nhất ${a}.` });
    return out;
  },
});

register("find-all-numbers-disappeared-in-an-array", {
  input: "nums = [4, 3, 2, 7, 8, 2, 3, 1]",
  expect: [5, 6],
  code: `for v in nums:
  i = |v| - 1
  nums[i] = -|nums[i]|     // đánh dấu i + 1 đã xuất hiện
for i in 0..n-1:
  if nums[i] > 0: add i + 1
return result`,
  run({ nums }, T) {
    const a = [...nums], res = [];
    for (let k = 0; k < a.length; k++) {
      const i = Math.abs(a[k]) - 1;
      a[i] = -Math.abs(a[i]);
      T.step({ line: 3, arrays: [A("nums", a, { hl: { [k]: "cur", [i]: "mark" }, ptr: ptrs({ k, i }) })], msg: `Giá trị ${i + 1} xuất hiện → đổi dấu vị trí ${i} thành âm.` });
    }
    a.forEach((v, i) => {
      if (v > 0) res.push(i + 1);
      T.step({ line: 5, key: v > 0, arrays: [A("nums", a, { hl: { [i]: v > 0 ? "ok" : "dim" }, ptr: ptrs({ i }) })], vars: { "kết quả": res }, msg: v > 0 ? `Vị trí ${i} còn dương → thiếu số ${i + 1}.` : `Vị trí ${i} âm → số ${i + 1} có mặt.` });
    });
    return res;
  },
});

register("assign-cookies", {
  input: "g = [1, 2, 3]\ns = [1, 1]",
  expect: 1,
  code: `sort(g); sort(s)
i = 0
for j in 0..len(s)-1:
  if i < len(g) and s[j] >= g[i]: i++
return i`,
  run({ g, s }, T) {
    const a = [...g].sort((x, y) => x - y), b = [...s].sort((x, y) => x - y);
    let i = 0;
    T.step({ line: 1, arrays: [A("độ tham g", a), A("bánh s", b)], msg: "Sắp xếp cả hai, dùng bánh nhỏ nhất đủ cho đứa dễ tính nhất." });
    for (let j = 0; j < b.length; j++) {
      if (i >= a.length) break;
      const ok = b[j] >= a[i];
      T.step({ line: 4, key: ok, arrays: [A("độ tham g", a, { hl: { ...tones([...Array(i).keys()], "done"), [i]: ok ? "ok" : "cur" }, ptr: ptrs({ i }) }), A("bánh s", b, { hl: { [j]: ok ? "ok" : "bad" }, ptr: ptrs({ j }) })], vars: { "đã no": i + (ok ? 1 : 0) }, msg: ok ? `Bánh ${b[j]} đủ cho trẻ cần ${a[i]}.` : `Bánh ${b[j]} quá nhỏ cho trẻ cần ${a[i]} → bỏ bánh này.` });
      if (ok) i++;
    }
    T.step({ line: 5, arrays: [A("độ tham g", a, { hl: tones([...Array(i).keys()], "done") }), A("bánh s", b)], vars: { "đã no": i }, msg: `Có ${i} trẻ được no.` });
    return i;
  },
});

register("max-consecutive-ones", {
  input: "nums = [1, 1, 0, 1, 1, 1]",
  expect: 3,
  code: `cur = 0, best = 0
for v in nums:
  cur = v == 1 ? cur + 1 : 0
  best = max(best, cur)
return best`,
  run({ nums }, T) {
    let cur = 0, best = 0;
    nums.forEach((v, i) => {
      cur = v === 1 ? cur + 1 : 0;
      const up = cur > best;
      best = Math.max(best, cur);
      T.step({ line: up ? 4 : 3, key: up, arrays: [A("nums", nums, { range: [i - cur + 1, i], hl: { [i]: v ? "cur" : "bad" }, ptr: ptrs({ i }) })], vars: { cur, best }, msg: v ? `Chuỗi số 1 dài ${cur}.` : "Gặp 0 → chuỗi đứt, đếm lại." });
    });
    return best;
  },
});

register("array-partition", {
  input: "nums = [1, 4, 3, 2]",
  expect: 4,
  code: `sort(nums)
sum = 0
for i = 0; i < n; i += 2:
  sum += nums[i]
return sum`,
  run({ nums }, T) {
    const a = [...nums].sort((x, y) => x - y);
    let sum = 0;
    T.step({ line: 1, arrays: [A("nums (đã sắp xếp)", a)], msg: "Ghép hai số liền nhau sau khi sắp xếp để không phí số lớn." });
    for (let i = 0; i < a.length; i += 2) {
      sum += a[i];
      T.step({ line: 4, arrays: [A("nums", a, { hl: { [i]: "ok", [i + 1]: "mark" }, ptr: ptrs({ i }) })], vars: { sum }, msg: `Cặp (${a[i]}, ${a[i + 1]}): min là ${a[i]}, sum = ${sum}.` });
    }
    return sum;
  },
});

register("maximum-product-of-three-numbers", {
  input: "nums = [-10, -10, 1, 3, 2]",
  expect: 300,
  code: `sort(nums)
return max(nums[n-1] * nums[n-2] * nums[n-3],
           nums[0] * nums[1] * nums[n-1])`,
  run({ nums }, T) {
    const a = [...nums].sort((x, y) => x - y), n = a.length;
    T.step({ line: 1, arrays: [A("nums (đã sắp xếp)", a)], msg: "Đáp án chỉ có thể là 3 số lớn nhất, hoặc 2 số âm nhỏ nhất nhân số lớn nhất." });
    const p1 = a[n - 1] * a[n - 2] * a[n - 3], p2 = a[0] * a[1] * a[n - 1];
    T.step({ line: 2, arrays: [A("nums", a, { hl: tones([n - 1, n - 2, n - 3], "cur") })], vars: { "ba số lớn nhất": p1 }, msg: `${a[n - 1]} × ${a[n - 2]} × ${a[n - 3]} = ${p1}.` });
    T.step({ line: 3, arrays: [A("nums", a, { hl: tones([0, 1, n - 1], "cur") })], vars: { "ba số lớn nhất": p1, "hai âm + lớn nhất": p2 }, msg: `${a[0]} × ${a[1]} × ${a[n - 1]} = ${p2}.` });
    const best = Math.max(p1, p2);
    T.step({ line: 2, key: true, arrays: [A("nums", a, { hl: tones(p1 >= p2 ? [n - 1, n - 2, n - 3] : [0, 1, n - 1], "ok") })], vars: { "kết quả": best }, msg: `Lớn hơn là ${best}.` });
    return best;
  },
});

register("maximum-average-subarray-i", {
  input: "nums = [1, 12, -5, -6, 50, 3]\nk = 4",
  expect: 12.75,
  code: `sum = nums[0] + ... + nums[k-1]
best = sum
for i in k..n-1:
  sum += nums[i] - nums[i-k]
  best = max(best, sum)
return best / k`,
  run({ nums, k }, T) {
    let sum = nums.slice(0, k).reduce((x, y) => x + y, 0), best = sum;
    T.step({ line: 1, arrays: [A("nums", nums, { range: [0, k - 1], ptr: ptrs({ l: 0, r: k - 1 }) })], vars: { sum, best }, msg: `Tổng cửa sổ đầu tiên: ${sum}.` });
    for (let i = k; i < nums.length; i++) {
      sum += nums[i] - nums[i - k];
      const up = sum > best;
      best = Math.max(best, sum);
      T.step({ line: up ? 5 : 4, key: up, arrays: [A("nums", nums, { range: [i - k + 1, i], hl: { [i]: "ok", [i - k]: "bad" }, ptr: ptrs({ l: i - k + 1, r: i }) })], vars: { sum, best }, msg: `Trượt: thêm ${nums[i]}, bớt ${nums[i - k]} → tổng ${sum}.` });
    }
    T.step({ line: 6, arrays: [A("nums", nums)], vars: { best, "best / k": best / k }, msg: `Trung bình lớn nhất: ${best} / ${k} = ${best / k}.` });
    return best / k;
  },
});

register("set-mismatch", {
  input: "nums = [1, 2, 2, 4]",
  expect: [2, 3],
  code: `count = [0] * (n + 1)
for v in nums: count[v]++
for x in 1..n:
  if count[x] == 2: dup = x
  if count[x] == 0: miss = x
return [dup, miss]`,
  run({ nums }, T) {
    const n = nums.length, count = Array(n + 1).fill(0);
    nums.forEach((v, i) => {
      count[v]++;
      T.step({ line: 2, arrays: [A("nums", nums, { hl: { [i]: "cur" }, ptr: ptrs({ i }) }), A("count", count, { hl: { [v]: "mark" } })], msg: `Đếm ${v}.` });
    });
    let dup = -1, miss = -1;
    for (let x = 1; x <= n; x++) {
      if (count[x] === 2) dup = x;
      if (count[x] === 0) miss = x;
      T.step({ line: count[x] === 2 ? 4 : count[x] === 0 ? 5 : 3, key: count[x] !== 1, arrays: [A("nums", nums), A("count", count, { hl: { [x]: count[x] === 1 ? "done" : "bad" }, ptr: ptrs({ x }) })], vars: { dup, miss }, msg: count[x] === 2 ? `${x} xuất hiện 2 lần → số trùng.` : count[x] === 0 ? `${x} không xuất hiện → số thiếu.` : `${x} xuất hiện đúng 1 lần.` });
    }
    return [dup, miss];
  },
});

register("find-pivot-index", {
  input: "nums = [1, 7, 3, 6, 5, 6]",
  expect: 3,
  code: `total = sum(nums), left = 0
for i in 0..n-1:
  if left == total - left - nums[i]: return i
  left += nums[i]
return -1`,
  run({ nums }, T) {
    const total = nums.reduce((x, y) => x + y, 0);
    let left = 0;
    for (let i = 0; i < nums.length; i++) {
      const right = total - left - nums[i], hit = left === right;
      T.step({ line: 3, key: hit, arrays: [A("nums", nums, { hl: { ...tones([...Array(i).keys()], "c1"), [i]: hit ? "ok" : "cur", ...tones(nums.map((_, x) => x).slice(i + 1), "c3") }, ptr: ptrs({ i }) })], vars: { "tổng trái": left, "tổng phải": right }, msg: hit ? `Trái ${left} = phải ${right} → pivot là ${i}.` : `Trái ${left} ≠ phải ${right}.` });
      if (hit) return i;
      left += nums[i];
    }
    T.step({ line: 5, arrays: [A("nums", nums)], msg: "Không có vị trí cân bằng." });
    return -1;
  },
});

register("monotonic-array", {
  input: "nums = [1, 2, 2, 3]",
  expect: true,
  code: `inc = true, dec = true
for i in 1..n-1:
  if nums[i] < nums[i-1]: inc = false
  if nums[i] > nums[i-1]: dec = false
return inc or dec`,
  run({ nums }, T) {
    let inc = true, dec = true;
    for (let i = 1; i < nums.length; i++) {
      if (nums[i] < nums[i - 1]) inc = false;
      if (nums[i] > nums[i - 1]) dec = false;
      T.step({ line: nums[i] < nums[i - 1] ? 3 : 4, arrays: [A("nums", nums, { bars: true, hl: { [i - 1]: "mark", [i]: "cur" }, ptr: ptrs({ i }) })], vars: { "không giảm": inc, "không tăng": dec }, msg: `So ${nums[i - 1]} và ${nums[i]}.` });
    }
    T.step({ line: 5, key: true, arrays: [A("nums", nums, { bars: true })], vars: { "không giảm": inc, "không tăng": dec }, msg: inc || dec ? "Mảng đơn điệu." : "Có cả chiều tăng lẫn giảm → không đơn điệu." });
    return inc || dec;
  },
});

register("sort-array-by-parity", {
  input: "nums = [3, 1, 2, 4]",
  expect: [4, 2, 1, 3],
  code: `l = 0, r = n - 1
while l < r:
  if nums[l] % 2 == 0: l++
  else if nums[r] % 2 == 1: r--
  else: swap(nums[l], nums[r])`,
  run({ nums }, T) {
    const a = [...nums];
    let l = 0, r = a.length - 1;
    const view = (line, msg, hl = {}) => T.step({ line, arrays: [A("nums", a, { hl: { [l]: "cur", [r]: "cur", ...hl }, ptr: ptrs({ l, r }) })], msg });
    while (l < r) {
      if (a[l] % 2 === 0) { view(3, `${a[l]} chẵn, đã đúng bên trái.`); l++; }
      else if (a[r] % 2 === 1) { view(4, `${a[r]} lẻ, đã đúng bên phải.`); r--; }
      else {
        view(5, `${a[l]} lẻ ở trái, ${a[r]} chẵn ở phải → đổi chỗ.`, { [l]: "bad", [r]: "bad" });
        [a[l], a[r]] = [a[r], a[l]];
        view(5, "Đã đổi chỗ.", { [l]: "ok", [r]: "ok" });
      }
    }
    T.step({ line: 2, key: true, arrays: [A("nums", a)], msg: "Số chẵn đã nằm hết bên trái." });
    return a;
  },
});

register("squares-of-a-sorted-array", {
  input: "nums = [-4, -1, 0, 3, 10]",
  expect: [0, 1, 9, 16, 100],
  code: `l = 0, r = n - 1, k = n - 1
while l <= r:
  if |nums[l]| > |nums[r]|:
    out[k--] = nums[l]^2; l++
  else:
    out[k--] = nums[r]^2; r--
return out`,
  run({ nums }, T) {
    const n = nums.length, out = Array(n).fill(null);
    let l = 0, r = n - 1, k = n - 1;
    while (l <= r) {
      const left = Math.abs(nums[l]) > Math.abs(nums[r]);
      out[k] = left ? nums[l] ** 2 : nums[r] ** 2;
      T.step({ line: left ? 4 : 6, arrays: [A("nums", nums, { range: [l, r], hl: { [left ? l : r]: "ok" }, ptr: ptrs({ l, r }) }), A("out", out, { hl: { [k]: "ok" }, ptr: ptrs({ k }) })], msg: `Trị tuyệt đối lớn nhất nằm ở hai đầu: ${left ? nums[l] : nums[r]} → ghi ${out[k]} vào cuối.` });
      if (left) l++; else r--;
      k--;
    }
    return out;
  },
});

register("unique-number-of-occurrences", {
  input: "arr = [1, 2, 2, 1, 1, 3]",
  expect: true,
  code: `count = frequency(arr)
seen = new HashSet()
for c in count.values():
  if c in seen: return false
  seen.add(c)
return true`,
  run({ arr }, T) {
    const count = new Map();
    arr.forEach((v, i) => {
      count.set(v, (count.get(v) ?? 0) + 1);
      T.step({ line: 1, arrays: [A("arr", arr, { hl: { [i]: "cur" }, ptr: ptrs({ i }) })], maps: [M("số lần", count, { hl: { [v]: "ok" } })], msg: `Đếm ${v}.` });
    });
    const seen = new Set();
    for (const [v, c] of count) {
      const dup = seen.has(c);
      T.step({ line: 4, key: dup, arrays: [A("arr", arr)], maps: [M("số lần", count, { hl: { [v]: dup ? "bad" : "cur" } }), M("đã thấy", seen)], msg: dup ? `Số lần ${c} bị trùng.` : `${v} xuất hiện ${c} lần, chưa trùng.` });
      if (dup) return false;
      seen.add(c);
    }
    T.step({ line: 6, key: true, maps: [M("đã thấy", seen)], arrays: [A("arr", arr)], msg: "Mọi số lần xuất hiện đều khác nhau." });
    return true;
  },
});

register("running-sum-of-1d-array", {
  input: "nums = [1, 2, 3, 4]",
  expect: [1, 3, 6, 10],
  code: `for i in 1..n-1:
  nums[i] += nums[i-1]
return nums`,
  run({ nums }, T) {
    const a = [...nums];
    for (let i = 1; i < a.length; i++) {
      a[i] += a[i - 1];
      T.step({ line: 2, arrays: [A("nums", a, { hl: { [i - 1]: "mark", [i]: "cur" }, ptr: ptrs({ i }) })], msg: `nums[${i}] = ${nums[i]} + ${a[i - 1]} = ${a[i]}.` });
    }
    return a;
  },
});

register("number-of-good-pairs", {
  input: "nums = [1, 2, 3, 1, 1, 3]",
  expect: 4,
  code: `count = new HashMap(), pairs = 0
for v in nums:
  pairs += count[v]     // ghép với các số v đã gặp
  count[v]++
return pairs`,
  run({ nums }, T) {
    const count = new Map();
    let pairs = 0;
    nums.forEach((v, i) => {
      const c = count.get(v) ?? 0;
      pairs += c;
      count.set(v, c + 1);
      T.step({ line: 3, key: c > 0, arrays: [A("nums", nums, { hl: { [i]: "cur", ...tones(nums.map((x, j) => (j < i && x === v ? j : -1)).filter((j) => j >= 0), "mark") }, ptr: ptrs({ i }) })], maps: [M("số lần", count, { hl: { [v]: "ok" } })], vars: { pairs }, msg: c ? `${v} ghép được với ${c} số trước đó.` : `${v} chưa gặp lần nào.` });
    });
    return pairs;
  },
});

register("richest-customer-wealth", {
  input: "accounts = [[1, 2, 3], [3, 2, 1], [4, 5, 1]]",
  expect: 10,
  code: `best = 0
for row in accounts:
  best = max(best, sum(row))
return best`,
  run({ accounts }, T) {
    let best = 0;
    accounts.forEach((row, r) => {
      const s = row.reduce((x, y) => x + y, 0);
      const up = s > best;
      best = Math.max(best, s);
      T.step({ line: 3, key: up, grids: [{ label: "accounts", cells: accounts.map((x, i) => [...x, i <= r ? x.reduce((p, q) => p + q, 0) : null]), hl: Object.fromEntries(row.map((_, c) => [r + "," + c, "cur"]).concat([[r + "," + row.length, up ? "ok" : "mark"]])), colHead: [...row.map((_, c) => `ngân hàng ${c}`), "tổng"] }], vars: { best }, msg: `Khách ${r} có ${s}${up ? ", giàu nhất hiện tại." : "."}` });
    });
    return best;
  },
});

