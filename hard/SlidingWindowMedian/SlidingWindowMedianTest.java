package SlidingWindowMedian;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SlidingWindowMedianTest {

    @Test
    void examplesAndRandom() {
        assertArrayEquals(new double[]{1, -1, -1, 3, 5, 6}, SlidingWindowMedian.medianSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3), 1e-9);
        assertArrayEquals(new double[]{2, 3, 3, 3, 2, 3, 2}, SlidingWindowMedian.medianSlidingWindow(new int[]{1, 2, 3, 4, 2, 3, 1, 4, 2}, 3), 1e-9);
        assertArrayEquals(new double[]{2147483647.0}, SlidingWindowMedian.medianSlidingWindow(new int[]{2147483647, 2147483647}, 2), 1e-9);
        Random r = new Random(480);
        for (int t = 0; t < 2000; t++) {
            int[] nums = r.ints(1 + r.nextInt(15), -5, 6).toArray();
            int k = 1 + r.nextInt(nums.length);
            assertArrayEquals(SlidingWindowMedian.medianSlidingWindowSorted(nums, k), SlidingWindowMedian.medianSlidingWindow(nums, k), 1e-9);
        }
    }
}
