/* Dynamic programming, 1-D tables and 2-D grids. */
import { A, G, chars, clone, matrix, ptrs, register, tones } from "./engine.js";

const range = (a, b) => Array.from({ length: Math.max(0, b - a + 1) }, (_, i) => a + i);
const show = (v) => (v === Infinity || v === -Infinity ? (v > 0 ? "∞" : "−∞") : v);
const shown = (g) => g.map((r) => r.map((v) => (typeof v === "boolean" ? (v ? "T" : "F") : show(v))));

/** Fills a 2-D table in order; `cell(i, j)` returns [value, sources, msg, line?]. */
function fill(T, label, dp, order, cell, heads = {}) {
  for (const [i, j] of order) {
    const [v, from, msg, line = 3, key] = cell(i, j);
    dp[i][j] = v;
    const hl = { [i + "," + j]: "cur" };
    for (const [r, c] of from) hl[r + "," + c] = "mark";
    if (msg) T.step({ line, key, grids: [G(label, shown(dp), { hl, ...heads })], msg });
  }
}
const rowMajor = (m, n) => range(0, m - 1).flatMap((i) => range(0, n - 1).map((j) => [i, j]));

function strMatch(slug, input, expect, code, cell) {
  register(slug, {
    input, expect, code,
    run({ s, p }, T) {
      const m = s.length, n = p.length, dp = matrix(m + 1, n + 1, false);
      dp[0][0] = true;
      for (let j = 1; j <= n; j++) if (p[j - 1] === "*") dp[0][j] = slug.startsWith("wild") ? dp[0][j - 1] : j >= 2 && dp[0][j - 2];
      const heads = { rowHead: ["∅", ...s], colHead: ["∅", ...p] };
      T.step({ line: 1, grids: [G("dp", shown(dp), heads)], msg: "Hàng đầu: pattern khớp chuỗi rỗng khi toàn dấu * bỏ được." });
      fill(T, "dp", dp, rowMajor(m, n).map(([i, j]) => [i + 1, j + 1]), (i, j) => cell(dp, s, p, i, j), heads);
      T.step({ line: 6, key: true, grids: [G("dp", shown(dp), { ...heads, hl: { [m + "," + n]: dp[m][n] ? "ok" : "bad" } })], msg: dp[m][n] ? "Khớp toàn bộ." : "Không khớp." });
      return dp[m][n];
    },
  });
}

strMatch("regular-expression-matching", 's = "aab"\np = "c*a*b"', true, `dp[0][0] = true; init first row for x*
for i in 1..m, j in 1..n:
  if p[j-1] == '*':
    dp[i][j] = dp[i][j-2] or (match(s[i-1], p[j-2]) and dp[i-1][j])
  else: dp[i][j] = match(s[i-1], p[j-1]) and dp[i-1][j-1]
return dp[m][n]`, (dp, s, p, i, j) => {
  const match = (a, b) => b === "." || a === b;
  if (p[j - 1] === "*") {
    const zero = dp[i][j - 2], more = match(s[i - 1], p[j - 2]) && dp[i - 1][j];
    return [zero || more, [[i, j - 2], [i - 1, j]], `'${p[j - 2]}*': bỏ hẳn hoặc ăn thêm '${s[i - 1]}'.`, 4];
  }
  const ok = match(s[i - 1], p[j - 1]) && dp[i - 1][j - 1];
  return [ok, [[i - 1, j - 1]], `'${s[i - 1]}' với '${p[j - 1]}': ${match(s[i - 1], p[j - 1]) ? "khớp, lấy ô chéo" : "không khớp"}.`, 5];
});

strMatch("wildcard-matching", 's = "adceb"\np = "*a*b"', true, `dp[0][0] = true; dp[0][j] = dp[0][j-1] when p[j-1] == '*'
for i in 1..m, j in 1..n:
  if p[j-1] == '*':
    dp[i][j] = dp[i][j-1] or dp[i-1][j]
  else: dp[i][j] = match(s[i-1], p[j-1]) and dp[i-1][j-1]
return dp[m][n]`, (dp, s, p, i, j) => {
  if (p[j - 1] === "*") return [dp[i][j - 1] || dp[i - 1][j], [[i, j - 1], [i - 1, j]], "`*` khớp rỗng (trái) hoặc ăn thêm một ký tự (trên).", 4];
  const m = p[j - 1] === "?" || s[i - 1] === p[j - 1];
  return [m && dp[i - 1][j - 1], [[i - 1, j - 1]], `'${s[i - 1]}' với '${p[j - 1]}': ${m ? "khớp" : "không khớp"}.`, 5];
});

function gridPath(slug, input, expect, code, run) {
  register(slug, { input, expect, code, run });
}

gridPath("unique-paths-ii", "obstacleGrid = [[0, 0, 0], [0, 1, 0], [0, 0, 0]]", 2, `dp[0][0] = 1
for each cell:
  if obstacle: dp[r][c] = 0
  else: dp[r][c] = dp[r-1][c] + dp[r][c-1]
return dp[m-1][n-1]`, ({ obstacleGrid: g }, T) => {
  const m = g.length, n = g[0].length, dp = matrix(m, n, null);
  fill(T, "số đường", dp, rowMajor(m, n), (r, c) => {
    if (g[r][c]) return [0, [], `(${r}, ${c}) có vật cản.`, 3];
    if (!r && !c) return [1, [], "Ô xuất phát.", 1];
    return [(dp[r - 1]?.[c] ?? 0) + (dp[r][c - 1] ?? 0), [[r - 1, c], [r, c - 1]].filter(([a, b]) => a >= 0 && b >= 0), `Từ trên ${dp[r - 1]?.[c] ?? 0} + từ trái ${dp[r][c - 1] ?? 0}.`, 4];
  });
  return dp[m - 1][n - 1];
});

