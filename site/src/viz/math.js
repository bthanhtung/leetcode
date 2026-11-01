/* Math, bits, number theory and geometry. */
import { A, G, M, S, chars, ptrs, register, tones } from "./engine.js";

const bits = (n, w = 8) => chars((n >>> 0).toString(2).padStart(w, "0"));
const range = (a, b) => Array.from({ length: Math.max(0, b - a + 1) }, (_, i) => a + i);

register("reverse-integer", {
  input: "x = -123",
  expect: -321,
  code: `r = 0
while x != 0:
  d = x % 10
  x /= 10
  if r overflows after r * 10 + d: return 0
  r = r * 10 + d
return r`,
  run({ x }, T) {
    let r = 0;
    while (x !== 0) {
      const d = x % 10;
      x = Math.trunc(x / 10);
      const next = r * 10 + d;
      if (next > 2147483647 || next < -2147483648) { T.step({ line: 5, key: true, texts: [{ label: "r", value: next }], msg: "Tràn 32-bit → trả về 0." }); return 0; }
      r = next;
      T.step({ line: 6, texts: [{ label: "x", value: x }, { label: "r", value: r }], vars: { d }, msg: `Tách chữ số cuối ${d}, nối vào r.` });
    }
    return r;
  },
});

register("palindrome-number", {
  input: "x = 1221",
  expect: true,
  code: `if x < 0 or (x % 10 == 0 and x != 0): return false
half = 0
while x > half:
  half = half * 10 + x % 10
  x /= 10
return x == half or x == half / 10`,
  run({ x }, T) {
    if (x < 0 || (x % 10 === 0 && x !== 0)) { T.step({ line: 1, texts: [{ label: "x", value: x }], msg: "Số âm hoặc tận cùng 0 không đối xứng." }); return false; }
    let half = 0;
    while (x > half) {
      half = half * 10 + (x % 10);
      x = Math.floor(x / 10);
      T.step({ line: 5, texts: [{ label: "nửa đầu", value: x }, { label: "nửa sau đảo", value: half }], msg: "Chỉ đảo nửa sau, khỏi lo tràn số." });
    }
    const ok = x === half || x === Math.floor(half / 10);
    T.step({ line: 6, key: true, texts: [{ label: "nửa đầu", value: x }, { label: "nửa sau đảo", value: half }], msg: ok ? "Hai nửa bằng nhau → đối xứng." : "Hai nửa khác nhau." });
    return ok;
  },
});

register("divide-two-integers", {
  input: "dividend = 43\ndivisor = 3",
  expect: 14,
  code: `a = |dividend|, b = |divisor|, q = 0
while a >= b:
  shift = 0
  while a >= b << (shift + 1): shift++
  a -= b << shift
  q += 1 << shift
return sign * q`,
  run({ dividend, divisor }, T) {
    if (dividend === -2147483648 && divisor === -1) return 2147483647;
    const sign = dividend < 0 !== divisor < 0 ? -1 : 1;
    let a = Math.abs(dividend), q = 0;
    const b = Math.abs(divisor);
    while (a >= b) {
      let shift = 0;
      while (a >= b * 2 ** (shift + 1)) shift++;
      const part = b * 2 ** shift;
      a -= part;
      q += 2 ** shift;
      T.step({ line: 5, arrays: [A("b nhân đôi dần", range(0, shift).map((s) => b * 2 ** s), { small: true, indexLabels: range(0, shift).map((s) => `×${2 ** s}`), hl: { [shift]: "ok" } })], vars: { "còn lại": a, q }, msg: `Trừ ${part} (= ${b} × ${2 ** shift}), thương cộng ${2 ** shift}.` });
    }
    T.step({ line: 7, key: true, texts: [{ label: "thương", value: sign * q }], msg: `Kết quả ${sign * q}.` });
    return sign * q;
  },
});

