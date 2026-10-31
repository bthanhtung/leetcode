<!-- approaches: Backtracking with column/diagonal sets · Backtracking with bitmasks -->
# 51. N-Queens

**Hard** · Array · Backtracking · Algorithm X · [LeetCode ↗](https://leetcode.com/problems/n-queens/) · Code: [NQueens.java](NQueens.java) · Tests: [NQueensTest.java](NQueensTest.java)

## Đề bài

Liệt kê **mọi** cách đặt `n` quân hậu không ăn nhau, mỗi lời giải là một bàn cờ dạng chuỗi.

```
n = 4:   . Q . .        . . Q .
         . . . Q        Q . . .
         Q . . .        . . . Q
         . . Q .        . Q . .
```

## Ý tưởng

> Giống 52 N-Queens II, nhưng thay vì **đếm** thì **ghi lại** vị trí: `queens[r]` = cột của hậu ở hàng `r`. Tới hàng cuối thì vẽ bàn cờ từ mảng này.

```mermaid
flowchart LR
    A["queens = [1, 3, 0, 2]"] --> B[".Q.. / ...Q / Q... / ..Q."]
```

Quy tắc khống chế (như bài 52):

| loại | ô (r, c) cùng nhóm khi |
|---|---|
| cột | `c` bằng nhau |
| chéo chính ↘ | `r − c` bằng nhau |
| chéo phụ ↙ | `r + c` bằng nhau |

## Cách 1 — Backtracking + mảng boolean ⭐

## Cách 2 — Bitmask

`Integer.numberOfTrailingZeros(bit)` đổi bit thành số cột để vẽ bàn cờ.

## Độ phức tạp

Không có công thức chính xác; với cắt tỉa, số node của cây tìm kiếm nhỏ hơn rất nhiều so với `n!`. Số lời giải tăng nhanh: `n = 8` có 92, `n = 12` có 14 200.

## Bài liên quan

52 N-Queens II · 37 Sudoku Solver · 46 Permutations (mỗi lời giải N-Queens là một hoán vị của các cột).