gridPath("minimum-path-sum", "grid = [[1, 3, 1], [1, 5, 1], [4, 2, 1]]", 7, `for each cell:
  best = min(from above, from left)
  dp[r][c] = grid[r][c] + best
return dp[m-1][n-1]`, ({ grid }, T) => {
  const m = grid.length, n = grid[0].length, dp = matrix(m, n, null);
  fill(T, "chi phí nhỏ nhất", dp, rowMajor(m, n), (r, c) => {
    const up = r ? dp[r - 1][c] : Infinity, left = c ? dp[r][c - 1] : Infinity, best = !r && !c ? 0 : Math.min(up, left);
    return [grid[r][c] + best, [[r - 1, c], [r, c - 1]].filter(([a, b]) => a >= 0 && b >= 0), `${grid[r][c]} + min(${show(up)}, ${show(left)}) = ${grid[r][c] + best}.`];
  });
  return dp[m - 1][n - 1];
});

gridPath("dungeon-game", "dungeon = [[-2, -3, 3], [-5, -10, 1], [10, 30, -5]]", 7, `// đi ngược: máu tối thiểu cần có khi bước vào ô
for r, c from bottom-right:
  need = min(dp[r+1][c], dp[r][c+1]) - dungeon[r][c]
  dp[r][c] = max(1, need)
return dp[0][0]`, ({ dungeon: d }, T) => {
  const m = d.length, n = d[0].length, dp = matrix(m, n, null);
  fill(T, "máu tối thiểu", dp, rowMajor(m, n).reverse(), (r, c) => {
    const nxt = Math.min(dp[r + 1]?.[c] ?? Infinity, dp[r][c + 1] ?? Infinity), need = (nxt === Infinity ? 1 : nxt) - d[r][c];
    return [Math.max(1, need), [[r + 1, c], [r, c + 1]].filter(([a, b]) => a < m && b < n), `Sau ô này cần ${show(nxt === Infinity ? 1 : nxt)} máu, ô mang ${d[r][c]} → vào với ${Math.max(1, need)}.`, 4];
  });
  return dp[0][0];
});

register("scramble-string", {
  input: 's1 = "great"\ns2 = "rgeat"',
  expect: true,
  code: `go(i, j, len):
  if substrings equal: return true
  if letters differ: return false
  for k in 1..len-1:
    if go(i, j, k) and go(i+k, j+k, len-k): return true
    if go(i, j+len-k, k) and go(i+k, j, len-k): return true
  return false`,
  run({ s1, s2 }, T) {
    const memo = new Map();
    const same = (a, b) => [...a].sort().join() === [...b].sort().join();
    const go = (i, j, len) => {
      const key = `${i},${j},${len}`;
      if (memo.has(key)) return memo.get(key);
      const a = s1.slice(i, i + len), b = s2.slice(j, j + len);
      const view = (line, msg, tone) => T.step({ line, arrays: [A("s1", chars(s1), { hl: tones(range(i, i + len - 1), tone) }), A("s2", chars(s2), { hl: tones(range(j, j + len - 1), tone) })], msg });
      let r = false;
      if (a === b) { view(2, `"${a}" giống hệt nhau.`, "ok"); r = true; }
      else if (!same(a, b)) { view(3, `"${a}" và "${b}" khác bộ chữ cái → loại ngay.`, "bad"); }
      else for (let k = 1; k < len && !r; k++) {
        view(5, `Thử cắt "${a}" ở vị trí ${k}.`, "cur");
        r = (go(i, j, k) && go(i + k, j + k, len - k)) || (go(i, j + len - k, k) && go(i + k, j, len - k));
      }
      memo.set(key, r);
      return r;
    };
    return go(0, 0, s1.length);
  },
});

register("interleaving-string", {
  input: 's1 = "aabcc"\ns2 = "dbbca"\ns3 = "aadbbcbcac"',
  expect: true,
  code: `if m + n != len(s3): return false
dp[0][0] = true
for i in 0..m, j in 0..n:
  dp[i][j] = (dp[i-1][j] and s1[i-1] == s3[i+j-1])
          or (dp[i][j-1] and s2[j-1] == s3[i+j-1])
return dp[m][n]`,
  run({ s1, s2, s3 }, T) {
    const m = s1.length, n = s2.length;
    if (m + n !== s3.length) { T.step({ line: 1, arrays: [A("s3", chars(s3))], msg: "Độ dài không khớp." }); return false; }
    const dp = matrix(m + 1, n + 1, false), heads = { rowHead: ["∅", ...s1], colHead: ["∅", ...s2] };
    fill(T, "dp", dp, rowMajor(m + 1, n + 1), (i, j) => {
      if (!i && !j) return [true, [], "Chuỗi rỗng đan từ hai chuỗi rỗng.", 2];
      const a = i > 0 && dp[i - 1][j] && s1[i - 1] === s3[i + j - 1], b = j > 0 && dp[i][j - 1] && s2[j - 1] === s3[i + j - 1];
      return [a || b, [[i - 1, j], [i, j - 1]].filter(([x, y]) => x >= 0 && y >= 0), `s3[${i + j - 1}] = '${s3[i + j - 1]}' ${a ? "lấy từ s1" : b ? "lấy từ s2" : "không lấy được"}.`, 4];
    }, heads);
    return dp[m][n];
  },
});

