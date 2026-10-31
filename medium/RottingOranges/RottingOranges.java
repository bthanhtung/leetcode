package RottingOranges;

import java.util.ArrayDeque;
import java.util.Queue;

/*
 * 994. Rotting Oranges — https://leetcode.com/problems/rotting-oranges/
 * 0 = empty, 1 = fresh orange, 2 = rotten. Every minute, fresh oranges next to a rotten one (4 directions) rot.
 * Return the minutes until no fresh orange remains, or -1 if impossible.
 * Input: [[2,1,1],[1,1,0],[0,1,1]] -> 4 · [[2,1,1],[0,1,1],[1,0,1]] -> -1 · [[0,2]] -> 0
 * Explanation: README.md
 */
public class RottingOranges {
    public static void main(String[] args) {
        System.out.println(orangesRotting(new int[][]{{2, 1, 1}, {1, 1, 0}, {0, 1, 1}}));           // 4
        System.out.println(orangesRottingSimulation(new int[][]{{2, 1, 1}, {0, 1, 1}, {1, 0, 1}})); // -1
    }

    /* Approach 1 - BFS starting from all rotten oranges at once; each BFS level is one minute. Time O(m * n). */
    public static int orangesRotting(int[][] grid) {
        int m = grid.length, n = grid[0].length, fresh = 0;
        int[][] g = new int[m][];
        Queue<int[]> queue = new ArrayDeque<>();
        for (int i = 0; i < m; i++) {
            g[i] = grid[i].clone();
            for (int j = 0; j < n; j++) {
                if (g[i][j] == 2) queue.add(new int[]{i, j});
                else if (g[i][j] == 1) fresh++;
            }
        }
        int minutes = 0;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        while (fresh > 0 && !queue.isEmpty()) {
            minutes++;
            for (int size = queue.size(); size > 0; size--) {
                int[] c = queue.poll();
                for (int[] d : dirs) {
                    int r = c[0] + d[0], col = c[1] + d[1];
                    if (r >= 0 && r < m && col >= 0 && col < n && g[r][col] == 1) {
                        g[r][col] = 2;
                        fresh--;
                        queue.add(new int[]{r, col});
                    }
                }
            }
        }
        return fresh == 0 ? minutes : -1;
    }

    /* Approach 2 - simulate minute by minute over the whole grid until nothing changes. Time O((m * n)^2) worst. */
    public static int orangesRottingSimulation(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] g = new int[m][];
        for (int i = 0; i < m; i++) g[i] = grid[i].clone();
        for (int minutes = 0; ; minutes++) {
            int[][] next = new int[m][];
            for (int i = 0; i < m; i++) next[i] = g[i].clone();
            boolean changed = false, anyFresh = false;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (g[i][j] != 1) continue;
                    anyFresh = true;
                    if ((i > 0 && g[i - 1][j] == 2) || (i + 1 < m && g[i + 1][j] == 2)
                            || (j > 0 && g[i][j - 1] == 2) || (j + 1 < n && g[i][j + 1] == 2)) {
                        next[i][j] = 2;
                        changed = true;
                    }
                }
            }
            if (!anyFresh) return minutes;
            if (!changed) return -1;
            g = next;
        }
    }
}
