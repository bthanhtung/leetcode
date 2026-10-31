package SpiralMatrix;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SpiralMatrixTest {

    @Test
    void examplesAndAllShapes() {
        assertEquals(List.of(1, 2, 3, 6, 9, 8, 7, 4, 5), SpiralMatrix.spiralOrder(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}));
        assertEquals(List.of(1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7), SpiralMatrix.spiralOrder(new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}}));
        for (int m = 1; m <= 8; m++) {
            for (int n = 1; n <= 8; n++) {
                int[][] g = new int[m][n];
                for (int i = 0; i < m; i++) for (int j = 0; j < n; j++) g[i][j] = i * n + j;
                List<Integer> a = SpiralMatrix.spiralOrder(g);
                assertEquals(m * n, a.size());
                assertEquals(SpiralMatrix.spiralOrderTurns(g), a);
            }
        }
    }
}
