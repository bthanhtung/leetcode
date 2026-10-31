package FindTheIndexOfTheFirstOccurrenceInAString;

/*
 * 28. Find the Index of the First Occurrence in a String — https://leetcode.com/problems/find-the-index-of-the-first-occurrence-in-a-string/
 * Return the index of the first occurrence of needle in haystack, or -1.
 * Input: ("sadbutsad","sad") -> 0 · ("leetcode","leeto") -> -1
 * Explanation: README.md
 */
public class FindTheIndexOfTheFirstOccurrenceInAString {
    public static void main(String[] args) {
        System.out.println(strStr("sadbutsad", "sad"));            // 0
        System.out.println(strStrKmp("aabaaabaaac", "aabaaac"));   // 4
    }

    /* Approach 1 - compare needle at every start. O(m * n) worst case. */
    public static int strStr(String haystack, String needle) {
        int n = haystack.length(), m = needle.length();
        for (int i = 0; i + m <= n; i++) {
            int j = 0;
            while (j < m && haystack.charAt(i + j) == needle.charAt(j)) j++;
            if (j == m) return i;
        }
        return -1;
    }

    /*
     * Approach 2 - KMP: fail[j] = length of the longest proper prefix of needle[0..j] that is also a suffix. On a
     * mismatch, fall back to fail[j - 1] instead of restarting, so the haystack pointer never moves back. O(m + n).
     */
    public static int strStrKmp(String haystack, String needle) {
        int m = needle.length();
        if (m == 0) return 0;
        int[] fail = new int[m];
        for (int i = 1, k = 0; i < m; i++) {
            while (k > 0 && needle.charAt(i) != needle.charAt(k)) k = fail[k - 1];
            if (needle.charAt(i) == needle.charAt(k)) k++;
            fail[i] = k;
        }
        for (int i = 0, j = 0; i < haystack.length(); i++) {
            while (j > 0 && haystack.charAt(i) != needle.charAt(j)) j = fail[j - 1];
            if (haystack.charAt(i) == needle.charAt(j)) j++;
            if (j == m) return i - m + 1;
        }
        return -1;
    }

    /* Approach 3 - Rabin-Karp: compare rolling hashes, verify on a hash match. Average O(m + n). */
    public static int strStrRabinKarp(String haystack, String needle) {
        int n = haystack.length(), m = needle.length();
        if (m > n) return -1;
        long mod = 1_000_000_007L, base = 31, power = 1, hNeedle = 0, hWindow = 0;
        for (int i = 0; i < m; i++) {
            hNeedle = (hNeedle * base + needle.charAt(i)) % mod;
            hWindow = (hWindow * base + haystack.charAt(i)) % mod;
            if (i > 0) power = power * base % mod;
        }
        for (int i = 0; ; i++) {
            if (hNeedle == hWindow && haystack.regionMatches(i, needle, 0, m)) return i;
            if (i + m == n) return -1;
            hWindow = (hWindow - haystack.charAt(i) * power % mod + mod) % mod;
            hWindow = (hWindow * base + haystack.charAt(i + m)) % mod;
        }
    }
}
