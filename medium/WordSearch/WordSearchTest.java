package WordSearch;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordSearchTest {

    private static boolean brute(char[][] b, String w) {
        int m = b.length, n = b[0].length;
        for (int s = 0; s < m * n; s++) if (path(b, w, 0, s, new boolean[m * n])) return true;
        return false;
    }

    private static boolean path(char[][] b, String w, int k, int cell, boolean[] used) {
        int n = b[0].length, i = cell / n, j = cell % n;
        if (used[cell] || b[i][j] != w.charAt(k)) return false;
        if (k == w.length() - 1) return true;
        used[cell] = true;
        int[][] d = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] dd : d) {
            int a = i + dd[0], c = j + dd[1];
            if (a >= 0 && c >= 0 && a < b.length && c < n && path(b, w, k + 1, a * n + c, used)) { used[cell] = false; return true; }
        }
        used[cell] = false;
        return false;
    }

    @Test
    void examplesAndRandom() {
        char[][] b = {"ABCE".toCharArray(), "SFCS".toCharArray(), "ADEE".toCharArray()};
        assertEquals(true, WordSearch.exist(b, "ABCCED"));
        assertEquals(true, WordSearch.exist(b, "SEE"));
        assertEquals(false, WordSearch.exist(b, "ABCB"));
        assertEquals("ABCE", new String(b[0]));        // board restored
        Random r = new Random(79);
        for (int t = 0; t < 1500; t++) {
            char[][] g = new char[1 + r.nextInt(3)][1 + r.nextInt(3)];
            for (char[] row : g) for (int j = 0; j < row.length; j++) row[j] = (char) ('a' + r.nextInt(2));
            StringBuilder w = new StringBuilder();
            for (int k = 1 + r.nextInt(5); k > 0; k--) w.append((char) ('a' + r.nextInt(2)));
            assertEquals(brute(g, w.toString()), WordSearch.exist(g, w.toString()));
        }
    }
}
