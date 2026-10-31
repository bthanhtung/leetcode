package TwoSum;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TwoSumTest {

    private static void check(int[] nums, int target, int[] answer) {
        int[] sorted = answer.clone();
        Arrays.sort(sorted);
        assertEquals(target, nums[sorted[0]] + nums[sorted[1]]);
        org.junit.jupiter.api.Assertions.assertNotEquals(sorted[0], sorted[1]);
    }

    @Test
    void examples() {
        assertArrayEquals(new int[]{0, 1}, TwoSum.twoSumHashMap(new int[]{2, 7, 11, 15}, 9));
        assertArrayEquals(new int[]{1, 2}, TwoSum.twoSumBruteForce(new int[]{3, 2, 4}, 6));
        assertArrayEquals(new int[]{0, 1}, TwoSum.twoSumSortedPointers(new int[]{3, 3}, 6));
        check(new int[]{-3, 4, 3, 90}, 0, TwoSum.twoSumHashMap(new int[]{-3, 4, 3, 90}, 0));   // negatives
    }

    @Test
    void allApproachesOnRandomInputs() {
        Random r = new Random(1);
        for (int t = 0; t < 300; t++) {
            int[] nums = r.ints(2 + r.nextInt(30), -50, 50).toArray();
            int i = r.nextInt(nums.length), j = (i + 1 + r.nextInt(nums.length - 1)) % nums.length;
            int target = nums[i] + nums[j];
            check(nums, target, TwoSum.twoSumBruteForce(nums, target));
            check(nums, target, TwoSum.twoSumHashMap(nums, target));
            check(nums, target, TwoSum.twoSumSortedPointers(nums, target));
        }
    }
}
