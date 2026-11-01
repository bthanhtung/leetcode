/* Strings: scanning, matching, parsing, counting characters. */
import { A, G, M, chars, clone, matrix, ptrs, register, tones } from "./engine.js";

const range = (a, b) => Array.from({ length: Math.max(0, b - a + 1) }, (_, i) => a + i);

register("longest-palindromic-substring", {
  input: 's = "babad"',
  expect: "bab",
  code: `best = ""
for c in 0..n-1:
  for (l, r) in [(c, c), (c, c + 1)]:   // tâm lẻ, tâm chẵn
    while l >= 0 and r < n and s[l] == s[r]:
      l--, r++
    best = longer(best, s[l+1..r-1])
return best`,
  run({ s }, T) {
    const a = chars(s);
    let bl = 0, br = -1;
    for (let c = 0; c < a.length; c++) {
      for (const [l0, r0] of [[c, c], [c, c + 1]]) {
        let l = l0, r = r0;
        while (l >= 0 && r < a.length && a[l] === a[r]) {
          T.step({ line: 4, arrays: [A("s", a, { range: [l, r], hl: { [l]: "ok", [r]: "ok" }, ptr: ptrs({ l, r }) })], vars: { best: s.slice(bl, br + 1) }, msg: `'${a[l]}' = '${a[r]}' → nở rộng từ tâm ${l0 === r0 ? c : `${c}|${c + 1}`}.` });
          l--; r++;
        }
        if (r - l - 1 > br - bl + 1) {
          bl = l + 1; br = r - 1;
          T.step({ line: 6, key: true, arrays: [A("s", a, { hl: tones(range(bl, br), "ok") })], vars: { best: s.slice(bl, br + 1) }, msg: `Đoạn đối xứng dài nhất mới: "${s.slice(bl, br + 1)}".` });
        }
      }
    }
    return s.slice(bl, br + 1);
  },
});

register("zigzag-conversion", {
  input: 's = "PAYPALISHIRING"\nnumRows = 3',
  expect: "PAHNAPLSIIGYIR",
  code: `rows = [""] * numRows
r = 0, dir = 1
for c in s:
  rows[r] += c
  if r == 0: dir = 1
  if r == numRows - 1: dir = -1
  r += dir
return join(rows)`,
  run({ s, numRows }, T) {
    if (numRows === 1) { T.step({ line: 8, arrays: [A("s", chars(s))], msg: "Một hàng thì giữ nguyên." }); return s; }
    const a = chars(s), rows = Array.from({ length: numRows }, () => []);
    let r = 0, dir = 1;
    const width = a.length;
    const board = matrix(numRows, Math.ceil(width / (2 * numRows - 2)) * (numRows - 1) + 1, null);
    let col = 0;
    a.forEach((c, i) => {
      rows[r].push(c);
      board[r][col] = c;
      T.step({ line: 4, arrays: [A("s", a, { hl: { [i]: "cur" }, ptr: ptrs({ i }) })], grids: [G("ziczac", clone(board), { hl: { [r + "," + col]: "cur" } })], vars: { hàng: r, hướng: dir > 0 ? "xuống" : "lên" }, msg: `'${c}' vào hàng ${r}.` });
      if (r === 0) dir = 1;
      if (r === numRows - 1) dir = -1;
      r += dir;
      if (dir < 0) col++;
    });
    const out = rows.map((x) => x.join("")).join("");
    T.step({ line: 8, key: true, grids: [G("ziczac", clone(board))], texts: [{ label: "đọc theo hàng", value: out }], msg: `Nối các hàng: "${out}".` });
    return out;
  },
});

register("string-to-integer-atoi", {
  input: 's = "   -042abc"',
  expect: -42,
  code: `i = 0
while s[i] == ' ': i++
sign = s[i] == '-' ? -1 : 1
if s[i] in "+-": i++
x = 0
while s[i] is digit:
  if x would overflow: return clamp
  x = x * 10 + digit(s[i++])
return sign * x`,
  run({ s }, T) {
    const a = chars(s), MAX = 2147483647, MIN = -2147483648;
    let i = 0;
    const view = (line, msg, tone = "cur", extra = {}) => T.step({ line, arrays: [A("s", a, { hl: { ...tones(range(0, i - 1), "dim"), [i]: tone }, ptr: ptrs({ i }, a.length) })], vars: extra, msg });
    while (a[i] === " ") { view(2, "Bỏ khoảng trắng đầu chuỗi."); i++; }
    const sign = a[i] === "-" ? -1 : 1;
    view(3, `Dấu: ${sign < 0 ? "âm" : "dương"}.`, "mark", { sign });
    if (a[i] === "+" || a[i] === "-") i++;
    let x = 0;
    while (i < a.length && a[i] >= "0" && a[i] <= "9") {
      const d = Number(a[i]);
      if (x > Math.floor((MAX - d) / 10)) { view(7, "Tràn 32-bit → chặn về biên.", "bad", { x }); return sign > 0 ? MAX : MIN; }
      x = x * 10 + d;
      view(8, `Gặp chữ số ${d}: x = ${x}.`, "ok", { sign, x });
      i++;
    }
    T.step({ line: 9, key: true, arrays: [A("s", a, { ptr: ptrs({ i }, a.length) })], vars: { kết_quả: sign * x }, msg: `Dừng ở ký tự không phải số. Kết quả ${sign * x}.` });
    return sign * x;
  },
});

const ROMAN = [[1000, "M"], [900, "CM"], [500, "D"], [400, "CD"], [100, "C"], [90, "XC"], [50, "L"], [40, "XL"], [10, "X"], [9, "IX"], [5, "V"], [4, "IV"], [1, "I"]];

register("integer-to-roman", {
  input: "num = 1994",
  expect: "MCMXCIV",
  code: `values = [1000, 900, 500, 400, ... , 4, 1]
for (v, sym) in values:
  while num >= v:
    out += sym
    num -= v
return out`,
  run({ num }, T) {
    let out = "";
    const labels = ROMAN.map((x) => x[1]), vals = ROMAN.map((x) => x[0]);
    ROMAN.forEach(([v, sym], i) => {
      while (num >= v) {
        out += sym;
        num -= v;
        T.step({ line: 4, key: true, arrays: [A("bảng ký hiệu", vals, { hl: { [i]: "ok" }, indexLabels: labels })], vars: { "còn lại": num, out }, msg: `Trừ ${v}, viết ${sym}.` });
      }
    });
    return out;
  },
});

register("roman-to-integer", {
  input: 's = "MCMXCIV"',
  expect: 1994,
  code: `total = 0
for i in 0..n-1:
  if i + 1 < n and val(s[i]) < val(s[i+1]):
    total -= val(s[i])
  else:
    total += val(s[i])
return total`,
  run({ s }, T) {
    const V = { I: 1, V: 5, X: 10, L: 50, C: 100, D: 500, M: 1000 }, a = chars(s);
    let total = 0;
    a.forEach((c, i) => {
      const minus = i + 1 < a.length && V[c] < V[a[i + 1]];
      total += minus ? -V[c] : V[c];
      T.step({ line: minus ? 4 : 6, arrays: [A("s", a, { hl: { [i]: minus ? "bad" : "ok", ...(minus ? { [i + 1]: "mark" } : {}) }, ptr: ptrs({ i }) })], vars: { total }, msg: minus ? `${c} (${V[c]}) đứng trước ${a[i + 1]} lớn hơn → trừ.` : `${c} (${V[c]}) → cộng.` });
    });
    return total;
  },
});

