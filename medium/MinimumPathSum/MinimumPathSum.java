package MinimumPathSum;

/*
 * 64. Minimum Path Sum — https://leetcode.com/problems/minimum-path-sum/
 * Given an m x n grid of non-negative numbers, find a path from top-left to bottom-right (moving only right or down)
 * that minimizes the sum of the numbers along it.
 * Input: [[1,3,1],[1,5,1],[4,2,1]] -> 7 (1→3→1→1→1) · [[1,2,3],[4,5,6]] -> 12
 * Explanation: README.md
 */
public class MinimumPathSum {
    public static void main(String[] args) {
        System.out.println(minPathSum(new int[][]{{1, 3, 1}, {1, 5, 1}, {4, 2, 1}}));      // 7
        System.out.println(minPathSumTable(new int[][]{{1, 2, 3}, {4, 5, 6}}));          // 12
    }

    /* Approach 1 - best(i, j) = grid[i][j] + min(best(i-1, j), best(i, j-1)), memoized. Time O(m * n). */
    public static int minPathSumMemo(int[][] grid) {
        return best(grid, grid.length - 1, grid[0].length - 1, new Integer[grid.length][grid[0].length]);
    }

    private static int best(int[][] g, int i, int j, Integer[][] memo) {
        if (i == 0 && j == 0) return g[0][0];
        if (i < 0 || j < 0) return Integer.MAX_VALUE;
        if (memo[i][j] == null) {
            memo[i][j] = g[i][j] + Math.min(best(g, i - 1, j, memo), best(g, i, j - 1, memo));
        }
        return memo[i][j];
    }

    /* Approach 2 - bottom-up table; first row and column have only one way in. Time O(m * n), space O(m * n). */
    public static int minPathSumTable(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) dp[i][j] = grid[0][0];
                else if (i == 0) dp[i][j] = dp[i][j - 1] + grid[i][j];
                else if (j == 0) dp[i][j] = dp[i - 1][j] + grid[i][j];
                else dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        return dp[m - 1][n - 1];
    }

    /* Approach 3 - one row is enough: dp[j] still holds the row above when we read it. Space O(n). */
    public static int minPathSum(int[][] grid) {
        int n = grid[0].length;
        int[] dp = new int[n];
        java.util.Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int[] row : grid) {
            dp[0] += row[0];
            for (int j = 1; j < n; j++) {
                dp[j] = Math.min(dp[j], dp[j - 1]) + row[j];      // dp[j] = from above, dp[j-1] = from the left
            }
        }
        return dp[n - 1];
    }
}