register("distinct-subsequences", {
  input: 's = "rabbbit"\nt = "rabbit"',
  expect: 3,
  code: `dp[i][0] = 1
for i in 1..m, j in 1..n:
  dp[i][j] = dp[i-1][j]
  if s[i-1] == t[j-1]: dp[i][j] += dp[i-1][j-1]
return dp[m][n]`,
  run({ s, t }, T) {
    const m = s.length, n = t.length, dp = matrix(m + 1, n + 1, 0);
    for (let i = 0; i <= m; i++) dp[i][0] = 1;
    fill(T, "số cách", dp, rowMajor(m, n).map(([i, j]) => [i + 1, j + 1]), (i, j) => {
      const same = s[i - 1] === t[j - 1];
      return [dp[i - 1][j] + (same ? dp[i - 1][j - 1] : 0), same ? [[i - 1, j], [i - 1, j - 1]] : [[i - 1, j]], same ? `'${s[i - 1]}' khớp: bỏ qua (${dp[i - 1][j]}) hoặc dùng (${dp[i - 1][j - 1]}).` : `'${s[i - 1]}' ≠ '${t[j - 1]}': chỉ bỏ qua.`, same ? 4 : 3];
    }, { rowHead: ["∅", ...s], colHead: ["∅", ...t] });
    return dp[m][n];
  },
});

register("triangle", {
  input: "triangle = [[2], [3, 4], [6, 5, 7], [4, 1, 8, 3]]",
  expect: 11,
  code: `dp = last row
for r from n-2 down to 0:
  for c in 0..r:
    dp[c] = triangle[r][c] + min(dp[c], dp[c+1])
return dp[0]`,
  run({ triangle: t }, T) {
    const n = t.length, g = clone(t).map((r) => [...r, ...Array(n - r.length).fill(null)]);
    for (let r = n - 2; r >= 0; r--) for (let c = 0; c <= r; c++) {
      const a = g[r + 1][c], b = g[r + 1][c + 1];
      g[r][c] = t[r][c] + Math.min(a, b);
      T.step({ line: 4, grids: [G("tổng nhỏ nhất xuống đáy", clone(g), { hl: { [r + "," + c]: "cur", [r + 1 + "," + c]: "mark", [r + 1 + "," + (c + 1)]: "mark" } })], msg: `${t[r][c]} + min(${a}, ${b}) = ${g[r][c]}.` });
    }
    return g[0][0];
  },
});

function stateMachine(slug, input, expect, code, run) {
  register(slug, { input, expect, code, run });
}

stateMachine("best-time-to-buy-and-sell-stock-iii", "prices = [3, 3, 5, 0, 0, 3, 1, 4]", 6, `buy1 = buy2 = -inf, sell1 = sell2 = 0
for p in prices:
  buy1 = max(buy1, -p)
  sell1 = max(sell1, buy1 + p)
  buy2 = max(buy2, sell1 - p)
  sell2 = max(sell2, buy2 + p)
return sell2`, ({ prices }, T) => {
  let b1 = -Infinity, s1 = 0, b2 = -Infinity, s2 = 0;
  prices.forEach((p, i) => {
    b1 = Math.max(b1, -p); s1 = Math.max(s1, b1 + p); b2 = Math.max(b2, s1 - p); s2 = Math.max(s2, b2 + p);
    T.step({ line: 6, arrays: [A("prices", prices, { bars: true, hl: { [i]: "cur" }, ptr: ptrs({ i }) }), A("trạng thái", [b1, s1, b2, s2], { noIndex: false, indexLabels: ["mua 1", "bán 1", "mua 2", "bán 2"], small: true })], msg: `Ngày ${i}, giá ${p}: cập nhật lợi nhuận tốt nhất cho từng trạng thái.` });
  });
  return s2;
});

stateMachine("best-time-to-buy-and-sell-stock-iv", "k = 2\nprices = [3, 2, 6, 5, 0, 3]", 7, `buy[1..k] = -inf, sell[1..k] = 0
for p in prices:
  for t in 1..k:
    buy[t] = max(buy[t], sell[t-1] - p)
    sell[t] = max(sell[t], buy[t] + p)
return sell[k]`, ({ k, prices }, T) => {
  const buy = Array(k + 1).fill(-Infinity), sell = Array(k + 1).fill(0);
  prices.forEach((p, i) => {
    for (let t = 1; t <= k; t++) { buy[t] = Math.max(buy[t], sell[t - 1] - p); sell[t] = Math.max(sell[t], buy[t] + p); }
    T.step({ line: 5, arrays: [A("prices", prices, { bars: true, hl: { [i]: "cur" }, ptr: ptrs({ i }) })], grids: [G("trạng thái theo số giao dịch", [buy.slice(1).map(show), sell.slice(1)], { rowHead: ["mua", "bán"], colHead: range(1, k) })], msg: `Ngày ${i}, giá ${p}.` });
  });
  return sell[k];
});

stateMachine("best-time-to-buy-and-sell-stock-with-cooldown", "prices = [1, 2, 3, 0, 2]", 3, `hold = -inf, sold = 0, rest = 0
for p in prices:
  prevSold = sold
  sold = hold + p
  hold = max(hold, rest - p)
  rest = max(rest, prevSold)
return max(sold, rest)`, ({ prices }, T) => {
  let hold = -Infinity, sold = 0, rest = 0;
  prices.forEach((p, i) => {
    const prev = sold;
    sold = hold + p;
    hold = Math.max(hold, rest - p);
    rest = Math.max(rest, prev);
    T.step({ line: 6, arrays: [A("prices", prices, { bars: true, hl: { [i]: "cur" }, ptr: ptrs({ i }) })], vars: { "đang giữ": show(hold), "vừa bán": show(sold), "nghỉ": rest }, msg: `Ngày ${i}: sau khi bán phải nghỉ một ngày.` });
  });
  return Math.max(sold, rest);
});

register("palindrome-partitioning-ii", {
  input: 's = "aab"',
  expect: 1,
  code: `cuts[i] = i - 1 worst case
for center c, expand palindromes (l, r):
  cuts[r+1] = min(cuts[r+1], cuts[l] + 1)
return cuts[n]`,
  run({ s }, T) {
    const n = s.length, cuts = range(-1, n - 1);
    for (let c = 0; c < n; c++) for (let [l, r] of [[c, c], [c, c + 1]]) {
      while (l >= 0 && r < n && s[l] === s[r]) {
        cuts[r + 1] = Math.min(cuts[r + 1], cuts[l] + 1);
        T.step({ line: 3, arrays: [A("s", chars(s), { hl: tones(range(l, r), "ok") }), A("cuts", [...cuts], { indexLabels: range(0, n), hl: { [r + 1]: "cur", [l]: "mark" } })], msg: `"${s.slice(l, r + 1)}" đối xứng: tới hết nó cần tối đa ${cuts[l]} + 1 nhát.` });
        l--; r++;
      }
    }
    return cuts[n];
  },
});

