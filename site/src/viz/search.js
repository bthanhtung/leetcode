/* Binary search, sliding windows and matrices. */
import { A, G, Heap, M, Q, chars, clone, ptrs, register, tones } from "./engine.js";

const range = (a, b) => Array.from({ length: Math.max(0, b - a + 1) }, (_, i) => a + i);

/** One binary-search frame on an array. */
const bs = (T, label, a, lo, hi, mid, line, msg, tone = "cur", key) => T.step({ line, key, arrays: [A(label, a, { range: [lo, hi], dimOutside: true, hl: { [mid]: tone }, ptr: ptrs({ lo, mid, hi }, a.length) })], vars: { lo, mid, hi }, msg });

register("median-of-two-sorted-arrays", {
  input: "nums1 = [1, 3, 8, 9]\nnums2 = [2, 4, 5, 7, 10]",
  expect: 5,
  code: `// a là mảng ngắn hơn
lo = 0, hi = m, half = (m + n + 1) / 2
while lo <= hi:
  i = (lo + hi) / 2, j = half - i
  if a[i-1] > b[j]: hi = i - 1
  else if b[j-1] > a[i]: lo = i + 1
  else:
    return median from max(a[i-1], b[j-1]), min(a[i], b[j])`,
  run({ nums1, nums2 }, T) {
    const [a, b] = nums1.length <= nums2.length ? [nums1, nums2] : [nums2, nums1];
    const m = a.length, n = b.length, half = (m + n + 1) >> 1;
    let lo = 0, hi = m;
    while (lo <= hi) {
      const i = (lo + hi) >> 1, j = half - i;
      const al = i ? a[i - 1] : -Infinity, ar = i < m ? a[i] : Infinity, bl = j ? b[j - 1] : -Infinity, br = j < n ? b[j] : Infinity;
      const view = (line, msg, key) => T.step({ line, key, arrays: [A("a", a, { hl: { ...tones(range(0, i - 1), "c1"), ...tones(range(i, m - 1), "c3") }, ptr: ptrs({ i }, m) }), A("b", b, { hl: { ...tones(range(0, j - 1), "c1"), ...tones(range(j, n - 1), "c3") }, ptr: ptrs({ j }, n) })], vars: { "trái lớn nhất": `${al}, ${bl}`, "phải nhỏ nhất": `${ar}, ${br}` }, msg });
      if (al > br) { view(5, `a[i−1] = ${al} > b[j] = ${br} → cắt a quá nhiều bên trái, giảm i.`); hi = i - 1; }
      else if (bl > ar) { view(6, `b[j−1] = ${bl} > a[i] = ${ar} → cần lấy thêm từ a, tăng i.`); lo = i + 1; }
      else {
        const left = Math.max(al, bl), res = (m + n) % 2 ? left : (left + Math.min(ar, br)) / 2;
        view(8, `Phần trái (xanh) toàn ≤ phần phải (vàng) → trung vị ${res}.`, true);
        return res;
      }
    }
    return 0;
  },
});

register("find-first-and-last-position-of-element-in-sorted-array", {
  input: "nums = [5, 7, 7, 8, 8, 10]\ntarget = 8",
  expect: [3, 4],
  code: `bound(x): first index with nums[i] >= x
  lo = 0, hi = n
  while lo < hi:
    mid = (lo + hi) / 2
    if nums[mid] < x: lo = mid + 1 else hi = mid
  return lo
first = bound(target), last = bound(target + 1) - 1
return first valid ? [first, last] : [-1, -1]`,
  run({ nums, target }, T) {
    const bound = (x, name) => {
      let lo = 0, hi = nums.length;
      while (lo < hi) {
        const mid = (lo + hi) >> 1;
        bs(T, `nums (tìm ${name})`, nums, lo, hi - 1, mid, 5, nums[mid] < x ? `${nums[mid]} < ${x} → sang phải.` : `${nums[mid]} ≥ ${x} → giữ mid, sang trái.`);
        if (nums[mid] < x) lo = mid + 1; else hi = mid;
      }
      return lo;
    };
    const first = bound(target, "đầu tiên"), last = bound(target + 1, "sau cuối") - 1;
    const ok = first < nums.length && nums[first] === target;
    T.step({ line: 7, key: true, arrays: [A("nums", nums, { hl: ok ? tones(range(first, last), "ok") : {} })], msg: ok ? `${target} chiếm đoạn [${first}, ${last}].` : "Không có target." });
    return ok ? [first, last] : [-1, -1];
  },
});

register("search-insert-position", {
  input: "nums = [1, 3, 5, 6]\ntarget = 2",
  expect: 1,
  code: `lo = 0, hi = n
while lo < hi:
  mid = (lo + hi) / 2
  if nums[mid] < target: lo = mid + 1
  else: hi = mid
return lo`,
  run({ nums, target }, T) {
    let lo = 0, hi = nums.length;
    while (lo < hi) {
      const mid = (lo + hi) >> 1;
      bs(T, "nums", nums, lo, hi - 1, mid, nums[mid] < target ? 4 : 5, nums[mid] < target ? `${nums[mid]} < ${target} → chèn sau mid.` : `${nums[mid]} ≥ ${target} → mid vẫn có thể là chỗ chèn.`);
      if (nums[mid] < target) lo = mid + 1; else hi = mid;
    }
    T.step({ line: 6, key: true, arrays: [A("nums", nums, { ptr: ptrs({ "chèn ở đây": lo }, nums.length) })], vars: { lo }, msg: `Vị trí chèn: ${lo}.` });
    return lo;
  },
});

