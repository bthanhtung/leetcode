/* Linked lists. A list lives in two arrays, vals and next, so a frame can show pointers being rewired. */
import { A, Heap, L, M, ptrs, register } from "./engine.js";

const fromArray = (a) => ({ vals: [...a], next: a.map((_, i) => (i + 1 < a.length ? i + 1 : -1)) });
const toArray = (l, head) => {
  const out = [];
  for (let i = head; i !== -1; i = l.next[i]) out.push(l.vals[i]);
  return out;
};

register("add-two-numbers", {
  input: "l1 = [2, 4, 3]\nl2 = [5, 6, 4]",
  expect: [7, 0, 8],
  code: `dummy = new Node(), tail = dummy, carry = 0
while l1 or l2 or carry:
  sum = carry + (l1 ? l1.val : 0) + (l2 ? l2.val : 0)
  tail = tail.next = new Node(sum % 10)
  carry = sum / 10
  l1 = l1?.next; l2 = l2?.next
return dummy.next`,
  run({ l1, l2 }, T) {
    const out = [];
    let carry = 0;
    for (let i = 0; i < Math.max(l1.length, l2.length) || carry; i++) {
      const a = l1[i] ?? 0, b = l2[i] ?? 0, sum = a + b + carry;
      out.push(sum % 10);
      carry = Math.floor(sum / 10);
      T.step({ line: 4, lists: [L("l1", l1, { hl: { [i]: "cur" } }), L("l2", l2, { hl: { [i]: "cur" } }), L("kết quả", [...out], { hl: { [out.length - 1]: "ok" } })], vars: { carry }, msg: `${a} + ${b} + nhớ = ${sum}: ghi ${sum % 10}, nhớ ${carry}. Số lưu ngược nên cộng từ đầu danh sách.` });
    }
    return out;
  },
});

register("remove-nth-node-from-end-of-list", {
  input: "head = [1, 2, 3, 4, 5]\nn = 2",
  expect: [1, 2, 3, 5],
  code: `dummy.next = head
fast = slow = dummy
repeat n times: fast = fast.next
while fast.next:
  fast = fast.next; slow = slow.next
slow.next = slow.next.next
return dummy.next`,
  run({ head, n }, T) {
    const l = fromArray(["∅", ...head]);
    let fast = 0, slow = 0;
    const view = (line, msg, hl = {}, key) => T.step({ line, key, lists: [L("list", l, { ptr: ptrs({ slow, fast }), hl: { [slow]: "mark", [fast]: "cur", ...hl } })], msg });
    view(2, "Thêm nút giả trước head để xoá được cả nút đầu.");
    for (let i = 0; i < n; i++) { fast = l.next[fast]; view(3, `fast đi trước ${i + 1} bước.`); }
    while (l.next[fast] !== -1) { fast = l.next[fast]; slow = l.next[slow]; view(5, "Cả hai cùng đi, khoảng cách luôn là n."); }
    const del = l.next[slow];
    view(6, `fast ở cuối, nút sau slow chính là nút cần xoá (${l.vals[del]}).`, { [del]: "bad" }, true);
    l.next[slow] = l.next[del];
    l.next[del] = -1;
    view(6, "Nối vòng qua nút đó.", { [del]: "dim" });
    return toArray(l, l.next[0]);
  },
});

