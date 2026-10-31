package SearchInsertPosition;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchInsertPositionTest {

    @Test
    void examples() {
        assertEquals(2, SearchInsertPosition.searchInsert(new int[]{1, 3, 5, 6}, 5));
        assertEquals(1, SearchInsertPosition.searchInsert(new int[]{1, 3, 5, 6}, 2));
        assertEquals(4, SearchInsertPosition.searchInsert(new int[]{1, 3, 5, 6}, 7));
        assertEquals(0, SearchInsertPosition.searchInsert(new int[]{1, 3, 5, 6}, 0));
        assertEquals(0, SearchInsertPosition.searchInsert(new int[]{}, 3));
    }

    @Test
    void randomAgree() {
        Random r = new Random(35);
        for (int t = 0; t < 2000; t++) {
            int[] nums = r.ints(r.nextInt(10), -20, 20).distinct().sorted().toArray();
            int target = r.nextInt(50) - 25;
            int expected = SearchInsertPosition.searchInsertLinear(nums, target);
            assertEquals(expected, SearchInsertPosition.searchInsert(nums, target));
            assertEquals(expected, SearchInsertPosition.searchInsertLibrary(nums, target));
        }
    }
}