register("powx-n", {
  input: "x = 2\nn = 10",
  expect: 1024,
  code: `if n < 0: x = 1 / x; n = -n
result = 1
while n > 0:
  if n & 1: result *= x
  x *= x
  n >>= 1
return result`,
  run({ x, n }, T) {
    if (n < 0) { x = 1 / x; n = -n; }
    let r = 1;
    while (n > 0) {
      const take = n & 1;
      if (take) r *= x;
      T.step({ line: 4, key: !!take, arrays: [A("n (nhị phân)", chars(n.toString(2)), { hl: { [n.toString(2).length - 1]: take ? "ok" : "dim" }, noIndex: true })], vars: { x, result: r }, msg: take ? `Bit thấp nhất là 1 → nhân ${x} vào kết quả.` : "Bit thấp nhất là 0 → bỏ qua." });
      x *= x;
      n >>= 1;
      if (n) T.step({ line: 5, arrays: [A("n (nhị phân)", chars(n.toString(2)), { noIndex: true })], vars: { x, result: r }, msg: "Bình phương x, dịch n sang phải." });
    }
    return r;
  },
});

register("permutation-sequence", {
  input: "n = 4\nk = 9",
  expect: "2314",
  code: `digits = [1..n], k--, out = ""
for i from n-1 down to 0:
  idx = k / i!
  out += digits.remove(idx)
  k %= i!
return out`,
  run({ n, k }, T) {
    const f = [1];
    for (let i = 1; i <= n; i++) f[i] = f[i - 1] * i;
    const digits = range(1, n);
    let out = "";
    k--;
    T.step({ line: 1, arrays: [A("số còn lại", [...digits])], vars: { k }, msg: "Đổi k sang đếm từ 0." });
    for (let i = n - 1; i >= 0; i--) {
      const idx = Math.floor(k / f[i]), d = digits[idx];
      T.step({ line: 3, arrays: [A("số còn lại", [...digits], { hl: { [idx]: "ok" }, ptr: ptrs({ idx }) })], vars: { k, [`${i}!`]: f[i], "kết quả": out }, msg: `Mỗi chữ số đầu ứng với ${f[i]} hoán vị: ${k} / ${f[i]} = ${idx}, chọn ${d}.` });
      digits.splice(idx, 1);
      out += d;
      k %= f[i];
    }
    T.step({ line: 6, key: true, texts: [{ label: "kết quả", value: out }], msg: `Hoán vị cần tìm: ${out}.` });
    return out;
  },
});

function binaryRoot(slug, input, expect, code, line, verdict) {
  register(slug, {
    input, expect, code,
    run(args, T) {
      const n = Object.values(args)[0];
      let lo = 0, hi = n;
      let ans = 0;
      while (lo <= hi) {
        const mid = Math.floor((lo + hi) / 2), sq = mid * mid;
        const hit = sq === n;
        T.step({ line, key: hit, intervals: [{ label: "khoảng tìm", items: [[lo, hi]], names: [`${lo} … ${hi}`], axis: [0, n], cursor: mid, hl: { 0: "win" } }], vars: { mid, "mid²": sq, n }, msg: sq <= n ? `${mid}² = ${sq} ≤ ${n}: thử lớn hơn.` : `${mid}² = ${sq} > ${n}: thử nhỏ hơn.` });
        if (hit) { ans = mid; break; }
        if (sq < n) { ans = mid; lo = mid + 1; } else hi = mid - 1;
      }
      return verdict(ans, n);
    },
  });
}

binaryRoot("sqrtx", "x = 8", 2, `lo = 0, hi = x
while lo <= hi:
  mid = (lo + hi) / 2
  if mid * mid <= x: ans = mid; lo = mid + 1
  else: hi = mid - 1
return ans`, 4, (ans) => ans);

binaryRoot("valid-perfect-square", "num = 16", true, `lo = 0, hi = num
while lo <= hi:
  mid = (lo + hi) / 2
  if mid * mid == num: return true
  if mid * mid < num: lo = mid + 1
  else: hi = mid - 1
return false`, 4, (ans, n) => ans * ans === n);

