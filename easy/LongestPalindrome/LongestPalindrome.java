package LongestPalindrome;

import java.util.HashSet;
import java.util.Set;

/*
 * 409. Longest Palindrome — https://leetcode.com/problems/longest-palindrome/
 * Return the length of the longest palindrome that can be built with the letters of s (case sensitive).
 * Input: "abccccdd" -> 7 ("dccaccd") · "a" -> 1
 * Explanation: README.md
 */
public class LongestPalindrome {
    public static void main(String[] args) {
        System.out.println(longestPalindrome("abccccdd"));        // 7
        System.out.println(longestPalindromeSet("a"));            // 1
    }

    /* Approach 1 - every pair of equal letters can go on both sides; one odd letter can sit in the middle. Time O(n). */
    public static int longestPalindrome(String s) {
        int[] count = new int[128];
        for (char c : s.toCharArray()) count[c]++;
        int length = 0;
        boolean hasOdd = false;
        for (int c : count) {
            length += c / 2 * 2;
            if (c % 2 == 1) hasOdd = true;
        }
        return length + (hasOdd ? 1 : 0);
    }

    /* Approach 2 - toggle letters in a set; what stays in the set are letters with odd counts. */
    public static int longestPalindromeSet(String s) {
        Set<Character> odd = new HashSet<>();
        for (char c : s.toCharArray()) {
            if (!odd.remove(c)) odd.add(c);
        }
        return odd.isEmpty() ? s.length() : s.length() - odd.size() + 1;
    }
}
