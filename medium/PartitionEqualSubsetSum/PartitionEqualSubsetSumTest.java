package PartitionEqualSubsetSum;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PartitionEqualSubsetSumTest {

    @Test
    void examplesAndBruteForce() {
        assertEquals(true, PartitionEqualSubsetSum.canPartition(new int[]{1, 5, 11, 5}));
        assertEquals(false, PartitionEqualSubsetSum.canPartition(new int[]{1, 2, 3, 5}));
        assertEquals(false, PartitionEqualSubsetSum.canPartition(new int[]{1}));
        Random r = new Random(416);
        for (int t = 0; t < 2000; t++) {
            int[] nums = r.ints(1 + r.nextInt(10), 1, 12).toArray();
            int total = java.util.Arrays.stream(nums).sum();
            boolean expected = false;
            for (int mask = 0; mask < 1 << nums.length && !expected; mask++) {
                int s = 0;
                for (int i = 0; i < nums.length; i++) if ((mask >> i & 1) == 1) s += nums[i];
                expected = 2 * s == total;
            }
            assertEquals(expected, PartitionEqualSubsetSum.canPartition(nums));
            assertEquals(expected, PartitionEqualSubsetSum.canPartitionBits(nums));
        }
    }
}
