package ShortestPalindrome;

/*
 * 214. Shortest Palindrome — https://leetcode.com/problems/shortest-palindrome/
 * Add characters only in front of s to make the shortest possible palindrome; return it.
 * Input: "aacecaaa" -> "aaacecaaa" · "abcd" -> "dcbabcd"
 * Explanation: README.md
 */
public class ShortestPalindrome {
    public static void main(String[] args) {
        System.out.println(shortestPalindrome("aacecaaa"));        // aaacecaaa
        System.out.println(shortestPalindromeHash("abcd"));        // dcbabcd
    }

    /* Approach 1 - find the longest prefix that is a palindrome by trying lengths from n down. Time O(n^2). */
    public static String shortestPalindromeBrute(String s) {
        String rev = new StringBuilder(s).reverse().toString();
        for (int len = s.length(); len >= 0; len--) {
            if (s.startsWith(rev.substring(s.length() - len))) return rev.substring(0, s.length() - len) + s;
        }
        return s;
    }

    /*
     * Approach 2 - KMP: in t = s + "#" + reverse(s), the longest proper prefix that is also a suffix equals the longest
     * palindromic prefix of s. Reverse the rest and put it in front. Time O(n).
     */
    public static String shortestPalindrome(String s) {
        String t = s + "#" + new StringBuilder(s).reverse();
        int[] fail = new int[t.length()];
        for (int i = 1; i < t.length(); i++) {
            int k = fail[i - 1];
            while (k > 0 && t.charAt(i) != t.charAt(k)) k = fail[k - 1];
            if (t.charAt(i) == t.charAt(k)) k++;
            fail[i] = k;
        }
        int palPrefix = fail[t.length() - 1];
        return new StringBuilder(s.substring(palPrefix)).reverse() + s;
    }

    /* Approach 3 - rolling hash of each prefix read forward and backward; equal hashes mark a palindromic prefix. O(n). */
    public static String shortestPalindromeHash(String s) {
        long base = 131, mod = 1_000_000_007L, forward = 0, backward = 0, power = 1;
        int best = 0;
        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i);
            forward = (forward * base + c) % mod;
            backward = (backward + c * power) % mod;
            power = power * base % mod;
            if (forward == backward && isPalindrome(s, i)) best = i + 1;      // verify to rule out collisions
        }
        return new StringBuilder(s.substring(best)).reverse() + s;
    }

    private static boolean isPalindrome(String s, int end) {
        for (int i = 0, j = end; i < j; i++, j--) if (s.charAt(i) != s.charAt(j)) return false;
        return true;
    }
}
