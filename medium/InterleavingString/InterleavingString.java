package InterleavingString;

/*
 * 97. Interleaving String — https://leetcode.com/problems/interleaving-string/
 * Is s3 formed by interleaving s1 and s2 (keeping the order of characters within each)?
 * Input: ("aabcc","dbbca","aadbbcbcac") -> true · ("aabcc","dbbca","aadbbbaccc") -> false · ("","","") -> true
 * Explanation: README.md
 */
public class InterleavingString {
    public static void main(String[] args) {
        System.out.println(isInterleave("aabcc", "dbbca", "aadbbcbcac"));     // true
    }

    /*
     * dp[i][j] = s3[0..i+j) is an interleaving of s1[0..i) and s2[0..j). The last character came either from s1
     * (dp[i-1][j] and s1[i-1] == s3[i+j-1]) or from s2. Time O(m * n).
     */
    public static boolean isInterleave(String s1, String s2, String s3) {
        int m = s1.length(), n = s2.length();
        if (m + n != s3.length()) return false;
        boolean[][] dp = new boolean[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 && j == 0) dp[i][j] = true;
                else dp[i][j] = (i > 0 && dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(i + j - 1))
                        || (j > 0 && dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1));
            }
        }
        return dp[m][n];
    }

    /* Same recurrence with one row (follow up: O(n) extra space). */
    public static boolean isInterleaveRow(String s1, String s2, String s3) {
        int m = s1.length(), n = s2.length();
        if (m + n != s3.length()) return false;
        boolean[] dp = new boolean[n + 1];
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 && j == 0) dp[j] = true;
                else dp[j] = (i > 0 && dp[j] && s1.charAt(i - 1) == s3.charAt(i + j - 1))
                        || (j > 0 && dp[j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1));
            }
        }
        return dp[n];
    }
}
