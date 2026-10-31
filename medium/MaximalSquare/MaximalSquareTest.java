package MaximalSquare;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaximalSquareTest {

    private static int brute(char[][] g) {
        int best = 0;
        for (int i = 0; i < g.length; i++)
            for (int j = 0; j < g[0].length; j++)
                for (int k = 1; i + k <= g.length && j + k <= g[0].length; k++) {
                    boolean ok = true;
                    for (int a = i; a < i + k && ok; a++) for (int b = j; b < j + k; b++) if (g[a][b] != '1') { ok = false; break; }
                    if (ok) best = Math.max(best, k * k);
                }
        return best;
    }

    @Test
    void exampleAndRandom() {
        char[][] m = {"10100".toCharArray(), "10111".toCharArray(), "11111".toCharArray(), "10010".toCharArray()};
        assertEquals(4, MaximalSquare.maximalSquare(m));
        assertEquals(1, MaximalSquare.maximalSquare(new char[][]{"01".toCharArray(), "10".toCharArray()}));
        assertEquals(0, MaximalSquare.maximalSquare(new char[][]{"0".toCharArray()}));
        Random r = new Random(221);
        for (int t = 0; t < 1000; t++) {
            char[][] g = new char[1 + r.nextInt(6)][1 + r.nextInt(6)];
            for (char[] row : g) for (int j = 0; j < row.length; j++) row[j] = r.nextInt(4) == 0 ? '0' : '1';
            int expected = brute(g);
            assertEquals(expected, MaximalSquare.maximalSquare(g));
            assertEquals(expected, MaximalSquare.maximalSquareRow(g));
        }
    }
}
