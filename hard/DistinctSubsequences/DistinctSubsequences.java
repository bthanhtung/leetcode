package DistinctSubsequences;

/*
 * 115. Distinct Subsequences — https://leetcode.com/problems/distinct-subsequences/
 * Return the number of distinct subsequences of s that equal t (counted by positions).
 * Input: ("rabbbit","rabbit") -> 3 · ("babgbag","bag") -> 5
 * Explanation: README.md
 */
public class DistinctSubsequences {
    public static void main(String[] args) {
        System.out.println(numDistinct("rabbbit", "rabbit"));         // 3
        System.out.println(numDistinctTable("babgbag", "bag"));       // 5
    }

    /* Approach 1 - ways(i, j): s[i..] forming t[j..]. Skip s[i], or (if equal) match it with t[j]. Memoized. */
    public static int numDistinctMemo(String s, String t) {
        return ways(s, t, 0, 0, new Integer[s.length()][t.length()]);
    }

    private static int ways(String s, String t, int i, int j, Integer[][] memo) {
        if (j == t.length()) return 1;
        if (s.length() - i < t.length() - j) return 0;
        if (memo[i][j] == null) {
            int count = ways(s, t, i + 1, j, memo);
            if (s.charAt(i) == t.charAt(j)) count += ways(s, t, i + 1, j + 1, memo);
            memo[i][j] = count;
        }
        return memo[i][j];
    }

    /* Approach 2 - dp[i][j] = ways for prefixes s[0..i) and t[0..j). Time O(m * n), space O(m * n). */
    public static int numDistinctTable(String s, String t) {
        int m = s.length(), n = t.length();
        long[][] dp = new long[m + 1][n + 1];
        for (int i = 0; i <= m; i++) dp[i][0] = 1;                // the empty t is formed exactly once
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = dp[i - 1][j];
                if (s.charAt(i - 1) == t.charAt(j - 1)) dp[i][j] += dp[i - 1][j - 1];
            }
        }
        return (int) dp[m][n];
    }

    /* Approach 3 - one row; walk j from right to left so dp[j - 1] still belongs to the previous character of s. */
    public static int numDistinct(String s, String t) {
        int n = t.length();
        long[] dp = new long[n + 1];
        dp[0] = 1;
        for (char c : s.toCharArray()) {
            for (int j = n; j >= 1; j--) {
                if (c == t.charAt(j - 1)) dp[j] += dp[j - 1];
            }
        }
        return (int) dp[n];
    }
}
