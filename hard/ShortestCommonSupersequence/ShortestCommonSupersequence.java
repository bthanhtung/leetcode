package ShortestCommonSupersequence;

/*
 * 1092. Shortest Common Supersequence  — https://leetcode.com/problems/shortest-common-supersequence/
 * Return the shortest string that has both str1 and str2 as subsequences (any one if several).
 * Input: ("abac","cab") -> "cabac" · ("aaaaaaaa","aaaaaaaa") -> "aaaaaaaa"
 * Explanation: README.md
 */
public class ShortestCommonSupersequence {
    public static void main(String[] args) {
        System.out.println(shortestCommonSupersequence("abac", "cab"));          // cabac
        System.out.println(shortestCommonSupersequenceStrings("abac", "cab"));   // a string of length 5
    }

    /*
     * Approach 1 - build the LCS table, then walk back from the end: shared characters (LCS) are written once, others
     * from whichever string the table says to step back in. Length = m + n - LCS. Time O(m * n).
     */
    public static String shortestCommonSupersequence(String a, String b) {
        int m = a.length(), n = b.length();
        int[][] lcs = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++)
                lcs[i][j] = a.charAt(i - 1) == b.charAt(j - 1) ? lcs[i - 1][j - 1] + 1 : Math.max(lcs[i - 1][j], lcs[i][j - 1]);
        StringBuilder sb = new StringBuilder();
        int i = m, j = n;
        while (i > 0 && j > 0) {
            if (a.charAt(i - 1) == b.charAt(j - 1)) {
                sb.append(a.charAt(i - 1));
                i--;
                j--;
            } else if (lcs[i - 1][j] >= lcs[i][j - 1]) {
                sb.append(a.charAt(--i));
            } else {
                sb.append(b.charAt(--j));
            }
        }
        while (i > 0) sb.append(a.charAt(--i));
        while (j > 0) sb.append(b.charAt(--j));
        return sb.reverse().toString();
    }

    /* Approach 2 - DP that keeps the actual shortest supersequence of every pair of suffixes. O(m * n * (m + n)). */
    public static String shortestCommonSupersequenceStrings(String a, String b) {
        int m = a.length(), n = b.length();
        String[][] dp = new String[m + 1][n + 1];
        for (int i = m; i >= 0; i--) {
            for (int j = n; j >= 0; j--) {
                if (i == m) dp[i][j] = b.substring(j);
                else if (j == n) dp[i][j] = a.substring(i);
                else if (a.charAt(i) == b.charAt(j)) dp[i][j] = a.charAt(i) + dp[i + 1][j + 1];
                else {
                    String x = a.charAt(i) + dp[i + 1][j], y = b.charAt(j) + dp[i][j + 1];
                    dp[i][j] = x.length() <= y.length() ? x : y;
                }
            }
        }
        return dp[0][0];
    }
}
