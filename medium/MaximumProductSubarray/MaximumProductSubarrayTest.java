package MaximumProductSubarray;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaximumProductSubarrayTest {

    @Test
    void examplesAndBruteForce() {
        assertEquals(6, MaximumProductSubarray.maxProduct(new int[]{2, 3, -2, 4}));
        assertEquals(0, MaximumProductSubarray.maxProduct(new int[]{-2, 0, -1}));
        assertEquals(24, MaximumProductSubarray.maxProduct(new int[]{-2, 3, -4}));
        assertEquals(-2, MaximumProductSubarray.maxProduct(new int[]{-2}));
        Random r = new Random(152);
        for (int t = 0; t < 5000; t++) {
            int[] nums = r.ints(1 + r.nextInt(10), -4, 5).toArray();
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < nums.length; i++) {
                int p = 1;
                for (int j = i; j < nums.length; j++) best = Math.max(best, p *= nums[j]);
            }
            assertEquals(best, MaximumProductSubarray.maxProduct(nums));
            assertEquals(best, MaximumProductSubarray.maxProductPrefixSuffix(nums));
        }
    }
}
