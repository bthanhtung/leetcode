/* Visualizers for array, string, stack and 1-D DP problems. */
(function () {
  const { register } = Viz;
  const A = (label, values, o = {}) => ({ label, values, ...o });
  const chars = (s) => s.split("");

  register("two-sum", {
    input: "nums = [2, 7, 11, 15]\ntarget = 9",
    expect: [0, 1],
    run({ nums, target }, T) {
      const map = {};
      const mapView = () => A("map", Object.entries(map).map(([v, i]) => `${v}→${i}`), { small: true, noIndex: true });
      for (let i = 0; i < nums.length; i++) {
        const need = target - nums[i];
        T.step({ arrays: [A("nums", nums, { hl: { [i]: "cur" }, ptr: { [i]: "i" } }), mapView()], vars: { need }, msg: `Cần ${target} − ${nums[i]} = ${need}. Có trong map chưa?` });
        if (need in map) {
          const j = map[need];
          T.step({ arrays: [A("nums", nums, { hl: { [i]: "ok", [j]: "ok" }, ptr: { [i]: "i", [j]: "j" } }), mapView()], vars: { need }, msg: `Có! ${need} ở vị trí ${j} → đáp án [${j}, ${i}]` });
          return [j, i];
        }
        map[nums[i]] = i;
        T.step({ arrays: [A("nums", nums, { hl: { [i]: "done" }, ptr: { [i]: "i" } }), mapView()], vars: { need }, msg: `Chưa có. Lưu ${nums[i]} → ${i} vào map.` });
      }
      return [];
    },
  });

  register("binary-search", {
    input: "nums = [-1, 0, 3, 5, 9, 12, 15, 20]\ntarget = 12",
    expect: 5,
    run({ nums, target }, T) {
      let lo = 0, hi = nums.length - 1;
      while (lo <= hi) {
        const mid = (lo + hi) >> 1;
        const view = (cls, msg) => T.step({ arrays: [A("nums", nums, { range: [lo, hi], dimOutside: true, hl: { [mid]: cls }, ptr: { [lo]: lo === mid ? ["lo", "mid"] : "lo", [mid]: lo === mid ? ["lo", "mid"] : hi === mid ? ["mid", "hi"] : "mid", [hi]: hi === mid ? ["mid", "hi"] : "hi" } })], vars: { lo, mid, hi }, msg });
        if (nums[mid] === target) {
          view("ok", `nums[${mid}] = ${target} → tìm thấy.`);
          return mid;
        }
        if (nums[mid] < target) {
          view("cur", `nums[${mid}] = ${nums[mid]} < ${target} → bỏ nửa trái, lo = ${mid + 1}`);
          lo = mid + 1;
        } else {
          view("cur", `nums[${mid}] = ${nums[mid]} > ${target} → bỏ nửa phải, hi = ${mid - 1}`);
          hi = mid - 1;
        }
      }
      T.step({ arrays: [A("nums", nums, { dimOutside: true, range: [lo, hi] })], msg: "Khoảng tìm kiếm rỗng → không có, trả về −1." });
      return -1;
    },
  });

  register("valid-palindrome", {
    input: 's = "A man, a plan, a canal: Panama"',
    expect: true,
    run({ s }, T) {
      const c = chars(s);
      const ok = (ch) => /[a-z0-9]/i.test(ch);
      let i = 0, j = c.length - 1;
      const done = {};
      while (i < j) {
        if (!ok(c[i])) { done[i] = "dim"; i++; continue; }
        if (!ok(c[j])) { done[j] = "dim"; j--; continue; }
        const same = c[i].toLowerCase() === c[j].toLowerCase();
        T.step({ arrays: [A("s", c, { hl: { ...done, [i]: same ? "ok" : "bad", [j]: same ? "ok" : "bad" }, ptr: { [i]: "i", [j]: "j" } })], msg: same ? `'${c[i]}' và '${c[j]}' giống nhau (không phân biệt hoa thường).` : `'${c[i]}' ≠ '${c[j]}' → không đối xứng.` });
        if (!same) return false;
        done[i] = done[j] = "done";
        i++; j--;
      }
      T.step({ arrays: [A("s", c, { hl: done })], msg: "Hai con trỏ gặp nhau → chuỗi đối xứng." });
      return true;
    },
  });

  register("best-time-to-buy-and-sell-stock", {
    input: "prices = [7, 1, 5, 3, 6, 4]",
    expect: 5,
    run({ prices }, T) {
      let minI = 0, best = 0, bestPair = null;
      for (let i = 1; i < prices.length; i++) {
        const profit = prices[i] - prices[minI];
        if (profit > best) { best = profit; bestPair = [minI, i]; }
        T.step({ arrays: [A("prices", prices, { bars: true, hl: { [minI]: "ok", [i]: profit > 0 ? "cur" : "bad" }, ptr: { [minI]: "mua", [i]: "bán" } })], vars: { "giá thấp nhất": prices[minI], "lời nếu bán hôm nay": profit, best }, msg: `Bán ngày ${i} với giá ${prices[i]}, mua ở giá thấp nhất trước đó ${prices[minI]} → lời ${profit}.` });
        if (prices[i] < prices[minI]) {
          minI = i;
          T.step({ arrays: [A("prices", prices, { bars: true, hl: { [i]: "ok" }, ptr: { [i]: "min mới" } })], vars: { "giá thấp nhất": prices[minI], best }, msg: `${prices[i]} là giá thấp nhất mới.` });
        }
      }
      if (bestPair) T.step({ arrays: [A("prices", prices, { bars: true, hl: { [bestPair[0]]: "ok", [bestPair[1]]: "ok" }, ptr: { [bestPair[0]]: "mua", [bestPair[1]]: "bán" } })], vars: { best }, msg: `Lời lớn nhất: ${best}.` });
      return best;
    },
  });

  register("container-with-most-water", {
    input: "height = [1, 8, 6, 2, 5, 4, 8, 3, 7]",
    expect: 49,
    run({ height }, T) {
      let l = 0, r = height.length - 1, best = 0;
      while (l < r) {
        const area = Math.min(height[l], height[r]) * (r - l);
        best = Math.max(best, area);
        const moveLeft = height[l] < height[r];
        T.step({ arrays: [A("height", height, { bars: true, range: [l, r], hl: { [l]: "cur", [r]: "cur" }, ptr: { [l]: "l", [r]: "r" } })], vars: { "diện tích": `min(${height[l]}, ${height[r]}) × ${r - l} = ${area}`, best }, msg: `Cột thấp hơn quyết định mực nước → dời ${moveLeft ? "l sang phải" : "r sang trái"}.` });
        if (moveLeft) l++; else r--;
      }
      T.step({ arrays: [A("height", height, { bars: true })], vars: { best }, msg: `Hai con trỏ gặp nhau. Lượng nước lớn nhất: ${best}.` });
      return best;
    },
  });

  register("3sum", {
    input: "nums = [-1, 0, 1, 2, -1, -4]",
    expect: [[-1, -1, 2], [-1, 0, 1]],
    run({ nums }, T) {
      const a = [...nums].sort((x, y) => x - y);
      const res = [];
      T.step({ arrays: [A("sorted", a)], msg: "Sắp xếp trước để dùng hai con trỏ." });
      for (let i = 0; i < a.length - 2; i++) {
        if (i > 0 && a[i] === a[i - 1]) {
          T.step({ arrays: [A("sorted", a, { hl: { [i]: "dim" }, ptr: { [i]: "i" } })], vars: { "kết quả": res }, msg: `a[${i}] trùng với số trước → bỏ qua để không lặp bộ ba.` });
          continue;
        }
        let l = i + 1, r = a.length - 1;
        while (l < r) {
          const s = a[i] + a[l] + a[r];
          T.step({ arrays: [A("sorted", a, { range: [l, r], hl: { [i]: "mark", [l]: s === 0 ? "ok" : "cur", [r]: s === 0 ? "ok" : "cur" }, ptr: { [i]: "i", [l]: "l", [r]: "r" } })], vars: { sum: s, "kết quả": res }, msg: s === 0 ? `${a[i]} + ${a[l]} + ${a[r]} = 0 ✓` : s < 0 ? `Tổng ${s} < 0 → tăng l` : `Tổng ${s} > 0 → giảm r` });
          if (s === 0) {
            res.push([a[i], a[l], a[r]]);
            while (l < r && a[l] === a[l + 1]) l++;
            while (l < r && a[r] === a[r - 1]) r--;
            l++; r--;
          } else if (s < 0) l++;
          else r--;
        }
      }
      T.step({ arrays: [A("sorted", a)], vars: { "kết quả": res }, msg: `Xong: ${res.length} bộ ba.` });
      return res;
    },
  });

  register("longest-substring-without-repeating-characters", {
    input: 's = "abcabcbb"',
    expect: 3,
    run({ s }, T) {
      const c = chars(s);
      const last = {};
      let l = 0, best = 0, bestRange = [0, -1];
      for (let r = 0; r < c.length; r++) {
        if (last[c[r]] !== undefined && last[c[r]] >= l) {
          T.step({ arrays: [A("s", c, { range: [l, r - 1], hl: { [r]: "bad", [last[c[r]]]: "bad" }, ptr: { [l]: "l", [r]: "r" } })], vars: { best }, msg: `'${c[r]}' đã có trong cửa sổ ở vị trí ${last[c[r]]} → nhảy l tới ${last[c[r]] + 1}.` });
          l = last[c[r]] + 1;
        }
        last[c[r]] = r;
        if (r - l + 1 > best) { best = r - l + 1; bestRange = [l, r]; }
        T.step({ arrays: [A("s", c, { range: [l, r], hl: { [r]: "cur" }, ptr: { [l]: l === r ? ["l", "r"] : "l", [r]: l === r ? ["l", "r"] : "r" } })], vars: { "cửa sổ": c.slice(l, r + 1).join(""), best }, msg: `Cửa sổ "${c.slice(l, r + 1).join("")}" dài ${r - l + 1}.` });
      }
      T.step({ arrays: [A("s", c, { range: bestRange })], vars: { best }, msg: `Dài nhất: ${best}.` });
      return best;
    },
  });

  register("minimum-window-substring", {
    input: 's = "ADOBECODEBANC"\nt = "ABC"',
    expect: "BANC",
    run({ s, t }, T) {
      const c = chars(s);
      const need = {};
      for (const ch of t) need[ch] = (need[ch] || 0) + 1;
      let missing = t.length, l = 0, best = [0, Infinity];
      const needView = () => Object.entries(need).filter(([k]) => t.includes(k)).map(([k, v]) => `${k}:${v}`).join(" ");
      for (let r = 0; r < c.length; r++) {
        if (need[c[r]] > 0) missing--;
        need[c[r]] = (need[c[r]] || 0) - 1;
        T.step({ arrays: [A("s", c, { range: [l, r], hl: { [r]: t.includes(c[r]) ? "cur" : "" }, ptr: { [l]: "l", [r]: "r" } })], vars: { "còn thiếu": missing, need: needView() }, msg: `Mở rộng: thêm '${c[r]}'.` });
        while (missing === 0) {
          if (r - l < best[1] - best[0]) best = [l, r];
          T.step({ arrays: [A("s", c, { range: [l, r], hl: { [l]: "ok", [r]: "ok" }, ptr: { [l]: "l", [r]: "r" } })], vars: { "tốt nhất": s.slice(best[0], best[1] + 1), need: needView() }, msg: `Cửa sổ "${s.slice(l, r + 1)}" đủ chữ của t → thử thu hẹp từ trái.` });
          need[c[l]]++;
          if (need[c[l]] > 0) missing++;
          l++;
        }
      }
      const ans = best[1] === Infinity ? "" : s.slice(best[0], best[1] + 1);
      T.step({ arrays: [A("s", c, { range: best[1] === Infinity ? [-1, -2] : best })], vars: { "kết quả": ans }, msg: `Cửa sổ ngắn nhất: "${ans}".` });
      return ans;
    },
  });

  register("maximum-subarray", {
    input: "nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4]",
    expect: 6,
    run({ nums }, T) {
      let cur = nums[0], best = nums[0], start = 0, bestRange = [0, 0];
      T.step({ arrays: [A("nums", nums, { range: [0, 0], ptr: { 0: "i" } })], vars: { cur, best }, msg: "Bắt đầu với phần tử đầu tiên." });
      for (let i = 1; i < nums.length; i++) {
        const restart = nums[i] > cur + nums[i];
        if (restart) { cur = nums[i]; start = i; } else cur += nums[i];
        if (cur > best) { best = cur; bestRange = [start, i]; }
        T.step({ arrays: [A("nums", nums, { range: [start, i], hl: { [i]: "cur" }, ptr: { [i]: "i" } })], vars: { cur, best }, msg: restart ? `Tổng cũ âm kéo xuống → bắt đầu đoạn mới tại ${nums[i]}.` : `Nối ${nums[i]} vào đoạn hiện tại → cur = ${cur}.` });
      }
      T.step({ arrays: [A("nums", nums, { range: bestRange })], vars: { best }, msg: `Đoạn có tổng lớn nhất: ${best}.` });
      return best;
    },
  });

  register("trapping-rain-water", {
    input: "height = [0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1]",
    expect: 6,
    run({ height }, T) {
      const water = height.map(() => 0);
      let l = 0, r = height.length - 1, lmax = 0, rmax = 0, total = 0;
      while (l < r) {
        if (height[l] < height[r]) {
          lmax = Math.max(lmax, height[l]);
          water[l] = lmax - height[l];
          total += water[l];
          T.step({ arrays: [A("height", height, { bars: true, water: [...water], hl: { [l]: "cur" }, ptr: { [l]: "l", [r]: "r" } })], vars: { leftMax: lmax, rightMax: rmax, total }, msg: `Bên trái thấp hơn → nước ở l bị chặn bởi leftMax: ${lmax} − ${height[l]} = ${water[l]}.` });
          l++;
        } else {
          rmax = Math.max(rmax, height[r]);
          water[r] = rmax - height[r];
          total += water[r];
          T.step({ arrays: [A("height", height, { bars: true, water: [...water], hl: { [r]: "cur" }, ptr: { [l]: "l", [r]: "r" } })], vars: { leftMax: lmax, rightMax: rmax, total }, msg: `Bên phải thấp hơn (hoặc bằng) → nước ở r = rightMax − h = ${rmax} − ${height[r]} = ${water[r]}.` });
          r--;
        }
      }
      T.step({ arrays: [A("height", height, { bars: true, water })], vars: { total }, msg: `Tổng lượng nước: ${total}.` });
      return total;
    },
  });

  register("valid-parentheses", {
    input: 's = "{[()]}()"',
    expect: true,
    run({ s }, T) {
      const c = chars(s);
      const pair = { ")": "(", "]": "[", "}": "{" };
      const stack = [];
      for (let i = 0; i < c.length; i++) {
        if (!pair[c[i]]) {
          stack.push(c[i]);
          T.step({ arrays: [A("s", c, { hl: { [i]: "cur" }, ptr: { [i]: "i" } }), A("stack", [...stack], { small: true })], msg: `Ngoặc mở '${c[i]}' → push.` });
        } else {
          const top = stack.pop();
          const ok = top === pair[c[i]];
          T.step({ arrays: [A("s", c, { hl: { [i]: ok ? "ok" : "bad" }, ptr: { [i]: "i" } }), A("stack", [...stack], { small: true })], msg: ok ? `'${c[i]}' khớp với '${top}' trên đỉnh → pop.` : `'${c[i]}' không khớp với đỉnh stack (${top ?? "rỗng"}) → sai.` });
          if (!ok) return false;
        }
      }
      T.step({ arrays: [A("s", c), A("stack", stack, { small: true })], msg: stack.length ? "Còn ngoặc mở chưa đóng → sai." : "Stack rỗng → hợp lệ." });
      return stack.length === 0;
    },
  });

  register("daily-temperatures", {
    input: "temperatures = [73, 74, 75, 71, 69, 72, 76, 73]",
    expect: [1, 1, 4, 2, 1, 1, 0, 0],
    run({ temperatures: t }, T) {
      const ans = t.map(() => 0);
      const stack = [];
      for (let i = 0; i < t.length; i++) {
        while (stack.length && t[stack[stack.length - 1]] < t[i]) {
          const j = stack.pop();
          ans[j] = i - j;
          T.step({ arrays: [A("temps", t, { hl: { [i]: "cur", [j]: "ok" }, ptr: { [i]: "i", [j]: "j" } }), A("stack (chỉ số)", [...stack], { small: true }), A("answer", [...ans])], msg: `${t[i]} > ${t[j]} → ngày ${j} chờ ${i - j} ngày. Pop ${j}.` });
        }
        stack.push(i);
        T.step({ arrays: [A("temps", t, { hl: { [i]: "cur" }, ptr: { [i]: "i" } }), A("stack (chỉ số)", [...stack], { small: true }), A("answer", [...ans])], msg: `Push ${i}. Stack giữ các ngày chưa tìm được ngày ấm hơn (nhiệt độ giảm dần).` });
      }
      return ans;
    },
  });

  register("largest-rectangle-in-histogram", {
    input: "heights = [2, 1, 5, 6, 2, 3]",
    expect: 10,
    run({ heights }, T) {
      const h = [...heights, 0];
      const stack = [];
      let best = 0, bestRange = null;
      for (let i = 0; i < h.length; i++) {
        while (stack.length && h[stack[stack.length - 1]] >= h[i]) {
          const top = stack.pop();
          const left = stack.length ? stack[stack.length - 1] + 1 : 0;
          const area = h[top] * (i - left);
          if (area > best) { best = area; bestRange = [left, i - 1]; }
          T.step({ arrays: [A("heights", heights, { bars: true, range: [left, i - 1], hl: { [top]: "cur" }, ptr: { [Math.min(i, heights.length - 1)]: i < heights.length ? "i" : "" } }), A("stack", stack.map((k) => heights[k]), { small: true })], vars: { "diện tích": `${h[top]} × ${i - left} = ${area}`, best }, msg: `Cột ${h[top]} không kéo dài được qua ${i < heights.length ? "cột " + h[i] : "cuối mảng"} → tính hình chữ nhật cao ${h[top]}.` });
        }
        stack.push(i);
        if (i < heights.length) T.step({ arrays: [A("heights", heights, { bars: true, hl: { [i]: "mark" }, ptr: { [i]: "i" } }), A("stack", stack.map((k) => heights[k]), { small: true })], vars: { best }, msg: `Push cột ${heights[i]} (stack giữ các cột tăng dần).` });
      }
      T.step({ arrays: [A("heights", heights, { bars: true, range: bestRange || [-1, -2] })], vars: { best }, msg: `Hình chữ nhật lớn nhất: ${best}.` });
      return best;
    },
  });

  register("sliding-window-maximum", {
    input: "nums = [1, 3, -1, -3, 5, 3, 6, 7]\nk = 3",
    expect: [3, 3, 5, 5, 6, 7],
    run({ nums, k }, T) {
      const dq = [], out = [];
      for (let i = 0; i < nums.length; i++) {
        if (dq.length && dq[0] <= i - k) dq.shift();
        while (dq.length && nums[dq[dq.length - 1]] <= nums[i]) dq.pop();
        dq.push(i);
        const l = Math.max(0, i - k + 1);
        if (i >= k - 1) out.push(nums[dq[0]]);
        T.step({ arrays: [A("nums", nums, { range: [l, i], hl: { [dq[0]]: "ok", [i]: dq[0] === i ? "ok" : "cur" }, ptr: { [i]: "i" } }), A("deque", dq.map((j) => nums[j]), { small: true }), A("max", [...out], { small: true })], msg: i >= k - 1 ? `Đầu deque là max của cửa sổ: ${nums[dq[0]]}.` : "Chưa đủ k phần tử." });
      }
      return out;
    },
  });

  register("search-in-rotated-sorted-array", {
    input: "nums = [4, 5, 6, 7, 0, 1, 2]\ntarget = 0",
    expect: 4,
    run({ nums, target }, T) {
      let lo = 0, hi = nums.length - 1;
      while (lo <= hi) {
        const mid = (lo + hi) >> 1;
        const base = { range: [lo, hi], dimOutside: true, ptr: { [lo]: "lo", [hi]: "hi", [mid]: "mid" } };
        if (nums[mid] === target) {
          T.step({ arrays: [A("nums", nums, { ...base, hl: { [mid]: "ok" } })], msg: `Tìm thấy ở vị trí ${mid}.` });
          return mid;
        }
        const leftSorted = nums[lo] <= nums[mid];
        const hl = {};
        for (let i = leftSorted ? lo : mid; i <= (leftSorted ? mid : hi); i++) hl[i] = "mark";
        hl[mid] = "cur";
        let goLeft;
        if (leftSorted) goLeft = nums[lo] <= target && target < nums[mid];
        else goLeft = !(nums[mid] < target && target <= nums[hi]);
        T.step({ arrays: [A("nums", nums, { ...base, hl })], msg: `Nửa ${leftSorted ? "trái" : "phải"} đã sắp xếp. ${target} ${(leftSorted ? goLeft : !goLeft) ? "nằm" : "không nằm"} trong khoảng đó → tìm bên ${goLeft ? "trái" : "phải"}.` });
        if (goLeft) hi = mid - 1; else lo = mid + 1;
      }
      return -1;
    },
  });

  register("koko-eating-bananas", {
    input: "piles = [3, 6, 7, 11]\nh = 8",
    expect: 4,
    run({ piles, h }, T) {
      let lo = 1, hi = Math.max(...piles);
      while (lo < hi) {
        const mid = Math.floor((lo + hi) / 2);
        const hours = piles.map((p) => Math.ceil(p / mid));
        const total = hours.reduce((a, b) => a + b, 0);
        T.step({ arrays: [A("piles", piles, { bars: true }), A(`giờ (k=${mid})`, hours, { hl: Object.fromEntries(hours.map((_, i) => [i, total <= h ? "ok" : "bad"])) })], vars: { lo, hi, k: mid, "tổng giờ": total, h }, msg: total <= h ? `Tốc độ ${mid} kịp (${total} ≤ ${h}) → thử chậm hơn: hi = ${mid}.` : `Tốc độ ${mid} không kịp (${total} > ${h}) → lo = ${mid + 1}.` });
        if (total <= h) hi = mid; else lo = mid + 1;
      }
      T.step({ arrays: [A("piles", piles, { bars: true }), A(`giờ (k=${lo})`, piles.map((p) => Math.ceil(p / lo)))], vars: { k: lo }, msg: `Tốc độ nhỏ nhất: ${lo}.` });
      return lo;
    },
  });

  register("jump-game", {
    input: "nums = [2, 3, 1, 1, 4]",
    expect: true,
    run({ nums }, T) {
      let reach = 0;
      for (let i = 0; i < nums.length; i++) {
        if (i > reach) {
          T.step({ arrays: [A("nums", nums, { range: [0, reach], hl: { [i]: "bad" }, ptr: { [i]: "i" } })], vars: { reach }, msg: `Ô ${i} vượt quá tầm với ${reach} → kẹt.` });
          return false;
        }
        reach = Math.max(reach, i + nums[i]);
        T.step({ arrays: [A("nums", nums, { range: [0, Math.min(reach, nums.length - 1)], hl: { [i]: "cur" }, ptr: { [i]: "i" } })], vars: { reach }, msg: `Từ ô ${i} nhảy xa nhất tới ${i + nums[i]} → reach = ${reach}.` });
        if (reach >= nums.length - 1) {
          T.step({ arrays: [A("nums", nums, { range: [0, nums.length - 1], hl: { [nums.length - 1]: "ok" } })], vars: { reach }, msg: "Tầm với đã chạm ô cuối." });
          return true;
        }
      }
      return true;
    },
  });

  register("product-of-array-except-self", {
    input: "nums = [1, 2, 3, 4]",
    expect: [24, 12, 8, 6],
    run({ nums }, T) {
      const n = nums.length, out = Array(n).fill(null);
      let p = 1;
      for (let i = 0; i < n; i++) {
        out[i] = p;
        T.step({ arrays: [A("nums", nums, { range: [0, i - 1], hl: { [i]: "cur" }, ptr: { [i]: "i" } }), A("answer", [...out], { hl: { [i]: "cur" } })], vars: { "tích bên trái": p }, msg: `answer[${i}] = tích các số bên trái = ${p}.` });
        p *= nums[i];
      }
      p = 1;
      for (let i = n - 1; i >= 0; i--) {
        out[i] *= p;
        T.step({ arrays: [A("nums", nums, { range: [i + 1, n - 1], hl: { [i]: "cur" }, ptr: { [i]: "i" } }), A("answer", [...out], { hl: { [i]: "ok" } })], vars: { "tích bên phải": p }, msg: `Nhân thêm tích bên phải ${p} → answer[${i}] = ${out[i]}.` });
        p *= nums[i];
      }
      return out;
    },
  });

  register("move-zeroes", {
    input: "nums = [0, 1, 0, 3, 12]",
    expect: [1, 3, 12, 0, 0],
    run({ nums }, T) {
      const a = [...nums];
      let slow = 0;
      for (let fast = 0; fast < a.length; fast++) {
        if (a[fast] !== 0) {
          [a[slow], a[fast]] = [a[fast], a[slow]];
          T.step({ arrays: [A("nums", [...a], { range: [0, slow], hl: { [slow]: "ok", [fast]: "cur" }, ptr: { [slow]: slow === fast ? ["slow", "fast"] : "slow", [fast]: slow === fast ? ["slow", "fast"] : "fast" } })], msg: `${a[slow]} khác 0 → đổi chỗ về vị trí slow = ${slow}.` });
          slow++;
        } else {
          T.step({ arrays: [A("nums", [...a], { range: [0, slow - 1], hl: { [fast]: "dim" }, ptr: { [slow]: slow === fast ? ["slow", "fast"] : "slow", [fast]: slow === fast ? ["slow", "fast"] : "fast" } })], msg: "Gặp 0 → chỉ tiến fast." });
        }
      }
      return a;
    },
  });

  register("sort-colors", {
    input: "nums = [2, 0, 2, 1, 1, 0]",
    expect: [0, 0, 1, 1, 2, 2],
    run({ nums }, T) {
      const a = [...nums];
      let lo = 0, mid = 0, hi = a.length - 1;
      const view = (msg, cls) => {
        const hl = {};
        for (let i = 0; i < lo; i++) hl[i] = "c0";
        for (let i = hi + 1; i < a.length; i++) hl[i] = "c2";
        if (mid < a.length) hl[mid] = cls || "cur";
        T.step({ arrays: [A("nums", [...a], { hl, ptr: { [lo]: lo === mid ? ["lo", "mid"] : "lo", [mid]: lo === mid ? ["lo", "mid"] : mid === hi ? ["mid", "hi"] : "mid", [hi]: mid === hi ? ["mid", "hi"] : "hi" } })], msg });
      };
      while (mid <= hi) {
        if (a[mid] === 0) {
          view("0 → đổi về vùng lo, tiến cả lo và mid.");
          [a[lo], a[mid]] = [a[mid], a[lo]]; lo++; mid++;
        } else if (a[mid] === 1) {
          view("1 → đã đúng vùng giữa, tiến mid.");
          mid++;
        } else {
          view("2 → đổi ra vùng hi, lùi hi (mid giữ nguyên để xét số vừa đổi vào).");
          [a[mid], a[hi]] = [a[hi], a[mid]]; hi--;
        }
      }
      view("Xong: 0 | 1 | 2.", "ok");
      return a;
    },
  });

  register("kth-largest-element-in-an-array", {
    input: "nums = [3, 2, 1, 5, 6, 4]\nk = 2",
    expect: 5,
    run({ nums, k }, T) {
      const a = [...nums];
      const target = a.length - k;
      let lo = 0, hi = a.length - 1;
      while (true) {
        const pivot = a[hi];
        let store = lo;
        for (let i = lo; i < hi; i++) {
          if (a[i] < pivot) { [a[i], a[store]] = [a[store], a[i]]; store++; }
          T.step({ arrays: [A("nums", [...a], { range: [lo, hi], dimOutside: true, hl: { [hi]: "mark", [i]: "cur" }, ptr: { [store]: "store", [i]: "i", [hi]: "pivot" } })], vars: { pivot, "vị trí cần": target }, msg: `${a[i] < pivot ? "Nhỏ hơn" : "Không nhỏ hơn"} pivot ${pivot}.` });
        }
        [a[store], a[hi]] = [a[hi], a[store]];
        T.step({ arrays: [A("nums", [...a], { range: [lo, hi], dimOutside: true, hl: { [store]: store === target ? "ok" : "mark" }, ptr: { [store]: "pivot" } })], vars: { "vị trí pivot": store, "vị trí cần": target }, msg: store === target ? `Pivot rơi đúng vị trí ${target} → phần tử lớn thứ ${k} là ${a[store]}.` : `Pivot ở vị trí ${store}; cần ${target} → chỉ xử lý tiếp nửa ${store < target ? "phải" : "trái"}.` });
        if (store === target) return a[store];
        if (store < target) lo = store + 1; else hi = store - 1;
      }
    },
  });

  register("next-permutation", {
    input: "nums = [1, 3, 5, 4, 2]",
    expect: [1, 4, 2, 3, 5],
    run({ nums }, T) {
      const a = [...nums];
      let i = a.length - 2;
      while (i >= 0 && a[i] >= a[i + 1]) i--;
      T.step({ arrays: [A("nums", [...a], { range: [i + 1, a.length - 1], hl: i >= 0 ? { [i]: "cur" } : {}, ptr: i >= 0 ? { [i]: "i" } : {} })], msg: i >= 0 ? `Phần sau (xanh) giảm dần. Điểm xoay i = ${i} (giá trị ${a[i]}).` : "Cả mảng giảm dần → không có điểm xoay." });
      if (i >= 0) {
        let j = a.length - 1;
        while (a[j] <= a[i]) j--;
        T.step({ arrays: [A("nums", [...a], { range: [i + 1, a.length - 1], hl: { [i]: "cur", [j]: "ok" }, ptr: { [i]: "i", [j]: "j" } })], msg: `${a[j]} là số nhỏ nhất lớn hơn ${a[i]} ở bên phải → đổi chỗ.` });
        [a[i], a[j]] = [a[j], a[i]];
        T.step({ arrays: [A("nums", [...a], { range: [i + 1, a.length - 1], hl: { [i]: "ok" } })], msg: "Sau khi đổi." });
      }
      for (let l = i + 1, r = a.length - 1; l < r; l++, r--) [a[l], a[r]] = [a[r], a[l]];
      T.step({ arrays: [A("nums", [...a], { range: [i + 1, a.length - 1], hl: i >= 0 ? { [i]: "ok" } : {} })], msg: "Đảo ngược phần sau để nó nhỏ nhất → hoán vị kế tiếp." });
      return a;
    },
  });

  register("rotate-array", {
    input: "nums = [1, 2, 3, 4, 5, 6, 7]\nk = 3",
    expect: [5, 6, 7, 1, 2, 3, 4],
    run({ nums, k }, T) {
      const a = [...nums];
      const n = a.length;
      k %= n;
      const rev = (from, to, msg) => {
        T.step({ arrays: [A("nums", [...a], { range: [from, to] })], msg: `${msg}: đảo đoạn [${from}, ${to}].` });
        for (let l = from, r = to; l < r; l++, r--) [a[l], a[r]] = [a[r], a[l]];
        T.step({ arrays: [A("nums", [...a], { range: [from, to] })], msg: "Sau khi đảo." });
      };
      rev(0, n - 1, "Bước 1");
      rev(0, k - 1, "Bước 2");
      rev(k, n - 1, "Bước 3");
      T.step({ arrays: [A("nums", [...a])], msg: `Đã xoay phải ${k} bước.` });
      return a;
    },
  });

  register("subarray-sum-equals-k", {
    input: "nums = [1, 2, 3, -2, 2]\nk = 3",
    expect: 4,
    run({ nums, k }, T) {
      const seen = { 0: 1 };
      let prefix = 0, count = 0;
      const view = () => A("map prefix→lần", Object.entries(seen).map(([p, c]) => `${p}:${c}`), { small: true, noIndex: true });
      for (let i = 0; i < nums.length; i++) {
        prefix += nums[i];
        const add = seen[prefix - k] || 0;
        count += add;
        T.step({ arrays: [A("nums", nums, { range: [0, i], hl: { [i]: "cur" }, ptr: { [i]: "i" } }), view()], vars: { prefix, "tìm prefix − k": prefix - k, count }, msg: add ? `Đã gặp prefix ${prefix - k} ${add} lần → thêm ${add} đoạn kết thúc tại ${i}.` : `Chưa gặp prefix ${prefix - k}.` });
        seen[prefix] = (seen[prefix] || 0) + 1;
      }
      T.step({ arrays: [A("nums", nums), view()], vars: { count }, msg: `Tổng cộng ${count} đoạn có tổng ${k}.` });
      return count;
    },
  });

  register("asteroid-collision", {
    input: "asteroids = [5, 10, -5, 8, -12]",
    expect: [-12],
    run({ asteroids }, T) {
      const stack = [];
      for (let i = 0; i < asteroids.length; i++) {
        const a = asteroids[i];
        let alive = true;
        while (alive && a < 0 && stack.length && stack[stack.length - 1] > 0) {
          const top = stack[stack.length - 1];
          const msg = top < -a ? `${a} phá ${top}.` : top === -a ? `${top} và ${a} cùng nổ.` : `${top} phá ${a}.`;
          if (top <= -a) stack.pop();
          if (top >= -a) alive = false;
          T.step({ arrays: [A("asteroids", asteroids, { hl: { [i]: "bad" }, ptr: { [i]: "i" } }), A("stack (còn sống)", [...stack], { small: true })], msg });
        }
        if (alive) {
          stack.push(a);
          T.step({ arrays: [A("asteroids", asteroids, { hl: { [i]: "cur" }, ptr: { [i]: "i" } }), A("stack (còn sống)", [...stack], { small: true })], msg: `${a} ${a > 0 ? "bay sang phải" : "không va chạm"} → push.` });
        }
      }
      return stack;
    },
  });

  register("gas-station", {
    input: "gas = [1, 2, 3, 4, 5]\ncost = [3, 4, 5, 1, 2]",
    expect: 3,
    run({ gas, cost }, T) {
      const diff = gas.map((g, i) => g - cost[i]);
      let total = 0, tank = 0, start = 0;
      for (let i = 0; i < gas.length; i++) {
        total += diff[i];
        tank += diff[i];
        T.step({ arrays: [A("gas − cost", diff, { range: [start, i], hl: { [i]: tank < 0 ? "bad" : "cur" }, ptr: { [start]: "start", [i]: start === i ? ["start", "i"] : "i" } })], vars: { tank, total }, msg: tank < 0 ? `Bình âm sau trạm ${i} → không trạm nào trong [${start}, ${i}] xuất phát được. start = ${i + 1}.` : `Bình còn ${tank}.` });
        if (tank < 0) { start = i + 1; tank = 0; }
      }
      const ans = total >= 0 ? start : -1;
      T.step({ arrays: [A("gas − cost", diff, { hl: ans >= 0 ? { [ans]: "ok" } : {} })], vars: { total }, msg: total >= 0 ? `Tổng ≥ 0 → xuất phát từ trạm ${start}.` : "Tổng xăng < tổng chi phí → không thể." });
      return ans;
    },
  });

  register("majority-element", {
    input: "nums = [2, 2, 1, 1, 1, 2, 2]",
    expect: 2,
    run({ nums }, T) {
      let cand = null, count = 0;
      for (let i = 0; i < nums.length; i++) {
        let msg;
        if (count === 0) { cand = nums[i]; count = 1; msg = `count = 0 → chọn ${cand} làm ứng viên.`; }
        else if (nums[i] === cand) { count++; msg = `${nums[i]} = ứng viên → count++.`; }
        else { count--; msg = `${nums[i]} ≠ ứng viên → triệt tiêu một cặp, count−−.`; }
        T.step({ arrays: [A("nums", nums, { hl: { [i]: nums[i] === cand ? "ok" : "bad" }, ptr: { [i]: "i" } })], vars: { "ứng viên": cand, count }, msg });
      }
      return cand;
    },
  });

  register("merge-sorted-array", {
    input: "nums1 = [1, 2, 3, 0, 0, 0]\nm = 3\nnums2 = [2, 5, 6]\nn = 3",
    expect: [1, 2, 2, 3, 5, 6],
    run({ nums1, m, nums2, n }, T) {
      const a = [...nums1];
      let i = m - 1, j = n - 1, k = m + n - 1;
      while (j >= 0) {
        const fromA = i >= 0 && a[i] > nums2[j];
        a[k] = fromA ? a[i] : nums2[j];
        T.step({ arrays: [A("nums1", [...a], { range: [k, m + n - 1], hl: { [k]: "ok", ...(i >= 0 ? { [i]: fromA ? "cur" : "" } : {}) }, ptr: { ...(i >= 0 ? { [i]: "i" } : {}), [k]: k === i ? ["i", "k"] : "k" } }), A("nums2", nums2, { range: [j, j], hl: { [j]: fromA ? "" : "cur" }, ptr: { [j]: "j" } })], msg: `Số lớn hơn giữa ${i >= 0 ? a[i] : "∅"} và ${nums2[j]} đặt vào cuối (vị trí ${k}).` });
        if (fromA) i--; else j--;
        k--;
      }
      return a;
    },
  });

  register("find-peak-element", {
    input: "nums = [1, 2, 1, 3, 5, 6, 4]",
    expect: 5,
    run({ nums }, T) {
      let lo = 0, hi = nums.length - 1;
      while (lo < hi) {
        const mid = (lo + hi) >> 1;
        const up = nums[mid] < nums[mid + 1];
        T.step({ arrays: [A("nums", nums, { bars: true, range: [lo, hi], dimOutside: true, hl: { [mid]: "cur", [mid + 1]: "mark" }, ptr: { [lo]: "lo", [hi]: "hi", [mid]: "mid" } })], msg: up ? `${nums[mid]} < ${nums[mid + 1]}: đang dốc lên → có đỉnh bên phải.` : `${nums[mid]} > ${nums[mid + 1]}: đang dốc xuống → có đỉnh ở mid hoặc bên trái.` });
        if (up) lo = mid + 1; else hi = mid;
      }
      T.step({ arrays: [A("nums", nums, { bars: true, hl: { [lo]: "ok" }, ptr: { [lo]: "đỉnh" } })], msg: `Đỉnh tại vị trí ${lo}.` });
      return lo;
    },
  });

  register("climbing-stairs", {
    input: "n = 6",
    expect: 13,
    run({ n }, T) {
      const dp = Array(n + 1).fill(null);
      dp[0] = 1; dp[1] = 1;
      T.step({ arrays: [A("ways", [...dp], { hl: { 0: "done", 1: "done" } })], msg: "Có 1 cách đứng ở bậc 0 và 1 cách lên bậc 1." });
      for (let i = 2; i <= n; i++) {
        dp[i] = dp[i - 1] + dp[i - 2];
        T.step({ arrays: [A("ways", [...dp], { hl: { [i]: "cur", [i - 1]: "mark", [i - 2]: "mark" }, ptr: { [i]: "i" } })], msg: `Bước cuối là 1 hoặc 2 bậc: ways[${i}] = ${dp[i - 1]} + ${dp[i - 2]} = ${dp[i]}.` });
      }
      return dp[n];
    },
  });

  register("house-robber", {
    input: "nums = [2, 7, 9, 3, 1]",
    expect: 12,
    run({ nums }, T) {
      const dp = Array(nums.length).fill(null);
      for (let i = 0; i < nums.length; i++) {
        const take = nums[i] + (i >= 2 ? dp[i - 2] : 0);
        const skip = i >= 1 ? dp[i - 1] : 0;
        dp[i] = Math.max(take, skip);
        T.step({ arrays: [A("nums", nums, { hl: { [i]: "cur" }, ptr: { [i]: "i" } }), A("best", [...dp], { hl: { [i]: take >= skip ? "ok" : "mark" } })], vars: { "lấy nhà này": take, "bỏ nhà này": skip }, msg: `best[${i}] = max(${nums[i]} + best[${i - 2}], best[${i - 1}]) = ${dp[i]}.` });
      }
      return dp[nums.length - 1];
    },
  });

  register("coin-change", {
    input: "coins = [1, 2, 5]\namount = 11",
    expect: 3,
    run({ coins, amount }, T) {
      const INF = amount + 1;
      const dp = Array(amount + 1).fill(INF);
      dp[0] = 0;
      const show = (v) => v.map((x) => (x >= INF ? "∞" : x));
      for (let a = 1; a <= amount; a++) {
        const from = {};
        for (const c of coins) if (c <= a && dp[a - c] + 1 < dp[a]) { dp[a] = dp[a - c] + 1; from.c = c; }
        T.step({ arrays: [A("dp", show(dp), { hl: { [a]: "cur", ...(from.c ? { [a - from.c]: "mark" } : {}) }, ptr: { [a]: "a" } })], vars: { coins }, msg: from.c ? `dp[${a}] = dp[${a - from.c}] + 1 = ${dp[a]} (xu cuối là ${from.c}).` : `Không ghép được ${a}.` });
      }
      return dp[amount] >= INF ? -1 : dp[amount];
    },
  });

  register("longest-increasing-subsequence", {
    input: "nums = [10, 9, 2, 5, 3, 7, 101, 18]",
    expect: 4,
    run({ nums }, T) {
      const tails = [];
      for (let i = 0; i < nums.length; i++) {
        let lo = 0, hi = tails.length;
        while (lo < hi) { const m = (lo + hi) >> 1; if (tails[m] < nums[i]) lo = m + 1; else hi = m; }
        const msg = lo === tails.length ? `${nums[i]} lớn hơn mọi đuôi → nối dài, độ dài ${lo + 1}.` : `${nums[i]} thay đuôi ${tails[lo]} của dãy dài ${lo + 1} (đuôi nhỏ hơn thì dễ nối hơn).`;
        tails[lo] = nums[i];
        T.step({ arrays: [A("nums", nums, { hl: { [i]: "cur" }, ptr: { [i]: "i" } }), A("tails", [...tails], { hl: { [lo]: "ok" } })], msg });
      }
      return tails.length;
    },
  });

  register("decode-ways", {
    input: 's = "11106"',
    expect: 2,
    run({ s }, T) {
      const n = s.length;
      const dp = Array(n + 1).fill(null);
      dp[0] = 1;
      dp[1] = s[0] === "0" ? 0 : 1;
      const labels = ["", ...s.split("")];
      T.step({ arrays: [A("s", s.split("")), A("ways", [...dp], { indexLabels: labels.map((_, i) => i) })], msg: "ways[i] = số cách giải mã i ký tự đầu." });
      for (let i = 2; i <= n; i++) {
        const one = s[i - 1] !== "0" ? dp[i - 1] : 0;
        const two = Number(s.slice(i - 2, i));
        const twoWays = two >= 10 && two <= 26 ? dp[i - 2] : 0;
        dp[i] = one + twoWays;
        T.step({ arrays: [A("s", s.split(""), { hl: { [i - 1]: "cur", ...(twoWays ? { [i - 2]: "mark" } : {}) } }), A("ways", [...dp], { hl: { [i]: "ok" } })], msg: `Một chữ số '${s[i - 1]}': ${s[i - 1] !== "0" ? "+" + dp[i - 1] : "không hợp lệ"}. Hai chữ số '${s.slice(i - 2, i)}': ${twoWays ? "+" + dp[i - 2] : "không hợp lệ"}. → ${dp[i]}` });
      }
      return dp[n];
    },
  });

  register("partition-equal-subset-sum", {
    input: "nums = [1, 5, 11, 5]",
    expect: true,
    run({ nums }, T) {
      const total = nums.reduce((a, b) => a + b, 0);
      if (total % 2) {
        T.step({ arrays: [A("nums", nums)], msg: `Tổng ${total} lẻ → không chia đôi được.` });
        return false;
      }
      const half = total / 2;
      const can = Array(half + 1).fill(false);
      can[0] = true;
      const view = (hl) => A("đạt tổng", can.map((b) => (b ? "✓" : "")), { hl });
      T.step({ arrays: [A("nums", nums), view({ 0: "ok" })], vars: { half }, msg: `Cần một tập con có tổng ${half}.` });
      for (let k = 0; k < nums.length; k++) {
        const x = nums[k];
        const added = {};
        for (let s = half; s >= x; s--) if (!can[s] && can[s - x]) { can[s] = true; added[s] = "ok"; }
        T.step({ arrays: [A("nums", nums, { hl: { [k]: "cur" } }), view(added)], vars: { half }, msg: `Thêm số ${x}: các tổng mới đạt được tô xanh (duyệt s giảm dần để mỗi số chỉ dùng một lần).` });
        if (can[half]) break;
      }
      T.step({ arrays: [A("nums", nums), view({ [half]: can[half] ? "ok" : "bad" })], msg: can[half] ? `Đạt được ${half} → chia được.` : `Không đạt ${half}.` });
      return can[half];
    },
  });

  register("subsets", {
    input: "nums = [1, 2, 3]",
    expect: 8,
    run({ nums }, T) {
      const res = [];
      const path = [];
      (function go(i) {
        res.push([...path]);
        T.step({ arrays: [A("nums", nums, { range: [i, nums.length - 1] }), A("path", [...path], { small: true })], vars: { "số tập con": res.length, "vừa thêm": `[${path.join(",")}]` }, msg: `Ghi nhận [${path.join(", ")}]. Thử thêm các số từ vị trí ${i}.` });
        for (let j = i; j < nums.length; j++) {
          path.push(nums[j]);
          go(j + 1);
          path.pop();
        }
      })(0);
      return res.length;
    },
  });

  register("permutations", {
    input: "nums = [1, 2, 3]",
    expect: 6,
    run({ nums }, T) {
      const used = nums.map(() => false), path = [];
      let count = 0;
      (function go() {
        if (path.length === nums.length) {
          count++;
          T.step({ arrays: [A("nums", nums, { hl: Object.fromEntries(nums.map((_, i) => [i, "ok"])) }), A("path", [...path], { small: true })], vars: { "hoán vị": count }, msg: `Đủ ${nums.length} số → hoán vị thứ ${count}: [${path.join(", ")}].` });
          return;
        }
        for (let i = 0; i < nums.length; i++) {
          if (used[i]) continue;
          used[i] = true; path.push(nums[i]);
          T.step({ arrays: [A("nums", nums, { hl: Object.fromEntries(used.map((u, k) => [k, u ? "dim" : ""])), ptr: { [i]: "chọn" } }), A("path", [...path], { small: true })], vars: { "hoán vị": count }, msg: `Chọn ${nums[i]}.` });
          go();
          used[i] = false; path.pop();
        }
      })();
      return count;
    },
  });
})();
