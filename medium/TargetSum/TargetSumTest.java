package TargetSum;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TargetSumTest {

    @Test
    void examplesAndBruteForce() {
        assertEquals(5, TargetSum.findTargetSumWays(new int[]{1, 1, 1, 1, 1}, 3));
        assertEquals(1, TargetSum.findTargetSumWays(new int[]{1}, 1));
        assertEquals(2, TargetSum.findTargetSumWays(new int[]{0}, 0));
        assertEquals(0, TargetSum.findTargetSumWays(new int[]{100}, -200));
        Random r = new Random(494);
        for (int t = 0; t < 1000; t++) {
            int[] nums = r.ints(1 + r.nextInt(10), 0, 6).toArray();
            int target = r.nextInt(21) - 10, brute = 0;
            for (int mask = 0; mask < 1 << nums.length; mask++) {
                int s = 0;
                for (int i = 0; i < nums.length; i++) s += (mask >> i & 1) == 1 ? nums[i] : -nums[i];
                if (s == target) brute++;
            }
            assertEquals(brute, TargetSum.findTargetSumWays(nums, target));
            assertEquals(brute, TargetSum.findTargetSumWaysMemo(nums, target));
        }
    }
}
