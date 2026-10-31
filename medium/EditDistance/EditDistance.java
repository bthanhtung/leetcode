package EditDistance;

/*
 * 72. Edit Distance — https://leetcode.com/problems/edit-distance/
 * Return the minimum number of operations (insert, delete or replace a character) to turn word1 into word2.
 * Input: ("horse","ros") -> 3 · ("intention","execution") -> 5
 * Explanation: README.md
 */
public class EditDistance {
    public static void main(String[] args) {
        System.out.println(minDistance("horse", "ros"));                 // 3
        System.out.println(minDistanceMemo("intention", "execution"));   // 5
    }

    /* Approach 1 - d(i, j) for prefixes word1[0..i) and word2[0..j), memoized. Time O(m * n). */
    public static int minDistanceMemo(String a, String b) {
        return d(a, b, a.length(), b.length(), new Integer[a.length() + 1][b.length() + 1]);
    }

    private static int d(String a, String b, int i, int j, Integer[][] memo) {
        if (i == 0) return j;                        // insert the remaining j characters
        if (j == 0) return i;                        // delete the remaining i characters
        if (memo[i][j] == null) {
            memo[i][j] = a.charAt(i - 1) == b.charAt(j - 1)
                    ? d(a, b, i - 1, j - 1, memo)
                    : 1 + Math.min(d(a, b, i - 1, j - 1, memo), Math.min(d(a, b, i - 1, j, memo), d(a, b, i, j - 1, memo)));
        }
        return memo[i][j];
    }

    /* Approach 2 - bottom-up table. Time O(m * n), space O(m * n). */
    public static int minDistanceTable(String a, String b) {
        int m = a.length(), n = b.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1];
                else dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
            }
        }
        return dp[m][n];
    }

    /* Approach 3 - only the previous row is needed. Space O(n). */
    public static int minDistance(String a, String b) {
        int m = a.length(), n = b.length();
        int[] prev = new int[n + 1], cur = new int[n + 1];
        for (int j = 0; j <= n; j++) prev[j] = j;
        for (int i = 1; i <= m; i++) {
            cur[0] = i;
            for (int j = 1; j <= n; j++) {
                cur[j] = a.charAt(i - 1) == b.charAt(j - 1) ? prev[j - 1] : 1 + Math.min(prev[j - 1], Math.min(prev[j], cur[j - 1]));
            }
            int[] t = prev;
            prev = cur;
            cur = t;
        }
        return prev[n];
    }
}
