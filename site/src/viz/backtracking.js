/* Backtracking: each visualizer grows a recursion tree as it explores. */
import { A, DIRS, G, RecTree, chars, clone, matrix, register, tones } from "./engine.js";

const range = (a, b) => Array.from({ length: Math.max(0, b - a + 1) }, (_, i) => a + i);

register("letter-combinations-of-a-phone-number", {
  input: 'digits = "23"',
  expect: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"],
  code: `dfs(i, path):
  if i == len(digits): result.add(path); return
  for c in letters[digits[i]]:
    dfs(i + 1, path + c)`,
  run({ digits }, T) {
    const map = { 2: "abc", 3: "def", 4: "ghi", 5: "jkl", 6: "mno", 7: "pqrs", 8: "tuv", 9: "wxyz" }, out = [], rec = new RecTree();
    if (!digits) { T.step({ line: 1, msg: "Chuỗi rỗng." }); return out; }
    const dfs = (i, path) => {
      rec.enter(path || "∅");
      if (i === digits.length) {
        out.push(path);
        rec.mark("ok");
        T.step({ line: 2, key: true, nary: [rec.view()], vars: { "kết quả": out }, msg: `Đủ độ dài: "${path}".` });
      } else {
        T.step({ line: 3, nary: [rec.view()], arrays: [A("digits", chars(digits), { hl: { [i]: "cur" } })], msg: `Chữ số ${digits[i]} có các chữ ${map[digits[i]]}.` });
        for (const c of map[digits[i]]) dfs(i + 1, path + c);
      }
      rec.leave(i === digits.length ? "ok" : "done");
    };
    dfs(0, "");
    return out;
  },
});

register("generate-parentheses", {
  input: "n = 3",
  expect: ["((()))", "(()())", "(())()", "()(())", "()()()"],
  code: `dfs(path, open, close):
  if len(path) == 2n: result.add(path); return
  if open < n: dfs(path + "(", open + 1, close)
  if close < open: dfs(path + ")", open, close + 1)`,
  run({ n }, T) {
    const out = [], rec = new RecTree();
    const dfs = (p, o, c) => {
      rec.enter(p || "∅");
      const leaf = p.length === 2 * n;
      if (leaf) out.push(p);
      T.step({ line: leaf ? 2 : o < n ? 3 : 4, key: leaf, nary: [rec.view()], vars: { open: o, close: c, "kết quả": out }, msg: leaf ? `Hoàn chỉnh: ${p}.` : `Đã mở ${o}, đóng ${c}.` });
      if (!leaf) {
        if (o < n) dfs(`${p}(`, o + 1, c);
        if (c < o) dfs(`${p})`, o, c + 1);
      }
      rec.leave(leaf ? "ok" : "done");
    };
    dfs("", 0, 0);
    return out;
  },
});

register("sudoku-solver", {
  input: 'board = [["5","3","4","6","7","8","9","1","2"],["6","7","2","1","9","5","3","4","8"],["1","9","8","3","4","2","5","6","7"],["8","5","9","7","6","1","4","2","3"],["4","2","6","8",".","3",".",".","1"],["7","1","3","9","2","4","8","5","6"],[".","6","1","5","3","7","2","8","."],["2","8","7",".","1","9","6","3","5"],["3","4","5","2","8","6","1","7","."]]',
  expect: [["5", "3", "4", "6", "7", "8", "9", "1", "2"], ["6", "7", "2", "1", "9", "5", "3", "4", "8"], ["1", "9", "8", "3", "4", "2", "5", "6", "7"], ["8", "5", "9", "7", "6", "1", "4", "2", "3"], ["4", "2", "6", "8", "5", "3", "7", "9", "1"], ["7", "1", "3", "9", "2", "4", "8", "5", "6"], ["9", "6", "1", "5", "3", "7", "2", "8", "4"], ["2", "8", "7", "4", "1", "9", "6", "3", "5"], ["3", "4", "5", "2", "8", "6", "1", "7", "9"]],
  code: `solve():
  find next empty cell
  if none: return true
  for d in 1..9:
    if d fits row, column and box:
      place d
      if solve(): return true
      remove d
  return false`,
  run({ board }, T) {
    const b = clone(board);
    const fits = (r, c, d) => {
      for (let i = 0; i < 9; i++) if (b[r][i] === d || b[i][c] === d || b[3 * Math.floor(r / 3) + Math.floor(i / 3)][3 * Math.floor(c / 3) + (i % 3)] === d) return false;
      return true;
    };
    const given = {};
    board.forEach((row, r) => row.forEach((v, c) => { if (v !== ".") given[r + "," + c] = "dim"; }));
    const view = (line, msg, r, c, tone, key) => T.step({ line, key, grids: [G("board", b.map((x) => x.map((v) => (v === "." ? null : v))), { hl: { ...given, [r + "," + c]: tone } })], msg });
    const solve = () => {
      for (let r = 0; r < 9; r++) for (let c = 0; c < 9; c++) {
        if (b[r][c] !== ".") continue;
        for (let d = 1; d <= 9; d++) {
          const ch = String(d);
          if (!fits(r, c, ch)) continue;
          b[r][c] = ch;
          view(6, `Thử ${ch} ở (${r}, ${c}).`, r, c, "cur");
          if (solve()) return true;
          b[r][c] = ".";
          view(8, `${ch} dẫn tới ngõ cụt, gỡ ra.`, r, c, "bad");
        }
        return false;
      }
      return true;
    };
    solve();
    return b;
  },
});