register("search-a-2d-matrix", {
  input: "matrix = [[1, 3, 5, 7], [10, 11, 16, 20], [23, 30, 34, 60]]\ntarget = 16",
  expect: true,
  code: `lo = 0, hi = m * n - 1
while lo <= hi:
  mid = (lo + hi) / 2
  v = matrix[mid / n][mid % n]
  if v == target: return true
  if v < target: lo = mid + 1 else hi = mid - 1
return false`,
  run({ matrix, target }, T) {
    const m = matrix.length, n = matrix[0].length;
    let lo = 0, hi = m * n - 1;
    while (lo <= hi) {
      const mid = (lo + hi) >> 1, r = Math.floor(mid / n), c = mid % n, v = matrix[r][c];
      const hl = {};
      for (let k = 0; k < m * n; k++) hl[Math.floor(k / n) + "," + (k % n)] = k === mid ? (v === target ? "ok" : "cur") : k >= lo && k <= hi ? "win" : "dim";
      T.step({ line: v === target ? 5 : 6, key: v === target, grids: [G("matrix (coi như mảng phẳng)", matrix, { hl })], vars: { mid, "(hàng, cột)": `(${r}, ${c})`, v }, msg: v === target ? "Tìm thấy." : v < target ? `${v} < ${target} → sang phải.` : `${v} > ${target} → sang trái.` });
      if (v === target) return true;
      if (v < target) lo = mid + 1; else hi = mid - 1;
    }
    return false;
  },
});

function rotatedMin(slug, input, expect, code, dup) {
  register(slug, {
    input, expect, code,
    run({ nums }, T) {
      let lo = 0, hi = nums.length - 1;
      while (lo < hi) {
        const mid = (lo + hi) >> 1;
        if (nums[mid] > nums[hi]) { bs(T, "nums", nums, lo, hi, mid, 4, `${nums[mid]} > ${nums[hi]} → điểm gãy nằm bên phải mid.`); lo = mid + 1; }
        else if (dup && nums[mid] === nums[hi]) { bs(T, "nums", nums, lo, hi, mid, 5, `${nums[mid]} = ${nums[hi]} → chưa biết bên nào, chỉ bỏ được hi.`, "mark"); hi--; }
        else { bs(T, "nums", nums, lo, hi, mid, dup ? 6 : 5, `${nums[mid]} ≤ ${nums[hi]} → mid đã ở đoạn sau, min ở mid hoặc bên trái.`); hi = mid; }
      }
      T.step({ line: dup ? 7 : 6, key: true, arrays: [A("nums", nums, { hl: { [lo]: "ok" }, ptr: ptrs({ min: lo }) })], msg: `Nhỏ nhất là ${nums[lo]}.` });
      return nums[lo];
    },
  });
}

rotatedMin("find-minimum-in-rotated-sorted-array", "nums = [4, 5, 6, 7, 0, 1, 2]", 0, `lo = 0, hi = n - 1
while lo < hi:
  mid = (lo + hi) / 2
  if nums[mid] > nums[hi]: lo = mid + 1
  else: hi = mid
return nums[lo]`, false);

rotatedMin("find-minimum-in-rotated-sorted-array-ii", "nums = [2, 2, 2, 0, 1]", 0, `lo = 0, hi = n - 1
while lo < hi:
  mid = (lo + hi) / 2
  if nums[mid] > nums[hi]: lo = mid + 1
  else if nums[mid] == nums[hi]: hi--
  else: hi = mid
return nums[lo]`, true);

register("search-a-2d-matrix-ii", {
  input: "matrix = [[1, 4, 7, 11], [2, 5, 8, 12], [3, 6, 9, 16], [10, 13, 14, 17]]\ntarget = 5",
  expect: true,
  code: `r = 0, c = n - 1            // góc trên phải
while r < m and c >= 0:
  if matrix[r][c] == target: return true
  if matrix[r][c] > target: c--
  else: r++
return false`,
  run({ matrix, target }, T) {
    let r = 0, c = matrix[0].length - 1;
    const hl = {};
    while (r < matrix.length && c >= 0) {
      const v = matrix[r][c], hit = v === target;
      T.step({ line: hit ? 3 : v > target ? 4 : 5, key: hit, grids: [G("matrix", matrix, { hl: { ...hl, [r + "," + c]: hit ? "ok" : "cur" } })], msg: hit ? "Tìm thấy." : v > target ? `${v} > ${target}: cả cột này đều lớn hơn, bỏ cột.` : `${v} < ${target}: cả hàng này đều nhỏ hơn, bỏ hàng.` });
      if (hit) return true;
      if (v > target) { for (let i = 0; i < matrix.length; i++) hl[i + "," + c] = "dim"; c--; }
      else { for (let j = 0; j < matrix[0].length; j++) hl[r + "," + j] = "dim"; r++; }
    }
    return false;
  },
});

function guessGame(slug, input, expect, code) {
  register(slug, {
    input, expect, code,
    run(args, T) {
      const n = args.n, secret = args.pick ?? args.bad;
      const bad = args.bad !== undefined;
      let lo = 1, hi = n;
      const a = range(1, n);
      while (lo < hi || (!bad && lo <= hi)) {
        const mid = (lo + hi) >> 1;
        const go = mid >= secret;
        const hit = !bad && mid === secret;
        T.step({ line: 4, key: hit, arrays: [A("phiên bản", a, { small: true, noIndex: true, range: [lo - 1, hi - 1], dimOutside: true, hl: { [mid - 1]: hit ? "ok" : "cur" }, ptr: ptrs({ mid: mid - 1 }) })], vars: { lo, mid, hi }, msg: bad ? (go ? `Bản ${mid} hỏng → bản đầu tiên ở ${mid} hoặc trước đó.` : `Bản ${mid} tốt → bản hỏng nằm sau.`) : hit ? `Đoán trúng ${mid}.` : go ? `${mid} cao quá.` : `${mid} thấp quá.` });
        if (hit) return mid;
        if (go) hi = bad ? mid : mid - 1; else lo = mid + 1;
      }
      return lo;
    },
  });
}

