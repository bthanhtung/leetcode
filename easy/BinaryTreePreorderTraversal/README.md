<!-- approaches: Recursive · Iterative stack · Morris -->
# 144. Binary Tree Preorder Traversal

**Easy** · Stack · Tree · Depth-First Search · Binary Tree · [LeetCode ↗](https://leetcode.com/problems/binary-tree-preorder-traversal/) · Code: [BinaryTreePreorderTraversal.java](BinaryTreePreorderTraversal.java) · Tests: [BinaryTreePreorderTraversalTest.java](BinaryTreePreorderTraversalTest.java)

## Đề bài

Duyệt cây nhị phân theo thứ tự **trước** (preorder): gốc → trái → phải.

```
    1
     \
      2        ->  [1, 2, 3]
     /
    3
```

## Ý tưởng

> Preorder ghi node **ngay khi tới**, trước khi đi xuống con. Đây là thứ tự để **sao chép / tuần tự hoá** một cây (297) vì gốc luôn đứng trước các con.

## Cách 1 — Đệ quy

## Cách 2 — Stack `O(n) · O(h)` ⭐

Lấy ra → ghi → đẩy **phải trước, trái sau** (để trái được lấy ra trước).

```mermaid
flowchart LR
    A["pop 1, push 3, 2"] --> B["pop 2, push 5, 4"] --> C["pop 4"] --> D["pop 5 ..."] --> E["pop 3 ..."]
```

## Cách 3 — Morris `O(1)` bộ nhớ

Giống Morris inorder (bài 94), chỉ khác **thời điểm ghi**: ghi node lúc **tạo** sợi nối (lần đầu tới), thay vì lúc gỡ.

## Bài liên quan

94 Inorder · 145 Postorder · 589 N-ary Tree Preorder · 105 Construct Binary Tree from Preorder and Inorder · 297 Serialize and Deserialize Binary Tree.