register("word-break", {
  input: 's = "leetcode"\nwordDict = ["leet", "code"]',
  expect: true,
  code: `dp[0] = true
for i in 1..n:
  for j in 0..i-1:
    if dp[j] and s[j..i-1] in dict: dp[i] = true; break
return dp[n]`,
  run({ s, wordDict }, T) {
    const dict = new Set(wordDict), dp = Array(s.length + 1).fill(false);
    dp[0] = true;
    const v = () => A("dp", dp.map((x) => (x ? "T" : "F")), { small: true, hl: tones(dp.map((_, i) => i).filter((i) => dp[i]), "done") });
    for (let i = 1; i <= s.length; i++) for (let j = 0; j < i; j++) {
      const seg = s.slice(j, i), hit = dp[j] && dict.has(seg);
      if (dp[j]) T.step({ line: 4, key: hit, arrays: [A("s", chars(s), { small: true, hl: tones(range(j, i - 1), hit ? "ok" : "cur") }), v()], msg: hit ? `"${seg}" có trong từ điển → dp[${i}] đúng.` : `"${seg}" không có.` });
      if (hit) { dp[i] = true; break; }
    }
    return dp[s.length];
  },
});

register("maximum-product-subarray", {
  input: "nums = [2, 3, -2, 4]",
  expect: 6,
  code: `hi = lo = best = nums[0]
for v in nums[1..]:
  if v < 0: swap(hi, lo)
  hi = max(v, hi * v)
  lo = min(v, lo * v)
  best = max(best, hi)
return best`,
  run({ nums }, T) {
    let hi = nums[0], lo = nums[0], best = nums[0];
    for (let i = 1; i < nums.length; i++) {
      const v = nums[i];
      if (v < 0) [hi, lo] = [lo, hi];
      hi = Math.max(v, hi * v);
      lo = Math.min(v, lo * v);
      const up = hi > best;
      best = Math.max(best, hi);
      T.step({ line: up ? 6 : 4, key: up, arrays: [A("nums", nums, { hl: { [i]: "cur" }, ptr: ptrs({ i }) })], vars: { "tích lớn nhất tại i": hi, "tích nhỏ nhất tại i": lo, best }, msg: v < 0 ? "Số âm đổi vai max và min, vì vậy phải giữ cả min." : `Nối vào đoạn cũ hoặc bắt đầu lại từ ${v}.` });
    }
    return best;
  },
});

register("house-robber-ii", {
  input: "nums = [2, 3, 2]",
  expect: 3,
  code: `line(a): take, skip = 0, 0
  for v in a: take, skip = skip + v, max(take, skip)
  return max(take, skip)
return max(line(nums[1..]), line(nums[..n-2]))`,
  run({ nums }, T) {
    if (nums.length === 1) { T.step({ line: 4, arrays: [A("nums", nums)], msg: "Chỉ có một nhà." }); return nums[0]; }
    const line = (lo, hi, label) => {
      let take = 0, skip = 0;
      for (let i = lo; i <= hi; i++) {
        [take, skip] = [skip + nums[i], Math.max(take, skip)];
        T.step({ line: 2, arrays: [A(`nums (${label})`, nums, { range: [lo, hi], dimOutside: true, hl: { [i]: "cur" }, ptr: ptrs({ i }) })], vars: { "cướp nhà này": take, "bỏ nhà này": skip }, msg: `Nhà ${i} có ${nums[i]}.` });
      }
      return Math.max(take, skip);
    };
    const a = line(1, nums.length - 1, "bỏ nhà đầu"), b = line(0, nums.length - 2, "bỏ nhà cuối");
    T.step({ line: 4, key: true, arrays: [A("nums", nums)], vars: { "bỏ nhà đầu": a, "bỏ nhà cuối": b }, msg: "Nhà đầu và cuối kề nhau: tách hai trường hợp." });
    return Math.max(a, b);
  },
});

register("perfect-squares", {
  input: "n = 12",
  expect: 3,
  code: `dp[0] = 0
for i in 1..n:
  for each square s <= i:
    dp[i] = min(dp[i], dp[i-s] + 1)
return dp[n]`,
  run({ n }, T) {
    const dp = Array(n + 1).fill(Infinity);
    dp[0] = 0;
    for (let i = 1; i <= n; i++) {
      let from = 0;
      for (let k = 1; k * k <= i; k++) if (dp[i - k * k] + 1 < dp[i]) { dp[i] = dp[i - k * k] + 1; from = i - k * k; }
      T.step({ line: 4, arrays: [A("dp", dp.map(show), { small: true, hl: { [i]: "cur", [from]: "mark" } })], msg: `${i} = ${from} + ${i - from}: cần ${dp[i]} số chính phương.` });
    }
    return dp[n];
  },
});

