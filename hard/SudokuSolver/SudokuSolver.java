package SudokuSolver;

/*
 * 37. Sudoku Solver — https://leetcode.com/problems/sudoku-solver/
 * Solve a 9x9 Sudoku in place ('.' = empty). Each row, column and 3x3 box must contain 1-9 exactly once.
 * The input has exactly one solution.
 * Explanation: README.md
 */
public class SudokuSolver {
    public static void main(String[] args) {
        char[][] board = parse("53..7....", "6..195...", ".98....6.", "8...6...3", "4..8.3..1", "7...2...6", ".6....28.", "...419..5", "....8..79");
        solveSudoku(board);
        for (char[] row : board) System.out.println(new String(row));
    }

    public static char[][] parse(String... rows) {
        char[][] b = new char[9][];
        for (int i = 0; i < 9; i++) b[i] = rows[i].toCharArray();
        return b;
    }

    /* Approach 1 - try 1-9 in the first empty cell, check row/column/box by scanning, recurse, undo on failure. */
    public static void solveSudokuSimple(char[][] board) {
        fill(board, 0);
    }

    private static boolean fill(char[][] b, int pos) {
        if (pos == 81) return true;
        int r = pos / 9, c = pos % 9;
        if (b[r][c] != '.') return fill(b, pos + 1);
        for (char d = '1'; d <= '9'; d++) {
            if (!canPlace(b, r, c, d)) continue;
            b[r][c] = d;
            if (fill(b, pos + 1)) return true;
            b[r][c] = '.';
        }
        return false;
    }

    private static boolean canPlace(char[][] b, int r, int c, char d) {
        int br = r / 3 * 3, bc = c / 3 * 3;
        for (int i = 0; i < 9; i++) {
            if (b[r][i] == d || b[i][c] == d || b[br + i / 3][bc + i % 3] == d) return false;
        }
        return true;
    }

    /*
     * Approach 2 - bitmasks of used digits per row, column and box make checks O(1); at each step fill the empty cell
     * with the fewest candidates first (most constrained), which prunes the search tree dramatically.
     */
    public static void solveSudoku(char[][] board) {
        int[] rows = new int[9], cols = new int[9], boxes = new int[9];
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                if (board[r][c] != '.') {
                    int bit = 1 << (board[r][c] - '1');
                    rows[r] |= bit;
                    cols[c] |= bit;
                    boxes[r / 3 * 3 + c / 3] |= bit;
                }
        search(board, rows, cols, boxes);
    }

    private static boolean search(char[][] b, int[] rows, int[] cols, int[] boxes) {
        int bestR = -1, bestC = -1, bestOptions = 0, fewest = 10;
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (b[r][c] != '.') continue;
                int options = ~(rows[r] | cols[c] | boxes[r / 3 * 3 + c / 3]) & 0x1FF;
                int count = Integer.bitCount(options);
                if (count < fewest) {
                    fewest = count;
                    bestR = r;
                    bestC = c;
                    bestOptions = options;
                }
            }
        }
        if (bestR < 0) return true;                                // no empty cell left
        int box = bestR / 3 * 3 + bestC / 3;
        for (int options = bestOptions; options != 0; options &= options - 1) {
            int bit = options & -options;
            b[bestR][bestC] = (char) ('1' + Integer.numberOfTrailingZeros(bit));
            rows[bestR] |= bit;
            cols[bestC] |= bit;
            boxes[box] |= bit;
            if (search(b, rows, cols, boxes)) return true;
            rows[bestR] ^= bit;
            cols[bestC] ^= bit;
            boxes[box] ^= bit;
        }
        b[bestR][bestC] = '.';
        return false;
    }
}
