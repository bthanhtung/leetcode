package GameOfLife;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class GameOfLifeTest {

    @Test
    void examplesAndRandom() {
        int[][] board = {{0, 1, 0}, {0, 0, 1}, {1, 1, 1}, {0, 0, 0}};
        GameOfLife.gameOfLife(board);
        assertArrayEquals(new int[][]{{0, 0, 0}, {1, 0, 1}, {0, 1, 1}, {0, 1, 0}}, board);
        int[][] block = {{1, 1}, {1, 0}};
        GameOfLife.gameOfLife(block);
        assertArrayEquals(new int[][]{{1, 1}, {1, 1}}, block);
        Random r = new Random(289);
        for (int t = 0; t < 500; t++) {
            int m = 1 + r.nextInt(7), n = 1 + r.nextInt(7);
            int[][] a = new int[m][n];
            for (int[] row : a) for (int j = 0; j < n; j++) row[j] = r.nextInt(2);
            int[][] b = new int[m][];
            for (int i = 0; i < m; i++) b[i] = a[i].clone();
            GameOfLife.gameOfLifeCopy(a);
            GameOfLife.gameOfLife(b);
            assertArrayEquals(a, b);
        }
    }
}
