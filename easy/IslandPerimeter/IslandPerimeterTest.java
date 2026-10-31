package IslandPerimeter;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IslandPerimeterTest {

    @Test
    void examplesAndRandomIslands() {
        int[][] grid = {{0, 1, 0, 0}, {1, 1, 1, 0}, {0, 1, 0, 0}, {1, 1, 0, 0}};
        assertEquals(16, IslandPerimeter.islandPerimeter(grid));
        assertEquals(4, IslandPerimeter.islandPerimeter(new int[][]{{1}}));
        assertEquals(4, IslandPerimeter.islandPerimeterDfs(new int[][]{{1, 0}}));
        Random r = new Random(463);
        for (int t = 0; t < 300; t++) {
            int m = 1 + r.nextInt(6), n = 1 + r.nextInt(6);
            int[][] g = new int[m][n];
            int i = r.nextInt(m), j = r.nextInt(n);
            for (int step = 0; step < 15; step++) {          // random walk keeps the island connected
                g[i][j] = 1;
                int d = r.nextInt(4);
                i = Math.max(0, Math.min(m - 1, i + (d == 0 ? 1 : d == 1 ? -1 : 0)));
                j = Math.max(0, Math.min(n - 1, j + (d == 2 ? 1 : d == 3 ? -1 : 0)));
            }
            int expected = IslandPerimeter.islandPerimeter(g);
            assertEquals(expected, IslandPerimeter.islandPerimeterShared(g));
            assertEquals(expected, IslandPerimeter.islandPerimeterDfs(g));
        }
    }
}
