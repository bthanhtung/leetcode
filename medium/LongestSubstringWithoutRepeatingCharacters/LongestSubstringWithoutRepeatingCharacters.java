package LongestSubstringWithoutRepeatingCharacters;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
 * 3. Longest Substring Without Repeating Characters — https://leetcode.com/problems/longest-substring-without-repeating-characters/
 * Given a string s, find the length of the longest substring without duplicate characters.
 * Input: "abcabcbb" -> 3 ("abc") · "bbbbb" -> 1 · "pwwkew" -> 3 ("wke")
 * Explanation: README.md
 */
public class LongestSubstringWithoutRepeatingCharacters {
    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcabcbb"));          // 3
        System.out.println(lengthOfLongestSubstringSet("pwwkew"));         // 3
        System.out.println(lengthOfLongestSubstringBruteForce("bbbbb"));   // 1
    }

    /* Approach 1 - every start, extend while characters stay unique. Time O(n^2) (alphabet-bounded), space O(k). */
    public static int lengthOfLongestSubstringBruteForce(String s) {
        int best = 0;
        for (int i = 0; i < s.length(); i++) {
            Set<Character> seen = new HashSet<>();
            int j = i;
            while (j < s.length() && seen.add(s.charAt(j))) {
                j++;
            }
            best = Math.max(best, j - i);
        }
        return best;
    }

    /* Approach 2 - sliding window with a set: grow right; on a repeat, shrink from the left until it's gone. Time O(n). */
    public static int lengthOfLongestSubstringSet(String s) {
        Set<Character> window = new HashSet<>();
        int best = 0, left = 0;
        for (int right = 0; right < s.length(); right++) {
            while (!window.add(s.charAt(right))) {
                window.remove(s.charAt(left++));
            }
            best = Math.max(best, right - left + 1);
        }
        return best;
    }

    /*
     * Approach 3 - sliding window that jumps: remember the last index of each character; on a repeat, move left
     * straight past the previous occurrence. Each character is visited once. Time O(n), space O(alphabet).
     */
    public static int lengthOfLongestSubstring(String s) {
        int[] last = new int[128];
        Arrays.fill(last, -1);
        int best = 0, left = 0;
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            left = Math.max(left, last[c] + 1);       // max: never move left backwards
            last[c] = right;
            best = Math.max(best, right - left + 1);
        }
        return best;
    }
}
