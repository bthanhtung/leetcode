package ValidAnagram;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * 242. Valid Anagram — https://leetcode.com/problems/valid-anagram/
 * Return true if t is an anagram of s (same letters, same counts). Lowercase English letters.
 * Input: ("anagram","nagaram") -> true · ("rat","car") -> false. Follow up: Unicode characters?
 * Explanation: README.md
 */
public class ValidAnagram {
    public static void main(String[] args) {
        System.out.println(isAnagram("anagram", "nagaram"));       // true
        System.out.println(isAnagramSort("rat", "car"));           // false
    }

    /* Approach 1 - sort both and compare. Time O(n log n), space O(n). */
    public static boolean isAnagramSort(String s, String t) {
        if (s.length() != t.length()) return false;
        char[] a = s.toCharArray(), b = t.toCharArray();
        Arrays.sort(a);
        Arrays.sort(b);
        return Arrays.equals(a, b);
    }

    /* Approach 2 - one array of 26 counters: +1 for s, -1 for t; all must end at 0. Time O(n), space O(1). */
    public static boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;
        int[] count = new int[26];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
            count[t.charAt(i) - 'a']--;
        }
        for (int c : count) {
            if (c != 0) return false;
        }
        return true;
    }

    /* Approach 3 - follow up: count code points in a map so any Unicode text works. Time O(n). */
    public static boolean isAnagramUnicode(String s, String t) {
        Map<Integer, Integer> count = new HashMap<>();
        s.codePoints().forEach(cp -> count.merge(cp, 1, Integer::sum));
        for (int cp : t.codePoints().toArray()) {
            Integer left = count.get(cp);
            if (left == null) return false;
            if (left == 1) count.remove(cp);
            else count.put(cp, left - 1);
        }
        return count.isEmpty();
    }
}