register("merge-two-sorted-lists", {
  input: "list1 = [1, 2, 4]\nlist2 = [1, 3, 4]",
  expect: [1, 1, 2, 3, 4, 4],
  code: `tail = dummy
while l1 and l2:
  if l1.val <= l2.val: tail.next = l1; l1 = l1.next
  else: tail.next = l2; l2 = l2.next
  tail = tail.next
tail.next = l1 or l2
return dummy.next`,
  run({ list1, list2 }, T) {
    let i = 0, j = 0;
    const out = [];
    const view = (line, msg) => T.step({ line, lists: [L("list1", list1, { hl: Object.fromEntries(list1.map((_, k) => [k, k < i ? "dim" : k === i ? "cur" : "base"])), ptr: ptrs({ l1: i }, list1.length) }), L("list2", list2, { hl: Object.fromEntries(list2.map((_, k) => [k, k < j ? "dim" : k === j ? "cur" : "base"])), ptr: ptrs({ l2: j }, list2.length) }), L("kết quả", [...out], { hl: { [out.length - 1]: "ok" } })], msg });
    while (i < list1.length && j < list2.length) {
      if (list1[i] <= list2[j]) { const v = list1[i++]; out.push(v); view(3, `${v} ≤ ${list2[j]} → lấy từ list1.`); }
      else { const v = list2[j++]; out.push(v); view(4, `${v} < ${list1[i]} → lấy từ list2.`); }
    }
    while (i < list1.length) out.push(list1[i++]);
    while (j < list2.length) out.push(list2[j++]);
    view(6, "Một danh sách hết, nối nguyên phần còn lại.");
    return out;
  },
});

register("merge-k-sorted-lists", {
  input: "lists = [[1, 4, 5], [1, 3, 4], [2, 6]]",
  expect: [1, 1, 2, 3, 4, 4, 5, 6],
  code: `heap = min-heap of each list's head
while heap not empty:
  node = heap.pop()
  tail = tail.next = node
  if node.next: heap.push(node.next)
return dummy.next`,
  run({ lists }, T) {
    const heap = new Heap((a, b) => a.v < b.v), out = [], pos = lists.map(() => 0);
    lists.forEach((x, r) => { if (x.length) heap.push({ v: x[0], r }); });
    const views = (hlRow) => lists.map((x, r) => L(`list ${r}`, x, { hl: Object.fromEntries(x.map((_, k) => [k, k < pos[r] ? "dim" : r === hlRow && k === pos[r] ? "cur" : "base"])) }));
    T.step({ line: 1, lists: views(-1), trees: [heap.tree("min-heap", (e) => e.v)], msg: "Đưa đầu mỗi danh sách vào heap." });
    while (heap.size) {
      const { v, r } = heap.pop();
      out.push(v);
      pos[r]++;
      if (pos[r] < lists[r].length) heap.push({ v: lists[r][pos[r]], r });
      T.step({ line: 5, lists: [...views(r), L("kết quả", [...out], { hl: { [out.length - 1]: "ok" } })], trees: [heap.tree("min-heap", (e) => e.v)], msg: `Lấy ${v} nhỏ nhất (từ list ${r}), đưa nút kế của list ${r} vào heap.` });
    }
    return out;
  },
});

function kReverse(slug, input, expect, k0, code) {
  register(slug, {
    input, expect, code,
    run(args, T) {
      const k = k0 ?? args.k;
      const l = fromArray(["∅", ...args.head]);
      let prev = 0;
      const view = (line, msg, ptr, hl, key) => T.step({ line, key, lists: [L("list", l, { ptr, hl })], msg });
      for (;;) {
        let end = prev;
        for (let i = 0; i < k && end !== -1; i++) end = l.next[end];
        if (end === -1) { view(2, `Còn chưa đủ ${k} nút → giữ nguyên.`, ptrs({ prev }), {}, true); break; }
        const first = l.next[prev], after = l.next[end];
        view(2, `Nhóm ${k} nút tiếp theo bắt đầu từ ${l.vals[first]}.`, ptrs({ prev, first, end }), { [first]: "mark", [end]: "mark" });
        let p = after, c = first;
        while (c !== after) {
          const nx = l.next[c];
          l.next[c] = p;
          p = c;
          c = nx;
          view(4, `Đảo chiều con trỏ của nút ${l.vals[p]}.`, ptrs({ p, c: c === -1 ? null : c }), { [p]: "cur" });
        }
        l.next[prev] = end;
        view(5, "Nối đoạn đã đảo vào danh sách.", ptrs({ prev }), { [end]: "ok", [first]: "ok" }, true);
        prev = first;
      }
      return toArray(l, l.next[0]);
    },
  });
}

