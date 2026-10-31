package StreamOfCharacters;

import java.util.ArrayList;
import java.util.List;

/*
 * 1032. Stream of Characters — https://leetcode.com/problems/stream-of-characters/
 * StreamChecker(words); query(letter) appends a letter to the stream and returns true if some suffix of the stream
 * equals one of the words.
 * Example: words = ["cd","f","kl"]; query a..l returns true at 'd' ("cd"), 'f' and 'l' ("kl")
 * Explanation: README.md
 */
public class StreamOfCharacters {
    public static void main(String[] args) {
        StreamChecker checker = new StreamChecker(new String[]{"cd", "f", "kl"});
        for (char c = 'a'; c <= 'l'; c++) System.out.print(checker.query(c) ? c + "! " : c + " ");   // a b c d! e f! g h i j k l!
    }

    /*
     * Approach 1 - store every word reversed in a trie; on each query, walk the trie from the newest letter backwards
     * through the stream. Cost per query = length of the longest word (not the stream length).
     */
    public static class StreamChecker {
        private static final class Node {
            final Node[] next = new Node[26];
            boolean end;
        }

        private final Node root = new Node();
        private final StringBuilder stream = new StringBuilder();
        private int maxLength;

        public StreamChecker(String[] words) {
            for (String w : words) {
                Node node = root;
                for (int i = w.length() - 1; i >= 0; i--) {
                    int c = w.charAt(i) - 'a';
                    if (node.next[c] == null) node.next[c] = new Node();
                    node = node.next[c];
                }
                node.end = true;
                maxLength = Math.max(maxLength, w.length());
            }
        }

        public boolean query(char letter) {
            stream.append(letter);
            Node node = root;
            for (int i = stream.length() - 1; i >= 0 && i >= stream.length() - maxLength; i--) {
                node = node.next[stream.charAt(i) - 'a'];
                if (node == null) return false;
                if (node.end) return true;
            }
            return false;
        }
    }

    /* Approach 2 - check every word against the end of the stream. Cost per query = total length of all words. */
    public static class StreamCheckerBrute {
        private final List<String> words = new ArrayList<>();
        private final StringBuilder stream = new StringBuilder();

        public StreamCheckerBrute(String[] words) {
            this.words.addAll(List.of(words));
        }

        public boolean query(char letter) {
            stream.append(letter);
            for (String w : words) {
                int start = stream.length() - w.length();
                if (start >= 0 && stream.indexOf(w, start) == start) return true;
            }
            return false;
        }
    }
}