guessGame("first-bad-version", "n = 12\nbad = 8", 8, `lo = 1, hi = n
while lo < hi:
  mid = (lo + hi) / 2
  if isBad(mid): hi = mid
  else: lo = mid + 1
return lo`);

guessGame("guess-number-higher-or-lower", "n = 15\npick = 6", 6, `lo = 1, hi = n
while lo <= hi:
  mid = (lo + hi) / 2
  if guess(mid) == 0: return mid
  if guess(mid) < 0: hi = mid - 1
  else: lo = mid + 1`);

register("split-array-largest-sum", {
  input: "nums = [7, 2, 5, 10, 8]\nk = 2",
  expect: 18,
  code: `lo = max(nums), hi = sum(nums)
while lo < hi:
  mid = (lo + hi) / 2
  if parts needed with limit mid <= k: hi = mid
  else: lo = mid + 1
return lo`,
  run({ nums, k }, T) {
    let lo = Math.max(...nums), hi = nums.reduce((x, y) => x + y, 0);
    while (lo < hi) {
      const mid = (lo + hi) >> 1, hl = {};
      let parts = 1, cur = 0;
      nums.forEach((v, i) => {
        if (cur + v > mid) { parts++; cur = 0; }
        cur += v;
        hl[i] = `c${((parts - 1) % 6) + 1}`;
      });
      const ok = parts <= k;
      T.step({ line: ok ? 4 : 5, key: ok, arrays: [A("nums", nums, { hl })], vars: { lo, hi, "giới hạn": mid, "số đoạn cần": parts, k }, msg: ok ? `Giới hạn ${mid} chia được ${parts} ≤ ${k} đoạn → thử nhỏ hơn.` : `Giới hạn ${mid} cần tới ${parts} đoạn → tăng lên.` });
      if (ok) hi = mid; else lo = mid + 1;
    }
    return lo;
  },
});

register("find-in-mountain-array", {
  input: "mountainArr = [1, 2, 3, 4, 5, 3, 1]\ntarget = 3",
  expect: 2,
  code: `peak = binary search for a[i] < a[i+1]
idx = binary search target in [0, peak]
if idx >= 0: return idx
return binary search target in [peak, n-1] (descending)`,
  run({ mountainArr: a, target }, T) {
    let lo = 0, hi = a.length - 1;
    while (lo < hi) {
      const mid = (lo + hi) >> 1;
      const up = a[mid] < a[mid + 1];
      T.step({ line: 1, arrays: [A("mountain", a, { bars: true, range: [lo, hi], hl: { [mid]: "cur" }, ptr: ptrs({ mid }) })], msg: up ? "Đang đi lên → đỉnh ở bên phải." : "Đang đi xuống → đỉnh ở mid hoặc bên trái." });
      if (up) lo = mid + 1; else hi = mid;
    }
    const peak = lo;
    T.step({ line: 1, key: true, arrays: [A("mountain", a, { bars: true, hl: { [peak]: "mark" }, ptr: ptrs({ đỉnh: peak }) })], msg: `Đỉnh ở vị trí ${peak}.` });
    const find = (l, r, asc, line) => {
      while (l <= r) {
        const m = (l + r) >> 1, hit = a[m] === target, right = asc ? a[m] < target : a[m] > target;
        T.step({ line, key: hit, arrays: [A("mountain", a, { bars: true, range: [l, r], hl: { [peak]: "mark", [m]: hit ? "ok" : "cur" }, ptr: ptrs({ m }) })], msg: hit ? `Tìm thấy ở ${m}.` : `a[${m}] = ${a[m]}, thu hẹp sườn ${asc ? "lên" : "xuống"}.` });
        if (hit) return m;
        if (right) l = m + 1; else r = m - 1;
      }
      return -1;
    };
    const left = find(0, peak, true, 2);
    return left >= 0 ? left : find(peak, a.length - 1, false, 4);
  },
});

register("russian-doll-envelopes", {
  input: "envelopes = [[5, 4], [6, 4], [6, 7], [2, 3]]",
  expect: 3,
  code: `sort by width asc, height desc
tails = []
for (w, h) in envelopes:
  i = lower_bound(tails, h)
  tails[i] = h
return len(tails)`,
  run({ envelopes }, T) {
    const e = [...envelopes].sort((a, b) => a[0] - b[0] || b[1] - a[1]), tails = [];
    const labels = e.map((x) => `${x[0]}×${x[1]}`);
    T.step({ line: 1, arrays: [A("phong bì đã sắp", labels, { small: true })], msg: "Rộng tăng dần; cùng rộng thì cao giảm dần để không lồng nhầm." });
    e.forEach(([, h], k) => {
      let lo = 0, hi = tails.length;
      while (lo < hi) { const m = (lo + hi) >> 1; if (tails[m] < h) lo = m + 1; else hi = m; }
      const grew = lo === tails.length;
      tails[lo] = h;
      T.step({ line: 5, key: grew, arrays: [A("phong bì", labels, { small: true, hl: { [k]: "cur" } }), A("tails", [...tails], { hl: { [lo]: grew ? "ok" : "mark" } })], msg: grew ? `Cao ${h} nối dài chuỗi lên ${tails.length}.` : `Cao ${h} thay thế ô ${lo}, giữ đuôi nhỏ nhất có thể.` });
    });
    return tails.length;
  },
});

/* ---------- sliding windows ---------- */

