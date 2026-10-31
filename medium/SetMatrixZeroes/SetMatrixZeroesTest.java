package SetMatrixZeroes;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SetMatrixZeroesTest {

    @Test
    void examplesAndRandom() {
        int[][] a = {{1, 1, 1}, {1, 0, 1}, {1, 1, 1}};
        SetMatrixZeroes.setZeroes(a);
        assertArrayEquals(new int[][]{{1, 0, 1}, {0, 0, 0}, {1, 0, 1}}, a);
        Random r = new Random(73);
        for (int t = 0; t < 1000; t++) {
            int m = 1 + r.nextInt(6), n = 1 + r.nextInt(6);
            int[][] x = new int[m][n], y = new int[m][n];
            for (int i = 0; i < m; i++) for (int j = 0; j < n; j++) x[i][j] = y[i][j] = r.nextInt(100) < 12 ? 0 : 1 + r.nextInt(9);
            SetMatrixZeroes.setZeroesSets(x);
            SetMatrixZeroes.setZeroes(y);
            assertArrayEquals(x, y);
        }
    }
}