register("burst-balloons", {
  input: "nums = [3, 1, 5, 8]",
  expect: 167,
  code: `a = [1] + nums + [1]
for len in 2..n+1, l, r = l + len:
  for k in l+1..r-1:          // k là quả nổ CUỐI trong (l, r)
    dp[l][r] = max(dp[l][r], dp[l][k] + a[l]*a[k]*a[r] + dp[k][r])
return dp[0][n+1]`,
  run({ nums }, T) {
    const a = [1, ...nums, 1], n = a.length, dp = matrix(n, n, 0);
    for (let len = 2; len < n; len++) for (let l = 0; l + len < n; l++) {
      const r = l + len;
      let at = -1;
      for (let k = l + 1; k < r; k++) {
        const v = dp[l][k] + a[l] * a[k] * a[r] + dp[k][r];
        if (v > dp[l][r]) { dp[l][r] = v; at = k; }
      }
      T.step({ line: 4, grids: [G("dp[l][r]", clone(dp), { rowHead: a, colHead: a, hl: { [l + "," + r]: "cur", [l + "," + at]: "mark", [at + "," + r]: "mark" } })], arrays: [A("a", a, { range: [l, r], hl: { [at]: "ok", [l]: "mark", [r]: "mark" } })], msg: `Trong (${l}, ${r}), để quả ${a[at]} nổ cuối: được ${dp[l][r]}.` });
    }
    return dp[0][n - 1];
  },
});

register("frog-jump", {
  input: "stones = [0, 1, 3, 5, 6, 8, 12, 17]",
  expect: true,
  code: `jumps[stone] = set of jump sizes that reach it
jumps[0] = {0}
for s in stones:
  for k in jumps[s]:
    for step in k-1, k, k+1 (step > 0):
      if s + step is a stone: jumps[s + step].add(step)
return jumps[last] not empty`,
  run({ stones }, T) {
    const j = new Map(stones.map((s) => [s, new Set()]));
    j.get(0).add(0);
    stones.forEach((s, i) => {
      for (const k of j.get(s)) for (const step of [k - 1, k, k + 1]) if (step > 0 && j.has(s + step)) j.get(s + step).add(step);
      T.step({ line: 6, arrays: [A("stones", stones, { hl: { [i]: "cur", ...tones(stones.map((_, x) => x).filter((x) => j.get(stones[x]).size), "done"), [i]: "cur" }, ptr: ptrs({ i }) }), A("bước nhảy tới được", stones.map((x) => [...j.get(x)].join(",") || "·"), { small: true })], msg: `Từ đá ${s}, thử các bước ${[...j.get(s)].map((k) => `${k - 1},${k},${k + 1}`).join(" | ") || "không có"}.` });
    });
    return j.get(stones[stones.length - 1]).size > 0;
  },
});

register("target-sum", {
  input: "nums = [1, 1, 1, 1, 1]\ntarget = 3",
  expect: 5,
  code: `// tập + có tổng P: P = (sum + target) / 2
if (sum + target) is odd or negative: return 0
dp[0] = 1
for v in nums:
  for s from P down to v: dp[s] += dp[s-v]
return dp[P]`,
  run({ nums, target }, T) {
    const sum = nums.reduce((x, y) => x + y, 0);
    if (Math.abs(target) > sum || (sum + target) % 2) { T.step({ line: 2, arrays: [A("nums", nums)], msg: "Không thể đạt target." }); return 0; }
    const P = (sum + target) / 2, dp = Array(P + 1).fill(0);
    dp[0] = 1;
    T.step({ line: 3, arrays: [A("dp (số cách đạt tổng)", [...dp])], vars: { P }, msg: `Đổi thành bài chọn tập con có tổng ${P}.` });
    nums.forEach((v, i) => {
      for (let s = P; s >= v; s--) dp[s] += dp[s - v];
      T.step({ line: 5, arrays: [A("nums", nums, { hl: { [i]: "cur" } }), A("dp (số cách đạt tổng)", [...dp], { hl: { [P]: "ok" } })], msg: `Xét thêm số ${v}.` });
    });
    return dp[P];
  },
});

register("freedom-trail", {
  input: 'ring = "godding"\nkey = "gd"',
  expect: 4,
  code: `dp[pos] = 0 for pos 0, inf otherwise
for each char c in key:
  next[q] = min over p of dp[p] + dist(p, q), for q with ring[q] == c
  dp = next
return min(dp) + len(key)`,
  run({ ring, key }, T) {
    const n = ring.length, dist = (a, b) => Math.min(Math.abs(a - b), n - Math.abs(a - b));
    let dp = Array(n).fill(Infinity);
    dp[0] = 0;
    for (const c of key) {
      const nx = Array(n).fill(Infinity);
      for (let q = 0; q < n; q++) if (ring[q] === c) for (let p = 0; p < n; p++) nx[q] = Math.min(nx[q], dp[p] + dist(p, q));
      T.step({ line: 3, arrays: [A("ring", chars(ring), { hl: tones(range(0, n - 1).filter((q) => ring[q] === c), "cur") }), A("số bước xoay tối thiểu", nx.map(show), { small: true })], msg: `Đưa chữ '${c}' về đỉnh, từ mọi vị trí trước đó.` });
      dp = nx;
    }
    return Math.min(...dp) + key.length;
  },
});

register("longest-palindromic-subsequence", {
  input: 's = "bbbab"',
  expect: 4,
  code: `for i from n-1 down to 0:
  dp[i][i] = 1
  for j in i+1..n-1:
    if s[i] == s[j]: dp[i][j] = dp[i+1][j-1] + 2
    else: dp[i][j] = max(dp[i+1][j], dp[i][j-1])
return dp[0][n-1]`,
  run({ s }, T) {
    const n = s.length, dp = matrix(n, n, 0);
    const heads = { rowHead: [...s], colHead: [...s] };
    for (let i = n - 1; i >= 0; i--) {
      dp[i][i] = 1;
      for (let j = i + 1; j < n; j++) {
        const same = s[i] === s[j];
        dp[i][j] = same ? dp[i + 1][j - 1] + 2 : Math.max(dp[i + 1][j], dp[i][j - 1]);
        T.step({ line: same ? 4 : 5, grids: [G("dp[i][j]", clone(dp), { ...heads, hl: { [i + "," + j]: "cur", ...(same ? { [i + 1 + "," + (j - 1)]: "mark" } : { [i + 1 + "," + j]: "mark", [i + "," + (j - 1)]: "mark" }) } })], msg: same ? `'${s[i]}' hai đầu giống nhau: lấy trong rồi cộng 2.` : "Khác nhau: bỏ một đầu." });
      }
    }
    return dp[0][n - 1];
  },
});