register("substring-with-concatenation-of-all-words", {
  input: 's = "barfoothefoobarman"\nwords = ["foo", "bar"]',
  expect: [0, 9],
  code: `need = frequency(words), w = len(words[0])
for offset in 0..w-1:
  l = offset, have = {}, count = 0
  for r = offset; r + w <= n; r += w:
    word = s[r..r+w-1]
    if word not in need: reset window; continue
    have[word]++, count++
    while have[word] > need[word]: drop leftmost word
    if count == len(words): result.add(l)`,
  run({ s, words }, T) {
    const need = new Map(), w = words[0].length, a = chars(s), res = [];
    words.forEach((x) => need.set(x, (need.get(x) ?? 0) + 1));
    for (let off = 0; off < w; off++) {
      let l = off, have = new Map(), count = 0;
      for (let r = off; r + w <= s.length; r += w) {
        const word = s.slice(r, r + w);
        if (!need.has(word)) {
          T.step({ line: 6, arrays: [A("s", a, { small: true, hl: tones(range(r, r + w - 1), "bad") })], msg: `"${word}" không có trong words → bỏ cửa sổ.` });
          have = new Map(); count = 0; l = r + w;
          continue;
        }
        have.set(word, (have.get(word) ?? 0) + 1);
        count++;
        while (have.get(word) > need.get(word)) {
          const drop = s.slice(l, l + w);
          have.set(drop, have.get(drop) - 1);
          l += w;
          count--;
        }
        const hit = count === words.length;
        if (hit) res.push(l);
        T.step({ line: hit ? 9 : 7, key: hit, arrays: [A("s", a, { small: true, range: [l, r + w - 1], hl: hit ? tones(range(l, r + w - 1), "ok") : tones(range(r, r + w - 1), "cur") })], maps: [M("đang có", have), M("cần", need)], vars: { "kết quả": res }, msg: hit ? `Đủ mọi từ, bắt đầu tại ${l}.` : `Thêm "${word}" vào cửa sổ.` });
      }
    }
    return res.sort((x, y) => x - y);
  },
});

register("minimum-size-subarray-sum", {
  input: "target = 7\nnums = [2, 3, 1, 2, 4, 3]",
  expect: 2,
  code: `l = 0, sum = 0, best = inf
for r in 0..n-1:
  sum += nums[r]
  while sum >= target:
    best = min(best, r - l + 1)
    sum -= nums[l++]
return best == inf ? 0 : best`,
  run({ target, nums }, T) {
    let l = 0, sum = 0, best = Infinity;
    nums.forEach((v, r) => {
      sum += v;
      T.step({ line: 3, arrays: [A("nums", nums, { range: [l, r], hl: { [r]: "cur" }, ptr: ptrs({ l, r }) })], vars: { sum, best: best === Infinity ? "∞" : best }, msg: `Mở rộng, thêm ${v}: tổng ${sum}.` });
      while (sum >= target) {
        best = Math.min(best, r - l + 1);
        T.step({ line: 5, key: true, arrays: [A("nums", nums, { range: [l, r], hl: { [l]: "mark" }, ptr: ptrs({ l, r }) })], vars: { sum, best }, msg: `Tổng ${sum} ≥ ${target}, đoạn dài ${r - l + 1}. Thu hẹp từ trái.` });
        sum -= nums[l++];
      }
    });
    return best === Infinity ? 0 : best;
  },
});

register("longest-repeating-character-replacement", {
  input: 's = "AABABBA"\nk = 1',
  expect: 4,
  code: `count = {}, l = 0, maxFreq = 0, best = 0
for r in 0..n-1:
  maxFreq = max(maxFreq, ++count[s[r]])
  if (r - l + 1) - maxFreq > k:
    count[s[l++]]--
  best = max(best, r - l + 1)
return best`,
  run({ s, k }, T) {
    const a = chars(s), count = new Map();
    let l = 0, maxFreq = 0, best = 0;
    a.forEach((c, r) => {
      count.set(c, (count.get(c) ?? 0) + 1);
      maxFreq = Math.max(maxFreq, count.get(c));
      const shrink = r - l + 1 - maxFreq > k;
      if (shrink) { T.step({ line: 5, arrays: [A("s", a, { range: [l, r], hl: { [l]: "bad" }, ptr: ptrs({ l, r }) })], maps: [M("count", count)], vars: { maxFreq, best }, msg: `Cần thay ${r - l + 1 - maxFreq} > ${k} ký tự → dời l.` }); count.set(a[l], count.get(a[l]) - 1); l++; }
      best = Math.max(best, r - l + 1);
      T.step({ line: 6, arrays: [A("s", a, { range: [l, r], hl: { [r]: "cur" }, ptr: ptrs({ l, r }) })], maps: [M("count", count, { hl: { [c]: "ok" } })], vars: { maxFreq, best }, msg: `Cửa sổ dài ${r - l + 1}, chỉ cần thay ${r - l + 1 - maxFreq} ký tự.` });
    });
    return best;
  },
});

function fixedAnagram(slug, input, expect, code, s1Name, s2Name, finish) {
  register(slug, {
    input, expect, code,
    run(args, T) {
      const p = args[s1Name], s = args[s2Name], a = chars(s), m = p.length, need = new Map(), res = [];
      for (const c of p) need.set(c, (need.get(c) ?? 0) + 1);
      let diff = need.size;
      for (let r = 0; r < a.length; r++) {
        const add = a[r];
        if (need.has(add)) { need.set(add, need.get(add) - 1); if (need.get(add) === 0) diff--; else if (need.get(add) === -1) diff++; }
        if (r >= m) {
          const out = a[r - m];
          if (need.has(out)) { need.set(out, need.get(out) + 1); if (need.get(out) === 0) diff--; else if (need.get(out) === 1) diff++; }
        }
        const l = Math.max(0, r - m + 1), hit = r >= m - 1 && diff === 0;
        if (hit) res.push(l);
        T.step({ line: hit ? 5 : 3, key: hit, arrays: [A(s2Name, a, { range: [l, r], hl: hit ? tones(range(l, r), "ok") : { [r]: "cur" }, ptr: ptrs({ l, r }) })], maps: [M("thiếu (âm = dư)", need)], vars: { "số ký tự lệch": diff, "kết quả": res }, msg: hit ? `Cửa sổ tại ${l} là một hoán vị của "${p}".` : "Trượt cửa sổ, cập nhật số lệch." });
        if (hit && finish) return true;
      }
      return finish ? false : res;
    },
  });
}

