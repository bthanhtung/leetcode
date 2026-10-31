package MinimumPathSum;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinimumPathSumTest {

    @Test
    void examplesAndRandom() {
        assertEquals(7, MinimumPathSum.minPathSum(new int[][]{{1, 3, 1}, {1, 5, 1}, {4, 2, 1}}));
        assertEquals(12, MinimumPathSum.minPathSum(new int[][]{{1, 2, 3}, {4, 5, 6}}));
        Random r = new Random(64);
        for (int t = 0; t < 500; t++) {
            int m = 1 + r.nextInt(8), n = 1 + r.nextInt(8);
            int[][] g = new int[m][n];
            for (int[] row : g) for (int j = 0; j < n; j++) row[j] = r.nextInt(10);
            int expected = MinimumPathSum.minPathSumTable(g);
            assertEquals(expected, MinimumPathSum.minPathSum(g));
            assertEquals(expected, MinimumPathSum.minPathSumMemo(g));
        }
    }
}
