package com.tungdadev.medium;

import java.util.ArrayList;
import java.util.List;

/**
 * Link: https://leetcode.com/problems/letter-combinations-of-a-phone-number/description/
 * Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent. Return the answer in any order.
 * A mapping of digits to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.
 */
public class LetterCombinationsPhoneNumber {
    /*
     * Just to test
     */
    public static void main(String[] args) {
        System.out.println(letterCombinations("23"));
    }

    /*
     * Solution implement here
     */
    public static List<String> letterCombinations(String digits) {
        if (digits.isEmpty()) return new ArrayList<>();

        List<String> res = new ArrayList<>();

        dfs(digits, 0, new StringBuilder(), res);
        return res;
    }

    private static void dfs(String digits, int i, StringBuilder sb, List<String> res) {
        if (i == digits.length()) {
            res.add(sb.toString());
            return;
        }

        for (char c : digitToLetters[digits.charAt(i) - '0'].toCharArray()) {
            sb.append(c);
            dfs(digits, i + 1, sb, res);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    private static final String[] digitToLetters =
            {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

}