register("max-points-on-a-line", {
  input: "points = [[1, 1], [3, 2], [5, 3], [4, 1], [2, 3], [1, 4]]",
  expect: 4,
  code: `best = 1
for i in 0..n-1:
  slopes = {}
  for j in i+1..n-1:
    key = reduced (dy, dx) of points i, j
    slopes[key]++
    best = max(best, slopes[key] + 1)
return best`,
  run({ points }, T) {
    const gcd = (a, b) => (b ? gcd(b, a % b) : Math.abs(a));
    let best = 1;
    points.forEach((p, i) => {
      const slopes = new Map(), members = new Map();
      for (let j = i + 1; j < points.length; j++) {
        let dy = points[j][1] - p[1], dx = points[j][0] - p[0];
        const g = gcd(dy, dx) || 1;
        dy /= g; dx /= g;
        if (dx < 0 || (dx === 0 && dy < 0)) { dx = -dx; dy = -dy; }
        const key = `${dy}/${dx}`;
        slopes.set(key, (slopes.get(key) ?? 0) + 1);
        members.set(key, [...(members.get(key) ?? []), j]);
        const up = slopes.get(key) + 1 > best;
        best = Math.max(best, slopes.get(key) + 1);
        T.step({ line: 6, key: up, points: [{ label: "các điểm", pts: points, hl: { [i]: "cur", ...tones(members.get(key), "ok") }, path: [i, ...members.get(key)] }], maps: [M("độ dốc", slopes, { hl: { [key]: "ok" } })], vars: { best }, msg: `Điểm ${j} so với ${i}: độ dốc ${key}, thẳng hàng ${slopes.get(key) + 1} điểm.` });
      }
    });
    return best;
  },
});

register("reverse-bits", {
  input: "n = 43261596",
  expect: 964176192,
  code: `r = 0
repeat 32 times:
  r = (r << 1) | (n & 1)
  n >>>= 1
return r`,
  run({ n }, T) {
    let r = 0;
    for (let i = 0; i < 32; i++) {
      const b = n & 1;
      r = ((r << 1) | b) >>> 0;
      n >>>= 1;
      if (i < 8 || i === 31 || b) T.step({ line: 3, key: i === 31, arrays: [A("n", bits(n, 32), { small: true, noIndex: true, hl: { 31: "cur" } }), A("r", bits(r, 32), { small: true, noIndex: true, hl: { 31: "ok" } })], vars: { "lượt": i + 1, bit: b }, msg: `Lấy bit thấp nhất của n (${b}) đưa vào đuôi r.` });
    }
    return r;
  },
});

register("number-of-1-bits", {
  input: "n = 11",
  expect: 3,
  code: `count = 0
while n != 0:
  n &= n - 1      // xoá bit 1 thấp nhất
  count++
return count`,
  run({ n }, T) {
    let count = 0;
    while (n !== 0) {
      const before = n;
      n &= n - 1;
      count++;
      T.step({ line: 3, arrays: [A("trước", bits(before), { noIndex: true, small: true }), A("sau n & (n−1)", bits(n), { noIndex: true, small: true, hl: tones(range(0, 7).filter((i) => bits(before)[i] !== bits(n)[i]), "bad") })], vars: { count }, msg: "Bit 1 thấp nhất biến mất, mỗi vòng đếm đúng một bit." });
    }
    return count;
  },
});

register("happy-number", {
  input: "n = 19",
  expect: true,
  code: `seen = {}
while n != 1 and n not in seen:
  seen.add(n)
  n = sum of squares of digits of n
return n == 1`,
  run({ n }, T) {
    const seen = new Set();
    while (n !== 1 && !seen.has(n)) {
      seen.add(n);
      const parts = chars(String(n));
      const next = parts.reduce((s, d) => s + Number(d) ** 2, 0);
      T.step({ line: 4, arrays: [A("chữ số", parts)], maps: [M("đã gặp", seen, { hl: { [n]: "cur" } })], msg: `${parts.map((d) => `${d}²`).join(" + ")} = ${next}.` });
      n = next;
    }
    T.step({ line: 5, key: true, maps: [M("đã gặp", seen, { hl: { [n]: "bad" } })], vars: { n }, msg: n === 1 ? "Về 1 → số vui." : `${n} lặp lại → vòng lặp vô tận.` });
    return n === 1;
  },
});

function powerCheck(slug, input, expect, code, base) {
  register(slug, {
    input, expect, code,
    run({ n }, T) {
      if (n <= 0) { T.step({ line: 1, texts: [{ label: "n", value: n }], msg: "Số không dương thì không phải." }); return false; }
      while (n % base === 0) { T.step({ line: 2, texts: [{ label: "n", value: n }], msg: `Chia hết cho ${base}.` }); n /= base; }
      T.step({ line: 3, key: true, texts: [{ label: "n", value: n }], msg: n === 1 ? `Về 1 → là lũy thừa của ${base}.` : `Dừng ở ${n} ≠ 1 → không phải.` });
      return n === 1;
    },
  });
}