register("coin-change-ii", {
  input: "amount = 5\ncoins = [1, 2, 5]",
  expect: 4,
  code: `dp[0] = 1
for c in coins:
  for s in c..amount:
    dp[s] += dp[s-c]
return dp[amount]`,
  run({ amount, coins }, T) {
    const dp = Array(amount + 1).fill(0);
    dp[0] = 1;
    coins.forEach((c) => {
      for (let s = c; s <= amount; s++) {
        dp[s] += dp[s - c];
        T.step({ line: 4, arrays: [A("dp", [...dp], { hl: { [s]: "cur", [s - c]: "mark" } })], vars: { "đồng": c }, msg: `Tổng ${s}: thêm ${dp[s - c]} cách dùng đồng ${c}. Lặp đồng ở vòng ngoài nên không đếm trùng thứ tự.` });
      }
    });
    return dp[amount];
  },
});

register("remove-boxes", {
  input: "boxes = [1, 3, 2, 2, 2, 3, 4, 3, 1]",
  expect: 23,
  code: `// dp(l, r, k): đoạn [l, r] với k hộp cùng màu boxes[l] dính bên trái
dp(l, r, k):
  skip boxes equal to boxes[l]; k grows
  best = (k + 1)^2 + dp(l + 1, r, 0)
  for m in l+1..r with boxes[m] == boxes[l]:
    best = max(best, dp(l + 1, m - 1, 0) + dp(m, r, k + 1))
  return best`,
  run({ boxes }, T) {
    const memo = new Map();
    const dp = (l, r, k) => {
      if (l > r) return 0;
      while (l < r && boxes[l + 1] === boxes[l]) { l++; k++; }
      const key = `${l},${r},${k}`;
      if (memo.has(key)) return memo.get(key);
      let best = (k + 1) ** 2 + dp(l + 1, r, 0);
      for (let m = l + 1; m <= r; m++) if (boxes[m] === boxes[l]) best = Math.max(best, dp(l + 1, m - 1, 0) + dp(m, r, k + 1));
      memo.set(key, best);
      if (r - l >= 3 || r === boxes.length - 1) T.step({ line: 6, arrays: [A("boxes", boxes, { range: [l, r], dimOutside: true, hl: { [l]: "cur" } })], vars: { "kèm trái k": k, "giá trị": best, "trạng thái đã nhớ": memo.size }, msg: `Đoạn [${l}, ${r}] có thêm ${k} hộp màu ${boxes[l]} bên trái: tối đa ${best} điểm.` });
      return best;
    };
    return dp(0, boxes.length - 1, 0);
  },
});

register("k-inverse-pairs-array", {
  input: "n = 3\nk = 1",
  expect: 2,
  code: `dp[0][0] = 1
for i in 1..n:
  for j in 0..k:
    dp[i][j] = dp[i][j-1] + dp[i-1][j]
    if j >= i: dp[i][j] -= dp[i-1][j-i]
return dp[n][k]`,
  run({ n, k }, T) {
    const dp = matrix(n + 1, k + 1, 0);
    dp[0][0] = 1;
    for (let i = 1; i <= n; i++) for (let j = 0; j <= k; j++) {
      dp[i][j] = (j ? dp[i][j - 1] : 0) + dp[i - 1][j] - (j >= i ? dp[i - 1][j - i] : 0);
      T.step({ line: 5, grids: [G("dp[n][k]", clone(dp), { hl: { [i + "," + j]: "cur", [i - 1 + "," + j]: "mark", ...(j ? { [i + "," + (j - 1)]: "mark" } : {}) } })], msg: `Chèn số ${i} vào i vị trí tạo 0..${i - 1} cặp ngược: tổng cửa sổ trên hàng trước.` });
    }
    return dp[n][k];
  },
});

register("min-cost-climbing-stairs", {
  input: "cost = [10, 15, 20]",
  expect: 15,
  code: `a = 0, b = 0
for i in 2..n:
  (a, b) = (b, min(b + cost[i-1], a + cost[i-2]))
return b`,
  run({ cost }, T) {
    const dp = Array(cost.length + 1).fill(null);
    dp[0] = dp[1] = 0;
    for (let i = 2; i <= cost.length; i++) {
      dp[i] = Math.min(dp[i - 1] + cost[i - 1], dp[i - 2] + cost[i - 2]);
      T.step({ line: 3, arrays: [A("cost", cost, { hl: { [i - 1]: "mark", [i - 2]: "mark" } }), A("dp", [...dp], { hl: { [i]: "cur" } })], msg: `Tới bậc ${i}: min(${dp[i - 1]} + ${cost[i - 1]}, ${dp[i - 2]} + ${cost[i - 2]}) = ${dp[i]}.` });
    }
    return dp[cost.length];
  },
});

register("super-egg-drop", {
  input: "k = 2\nn = 6",
  expect: 3,
  code: `// f[m][k]: số tầng phân biệt được với m lần thả, k trứng
m = 0
while f[m][k] < n:
  m++
  for e in 1..k:
    f[m][e] = f[m-1][e-1] + f[m-1][e] + 1
return m`,
  run({ k, n }, T) {
    const f = [Array(k + 1).fill(0)];
    let m = 0;
    while (f[m][k] < n) {
      m++;
      f.push(Array(k + 1).fill(0));
      for (let e = 1; e <= k; e++) {
        f[m][e] = f[m - 1][e - 1] + f[m - 1][e] + 1;
        T.step({ line: 6, grids: [G("f[lần thả][trứng]", clone(f), { hl: { [m + "," + e]: "cur", [m - 1 + "," + e]: "mark", [m - 1 + "," + (e - 1)]: "mark" }, colHead: range(0, k) })], msg: `Thả một lần: vỡ thì xét dưới, không vỡ thì xét trên, cộng tầng vừa thả = ${f[m][e]}.` });
      }
    }
    return m;
  },
});

