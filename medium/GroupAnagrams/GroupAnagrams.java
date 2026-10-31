package GroupAnagrams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 49. Group Anagrams — https://leetcode.com/problems/group-anagrams/
 * Given an array of strings, group the anagrams together (words made of the same letters). Any order.
 * Input: ["eat","tea","tan","ate","nat","bat"] -> [["bat"],["nat","tan"],["ate","eat","tea"]]
 * Explanation: README.md
 */
public class GroupAnagrams {
    public static void main(String[] args) {
        String[] words = {"eat", "tea", "tan", "ate", "nat", "bat"};
        System.out.println(groupAnagrams(words));            // [[eat, tea, ate], [tan, nat], [bat]] in some order
        System.out.println(groupAnagramsCount(words));
    }

    /* Approach 1 - key = the word's letters sorted ("eat" -> "aet"). Time O(n * k log k), space O(n * k). */
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> groups = new HashMap<>();
        for (String s : strs) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            groups.computeIfAbsent(new String(chars), key -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(groups.values());
    }

    /* Approach 2 - key = letter counts ("eat" -> "#1#0#0#0#1...#1..."); no sorting. Time O(n * k), space O(n * k). */
    public static List<List<String>> groupAnagramsCount(String[] strs) {
        Map<String, List<String>> groups = new HashMap<>();
        for (String s : strs) {
            int[] count = new int[26];
            for (char c : s.toCharArray()) {
                count[c - 'a']++;
            }
            StringBuilder key = new StringBuilder();
            for (int c : count) {
                key.append('#').append(c);       // separator: counts 1,11 and 11,1 must give different keys
            }
            groups.computeIfAbsent(key.toString(), k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(groups.values());
    }
}
