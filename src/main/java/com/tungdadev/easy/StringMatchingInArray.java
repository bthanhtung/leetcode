package com.tungdadev.easy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Link: https://leetcode.com/problems/string-matching-in-an-array
 * Given an array of string words, return all strings in words that is a substring of another word. You can return the answer in any order.
 * A substring is a contiguous sequence of characters within a string
*/
 public class StringMatchingInArray {
    /*
     * Just to test
     */
    public static void main(String[] args) {
        String[] words = {"mass", "as", "hero", "superhero"}; // ["as","hero"]
        // String[] words = {"leetcode","et","code"}; // ["et","code"]
        // String[] words = {"blue","green","bu"}; // []
        System.out.println(stringMatching(words));
    }

    /*
     * Solution implement here
     */
    public static List<String> stringMatching(String[] words) {
        List<String> result = new ArrayList<>();
        for(int i = 0; i < words.length; i++) {
            for(int j = 0; j < words.length; j++) {
                if(words[j].contains(words[i]) && i != j) {
                    result.add(words[i]);
                    break;
                }
            }
        }
        return result;
    }
}
