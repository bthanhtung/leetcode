package LongestIncreasingPathInAMatrix;

import java.util.ArrayDeque;
import java.util.Queue;

/*
 * 329. Longest Increasing Path in a Matrix — https://leetcode.com/problems/longest-increasing-path-in-a-matrix/
 * Return the length of the longest strictly increasing path in a matrix, moving in 4 directions.
 * Input: [[9,9,4],[6,6,8],[2,1,1]] -> 4 (1→2→6→9) · [[3,4,5],[3,2,6],[2,2,1]] -> 4 (3→4→5→6)
 * Explanation: README.md
 */
public class LongestIncreasingPathInAMatrix {
    private static final int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public static void main(String[] args) {
        System.out.println(longestIncreasingPath(new int[][]{{9, 9, 4}, {6, 6, 8}, {2, 1, 1}}));       // 4
        System.out.println(longestIncreasingPathTopo(new int[][]{{3, 4, 5}, {3, 2, 6}, {2, 2, 1}}));   // 4
    }

    /*
     * Approach 1 - longest(cell) = 1 + max longest(bigger neighbour). Strictly increasing means no cycles, so plain
     * memoization works (no visited set needed). Time O(m n).
     */
    public static int longestIncreasingPath(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length, best = 0;
        int[][] memo = new int[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) best = Math.max(best, dfs(matrix, i, j, memo));
        return best;
    }

    private static int dfs(int[][] a, int i, int j, int[][] memo) {
        if (memo[i][j] > 0) return memo[i][j];
        int best = 1;
        for (int[] d : DIRS) {
            int r = i + d[0], c = j + d[1];
            if (r >= 0 && c >= 0 && r < a.length && c < a[0].length && a[r][c] > a[i][j]) {
                best = Math.max(best, 1 + dfs(a, r, c, memo));
            }
        }
        return memo[i][j] = best;
    }

    /*
     * Approach 2 - topological peeling: repeatedly remove the cells that have no bigger neighbour left (local maxima),
     * layer by layer; the number of layers is the length of the longest path. Time O(m n), no recursion.
     */
    public static int longestIncreasingPathTopo(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[][] bigger = new int[m][n];                      // out-degree: neighbours with a bigger value
        Queue<int[]> queue = new ArrayDeque<>();
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                for (int[] d : DIRS) {
                    int r = i + d[0], c = j + d[1];
                    if (r >= 0 && c >= 0 && r < m && c < n && matrix[r][c] > matrix[i][j]) bigger[i][j]++;
                }
                if (bigger[i][j] == 0) queue.add(new int[]{i, j});
            }
        int layers = 0;
        while (!queue.isEmpty()) {
            layers++;
            for (int size = queue.size(); size > 0; size--) {
                int[] cell = queue.poll();
                for (int[] d : DIRS) {
                    int r = cell[0] + d[0], c = cell[1] + d[1];
                    if (r >= 0 && c >= 0 && r < m && c < n && matrix[r][c] < matrix[cell[0]][cell[1]] && --bigger[r][c] == 0) {
                        queue.add(new int[]{r, c});
                    }
                }
            }
        }
        return layers;
    }
}
