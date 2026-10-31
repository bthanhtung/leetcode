package WildcardMatching;

/*
 * 44. Wildcard Matching — https://leetcode.com/problems/wildcard-matching/
 * Wildcard match covering the entire string: '?' matches any single character, '*' any sequence (also empty).
 * Input: ("aa","a") -> false · ("aa","*") -> true · ("cb","?a") -> false · ("adceb","*a*b") -> true
 * Explanation: README.md
 */
public class WildcardMatching {
    public static void main(String[] args) {
        System.out.println(isMatch("adceb", "*a*b"));            // true
        System.out.println(isMatchGreedy("acdcb", "a*c?b"));     // false
    }

    /*
     * Approach 1 - dp[i][j] = s[0..i) matches p[0..j).
     *   p[j-1] == '*': dp[i][j-1] (star matches empty) || dp[i-1][j] (star eats s[i-1])
     *   otherwise:     dp[i-1][j-1] && (p[j-1] == '?' || p[j-1] == s[i-1])
     * Time O(m * n).
     */
    public static boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for (int j = 1; j <= n; j++) dp[0][j] = dp[0][j - 1] && p.charAt(j - 1) == '*';
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char c = p.charAt(j - 1);
                if (c == '*') dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                else dp[i][j] = dp[i - 1][j - 1] && (c == '?' || c == s.charAt(i - 1));
            }
        }
        return dp[m][n];
    }

    /*
     * Approach 2 - greedy: remember the last '*' and the string position it started matching from. On a mismatch,
     * let that star swallow one more character and retry. O(1) space, O(m * n) worst case, linear in practice.
     */
    public static boolean isMatchGreedy(String s, String p) {
        int i = 0, j = 0, star = -1, mark = 0;
        while (i < s.length()) {
            if (j < p.length() && (p.charAt(j) == '?' || p.charAt(j) == s.charAt(i))) {
                i++;
                j++;
            } else if (j < p.length() && p.charAt(j) == '*') {
                star = j++;
                mark = i;
            } else if (star >= 0) {
                j = star + 1;
                i = ++mark;
            } else {
                return false;
            }
        }
        while (j < p.length() && p.charAt(j) == '*') j++;
        return j == p.length();
    }
}
