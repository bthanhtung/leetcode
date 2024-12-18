package com.tungdadev.medium;

import java.util.HashMap;
import java.util.Map;

/**
 * Link: https://leetcode.com/problems/longest-substring-without-repeating-characters/description/
 * Given a string s, find the length of the longest substring without repeating characters.
 */
public class LongestSubstringWithoutRepeatingCharacters {
    /*
     * Just to test
     */
    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcabcbb"));
    }

    /*
     * Solution implement here
     */
    public static int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> visited = new HashMap<>();
        String res = "";
        for (int start = 0, end = 0; end < s.length(); end++) {
            char currChar = s.charAt(end);
            if (visited.containsKey(currChar)) {
                start = Math.max(visited.get(currChar)+1, start);
            }
            if (res.length() < end - start + 1) {
                res = s.substring(start, end + 1);
            }
            visited.put(currChar, end);
        }
        return res.length();
    }
}
