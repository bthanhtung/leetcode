package UniquePathsII;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UniquePathsIITest {

    private static int count(int[][] g, int i, int j) {
        if (i >= g.length || j >= g[0].length || g[i][j] == 1) return 0;
        if (i == g.length - 1 && j == g[0].length - 1) return 1;
        return count(g, i + 1, j) + count(g, i, j + 1);
    }

    @Test
    void examplesAndRandom() {
        assertEquals(2, UniquePathsII.uniquePathsWithObstacles(new int[][]{{0, 0, 0}, {0, 1, 0}, {0, 0, 0}}));
        assertEquals(1, UniquePathsII.uniquePathsWithObstacles(new int[][]{{0, 1}, {0, 0}}));
        assertEquals(0, UniquePathsII.uniquePathsWithObstacles(new int[][]{{1}}));
        assertEquals(0, UniquePathsII.uniquePathsWithObstacles(new int[][]{{0, 0}, {0, 1}}));
        Random r = new Random(63);
        for (int t = 0; t < 2000; t++) {
            int[][] g = new int[1 + r.nextInt(6)][1 + r.nextInt(6)];
            for (int[] row : g) for (int j = 0; j < row.length; j++) row[j] = r.nextInt(4) == 0 ? 1 : 0;
            assertEquals(count(g, 0, 0), UniquePathsII.uniquePathsWithObstacles(g));
        }
    }
}