register("longest-common-prefix", {
  input: 'strs = ["flower", "flow", "flight"]',
  expect: "fl",
  code: `for i in 0..len(strs[0])-1:
  c = strs[0][i]
  for s in strs[1..]:
    if i == len(s) or s[i] != c:
      return strs[0][0..i-1]
return strs[0]`,
  run({ strs }, T) {
    const cols = Math.max(...strs.map((x) => x.length));
    const cells = strs.map((x) => [...chars(x), ...Array(cols - x.length).fill(null)]);
    const first = strs[0];
    for (let i = 0; i < first.length; i++) {
      for (let r = 1; r < strs.length; r++) {
        const same = i < strs[r].length && strs[r][i] === first[i];
        const hl = {};
        strs.forEach((_, x) => { for (let c = 0; c < i; c++) hl[x + "," + c] = "done"; });
        hl["0," + i] = "mark";
        hl[r + "," + i] = same ? "ok" : "bad";
        T.step({ line: 4, key: !same, grids: [G("các chuỗi", cells, { hl })], vars: { "tiền tố": first.slice(0, i) }, msg: same ? `Cột ${i}: chuỗi ${r} cũng là '${first[i]}'.` : `Cột ${i}: chuỗi ${r} khác → dừng.` });
        if (!same) return first.slice(0, i);
      }
    }
    T.step({ line: 6, grids: [G("các chuỗi", cells)], msg: "Cả chuỗi đầu là tiền tố chung." });
    return first;
  },
});

register("find-the-index-of-the-first-occurrence-in-a-string", {
  input: 'haystack = "aabaabaaf"\nneedle = "aabaaf"',
  expect: 3,
  code: `lps = prefix function of needle      // KMP
j = 0
for i in 0..n-1:
  while j > 0 and hay[i] != needle[j]: j = lps[j-1]
  if hay[i] == needle[j]: j++
  if j == m: return i - m + 1
return -1`,
  run({ haystack, needle }, T) {
    const h = chars(haystack), p = chars(needle), lps = Array(p.length).fill(0);
    for (let i = 1, k = 0; i < p.length; i++) {
      while (k && p[i] !== p[k]) k = lps[k - 1];
      if (p[i] === p[k]) k++;
      lps[i] = k;
      T.step({ line: 1, arrays: [A("needle", p, { hl: { [i]: "cur", ...(k ? { [k - 1]: "mark" } : {}) } }), A("lps", lps, { hl: { [i]: "ok" } })], msg: `Tiền tố dài nhất cũng là hậu tố tới vị trí ${i}: ${k}.` });
    }
    let j = 0;
    const view = (i, line, msg, tone, key) => T.step({ line, key, arrays: [A("haystack", h, { range: [i - j + (tone === "ok" ? 0 : 0), i], hl: { [i]: tone }, ptr: ptrs({ i }) }), A("needle", [...Array(Math.max(0, i - j)).fill(null), ...p], { hl: { [i]: tone }, ptr: ptrs({ j: i }), noIndex: true }), A("lps", lps, { small: true, hl: { [j - 1]: "mark" } })], vars: { i, j }, msg });
    for (let i = 0; i < h.length; i++) {
      while (j > 0 && h[i] !== p[j]) {
        view(i, 4, `'${h[i]}' ≠ '${p[j]}' → không lùi i, nhảy j về lps[${j - 1}] = ${lps[j - 1]}.`, "bad");
        j = lps[j - 1];
      }
      if (h[i] === p[j]) { view(i, 5, `'${h[i]}' khớp.`, "ok"); j++; }
      else view(i, 5, `'${h[i]}' không khớp.`, "bad");
      if (j === p.length) {
        const at = i - j + 1;
        T.step({ line: 6, key: true, arrays: [A("haystack", h, { hl: tones(range(at, i), "ok") })], msg: `Tìm thấy tại vị trí ${at}.` });
        return at;
      }
    }
    T.step({ line: 7, arrays: [A("haystack", h)], msg: "Không tìm thấy." });
    return -1;
  },
});

register("count-and-say", {
  input: "n = 5",
  expect: "111221",
  code: `s = "1"
repeat n - 1 times:
  next = ""
  for each run of equal chars in s:
    next += len(run) + char
  s = next
return s`,
  run({ n }, T) {
    let s = "1";
    T.step({ line: 1, arrays: [A("s", chars(s))], msg: 'Bắt đầu từ "1".' });
    for (let t = 2; t <= n; t++) {
      let next = "", i = 0;
      const a = chars(s);
      while (i < a.length) {
        let j = i;
        while (j + 1 < a.length && a[j + 1] === a[i]) j++;
        next += `${j - i + 1}${a[i]}`;
        T.step({ line: 5, arrays: [A(`s (lượt ${t - 1})`, a, { range: [i, j], hl: tones(range(i, j), "cur") }), A("next", chars(next))], msg: `${j - i + 1} chữ '${a[i]}' → viết "${j - i + 1}${a[i]}".` });
        i = j + 1;
      }
      s = next;
      T.step({ line: 6, key: true, arrays: [A(`s (lượt ${t})`, chars(s))], msg: `Lượt ${t}: "${s}".` });
    }
    return s;
  },
});

register("multiply-strings", {
  input: 'num1 = "123"\nnum2 = "45"',
  expect: "5535",
  code: `out = [0] * (m + n)
for i from m-1 down to 0:
  for j from n-1 down to 0:
    p = a[i] * b[j] + out[i+j+1]
    out[i+j+1] = p % 10
    out[i+j] += p / 10
return strip leading zeros(out)`,
  run({ num1, num2 }, T) {
    const a = chars(num1).map(Number), b = chars(num2).map(Number), out = Array(a.length + b.length).fill(0);
    for (let i = a.length - 1; i >= 0; i--) {
      for (let j = b.length - 1; j >= 0; j--) {
        const p = a[i] * b[j] + out[i + j + 1];
        out[i + j + 1] = p % 10;
        out[i + j] += Math.floor(p / 10);
        T.step({ line: 5, arrays: [A("num1", a, { hl: { [i]: "cur" }, ptr: ptrs({ i }) }), A("num2", b, { hl: { [j]: "cur" }, ptr: ptrs({ j }) }), A("out", out, { hl: { [i + j + 1]: "ok", [i + j]: "mark" } })], vars: { p }, msg: `${a[i]} × ${b[j]} + nhớ cũ = ${p}: ghi ${p % 10} ở ô ${i + j + 1}, nhớ ${Math.floor(p / 10)} sang ô ${i + j}.` });
      }
    }
    const res = out.join("").replace(/^0+(?=.)/, "");
    T.step({ line: 7, key: true, arrays: [A("out", out)], vars: { "kết quả": res }, msg: `Bỏ số 0 đầu: ${res}.` });
    return res;
  },
});

