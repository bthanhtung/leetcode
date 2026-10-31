package MaximumProductOfThreeNumbers;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaximumProductOfThreeNumbersTest {

    @Test
    void examplesAndRandom() {
        assertEquals(6, MaximumProductOfThreeNumbers.maximumProduct(new int[]{1, 2, 3}));
        assertEquals(24, MaximumProductOfThreeNumbers.maximumProduct(new int[]{1, 2, 3, 4}));
        assertEquals(-6, MaximumProductOfThreeNumbers.maximumProduct(new int[]{-1, -2, -3}));
        assertEquals(500, MaximumProductOfThreeNumbers.maximumProduct(new int[]{-10, -10, 5, 2}));
        Random r = new Random(628);
        for (int t = 0; t < 3000; t++) {
            int[] nums = r.ints(3 + r.nextInt(10), -1000, 1001).toArray();
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < nums.length; i++)
                for (int j = i + 1; j < nums.length; j++)
                    for (int k = j + 1; k < nums.length; k++) best = Math.max(best, nums[i] * nums[j] * nums[k]);
            assertEquals(best, MaximumProductOfThreeNumbers.maximumProduct(nums));
            assertEquals(best, MaximumProductOfThreeNumbers.maximumProductSort(nums));
        }
    }
}
