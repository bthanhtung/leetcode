package FindMinimumInRotatedSortedArrayII;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FindMinimumInRotatedSortedArrayIITest {

    @Test
    void allRotationsWithDuplicates() {
        assertEquals(1, FindMinimumInRotatedSortedArrayII.findMin(new int[]{1, 3, 5}));
        assertEquals(0, FindMinimumInRotatedSortedArrayII.findMin(new int[]{2, 2, 2, 0, 1}));
        assertEquals(1, FindMinimumInRotatedSortedArrayII.findMin(new int[]{3, 1, 3}));
        assertEquals(1, FindMinimumInRotatedSortedArrayII.findMin(new int[]{3, 3, 1, 3}));
        Random r = new Random(154);
        for (int t = 0; t < 500; t++) {
            int[] sorted = r.ints(1 + r.nextInt(12), 0, 4).sorted().toArray();
            int n = sorted.length;
            for (int k = 0; k < n; k++) {
                int[] rotated = new int[n];
                for (int i = 0; i < n; i++) rotated[i] = sorted[(i + k) % n];
                assertEquals(sorted[0], FindMinimumInRotatedSortedArrayII.findMin(rotated));
                assertEquals(sorted[0], FindMinimumInRotatedSortedArrayII.findMinLinear(rotated));
            }
        }
    }
}
