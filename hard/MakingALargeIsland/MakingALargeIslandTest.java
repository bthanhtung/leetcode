package MakingALargeIsland;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MakingALargeIslandTest {

    @Test
    void examplesAndRandom() {
        assertEquals(3, MakingALargeIsland.largestIsland(new int[][]{{1, 0}, {0, 1}}));
        assertEquals(4, MakingALargeIsland.largestIsland(new int[][]{{1, 1}, {1, 0}}));
        assertEquals(4, MakingALargeIsland.largestIsland(new int[][]{{1, 1}, {1, 1}}));
        assertEquals(1, MakingALargeIsland.largestIsland(new int[][]{{0}}));
        Random r = new Random(827);
        for (int t = 0; t < 500; t++) {
            int n = 1 + r.nextInt(7);
            int[][] g = new int[n][n];
            for (int[] row : g) for (int j = 0; j < n; j++) row[j] = r.nextInt(100) < 50 ? 1 : 0;
            assertEquals(MakingALargeIsland.largestIslandBrute(g), MakingALargeIsland.largestIsland(g));
        }
    }
}