function combo(slug, input, expect, code, explore) {
  register(slug, {
    input, expect, code,
    run(args, T) {
      const out = [], rec = new RecTree(), path = [];
      const ctx = {
        visit(label, line, msg, { hit, arrays, vars } = {}) {
          rec.enter(label);
          if (hit) out.push([...path]);
          T.step({ line, key: hit, nary: [rec.view()], arrays, vars: { path: [...path], "số kết quả": out.length, ...vars }, msg });
        },
        leave(hit) { rec.leave(hit ? "ok" : "done"); },
        path, out,
      };
      explore(args, ctx);
      return out;
    },
  });
}

combo("combination-sum", "candidates = [2, 3, 6, 7]\ntarget = 7", [[2, 2, 3], [7]], `dfs(start, left):
  if left == 0: result.add(path); return
  for i in start..n-1:
    if candidates[i] <= left:
      path.add(candidates[i])
      dfs(i, left - candidates[i])      // được dùng lại
      path.removeLast()`, ({ candidates, target }, x) => {
  const dfs = (s, left, label) => {
    const hit = left === 0;
    x.visit(label, hit ? 2 : 3, hit ? `Đủ tổng: [${x.path}].` : `Còn thiếu ${left}.`, { hit });
    if (!hit) for (let i = s; i < candidates.length; i++) if (candidates[i] <= left) {
      x.path.push(candidates[i]);
      dfs(i, left - candidates[i], `+${candidates[i]}`);
      x.path.pop();
    }
    x.leave(hit);
  };
  dfs(0, target, `${target}`);
});

combo("combination-sum-ii", "candidates = [10, 1, 2, 7, 6, 1, 5]\ntarget = 8", [[1, 1, 6], [1, 2, 5], [1, 7], [2, 6]], `sort(candidates)
dfs(start, left):
  if left == 0: result.add(path); return
  for i in start..n-1:
    if i > start and c[i] == c[i-1]: continue   // chặn trùng
    if c[i] > left: break
    path.add(c[i]); dfs(i + 1, left - c[i]); path.removeLast()`, ({ candidates, target }, x) => {
  const c = [...candidates].sort((p, q) => p - q);
  const dfs = (s, left, label) => {
    const hit = left === 0;
    x.visit(label, hit ? 3 : 4, hit ? `Đủ tổng: [${x.path}].` : `Còn thiếu ${left}.`, { hit, arrays: [A("đã sắp", c)] });
    if (!hit) for (let i = s; i < c.length; i++) {
      if (i > s && c[i] === c[i - 1]) continue;
      if (c[i] > left) break;
      x.path.push(c[i]);
      dfs(i + 1, left - c[i], `+${c[i]}`);
      x.path.pop();
    }
    x.leave(hit);
  };
  dfs(0, target, `${target}`);
});