register("length-of-last-word", {
  input: 's = "   fly me   to   the moon  "',
  expect: 4,
  code: `i = n - 1
while s[i] == ' ': i--
len = 0
while i >= 0 and s[i] != ' ':
  len++, i--
return len`,
  run({ s }, T) {
    const a = chars(s);
    let i = a.length - 1, len = 0;
    while (a[i] === " ") { T.step({ line: 2, arrays: [A("s", a, { hl: { [i]: "dim" }, ptr: ptrs({ i }), small: true })], msg: "Bỏ khoảng trắng cuối." }); i--; }
    while (i >= 0 && a[i] !== " ") {
      len++;
      T.step({ line: 5, arrays: [A("s", a, { hl: tones(range(i, i + len - 1), "ok"), ptr: ptrs({ i }), small: true })], vars: { len }, msg: `'${a[i]}' thuộc từ cuối, len = ${len}.` });
      i--;
    }
    T.step({ line: 6, key: true, arrays: [A("s", a, { hl: tones(range(i + 1, i + len), "ok"), small: true })], vars: { len }, msg: `Từ cuối dài ${len}.` });
    return len;
  },
});

register("valid-number", {
  input: 's = "-1.5e+3"',
  expect: true,
  code: `seenDigit = seenDot = seenExp = false
for i, c in s:
  if c is digit: seenDigit = true
  else if c in "+-":
    if i > 0 and s[i-1] not in "eE": return false
  else if c in "eE":
    if seenExp or !seenDigit: return false
    seenExp = true; seenDigit = false
  else if c == '.':
    if seenDot or seenExp: return false
    seenDot = true
  else: return false
return seenDigit`,
  run({ s }, T) {
    const a = chars(s);
    let d = false, dot = false, exp = false;
    const view = (i, line, ok, msg) => T.step({ line, key: !ok, arrays: [A("s", a, { hl: { ...tones(range(0, i - 1), "done"), [i]: ok ? "cur" : "bad" }, ptr: ptrs({ i }) })], vars: { "có chữ số": d, "có dấu chấm": dot, "có mũ": exp }, msg });
    for (let i = 0; i < a.length; i++) {
      const c = a[i];
      if (c >= "0" && c <= "9") { d = true; view(i, 3, true, `'${c}' là chữ số.`); }
      else if (c === "+" || c === "-") {
        const ok = i === 0 || a[i - 1] === "e" || a[i - 1] === "E";
        view(i, 5, ok, ok ? "Dấu đứng đầu hoặc ngay sau e: hợp lệ." : "Dấu ở giữa số: sai.");
        if (!ok) return false;
      } else if (c === "e" || c === "E") {
        const ok = !exp && d;
        view(i, 7, ok, ok ? "Phần mũ: cần thêm chữ số sau e." : "e sai chỗ.");
        if (!ok) return false;
        exp = true; d = false;
      } else if (c === ".") {
        const ok = !dot && !exp;
        view(i, 10, ok, ok ? "Dấu chấm đầu tiên." : "Dấu chấm thừa hoặc nằm sau e.");
        if (!ok) return false;
        dot = true;
      } else { view(i, 12, false, `'${c}' không được phép.`); return false; }
    }
    T.step({ line: 13, key: true, arrays: [A("s", a)], vars: { "có chữ số": d }, msg: d ? "Chuỗi là số hợp lệ." : "Thiếu chữ số ở cuối." });
    return d;
  },
});

function addAsText(slug, input, expect, base, code) {
  register(slug, {
    input, expect, code,
    run(args, T) {
      const [x, y] = Object.values(args);
      const a = chars(x), b = chars(y);
      const n = Math.max(a.length, b.length) + 1;
      const pad = (v) => [...Array(n - v.length).fill(null), ...v];
      const pa = pad(a), pb = pad(b), out = Array(n).fill(null);
      let carry = 0;
      for (let k = n - 1; k >= 0; k--) {
        const u = pa[k] === null ? 0 : Number(pa[k]), v = pb[k] === null ? 0 : Number(pb[k]);
        if (k === 0 && !u && !v && !carry) break;
        const sum = u + v + carry;
        out[k] = sum % base;
        carry = Math.floor(sum / base);
        T.step({ line: 4, arrays: [A("a", pa, { hl: { [k]: "cur" }, noIndex: true }), A("b", pb, { hl: { [k]: "cur" }, noIndex: true }), A("tổng", [...out], { hl: { [k]: "ok" }, noIndex: true })], vars: { carry }, msg: `${u} + ${v} + nhớ = ${sum}: ghi ${sum % base}, nhớ ${carry}.` });
      }
      const res = out.filter((v) => v !== null).join("");
      T.step({ line: 6, key: true, arrays: [A("tổng", chars(res), { noIndex: true })], msg: `Kết quả ${res}.` });
      return res;
    },
  });
}

addAsText("add-binary", 'a = "1011"\nb = "111"', "10010", 2, `i = len(a) - 1, j = len(b) - 1, carry = 0
while i >= 0 or j >= 0 or carry:
  sum = carry + digit(a, i--) + digit(b, j--)
  out = (sum % 2) + out
  carry = sum / 2
return out`);

addAsText("add-strings", 'num1 = "456"\nnum2 = "77"', "533", 10, `i = len(a) - 1, j = len(b) - 1, carry = 0
while i >= 0 or j >= 0 or carry:
  sum = carry + digit(a, i--) + digit(b, j--)
  out = (sum % 10) + out
  carry = sum / 10
return out`);

register("text-justification", {
  input: 'words = ["This", "is", "an", "example", "of", "text", "justification."]\nmaxWidth = 16',
  expect: ["This    is    an", "example  of text", "justification.  "],
  code: `i = 0
while i < n:
  j = i, len = 0
  while j < n and len + |words[j]| + (j - i) <= maxWidth:
    len += |words[j++]|
  if j == n or j - i == 1: line = left-justified
  else:
    gaps = j - i - 1, spaces = maxWidth - len
    each gap gets spaces / gaps, first (spaces % gaps) get +1
  add line; i = j`,
  run({ words, maxWidth }, T) {
    const res = [];
    let i = 0;
    while (i < words.length) {
      let j = i, len = 0;
      while (j < words.length && len + words[j].length + (j - i) <= maxWidth) {
        len += words[j].length;
        j++;
        T.step({ line: 5, arrays: [A("words", words, { small: true, range: [i, j - 1], hl: { [j - 1]: "cur" } })], vars: { "chữ": len, "dấu cách tối thiểu": j - i - 1, maxWidth }, msg: `"${words[j - 1]}" vừa dòng này.` });
      }
      let line;
      if (j === words.length || j - i === 1) {
        line = words.slice(i, j).join(" ").padEnd(maxWidth, " ");
        T.step({ line: 6, arrays: [A("words", words, { small: true, range: [i, j - 1] })], vars: { dòng: `"${line}"` }, msg: j === words.length ? "Dòng cuối: căn trái." : "Dòng một từ: căn trái." });
      } else {
        const gaps = j - i - 1, spaces = maxWidth - len, each = Math.floor(spaces / gaps), extra = spaces % gaps;
        line = "";
        for (let k = i; k < j; k++) {
          line += words[k];
          if (k < j - 1) line += " ".repeat(each + (k - i < extra ? 1 : 0));
        }
        T.step({ line: 9, arrays: [A("words", words, { small: true, range: [i, j - 1] })], vars: { "số khe": gaps, "tổng dấu cách": spaces, "mỗi khe": each, "khe được thêm": extra, dòng: `"${line}"` }, msg: `Chia ${spaces} dấu cách cho ${gaps} khe, khe bên trái được nhiều hơn.` });
      }
      res.push(line);
      T.step({ line: 10, key: true, arrays: [A("words", words, { small: true })], grids: [G("kết quả", res.map((l) => chars(l).map((c) => (c === " " ? "·" : c))))], msg: "Xong một dòng." });
      i = j;
    }
    return res;
  },
});

