package FindFirstAndLastPositionOfElementInSortedArray;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class FindFirstAndLastPositionOfElementInSortedArrayTest {

    @Test
    void examplesAndRandom() {
        assertArrayEquals(new int[]{3, 4}, FindFirstAndLastPositionOfElementInSortedArray.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8));
        assertArrayEquals(new int[]{-1, -1}, FindFirstAndLastPositionOfElementInSortedArray.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 6));
        assertArrayEquals(new int[]{-1, -1}, FindFirstAndLastPositionOfElementInSortedArray.searchRange(new int[]{}, 0));
        assertArrayEquals(new int[]{0, 1}, FindFirstAndLastPositionOfElementInSortedArray.searchRange(new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE}, Integer.MAX_VALUE));
        Random r = new Random(34);
        for (int t = 0; t < 3000; t++) {
            int[] nums = r.ints(r.nextInt(12), 0, 6).sorted().toArray();
            int target = r.nextInt(7), first = -1, last = -1;
            for (int i = 0; i < nums.length; i++) if (nums[i] == target) { if (first < 0) first = i; last = i; }
            assertArrayEquals(new int[]{first, last}, FindFirstAndLastPositionOfElementInSortedArray.searchRange(nums, target));
        }
    }
}