combo("permutations-ii", "nums = [1, 1, 2]", [[1, 1, 2], [1, 2, 1], [2, 1, 1]], `sort(nums)
dfs():
  if len(path) == n: result.add(path); return
  for i in 0..n-1:
    if used[i] or (i > 0 and nums[i] == nums[i-1] and !used[i-1]): continue
    used[i] = true; path.add(nums[i])
    dfs()
    path.removeLast(); used[i] = false`, ({ nums }, x) => {
  const a = [...nums].sort((p, q) => p - q), used = a.map(() => false);
  const dfs = (label) => {
    const hit = x.path.length === a.length;
    x.visit(label, hit ? 3 : 4, hit ? `Hoán vị: [${x.path}].` : `Đang có [${x.path}].`, { hit, arrays: [A("nums", a, { hl: tones(a.map((_, i) => i).filter((i) => used[i]), "done") })] });
    if (!hit) a.forEach((v, i) => {
      if (used[i] || (i && a[i] === a[i - 1] && !used[i - 1])) return;
      used[i] = true;
      x.path.push(v);
      dfs(String(v));
      x.path.pop();
      used[i] = false;
    });
    x.leave(hit);
  };
  dfs("∅");
});

combo("combinations", "n = 4\nk = 2", [[1, 2], [1, 3], [1, 4], [2, 3], [2, 4], [3, 4]], `dfs(start):
  if len(path) == k: result.add(path); return
  for x in start..n:
    path.add(x); dfs(x + 1); path.removeLast()`, ({ n, k }, x) => {
  const dfs = (s, label) => {
    const hit = x.path.length === k;
    x.visit(label, hit ? 2 : 3, hit ? `Đủ ${k} số: [${x.path}].` : `Chọn tiếp từ ${s}.`, { hit });
    if (!hit) for (let v = s; v <= n; v++) { x.path.push(v); dfs(v + 1, String(v)); x.path.pop(); }
    x.leave(hit);
  };
  dfs(1, "∅");
});

combo("subsets-ii", "nums = [1, 2, 2]", [[], [1], [1, 2], [1, 2, 2], [2], [2, 2]], `sort(nums)
dfs(start):
  result.add(path)
  for i in start..n-1:
    if i > start and nums[i] == nums[i-1]: continue
    path.add(nums[i]); dfs(i + 1); path.removeLast()`, ({ nums }, x) => {
  const a = [...nums].sort((p, q) => p - q);
  const dfs = (s, label) => {
    x.visit(label, 3, `Tập con [${x.path}].`, { hit: true });
    for (let i = s; i < a.length; i++) {
      if (i > s && a[i] === a[i - 1]) continue;
      x.path.push(a[i]);
      dfs(i + 1, String(a[i]));
      x.path.pop();
    }
    x.leave(true);
  };
  dfs(0, "∅");
});

register("n-queens-ii", {
  input: "n = 4",
  expect: 2,
  code: `place(row):
  if row == n: count++; return
  for col in 0..n-1:
    if col and both diagonals are free:
      mark; place(row + 1); unmark
return count`,
  run({ n }, T) {
    const cols = new Set(), d1 = new Set(), d2 = new Set(), q = [];
    let count = 0;
    const board = () => { const b = matrix(n, n, null); q.forEach((c, r) => (b[r][c] = "♛")); return b; };
    const place = (r) => {
      if (r === n) { count++; T.step({ line: 2, key: true, grids: [G("bàn cờ", board(), { hl: Object.fromEntries(q.map((c, i) => [i + "," + c, "ok"])) })], vars: { count }, msg: `Tìm được cách xếp thứ ${count}.` }); return; }
      for (let c = 0; c < n; c++) {
        const ok = !cols.has(c) && !d1.has(r - c) && !d2.has(r + c);
        const b = board();
        T.step({ line: 4, grids: [G("bàn cờ", b, { hl: { ...Object.fromEntries(q.map((x, i) => [i + "," + x, "done"])), [r + "," + c]: ok ? "cur" : "bad" } })], vars: { count }, msg: ok ? `(${r}, ${c}) an toàn, đặt hậu.` : `(${r}, ${c}) bị khống chế.` });
        if (!ok) continue;
        cols.add(c); d1.add(r - c); d2.add(r + c); q.push(c);
        place(r + 1);
        cols.delete(c); d1.delete(r - c); d2.delete(r + c); q.pop();
      }
    };
    place(0);
    return count;
  },
});