register("reverse-words-in-a-string", {
  input: 's = "  the sky   is blue  "',
  expect: "blue is sky the",
  code: `words = []
i = n - 1
while i >= 0:
  while i >= 0 and s[i] == ' ': i--
  j = i
  while i >= 0 and s[i] != ' ': i--
  if j >= 0 and i < j: words.add(s[i+1..j])
return join(words, " ")`,
  run({ s }, T) {
    const a = chars(s), words = [];
    let i = a.length - 1;
    while (i >= 0) {
      while (i >= 0 && a[i] === " ") i--;
      const j = i;
      while (i >= 0 && a[i] !== " ") i--;
      if (j >= 0 && i < j) {
        words.push(s.slice(i + 1, j + 1));
        T.step({ line: 7, arrays: [A("s", a, { small: true, hl: tones(range(i + 1, j), "ok"), ptr: ptrs({ i, j }) }), A("words", [...words], { small: true, noIndex: true })], msg: `Lấy từ "${words[words.length - 1]}" từ cuối chuỗi lên.` });
      }
    }
    const out = words.join(" ");
    T.step({ line: 8, key: true, arrays: [A("words", words, { small: true, noIndex: true })], msg: `"${out}"` });
    return out;
  },
});

register("excel-sheet-column-title", {
  input: "columnNumber = 701",
  expect: "ZY",
  code: `out = ""
while n > 0:
  n--                // cột bắt đầu từ 1, không có số 0
  out = char('A' + n % 26) + out
  n /= 26
return out`,
  run({ columnNumber: n }, T) {
    let out = "";
    while (n > 0) {
      n--;
      const c = String.fromCharCode(65 + (n % 26));
      out = c + out;
      T.step({ line: 4, arrays: [A("out", chars(out), { hl: { 0: "ok" } })], vars: { "n − 1": n, "dư 26": n % 26 }, msg: `Dư ${n % 26} ứng với '${c}'.` });
      n = Math.floor(n / 26);
    }
    return out;
  },
});

register("excel-sheet-column-number", {
  input: 'columnTitle = "ZY"',
  expect: 701,
  code: `x = 0
for c in title:
  x = x * 26 + (c - 'A' + 1)
return x`,
  run({ columnTitle: s }, T) {
    let x = 0;
    chars(s).forEach((c, i) => {
      const v = c.charCodeAt(0) - 64, old = x;
      x = x * 26 + v;
      T.step({ line: 3, arrays: [A("title", chars(s), { hl: { [i]: "cur" }, ptr: ptrs({ i }) })], vars: { x }, msg: `${old} × 26 + ${v} (${c}) = ${x}.` });
    });
    return x;
  },
});

function bijection(slug, input, expect, code, split) {
  register(slug, {
    input, expect, code,
    run(args, T) {
      const [x, y] = Object.values(args);
      const a = chars(x), b = split(y);
      if (a.length !== b.length) { T.step({ line: 1, arrays: [A("trái", a), A("phải", b)], msg: "Độ dài khác nhau." }); return false; }
      const f = new Map(), g = new Map();
      for (let i = 0; i < a.length; i++) {
        const bad = (f.has(a[i]) && f.get(a[i]) !== b[i]) || (g.has(b[i]) && g.get(b[i]) !== a[i]);
        T.step({ line: 3, key: bad, arrays: [A("trái", a, { hl: { [i]: bad ? "bad" : "cur" }, ptr: ptrs({ i }) }), A("phải", b, { hl: { [i]: bad ? "bad" : "cur" }, ptr: ptrs({ i }) })], maps: [M("trái → phải", f, { hl: { [a[i]]: bad ? "bad" : "ok" } }), M("phải → trái", g, { hl: { [b[i]]: bad ? "bad" : "ok" } })], msg: bad ? `'${a[i]}' và '${b[i]}' mâu thuẫn với ánh xạ đã có.` : `Ghép '${a[i]}' ↔ '${b[i]}' hợp lệ.` });
        if (bad) return false;
        f.set(a[i], b[i]);
        g.set(b[i], a[i]);
      }
      T.step({ line: 5, key: true, arrays: [A("trái", a), A("phải", b)], maps: [M("trái → phải", f), M("phải → trái", g)], msg: "Ánh xạ một-một suốt chuỗi." });
      return true;
    },
  });
}

bijection("isomorphic-strings", 's = "paper"\nt = "title"', true, `f = {}, g = {}
for i in 0..n-1:
  if f[s[i]] != t[i] or g[t[i]] != s[i]: return false
  f[s[i]] = t[i]; g[t[i]] = s[i]
return true`, chars);

bijection("word-pattern", 'pattern = "abba"\ns = "dog cat cat dog"', true, `f = {}, g = {}
for i in 0..n-1:
  if f[pattern[i]] != words[i] or g[words[i]] != pattern[i]: return false
  f[pattern[i]] = words[i]; g[words[i]] = pattern[i]
return true`, (s) => s.split(" "));

register("shortest-palindrome", {
  input: 's = "abcd"',
  expect: "dcbabcd",
  code: `t = s + "#" + reverse(s)
lps = prefix function of t
k = lps[len(t) - 1]            // tiền tố đối xứng dài nhất
return reverse(s[k..]) + s`,
  run({ s }, T) {
    const t = chars(`${s}#${[...s].reverse().join("")}`), lps = Array(t.length).fill(0);
    T.step({ line: 1, arrays: [A("t", t)], msg: "Ghép s, ký tự ngăn cách và chuỗi đảo." });
    for (let i = 1, k = 0; i < t.length; i++) {
      while (k && t[i] !== t[k]) k = lps[k - 1];
      if (t[i] === t[k]) k++;
      lps[i] = k;
      T.step({ line: 2, arrays: [A("t", t, { hl: { [i]: "cur", ...(k ? { [k - 1]: "mark" } : {}) }, ptr: ptrs({ i }) }), A("lps", lps, { hl: { [i]: "ok" } })], msg: `lps[${i}] = ${k}.` });
    }
    const k = lps[t.length - 1], out = [...s.slice(k)].reverse().join("") + s;
    T.step({ line: 3, key: true, arrays: [A("s", chars(s), { hl: tones(range(0, k - 1), "ok") })], vars: { k }, msg: `Tiền tố đối xứng dài nhất có độ dài ${k}.` });
    T.step({ line: 4, key: true, arrays: [A("kết quả", chars(out), { hl: tones(range(0, s.length - k - 1), "mark") })], msg: `Thêm phần còn lại đảo ngược vào đầu: "${out}".` });
    return out;
  },
});