register("number-of-submatrices-that-sum-to-target", {
  input: "matrix = [[0, 1, 0], [1, 1, 1], [0, 1, 0]]\ntarget = 0",
  expect: 4,
  code: `for top in 0..m-1:
  col = zeros
  for bottom in top..m-1:
    col += row bottom
    count += subarrays of col summing to target (prefix map)
return count`,
  run({ matrix: a, target }, T) {
    const m = a.length, n = a[0].length;
    let count = 0;
    for (let t = 0; t < m; t++) {
      const col = Array(n).fill(0);
      for (let b = t; b < m; b++) {
        col.forEach((_, c) => (col[c] += a[b][c]));
        const seen = new Map([[0, 1]]);
        let p = 0, add = 0;
        for (const v of col) { p += v; add += seen.get(p - target) ?? 0; seen.set(p, (seen.get(p) ?? 0) + 1); }
        count += add;
        const hl = {};
        for (let r = t; r <= b; r++) for (let c = 0; c < n; c++) hl[r + "," + c] = "win";
        T.step({ line: 5, key: add > 0, grids: [G("matrix", a, { hl })], arrays: [A("tổng theo cột", [...col])], vars: { "thêm": add, count }, msg: `Hàng ${t}..${b} gộp thành một mảng, đếm đoạn có tổng ${target}.` });
      }
    }
    return count;
  },
});

register("shortest-common-supersequence", {
  input: 'str1 = "abac"\nstr2 = "cab"',
  expect: "cabac",
  code: `dp = LCS table of str1, str2
i = m, j = n
while i > 0 or j > 0:
  if same char: take it; i--, j--
  else if dp[i-1][j] >= dp[i][j-1]: take str1[--i]
  else: take str2[--j]
return reverse(out)`,
  run({ str1: a, str2: b }, T) {
    const m = a.length, n = b.length, dp = matrix(m + 1, n + 1, 0);
    const heads = { rowHead: ["∅", ...a], colHead: ["∅", ...b] };
    for (let i = 1; i <= m; i++) for (let j = 1; j <= n; j++) dp[i][j] = a[i - 1] === b[j - 1] ? dp[i - 1][j - 1] + 1 : Math.max(dp[i - 1][j], dp[i][j - 1]);
    T.step({ line: 1, grids: [G("LCS", clone(dp), heads)], msg: "Dựng bảng LCS: phần chung chỉ cần viết một lần." });
    const out = [];
    let i = m, j = n;
    while (i > 0 || j > 0) {
      let line, c;
      if (i && j && a[i - 1] === b[j - 1]) { c = a[i - 1]; i--; j--; line = 4; }
      else if (i && (!j || dp[i - 1][j] >= dp[i][j - 1])) { c = a[--i]; line = 5; }
      else { c = b[--j]; line = 6; }
      out.push(c);
      T.step({ line, grids: [G("LCS", clone(dp), { ...heads, hl: { [i + "," + j]: "cur" } })], arrays: [A("kết quả (ngược)", [...out], { noIndex: true })], msg: `Lấy '${c}'.` });
    }
    return out.reverse().join("");
  },
});

register("count-vowels-permutation", {
  input: "n = 5",
  expect: 68,
  code: `a = e = i = o = u = 1
repeat n - 1 times:
  a' = e + i + u
  e' = a + i
  i' = e + o
  o' = i
  u' = i + o
return a + e + i + o + u`,
  run({ n }, T) {
    const label = ["a", "e", "i", "o", "u"];
    let v = [1, 1, 1, 1, 1];
    for (let t = 2; t <= n; t++) {
      const [a, e, i, o, u] = v;
      v = [e + i + u, a + i, e + o, i, i + o];
      T.step({ line: 7, arrays: [A(`độ dài ${t}, kết thúc bằng`, [...v], { indexLabels: label })], vars: { "tổng": v.reduce((x, y) => x + y, 0) }, msg: "Chuyển trạng thái theo luật nguyên âm nào được đứng sau nguyên âm nào." });
    }
    return v.reduce((x, y) => x + y, 0);
  },
});

register("minimum-insertion-steps-to-make-a-string-palindrome", {
  input: 's = "mbadm"',
  expect: 2,
  code: `for i from n-1 down to 0:
  for j in i+1..n-1:
    if s[i] == s[j]: dp[i][j] = dp[i+1][j-1]
    else: dp[i][j] = 1 + min(dp[i+1][j], dp[i][j-1])
return dp[0][n-1]`,
  run({ s }, T) {
    const n = s.length, dp = matrix(n, n, 0), heads = { rowHead: [...s], colHead: [...s] };
    for (let i = n - 1; i >= 0; i--) for (let j = i + 1; j < n; j++) {
      const same = s[i] === s[j];
      dp[i][j] = same ? dp[i + 1][j - 1] : 1 + Math.min(dp[i + 1][j], dp[i][j - 1]);
      T.step({ line: same ? 3 : 4, grids: [G("số ký tự cần chèn", clone(dp), { ...heads, hl: { [i + "," + j]: "cur", ...(same ? { [i + 1 + "," + (j - 1)]: "mark" } : { [i + 1 + "," + j]: "mark", [i + "," + (j - 1)]: "mark" }) } })], msg: same ? `'${s[i]}' khớp hai đầu.` : `Chèn một ký tự để khớp '${s[i]}' hoặc '${s[j]}'.` });
    }
    return dp[0][n - 1];
  },
});

