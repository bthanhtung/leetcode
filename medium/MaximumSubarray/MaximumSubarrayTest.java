package MaximumSubarray;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaximumSubarrayTest {

    @Test
    void examplesAndRandom() {
        assertEquals(6, MaximumSubarray.maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
        assertEquals(1, MaximumSubarray.maxSubArray(new int[]{1}));
        assertEquals(23, MaximumSubarray.maxSubArray(new int[]{5, 4, -1, 7, 8}));
        assertEquals(-1, MaximumSubarray.maxSubArray(new int[]{-3, -1, -2}));
        Random r = new Random(53);
        for (int t = 0; t < 2000; t++) {
            int[] nums = r.ints(1 + r.nextInt(20), -10, 10).toArray();
            int expected = MaximumSubarray.maxSubArrayBruteForce(nums);
            assertEquals(expected, MaximumSubarray.maxSubArray(nums));
            assertEquals(expected, MaximumSubarray.maxSubArrayDivide(nums));
            assertEquals(expected, MaximumSubarray.maxSubArrayPrefix(nums));
        }
    }
}
