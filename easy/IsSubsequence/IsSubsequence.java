package IsSubsequence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * 392. Is Subsequence — https://leetcode.com/problems/is-subsequence/
 * Return true if s is a subsequence of t (s can be obtained by deleting some characters of t).
 * Follow up: many s strings against the same t.
 * Input: ("abc","ahbgdc") -> true · ("axc","ahbgdc") -> false
 * Explanation: README.md
 */
public class IsSubsequence {
    public static void main(String[] args) {
        System.out.println(isSubsequence("abc", "ahbgdc"));     // true
    }

    /* Approach 1 - one pointer per string; advance i in s only on a match. Time O(|t|). */
    public static boolean isSubsequence(String s, String t) {
        int i = 0;
        for (int j = 0; j < t.length() && i < s.length(); j++) {
            if (s.charAt(i) == t.charAt(j)) i++;
        }
        return i == s.length();
    }

    /*
     * Approach 2 - follow up: index positions of every letter in t once; for each character of s binary-search the
     * first position after the previous match. Each query O(|s| log |t|).
     */
    public static boolean isSubsequenceIndexed(String s, String t) {
        List<List<Integer>> pos = new ArrayList<>();
        for (int c = 0; c < 26; c++) pos.add(new ArrayList<>());
        for (int j = 0; j < t.length(); j++) pos.get(t.charAt(j) - 'a').add(j);
        int prev = -1;
        for (char c : s.toCharArray()) {
            List<Integer> list = pos.get(c - 'a');
            int k = Collections.binarySearch(list, prev + 1);
            if (k < 0) k = -k - 1;
            if (k == list.size()) return false;
            prev = list.get(k);
        }
        return true;
    }
}
