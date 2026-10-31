package GameOfLife;

/*
 * 289. Game of Life — https://leetcode.com/problems/game-of-life/
 * Conway's Game of Life: each cell is live (1) or dead (0). Simultaneously, a live cell with 2 or 3 live neighbours
 * survives, a dead cell with exactly 3 becomes live, all others die/stay dead. Update the board in place to the next state.
 * Input: [[0,1,0],[0,0,1],[1,1,1],[0,0,0]] -> [[0,0,0],[1,0,1],[0,1,1],[0,1,0]]
 * Explanation: README.md
 */
public class GameOfLife {
    public static void main(String[] args) {
        int[][] board = {{0, 1, 0}, {0, 0, 1}, {1, 1, 1}, {0, 0, 0}};
        gameOfLife(board);
        System.out.println(java.util.Arrays.deepToString(board));     // [[0, 0, 0], [1, 0, 1], [0, 1, 1], [0, 1, 0]]
    }

    /* Approach 1 - read neighbours from a copy, write to the board. Time O(m * n), space O(m * n). */
    public static void gameOfLifeCopy(int[][] board) {
        int m = board.length, n = board[0].length;
        int[][] old = new int[m][];
        for (int i = 0; i < m; i++) old[i] = board[i].clone();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int live = 0;
                for (int di = -1; di <= 1; di++)
                    for (int dj = -1; dj <= 1; dj++) {
                        int r = i + di, c = j + dj;
                        if ((di != 0 || dj != 0) && r >= 0 && r < m && c >= 0 && c < n) live += old[r][c];
                    }
                board[i][j] = live == 3 || (old[i][j] == 1 && live == 2) ? 1 : 0;
            }
        }
    }

    /*
     * Approach 2 - in place: bit 0 holds the current state, bit 1 the next state. Neighbours are counted with
     * (cell & 1), so already-updated cells still report their old value. A final pass shifts right. Space O(1).
     */
    public static void gameOfLife(int[][] board) {
        int m = board.length, n = board[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int live = 0;
                for (int r = Math.max(0, i - 1); r <= Math.min(m - 1, i + 1); r++)
                    for (int c = Math.max(0, j - 1); c <= Math.min(n - 1, j + 1); c++)
                        live += board[r][c] & 1;
                live -= board[i][j] & 1;                         // the loop counted the cell itself
                if (live == 3 || ((board[i][j] & 1) == 1 && live == 2)) board[i][j] |= 2;
            }
        }
        for (int[] row : board)
            for (int j = 0; j < n; j++) row[j] >>= 1;
    }
}
