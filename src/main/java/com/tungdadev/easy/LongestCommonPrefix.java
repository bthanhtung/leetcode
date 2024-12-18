package com.tungdadev.easy;

/**
 * Link: https://leetcode.com/problems/longest-common-prefix
 * Write a function to find the longest common prefix string amongst an array of strings.
 * If there is no common prefix, return an empty string "".
 */
public class LongestCommonPrefix {
    /*
     * Just to test
     */
    public static void main(String[] args) {
        System.out.println(longestCommonPrefix(new String[]{"flower","flow","flight"}));
    }

    /*
     * Solution implement here
     */
    public static String longestCommonPrefix(String[] strs) {
        int strLength = strs.length;
        if (strLength == 0) {
            return "";
        }
        for (int i = 0; i < strs[0].length(); ++i)
            for (int j = 1; j < strs.length; ++j)
                if (i == strs[j].length() || strs[j].charAt(i) != strs[0].charAt(i))
                    return strs[0].substring(0, i);
        return strs[0];
    }
}
