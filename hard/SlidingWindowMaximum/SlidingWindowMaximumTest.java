package SlidingWindowMaximum;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SlidingWindowMaximumTest {

    @Test
    void examplesAndRandom() {
        assertArrayEquals(new int[]{3, 3, 5, 5, 6, 7}, SlidingWindowMaximum.maxSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3));
        assertArrayEquals(new int[]{1}, SlidingWindowMaximum.maxSlidingWindow(new int[]{1}, 1));
        Random r = new Random(239);
        for (int t = 0; t < 2000; t++) {
            int[] nums = r.ints(1 + r.nextInt(20), -10, 10).toArray();
            int k = 1 + r.nextInt(nums.length);
            int[] expected = SlidingWindowMaximum.maxSlidingWindowBrute(nums, k);
            assertArrayEquals(expected, SlidingWindowMaximum.maxSlidingWindow(nums, k));
            assertArrayEquals(expected, SlidingWindowMaximum.maxSlidingWindowBlocks(nums, k));
        }
    }
}
