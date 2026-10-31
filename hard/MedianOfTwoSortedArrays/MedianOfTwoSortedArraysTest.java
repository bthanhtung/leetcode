package MedianOfTwoSortedArrays;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MedianOfTwoSortedArraysTest {

    private static void all(double expected, int[] a, int[] b) {
        assertEquals(expected, MedianOfTwoSortedArrays.findMedianSortedArrays(a, b), 1e-9);
        assertEquals(expected, MedianOfTwoSortedArrays.findMedianMerge(a, b), 1e-9);
        assertEquals(expected, MedianOfTwoSortedArrays.findMedianKth(a, b), 1e-9);
    }

    @Test
    void examples() {
        all(2.0, new int[]{1, 3}, new int[]{2});
        all(2.5, new int[]{1, 2}, new int[]{3, 4});
        all(1.0, new int[]{}, new int[]{1});
        all(-1.0, new int[]{-3, -1}, new int[]{-2, 0, 5});   // merged: -3, -2, -1, 0, 5
    }

    @Test
    void randomAgainstSorting() {
        Random r = new Random(4);
        for (int t = 0; t < 2000; t++) {
            int[] a = r.ints(r.nextInt(8), -20, 20).sorted().toArray();
            int[] b = r.ints(a.length == 0 ? 1 + r.nextInt(8) : r.nextInt(8), -20, 20).sorted().toArray();
            int[] all = IntStream.concat(Arrays.stream(a), Arrays.stream(b)).sorted().toArray();
            int n = all.length;
            double expected = n % 2 == 1 ? all[n / 2] : (all[n / 2 - 1] + all[n / 2]) / 2.0;
            all(expected, a, b);
        }
    }
}
