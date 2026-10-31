package MaxAreaOfIsland;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaxAreaOfIslandTest {

    @Test
    void examplesAndRandom() {
        int[][] example = {
                {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}};
        assertEquals(6, MaxAreaOfIsland.maxAreaOfIsland(example));
        assertEquals(0, MaxAreaOfIsland.maxAreaOfIsland(new int[][]{{0, 0, 0, 0, 0, 0, 0, 0}}));
        Random r = new Random(695);
        for (int t = 0; t < 500; t++) {
            int m = 1 + r.nextInt(8), n = 1 + r.nextInt(8);
            int[][] g = new int[m][n];
            for (int[] row : g) for (int j = 0; j < n; j++) row[j] = r.nextInt(100) < 55 ? 1 : 0;
            int expected = MaxAreaOfIsland.maxAreaOfIsland(g);
            assertEquals(expected, MaxAreaOfIsland.maxAreaOfIslandBfs(g));
            assertEquals(expected, MaxAreaOfIsland.maxAreaOfIslandUnionFind(g));
        }
    }
}