function counting(slug, input, expect, code, run) {
  register(slug, { input, expect, code, run });
}

counting("valid-anagram", 's = "anagram"\nt = "nagaram"', true, `if len(s) != len(t): return false
count = [0] * 26
for c in s: count[c]++
for c in t:
  if --count[c] < 0: return false
return true`, ({ s, t }, T) => {
  if (s.length !== t.length) { T.step({ line: 1, arrays: [A("s", chars(s)), A("t", chars(t))], msg: "Độ dài khác nhau." }); return false; }
  const count = new Map();
  chars(s).forEach((c, i) => {
    count.set(c, (count.get(c) ?? 0) + 1);
    T.step({ line: 3, arrays: [A("s", chars(s), { hl: { [i]: "cur" }, ptr: ptrs({ i }) })], maps: [M("count", count, { hl: { [c]: "ok" } })], msg: `Đếm '${c}'.` });
  });
  for (let i = 0; i < t.length; i++) {
    const c = t[i], left = (count.get(c) ?? 0) - 1;
    count.set(c, left);
    T.step({ line: 5, key: left < 0, arrays: [A("t", chars(t), { hl: { [i]: left < 0 ? "bad" : "cur" }, ptr: ptrs({ i }) })], maps: [M("count", count, { hl: { [c]: left < 0 ? "bad" : "mark" } })], msg: left < 0 ? `'${c}' dư so với s.` : `Trừ '${c}', còn ${left}.` });
    if (left < 0) return false;
  }
  T.step({ line: 6, key: true, arrays: [A("t", chars(t))], maps: [M("count", count)], msg: "Mọi ký tự khớp số lượng." });
  return true;
});

counting("ransom-note", 'ransomNote = "aab"\nmagazine = "baa"', true, `count = frequency(magazine)
for c in ransomNote:
  if count[c] == 0: return false
  count[c]--
return true`, ({ ransomNote: r, magazine: m }, T) => {
  const count = new Map();
  for (const c of m) count.set(c, (count.get(c) ?? 0) + 1);
  T.step({ line: 1, arrays: [A("ransomNote", chars(r))], maps: [M("chữ trong tạp chí", count)], msg: "Đếm chữ có sẵn trong tạp chí." });
  for (let i = 0; i < r.length; i++) {
    const c = r[i], have = count.get(c) ?? 0;
    if (have) count.set(c, have - 1);
    T.step({ line: have ? 4 : 3, key: !have, arrays: [A("ransomNote", chars(r), { hl: { [i]: have ? "ok" : "bad" }, ptr: ptrs({ i }) })], maps: [M("chữ trong tạp chí", count, { hl: { [c]: have ? "mark" : "bad" } })], msg: have ? `Dùng một chữ '${c}'.` : `Hết chữ '${c}'.` });
    if (!have) return false;
  }
  return true;
});

counting("first-unique-character-in-a-string", 's = "loveleetcode"', 2, `count = frequency(s)
for i in 0..n-1:
  if count[s[i]] == 1: return i
return -1`, ({ s }, T) => {
  const count = new Map();
  for (const c of s) count.set(c, (count.get(c) ?? 0) + 1);
  T.step({ line: 1, arrays: [A("s", chars(s))], maps: [M("count", count)], msg: "Đếm tần suất mọi ký tự." });
  for (let i = 0; i < s.length; i++) {
    const once = count.get(s[i]) === 1;
    T.step({ line: 3, key: once, arrays: [A("s", chars(s), { hl: { [i]: once ? "ok" : "bad" }, ptr: ptrs({ i }) })], maps: [M("count", count, { hl: { [s[i]]: once ? "ok" : "bad" } })], msg: once ? `'${s[i]}' chỉ xuất hiện một lần.` : `'${s[i]}' lặp ${count.get(s[i])} lần.` });
    if (once) return i;
  }
  return -1;
});

counting("find-the-difference", 's = "abcd"\nt = "abcde"', "e", `x = 0
for c in s + t:
  x ^= c
return char(x)`, ({ s, t }, T) => {
  let x = 0;
  const all = chars(s + t);
  all.forEach((c, i) => {
    x ^= c.charCodeAt(0);
    T.step({ line: 3, arrays: [A("s + t", all, { hl: { [i]: "cur" }, ptr: ptrs({ i }) })], vars: { x, "ký tự": x ? String.fromCharCode(x) : "∅" }, msg: `XOR với '${c}'. Ký tự xuất hiện hai lần tự triệt tiêu.` });
  });
  return String.fromCharCode(x);
});

register("is-subsequence", {
  input: 's = "abc"\nt = "ahbgdc"',
  expect: true,
  code: `i = 0
for c in t:
  if i < len(s) and s[i] == c: i++
return i == len(s)`,
  run({ s, t }, T) {
    let i = 0;
    for (let j = 0; j < t.length; j++) {
      const hit = i < s.length && s[i] === t[j];
      T.step({ line: 3, key: hit, arrays: [A("s", chars(s), { hl: { ...tones(range(0, i - 1), "done"), [i]: hit ? "ok" : "cur" }, ptr: ptrs({ i }, s.length) }), A("t", chars(t), { hl: { [j]: hit ? "ok" : "cur" }, ptr: ptrs({ j }) })], msg: hit ? `Khớp '${t[j]}'.` : `'${t[j]}' không phải ký tự đang cần.` });
      if (hit) i++;
    }
    T.step({ line: 4, arrays: [A("s", chars(s), { hl: tones(range(0, i - 1), "done") })], msg: i === s.length ? "Tìm đủ theo thứ tự." : "Thiếu ký tự." });
    return i === s.length;
  },
});

register("convert-a-number-to-hexadecimal", {
  input: "num = 26",
  expect: "1a",
  code: `if num == 0: return "0"
out = ""
while num != 0 and len(out) < 8:
  out = hex[num & 15] + out
  num >>>= 4
return out`,
  run({ num }, T) {
    if (num === 0) { T.step({ line: 1, arrays: [A("out", ["0"])], msg: "0 giữ nguyên." }); return "0"; }
    let out = "", n = num >>> 0;
    while (n !== 0 && out.length < 8) {
      const d = n & 15;
      out = "0123456789abcdef"[d] + out;
      T.step({ line: 4, arrays: [A("bit", chars(n.toString(2).padStart(32, "0")), { small: true, noIndex: true, hl: tones(range(28, 31), "cur") }), A("out", chars(out), { hl: { 0: "ok" } })], vars: { "4 bit cuối": d }, msg: `4 bit cuối bằng ${d} → '${out[0]}'.` });
      n >>>= 4;
    }
    return out;
  },
});

