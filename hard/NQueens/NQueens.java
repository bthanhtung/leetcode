package NQueens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * 51. N-Queens — https://leetcode.com/problems/n-queens/
 * Return all distinct solutions to the n-queens puzzle, each board as strings with 'Q' and '.'.
 * Input: 4 -> [[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]] · 1 -> [["Q"]]
 * Explanation: README.md
 */
public class NQueens {
    public static void main(String[] args) {
        System.out.println(solveNQueens(4));
    }

    /* Approach 1 - backtracking row by row with boolean arrays; queens[r] = column of the queen in row r. */
    public static List<List<String>> solveNQueens(int n) {
        List<List<String>> out = new ArrayList<>();
        place(0, n, new int[n], new boolean[n], new boolean[2 * n], new boolean[2 * n], out);
        return out;
    }

    private static void place(int row, int n, int[] queens, boolean[] cols, boolean[] diag, boolean[] anti, List<List<String>> out) {
        if (row == n) {
            out.add(draw(queens));
            return;
        }
        for (int c = 0; c < n; c++) {
            int d = row - c + n, a = row + c;
            if (cols[c] || diag[d] || anti[a]) continue;
            queens[row] = c;
            cols[c] = diag[d] = anti[a] = true;
            place(row + 1, n, queens, cols, diag, anti, out);
            cols[c] = diag[d] = anti[a] = false;
        }
    }

    /* Approach 2 - the same search with bitmasks for the three kinds of attacks. */
    public static List<List<String>> solveNQueensBitmask(int n) {
        List<List<String>> out = new ArrayList<>();
        solve(n, 0, (1 << n) - 1, 0, 0, 0, new int[n], out);
        return out;
    }

    private static void solve(int n, int row, int full, int cols, int ld, int rd, int[] queens, List<List<String>> out) {
        if (row == n) {
            out.add(draw(queens));
            return;
        }
        for (int available = ~(cols | ld | rd) & full; available != 0; ) {
            int bit = available & -available;
            available -= bit;
            queens[row] = Integer.numberOfTrailingZeros(bit);
            solve(n, row + 1, full, cols | bit, (ld | bit) << 1 & full, (rd | bit) >> 1, queens, out);
        }
    }

    private static List<String> draw(int[] queens) {
        List<String> board = new ArrayList<>();
        for (int c : queens) {
            char[] row = new char[queens.length];
            Arrays.fill(row, '.');
            row[c] = 'Q';
            board.add(new String(row));
        }
        return board;
    }
}
