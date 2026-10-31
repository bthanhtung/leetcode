package StringMatchingInAnArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/*
 * 1408. String Matching in an Array — https://leetcode.com/problems/string-matching-in-an-array/
 * Given an array of distinct strings words, return all strings that are a substring of another word, in any order.
 * Input: ["mass","as","hero","superhero"] -> ["as","hero"] · ["leetcode","et","code"] -> ["et","code"]
 * Explanation: README.md
 */
public class StringMatchingInAnArray {
    public static void main(String[] args) {
        System.out.println(stringMatching(new String[]{"mass", "as", "hero", "superhero"}));         // [as, hero]
        System.out.println(stringMatchingJoined(new String[]{"leetcode", "et", "code"}));           // [et, code]
        System.out.println(stringMatchingSorted(new String[]{"blue", "green", "bu"}));              // []
    }

    /* Approach 1 - check every pair with contains(). Time O(n^2 * L^2) worst case, space O(1). */
    public static List<String> stringMatching(String[] words) {
        List<String> result = new ArrayList<>();
        for (String w : words) {
            for (String other : words) {
                if (w != other && other.contains(w)) {
                    result.add(w);
                    break;
                }
            }
        }
        return result;
    }

    /* Approach 2 - sort by length: a word can only be inside a longer word, so only look to the right. Same worst case, fewer checks. */
    public static List<String> stringMatchingSorted(String[] words) {
        String[] byLength = words.clone();
        Arrays.sort(byLength, Comparator.comparingInt(String::length));
        List<String> result = new ArrayList<>();
        for (int i = 0; i < byLength.length; i++) {
            for (int j = i + 1; j < byLength.length; j++) {
                if (byLength[j].contains(byLength[i])) {
                    result.add(byLength[i]);
                    break;
                }
            }
        }
        return result;
    }

    /*
     * Approach 3 - join all words with a separator; w is a substring of another word iff it occurs at least twice
     * in the joined text (once is itself). One string, two indexOf calls per word.
     */
    public static List<String> stringMatchingJoined(String[] words) {
        String all = String.join("|", words);
        List<String> result = new ArrayList<>();
        for (String w : words) {
            if (all.indexOf(w) != all.lastIndexOf(w)) {
                result.add(w);
            }
        }
        return result;
    }
}
