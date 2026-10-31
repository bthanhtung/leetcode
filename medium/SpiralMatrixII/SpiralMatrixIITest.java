package SpiralMatrixII;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SpiralMatrixIITest {

    @Test
    void examplesAndAgreement() {
        assertArrayEquals(new int[][]{{1, 2, 3}, {8, 9, 4}, {7, 6, 5}}, SpiralMatrixII.generateMatrix(3));
        assertArrayEquals(new int[][]{{1}}, SpiralMatrixII.generateMatrix(1));
        assertArrayEquals(new int[][]{{1, 2, 3, 4}, {12, 13, 14, 5}, {11, 16, 15, 6}, {10, 9, 8, 7}}, SpiralMatrixII.generateMatrix(4));
        for (int n = 1; n <= 20; n++) {
            assertArrayEquals(SpiralMatrixII.generateMatrix(n), SpiralMatrixII.generateMatrixTurns(n));
        }
    }
}
