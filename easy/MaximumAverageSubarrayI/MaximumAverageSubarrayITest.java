package MaximumAverageSubarrayI;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaximumAverageSubarrayITest {

    @Test
    void examplesAndRandom() {
        assertEquals(12.75, MaximumAverageSubarrayI.findMaxAverage(new int[]{1, 12, -5, -6, 50, 3}, 4), 1e-9);
        assertEquals(5.0, MaximumAverageSubarrayI.findMaxAverage(new int[]{5}, 1), 1e-9);
        Random r = new Random(643);
        for (int t = 0; t < 2000; t++) {
            int[] nums = r.ints(1 + r.nextInt(15), -10_000, 10_001).toArray();
            int k = 1 + r.nextInt(nums.length);
            assertEquals(MaximumAverageSubarrayI.findMaxAveragePrefix(nums, k), MaximumAverageSubarrayI.findMaxAverage(nums, k), 1e-9);
        }
    }
}
