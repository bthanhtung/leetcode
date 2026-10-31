package LongestCommonSubsequence;

/*
 * 1143. Longest Common Subsequence — https://leetcode.com/problems/longest-common-subsequence/
 * Return the length of the longest common subsequence of text1 and text2 (characters in order, not necessarily
 * contiguous), or 0.
 * Input: ("abcde","ace") -> 3 · ("abc","abc") -> 3 · ("abc","def") -> 0
 * Explanation: README.md
 */
public class LongestCommonSubsequence {
    public static void main(String[] args) {
        System.out.println(longestCommonSubsequence("abcde", "ace"));       // 3
        System.out.println(longestCommonSubsequenceTable("abc", "def"));    // 0
    }

    /* Approach 1 - lcs(i, j) on suffixes: equal heads -> 1 + lcs(i+1, j+1), else max of skipping one. Memoized. */
    public static int longestCommonSubsequenceMemo(String a, String b) {
        return lcs(a, b, 0, 0, new Integer[a.length()][b.length()]);
    }

    private static int lcs(String a, String b, int i, int j, Integer[][] memo) {
        if (i == a.length() || j == b.length()) return 0;
        if (memo[i][j] == null) {
            memo[i][j] = a.charAt(i) == b.charAt(j)
                    ? 1 + lcs(a, b, i + 1, j + 1, memo)
                    : Math.max(lcs(a, b, i + 1, j, memo), lcs(a, b, i, j + 1, memo));
        }
        return memo[i][j];
    }

    /* Approach 2 - dp[i][j] = LCS of the prefixes a[0..i) and b[0..j). Time O(m * n), space O(m * n). */
    public static int longestCommonSubsequenceTable(String a, String b) {
        int m = a.length(), n = b.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = a.charAt(i - 1) == b.charAt(j - 1) ? dp[i - 1][j - 1] + 1 : Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        return dp[m][n];
    }

    /* Approach 3 - one row plus a variable for the diagonal. Space O(min(m, n)). */
    public static int longestCommonSubsequence(String a, String b) {
        if (b.length() > a.length()) return longestCommonSubsequence(b, a);
        int[] dp = new int[b.length() + 1];
        for (int i = 1; i <= a.length(); i++) {
            int diagonal = 0;                                 // dp[i-1][j-1]
            for (int j = 1; j <= b.length(); j++) {
                int above = dp[j];
                dp[j] = a.charAt(i - 1) == b.charAt(j - 1) ? diagonal + 1 : Math.max(dp[j], dp[j - 1]);
                diagonal = above;
            }
        }
        return dp[b.length()];
    }
}
