<!-- approaches: Close into a ring, cut at length - k -->
# 61. Rotate List

**Medium** · Linked List · Two Pointers · [LeetCode ↗](https://leetcode.com/problems/rotate-list/) · Code: [RotateList.java](RotateList.java) · Tests: [RotateListTest.java](RotateListTest.java)

## Đề bài

Xoay danh sách liên kết sang phải `k` vị trí.

```
1 → 2 → 3 → 4 → 5, k = 2   ->   4 → 5 → 1 → 2 → 3
```

## Ý tưởng

> 1. Đếm độ dài `n`, tìm node cuối.
> 2. Nối node cuối về `head` → thành **vòng**.
> 3. Đi tiếp `n − k % n` bước từ node cuối → tới **đuôi mới**; `head` mới là node sau nó. Cắt vòng.

```
vòng: 1 → 2 → 3 → 4 → 5 → (1)
n = 5, k = 2: từ 5 đi 3 bước → 3 là đuôi mới → head mới = 4
```

```mermaid
flowchart LR
    A["đếm n, tìm tail"] --> B["tail.next = head"] --> C["đi n − k%n bước"] --> D["cắt: newHead = tail.next"]
```

## Lời giải `O(n) · O(1)` ⭐

`k` có thể cực lớn (tới 2·10⁹) → luôn `k % n`. Danh sách rỗng hoặc một node → trả nguyên.

## Bài liên quan

189 Rotate Array · 19 Remove Nth Node From End · 25 Reverse Nodes in k-Group.