fixedAnagram("find-all-anagrams-in-a-string", 's = "cbaebabacd"\np = "abc"', [0, 6], `need = frequency(p), diff = distinct(p)
for r in 0..n-1:
  add s[r], drop s[r-m]; update diff
  // diff = số ký tự chưa khớp
  if diff == 0: result.add(r - m + 1)
return result`, "p", "s", false);

fixedAnagram("permutation-in-string", 's1 = "ab"\ns2 = "eidbaooo"', true, `need = frequency(s1), diff = distinct(s1)
for r in 0..n-1:
  add s2[r], drop s2[r-m]; update diff
  // diff = số ký tự chưa khớp
  if diff == 0: return true
return false`, "s1", "s2", true);

register("sliding-window-median", {
  input: "nums = [1, 3, -1, -3, 5, 3, 6, 7]\nk = 3",
  expect: [1, -1, -1, 3, 5, 6],
  code: `window = sorted list of first k
for r in k..n:
  result.add(median of window)
  remove nums[r-k], insert nums[r]
return result`,
  run({ nums, k }, T) {
    const win = [...nums.slice(0, k)].sort((a, b) => a - b), res = [];
    for (let r = k; ; r++) {
      const med = k % 2 ? win[k >> 1] : (win[k / 2 - 1] + win[k / 2]) / 2;
      res.push(med);
      T.step({ line: 3, arrays: [A("nums", nums, { range: [r - k, r - 1], ptr: ptrs({ l: r - k, r: r - 1 }) }), A("cửa sổ đã sắp", [...win], { hl: k % 2 ? { [k >> 1]: "ok" } : { [k / 2 - 1]: "ok", [k / 2]: "ok" } })], vars: { "kết quả": res }, msg: `Trung vị của cửa sổ: ${med}.` });
      if (r === nums.length) break;
      win.splice(win.indexOf(nums[r - k]), 1);
      win.splice(win.findIndex((v) => v >= nums[r]) === -1 ? win.length : win.findIndex((v) => v >= nums[r]), 0, nums[r]);
    }
    return res;
  },
});

register("smallest-range-covering-elements-from-k-lists", {
  input: "nums = [[4, 10, 15, 24, 26], [0, 9, 12, 20], [5, 18, 22, 30]]",
  expect: [20, 24],
  code: `heap = (first of each list), hi = max of them
best = [-inf, inf]
loop:
  (lo, list, i) = heap.pop()
  if hi - lo < best size: best = [lo, hi]
  if list exhausted: return best
  push next of list; hi = max(hi, it)`,
  run({ nums }, T) {
    const heap = new Heap((a, b) => a.v < b.v), pos = nums.map(() => 0);
    let hi = -Infinity, best = [-1e9, 1e9];
    nums.forEach((l, r) => { heap.push({ v: l[0], r }); hi = Math.max(hi, l[0]); });
    const grid = (cur) => [G("các danh sách", nums.map((l) => [...l, ...Array(Math.max(...nums.map((x) => x.length)) - l.length).fill(null)]), { hl: Object.fromEntries(nums.flatMap((l, r) => l.map((_, c) => [r + "," + c, c === pos[r] ? (r === cur ? "cur" : "mark") : c < pos[r] ? "dim" : "base"]))) })];
    for (;;) {
      const { v: lo, r } = heap.pop();
      const up = hi - lo < best[1] - best[0];
      if (up) best = [lo, hi];
      T.step({ line: 5, key: up, grids: grid(r), vars: { lo, hi, best }, msg: `Mỗi danh sách có một đại diện, khoảng [${lo}, ${hi}]${up ? " là tốt nhất hiện tại." : "."}` });
      pos[r]++;
      if (pos[r] === nums[r].length) { T.step({ line: 6, key: true, grids: grid(-1), vars: { best }, msg: `List ${r} hết phần tử → không dời lo được nữa.` }); return best; }
      heap.push({ v: nums[r][pos[r]], r });
      hi = Math.max(hi, nums[r][pos[r]]);
    }
  },
});

register("shortest-subarray-with-sum-at-least-k", {
  input: "nums = [2, -1, 2]\nk = 3",
  expect: 3,
  code: `prefix[0] = 0, dq = deque of indices
for r in 0..n:
  while dq and prefix[r] - prefix[dq.front] >= k:
    best = min(best, r - dq.popFront())
  while dq and prefix[dq.back] >= prefix[r]: dq.popBack()
  dq.pushBack(r)
return best or -1`,
  run({ nums, k }, T) {
    const p = [0];
    nums.forEach((v) => p.push(p[p.length - 1] + v));
    const dq = [];
    let best = Infinity;
    p.forEach((v, r) => {
      while (dq.length && v - p[dq[0]] >= k) {
        const l = dq.shift();
        best = Math.min(best, r - l);
        T.step({ line: 4, key: true, arrays: [A("prefix", p, { hl: { [l]: "ok", [r]: "ok" }, ptr: ptrs({ l, r }) })], stacks: [Q("deque", dq.map((i) => `${i}:${p[i]}`))], vars: { best }, msg: `prefix[${r}] − prefix[${l}] ≥ ${k}, đoạn dài ${r - l}.` });
      }
      while (dq.length && p[dq[dq.length - 1]] >= v) dq.pop();
      dq.push(r);
      T.step({ line: 6, arrays: [A("prefix", p, { hl: { [r]: "cur" }, ptr: ptrs({ r }) })], stacks: [Q("deque", dq.map((i) => `${i}:${p[i]}`))], vars: { best: best === Infinity ? "∞" : best }, msg: "Giữ deque tăng dần: prefix lớn hơn mà đứng trước thì không bao giờ tốt hơn." });
    });
    return best === Infinity ? -1 : best;
  },
});

