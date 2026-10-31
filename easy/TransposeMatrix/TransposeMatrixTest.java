package TransposeMatrix;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class TransposeMatrixTest {

    @Test
    void examples() {
        int[][] square = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}, wide = {{1, 2, 3}, {4, 5, 6}};
        assertArrayEquals(new int[][]{{1, 4, 7}, {2, 5, 8}, {3, 6, 9}}, TransposeMatrix.transpose(square));
        assertArrayEquals(new int[][]{{1, 4}, {2, 5}, {3, 6}}, TransposeMatrix.transpose(wide));
        assertArrayEquals(TransposeMatrix.transpose(wide), TransposeMatrix.transposeStream(wide));
        assertArrayEquals(wide, TransposeMatrix.transpose(TransposeMatrix.transpose(wide)));
    }
}