register("minimum-difficulty-of-a-job-schedule", {
  input: "jobDifficulty = [6, 5, 4, 3, 2, 1]\nd = 2",
  expect: 7,
  code: `dp[0][i] = max(jobs[0..i])
for day in 1..d-1:
  for i in day..n-1:
    for j from i down to day:
      hardest = max(hardest, jobs[j])
      dp[day][i] = min(dp[day][i], dp[day-1][j-1] + hardest)
return dp[d-1][n-1]`,
  run({ jobDifficulty: a, d }, T) {
    const n = a.length;
    if (n < d) { T.step({ line: 7, arrays: [A("jobs", a)], msg: "Ít việc hơn số ngày." }); return -1; }
    const dp = matrix(d, n, Infinity);
    dp[0][0] = a[0];
    for (let i = 1; i < n; i++) dp[0][i] = Math.max(dp[0][i - 1], a[i]);
    T.step({ line: 1, grids: [G("dp[ngày][việc]", shown(dp), { colHead: a })], msg: "Ngày đầu làm mọi việc đầu tiên." });
    for (let day = 1; day < d; day++) for (let i = day; i < n; i++) {
      let hard = 0, at = -1;
      for (let j = i; j >= day; j--) {
        hard = Math.max(hard, a[j]);
        if (dp[day - 1][j - 1] + hard < dp[day][i]) { dp[day][i] = dp[day - 1][j - 1] + hard; at = j; }
      }
      T.step({ line: 6, grids: [G("dp[ngày][việc]", shown(dp), { colHead: a, hl: { [day + "," + i]: "cur", [day - 1 + "," + (at - 1)]: "mark" } })], msg: `Ngày ${day} làm các việc ${at}..${i}: tổng ${dp[day][i]}.` });
    }
    return dp[d - 1][n - 1];
  },
});

register("stone-game-iii", {
  input: "stoneValue = [1, 2, 3, 7]",
  expect: "Bob",
  code: `// dp[i]: hiệu điểm tốt nhất của người đi từ vị trí i
for i from n-1 down to 0:
  for take in 1..3:
    dp[i] = max(dp[i], sum(stones[i..i+take-1]) - dp[i+take])
return dp[0] > 0 ? "Alice" : dp[0] < 0 ? "Bob" : "Tie"`,
  run({ stoneValue: a }, T) {
    const n = a.length, dp = Array(n + 1).fill(0);
    for (let i = n - 1; i >= 0; i--) {
      dp[i] = -Infinity;
      let s = 0, best = 1;
      for (let k = 1; k <= 3 && i + k <= n; k++) {
        s += a[i + k - 1];
        if (s - dp[i + k] > dp[i]) { dp[i] = s - dp[i + k]; best = k; }
      }
      T.step({ line: 4, arrays: [A("stones", a, { hl: tones(range(i, i + best - 1), "cur") }), A("dp", dp.map(show), { hl: { [i]: "ok" } })], msg: `Từ ${i}, lấy ${best} viên là tốt nhất: hơn đối thủ ${dp[i]} điểm.` });
    }
    return dp[0] > 0 ? "Alice" : dp[0] < 0 ? "Bob" : "Tie";
  },
});

register("minimum-cost-to-cut-a-stick", {
  input: "n = 7\ncuts = [1, 3, 4, 5]",
  expect: 16,
  code: `c = sorted [0] + cuts + [n]
for len in 2..m-1, l, r = l + len:
  dp[l][r] = min over k in (l, r) of dp[l][k] + dp[k][r]
  dp[l][r] += c[r] - c[l]
return dp[0][m-1]`,
  run({ n, cuts }, T) {
    const c = [0, ...[...cuts].sort((x, y) => x - y), n], m = c.length, dp = matrix(m, m, 0);
    for (let len = 2; len < m; len++) for (let l = 0; l + len < m; l++) {
      const r = l + len;
      let best = Infinity, at = -1;
      for (let k = l + 1; k < r; k++) if (dp[l][k] + dp[k][r] < best) { best = dp[l][k] + dp[k][r]; at = k; }
      dp[l][r] = best + c[r] - c[l];
      T.step({ line: 4, grids: [G("dp[l][r]", clone(dp), { rowHead: c, colHead: c, hl: { [l + "," + r]: "cur", [l + "," + at]: "mark", [at + "," + r]: "mark" } })], msg: `Thanh [${c[l]}, ${c[r]}] dài ${c[r] - c[l]}, cắt trước ở ${c[at]}: tổng ${dp[l][r]}.` });
    }
    return dp[0][m - 1];
  },
});

register("odd-even-jump", {
  input: "arr = [10, 13, 12, 14, 15]",
  expect: 2,
  code: `odd[n-1] = even[n-1] = true
for i from n-2 down to 0:
  j = next index with smallest value >= arr[i]
  k = next index with largest value <= arr[i]
  odd[i] = even[j]
  even[i] = odd[k]
return count of odd[i]`,
  run({ arr }, T) {
    const n = arr.length, odd = Array(n).fill(false), even = Array(n).fill(false);
    odd[n - 1] = even[n - 1] = true;
    for (let i = n - 2; i >= 0; i--) {
      let j = -1, k = -1;
      for (let x = i + 1; x < n; x++) {
        if (arr[x] >= arr[i] && (j < 0 || arr[x] < arr[j])) j = x;
        if (arr[x] <= arr[i] && (k < 0 || arr[x] > arr[k])) k = x;
      }
      odd[i] = j >= 0 && even[j];
      even[i] = k >= 0 && odd[k];
      T.step({ line: 5, arrays: [A("arr", arr, { hl: { [i]: "cur", [j]: "ok", [k]: "mark" }, ptr: ptrs({ i, j, k }, n) }), A("nhảy lẻ tới cuối", odd.map((b) => (b ? "T" : "F")), { small: true }), A("nhảy chẵn tới cuối", even.map((b) => (b ? "T" : "F")), { small: true })], msg: `Từ ${i}: nhảy lẻ sang ${j}, nhảy chẵn sang ${k}.` });
    }
    return odd.filter(Boolean).length;
  },
});

