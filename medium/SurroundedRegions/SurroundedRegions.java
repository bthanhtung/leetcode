package SurroundedRegions;

import java.util.ArrayDeque;
import java.util.Deque;

/*
 * 130. Surrounded Regions — https://leetcode.com/problems/surrounded-regions/
 * Capture every region of 'O' that is fully surrounded by 'X' (not connected to the border) by flipping it to 'X'.
 * Input: [["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]
 *     -> [["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]
 * Explanation: README.md
 */
public class SurroundedRegions {
    public static void main(String[] args) {
        char[][] b = {"XXXX".toCharArray(), "XOOX".toCharArray(), "XXOX".toCharArray(), "XOXX".toCharArray()};
        solve(b);
        for (char[] row : b) System.out.println(new String(row));
    }

    /*
     * Instead of finding surrounded regions, find the safe ones: every 'O' reachable from a border 'O'. Mark them '#'
     * (iterative flood fill, no recursion depth issues), then flip remaining 'O' -> 'X' and '#' -> 'O'. O(m * n).
     */
    public static void solve(char[][] board) {
        int m = board.length, n = board[0].length;
        Deque<int[]> stack = new ArrayDeque<>();
        for (int i = 0; i < m; i++) {
            stack.push(new int[]{i, 0});
            stack.push(new int[]{i, n - 1});
        }
        for (int j = 0; j < n; j++) {
            stack.push(new int[]{0, j});
            stack.push(new int[]{m - 1, j});
        }
        while (!stack.isEmpty()) {
            int[] c = stack.pop();
            int i = c[0], j = c[1];
            if (i < 0 || j < 0 || i >= m || j >= n || board[i][j] != 'O') continue;
            board[i][j] = '#';
            stack.push(new int[]{i + 1, j});
            stack.push(new int[]{i - 1, j});
            stack.push(new int[]{i, j + 1});
            stack.push(new int[]{i, j - 1});
        }
        for (char[] row : board) {
            for (int j = 0; j < n; j++) row[j] = row[j] == '#' ? 'O' : 'X';
        }
    }
}
