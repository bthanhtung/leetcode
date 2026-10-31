package MergeSortedArray;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MergeSortedArrayTest {

    @Test
    void examplesAndRandom() {
        int[] a = {1, 2, 3, 0, 0, 0};
        MergeSortedArray.merge(a, 3, new int[]{2, 5, 6}, 3);
        assertArrayEquals(new int[]{1, 2, 2, 3, 5, 6}, a);
        int[] b = {0};
        MergeSortedArray.merge(b, 0, new int[]{1}, 1);
        assertArrayEquals(new int[]{1}, b);
        Random r = new Random(88);
        for (int t = 0; t < 1000; t++) {
            int m = r.nextInt(8), n = r.nextInt(8);
            int[] first = r.ints(m, -10, 10).sorted().toArray(), second = r.ints(n, -10, 10).sorted().toArray();
            int[] x = Arrays.copyOf(first, m + n), y = Arrays.copyOf(first, m + n);
            MergeSortedArray.merge(x, m, second, n);
            MergeSortedArray.mergeSort(y, m, second, n);
            assertArrayEquals(y, x);
        }
    }
}