powerCheck("power-of-three", "n = 27", true, `if n <= 0: return false
while n % 3 == 0: n /= 3
return n == 1`, 3);

register("power-of-two", {
  input: "n = 16",
  expect: true,
  code: `return n > 0 and (n & (n - 1)) == 0`,
  run({ n }, T) {
    const ok = n > 0 && (n & (n - 1)) === 0;
    T.step({ line: 1, arrays: [A("n", bits(n), { noIndex: true, hl: tones(range(0, 7).filter((i) => bits(n)[i] === "1"), "mark") }), A("n − 1", bits(n - 1), { noIndex: true })], msg: "Lũy thừa của 2 chỉ có đúng một bit 1." });
    T.step({ line: 1, key: true, arrays: [A("n & (n−1)", bits(n & (n - 1)), { noIndex: true, hl: { 7: ok ? "ok" : "bad" } })], msg: ok ? "Kết quả bằng 0 → đúng." : "Còn bit khác → sai." });
    return ok;
  },
});

register("number-of-digit-one", {
  input: "n = 13",
  expect: 6,
  code: `count = 0
for p = 1; p <= n; p *= 10:
  high = n / (p * 10), cur = n / p % 10, low = n % p
  count += high * p
  if cur > 1: count += p
  else if cur == 1: count += low + 1
return count`,
  run({ n }, T) {
    let count = 0;
    for (let p = 1; p <= n; p *= 10) {
      const high = Math.floor(n / (p * 10)), cur = Math.floor(n / p) % 10, low = n % p;
      const add = high * p + (cur > 1 ? p : cur === 1 ? low + 1 : 0);
      count += add;
      T.step({ line: cur > 1 ? 5 : cur === 1 ? 6 : 4, key: true, arrays: [A("n", chars(String(n)), { hl: { [String(n).length - 1 - Math.log10(p)]: "cur" } })], vars: { "hàng": p, high, cur, low, "thêm": add, count }, msg: `Ở hàng ${p}, chữ số 1 xuất hiện ${add} lần.` });
    }
    return count;
  },
});

register("add-digits", {
  input: "num = 38",
  expect: 2,
  code: `return num == 0 ? 0 : 1 + (num - 1) % 9`,
  run({ num }, T) {
    let n = num;
    while (n >= 10) {
      const parts = chars(String(n)), next = parts.reduce((s, d) => s + Number(d), 0);
      T.step({ line: 1, arrays: [A("chữ số", parts)], msg: `Cộng thủ công: ${parts.join(" + ")} = ${next}.` });
      n = next;
    }
    const r = num === 0 ? 0 : 1 + ((num - 1) % 9);
    T.step({ line: 1, key: true, texts: [{ label: "công thức", value: `1 + (${num} − 1) % 9 = ${r}` }], msg: "Công thức O(1) cho cùng kết quả: phần dư khi chia 9." });
    return r;
  },
});

register("ugly-number", {
  input: "n = 30",
  expect: true,
  code: `if n <= 0: return false
for p in [2, 3, 5]:
  while n % p == 0: n /= p
return n == 1`,
  run({ n }, T) {
    if (n <= 0) { T.step({ line: 1, texts: [{ label: "n", value: n }], msg: "Số không dương không xét." }); return false; }
    for (const p of [2, 3, 5]) while (n % p === 0) {
      n /= p;
      T.step({ line: 3, texts: [{ label: "n", value: n }], vars: { p }, msg: `Chia cho ${p}.` });
    }
    T.step({ line: 4, key: true, texts: [{ label: "n", value: n }], msg: n === 1 ? "Chỉ có thừa số 2, 3, 5." : `Còn thừa số ${n}.` });
    return n === 1;
  },
});

