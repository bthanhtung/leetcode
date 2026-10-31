package KeyboardRow;

import java.util.ArrayList;
import java.util.List;

/*
 * 500. Keyboard Row — https://leetcode.com/problems/keyboard-row/
 * Return the words that can be typed using letters of only one row of an American keyboard
 * ("qwertyuiop", "asdfghjkl", "zxcvbnm"). Case-insensitive.
 * Input: ["Hello","Alaska","Dad","Peace"] -> ["Alaska","Dad"]
 * Explanation: README.md
 */
public class KeyboardRow {
    public static void main(String[] args) {
        System.out.println(findWords(new String[]{"Hello", "Alaska", "Dad", "Peace"}));         // [Alaska, Dad]
        System.out.println(findWordsRegex(new String[]{"omk"}));                             // []
    }

    private static final int[] ROW = new int[26];

    static {
        String[] rows = {"qwertyuiop", "asdfghjkl", "zxcvbnm"};
        for (int r = 0; r < rows.length; r++) {
            for (char c : rows[r].toCharArray()) ROW[c - 'a'] = r;
        }
    }

    /* Approach 1 - letter -> row table; a word is fine if every letter has the first letter's row. Time O(total chars). */
    public static List<String> findWords(String[] words) {
        List<String> out = new ArrayList<>();
        for (String w : words) {
            String lower = w.toLowerCase();
            int row = ROW[lower.charAt(0) - 'a'];
            boolean ok = true;
            for (char c : lower.toCharArray()) {
                if (ROW[c - 'a'] != row) {
                    ok = false;
                    break;
                }
            }
            if (ok) out.add(w);
        }
        return out;
    }

    /* Approach 2 - one regular expression per row. Short, slower. */
    public static List<String> findWordsRegex(String[] words) {
        List<String> out = new ArrayList<>();
        for (String w : words) {
            if (w.toLowerCase().matches("[qwertyuiop]*|[asdfghjkl]*|[zxcvbnm]*")) out.add(w);
        }
        return out;
    }
}