register("longest-palindrome", {
  input: 's = "abccccdd"',
  expect: 7,
  code: `count = frequency(s)
len = 0
for c in count.values():
  len += c / 2 * 2
if len < n: len++      // còn ký tự lẻ để đặt giữa
return len`,
  run({ s }, T) {
    const count = new Map();
    for (const c of s) count.set(c, (count.get(c) ?? 0) + 1);
    let len = 0;
    for (const [k, c] of count) {
      len += Math.floor(c / 2) * 2;
      T.step({ line: 4, arrays: [A("s", chars(s), { hl: tones(range(0, s.length - 1).filter((i) => s[i] === k), "cur") })], maps: [M("count", count, { hl: { [k]: "ok" } })], vars: { len }, msg: `'${k}' có ${c} chữ, dùng được ${Math.floor(c / 2) * 2}.` });
    }
    const mid = len < s.length;
    if (mid) len++;
    T.step({ line: 5, key: true, arrays: [A("s", chars(s))], maps: [M("count", count)], vars: { len }, msg: mid ? `Còn ký tự lẻ, đặt một chữ ở giữa → ${len}.` : `Không còn gì cho vị trí giữa → ${len}.` });
    return len;
  },
});

register("fizz-buzz", {
  input: "n = 15",
  expect: ["1", "2", "Fizz", "4", "Buzz", "Fizz", "7", "8", "Fizz", "Buzz", "11", "Fizz", "13", "14", "FizzBuzz"],
  code: `for i in 1..n:
  if i % 15 == 0: add "FizzBuzz"
  else if i % 3 == 0: add "Fizz"
  else if i % 5 == 0: add "Buzz"
  else: add str(i)`,
  run({ n }, T) {
    const out = [];
    for (let i = 1; i <= n; i++) {
      const v = i % 15 === 0 ? "FizzBuzz" : i % 3 === 0 ? "Fizz" : i % 5 === 0 ? "Buzz" : String(i);
      out.push(v);
      T.step({ line: i % 15 === 0 ? 2 : i % 3 === 0 ? 3 : i % 5 === 0 ? 4 : 5, key: i % 15 === 0, maps: [M("kết quả", out.map((x, k) => [k + 1, x]), { hl: { [i]: i % 3 === 0 || i % 5 === 0 ? "ok" : "cur" } })], msg: `${i} → ${v}.` });
    }
    return out;
  },
});

register("number-of-segments-in-a-string", {
  input: 's = "Hello, my name is John"',
  expect: 5,
  code: `count = 0
for i in 0..n-1:
  if s[i] != ' ' and (i == 0 or s[i-1] == ' '):
    count++
return count`,
  run({ s }, T) {
    let count = 0;
    const hl = {};
    for (let i = 0; i < s.length; i++) {
      if (s[i] !== " " && (i === 0 || s[i - 1] === " ")) {
        count++;
        hl[i] = "ok";
        T.step({ line: 4, arrays: [A("s", chars(s), { small: true, hl: { ...hl }, ptr: ptrs({ i }) })], vars: { count }, msg: `Vị trí ${i} bắt đầu một đoạn mới.` });
      }
    }
    if (!count) T.step({ line: 5, arrays: [A("s", chars(s), { small: true })], msg: "Không có đoạn nào." });
    return count;
  },
});

register("repeated-substring-pattern", {
  input: 's = "abcabcabc"',
  expect: true,
  code: `lps = prefix function of s
k = lps[n - 1]
return k > 0 and n % (n - k) == 0`,
  run({ s }, T) {
    const a = chars(s), lps = Array(a.length).fill(0);
    for (let i = 1, k = 0; i < a.length; i++) {
      while (k && a[i] !== a[k]) k = lps[k - 1];
      if (a[i] === a[k]) k++;
      lps[i] = k;
      T.step({ line: 1, arrays: [A("s", a, { hl: { [i]: "cur" }, ptr: ptrs({ i }) }), A("lps", lps, { hl: { [i]: "ok" } })], msg: `lps[${i}] = ${k}.` });
    }
    const n = a.length, k = lps[n - 1], ok = k > 0 && n % (n - k) === 0;
    T.step({ line: 3, key: true, arrays: [A("s", a, { hl: ok ? tones(range(0, n - k - 1), "ok") : {} }), A("lps", lps)], vars: { "chu kỳ": n - k }, msg: ok ? `Chu kỳ ${n - k} chia hết độ dài ${n} → lặp từ "${s.slice(0, n - k)}".` : `Độ dài ${n} không chia hết cho chu kỳ ${n - k}.` });
    return ok;
  },
});

register("keyboard-row", {
  input: 'words = ["Hello", "Alaska", "Dad", "Peace"]',
  expect: ["Alaska", "Dad"],
  code: `row = map letter -> keyboard row
for w in words:
  if all letters of w share one row:
    result.add(w)
return result`,
  run({ words }, T) {
    const rows = ["qwertyuiop", "asdfghjkl", "zxcvbnm"], res = [];
    words.forEach((w, i) => {
      const used = new Set([...w.toLowerCase()].map((c) => rows.findIndex((r) => r.includes(c))));
      const ok = used.size === 1;
      if (ok) res.push(w);
      T.step({ line: 3, key: ok, arrays: [A("words", words, { small: true, hl: { [i]: ok ? "ok" : "bad" } })], grids: [G("bàn phím", rows.map((r) => chars(r.padEnd(10, " ")).map((c) => (c === " " ? null : c))), { hl: Object.fromEntries([...w.toLowerCase()].map((c) => { const r = rows.findIndex((x) => x.includes(c)); return [r + "," + rows[r].indexOf(c), ok ? "ok" : "bad"]; })) })], vars: { "kết quả": res }, msg: ok ? `"${w}" gõ được trên một hàng.` : `"${w}" trải trên ${used.size} hàng.` });
    });
    return res;
  },
});

register("detect-capital", {
  input: 'word = "FlaG"',
  expect: false,
  code: `upper = count of uppercase letters
return upper == n
    or upper == 0
    or (upper == 1 and word[0] is uppercase)`,
  run({ word }, T) {
    const up = (c) => c >= "A" && c <= "Z", a = chars(word), upper = a.filter(up).length;
    T.step({ line: 1, arrays: [A("word", a, { hl: tones(range(0, a.length - 1).filter((i) => up(a[i])), "mark") })], vars: { upper, n: a.length }, msg: `Có ${upper} chữ hoa.` });
    const ok = upper === a.length || upper === 0 || (upper === 1 && up(a[0]));
    T.step({ line: 2, key: true, arrays: [A("word", a, { hl: tones(range(0, a.length - 1), ok ? "ok" : "bad") })], msg: ok ? "Đúng một trong ba kiểu viết hoa." : "Không khớp kiểu nào." });
    return ok;
  },
});

