package ShortestSubarrayWithSumAtLeastK;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShortestSubarrayWithSumAtLeastKTest {

    @Test
    void examplesAndBruteForce() {
        assertEquals(1, ShortestSubarrayWithSumAtLeastK.shortestSubarray(new int[]{1}, 1));
        assertEquals(-1, ShortestSubarrayWithSumAtLeastK.shortestSubarray(new int[]{1, 2}, 4));
        assertEquals(3, ShortestSubarrayWithSumAtLeastK.shortestSubarray(new int[]{2, -1, 2}, 3));
        assertEquals(1, ShortestSubarrayWithSumAtLeastK.shortestSubarray(new int[]{84, -37, 32, 40, 95}, 95));
        Random r = new Random(862);
        for (int t = 0; t < 5000; t++) {
            int[] nums = r.ints(1 + r.nextInt(12), -5, 8).toArray();
            int k = 1 + r.nextInt(15), best = Integer.MAX_VALUE;
            for (int i = 0; i < nums.length; i++) {
                long s = 0;
                for (int j = i; j < nums.length; j++) if ((s += nums[j]) >= k) { best = Math.min(best, j - i + 1); break; }
            }
            assertEquals(best == Integer.MAX_VALUE ? -1 : best, ShortestSubarrayWithSumAtLeastK.shortestSubarray(nums, k));
        }
    }
}