register("restore-ip-addresses", {
  input: 's = "25525511135"',
  expect: ["255.255.11.135", "255.255.111.35"],
  code: `dfs(start, parts):
  if len(parts) == 4:
    if start == n: result.add(join(parts))
    return
  for len in 1..3:
    seg = s[start..start+len-1]
    if seg is valid (0..255, no leading 0): dfs(start + len, parts + seg)`,
  run({ s }, T) {
    const out = [], rec = new RecTree();
    const valid = (x) => x.length && (x === "0" || (x[0] !== "0" && Number(x) <= 255));
    const dfs = (st, parts, label) => {
      rec.enter(label);
      const full = parts.length === 4, hit = full && st === s.length;
      if (hit) out.push(parts.join("."));
      T.step({ line: hit ? 3 : full ? 4 : 7, key: hit, nary: [rec.view()], arrays: [A("s", chars(s), { small: true, hl: tones(range(0, st - 1), "done") })], vars: { "kết quả": out }, msg: hit ? `IP hợp lệ: ${parts.join(".")}.` : full ? "Đủ 4 phần nhưng còn dư chữ số." : `Đã cắt [${parts}].` });
      if (!full) for (let len = 1; len <= 3 && st + len <= s.length; len++) {
        const seg = s.slice(st, st + len);
        if (s.length - st - len > 3 * (3 - parts.length)) continue;
        if (valid(seg)) dfs(st + len, [...parts, seg], seg);
      }
      rec.leave(hit ? "ok" : "done");
    };
    dfs(0, [], "∅");
    return out;
  },
});

register("palindrome-partitioning", {
  input: 's = "aab"',
  expect: [["a", "a", "b"], ["aa", "b"]],
  code: `dfs(start):
  if start == n: result.add(path); return
  for end in start..n-1:
    if s[start..end] is a palindrome:
      path.add(s[start..end]); dfs(end + 1); path.removeLast()`,
  run({ s }, T) {
    const out = [], path = [], rec = new RecTree();
    const pal = (x) => x === [...x].reverse().join("");
    const dfs = (st, label) => {
      rec.enter(label);
      const hit = st === s.length;
      if (hit) out.push([...path]);
      T.step({ line: hit ? 2 : 3, key: hit, nary: [rec.view()], vars: { path: [...path], "kết quả": out }, msg: hit ? `Cách cắt: ${path.join(" | ")}.` : `Cắt tiếp từ vị trí ${st}.` });
      if (!hit) for (let e = st; e < s.length; e++) {
        const seg = s.slice(st, e + 1);
        if (!pal(seg)) continue;
        path.push(seg);
        dfs(e + 1, seg);
        path.pop();
      }
      rec.leave(hit ? "ok" : "done");
    };
    dfs(0, "∅");
    return out;
  },
});

register("word-break-ii", {
  input: 's = "catsanddog"\nwordDict = ["cat", "cats", "and", "sand", "dog"]',
  expect: ["cat sand dog", "cats and dog"],
  code: `dfs(start):
  if start == n: result.add(join(path)); return
  for word in dict:
    if s starts with word at start:
      path.add(word); dfs(start + len(word)); path.removeLast()`,
  run({ s, wordDict }, T) {
    const out = [], path = [], rec = new RecTree();
    const dfs = (st, label) => {
      rec.enter(label);
      const hit = st === s.length;
      if (hit) out.push(path.join(" "));
      T.step({ line: hit ? 2 : 4, key: hit, nary: [rec.view()], arrays: [A("s", chars(s), { small: true, hl: tones(range(0, st - 1), "done") })], vars: { "kết quả": out }, msg: hit ? `Câu: "${path.join(" ")}".` : `Tìm từ bắt đầu tại ${st}.` });
      if (!hit) for (const w of wordDict) if (s.startsWith(w, st)) { path.push(w); dfs(st + w.length, w); path.pop(); }
      rec.leave(hit ? "ok" : "done");
    };
    dfs(0, "∅");
    return out.sort();
  },
});

register("expression-add-operators", {
  input: 'num = "123"\ntarget = 6',
  expect: ["1*2*3", "1+2+3"],
  code: `dfs(i, expr, value, last):
  if i == n: if value == target: result.add(expr); return
  for j in i..n-1:             // không có số 0 đứng đầu
    x = num[i..j]
    dfs(j + 1, expr + "+" + x, value + x, x)
    dfs(j + 1, expr + "-" + x, value - x, -x)
    dfs(j + 1, expr + "*" + x, value - last + last * x, last * x)`,
  run({ num, target }, T) {
    const out = [], rec = new RecTree();
    const dfs = (i, expr, val, last) => {
      rec.enter(expr || "∅");
      const end = i === num.length, hit = end && val === target;
      if (hit) { out.push(expr); rec.mark("ok"); }
      T.step({ line: end ? 2 : 4, key: hit, nary: [rec.view()], vars: { value: val, "kết quả": out }, msg: hit ? `${expr} = ${target} ✓` : end ? `${expr} = ${val}.` : `Đang có ${expr || "biểu thức rỗng"}.` });
      if (!end) for (let j = i; j < num.length; j++) {
        if (j > i && num[i] === "0") break;
        const s = num.slice(i, j + 1), x = Number(s);
        if (!i) dfs(j + 1, s, x, x);
        else {
          dfs(j + 1, `${expr}+${s}`, val + x, x);
          dfs(j + 1, `${expr}-${s}`, val - x, -x);
          dfs(j + 1, `${expr}*${s}`, val - last + last * x, last * x);
        }
      }
      rec.leave(hit ? "ok" : end ? "bad" : "done");
    };
    dfs(0, "", 0, 0);
    return out.sort();
  },
});

