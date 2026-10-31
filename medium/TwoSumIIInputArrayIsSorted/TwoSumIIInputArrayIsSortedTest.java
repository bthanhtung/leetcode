package TwoSumIIInputArrayIsSorted;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TwoSumIIInputArrayIsSortedTest {

    private static void check(int[] nums, int target, int[] ans) {
        assertTrue(ans[0] >= 1 && ans[0] < ans[1] && ans[1] <= nums.length);
        assertEquals(target, nums[ans[0] - 1] + nums[ans[1] - 1]);
    }

    @Test
    void examplesAndRandom() {
        assertArrayEquals(new int[]{1, 2}, TwoSumIIInputArrayIsSorted.twoSum(new int[]{2, 7, 11, 15}, 9));
        assertArrayEquals(new int[]{1, 3}, TwoSumIIInputArrayIsSorted.twoSum(new int[]{2, 3, 4}, 6));
        assertArrayEquals(new int[]{1, 2}, TwoSumIIInputArrayIsSorted.twoSum(new int[]{-1, 0}, -1));
        Random r = new Random(167);
        for (int t = 0; t < 2000; t++) {
            int[] nums = r.ints(2 + r.nextInt(15), -30, 30).sorted().toArray();
            int i = r.nextInt(nums.length - 1), j = i + 1 + r.nextInt(nums.length - 1 - i);
            int target = nums[i] + nums[j];
            check(nums, target, TwoSumIIInputArrayIsSorted.twoSum(nums, target));
            check(nums, target, TwoSumIIInputArrayIsSorted.twoSumBinarySearch(nums, target));
        }
    }
}
