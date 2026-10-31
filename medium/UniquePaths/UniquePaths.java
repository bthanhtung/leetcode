package UniquePaths;

/*
 * 62. Unique Paths — https://leetcode.com/problems/unique-paths/
 * A robot on an m x n grid starts top-left and moves only right or down. How many unique paths reach bottom-right?
 * Input: m = 3, n = 7 -> 28 · m = 3, n = 2 -> 3
 * Explanation: README.md
 */
public class UniquePaths {
    public static void main(String[] args) {
        System.out.println(uniquePaths(3, 7));            // 28
        System.out.println(uniquePathsMath(3, 2));        // 3
    }

    /* Approach 1 - paths(i, j) = paths(i-1, j) + paths(i, j-1), memoized. Time O(m * n). */
    public static int uniquePathsMemo(int m, int n) {
        return paths(m - 1, n - 1, new int[m][n]);
    }

    private static int paths(int i, int j, int[][] memo) {
        if (i == 0 || j == 0) return 1;
        if (memo[i][j] == 0) memo[i][j] = paths(i - 1, j, memo) + paths(i, j - 1, memo);
        return memo[i][j];
    }

    /* Approach 2 - one row of the DP table, updated in place. Time O(m * n), space O(n). */
    public static int uniquePaths(int m, int n) {
        int[] dp = new int[n];
        java.util.Arrays.fill(dp, 1);                 // first row: one way to each cell
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[j] += dp[j - 1];                    // from above (old dp[j]) + from the left
            }
        }
        return dp[n - 1];
    }

    /* Approach 3 - any path is (m-1) downs and (n-1) rights in some order: C(m + n - 2, m - 1). Time O(min(m, n)). */
    public static int uniquePathsMath(int m, int n) {
        int k = Math.min(m, n) - 1, total = m + n - 2;
        long c = 1;
        for (int i = 1; i <= k; i++) {
            c = c * (total - k + i) / i;               // stays an integer at every step
        }
        return (int) c;
    }
}