register("subarrays-with-k-different-integers", {
  input: "nums = [1, 2, 1, 2, 3]\nk = 2",
  expect: 7,
  code: `atMost(k):
  count = {}, l = 0, total = 0
  for r in 0..n-1:
    add nums[r] to count
    while distinct(count) > k: remove nums[l++]
    total += r - l + 1
  return total
return atMost(k) - atMost(k - 1)`,
  run({ nums, k }, T) {
    const atMost = (x) => {
      const c = new Map();
      let l = 0, total = 0;
      nums.forEach((v, r) => {
        c.set(v, (c.get(v) ?? 0) + 1);
        while (c.size > x) { const u = nums[l++]; c.set(u, c.get(u) - 1); if (!c.get(u)) c.delete(u); }
        total += r - l + 1;
        T.step({ line: 6, arrays: [A("nums", nums, { range: [l, r], hl: { [r]: "cur" }, ptr: ptrs({ l, r }) })], maps: [M("trong cửa sổ", c)], vars: { "tối đa": x, total }, msg: `Có ${r - l + 1} đoạn kết thúc tại ${r} với ≤ ${x} giá trị khác nhau.` });
      });
      return total;
    };
    const a = atMost(k), b = atMost(k - 1);
    T.step({ line: 8, key: true, arrays: [A("nums", nums)], vars: { [`atMost(${k})`]: a, [`atMost(${k - 1})`]: b }, msg: `Đúng ${k} giá trị = ${a} − ${b} = ${a - b}.` });
    return a - b;
  },
});

register("minimum-number-of-k-consecutive-bit-flips", {
  input: "nums = [0, 0, 0, 1, 0, 1, 1, 0]\nk = 3",
  expect: 3,
  code: `flips = 0, active = 0
for i in 0..n-1:
  if i >= k and flipped[i-k]: active--
  if (nums[i] + active) % 2 == 0:
    if i + k > n: return -1
    flipped[i] = 1; active++; flips++
return flips`,
  run({ nums, k }, T) {
    const n = nums.length, flipped = Array(n).fill(0);
    let active = 0, flips = 0;
    const now = (i) => nums.map((v, x) => { let f = 0; for (let j = Math.max(0, x - k + 1); j <= Math.min(x, i); j++) f += flipped[j]; return (v + f) % 2; });
    for (let i = 0; i < n; i++) {
      if (i >= k && flipped[i - k]) active--;
      const flip = (nums[i] + active) % 2 === 0;
      if (flip && i + k > n) { T.step({ line: 5, key: true, arrays: [A("hiện tại", now(i), { hl: { [i]: "bad" } })], msg: "Không đủ chỗ để lật k bit." }); return -1; }
      if (flip) { flipped[i] = 1; active++; flips++; }
      T.step({ line: flip ? 6 : 4, key: flip, arrays: [A("hiện tại", now(i), { range: flip ? [i, i + k - 1] : undefined, hl: { [i]: flip ? "ok" : "cur" }, ptr: ptrs({ i }) })], vars: { active, flips }, msg: flip ? `Bit ${i} đang 0 → lật ${k} bit từ đây.` : `Bit ${i} đã là 1.` });
    }
    return flips;
  },
});

register("max-value-of-equation", {
  input: "points = [[1, 3], [2, 0], [5, 10], [6, -10]]\nk = 1",
  expect: 4,
  code: `dq = deque of (y - x), decreasing
for (x, y) in points:
  while dq and x - dq.front.x > k: dq.popFront()
  if dq: best = max(best, dq.front.val + x + y)
  while dq and dq.back.val <= y - x: dq.popBack()
  dq.pushBack((y - x, x))
return best`,
  run({ points, k }, T) {
    const dq = [];
    let best = -Infinity;
    points.forEach(([x, y], i) => {
      while (dq.length && x - dq[0][1] > k) dq.shift();
      const val = dq.length ? dq[0][0] + x + y : null;
      if (val !== null) best = Math.max(best, val);
      T.step({ line: 4, key: val === best, arrays: [A("points", points.map((p) => `(${p})`), { small: true, hl: { [i]: "cur" } })], stacks: [Q("deque (y−x, x)", dq.map((d) => `${d[0]}, ${d[1]}`))], vars: { best: best === -Infinity ? "−∞" : best }, msg: val === null ? "Chưa có điểm nào đủ gần." : `Ghép với đầu deque: ${dq[0][0]} + ${x} + ${y} = ${val}.` });
      while (dq.length && dq[dq.length - 1][0] <= y - x) dq.pop();
      dq.push([y - x, x]);
      T.step({ line: 6, arrays: [A("points", points.map((p) => `(${p})`), { small: true, hl: { [i]: "done" } })], stacks: [Q("deque (y−x, x)", dq.map((d) => `${d[0]}, ${d[1]}`))], vars: { best: best === -Infinity ? "−∞" : best }, msg: `Đẩy y − x = ${y - x} vào, giữ deque giảm dần.` });
    });
    return best;
  },
});