kReverse("swap-nodes-in-pairs", "head = [1, 2, 3, 4]", [2, 1, 4, 3], 2, `prev = dummy
while prev has 2 nodes after it:
  a = prev.next, b = a.next
  a.next = b.next; b.next = a
  prev.next = b
  prev = a`);

kReverse("reverse-nodes-in-k-group", "head = [1, 2, 3, 4, 5]\nk = 2", [2, 1, 4, 3, 5], undefined, `prev = dummy
while k nodes remain after prev:
  first = prev.next, end = k-th node
  reverse first..end
  prev.next = end
  prev = first`);

register("rotate-list", {
  input: "head = [1, 2, 3, 4, 5]\nk = 2",
  expect: [4, 5, 1, 2, 3],
  code: `n = length, tail = last node
k %= n
tail.next = head               // tạo vòng
newTail = node at n - k - 1
newHead = newTail.next
newTail.next = null
return newHead`,
  run({ head, k }, T) {
    const n = head.length, l = fromArray(head);
    k %= n;
    T.step({ line: 2, lists: [L("list", l)], vars: { n, k }, msg: `Xoay ${k} bước (đã lấy dư theo n).` });
    if (!k) return head;
    l.next[n - 1] = 0;
    T.step({ line: 3, lists: [L("list", l, { hl: { [n - 1]: "mark", 0: "mark" } })], msg: "Nối đuôi vào đầu thành vòng." });
    const t = n - k - 1;
    T.step({ line: 4, lists: [L("list", l, { hl: { [t]: "cur" }, ptr: ptrs({ newTail: t, newHead: t + 1 }) })], msg: `Đuôi mới ở vị trí ${t}.` });
    l.next[t] = -1;
    T.step({ line: 6, key: true, lists: [L("list", l, { hl: { [t]: "ok", [t + 1]: "ok" }, ptr: ptrs({ newTail: t, newHead: t + 1 }) })], msg: "Cắt vòng ngay sau đuôi mới." });
    return toArray(l, t + 1);
  },
});

register("remove-duplicates-from-sorted-list-ii", {
  input: "head = [1, 2, 3, 3, 4, 4, 5]",
  expect: [1, 2, 5],
  code: `prev = dummy, cur = head
while cur:
  if cur.next and cur.val == cur.next.val:
    skip all nodes equal to cur.val
    prev.next = cur
  else:
    prev = cur; cur = cur.next
return dummy.next`,
  run({ head }, T) {
    const l = fromArray(["∅", ...head]);
    let prev = 0, cur = 1;
    const dead = {};
    const view = (line, msg, hl = {}) => T.step({ line, lists: [L("list", l, { hl: { ...dead, ...hl }, ptr: ptrs({ prev, cur }) })], msg });
    while (cur !== -1 && cur < l.vals.length) {
      const nx = l.next[cur];
      if (nx !== -1 && l.vals[cur] === l.vals[nx]) {
        const v = l.vals[cur];
        while (cur !== -1 && l.vals[cur] === v) { dead[cur] = "bad"; view(4, `${v} bị lặp → xoá cả nhóm.`); cur = l.next[cur]; }
        l.next[prev] = cur;
        view(5, "Nối prev qua cả nhóm trùng.");
      } else {
        view(7, `${l.vals[cur]} không trùng, giữ lại.`, { [cur]: "ok" });
        prev = cur;
        cur = nx;
      }
    }
    return toArray(l, l.next[0]);
  },
});

