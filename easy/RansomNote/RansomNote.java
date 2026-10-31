package RansomNote;

import java.util.HashMap;
import java.util.Map;

/*
 * 383. Ransom Note — https://leetcode.com/problems/ransom-note/
 * Return true if ransomNote can be built from the letters of magazine; each magazine letter can be used once.
 * Input: ("a","b") -> false · ("aa","ab") -> false · ("aa","aab") -> true      Only lowercase English letters.
 * Explanation: README.md
 */
public class RansomNote {
    public static void main(String[] args) {
        System.out.println(canConstruct("aa", "aab"));          // true
        System.out.println(canConstructMap("aa", "ab"));        // false
        System.out.println(canConstructRemove("a", "b"));       // false
    }

    /* Approach 1 - remove each needed letter from a mutable copy of the magazine. Time O(m * n), space O(n). */
    public static boolean canConstructRemove(String ransomNote, String magazine) {
        StringBuilder left = new StringBuilder(magazine);
        for (char c : ransomNote.toCharArray()) {
            int at = left.indexOf(String.valueOf(c));
            if (at < 0) {
                return false;
            }
            left.deleteCharAt(at);
        }
        return true;
    }

    /* Approach 2 - count letters with a hash map (works for any alphabet). Time O(m + n), space O(k). */
    public static boolean canConstructMap(String ransomNote, String magazine) {
        Map<Character, Integer> count = new HashMap<>();
        for (char c : magazine.toCharArray()) {
            count.merge(c, 1, Integer::sum);
        }
        for (char c : ransomNote.toCharArray()) {
            int left = count.getOrDefault(c, 0);
            if (left == 0) {
                return false;
            }
            count.put(c, left - 1);
        }
        return true;
    }

    /* Approach 3 - counting array of 26 letters: fastest. Time O(m + n), space O(1). */
    public static boolean canConstruct(String ransomNote, String magazine) {
        if (ransomNote.length() > magazine.length()) {
            return false;
        }
        int[] count = new int[26];
        for (char c : magazine.toCharArray()) {
            count[c - 'a']++;
        }
        for (char c : ransomNote.toCharArray()) {
            if (--count[c - 'a'] < 0) {
                return false;
            }
        }
        return true;
    }
}