register("minimum-number-of-operations-to-make-array-continuous", {
  input: "nums = [1, 2, 3, 5, 6]",
  expect: 1,
  code: `n = len(nums), a = sorted unique(nums)
l = 0, keep = 0
for r in 0..len(a)-1:
  while a[r] - a[l] >= n: l++
  keep = max(keep, r - l + 1)
return n - keep`,
  run({ nums }, T) {
    const n = nums.length, a = [...new Set(nums)].sort((x, y) => x - y);
    let l = 0, keep = 0;
    a.forEach((v, r) => {
      while (v - a[l] >= n) l++;
      const up = r - l + 1 > keep;
      keep = Math.max(keep, r - l + 1);
      T.step({ line: 5, key: up, arrays: [A("đã sắp, bỏ trùng", a, { range: [l, r], hl: { [r]: "cur" }, ptr: ptrs({ l, r }) })], vars: { "giữ được": keep, "đổi": n - keep }, msg: `Đoạn giá trị [${a[l]}, ${v}] rộng < ${n}: giữ được ${r - l + 1} số.` });
    });
    return n - keep;
  },
});

/* ---------- matrices ---------- */

register("valid-sudoku", {
  input: 'board = [["5","3",".",".","7",".",".",".","."],["6",".",".","1","9","5",".",".","."],[".","9","8",".",".",".",".","6","."],["8",".",".",".","6",".",".",".","3"],["4",".",".","8",".","3",".",".","1"],["7",".",".",".","2",".",".",".","6"],[".","6",".",".",".",".","2","8","."],[".",".",".","4","1","9",".",".","5"],[".",".",".",".","8",".",".","7","9"]]',
  expect: true,
  code: `seen = {}
for r, c with board[r][c] != '.':
  for key in [row r has d, col c has d, box (r/3, c/3) has d]:
    if key in seen: return false
    seen.add(key)
return true`,
  run({ board }, T) {
    const seen = new Set();
    for (let r = 0; r < board.length; r++) for (let c = 0; c < board[r].length; c++) {
      const d = board[r][c];
      if (d === ".") continue;
      const keys = [`hàng ${r}: ${d}`, `cột ${c}: ${d}`, `ô ${Math.floor(r / 3)},${Math.floor(c / 3)}: ${d}`];
      const dup = keys.find((k) => seen.has(k));
      T.step({ line: 4, key: !!dup, grids: [G("board", board.map((x) => x.map((v) => (v === "." ? null : v))), { hl: { [r + "," + c]: dup ? "bad" : "cur" } })], vars: { "kiểm tra": keys.join(" | ") }, msg: dup ? `Trùng: ${dup}.` : `${d} ở (${r}, ${c}) chưa trùng.` });
      if (dup) return false;
      keys.forEach((k) => seen.add(k));
    }
    return true;
  },
});

register("rotate-image", {
  input: "matrix = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]",
  expect: [[7, 4, 1], [8, 5, 2], [9, 6, 3]],
  code: `// chuyển vị rồi đảo từng hàng
for i in 0..n-1:
  for j in i+1..n-1:
    swap(m[i][j], m[j][i])
for row in m:
  reverse(row)`,
  run({ matrix }, T) {
    const m = clone(matrix), n = m.length;
    for (let i = 0; i < n; i++) for (let j = i + 1; j < n; j++) {
      [m[i][j], m[j][i]] = [m[j][i], m[i][j]];
      T.step({ line: 4, grids: [G("matrix", clone(m), { hl: { [i + "," + j]: "cur", [j + "," + i]: "cur" } })], msg: `Đổi (${i}, ${j}) với (${j}, ${i}) qua đường chéo.` });
    }
    m.forEach((row, i) => {
      row.reverse();
      T.step({ line: 6, key: i === n - 1, grids: [G("matrix", clone(m), { hl: Object.fromEntries(row.map((_, c) => [i + "," + c, "ok"])) })], msg: `Đảo hàng ${i}.` });
    });
    return m;
  },
});

function spiralOrder(m, n) {
  const order = [];
  let t = 0, b = m - 1, l = 0, r = n - 1;
  while (t <= b && l <= r) {
    for (let c = l; c <= r; c++) order.push([t, c]);
    t++;
    for (let i = t; i <= b; i++) order.push([i, r]);
    r--;
    if (t <= b) { for (let c = r; c >= l; c--) order.push([b, c]); b--; }
    if (l <= r) { for (let i = b; i >= t; i--) order.push([i, l]); l++; }
  }
  return order;
}

const SPIRAL = `top = 0, bottom = m - 1, left = 0, right = n - 1
while top <= bottom and left <= right:
  go right along top; top++
  go down along right; right--
  go left along bottom; bottom--
  go up along left; left++`;

register("spiral-matrix", {
  input: "matrix = [[1, 2, 3, 4], [5, 6, 7, 8], [9, 10, 11, 12]]",
  expect: [1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7],
  code: SPIRAL,
  run({ matrix }, T) {
    const hl = {}, out = [];
    spiralOrder(matrix.length, matrix[0].length).forEach(([r, c], i, all) => {
      const prev = all[i - 1] ?? [r, c - 1], line = r === prev[0] ? (c > prev[1] ? 3 : 5) : r > prev[0] ? 4 : 6;
      out.push(matrix[r][c]);
      hl[r + "," + c] = "done";
      T.step({ line, grids: [G("matrix", matrix, { hl: { ...hl, [r + "," + c]: "cur" } })], arrays: [A("kết quả", [...out], { small: true, noIndex: true })], msg: `Lấy ${matrix[r][c]}.` });
    });
    return out;
  },
});

register("spiral-matrix-ii", {
  input: "n = 3",
  expect: [[1, 2, 3], [8, 9, 4], [7, 6, 5]],
  code: SPIRAL,
  run({ n }, T) {
    const m = Array.from({ length: n }, () => Array(n).fill(null));
    spiralOrder(n, n).forEach(([r, c], i, all) => {
      const prev = all[i - 1] ?? [r, c - 1], line = r === prev[0] ? (c > prev[1] ? 3 : 5) : r > prev[0] ? 4 : 6;
      m[r][c] = i + 1;
      T.step({ line, grids: [G("matrix", clone(m), { hl: { [r + "," + c]: "cur" } })], msg: `Điền ${i + 1}.` });
    });
    return m;
  },
});

