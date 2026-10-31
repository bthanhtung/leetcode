package MinimumSizeSubarraySum;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinimumSizeSubarraySumTest {

    @Test
    void examplesAndRandom() {
        assertEquals(2, MinimumSizeSubarraySum.minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3}));
        assertEquals(1, MinimumSizeSubarraySum.minSubArrayLen(4, new int[]{1, 4, 4}));
        assertEquals(0, MinimumSizeSubarraySum.minSubArrayLen(11, new int[]{1, 1, 1, 1, 1, 1, 1, 1}));
        Random r = new Random(209);
        for (int t = 0; t < 2000; t++) {
            int[] nums = r.ints(1 + r.nextInt(15), 1, 10).toArray();
            int target = 1 + r.nextInt(40);
            int expected = MinimumSizeSubarraySum.minSubArrayLenBruteForce(target, nums);
            assertEquals(expected, MinimumSizeSubarraySum.minSubArrayLen(target, nums));
            assertEquals(expected, MinimumSizeSubarraySum.minSubArrayLenBinarySearch(target, nums));
        }
    }
}
