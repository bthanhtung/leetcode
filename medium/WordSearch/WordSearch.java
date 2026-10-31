package WordSearch;

/*
 * 79. Word Search — https://leetcode.com/problems/word-search/
 * Does word exist in the grid? Letters must be sequentially adjacent (up/down/left/right), each cell used once.
 * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], "ABCCED" -> true, "SEE" -> true, "ABCB" -> false
 * Explanation: README.md
 */
public class WordSearch {
    public static void main(String[] args) {
        char[][] b = {"ABCE".toCharArray(), "SFCS".toCharArray(), "ADEE".toCharArray()};
        System.out.println(exist(b, "ABCCED"));     // true
        System.out.println(exist(b, "ABCB"));       // false
    }

    /*
     * Try every cell as the start; DFS to neighbours matching the next letter. Mark the cell with '#' while it is on
     * the path and restore it on the way back. Time O(m * n * 3^L).
     */
    public static boolean exist(char[][] board, String word) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (dfs(board, word, 0, i, j)) return true;
            }
        }
        return false;
    }

    private static boolean dfs(char[][] b, String word, int k, int i, int j) {
        if (k == word.length()) return true;
        if (i < 0 || j < 0 || i == b.length || j == b[0].length || b[i][j] != word.charAt(k)) return false;
        char saved = b[i][j];
        b[i][j] = '#';
        boolean found = dfs(b, word, k + 1, i + 1, j) || dfs(b, word, k + 1, i - 1, j)
                || dfs(b, word, k + 1, i, j + 1) || dfs(b, word, k + 1, i, j - 1);
        b[i][j] = saved;
        return found;
    }
}