register("set-matrix-zeroes", {
  input: "matrix = [[1, 1, 1], [1, 0, 1], [1, 1, 1]]",
  expect: [[1, 0, 1], [0, 0, 0], [1, 0, 1]],
  code: `use first row and column as markers
for each cell == 0:
  mark its row in matrix[r][0], its col in matrix[0][c]
for r, c from 1:
  if matrix[r][0] == 0 or matrix[0][c] == 0: cell = 0
zero first row and column if they had a 0`,
  run({ matrix }, T) {
    const a = clone(matrix), m = a.length, n = a[0].length;
    const row0 = a[0].includes(0), col0 = a.some((r) => r[0] === 0);
    for (let r = 0; r < m; r++) for (let c = 0; c < n; c++) if (a[r][c] === 0) {
      if (r && c) { a[r][0] = 0; a[0][c] = 0; }
      T.step({ line: 3, grids: [G("matrix", clone(a), { hl: { [r + "," + c]: "cur", [r + ",0"]: "mark", ["0," + c]: "mark" } })], msg: `Có số 0 ở (${r}, ${c}): đánh dấu ở đầu hàng và đầu cột.` });
    }
    for (let r = 1; r < m; r++) for (let c = 1; c < n; c++) if (a[r][0] === 0 || a[0][c] === 0) {
      a[r][c] = 0;
      T.step({ line: 5, grids: [G("matrix", clone(a), { hl: { [r + "," + c]: "bad", [r + ",0"]: "mark", ["0," + c]: "mark" } })], msg: `Hàng hoặc cột bị đánh dấu → (${r}, ${c}) thành 0.` });
    }
    if (row0) a[0].fill(0);
    if (col0) a.forEach((r) => (r[0] = 0));
    T.step({ line: 6, key: true, grids: [G("matrix", clone(a))], msg: "Xử lý hàng và cột đầu tiên sau cùng." });
    return a;
  },
});

register("game-of-life", {
  input: "board = [[0, 1, 0], [0, 0, 1], [1, 1, 1], [0, 0, 0]]",
  expect: [[0, 0, 0], [1, 0, 1], [0, 1, 1], [0, 1, 0]],
  code: `for each cell:
  live = count live neighbours (old value = cell & 1)
  if cell alive and live in {2, 3}: cell |= 2
  if cell dead and live == 3: cell |= 2
for each cell: cell >>= 1`,
  run({ board }, T) {
    const a = clone(board), m = a.length, n = a[0].length;
    for (let r = 0; r < m; r++) for (let c = 0; c < n; c++) {
      let live = 0;
      const hl = { [r + "," + c]: "cur" };
      for (let x = -1; x <= 1; x++) for (let y = -1; y <= 1; y++) {
        if ((x || y) && a[r + x]?.[c + y] !== undefined) { live += a[r + x][c + y] & 1; hl[r + x + "," + (c + y)] = "mark"; }
      }
      if ((a[r][c] & 1 && (live === 2 || live === 3)) || (!(a[r][c] & 1) && live === 3)) a[r][c] |= 2;
      T.step({ line: 3, grids: [G("board (bit 2 = đời sau)", clone(a), { hl })], vars: { "hàng xóm sống": live }, msg: `Ô (${r}, ${c}) có ${live} hàng xóm sống → đời sau ${a[r][c] & 2 ? "sống" : "chết"}.` });
    }
    a.forEach((row) => row.forEach((_, c) => (row[c] >>= 1)));
    T.step({ line: 5, key: true, grids: [G("thế hệ mới", clone(a), { hl: Object.fromEntries(a.flatMap((row, r) => row.flatMap((v, c) => (v ? [[r + "," + c, "ok"]] : [])))) })], msg: "Dịch bit để lấy thế hệ mới." });
    return a;
  },
});

register("island-perimeter", {
  input: "grid = [[0, 1, 0, 0], [1, 1, 1, 0], [0, 1, 0, 0], [1, 1, 0, 0]]",
  expect: 16,
  code: `p = 0
for each land cell:
  p += 4
  if land above: p -= 2
  if land left: p -= 2
return p`,
  run({ grid }, T) {
    let p = 0;
    const hl = {};
    grid.forEach((row, r) => row.forEach((v, c) => {
      if (!v) return;
      p += 4;
      if (r && grid[r - 1][c]) p -= 2;
      if (c && row[c - 1]) p -= 2;
      hl[r + "," + c] = "done";
      T.step({ line: 3, grids: [G("grid", grid, { hl: { ...hl, [r + "," + c]: "cur" } })], vars: { "chu vi": p }, msg: "Mỗi ô đất +4, mỗi cạnh chung với ô đã xét −2." });
    }));
    return p;
  },
});

register("transpose-matrix", {
  input: "matrix = [[1, 2, 3], [4, 5, 6]]",
  expect: [[1, 4], [2, 5], [3, 6]],
  code: `out = new[n][m]
for r in 0..m-1:
  for c in 0..n-1:
    out[c][r] = matrix[r][c]
return out`,
  run({ matrix }, T) {
    const m = matrix.length, n = matrix[0].length, out = Array.from({ length: n }, () => Array(m).fill(null));
    for (let r = 0; r < m; r++) for (let c = 0; c < n; c++) {
      out[c][r] = matrix[r][c];
      T.step({ line: 4, grids: [G("matrix", matrix, { hl: { [r + "," + c]: "cur" } }), G("out", clone(out), { hl: { [c + "," + r]: "ok" } })], msg: `(${r}, ${c}) → (${c}, ${r}).` });
    }
    return out;
  },
});