register("remove-invalid-parentheses", {
  input: 's = "()())()"',
  expect: ["(())()", "()()()"],
  code: `level = {s}
while level not empty:
  valid = strings in level that balance
  if valid not empty: return valid
  level = all strings with one parenthesis removed`,
  run({ s }, T) {
    const ok = (x) => { let c = 0; for (const ch of x) { if (ch === "(") c++; else if (ch === ")" && --c < 0) return false; } return c === 0; };
    let level = new Set([s]), removed = 0;
    for (;;) {
      const list = [...level], valid = list.filter(ok);
      T.step({ line: valid.length ? 4 : 3, key: !!valid.length, arrays: [A(`xoá ${removed} ngoặc`, list, { small: true, noIndex: true, hl: tones(list.map((_, i) => i).filter((i) => ok(list[i])), "ok") })], msg: valid.length ? `Có ${valid.length} chuỗi cân bằng, BFS đảm bảo xoá ít nhất.` : `Chưa chuỗi nào cân bằng, xoá thêm một ngoặc.` });
      if (valid.length) return valid.sort();
      const next = new Set();
      for (const x of list) for (let i = 0; i < x.length; i++) if ("()".includes(x[i])) next.add(x.slice(0, i) + x.slice(i + 1));
      level = next;
      removed++;
    }
  },
});

register("concatenated-words", {
  input: 'words = ["cat", "cats", "catsdogcats", "dog", "dogcatsdog", "hippopotamuses", "rat", "ratcatdogcat"]',
  expect: ["catsdogcats", "dogcatsdog", "ratcatdogcat"],
  code: `sort words by length
for w in words:
  dp[0] = true
  dp[i] = any dp[j] and w[j..i-1] in dict
  if dp[len(w)]: result.add(w)
  dict.add(w)
return result`,
  run({ words }, T) {
    const sorted = [...words].sort((a, b) => a.length - b.length), dict = new Set(), out = [];
    for (const w of sorted) {
      const dp = Array(w.length + 1).fill(false);
      dp[0] = true;
      for (let i = 1; i <= w.length; i++) for (let j = 0; j < i; j++) if (dp[j] && dict.has(w.slice(j, i))) { dp[i] = true; break; }
      const ok = dict.size > 0 && dp[w.length];
      if (ok) out.push(w);
      T.step({ line: 5, key: ok, arrays: [A(w, dp.map((x) => (x ? "T" : "·")), { small: true, indexLabels: ["", ...chars(w)], hl: tones(dp.map((_, i) => i).filter((i) => dp[i]), ok ? "ok" : "mark") })], vars: { "kết quả": out }, msg: ok ? `"${w}" ghép được từ các từ ngắn hơn.` : `"${w}" không ghép được.` });
      dict.add(w);
    }
    return out.sort();
  },
});

