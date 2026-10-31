package LongestPalindromicSubstring;

/*
 * 5. Longest Palindromic Substring — https://leetcode.com/problems/longest-palindromic-substring/
 * Given a string s, return the longest palindromic substring in s.
 * Input: "babad" -> "bab" (or "aba") · "cbbd" -> "bb"
 * Explanation: README.md
 */
public class LongestPalindromicSubstring {
    public static void main(String[] args) {
        System.out.println(longestPalindrome("babad"));          // bab
        System.out.println(longestPalindromeDp("cbbd"));         // bb
        System.out.println(longestPalindromeManacher("forgeeksskeegfor"));   // geeksskeeg
    }

    /* Approach 1 - expand around each of the 2n - 1 centers (a character or a gap). Time O(n^2), space O(1). */
    public static String longestPalindrome(String s) {
        int start = 0, end = 0;                  // best is s[start..end]
        for (int i = 0; i < s.length(); i++) {
            int odd = expand(s, i, i);           // center on a character: "aba"
            int even = expand(s, i, i + 1);      // center between two characters: "abba"
            int len = Math.max(odd, even);
            if (len > end - start + 1) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    private static int expand(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }

    /*
     * Approach 2 - DP: pal[i][j] is true when s[i] == s[j] and the inside s[i+1..j-1] is a palindrome.
     * Fill by increasing length. Time O(n^2), space O(n^2).
     */
    public static String longestPalindromeDp(String s) {
        int n = s.length();
        boolean[][] pal = new boolean[n][n];
        int start = 0, best = n == 0 ? 0 : 1;
        for (int len = 1; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;
                pal[i][j] = s.charAt(i) == s.charAt(j) && (len <= 2 || pal[i + 1][j - 1]);
                if (pal[i][j] && len > best) {
                    best = len;
                    start = i;
                }
            }
        }
        return s.substring(start, start + best);
    }

    /*
     * Approach 3 - Manacher: insert '#' between characters so every palindrome has odd length, and reuse the mirror
     * radius inside the right-most palindrome found so far. Time O(n), space O(n).
     */
    public static String longestPalindromeManacher(String s) {
        StringBuilder sb = new StringBuilder("^");
        for (char c : s.toCharArray()) {
            sb.append('#').append(c);
        }
        String t = sb.append("#$").toString();       // ^ and $ are sentinels, so the expansion needs no bounds check
        int[] radius = new int[t.length()];
        int center = 0, right = 0;
        for (int i = 1; i < t.length() - 1; i++) {
            if (i < right) {
                radius[i] = Math.min(right - i, radius[2 * center - i]);
            }
            while (t.charAt(i + radius[i] + 1) == t.charAt(i - radius[i] - 1)) {
                radius[i]++;
            }
            if (i + radius[i] > right) {
                center = i;
                right = i + radius[i];
            }
        }
        int bestCenter = 0;
        for (int i = 1; i < t.length() - 1; i++) {
            if (radius[i] > radius[bestCenter]) {
                bestCenter = i;
            }
        }
        int startInS = (bestCenter - radius[bestCenter]) / 2;
        return s.substring(startInS, startInS + radius[bestCenter]);
    }
}
