package FindTheDuplicateNumber;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FindTheDuplicateNumberTest {

    @Test
    void examplesAndRandom() {
        assertEquals(2, FindTheDuplicateNumber.findDuplicate(new int[]{1, 3, 4, 2, 2}));
        assertEquals(3, FindTheDuplicateNumber.findDuplicate(new int[]{3, 1, 3, 4, 2}));
        assertEquals(3, FindTheDuplicateNumber.findDuplicate(new int[]{3, 3, 3, 3, 3}));
        Random r = new Random(287);
        for (int t = 0; t < 2000; t++) {
            int n = 1 + r.nextInt(15), dup = 1 + r.nextInt(n);
            int[] nums = new int[n + 1];
            // values 1..n each at most once, then fill the rest with dup (at least two copies in total)
            java.util.List<Integer> pool = new java.util.ArrayList<>();
            for (int v = 1; v <= n; v++) if (v != dup) pool.add(v);
            java.util.Collections.shuffle(pool, r);
            int keep = r.nextInt(pool.size() + 1);
            for (int i = 0; i < keep; i++) nums[i] = pool.get(i);
            for (int i = keep; i <= n; i++) nums[i] = dup;
            for (int i = nums.length - 1; i > 0; i--) {
                int j = r.nextInt(i + 1), tmp = nums[i];
                nums[i] = nums[j];
                nums[j] = tmp;
            }
            int[] before = nums.clone();
            assertEquals(dup, FindTheDuplicateNumber.findDuplicate(nums));
            assertEquals(dup, FindTheDuplicateNumber.findDuplicateBinarySearch(nums));
            assertEquals(dup, FindTheDuplicateNumber.findDuplicateSet(nums));
            assertEquals(dup, FindTheDuplicateNumber.findDuplicateSort(nums));
            assertArrayEquals(before, nums, "nums must not be modified");
        }
    }
}