register("reverse-words-in-a-string-iii", {
  input: `s = "Let's take LeetCode"`,
  expect: "s'teL ekat edoCteeL",
  code: `start = 0
for i in 0..n:
  if i == n or s[i] == ' ':
    reverse(s, start, i - 1)
    start = i + 1
return s`,
  run({ s }, T) {
    const a = chars(s);
    let start = 0;
    for (let i = 0; i <= a.length; i++) {
      if (i === a.length || a[i] === " ") {
        T.step({ line: 3, arrays: [A("s", a, { small: true, range: [start, i - 1] })], msg: `Từ từ vị trí ${start} đến ${i - 1}.` });
        for (let l = start, r = i - 1; l < r; l++, r--) [a[l], a[r]] = [a[r], a[l]];
        T.step({ line: 4, key: true, arrays: [A("s", a, { small: true, hl: tones(range(start, i - 1), "ok") })], msg: "Đảo xong từ này." });
        start = i + 1;
      }
    }
    return a.join("");
  },
});

register("palindromic-substrings", {
  input: 's = "aaa"',
  expect: 6,
  code: `count = 0
for c in 0..n-1:
  for (l, r) in [(c, c), (c, c + 1)]:
    while l >= 0 and r < n and s[l] == s[r]:
      count++
      l--, r++
return count`,
  run({ s }, T) {
    const a = chars(s);
    let count = 0;
    for (let c = 0; c < a.length; c++) for (let [l, r] of [[c, c], [c, c + 1]]) {
      while (l >= 0 && r < a.length && a[l] === a[r]) {
        count++;
        T.step({ line: 5, key: true, arrays: [A("s", a, { hl: tones(range(l, r), "ok"), ptr: ptrs({ l, r }) })], vars: { count }, msg: `"${s.slice(l, r + 1)}" đối xứng, count = ${count}.` });
        l--; r++;
      }
    }
    return count;
  },
});

register("robot-return-to-origin", {
  input: 'moves = "UDLR"',
  expect: true,
  code: `x = 0, y = 0
for m in moves:
  update (x, y) by m
return x == 0 and y == 0`,
  run({ moves }, T) {
    let x = 0, y = 0;
    const trail = [[0, 0]];
    chars(moves).forEach((m, i) => {
      if (m === "U") y++; else if (m === "D") y--; else if (m === "L") x--; else x++;
      trail.push([x, y]);
      T.step({ line: 3, arrays: [A("moves", chars(moves), { hl: { [i]: "cur" }, ptr: ptrs({ i }) })], points: [{ label: "đường đi", pts: trail, path: trail.map((_, k) => k), hl: { 0: "mark", [trail.length - 1]: "cur" } }], vars: { x, y }, msg: `Đi ${m} tới (${x}, ${y}).` });
    });
    return x === 0 && y === 0;
  },
});

register("valid-palindrome-ii", {
  input: 's = "abca"',
  expect: true,
  code: `check(l, r): all s[l+k] == s[r-k]
l = 0, r = n - 1
while l < r:
  if s[l] != s[r]:
    return check(l + 1, r) or check(l, r - 1)
  l++, r--
return true`,
  run({ s }, T) {
    const a = chars(s);
    const check = (l, r, tag) => {
      while (l < r) {
        const same = a[l] === a[r];
        T.step({ line: 1, arrays: [A("s", a, { range: [l, r], hl: { [l]: same ? "ok" : "bad", [r]: same ? "ok" : "bad" }, ptr: ptrs({ l, r }) })], vars: { "đang thử": tag }, msg: same ? `'${a[l]}' = '${a[r]}'.` : `'${a[l]}' ≠ '${a[r]}', cách này hỏng.` });
        if (!same) return false;
        l++; r--;
      }
      return true;
    };
    let l = 0, r = a.length - 1;
    while (l < r) {
      if (a[l] !== a[r]) {
        T.step({ line: 4, key: true, arrays: [A("s", a, { hl: { [l]: "bad", [r]: "bad" }, ptr: ptrs({ l, r }) })], msg: `Lệch ở '${a[l]}' và '${a[r]}': thử xoá một trong hai.` });
        return check(l + 1, r, `xoá '${a[l]}'`) || check(l, r - 1, `xoá '${a[r]}'`);
      }
      T.step({ line: 6, arrays: [A("s", a, { range: [l, r], hl: { [l]: "ok", [r]: "ok" }, ptr: ptrs({ l, r }) })], msg: `'${a[l]}' = '${a[r]}'.` });
      l++; r--;
    }
    return true;
  },
});

register("jewels-and-stones", {
  input: 'jewels = "aA"\nstones = "aAAbbbb"',
  expect: 3,
  code: `set = HashSet(jewels)
count = 0
for c in stones:
  if c in set: count++
return count`,
  run({ jewels, stones }, T) {
    const set = new Set(jewels);
    let count = 0;
    chars(stones).forEach((c, i) => {
      const hit = set.has(c);
      if (hit) count++;
      T.step({ line: 4, arrays: [A("stones", chars(stones), { hl: { [i]: hit ? "ok" : "dim" }, ptr: ptrs({ i }) })], maps: [M("jewels", set, { hl: { [c]: hit ? "ok" : "base" } })], vars: { count }, msg: hit ? `'${c}' là đá quý.` : `'${c}' là đá thường.` });
    });
    return count;
  },
});

register("backspace-string-compare", {
  input: 's = "ab#c"\nt = "ad#c"',
  expect: true,
  code: `i = len(s) - 1, j = len(t) - 1
while i >= 0 or j >= 0:
  i = next valid char of s (skip by counting #)
  j = next valid char of t
  if s[i] != t[j]: return false
  i--, j--
return true`,
  run({ s, t }, T) {
    const a = chars(s), b = chars(t);
    const next = (x, i) => {
      let skip = 0;
      while (i >= 0) {
        if (x[i] === "#") { skip++; i--; } else if (skip) { skip--; i--; } else break;
      }
      return i;
    };
    let i = a.length - 1, j = b.length - 1;
    for (;;) {
      i = next(a, i); j = next(b, j);
      const same = (i < 0 && j < 0) || (i >= 0 && j >= 0 && a[i] === b[j]);
      if (i < 0 && j < 0) break;
      T.step({ line: 5, key: !same, arrays: [A("s", a, { hl: { [i]: same ? "ok" : "bad" }, ptr: ptrs({ i }) }), A("t", b, { hl: { [j]: same ? "ok" : "bad" }, ptr: ptrs({ j }) })], msg: same ? `Đi từ cuối, bỏ qua ký tự bị # xoá: '${a[i]}' khớp.` : "Hai ký tự còn lại khác nhau." });
      if (!same) return false;
      i--; j--;
    }
    T.step({ line: 7, key: true, arrays: [A("s", a), A("t", b)], msg: "Hai chuỗi giống nhau sau khi xoá." });
    return true;
  },
});

