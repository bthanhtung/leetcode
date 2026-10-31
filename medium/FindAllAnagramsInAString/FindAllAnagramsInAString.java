package FindAllAnagramsInAString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * 438. Find All Anagrams in a String — https://leetcode.com/problems/find-all-anagrams-in-a-string/
 * Return all start indices of p's anagrams in s (lowercase letters).
 * Input: s = "cbaebabacd", p = "abc" -> [0,6] · s = "abab", p = "ab" -> [0,1,2]
 * Explanation: README.md
 */
public class FindAllAnagramsInAString {
    public static void main(String[] args) {
        System.out.println(findAnagrams("cbaebabacd", "abc"));        // [0, 6]
        System.out.println(findAnagramsMatches("abab", "ab"));       // [0, 1, 2]
    }

    /* Approach 1 - sort every window of length |p| and compare. Time O(n * m log m). */
    public static List<Integer> findAnagramsSort(String s, String p) {
        char[] target = p.toCharArray();
        Arrays.sort(target);
        List<Integer> out = new ArrayList<>();
        for (int i = 0; i + p.length() <= s.length(); i++) {
            char[] w = s.substring(i, i + p.length()).toCharArray();
            Arrays.sort(w);
            if (Arrays.equals(w, target)) out.add(i);
        }
        return out;
    }

    /* Approach 2 - fixed-size sliding window: add the entering letter, remove the leaving one, compare 26 counts. O(26 n). */
    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> out = new ArrayList<>();
        if (p.length() > s.length()) return out;
        int[] need = new int[26], window = new int[26];
        for (char c : p.toCharArray()) need[c - 'a']++;
        for (int i = 0; i < s.length(); i++) {
            window[s.charAt(i) - 'a']++;
            if (i >= p.length()) window[s.charAt(i - p.length()) - 'a']--;
            if (i >= p.length() - 1 && Arrays.equals(window, need)) out.add(i - p.length() + 1);
        }
        return out;
    }

    /* Approach 3 - keep one balance array (need - window) and count how many letters are balanced. O(n). */
    public static List<Integer> findAnagramsMatches(String s, String p) {
        List<Integer> out = new ArrayList<>();
        int[] diff = new int[26];
        for (char c : p.toCharArray()) diff[c - 'a']++;
        int balanced = 0;
        for (int d : diff) if (d == 0) balanced++;
        for (int i = 0; i < s.length(); i++) {
            int in = s.charAt(i) - 'a';
            if (diff[in] == 0) balanced--;
            if (--diff[in] == 0) balanced++;
            if (i >= p.length()) {
                int outC = s.charAt(i - p.length()) - 'a';
                if (diff[outC] == 0) balanced--;
                if (++diff[outC] == 0) balanced++;
            }
            if (balanced == 26) out.add(i - p.length() + 1);
        }
        return out;
    }
}
