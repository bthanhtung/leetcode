package PalindromePairs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 336. Palindrome Pairs — https://leetcode.com/problems/palindrome-pairs/
 * Given unique words, return all pairs [i, j] (i != j) such that words[i] + words[j] is a palindrome.
 * Input: ["abcd","dcba","lls","s","sssll"] -> [[0,1],[1,0],[3,2],[2,4]] · ["a",""] -> [[0,1],[1,0]]
 * Explanation: README.md
 */
public class PalindromePairs {
    public static void main(String[] args) {
        System.out.println(palindromePairs(new String[]{"abcd", "dcba", "lls", "s", "sssll"}));
    }

    private static boolean isPal(String s, int i, int j) {
        while (i < j) if (s.charAt(i++) != s.charAt(j--)) return false;
        return true;
    }

    /* Approach 1 - check every ordered pair. O(n^2 * L). */
    public static List<List<Integer>> palindromePairsBrute(String[] words) {
        List<List<Integer>> out = new ArrayList<>();
        for (int i = 0; i < words.length; i++)
            for (int j = 0; j < words.length; j++) {
                String s = words[i] + words[j];
                if (i != j && isPal(s, 0, s.length() - 1)) out.add(List.of(i, j));
            }
        return out;
    }

    /*
     * Approach 2 - map each reversed word to its index. Split every word w into left + right at every position:
     *  - if left is a palindrome and reverse(right) exists as another word x, then x + w is a palindrome;
     *  - if right is a palindrome and reverse(left) exists as x, then w + x is a palindrome.
     * Skip the empty-right split in the second case to avoid reporting a pair twice. Time O(n * L^2).
     */
    public static List<List<Integer>> palindromePairs(String[] words) {
        Map<String, Integer> reversed = new HashMap<>();
        for (int i = 0; i < words.length; i++) reversed.put(new StringBuilder(words[i]).reverse().toString(), i);
        List<List<Integer>> out = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            String w = words[i];
            for (int cut = 0; cut <= w.length(); cut++) {
                String left = w.substring(0, cut), right = w.substring(cut);
                Integer j = reversed.get(left);
                if (j != null && j != i && isPal(w, cut, w.length() - 1)) out.add(List.of(i, j));        // w + words[j]
                Integer k = reversed.get(right);
                if (cut > 0 && k != null && k != i && isPal(w, 0, cut - 1)) out.add(List.of(k, i));    // words[k] + w
            }
        }
        return out;
    }
}
