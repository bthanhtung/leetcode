package SearchInRotatedSortedArray;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchInRotatedSortedArrayTest {

    @Test
    void everyRotationAndTarget() {
        assertEquals(4, SearchInRotatedSortedArray.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 0));
        assertEquals(-1, SearchInRotatedSortedArray.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 3));
        assertEquals(-1, SearchInRotatedSortedArray.search(new int[]{1}, 0));
        Random r = new Random(33);
        for (int t = 0; t < 300; t++) {
            int n = 1 + r.nextInt(10);
            int[] sorted = r.ints(0, 40).distinct().limit(n).sorted().toArray();
            for (int k = 0; k < n; k++) {
                int[] a = new int[n];
                for (int i = 0; i < n; i++) a[i] = sorted[(i + k) % n];
                for (int target = -1; target <= 40; target++) {
                    int expected = -1;
                    for (int i = 0; i < n; i++) if (a[i] == target) expected = i;
                    assertEquals(expected, SearchInRotatedSortedArray.search(a, target));
                }
            }
        }
    }
}
