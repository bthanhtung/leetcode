package com.tungdadev.medium;

import java.lang.reflect.Array;
import java.util.*;

/*
    * Link: https://leetcode.com/problems/group-anagrams/
    * Given an array of strings, group the anagrams together. You can return the answer in any order.
    * An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.
    * For example, "anagram" and "nagaram" are anagrams.
    * Input: strs = ["eat","tea","tan","ate","nat","bat"]
    * Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
 */
public class GroupAnagrams {
    public static void main(String[] args) {
        String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};
        System.out.println(groupAnagrams(strs));
    }

    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for(String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);

            String key = new String(chars);
            if(!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(str);
        }
        return new ArrayList<>(map.values());
    }
}