register("24-game", {
  input: "cards = [4, 1, 8, 7]",
  expect: true,
  code: `solve(nums):
  if len(nums) == 1: return |nums[0] - 24| < eps
  for each pair (a, b):
    for r in a + b, a - b, a * b, a / b:
      if solve(rest + [r]): return true
  return false`,
  run({ cards }, T) {
    const eps = 1e-6;
    let tried = 0;
    const solve = (nums, exprs) => {
      if (nums.length === 1) {
        tried++;
        const hit = Math.abs(nums[0] - 24) < eps;
        if (hit || tried % 40 === 1) T.step({ line: 2, key: hit, arrays: [A("số còn lại", exprs, { small: true, noIndex: true, hl: { 0: hit ? "ok" : "bad" } })], vars: { "đã thử": tried, "giá trị": Number(nums[0].toFixed(3)) }, msg: hit ? `${exprs[0]} = 24.` : `Thử ${exprs[0]}: không ra 24.` });
        return hit;
      }
      for (let i = 0; i < nums.length; i++) for (let j = 0; j < nums.length; j++) {
        if (i === j) continue;
        const restN = nums.filter((_, k) => k !== i && k !== j), restE = exprs.filter((_, k) => k !== i && k !== j);
        for (const [r, e] of [[nums[i] + nums[j], "+"], [nums[i] - nums[j], "−"], [nums[i] * nums[j], "×"], [nums[j] ? nums[i] / nums[j] : null, "÷"]]) {
          if (r === null) continue;
          if (solve([...restN, r], [...restE, `(${exprs[i]}${e}${exprs[j]})`])) return true;
        }
      }
      return false;
    };
    return solve(cards, cards.map(String));
  },
});

register("stickers-to-spell-word", {
  input: 'stickers = ["with", "example", "science"]\ntarget = "thehat"',
  expect: 3,
  code: `memo[""] = 0
solve(rest):
  if rest in memo: return memo[rest]
  for sticker containing rest[0]:
    best = min(best, 1 + solve(rest minus sticker letters))
  return memo[rest] = best`,
  run({ stickers, target }, T) {
    const memo = new Map([["", 0]]), rec = new RecTree();
    const count = (s) => { const c = {}; for (const x of s) c[x] = (c[x] ?? 0) + 1; return c; };
    const sc = stickers.map(count);
    const solve = (rest) => {
      if (memo.has(rest)) return memo.get(rest);
      rec.enter(rest);
      T.step({ line: 4, nary: [rec.view()], msg: `Cần ghép "${rest}".` });
      let best = Infinity;
      for (const c of sc) {
        if (!c[rest[0]]) continue;
        const left = { ...c };
        let next = "";
        for (const ch of rest) { if (left[ch]) left[ch]--; else next += ch; }
        best = Math.min(best, 1 + solve(next));
      }
      memo.set(rest, best);
      rec.leave("done");
      return best;
    };
    const sorted = [...target].sort().join("");
    const r = solve(sorted);
    T.step({ line: 6, key: true, nary: [rec.view()], vars: { "số nhãn": r }, msg: `Cần tối thiểu ${r} nhãn.` });
    return r === Infinity ? -1 : r;
  },
});

register("unique-paths-iii", {
  input: "grid = [[1, 0, 0, 0], [0, 0, 0, 0], [0, 0, 2, -1]]",
  expect: 2,
  code: `empty = number of walkable cells
dfs(r, c, left):
  if cell is wall or visited: return
  if cell is end: count += left == 0; return
  mark; dfs 4 directions with left - 1; unmark
return count`,
  run({ grid }, T) {
    const m = grid.length, n = grid[0].length;
    let sr = 0, sc = 0, empty = 0, count = 0;
    grid.forEach((row, r) => row.forEach((v, c) => { if (v !== -1) empty++; if (v === 1) { sr = r; sc = c; } }));
    const walls = Object.fromEntries(grid.flatMap((row, r) => row.flatMap((v, c) => (v === -1 ? [[r + "," + c, "dim"]] : []))));
    const seen = {};
    const order = matrix(m, n, null);
    const dfs = (r, c, left, depth) => {
      if (r < 0 || c < 0 || r >= m || c >= n || grid[r][c] === -1 || seen[r + "," + c]) return;
      order[r][c] = depth;
      if (grid[r][c] === 2) {
        const hit = left === 1;
        if (hit) { count++; T.step({ line: 4, key: true, grids: [G("đường đi", clone(order), { hl: { ...walls, ...seen, [r + "," + c]: "ok" } })], vars: { count }, msg: `Đi hết mọi ô rồi tới đích: đường thứ ${count}.` }); }
        order[r][c] = null;
        return;
      }
      seen[r + "," + c] = "done";
      T.step({ line: 5, grids: [G("đường đi", clone(order), { hl: { ...walls, ...seen, [r + "," + c]: "cur" } })], vars: { "còn": left, count }, msg: `Đi vào (${r}, ${c}).` });
      for (const [x, y] of DIRS) dfs(r + x, c + y, left - 1, depth + 1);
      delete seen[r + "," + c];
      order[r][c] = null;
    };
    dfs(sr, sc, empty, 0);
    return count;
  },
});

