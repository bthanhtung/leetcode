package SquaresOfASortedArray;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SquaresOfASortedArrayTest {

    @Test
    void examplesAndRandom() {
        assertArrayEquals(new int[]{0, 1, 9, 16, 100}, SquaresOfASortedArray.sortedSquares(new int[]{-4, -1, 0, 3, 10}));
        assertArrayEquals(new int[]{4, 9, 9, 49, 121}, SquaresOfASortedArray.sortedSquares(new int[]{-7, -3, 2, 3, 11}));
        Random r = new Random(977);
        for (int t = 0; t < 1000; t++) {
            int[] nums = r.ints(1 + r.nextInt(15), -10_000, 10_001).sorted().toArray();
            assertArrayEquals(SquaresOfASortedArray.sortedSquaresSort(nums), SquaresOfASortedArray.sortedSquares(nums));
        }
    }
}
