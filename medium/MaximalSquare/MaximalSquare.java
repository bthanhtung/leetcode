package MaximalSquare;

/*
 * 221. Maximal Square — https://leetcode.com/problems/maximal-square/
 * Find the largest square containing only '1's in a binary matrix and return its area.
 * Input: [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]] -> 4
 * Explanation: README.md
 */
public class MaximalSquare {
    public static void main(String[] args) {
        char[][] m = {"10100".toCharArray(), "10111".toCharArray(), "11111".toCharArray(), "10010".toCharArray()};
        System.out.println(maximalSquare(m));      // 4
    }

    /*
     * Approach 1 - dp[i][j] = side of the largest all-1 square whose bottom-right corner is (i, j)
     *            = 1 + min(dp[up], dp[left], dp[up-left]) when the cell is '1'. Time O(m * n).
     */
    public static int maximalSquare(char[][] matrix) {
        int m = matrix.length, n = matrix[0].length, best = 0;
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (matrix[i - 1][j - 1] == '1') {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
                    best = Math.max(best, dp[i][j]);
                }
            }
        }
        return best * best;
    }

    /* Approach 2 - the same recurrence with one row; `diag` remembers dp[i-1][j-1] before it is overwritten. O(n) space. */
    public static int maximalSquareRow(char[][] matrix) {
        int n = matrix[0].length, best = 0;
        int[] dp = new int[n + 1];
        for (char[] row : matrix) {
            int diag = 0;
            for (int j = 1; j <= n; j++) {
                int up = dp[j];
                dp[j] = row[j - 1] == '1' ? 1 + Math.min(diag, Math.min(up, dp[j - 1])) : 0;
                best = Math.max(best, dp[j]);
                diag = up;
            }
        }
        return best * best;
    }
}
