package SplitArrayLargestSum;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SplitArrayLargestSumTest {

    @Test
    void examplesAndRandom() {
        assertEquals(18, SplitArrayLargestSum.splitArray(new int[]{7, 2, 5, 10, 8}, 2));
        assertEquals(9, SplitArrayLargestSum.splitArray(new int[]{1, 2, 3, 4, 5}, 2));
        assertEquals(4, SplitArrayLargestSum.splitArray(new int[]{1, 4, 4}, 3));
        Random r = new Random(410);
        for (int t = 0; t < 1000; t++) {
            int[] nums = r.ints(1 + r.nextInt(12), 0, 30).toArray();
            int k = 1 + r.nextInt(nums.length);
            assertEquals(SplitArrayLargestSum.splitArrayDp(nums, k), SplitArrayLargestSum.splitArray(nums, k));
        }
    }
}
