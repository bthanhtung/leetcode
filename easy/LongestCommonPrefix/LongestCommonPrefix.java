package LongestCommonPrefix;

import java.util.Arrays;

/*
 * 14. Longest Common Prefix — https://leetcode.com/problems/longest-common-prefix/
 * Write a function to find the longest common prefix string amongst an array of strings. If there is none, return "".
 * Input: ["flower","flow","flight"] -> "fl" · ["dog","racecar","car"] -> ""
 * Explanation: README.md
 */
public class LongestCommonPrefix {
    public static void main(String[] args) {
        System.out.println(lcpVertical(new String[]{"flower", "flow", "flight"}));    // fl
        System.out.println(lcpHorizontal(new String[]{"dog", "racecar", "car"}));    // ""
        System.out.println(lcpSorted(new String[]{"interview", "internet", "interval"}));   // inter
    }

    /* Approach 1 - vertical scan: compare column i of every word with the first word. Time O(S) (total chars), space O(1). */
    public static String lcpVertical(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
        for (int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if (i == strs[j].length() || strs[j].charAt(i) != c) {
                    return strs[0].substring(0, i);
                }
            }
        }
        return strs[0];
    }

    /* Approach 2 - horizontal: shrink a candidate prefix until every word starts with it. Time O(S), space O(1). */
    public static String lcpHorizontal(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
        String prefix = strs[0];
        for (int i = 1; i < strs.length && !prefix.isEmpty(); i++) {
            while (!strs[i].startsWith(prefix)) {
                prefix = prefix.substring(0, prefix.length() - 1);
            }
        }
        return prefix;
    }

    /*
     * Approach 3 - sort, then compare only the first and last word: after sorting they are the most different,
     * so their common prefix is everyone's. Time O(n log n * m), space O(1) beyond sorting.
     */
    public static String lcpSorted(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
        String[] copy = strs.clone();
        Arrays.sort(copy);
        String first = copy[0], last = copy[copy.length - 1];
        int i = 0;
        while (i < first.length() && i < last.length() && first.charAt(i) == last.charAt(i)) {
            i++;
        }
        return first.substring(0, i);
    }
}
