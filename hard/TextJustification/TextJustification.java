package TextJustification;

import java.util.ArrayList;
import java.util.List;

/*
 * 68. Text Justification — https://leetcode.com/problems/text-justification/
 * Format words into lines of exactly maxWidth characters, fully justified: pack greedily, spread extra spaces as evenly
 * as possible with the left gaps getting more; the last line and single-word lines are left-justified.
 * Input: ["This","is","an","example","of","text","justification."], 16 ->
 *        ["This    is    an", "example  of text", "justification.  "]
 * Explanation: README.md
 */
public class TextJustification {
    public static void main(String[] args) {
        for (String line : fullJustify(new String[]{"This", "is", "an", "example", "of", "text", "justification."}, 16)) {
            System.out.println("[" + line + "]");
        }
    }

    /* Greedy - take as many words as fit (with one space between), then distribute the remaining spaces. O(total chars). */
    public static List<String> fullJustify(String[] words, int maxWidth) {
        List<String> out = new ArrayList<>();
        int i = 0;
        while (i < words.length) {
            int j = i, lettersLength = 0;
            while (j < words.length && lettersLength + words[j].length() + (j - i) <= maxWidth) {
                lettersLength += words[j].length();
                j++;
            }
            int gaps = j - i - 1, spaces = maxWidth - lettersLength;
            StringBuilder line = new StringBuilder(words[i]);
            if (j == words.length || gaps == 0) {                          // last line or a single word: left-justify
                for (int k = i + 1; k < j; k++) line.append(' ').append(words[k]);
                line.append(" ".repeat(maxWidth - line.length()));
            } else {
                int even = spaces / gaps, extra = spaces % gaps;            // the first `extra` gaps get one more space
                for (int k = i + 1; k < j; k++) {
                    line.append(" ".repeat(even + (k - i <= extra ? 1 : 0))).append(words[k]);
                }
            }
            out.add(line.toString());
            i = j;
        }
        return out;
    }
}
