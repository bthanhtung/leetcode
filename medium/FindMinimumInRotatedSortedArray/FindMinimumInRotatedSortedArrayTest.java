package FindMinimumInRotatedSortedArray;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FindMinimumInRotatedSortedArrayTest {

    @Test
    void allRotations() {
        assertEquals(1, FindMinimumInRotatedSortedArray.findMin(new int[]{3, 4, 5, 1, 2}));
        assertEquals(0, FindMinimumInRotatedSortedArray.findMin(new int[]{4, 5, 6, 7, 0, 1, 2}));
        assertEquals(11, FindMinimumInRotatedSortedArray.findMin(new int[]{11, 13, 15, 17}));
        Random r = new Random(153);
        for (int t = 0; t < 300; t++) {
            int[] sorted = r.ints(1 + r.nextInt(15), -50, 50).distinct().sorted().toArray();
            int n = sorted.length;
            for (int k = 0; k < n; k++) {
                int[] rotated = new int[n];
                for (int i = 0; i < n; i++) rotated[i] = sorted[(i + k) % n];
                assertEquals(sorted[0], FindMinimumInRotatedSortedArray.findMin(rotated));
                assertEquals(sorted[0], FindMinimumInRotatedSortedArray.findMinLinear(rotated));
            }
        }
    }
}
