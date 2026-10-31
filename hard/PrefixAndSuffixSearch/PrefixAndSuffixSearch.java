package PrefixAndSuffixSearch;

import java.util.HashMap;
import java.util.Map;

/*
 * 745. Prefix and Suffix Search — https://leetcode.com/problems/prefix-and-suffix-search/
 * WordFilter(words); f(pref, suff) returns the largest index of a word that has prefix pref and suffix suff, or -1.
 * Example: words = ["apple"]; f("a", "e") -> 0
 * Explanation: README.md
 */
public class PrefixAndSuffixSearch {
    public static void main(String[] args) {
        WordFilterTrie w = new WordFilterTrie(new String[]{"apple", "ape", "banana"});
        System.out.println(w.f("a", "e"));       // 1 (ape)
        System.out.println(w.f("b", "na"));      // 2
    }

    /*
     * Approach 1 - for each word insert every "suffix + '{' + word" into one trie ('{' sorts after 'z'). A query
     * f(pref, suff) walks "suff{pref". Each node stores the largest index passing through it. Query O(|pref| + |suff|).
     */
    public static class WordFilterTrie {
        private static final class Node {
            final Node[] next = new Node[27];
            int index;
        }

        private final Node root = new Node();

        public WordFilterTrie(String[] words) {
            for (int i = 0; i < words.length; i++) {
                String w = words[i] + "{";
                for (int s = 0; s < w.length(); s++) {
                    Node node = root;
                    String key = w.substring(s) + words[i];          // e.g. "le{apple"
                    for (char c : key.toCharArray()) {
                        int k = c - 'a';
                        if (node.next[k] == null) node.next[k] = new Node();
                        node = node.next[k];
                        node.index = i;                               // later words overwrite: largest index wins
                    }
                }
            }
        }

        public int f(String pref, String suff) {
            Node node = root;
            for (char c : (suff + "{" + pref).toCharArray()) {
                node = node.next[c - 'a'];
                if (node == null) return -1;
            }
            return node.index;
        }
    }

    /* Approach 2 - precompute every (prefix, suffix) pair of every word into a hash map. Words are short (<= 7). */
    public static class WordFilterMap {
        private final Map<String, Integer> best = new HashMap<>();

        public WordFilterMap(String[] words) {
            for (int i = 0; i < words.length; i++) {
                String w = words[i];
                for (int p = 0; p <= w.length(); p++)
                    for (int s = 0; s <= w.length(); s++) best.put(w.substring(0, p) + "#" + w.substring(w.length() - s), i);
            }
        }

        public int f(String pref, String suff) {
            return best.getOrDefault(pref + "#" + suff, -1);
        }
    }
}
