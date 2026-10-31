package NQueensII;

/*
 * 52. N-Queens II — https://leetcode.com/problems/n-queens-ii/
 * Return the number of distinct ways to place n queens on an n x n board so that no two attack each other.
 * Input: 4 -> 2 · 1 -> 1 · 8 -> 92
 * Explanation: README.md
 */
public class NQueensII {
    public static void main(String[] args) {
        System.out.println(totalNQueens(8));             // 92
        System.out.println(totalNQueensArrays(4));       // 2
    }

    /* Approach 1 - one queen per row; columns and both diagonals tracked in boolean arrays. */
    public static int totalNQueensArrays(int n) {
        return place(0, n, new boolean[n], new boolean[2 * n], new boolean[2 * n]);
    }

    private static int place(int row, int n, boolean[] cols, boolean[] diag, boolean[] anti) {
        if (row == n) return 1;
        int count = 0;
        for (int c = 0; c < n; c++) {
            int d = row - c + n, a = row + c;                 // same diagonal: row - col constant; anti: row + col
            if (cols[c] || diag[d] || anti[a]) continue;
            cols[c] = diag[d] = anti[a] = true;
            count += place(row + 1, n, cols, diag, anti);
            cols[c] = diag[d] = anti[a] = false;
        }
        return count;
    }

    /*
     * Approach 2 - bitmasks: cols, left-diagonal and right-diagonal attacks as bits. For the next row the diagonal
     * attacks shift by one. available = ~(cols | ld | rd) & full; take the lowest bit each time.
     */
    public static int totalNQueens(int n) {
        return solve((1 << n) - 1, 0, 0, 0);
    }

    private static int solve(int full, int cols, int ld, int rd) {
        if (cols == full) return 1;
        int count = 0;
        for (int available = ~(cols | ld | rd) & full; available != 0; ) {
            int bit = available & -available;                 // lowest free column
            available -= bit;
            count += solve(full, cols | bit, (ld | bit) << 1 & full, (rd | bit) >> 1);
        }
        return count;
    }
}
