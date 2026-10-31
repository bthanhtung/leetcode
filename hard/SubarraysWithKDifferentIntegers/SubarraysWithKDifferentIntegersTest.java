package SubarraysWithKDifferentIntegers;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubarraysWithKDifferentIntegersTest {

    @Test
    void examplesAndRandom() {
        assertEquals(7, SubarraysWithKDifferentIntegers.subarraysWithKDistinct(new int[]{1, 2, 1, 2, 3}, 2));
        assertEquals(3, SubarraysWithKDifferentIntegers.subarraysWithKDistinct(new int[]{1, 2, 1, 3, 4}, 3));
        Random r = new Random(992);
        for (int t = 0; t < 2000; t++) {
            int n = 1 + r.nextInt(15);
            int[] nums = r.ints(n, 1, Math.min(n, 4) + 1).toArray();
            int k = 1 + r.nextInt(n);
            assertEquals(SubarraysWithKDifferentIntegers.subarraysWithKDistinctBrute(nums, k), SubarraysWithKDifferentIntegers.subarraysWithKDistinct(nums, k));
        }
    }
}
