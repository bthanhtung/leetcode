package MinimumInsertionStepsToMakeAStringPalindrome;

/*
 * 1312. Minimum Insertion Steps to Make a String Palindrome — https://leetcode.com/problems/minimum-insertion-steps-to-make-a-string-palindrome/
 * Return the minimum number of characters to insert anywhere in s to make it a palindrome.
 * Input: "zzazz" -> 0 · "mbadm" -> 2 ("mbdadbm") · "leetcode" -> 5
 * Explanation: README.md
 */
public class MinimumInsertionStepsToMakeAStringPalindrome {
    public static void main(String[] args) {
        System.out.println(minInsertions("mbadm"));          // 2
        System.out.println(minInsertionsLps("leetcode"));    // 5
    }

    /* Approach 1 - dp[i][j] = insertions for s[i..j]: equal ends -> dp[i+1][j-1], else 1 + min(drop left, drop right). */
    public static int minInsertions(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        for (int i = n - 2; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                dp[i][j] = s.charAt(i) == s.charAt(j) ? dp[i + 1][j - 1] : 1 + Math.min(dp[i + 1][j], dp[i][j - 1]);
            }
        }
        return n == 0 ? 0 : dp[0][n - 1];
    }

    /* Approach 2 - characters not in the longest palindromic subsequence each need a mirror: n - LPS. LPS via LCS with reverse. */
    public static int minInsertionsLps(String s) {
        String t = new StringBuilder(s).reverse().toString();
        int n = s.length();
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int diagonal = 0;
            for (int j = 1; j <= n; j++) {
                int above = dp[j];
                dp[j] = s.charAt(i - 1) == t.charAt(j - 1) ? diagonal + 1 : Math.max(dp[j], dp[j - 1]);
                diagonal = above;
            }
        }
        return n - dp[n];
    }
}