register("nim-game", {
  input: "n = 8",
  expect: false,
  code: `return n % 4 != 0`,
  run({ n }, T) {
    const stones = range(1, n);
    const lose = (x) => x % 4 === 0;
    stones.forEach((x, i) => T.step({ line: 1, arrays: [A("số viên → người đi trước thắng?", stones.map((v) => (v <= x ? (lose(v) ? "thua" : "thắng") : "?")), { small: true, indexLabels: stones, hl: { [i]: lose(x) ? "bad" : "ok" } })], msg: lose(x) ? `${x} viên: bốc 1, 2 hay 3 đều để lại thế thắng cho đối thủ.` : `${x} viên: bốc ${x % 4} để đối thủ gặp bội của 4.` }));
    return n % 4 !== 0;
  },
});

register("counting-bits", {
  input: "n = 5",
  expect: [0, 1, 1, 2, 1, 2],
  code: `ans = [0] * (n + 1)
for i in 1..n:
  ans[i] = ans[i >> 1] + (i & 1)
return ans`,
  run({ n }, T) {
    const ans = Array(n + 1).fill(0);
    for (let i = 1; i <= n; i++) {
      ans[i] = ans[i >> 1] + (i & 1);
      T.step({ line: 3, arrays: [A("ans", ans, { hl: { [i]: "cur", [i >> 1]: "mark" }, ptr: ptrs({ i, "i>>1": i >> 1 }) }), A("i (nhị phân)", bits(i, 4), { small: true, noIndex: true, hl: { 3: i & 1 ? "ok" : "dim" } })], msg: `${i} = ${i >> 1} dịch trái rồi thêm bit ${i & 1}: ${ans[i >> 1]} + ${i & 1} = ${ans[i]}.` });
    }
    return ans;
  },
});

register("hamming-distance", {
  input: "x = 1\ny = 4",
  expect: 2,
  code: `z = x ^ y
count = 0
while z != 0:
  z &= z - 1
  count++
return count`,
  run({ x, y }, T) {
    let z = x ^ y, count = 0;
    T.step({ line: 1, arrays: [A("x", bits(x), { noIndex: true }), A("y", bits(y), { noIndex: true }), A("x ^ y", bits(z), { noIndex: true, hl: tones(range(0, 7).filter((i) => bits(z)[i] === "1"), "mark") })], msg: "XOR giữ lại đúng các bit khác nhau." });
    while (z) { z &= z - 1; count++; T.step({ line: 5, arrays: [A("z", bits(z), { noIndex: true })], vars: { count }, msg: `Xoá một bit 1, count = ${count}.` }); }
    return count;
  },
});

register("number-complement", {
  input: "num = 5",
  expect: 2,
  code: `mask = 1
while mask < num: mask = mask << 1 | 1
return num ^ mask`,
  run({ num }, T) {
    let mask = 1;
    while (mask < num) { mask = (mask << 1) | 1; T.step({ line: 2, arrays: [A("num", bits(num), { noIndex: true }), A("mask", bits(mask), { noIndex: true })], msg: "Mở rộng mask cho phủ hết độ dài của num." }); }
    const r = num ^ mask;
    T.step({ line: 3, key: true, arrays: [A("num", bits(num), { noIndex: true }), A("mask", bits(mask), { noIndex: true }), A("num ^ mask", bits(r), { noIndex: true, hl: tones(range(0, 7).filter((i) => bits(mask)[i] === "1"), "ok") })], msg: `Lật mọi bit có nghĩa → ${r}.` });
    return r;
  },
});

register("fibonacci-number", {
  input: "n = 6",
  expect: 8,
  code: `a = 0, b = 1
repeat n times:
  (a, b) = (b, a + b)
return a`,
  run({ n }, T) {
    const f = [0, 1];
    let a = 0, b = 1;
    for (let i = 0; i < n; i++) {
      [a, b] = [b, a + b];
      f[i + 2] = b;
      T.step({ line: 3, arrays: [A("F", f.slice(0, Math.max(2, i + 3)), { hl: { [i + 1]: "ok", [i + 2]: "cur" }, ptr: ptrs({ a: i + 1, b: i + 2 }) })], msg: `F(${i + 2}) = ${f[i]} + ${f[i + 1]} = ${b}.` });
    }
    return a;
  },
});

