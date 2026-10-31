<!-- approaches: Recursive · Iterative stack -->
# 589. N-ary Tree Preorder Traversal

**Easy** · Stack · Tree · Depth-First Search · [LeetCode ↗](https://leetcode.com/problems/n-ary-tree-preorder-traversal/) · Code: [NAryTreePreorderTraversal.java](NAryTreePreorderTraversal.java) · Tests: [NAryTreePreorderTraversalTest.java](NAryTreePreorderTraversalTest.java)

## Đề bài

Duyệt **cây n-phân** (mỗi node có danh sách con) theo thứ tự trước: node, rồi lần lượt các con từ trái sang phải.

```
        1
     /  |  \
    3   2   4        ->  [1, 3, 5, 6, 2, 4]
   / \
  5   6
```

## Ý tưởng

> Giống preorder cây nhị phân, chỉ thay "trái rồi phải" bằng "**mọi con** theo thứ tự".

## Cách 1 — Đệ quy ⭐

## Cách 2 — Stack

Stack lấy ra phần tử **vào sau cùng** → đẩy các con theo thứ tự **ngược** (phải → trái) để con trái nhất được lấy ra trước.

```mermaid
flowchart LR
    A["pop 1 → push 4, 2, 3"] --> B["pop 3 → push 6, 5"] --> C["pop 5"] --> D["pop 6"] --> E["pop 2"] --> F["pop 4"]
```

## Bài liên quan

590 N-ary Tree Postorder Traversal · 429 N-ary Tree Level Order Traversal · 144 Binary Tree Preorder Traversal.