register("defanging-an-ip-address", {
  input: 'address = "1.1.1.1"',
  expect: "1[.]1[.]1[.]1",
  code: `out = ""
for c in address:
  out += c == '.' ? "[.]" : c
return out`,
  run({ address }, T) {
    let out = "";
    chars(address).forEach((c, i) => {
      out += c === "." ? "[.]" : c;
      T.step({ line: 3, arrays: [A("address", chars(address), { hl: { [i]: c === "." ? "mark" : "cur" }, ptr: ptrs({ i }) }), A("out", chars(out), { small: true })], msg: c === "." ? "Dấu chấm thành [.]" : `Giữ '${c}'.` });
    });
    return out;
  },
});

register("string-matching-in-an-array", {
  input: 'words = ["mass", "as", "hero", "superhero"]',
  expect: ["as", "hero"],
  code: `for each word w:
  for each other word u:
    if u contains w:
      result.add(w); break
return result`,
  run({ words }, T) {
    const res = [];
    words.forEach((w, i) => {
      for (let j = 0; j < words.length; j++) {
        if (i === j) continue;
        const hit = words[j].includes(w);
        T.step({ line: 3, key: hit, arrays: [A("words", words, { small: true, hl: { [i]: "cur", [j]: hit ? "ok" : "mark" }, ptr: ptrs({ w: i, u: j }) })], vars: { "kết quả": res }, msg: hit ? `"${words[j]}" chứa "${w}".` : `"${words[j]}" không chứa "${w}".` });
        if (hit) { res.push(w); break; }
      }
    });
    return res;
  },
});

register("merge-strings-alternately", {
  input: 'word1 = "abc"\nword2 = "pqr"',
  expect: "apbqcr",
  code: `out = ""
for i in 0..max(n, m)-1:
  if i < n: out += word1[i]
  if i < m: out += word2[i]
return out`,
  run({ word1: a, word2: b }, T) {
    let out = "";
    for (let i = 0; i < Math.max(a.length, b.length); i++) {
      if (i < a.length) { out += a[i]; T.step({ line: 3, arrays: [A("word1", chars(a), { hl: { [i]: "cur" }, ptr: ptrs({ i }) }), A("word2", chars(b)), A("out", chars(out))], msg: `Lấy '${a[i]}' từ word1.` }); }
      if (i < b.length) { out += b[i]; T.step({ line: 4, arrays: [A("word1", chars(a)), A("word2", chars(b), { hl: { [i]: "cur" }, ptr: ptrs({ i }) }), A("out", chars(out))], msg: `Lấy '${b[i]}' từ word2.` }); }
    }
    return out;
  },
});

register("group-anagrams", {
  input: 'strs = ["eat", "tea", "tan", "ate", "nat", "bat"]',
  expect: [["eat", "tea", "ate"], ["tan", "nat"], ["bat"]],
  code: `groups = {}
for s in strs:
  key = sorted(s)
  groups[key].add(s)
return groups.values()`,
  run({ strs }, T) {
    const groups = new Map();
    strs.forEach((s, i) => {
      const key = [...s].sort().join("");
      if (!groups.has(key)) groups.set(key, []);
      groups.get(key).push(s);
      T.step({ line: 4, arrays: [A("strs", strs, { small: true, hl: { [i]: "cur" }, ptr: ptrs({ i }) })], maps: [M("groups", groups, { hl: { [key]: "ok" } })], vars: { key }, msg: `"${s}" sắp xếp thành "${key}", vào nhóm đó.` });
    });
    return [...groups.values()];
  },
});

register("partition-labels", {
  input: 's = "ababcbacadefegde"',
  expect: [9, 7],
  code: `last = last index of each char
start = 0, end = 0
for i in 0..n-1:
  end = max(end, last[s[i]])
  if i == end:
    result.add(end - start + 1)
    start = i + 1
return result`,
  run({ s }, T) {
    const a = chars(s), last = {}, res = [], colors = ["c1", "c2", "c3", "c4", "c5", "c6"], hl = {};
    a.forEach((c, i) => (last[c] = i));
    T.step({ line: 1, arrays: [A("s", a)], maps: [M("vị trí cuối", last)], msg: "Ghi vị trí cuối cùng của mỗi ký tự." });
    let start = 0, end = 0;
    a.forEach((c, i) => {
      end = Math.max(end, last[c]);
      hl[i] = colors[res.length % 6];
      T.step({ line: 4, arrays: [A("s", a, { hl: { ...hl, [i]: "cur" }, ptr: ptrs({ start, i, end }) })], maps: [M("vị trí cuối", last, { hl: { [c]: "mark" } })], vars: { "kết quả": res }, msg: `'${c}' xuất hiện lần cuối ở ${last[c]}, đoạn phải kéo tới ${end}.` });
      if (i === end) {
        res.push(end - start + 1);
        T.step({ line: 6, key: true, arrays: [A("s", a, { hl: { ...hl }, ptr: ptrs({ start, end }) })], maps: [M("vị trí cuối", last)], vars: { "kết quả": res }, msg: `i chạm end → cắt một đoạn dài ${end - start + 1}.` });
        start = i + 1;
      }
    });
    return res;
  },
});

register("integer-to-english-words", {
  input: "num = 1234567",
  expect: "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven",
  code: `if num == 0: return "Zero"
for (v, name) in [(10^9, "Billion"), (10^6, "Million"), (10^3, "Thousand"), (1, "")]:
  if num >= v:
    out += say(num / v) + name    // say xử lý nhóm 3 chữ số
    num %= v
return out`,
  run({ num }, T) {
    const low = "Zero One Two Three Four Five Six Seven Eight Nine Ten Eleven Twelve Thirteen Fourteen Fifteen Sixteen Seventeen Eighteen Nineteen".split(" ");
    const tens = "  Twenty Thirty Forty Fifty Sixty Seventy Eighty Ninety".split(" ");
    const say = (n) => {
      const parts = [];
      if (n >= 100) { parts.push(low[Math.floor(n / 100)], "Hundred"); n %= 100; }
      if (n >= 20) { parts.push(tens[Math.floor(n / 10)]); n %= 10; }
      if (n > 0) parts.push(low[n]);
      return parts;
    };
    if (num === 0) { T.step({ line: 1, texts: [{ label: "kết quả", value: "Zero" }], msg: "Số 0 là trường hợp riêng." }); return "Zero"; }
    const words = [];
    for (const [v, name] of [[1e9, "Billion"], [1e6, "Million"], [1e3, "Thousand"], [1, ""]]) {
      if (num < v) continue;
      const q = Math.floor(num / v);
      words.push(...say(q));
      if (name) words.push(name);
      num %= v;
      T.step({ line: 4, key: true, arrays: [A("nhóm vừa đọc", say(q).concat(name || []), { small: true, noIndex: true, hl: tones(say(q).map((_, i) => i), "ok") })], texts: [{ label: "kết quả", value: words.join(" ") }], vars: { "nhóm": q, "còn lại": num }, msg: `Nhóm ${q}${name ? " " + name : ""}.` });
    }
    return words.join(" ");
  },
});

