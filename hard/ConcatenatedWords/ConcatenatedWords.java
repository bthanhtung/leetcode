package ConcatenatedWords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * 472. Concatenated Words — https://leetcode.com/problems/concatenated-words/
 * Return all words in the list that are made of at least two shorter words from the same list.
 * Input: ["cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"] ->
 *        ["catsdogcats","dogcatsdog","ratcatdogcat"]
 * Explanation: README.md
 */
public class ConcatenatedWords {
    public static void main(String[] args) {
        String[] words = {"cat", "cats", "catsdogcats", "dog", "dogcatsdog", "hippopotamuses", "rat", "ratcatdogcat"};
        System.out.println(findAllConcatenatedWordsInADict(words));
    }

    /*
     * Approach 1 - sort by length; a word can only be built from shorter words, so check each word with word-break DP
     * (problem 139) against the set of shorter words, then add it to the set. Time O(n * L^2).
     */
    public static List<String> findAllConcatenatedWordsInADict(String[] words) {
        String[] sorted = words.clone();
        Arrays.sort(sorted, (a, b) -> a.length() - b.length());
        Set<String> shorter = new HashSet<>();
        List<String> out = new ArrayList<>();
        for (String w : sorted) {
            if (!w.isEmpty() && canBreak(w, shorter)) out.add(w);
            shorter.add(w);
        }
        return out;
    }

    private static boolean canBreak(String w, Set<String> dict) {
        if (dict.isEmpty()) return false;
        boolean[] ok = new boolean[w.length() + 1];
        ok[0] = true;
        for (int i = 1; i <= w.length(); i++) {
            for (int j = 0; j < i && !ok[i]; j++) ok[i] = ok[j] && dict.contains(w.substring(j, i));
        }
        return ok[w.length()];
    }

    /* Approach 2 - all words in a trie; DFS from each position counting how many dictionary words were used. */
    public static List<String> findAllConcatenatedWordsTrie(String[] words) {
        Node root = new Node();
        for (String w : words) {
            Node n = root;
            for (char c : w.toCharArray()) {
                if (n.next[c - 'a'] == null) n.next[c - 'a'] = new Node();
                n = n.next[c - 'a'];
            }
            n.end = true;
        }
        List<String> out = new ArrayList<>();
        for (String w : words) {
            if (!w.isEmpty() && pieces(w, 0, root, new Boolean[w.length()]) ) out.add(w);
        }
        return out;
    }

    private static final class Node {
        final Node[] next = new Node[26];
        boolean end;
    }

    /** True if w[start..] splits into dictionary words, at least two in total when start == 0. */
    private static boolean pieces(String w, int start, Node root, Boolean[] memo) {
        if (memo[start] != null) return memo[start];
        Node n = root;
        boolean result = false;
        for (int i = start; i < w.length() && !result; i++) {
            n = n.next[w.charAt(i) - 'a'];
            if (n == null) break;
            if (n.end) {
                if (i == w.length() - 1) result = start != 0;               // the whole word alone does not count
                else result = pieces(w, i + 1, root, memo);
            }
        }
        return memo[start] = result;
    }
}
