<!-- approaches: Head-insertion in one pass · Recursive reverse of the first k -->
# 92. Reverse Linked List II

**Medium** · Linked List · [LeetCode ↗](https://leetcode.com/problems/reverse-linked-list-ii/) · Code: [ReverseLinkedListII.java](ReverseLinkedListII.java) · Tests: [ReverseLinkedListIITest.java](ReverseLinkedListIITest.java)

## Đề bài

Đảo ngược đoạn từ vị trí `left` tới `right` (đánh số từ 1). Follow-up: một lượt.

```
1 → 2 → 3 → 4 → 5,  left = 2, right = 4   ->   1 → 4 → 3 → 2 → 5
```

## Ý tưởng — "chèn lên đầu đoạn"

> Dừng ở `pre` = node **ngay trước** đoạn. `start` = node đầu đoạn (sẽ thành node cuối đoạn sau khi đảo).
> Lặp `right − left` lần: lấy node **ngay sau `start`** và chèn nó vào **ngay sau `pre`**.

```
pre=1, start=2
1 → 2 → 3 → 4 → 5
1 → 3 → 2 → 4 → 5     (chuyển 3 lên đầu đoạn)
1 → 4 → 3 → 2 → 5     (chuyển 4 lên đầu đoạn)
```

```mermaid
flowchart LR
    A["move = start.next"] --> B["start.next = move.next"] --> C["move.next = pre.next"] --> D["pre.next = move"]
```

## Cách 1 — Chèn lên đầu, một lượt `O(n) · O(1)` ⭐

Thứ tự bốn phép gán quan trọng: phải "gỡ" `move` ra (`start.next = move.next`) trước khi chèn.

## Cách 2 — Đệ quy

Giảm `left` về 1 bằng đệ quy, rồi đảo `k` node đầu tiên, nhớ node `successor` ngay sau đoạn để nối lại.

## Bài liên quan

206 Reverse Linked List · 25 Reverse Nodes in k-Group · 24 Swap Nodes in Pairs.
