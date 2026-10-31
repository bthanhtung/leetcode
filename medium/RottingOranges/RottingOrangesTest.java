package RottingOranges;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RottingOrangesTest {

    @Test
    void examplesAndRandom() {
        assertEquals(4, RottingOranges.orangesRotting(new int[][]{{2, 1, 1}, {1, 1, 0}, {0, 1, 1}}));
        assertEquals(-1, RottingOranges.orangesRotting(new int[][]{{2, 1, 1}, {0, 1, 1}, {1, 0, 1}}));
        assertEquals(0, RottingOranges.orangesRotting(new int[][]{{0, 2}}));
        assertEquals(0, RottingOranges.orangesRotting(new int[][]{{0}}));
        Random r = new Random(994);
        for (int t = 0; t < 1000; t++) {
            int m = 1 + r.nextInt(6), n = 1 + r.nextInt(6);
            int[][] g = new int[m][n];
            for (int[] row : g) for (int j = 0; j < n; j++) row[j] = r.nextInt(10) < 2 ? 0 : r.nextInt(10) < 2 ? 2 : 1;
            assertEquals(RottingOranges.orangesRottingSimulation(g), RottingOranges.orangesRotting(g));
        }
    }
}
