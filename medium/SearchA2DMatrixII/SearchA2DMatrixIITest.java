package SearchA2DMatrixII;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchA2DMatrixIITest {

    @Test
    void examplesAndRandom() {
        int[][] m = {{1, 4, 7, 11, 15}, {2, 5, 8, 12, 19}, {3, 6, 9, 16, 22}, {10, 13, 14, 17, 24}, {18, 21, 23, 26, 30}};
        assertEquals(true, SearchA2DMatrixII.searchMatrix(m, 5));
        assertEquals(false, SearchA2DMatrixII.searchMatrix(m, 20));
        Random r = new Random(240);
        for (int t = 0; t < 500; t++) {
            int rows = 1 + r.nextInt(6), cols = 1 + r.nextInt(6);
            int[][] g = new int[rows][cols];
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < cols; j++)
                    g[i][j] = Math.max(i > 0 ? g[i - 1][j] : 0, j > 0 ? g[i][j - 1] : 0) + r.nextInt(3);
            for (int target = -1; target <= g[rows - 1][cols - 1] + 1; target++) {
                assertEquals(SearchA2DMatrixII.searchMatrixBinary(g, target), SearchA2DMatrixII.searchMatrix(g, target));
            }
        }
    }
}
