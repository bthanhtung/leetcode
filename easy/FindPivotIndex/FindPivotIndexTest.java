package FindPivotIndex;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FindPivotIndexTest {

    private static int brute(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int l = 0, r = 0;
            for (int j = 0; j < i; j++) l += nums[j];
            for (int j = i + 1; j < nums.length; j++) r += nums[j];
            if (l == r) return i;
        }
        return -1;
    }

    @Test
    void examplesAndRandom() {
        assertEquals(3, FindPivotIndex.pivotIndex(new int[]{1, 7, 3, 6, 5, 6}));
        assertEquals(-1, FindPivotIndex.pivotIndex(new int[]{1, 2, 3}));
        assertEquals(0, FindPivotIndex.pivotIndex(new int[]{2, 1, -1}));
        Random r = new Random(724);
        for (int t = 0; t < 2000; t++) {
            int[] nums = r.ints(1 + r.nextInt(10), -3, 4).toArray();
            int expected = brute(nums);
            assertEquals(expected, FindPivotIndex.pivotIndex(nums));
            assertEquals(expected, FindPivotIndex.pivotIndexPrefix(nums));
        }
    }
}
