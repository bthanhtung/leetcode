package ReversePairs;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReversePairsTest {

    @Test
    void examplesAndBruteForce() {
        assertEquals(2, ReversePairs.reversePairs(new int[]{1, 3, 2, 3, 1}));
        assertEquals(3, ReversePairs.reversePairs(new int[]{2, 4, 3, 5, 1}));
        assertEquals(0, ReversePairs.reversePairs(new int[]{2147483647, 2147483647, 2147483647}));
        assertEquals(6, ReversePairs.reversePairs(new int[]{-5, -5, -5, Integer.MIN_VALUE}));      // -5 > 2 * -5 too
        Random r = new Random(493);
        for (int t = 0; t < 3000; t++) {
            int[] nums = t % 2 == 0 ? r.ints(r.nextInt(20), -10, 11).toArray() : r.ints(r.nextInt(20)).toArray();
            int brute = 0;
            for (int i = 0; i < nums.length; i++)
                for (int j = i + 1; j < nums.length; j++) if (nums[i] > 2L * nums[j]) brute++;
            assertEquals(brute, ReversePairs.reversePairs(nums));
            assertEquals(brute, ReversePairs.reversePairsBit(nums));
        }
    }
}
