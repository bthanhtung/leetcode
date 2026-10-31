package RegularExpressionMatching;

/*
 * 10. Regular Expression Matching — https://leetcode.com/problems/regular-expression-matching/
 * Implement matching with '.' (any single character) and '*' (zero or more of the preceding element). The match must
 * cover the entire string.
 * Input: ("aa","a") -> false · ("aa","a*") -> true · ("ab",".*") -> true · ("aab","c*a*b") -> true
 * Explanation: README.md
 */
public class RegularExpressionMatching {
    public static void main(String[] args) {
        System.out.println(isMatch("aab", "c*a*b"));              // true
        System.out.println(isMatchRecursive("aa", "a"));         // false
        System.out.println(isMatchMemo("mississippi", "mis*is*p*."));   // false
    }

    /* Approach 1 - plain recursion on (text, pattern). Exponential in the worst case. */
    public static boolean isMatchRecursive(String s, String p) {
        if (p.isEmpty()) {
            return s.isEmpty();
        }
        boolean first = !s.isEmpty() && (p.charAt(0) == s.charAt(0) || p.charAt(0) == '.');
        if (p.length() >= 2 && p.charAt(1) == '*') {
            return isMatchRecursive(s, p.substring(2))                  // x* matches nothing
                    || (first && isMatchRecursive(s.substring(1), p));  // x* eats one more character
        }
        return first && isMatchRecursive(s.substring(1), p.substring(1));
    }

    /* Approach 2 - the same recursion on indexes, memoized. Time O(m * n), space O(m * n). */
    public static boolean isMatchMemo(String s, String p) {
        return match(s, p, 0, 0, new Boolean[s.length() + 1][p.length() + 1]);
    }

    private static boolean match(String s, String p, int i, int j, Boolean[][] memo) {
        if (memo[i][j] != null) {
            return memo[i][j];
        }
        boolean result;
        if (j == p.length()) {
            result = i == s.length();
        } else {
            boolean first = i < s.length() && (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.');
            if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
                result = match(s, p, i, j + 2, memo) || (first && match(s, p, i + 1, j, memo));
            } else {
                result = first && match(s, p, i + 1, j + 1, memo);
            }
        }
        return memo[i][j] = result;
    }

    /*
     * Approach 3 - bottom-up DP: dp[i][j] = does s[0..i) match p[0..j)?
     *   p[j-1] is a letter or '.': dp[i][j] = dp[i-1][j-1] && s[i-1] matches p[j-1]
     *   p[j-1] is '*':             dp[i][j] = dp[i][j-2]                                   (zero copies)
     *                                     || (dp[i-1][j] && s[i-1] matches p[j-2])         (one more copy)
     * Time O(m * n), space O(m * n).
     */
    public static boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for (int j = 2; j <= n; j++) {
            dp[0][j] = p.charAt(j - 1) == '*' && dp[0][j - 2];     // "a*b*" can match the empty string
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char pc = p.charAt(j - 1);
                if (pc == '*') {
                    char prev = p.charAt(j - 2);
                    dp[i][j] = dp[i][j - 2] || (dp[i - 1][j] && (prev == '.' || prev == s.charAt(i - 1)));
                } else {
                    dp[i][j] = dp[i - 1][j - 1] && (pc == '.' || pc == s.charAt(i - 1));
                }
            }
        }
        return dp[m][n];
    }
}
