package DungeonGame;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DungeonGameTest {

    /** Minimum over all monotone paths of the health needed for that path. */
    private static int brute(int[][] d, int i, int j) {
        int m = d.length, n = d[0].length;
        if (i == m - 1 && j == n - 1) return Math.max(1, 1 - d[i][j]);
        int next = Integer.MAX_VALUE;
        if (i + 1 < m) next = Math.min(next, brute(d, i + 1, j));
        if (j + 1 < n) next = Math.min(next, brute(d, i, j + 1));
        return Math.max(1, next - d[i][j]);
    }

    @Test
    void examplesAndRandom() {
        assertEquals(7, DungeonGame.calculateMinimumHP(new int[][]{{-2, -3, 3}, {-5, -10, 1}, {10, 30, -5}}));
        assertEquals(1, DungeonGame.calculateMinimumHP(new int[][]{{0}}));
        Random r = new Random(174);
        for (int t = 0; t < 500; t++) {
            int m = 1 + r.nextInt(5), n = 1 + r.nextInt(5);
            int[][] d = new int[m][n];
            for (int[] row : d) for (int j = 0; j < n; j++) row[j] = r.nextInt(21) - 12;
            int expected = brute(d, 0, 0);
            assertEquals(expected, DungeonGame.calculateMinimumHP(d));
            assertEquals(expected, DungeonGame.calculateMinimumHPBinarySearch(d));
        }
    }
}
