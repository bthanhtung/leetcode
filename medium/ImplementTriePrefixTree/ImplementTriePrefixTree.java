package ImplementTriePrefixTree;

import java.util.HashMap;
import java.util.Map;

/*
 * 208. Implement Trie (Prefix Tree) — https://leetcode.com/problems/implement-trie-prefix-tree/
 * Implement a Trie with insert(word), search(word) (whole word present?) and startsWith(prefix). Lowercase letters.
 * Example: insert("apple"), search("apple") -> true, search("app") -> false, startsWith("app") -> true,
 *          insert("app"), search("app") -> true
 * Explanation: README.md
 */
public class ImplementTriePrefixTree {
    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("apple");
        System.out.println(trie.search("apple"));     // true
        System.out.println(trie.search("app"));       // false
        System.out.println(trie.startsWith("app"));   // true
        trie.insert("app");
        System.out.println(trie.search("app"));       // true
    }

    /* Approach 1 - every node has 26 child slots and an end-of-word flag. Each operation O(L). Fast, memory-hungry. */
    public static class Trie {
        private static final class Node {
            final Node[] next = new Node[26];
            boolean end;
        }

        private final Node root = new Node();

        public void insert(String word) {
            Node node = root;
            for (char c : word.toCharArray()) {
                int i = c - 'a';
                if (node.next[i] == null) node.next[i] = new Node();
                node = node.next[i];
            }
            node.end = true;
        }

        public boolean search(String word) {
            Node node = walk(word);
            return node != null && node.end;
        }

        public boolean startsWith(String prefix) {
            return walk(prefix) != null;
        }

        private Node walk(String s) {
            Node node = root;
            for (char c : s.toCharArray()) {
                node = node.next[c - 'a'];
                if (node == null) return null;
            }
            return node;
        }
    }

    /* Approach 2 - children in a hash map: only existing edges cost memory, works for any alphabet. */
    public static class TrieMap {
        private static final class Node {
            final Map<Character, Node> next = new HashMap<>();
            boolean end;
        }

        private final Node root = new Node();

        public void insert(String word) {
            Node node = root;
            for (char c : word.toCharArray()) node = node.next.computeIfAbsent(c, k -> new Node());
            node.end = true;
        }

        public boolean search(String word) {
            Node node = walk(word);
            return node != null && node.end;
        }

        public boolean startsWith(String prefix) {
            return walk(prefix) != null;
        }

        private Node walk(String s) {
            Node node = root;
            for (char c : s.toCharArray()) {
                node = node.next.get(c);
                if (node == null) return null;
            }
            return node;
        }
    }
}