register("remove-duplicates-from-sorted-list", {
  input: "head = [1, 1, 2, 3, 3]",
  expect: [1, 2, 3],
  code: `cur = head
while cur and cur.next:
  if cur.val == cur.next.val: cur.next = cur.next.next
  else: cur = cur.next
return head`,
  run({ head }, T) {
    const l = fromArray(head);
    let cur = 0;
    const dead = {};
    while (cur !== -1 && l.next[cur] !== -1) {
      const nx = l.next[cur];
      if (l.vals[cur] === l.vals[nx]) {
        dead[nx] = "bad";
        l.next[cur] = l.next[nx];
        l.next[nx] = -1;
        T.step({ line: 3, lists: [L("list", l, { hl: { ...dead, [cur]: "cur" }, ptr: ptrs({ cur }) })], msg: `Nút sau trùng ${l.vals[cur]} → bỏ qua.` });
      } else {
        T.step({ line: 4, lists: [L("list", l, { hl: { ...dead, [cur]: "ok" }, ptr: ptrs({ cur }) })], msg: "Khác nhau → tiến tiếp." });
        cur = nx;
      }
    }
    return toArray(l, 0);
  },
});

register("partition-list", {
  input: "head = [1, 4, 3, 2, 5, 2]\nx = 3",
  expect: [1, 2, 2, 4, 3, 5],
  code: `small = dummyA, large = dummyB
for node in list:
  if node.val < x: small = small.next = node
  else: large = large.next = node
large.next = null
small.next = dummyB.next
return dummyA.next`,
  run({ head, x }, T) {
    const a = [], b = [];
    head.forEach((v, i) => {
      (v < x ? a : b).push(v);
      T.step({ line: v < x ? 3 : 4, lists: [L("list", head, { hl: { [i]: "cur" } }), L(`< ${x}`, [...a]), L(`≥ ${x}`, [...b])], msg: `${v} ${v < x ? "<" : "≥"} ${x}.` });
    });
    const out = [...a, ...b];
    T.step({ line: 6, key: true, lists: [L("kết quả", out, { hl: Object.fromEntries(out.map((_, i) => [i, i < a.length ? "c1" : "c3"])) })], msg: "Nối danh sách nhỏ trước danh sách lớn, thứ tự mỗi nhóm giữ nguyên." });
    return out;
  },
});

register("reverse-linked-list-ii", {
  input: "head = [1, 2, 3, 4, 5]\nleft = 2\nright = 4",
  expect: [1, 4, 3, 2, 5],
  code: `prev = node before left
cur = prev.next
repeat right - left times:
  nxt = cur.next
  cur.next = nxt.next
  nxt.next = prev.next
  prev.next = nxt
return dummy.next`,
  run({ head, left, right }, T) {
    const l = fromArray(["∅", ...head]);
    const prev = left - 1, cur = left;
    T.step({ line: 2, lists: [L("list", l, { ptr: ptrs({ prev, cur }), hl: { [prev]: "mark", [cur]: "cur" } })], msg: "prev đứng trước đoạn cần đảo." });
    for (let t = 0; t < right - left; t++) {
      const nxt = l.next[cur];
      l.next[cur] = l.next[nxt];
      l.next[nxt] = l.next[prev];
      l.next[prev] = nxt;
      T.step({ line: 7, lists: [L("list", l, { ptr: ptrs({ prev, cur, nxt }), hl: { [prev]: "mark", [cur]: "cur", [nxt]: "ok" } })], msg: `Nhấc nút ${l.vals[nxt]} chèn ngay sau prev.` });
    }
    return toArray(l, l.next[0]);
  },
});

register("copy-list-with-random-pointer", {
  input: "vals = [7, 13, 11, 10, 1]\nrandom = [-1, 0, 4, 2, 0]",
  expect: [[7, -1], [13, 0], [11, 4], [10, 2], [1, 0]],
  code: `map = {}
for node in list: map[node] = new Node(node.val)
for node in list:
  map[node].next = map[node.next]
  map[node].random = map[node.random]
return map[head]`,
  run({ vals, random }, T) {
    const n = vals.length, copy = [], rand = Array(n).fill("?");
    vals.forEach((v, i) => {
      copy.push(v);
      T.step({ line: 2, lists: [L("gốc (mũi tên cong = random)", { vals, next: random }, { hl: { [i]: "cur" } })], arrays: [A("bản sao", [...copy], { hl: { [i]: "ok" } })], maps: [M("gốc → bản sao", copy.map((c, k) => [k, `copy${k}`]))], msg: `Tạo bản sao của nút ${v}.` });
    });
    vals.forEach((v, i) => {
      rand[i] = random[i];
      T.step({ line: 5, lists: [L("gốc (mũi tên cong = random)", { vals, next: random }, { hl: { [i]: "cur", ...(random[i] >= 0 ? { [random[i]]: "mark" } : {}) } })], arrays: [A("random của bản sao", [...rand], { hl: { [i]: "ok" } })], msg: random[i] >= 0 ? `random của ${v} trỏ tới ${vals[random[i]]} → tra map để lấy bản sao tương ứng.` : `random của ${v} là null.` });
    });
    return vals.map((v, i) => [v, random[i]]);
  },
});

