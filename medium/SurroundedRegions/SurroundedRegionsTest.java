package SurroundedRegions;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SurroundedRegionsTest {

    /** Reference: an 'O' survives iff a BFS from it reaches the border. */
    private static char[][] reference(char[][] b) {
        int m = b.length, n = b[0].length;
        char[][] out = new char[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) out[i][j] = b[i][j] == 'O' && reachesBorder(b, i, j) ? 'O' : 'X';
        return out;
    }

    private static boolean reachesBorder(char[][] b, int si, int sj) {
        int m = b.length, n = b[0].length;
        boolean[][] seen = new boolean[m][n];
        java.util.ArrayDeque<int[]> q = new java.util.ArrayDeque<>();
        q.add(new int[]{si, sj});
        seen[si][sj] = true;
        while (!q.isEmpty()) {
            int[] c = q.poll();
            if (c[0] == 0 || c[1] == 0 || c[0] == m - 1 || c[1] == n - 1) return true;
            int[][] d = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            for (int[] dd : d) {
                int a = c[0] + dd[0], e = c[1] + dd[1];
                if (!seen[a][e] && b[a][e] == 'O') { seen[a][e] = true; q.add(new int[]{a, e}); }
            }
        }
        return false;
    }

    @Test
    void exampleAndRandom() {
        char[][] b = {"XXXX".toCharArray(), "XOOX".toCharArray(), "XXOX".toCharArray(), "XOXX".toCharArray()};
        SurroundedRegions.solve(b);
        assertArrayEquals(new char[][]{"XXXX".toCharArray(), "XXXX".toCharArray(), "XXXX".toCharArray(), "XOXX".toCharArray()}, b);
        Random r = new Random(130);
        for (int t = 0; t < 1000; t++) {
            char[][] g = new char[1 + r.nextInt(7)][1 + r.nextInt(7)];
            for (char[] row : g) for (int j = 0; j < row.length; j++) row[j] = r.nextBoolean() ? 'O' : 'X';
            char[][] expected = reference(g);
            SurroundedRegions.solve(g);
            assertArrayEquals(expected, g);
        }
        char[][] big = new char[300][300];
        for (char[] row : big) java.util.Arrays.fill(row, 'O');
        SurroundedRegions.solve(big);                      // one huge region touching the border: must not overflow the stack
        assertArrayEquals(new char[]{'O'}, new char[]{big[150][150]});
    }
}
