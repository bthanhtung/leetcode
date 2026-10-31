package KthLargestElementInAnArray;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KthLargestElementInAnArrayTest {

    @Test
    void examplesAndRandom() {
        assertEquals(5, KthLargestElementInAnArray.findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2));
        assertEquals(4, KthLargestElementInAnArray.findKthLargest(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4));
        Random r = new Random(215);
        for (int t = 0; t < 2000; t++) {
            int n = 1 + r.nextInt(20), k = 1 + r.nextInt(n);
            int[] nums = r.ints(n, -10_000, 10_001).map(x -> r.nextBoolean() ? x % 5 : x).toArray();
            int expected = KthLargestElementInAnArray.findKthLargestSort(nums, k);
            assertEquals(expected, KthLargestElementInAnArray.findKthLargest(nums, k));
            assertEquals(expected, KthLargestElementInAnArray.findKthLargestQuickselect(nums, k));
            assertEquals(expected, KthLargestElementInAnArray.findKthLargestCounting(nums, k));
        }
    }
}
