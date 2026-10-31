package PalindromicSubstrings;

/*
 * 647. Palindromic Substrings — https://leetcode.com/problems/palindromic-substrings/
 * Count the palindromic substrings of s (substrings at different positions count separately).
 * Input: "abc" -> 3 ("a","b","c") · "aaa" -> 6 ("a","a","a","aa","aa","aaa")
 * Explanation: README.md
 */
public class PalindromicSubstrings {
    public static void main(String[] args) {
        System.out.println(countSubstrings("abc"));        // 3
        System.out.println(countSubstringsDp("aaa"));      // 6
    }

    /* Approach 1 - check every substring. Time O(n^3). */
    public static int countSubstringsBruteForce(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                int l = i, r = j;
                while (l < r && s.charAt(l) == s.charAt(r)) {
                    l++;
                    r--;
                }
                if (l >= r) count++;
            }
        }
        return count;
    }

    /* Approach 2 - expand around each of the 2n - 1 centers; every successful step is one more palindrome. O(n^2), O(1). */
    public static int countSubstrings(String s) {
        int count = 0;
        for (int center = 0; center < 2 * s.length() - 1; center++) {
            int l = center / 2, r = l + center % 2;            // even center -> one char, odd center -> the gap after it
            while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
                count++;
                l--;
                r++;
            }
        }
        return count;
    }

    /* Approach 3 - DP: pal[i][j] = s[i] == s[j] && (j - i < 2 || pal[i+1][j-1]); count the true cells. O(n^2), O(n^2). */
    public static int countSubstringsDp(String s) {
        int n = s.length(), count = 0;
        boolean[][] pal = new boolean[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                pal[i][j] = s.charAt(i) == s.charAt(j) && (j - i < 2 || pal[i + 1][j - 1]);
                if (pal[i][j]) count++;
            }
        }
        return count;
    }
}
