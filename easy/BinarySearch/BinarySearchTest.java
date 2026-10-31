package BinarySearch;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinarySearchTest {

    @Test
    void examplesAndRandom() {
        assertEquals(4, BinarySearch.search(new int[]{-1, 0, 3, 5, 9, 12}, 9));
        assertEquals(-1, BinarySearch.search(new int[]{-1, 0, 3, 5, 9, 12}, 2));
        Random r = new Random(704);
        for (int t = 0; t < 3000; t++) {
            int[] nums = r.ints(r.nextInt(12), -20, 20).distinct().sorted().toArray();
            int target = r.nextInt(44) - 22;
            int i = Arrays.binarySearch(nums, target);
            int expected = i >= 0 ? i : -1;
            assertEquals(expected, BinarySearch.search(nums, target));
            assertEquals(expected, BinarySearch.searchHalfOpen(nums, target));
            assertEquals(expected, BinarySearch.searchRecursive(nums, target));
        }
    }
}