register("find-the-closest-palindrome", {
  input: 'n = "1234"',
  expect: "1221",
  code: `L = len(n), x = int(n)
candidates = { 10^(L-1) - 1, 10^L + 1 }
half = prefix of first (L+1)/2 digits
for h in [half - 1, half, half + 1]:
  candidates.add(mirror(h))
remove x
return closest to x (smaller on tie)`,
  run({ n }, T) {
    const L = n.length, x = BigInt(n), half = BigInt(n.slice(0, Math.ceil(L / 2)));
    const mirror = (h) => { const s = String(h); return BigInt(s + [...s.slice(0, L % 2 ? s.length - 1 : s.length)].reverse().join("")); };
    const c = [10n ** BigInt(L - 1) - 1n, 10n ** BigInt(L) + 1n];
    T.step({ line: 2, arrays: [A("ứng viên", c.map(String), { small: true, noIndex: true })], msg: "Hai trường hợp biên: 99…9 và 100…01." });
    for (const h of [half - 1n, half, half + 1n]) {
      c.push(mirror(h));
      T.step({ line: 5, arrays: [A("ứng viên", c.map(String), { small: true, noIndex: true, hl: { [c.length - 1]: "cur" } })], vars: { "nửa đầu": String(h) }, msg: `Soi gương ${h} → ${c[c.length - 1]}.` });
    }
    let best = null;
    const abs = (v) => (v < 0n ? -v : v);
    for (const v of c) {
      if (v === x) continue;
      if (best === null || abs(v - x) < abs(best - x) || (abs(v - x) === abs(best - x) && v < best)) best = v;
    }
    T.step({ line: 7, key: true, arrays: [A("ứng viên", c.map(String), { small: true, noIndex: true, hl: { [c.indexOf(best)]: "ok" } })], msg: `Gần ${n} nhất: ${best}.` });
    return String(best);
  },
});

register("erect-the-fence", {
  input: "trees = [[1, 1], [2, 2], [2, 0], [2, 4], [3, 3], [4, 2]]",
  expect: [[1, 1], [2, 0], [4, 2], [3, 3], [2, 4]],
  code: `sort points by (x, y)
for each point p:                    // nửa dưới
  while hull has 2+ and turn(a, b, p) < 0: pop
  push p
repeat in reverse for the upper half
return unique(hull)`,
  run({ trees }, T) {
    const pts = [...trees].sort((a, b) => a[0] - b[0] || a[1] - b[1]);
    const cross = (o, a, b) => (pts[a][0] - pts[o][0]) * (pts[b][1] - pts[o][1]) - (pts[a][1] - pts[o][1]) * (pts[b][0] - pts[o][0]);
    const hull = [];
    const view = (line, p, msg) => T.step({ line, points: [{ label: "cây", pts, path: [...hull], hl: { ...tones(hull, "ok"), [p]: "cur" } }], stacks: [S("hull", hull.map((i) => `(${pts[i]})`))], msg });
    const scan = (order, line) => {
      const base = hull.length;
      for (const p of order) {
        while (hull.length >= base + 2 && cross(hull[hull.length - 2], hull[hull.length - 1], p) < 0) {
          view(3, p, "Rẽ sai chiều → loại điểm giữa.");
          hull.pop();
        }
        hull.push(p);
        view(line, p, `Thêm điểm (${pts[p]}).`);
      }
    };
    const idx = pts.map((_, i) => i);
    scan(idx, 4);
    hull.pop();
    scan([...idx].reverse(), 5);
    hull.pop();
    const seen = new Set(), out = [];
    for (const i of hull) if (!seen.has(i)) { seen.add(i); out.push(pts[i]); }
    T.step({ line: 6, key: true, points: [{ label: "hàng rào", pts, path: [...hull, hull[0]], hl: tones(hull, "ok") }], msg: `Hàng rào đi qua ${out.length} cây.` });
    return out;
  },
});

