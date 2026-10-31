package ScrambleString;

import java.util.HashMap;
import java.util.Map;

/*
 * 87. Scramble String — https://leetcode.com/problems/scramble-string/
 * Scramble s: split it into two non-empty parts, optionally swap them, and scramble each part recursively.
 * Given s1 and s2 of equal length, return whether s2 is a scrambled string of s1.
 * Input: ("great","rgeat") -> true · ("abcde","caebd") -> false · ("a","a") -> true
 * Explanation: README.md
 */
public class ScrambleString {
    public static void main(String[] args) {
        System.out.println(isScramble("great", "rgeat"));         // true
        System.out.println(isScrambleDp("abcde", "caebd"));       // false
    }

    /* Approach 1 - try every split point, with and without the swap; prune by letter counts; memoize on (s1, s2). */
    public static boolean isScramble(String s1, String s2) {
        return scramble(s1, s2, new HashMap<>());
    }

    private static boolean scramble(String a, String b, Map<String, Boolean> memo) {
        if (a.equals(b)) return true;
        String key = a + "#" + b;
        Boolean known = memo.get(key);
        if (known != null) return known;
        int n = a.length();
        int[] count = new int[26];
        for (int i = 0; i < n; i++) {
            count[a.charAt(i) - 'a']++;
            count[b.charAt(i) - 'a']--;
        }
        boolean result = false;
        for (int c : count) if (c != 0) { memo.put(key, false); return false; }
        for (int i = 1; i < n && !result; i++) {
            result = (scramble(a.substring(0, i), b.substring(0, i), memo) && scramble(a.substring(i), b.substring(i), memo))
                    || (scramble(a.substring(0, i), b.substring(n - i), memo) && scramble(a.substring(i), b.substring(0, n - i), memo));
        }
        memo.put(key, result);
        return result;
    }

    /*
     * Approach 2 - dp[len][i][j] = s1.substring(i, i+len) scrambles to s2.substring(j, j+len). Build by length.
     * Time O(n^4), space O(n^3).
     */
    public static boolean isScrambleDp(String s1, String s2) {
        int n = s1.length();
        boolean[][][] dp = new boolean[n + 1][n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) dp[1][i][j] = s1.charAt(i) == s2.charAt(j);
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len <= n; i++) {
                for (int j = 0; j + len <= n; j++) {
                    for (int k = 1; k < len && !dp[len][i][j]; k++) {
                        dp[len][i][j] = (dp[k][i][j] && dp[len - k][i + k][j + k])               // no swap
                                || (dp[k][i][j + len - k] && dp[len - k][i + k][j]);             // swapped
                    }
                }
            }
        }
        return dp[n][0][0];
    }
}
