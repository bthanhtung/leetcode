package SubstringWithConcatenationOfAllWords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 30. Substring with Concatenation of All Words — https://leetcode.com/problems/substring-with-concatenation-of-all-words/
 * words all have the same length. Return the start indices of every substring of s that is a concatenation of all
 * words in any order (each used exactly as many times as it appears).
 * Input: s = "barfoothefoobarman", words = ["foo","bar"] -> [0,9]
 *        s = "wordgoodgoodgoodbestword", words = ["word","good","best","word"] -> []
 *        s = "barfoofoobarthefoobarman", words = ["bar","foo","the"] -> [6,9,12]
 * Explanation: README.md
 */
public class SubstringWithConcatenationOfAllWords {
    public static void main(String[] args) {
        System.out.println(findSubstring("barfoofoobarthefoobarman", new String[]{"bar", "foo", "the"}));   // [6, 9, 12]
    }

    /*
     * Words have length L, so split s into word slots starting at each offset 0..L-1. On each offset run a sliding
     * window over slots: add the word on the right; if it is unknown, reset; if it is over-used, drop words from the
     * left. When the window holds exactly words.length words, record its start. Time O(n * L).
     */
    public static List<Integer> findSubstring(String s, String[] words) {
        List<Integer> out = new ArrayList<>();
        int L = words[0].length(), k = words.length, n = s.length();
        Map<String, Integer> need = new HashMap<>();
        for (String w : words) need.merge(w, 1, Integer::sum);
        for (int offset = 0; offset < L; offset++) {
            Map<String, Integer> window = new HashMap<>();
            int left = offset, used = 0;
            for (int right = offset; right + L <= n; right += L) {
                String w = s.substring(right, right + L);
                if (!need.containsKey(w)) {
                    window.clear();
                    used = 0;
                    left = right + L;
                    continue;
                }
                window.merge(w, 1, Integer::sum);
                used++;
                while (window.get(w) > need.get(w)) {
                    window.merge(s.substring(left, left + L), -1, Integer::sum);
                    left += L;
                    used--;
                }
                if (used == k) out.add(left);
            }
        }
        out.sort(null);
        return out;
    }
}
