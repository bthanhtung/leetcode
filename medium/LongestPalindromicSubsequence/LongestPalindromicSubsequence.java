package LongestPalindromicSubsequence;

/*
 * 516. Longest Palindromic Subsequence — https://leetcode.com/problems/longest-palindromic-subsequence/
 * Return the length of the longest palindromic subsequence of s (characters kept in order, not necessarily contiguous).
 * Input: "bbbab" -> 4 ("bbbb") · "cbbd" -> 2 ("bb")
 * Explanation: README.md
 */
public class LongestPalindromicSubsequence {
    public static void main(String[] args) {
        System.out.println(longestPalindromeSubseq("bbbab"));      // 4
        System.out.println(longestPalindromeSubseqLcs("cbbd"));    // 2
    }

    /* Approach 1 - lps(i, j): equal ends -> 2 + lps(i+1, j-1), else max(lps(i+1, j), lps(i, j-1)). Memoized O(n^2). */
    public static int longestPalindromeSubseqMemo(String s) {
        return lps(s, 0, s.length() - 1, new int[s.length()][s.length()]);
    }

    private static int lps(String s, int i, int j, int[][] memo) {
        if (i > j) return 0;
        if (i == j) return 1;
        if (memo[i][j] == 0) {
            memo[i][j] = s.charAt(i) == s.charAt(j)
                    ? 2 + lps(s, i + 1, j - 1, memo)
                    : Math.max(lps(s, i + 1, j, memo), lps(s, i, j - 1, memo));
        }
        return memo[i][j];
    }

    /* Approach 2 - bottom-up table over increasing interval lengths (i from the end). Time O(n^2), space O(n^2). */
    public static int longestPalindromeSubseq(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            dp[i][i] = 1;
            for (int j = i + 1; j < n; j++) {
                dp[i][j] = s.charAt(i) == s.charAt(j) ? dp[i + 1][j - 1] + 2 : Math.max(dp[i + 1][j], dp[i][j - 1]);
            }
        }
        return dp[0][n - 1];
    }

    /* Approach 3 - a palindromic subsequence is a common subsequence of s and reverse(s): LCS with one rolling row. */
    public static int longestPalindromeSubseqLcs(String s) {
        String t = new StringBuilder(s).reverse().toString();
        int n = s.length();
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int diagonal = 0;                                  // dp[i-1][j-1]
            for (int j = 1; j <= n; j++) {
                int above = dp[j];
                dp[j] = s.charAt(i - 1) == t.charAt(j - 1) ? diagonal + 1 : Math.max(dp[j], dp[j - 1]);
                diagonal = above;
            }
        }
        return dp[n];
    }
}
