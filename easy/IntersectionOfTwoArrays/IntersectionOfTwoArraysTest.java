package IntersectionOfTwoArrays;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class IntersectionOfTwoArraysTest {

    private static int[] sorted(int[] a) {
        int[] c = a.clone();
        Arrays.sort(c);
        return c;
    }

    @Test
    void examplesAndRandom() {
        assertArrayEquals(new int[]{2}, IntersectionOfTwoArrays.intersection(new int[]{1, 2, 2, 1}, new int[]{2, 2}));
        assertArrayEquals(new int[]{4, 9}, sorted(IntersectionOfTwoArrays.intersection(new int[]{4, 9, 5}, new int[]{9, 4, 9, 8, 4})));
        Random r = new Random(349);
        for (int t = 0; t < 1000; t++) {
            int[] a = r.ints(r.nextInt(10), 0, 8).toArray(), b = r.ints(r.nextInt(10), 0, 8).toArray();
            int[] expected = sorted(IntersectionOfTwoArrays.intersection(a, b));
            assertArrayEquals(expected, sorted(IntersectionOfTwoArrays.intersectionTwoPointers(a, b)));
            assertArrayEquals(expected, sorted(IntersectionOfTwoArrays.intersectionBinarySearch(a, b)));
        }
    }
}
