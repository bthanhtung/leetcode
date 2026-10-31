package LongestIncreasingPathInAMatrix;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LongestIncreasingPathInAMatrixTest {

    @Test
    void examplesAndRandom() {
        assertEquals(4, LongestIncreasingPathInAMatrix.longestIncreasingPath(new int[][]{{9, 9, 4}, {6, 6, 8}, {2, 1, 1}}));
        assertEquals(4, LongestIncreasingPathInAMatrix.longestIncreasingPath(new int[][]{{3, 4, 5}, {3, 2, 6}, {2, 2, 1}}));
        assertEquals(1, LongestIncreasingPathInAMatrix.longestIncreasingPath(new int[][]{{1}}));
        Random r = new Random(329);
        for (int t = 0; t < 500; t++) {
            int m = 1 + r.nextInt(7), n = 1 + r.nextInt(7);
            int[][] g = new int[m][n];
            for (int[] row : g) for (int j = 0; j < n; j++) row[j] = r.nextInt(10);
            assertEquals(LongestIncreasingPathInAMatrix.longestIncreasingPath(g), LongestIncreasingPathInAMatrix.longestIncreasingPathTopo(g));
        }
    }
}
