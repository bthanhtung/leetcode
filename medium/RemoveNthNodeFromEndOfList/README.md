<!-- approaches: Two pointers n apart with a dummy head · Count length first -->
# 19. Remove Nth Node From End of List

**Medium** · Linked List · Two Pointers · [LeetCode ↗](https://leetcode.com/problems/remove-nth-node-from-end-of-list/) · Code: [RemoveNthNodeFromEndOfList.java](RemoveNthNodeFromEndOfList.java) · Tests: [RemoveNthNodeFromEndOfListTest.java](RemoveNthNodeFromEndOfListTest.java)

## Đề bài

Xoá node thứ `n` **tính từ cuối** danh sách liên kết. Follow-up: chỉ duyệt một lượt.

```
1 → 2 → 3 → 4 → 5,  n = 2   ->   1 → 2 → 3 → 5
```

## Ý tưởng

> Hai con trỏ **cách nhau đúng `n` bước**. Khi `fast` tới node cuối, `slow` đứng **ngay trước** node cần xoá.
> Một node giả `dummy` trước `head` giúp xoá cả node đầu mà không phải xử lý riêng.

```
dummy → 1 → 2 → 3 → 4 → 5
 slow         fast            (fast đi trước 2 bước)
              slow        fast  → slow.next = 4 bị xoá
```

```mermaid
flowchart LR
    A["fast đi trước n bước"] --> B["cùng đi tới khi fast.next == null"] --> C["slow.next = slow.next.next"]
```

## Cách 1 — Hai con trỏ, một lượt ⭐

## Cách 2 — Đếm độ dài, hai lượt

## Bài liên quan

876 Middle of the Linked List · 61 Rotate List · 1721 Swapping Nodes in a Linked List · 141 Linked List Cycle.
