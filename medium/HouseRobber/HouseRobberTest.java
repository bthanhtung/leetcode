package HouseRobber;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HouseRobberTest {

    private static int brute(int[] nums, int i) {
        if (i >= nums.length) return 0;
        return Math.max(nums[i] + brute(nums, i + 2), brute(nums, i + 1));
    }

    @Test
    void examplesAndRandom() {
        assertEquals(4, HouseRobber.rob(new int[]{1, 2, 3, 1}));
        assertEquals(12, HouseRobber.rob(new int[]{2, 7, 9, 3, 1}));
        Random r = new Random(198);
        for (int t = 0; t < 1000; t++) {
            int[] nums = r.ints(1 + r.nextInt(15), 0, 50).toArray();
            int expected = brute(nums, 0);
            assertEquals(expected, HouseRobber.rob(nums));
            assertEquals(expected, HouseRobber.robTable(nums));
            assertEquals(expected, HouseRobber.robMemo(nums));
        }
    }
}