register("linked-list-cycle-ii", {
  input: "head = [3, 2, 0, -4]\npos = 1",
  expect: 1,
  code: `slow = fast = head
while fast and fast.next:
  slow = slow.next; fast = fast.next.next
  if slow == fast:
    p = head
    while p != slow: p = p.next; slow = slow.next
    return p
return null`,
  run({ head, pos }, T) {
    const l = fromArray(head);
    if (pos >= 0) l.next[head.length - 1] = pos;
    let slow = 0, fast = 0;
    const view = (line, msg, ptr, hl, key) => T.step({ line, key, lists: [L("list", l, { ptr, hl })], msg });
    view(1, "Rùa và thỏ cùng xuất phát.", ptrs({ slow, fast }), {});
    while (fast !== -1 && l.next[fast] !== -1) {
      slow = l.next[slow];
      fast = l.next[l.next[fast]];
      view(3, "Rùa 1 bước, thỏ 2 bước.", ptrs({ slow, fast }), { [slow]: "cur", [fast]: "mark" });
      if (fast !== -1 && slow === fast) {
        view(4, "Gặp nhau → có chu trình.", ptrs({ slow, fast }), { [slow]: "ok" }, true);
        let p = 0;
        view(5, "Đi từ đầu và từ điểm gặp, cùng tốc độ.", ptrs({ p, slow }), { [p]: "cur", [slow]: "mark" });
        while (p !== slow) {
          p = l.next[p];
          slow = l.next[slow];
          view(6, "Cùng tiến một bước.", ptrs({ p, slow }), { [p]: "cur", [slow]: "mark" });
        }
        view(7, `Gặp lại tại nút ${p}: cửa vào chu trình.`, ptrs({ p }), { [p]: "ok" }, true);
        return p;
      }
    }
    view(8, "Thỏ chạm cuối → không có chu trình.", ptrs({}), {}, true);
    return -1;
  },
});

register("reorder-list", {
  input: "head = [1, 2, 3, 4, 5]",
  expect: [1, 5, 2, 4, 3],
  code: `mid = middle node (slow, fast)
second = reverse(mid.next); mid.next = null
first = head
while second:
  link first -> second -> first.next
  advance both
return head`,
  run({ head }, T) {
    const n = head.length, m = Math.floor((n - 1) / 2);
    const first = head.slice(0, m + 1), second = head.slice(m + 1);
    T.step({ line: 1, lists: [L("list", head, { hl: { [m]: "mark" }, ptr: ptrs({ mid: m }) })], msg: "Tìm nút giữa bằng hai con trỏ nhanh chậm." });
    const rev = [...second].reverse();
    T.step({ line: 2, lists: [L("nửa đầu", first), L("nửa sau (đã đảo)", rev)], msg: "Tách đôi và đảo nửa sau." });
    const out = [];
    for (let i = 0; i < first.length; i++) {
      out.push(first[i]);
      T.step({ line: 5, lists: [L("nửa đầu", first, { hl: { [i]: "cur" } }), L("nửa sau (đã đảo)", rev, { hl: { [i]: "cur" } }), L("kết quả", [...out], { hl: { [out.length - 1]: "ok" } })], msg: `Lấy ${first[i]} từ nửa đầu.` });
      if (i < rev.length) {
        out.push(rev[i]);
        T.step({ line: 5, lists: [L("nửa đầu", first, { hl: { [i]: "cur" } }), L("nửa sau (đã đảo)", rev, { hl: { [i]: "cur" } }), L("kết quả", [...out], { hl: { [out.length - 1]: "ok" } })], msg: `Xen ${rev[i]} từ nửa sau.` });
      }
    }
    return out;
  },
});

