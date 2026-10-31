package ReverseWordsInAString;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/*
 * 151. Reverse Words in a String — https://leetcode.com/problems/reverse-words-in-a-string/
 * Reverse the order of the words in s. Words are separated by spaces; the result must have single spaces only,
 * without leading or trailing spaces.
 * Input: "the sky is blue" -> "blue is sky the" · "  hello world  " -> "world hello" · "a good   example" -> "example good a"
 * Explanation: README.md
 */
public class ReverseWordsInAString {
    public static void main(String[] args) {
        System.out.println(reverseWords("  hello world  "));        // world hello
        System.out.println(reverseWordsInPlace("a good   example")); // example good a
    }

    /* Approach 1 - trim, split on runs of spaces, reverse, join. Time O(n). */
    public static String reverseWords(String s) {
        List<String> words = Arrays.asList(s.trim().split("\\s+"));
        Collections.reverse(words);
        return String.join(" ", words);
    }

    /*
     * Approach 2 - the classic in-place trick on a char array: squeeze spaces, reverse the whole string, then reverse
     * every word back. Time O(n), O(1) extra besides the array (a mutable string in C/C++).
     */
    public static String reverseWordsInPlace(String s) {
        char[] c = s.toCharArray();
        int n = 0;                                            // compact: remove leading/trailing/duplicate spaces
        for (int i = 0; i < c.length; i++) {
            if (c[i] != ' ' || (n > 0 && c[n - 1] != ' ')) c[n++] = c[i];
        }
        if (n > 0 && c[n - 1] == ' ') n--;
        reverse(c, 0, n - 1);
        for (int start = 0, i = 0; i <= n; i++) {
            if (i == n || c[i] == ' ') {
                reverse(c, start, i - 1);
                start = i + 1;
            }
        }
        return new String(c, 0, n);
    }

    private static void reverse(char[] c, int i, int j) {
        for (; i < j; i++, j--) {
            char t = c[i];
            c[i] = c[j];
            c[j] = t;
        }
    }

    /* Approach 3 - read words left to right and push each to the front of a deque. */
    public static String reverseWordsDeque(String s) {
        Deque<String> words = new ArrayDeque<>();
        StringBuilder word = new StringBuilder();
        for (int i = 0; i <= s.length(); i++) {
            if (i == s.length() || s.charAt(i) == ' ') {
                if (!word.isEmpty()) {
                    words.addFirst(word.toString());
                    word.setLength(0);
                }
            } else {
                word.append(s.charAt(i));
            }
        }
        return String.join(" ", words);
    }
}
