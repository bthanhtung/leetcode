package DesignAddAndSearchWordsDataStructure;

/*
 * 211. Design Add and Search Words Data Structure — https://leetcode.com/problems/design-add-and-search-words-data-structure/
 * WordDictionary: addWord(word); search(word) where '.' matches any single letter.
 * Input: add "bad","dad","mad"; search "pad" -> false, "bad" -> true, ".ad" -> true, "b.." -> true
 * Explanation: README.md
 */
public class DesignAddAndSearchWordsDataStructure {
    public static void main(String[] args) {
        WordDictionary d = new WordDictionary();
        d.addWord("bad");
        d.addWord("dad");
        System.out.println(d.search(".ad"));     // true
        System.out.println(d.search("b.."));     // true
    }

    /* Trie; a '.' branches into all children. addWord O(L); search O(L) without dots, up to O(26^dots * L) with. */
    public static class WordDictionary {
        private static final class Node {
            final Node[] next = new Node[26];
            boolean end;
        }

        private final Node root = new Node();

        public void addWord(String word) {
            Node n = root;
            for (char c : word.toCharArray()) {
                if (n.next[c - 'a'] == null) n.next[c - 'a'] = new Node();
                n = n.next[c - 'a'];
            }
            n.end = true;
        }

        public boolean search(String word) {
            return search(word, 0, root);
        }

        private boolean search(String word, int i, Node n) {
            if (i == word.length()) return n.end;
            char c = word.charAt(i);
            if (c != '.') {
                Node child = n.next[c - 'a'];
                return child != null && search(word, i + 1, child);
            }
            for (Node child : n.next) {
                if (child != null && search(word, i + 1, child)) return true;
            }
            return false;
        }
    }
}