register("sort-list", {
  input: "head = [4, 2, 1, 3]",
  expect: [1, 2, 3, 4],
  code: `sort(head):
  if head is null or single: return head
  mid = split in half
  left = sort(head), right = sort(mid)
  return merge(left, right)`,
  run({ head }, T) {
    const sort = (a, depth) => {
      if (a.length <= 1) return a;
      const m = a.length >> 1, x = a.slice(0, m), y = a.slice(m);
      T.step({ line: 3, lists: [L("nửa trái", x), L("nửa phải", y)], vars: { "độ sâu": depth }, msg: `Tách [${a}] làm đôi.` });
      const p = sort(x, depth + 1), q = sort(y, depth + 1), out = [];
      let i = 0, j = 0;
      while (i < p.length && j < q.length) out.push(p[i] <= q[j] ? p[i++] : q[j++]);
      out.push(...p.slice(i), ...q.slice(j));
      T.step({ line: 5, key: true, lists: [L("trái đã sắp", p), L("phải đã sắp", q), L("sau khi trộn", out, { hl: Object.fromEntries(out.map((_, k) => [k, "ok"])) })], vars: { "độ sâu": depth }, msg: `Trộn thành [${out}].` });
      return out;
    };
    return sort(head, 0);
  },
});

register("intersection-of-two-linked-lists", {
  input: "listA = [4, 1, 8, 4, 5]\nlistB = [5, 6, 1, 8, 4, 5]\nskipA = 2\nskipB = 3",
  expect: 8,
  code: `a = headA, b = headB
while a != b:
  a = a ? a.next : headB
  b = b ? b.next : headA
return a`,
  run({ listA, listB, skipA, skipB }, T) {
    // Shared tail is one set of nodes: A's head part, B's head part, then the common part.
    const na = skipA, nb = skipB, common = listA.slice(skipA);
    const vals = [...listA.slice(0, na), ...listB.slice(0, nb), ...common];
    const next = vals.map((_, i) => (i + 1 < vals.length ? i + 1 : -1));
    if (na) next[na - 1] = na + nb;
    const seqA = [...Array(na).keys(), ...common.map((_, i) => na + nb + i)], seqB = [...Array(nb).keys()].map((i) => na + i).concat(common.map((_, i) => na + nb + i));
    const pathA = [...seqA, -1, ...seqB, -1], pathB = [...seqB, -1, ...seqA, -1];
    for (let t = 0; t < pathA.length; t++) {
      const a = pathA[t], b = pathB[t], meet = a === b;
      T.step({ line: meet ? 5 : 3, key: meet, lists: [L("hai danh sách", { vals, next }, { ptr: ptrs({ a, b }), hl: meet && a !== -1 ? { [a]: "ok" } : { [a]: "cur", [b]: "mark" } })], msg: meet ? (a === -1 ? "Cả hai về null: không giao." : `Gặp nhau tại nút ${vals[a]}.`) : "Hết danh sách mình thì sang đầu danh sách kia, nhờ vậy cả hai đi cùng quãng đường." });
      if (meet) return a === -1 ? null : vals[a];
    }
    return null;
  },
});

