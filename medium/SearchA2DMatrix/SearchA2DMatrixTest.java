package SearchA2DMatrix;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchA2DMatrixTest {

    @Test
    void examplesAndRandom() {
        int[][] m = {{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};
        assertEquals(true, SearchA2DMatrix.searchMatrix(m, 3));
        assertEquals(false, SearchA2DMatrix.searchMatrix(m, 13));
        Random r = new Random(74);
        for (int t = 0; t < 300; t++) {
            int rows = 1 + r.nextInt(5), cols = 1 + r.nextInt(5);
            int[] values = r.ints(rows * cols, -50, 50).distinct().sorted().toArray();
            if (values.length < rows * cols) continue;
            int[][] g = new int[rows][cols];
            for (int k = 0; k < values.length; k++) g[k / cols][k % cols] = values[k];
            for (int target = -52; target <= 52; target++) {
                boolean expected = java.util.Arrays.binarySearch(values, target) >= 0;
                assertEquals(expected, SearchA2DMatrix.searchMatrix(g, target));
                assertEquals(expected, SearchA2DMatrix.searchMatrixTwoStep(g, target));
            }
        }
    }
}
