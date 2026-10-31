package LetterCombinationsOfAPhoneNumber;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/*
 * 17. Letter Combinations of a Phone Number — https://leetcode.com/problems/letter-combinations-of-a-phone-number/
 * Given a string of digits 2-9, return all letter combinations they could represent on a phone keypad. Any order.
 * Input: "23" -> ["ad","ae","af","bd","be","bf","cd","ce","cf"] · "" -> [] · "2" -> ["a","b","c"]
 * Explanation: README.md
 */
public class LetterCombinationsOfAPhoneNumber {
    private static final String[] KEYS = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    public static void main(String[] args) {
        System.out.println(letterCombinations("23"));        // [ad, ae, af, bd, be, bf, cd, ce, cf]
        System.out.println(letterCombinationsQueue("2"));    // [a, b, c]
    }

    /* Approach 1 - backtracking: choose a letter for position i, recurse, undo. Time O(4^n * n), space O(n) recursion. */
    public static List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if (!digits.isEmpty()) {
            backtrack(digits, 0, new StringBuilder(), result);
        }
        return result;
    }

    private static void backtrack(String digits, int i, StringBuilder path, List<String> result) {
        if (i == digits.length()) {
            result.add(path.toString());
            return;
        }
        for (char c : KEYS[digits.charAt(i) - '0'].toCharArray()) {
            path.append(c);
            backtrack(digits, i + 1, path, result);
            path.deleteCharAt(path.length() - 1);
        }
    }

    /* Approach 2 - breadth-first: extend every partial combination by each letter of the next digit. Same complexity. */
    public static List<String> letterCombinationsQueue(String digits) {
        if (digits.isEmpty()) {
            return new ArrayList<>();
        }
        Deque<String> queue = new ArrayDeque<>();
        queue.add("");
        for (char d : digits.toCharArray()) {
            for (int size = queue.size(); size > 0; size--) {
                String prefix = queue.poll();
                for (char c : KEYS[d - '0'].toCharArray()) {
                    queue.add(prefix + c);
                }
            }
        }
        return new ArrayList<>(queue);
    }
}
