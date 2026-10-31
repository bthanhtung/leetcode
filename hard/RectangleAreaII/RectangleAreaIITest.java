package RectangleAreaII;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RectangleAreaIITest {

    private static int paint(int[][] rects) {
        boolean[][] grid = new boolean[20][20];
        for (int[] r : rects) for (int x = r[0]; x < r[2]; x++) for (int y = r[1]; y < r[3]; y++) grid[x][y] = true;
        int area = 0;
        for (boolean[] row : grid) for (boolean b : row) if (b) area++;
        return area;
    }

    @Test
    void examplesAndRandom() {
        assertEquals(6, RectangleAreaII.rectangleArea(new int[][]{{0, 0, 2, 2}, {1, 0, 2, 3}, {1, 0, 3, 1}}));
        assertEquals(49, RectangleAreaII.rectangleArea(new int[][]{{0, 0, 1000000000, 1000000000}}));
        assertEquals(49, RectangleAreaII.rectangleAreaSweep(new int[][]{{0, 0, 1000000000, 1000000000}}));
        Random r = new Random(850);
        for (int t = 0; t < 500; t++) {
            int[][] rects = new int[1 + r.nextInt(6)][];
            for (int i = 0; i < rects.length; i++) {
                int x1 = r.nextInt(19), y1 = r.nextInt(19);
                rects[i] = new int[]{x1, y1, x1 + 1 + r.nextInt(20 - x1 - 1), y1 + 1 + r.nextInt(20 - y1 - 1)};
            }
            int expected = paint(rects);
            assertEquals(expected, RectangleAreaII.rectangleArea(rects));
            assertEquals(expected, RectangleAreaII.rectangleAreaSweep(rects));
        }
    }
}
