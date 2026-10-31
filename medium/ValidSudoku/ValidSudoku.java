package ValidSudoku;

import java.util.HashSet;
import java.util.Set;

/*
 * 36. Valid Sudoku — https://leetcode.com/problems/valid-sudoku/
 * Determine if a partially filled 9x9 Sudoku board is valid: each row, column and 3x3 box contains the digits 1-9
 * at most once. '.' is an empty cell. The board need not be solvable.
 * Explanation: README.md
 */
public class ValidSudoku {
    public static void main(String[] args) {
        char[][] board = {
                "53..7....".toCharArray(), "6..195...".toCharArray(), ".98....6.".toCharArray(),
                "8...6...3".toCharArray(), "4..8.3..1".toCharArray(), "7...2...6".toCharArray(),
                ".6....28.".toCharArray(), "...419..5".toCharArray(), "....8..79".toCharArray()};
        System.out.println(isValidSudoku(board));        // true
    }

    /* Approach 1 - one string key per constraint: "5 in row 0", "5 in col 3", "5 in box 0-1". */
    public static boolean isValidSudokuSet(char[][] board) {
        Set<String> seen = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char c = board[i][j];
                if (c == '.') continue;
                if (!seen.add(c + " in row " + i) || !seen.add(c + " in col " + j)
                        || !seen.add(c + " in box " + i / 3 + "-" + j / 3)) return false;
            }
        }
        return true;
    }

    /* Approach 2 - 9-bit masks for every row, column and box; bit d means digit d+1 already appears. One pass, O(1). */
    public static boolean isValidSudoku(char[][] board) {
        int[] rows = new int[9], cols = new int[9], boxes = new int[9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') continue;
                int bit = 1 << (board[i][j] - '1');
                int box = (i / 3) * 3 + j / 3;
                if ((rows[i] & bit) != 0 || (cols[j] & bit) != 0 || (boxes[box] & bit) != 0) return false;
                rows[i] |= bit;
                cols[j] |= bit;
                boxes[box] |= bit;
            }
        }
        return true;
    }
}