register("remove-linked-list-elements", {
  input: "head = [1, 2, 6, 3, 4, 5, 6]\nval = 6",
  expect: [1, 2, 3, 4, 5],
  code: `cur = dummy
while cur.next:
  if cur.next.val == val: cur.next = cur.next.next
  else: cur = cur.next
return dummy.next`,
  run({ head, val }, T) {
    const l = fromArray(["∅", ...head]);
    let cur = 0;
    const dead = {};
    while (l.next[cur] !== -1) {
      const nx = l.next[cur];
      if (l.vals[nx] === val) {
        l.next[cur] = l.next[nx];
        l.next[nx] = -1;
        dead[nx] = "bad";
        T.step({ line: 3, lists: [L("list", l, { hl: { ...dead, [cur]: "cur" }, ptr: ptrs({ cur }) })], msg: `Nút sau mang giá trị ${val} → nối vòng qua.` });
      } else {
        T.step({ line: 4, lists: [L("list", l, { hl: { ...dead, [cur]: "cur" }, ptr: ptrs({ cur }) })], msg: "Giữ nút này, tiến tiếp." });
        cur = nx;
      }
    }
    return toArray(l, l.next[0]);
  },
});

register("palindrome-linked-list", {
  input: "head = [1, 2, 2, 1]",
  expect: true,
  code: `slow = middle of list
second = reverse(slow)
first = head
while second:
  if first.val != second.val: return false
  advance both
return true`,
  run({ head }, T) {
    const n = head.length, mid = n >> 1, half = head.slice(Math.ceil(n / 2)).reverse();
    T.step({ line: 1, lists: [L("list", head, { hl: { [Math.ceil(n / 2)]: "mark" }, ptr: ptrs({ slow: Math.ceil(n / 2) }) })], msg: "Tìm điểm giữa." });
    T.step({ line: 2, lists: [L("nửa đầu", head.slice(0, mid)), L("nửa sau (đã đảo)", half)], msg: "Đảo nửa sau để so từ hai đầu." });
    for (let i = 0; i < mid; i++) {
      const same = head[i] === half[i];
      T.step({ line: 5, key: !same, lists: [L("nửa đầu", head.slice(0, mid), { hl: { [i]: same ? "ok" : "bad" } }), L("nửa sau (đã đảo)", half, { hl: { [i]: same ? "ok" : "bad" } })], msg: same ? `${head[i]} = ${half[i]}.` : `${head[i]} ≠ ${half[i]}.` });
      if (!same) return false;
    }
    return true;
  },
});

register("odd-even-linked-list", {
  input: "head = [2, 1, 3, 5, 6, 4, 7]",
  expect: [2, 3, 6, 7, 1, 5, 4],
  code: `odd = head, even = head.next, evenHead = even
while even and even.next:
  odd.next = even.next; odd = odd.next
  even.next = odd.next; even = even.next
odd.next = evenHead
return head`,
  run({ head }, T) {
    const l = fromArray(head);
    let odd = 0, even = 1;
    const tone = () => Object.fromEntries(head.map((_, i) => [i, i % 2 ? "c3" : "c1"]));
    while (even !== -1 && l.next[even] !== -1) {
      l.next[odd] = l.next[even];
      odd = l.next[odd];
      l.next[even] = l.next[odd];
      even = l.next[even];
      T.step({ line: 4, lists: [L("list", l, { hl: tone(), ptr: ptrs({ odd, even }) })], msg: "Vị trí lẻ nối vị trí lẻ, vị trí chẵn nối vị trí chẵn." });
    }
    l.next[odd] = 1;
    T.step({ line: 5, key: true, lists: [L("list", l, { hl: tone(), ptr: ptrs({ odd }) })], msg: "Nối đuôi nhóm lẻ vào đầu nhóm chẵn." });
    return toArray(l, 0);
  },
});

register("convert-binary-number-in-a-linked-list-to-integer", {
  input: "head = [1, 0, 1]",
  expect: 5,
  code: `x = 0
for node in list:
  x = x * 2 + node.val
return x`,
  run({ head }, T) {
    let x = 0;
    head.forEach((b, i) => {
      const old = x;
      x = x * 2 + b;
      T.step({ line: 3, lists: [L("list", head, { hl: { [i]: "cur" } })], vars: { x }, msg: `${old} × 2 + ${b} = ${x}.` });
    });
    return x;
  },
});

