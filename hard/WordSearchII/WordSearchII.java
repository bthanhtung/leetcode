package WordSearchII;

import java.util.ArrayList;
import java.util.List;

/*
 * 212. Word Search II — https://leetcode.com/problems/word-search-ii/
 * Return every word from the list that can be formed on the board by a path of horizontally/vertically adjacent cells,
 * each cell used at most once per word.
 * Input: board = [["o","a","a","n"],["e","t","a","e"],["i","h","k","r"],["i","f","l","v"]],
 *        words = ["oath","pea","eat","rain"] -> ["eat","oath"]
 * Explanation: README.md
 */
public class WordSearchII {
    private static final int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public static void main(String[] args) {
        char[][] board = {"oaan".toCharArray(), "etae".toCharArray(), "ihkr".toCharArray(), "iflv".toCharArray()};
        System.out.println(findWords(board, new String[]{"oath", "pea", "eat", "rain"}));      // [oath, eat]
    }

    private static final class Node {
        final Node[] next = new Node[26];
        String word;                                          // set at the node where a word ends
        int below;                                            // words still to be found in this subtree
    }

    /*
     * Approach 1 - all words in a trie; DFS from every cell walks the board and the trie together, so one walk checks
     * all words sharing a prefix. Found words are removed from the trie (below counters) to prune later searches.
     */
    public static List<String> findWords(char[][] board, String[] words) {
        Node root = new Node();
        for (String w : words) {
            Node n = root;
            n.below++;
            for (char c : w.toCharArray()) {
                if (n.next[c - 'a'] == null) n.next[c - 'a'] = new Node();
                n = n.next[c - 'a'];
                n.below++;
            }
            if (n.word != null) {                             // duplicate word in the input: undo the counts
                Node m = root;
                m.below--;
                for (char c : w.toCharArray()) {
                    m = m.next[c - 'a'];
                    m.below--;
                }
            }
            n.word = w;
        }
        List<String> out = new ArrayList<>();
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++) dfs(board, i, j, root, out);
        return out;
    }

    /** Returns how many words were found in this call, so ancestors can decrement their counters. */
    private static int dfs(char[][] b, int i, int j, Node parent, List<String> out) {
        if (i < 0 || j < 0 || i >= b.length || j >= b[0].length || b[i][j] == '#') return 0;
        Node node = parent.next[b[i][j] - 'a'];
        if (node == null || node.below == 0) return 0;
        int found = 0;
        if (node.word != null) {
            out.add(node.word);
            node.word = null;
            found++;
        }
        char c = b[i][j];
        b[i][j] = '#';
        for (int[] d : DIRS) found += dfs(b, i + d[0], j + d[1], node, out);
        b[i][j] = c;
        node.below -= found;
        return found;
    }

    /* Approach 2 - run Word Search I (problem 79) for each word separately. Simple, slow when there are many words. */
    public static List<String> findWordsEach(char[][] board, String[] words) {
        List<String> out = new ArrayList<>();
        for (String w : words) {
            if (out.contains(w)) continue;
            boolean found = false;
            for (int i = 0; i < board.length && !found; i++)
                for (int j = 0; j < board[0].length && !found; j++) found = exists(board, w, 0, i, j);
            if (found) out.add(w);
        }
        return out;
    }

    private static boolean exists(char[][] b, String w, int k, int i, int j) {
        if (k == w.length()) return true;
        if (i < 0 || j < 0 || i >= b.length || j >= b[0].length || b[i][j] != w.charAt(k)) return false;
        char c = b[i][j];
        b[i][j] = '#';
        boolean ok = false;
        for (int[] d : DIRS) ok = ok || exists(b, w, k + 1, i + d[0], j + d[1]);
        b[i][j] = c;
        return ok;
    }
}