register("kth-smallest-number-in-multiplication-table", {
  input: "m = 3\nn = 3\nk = 5",
  expect: 3,
  code: `lo = 1, hi = m * n
while lo < hi:
  mid = (lo + hi) / 2
  cnt = sum of min(mid / i, n) for i in 1..m
  if cnt >= k: hi = mid
  else: lo = mid + 1
return lo`,
  run({ m, n, k }, T) {
    const cells = Array.from({ length: m }, (_, i) => Array.from({ length: n }, (_, j) => (i + 1) * (j + 1)));
    let lo = 1, hi = m * n;
    while (lo < hi) {
      const mid = (lo + hi) >> 1;
      let cnt = 0;
      for (let i = 1; i <= m; i++) cnt += Math.min(Math.floor(mid / i), n);
      const hl = {};
      cells.forEach((r, i) => r.forEach((v, j) => { if (v <= mid) hl[i + "," + j] = "c1"; }));
      T.step({ line: cnt >= k ? 5 : 6, grids: [G("bảng cửu chương", cells, { hl })], vars: { lo, hi, mid, "số ô ≤ mid": cnt, k }, msg: `Có ${cnt} ô ≤ ${mid} ${cnt >= k ? `≥ ${k} → đáp án ≤ ${mid}.` : `< ${k} → đáp án > ${mid}.`}` });
      if (cnt >= k) hi = mid; else lo = mid + 1;
    }
    T.step({ line: 7, key: true, grids: [G("bảng cửu chương", cells, { hl: Object.fromEntries(cells.flatMap((r, i) => r.flatMap((v, j) => (v === lo ? [[i + "," + j, "ok"]] : [])))) })], msg: `Số nhỏ thứ ${k} là ${lo}.` });
    return lo;
  },
});

register("reaching-points", {
  input: "sx = 1\nsy = 1\ntx = 3\nty = 5",
  expect: true,
  code: `while tx >= sx and ty >= sy:
  if tx == sx and ty == sy: return true
  if tx > ty: tx -= k * ty      // lùi về nhanh bằng modulo
  else: ty -= k * tx
return false`,
  run({ sx, sy, tx, ty }, T) {
    const path = [[tx, ty]];
    while (tx >= sx && ty >= sy) {
      if (tx === sx && ty === sy) { T.step({ line: 2, key: true, points: [{ label: "đi ngược từ đích", pts: path, path: path.map((_, i) => i), hl: { [path.length - 1]: "ok" } }], msg: "Về đúng điểm xuất phát." }); return true; }
      if (tx > ty) {
        if (ty === sy) { const ok = (tx - sx) % ty === 0; T.step({ line: 3, key: true, points: [{ label: "đi ngược từ đích", pts: path }], msg: ok ? "Chỉ còn trừ dần tx, khớp sx." : "Không khớp sx." }); return ok; }
        tx = tx % ty || ty;
      } else {
        if (tx === sx) { const ok = (ty - sy) % tx === 0; T.step({ line: 4, key: true, points: [{ label: "đi ngược từ đích", pts: path, path: path.map((_, i) => i), hl: { [path.length - 1]: ok ? "ok" : "bad" } }], vars: { tx, ty }, msg: ok ? `tx đã khớp, (ty − sy) chia hết cho ${tx} → tới được.` : "Không khớp sy." }); return ok; }
        ty = ty % tx || tx;
      }
      path.push([tx, ty]);
      T.step({ line: tx > ty ? 3 : 4, points: [{ label: "đi ngược từ đích", pts: path, path: path.map((_, i) => i), hl: { [path.length - 1]: "cur" } }], vars: { tx, ty }, msg: "Số lớn hơn chắc chắn vừa được cộng ở bước trước, nên trừ ngược lại." });
    }
    T.step({ line: 5, key: true, vars: { tx, ty }, points: [{ label: "đi ngược từ đích", pts: path }], msg: "Đã lọt xuống dưới điểm xuất phát." });
    return false;
  },
});

register("consecutive-numbers-sum", {
  input: "n = 15",
  expect: 4,
  code: `count = 0
for k = 1; k * (k + 1) / 2 <= n; k++:
  rest = n - k * (k - 1) / 2
  if rest % k == 0: count++     // bắt đầu từ rest / k
return count`,
  run({ n }, T) {
    let count = 0;
    for (let k = 1; (k * (k + 1)) / 2 <= n; k++) {
      const rest = n - (k * (k - 1)) / 2, ok = rest % k === 0;
      if (ok) count++;
      T.step({ line: 4, key: ok, arrays: ok ? [A(`dãy ${k} số`, range(rest / k, rest / k + k - 1), { hl: tones(range(0, k - 1), "ok") })] : [A(`dãy ${k} số`, ["?"])], vars: { k, rest, count }, msg: ok ? `${k} số liên tiếp từ ${rest / k} có tổng ${n}.` : `${rest} không chia hết cho ${k}.` });
    }
    return count;
  },
});

