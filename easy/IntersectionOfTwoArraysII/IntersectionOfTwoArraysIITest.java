package IntersectionOfTwoArraysII;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class IntersectionOfTwoArraysIITest {

    private static int[] sorted(int[] a) {
        int[] c = a.clone();
        Arrays.sort(c);
        return c;
    }

    @Test
    void examplesAndRandom() {
        assertArrayEquals(new int[]{2, 2}, IntersectionOfTwoArraysII.intersect(new int[]{1, 2, 2, 1}, new int[]{2, 2}));
        assertArrayEquals(new int[]{4, 9}, sorted(IntersectionOfTwoArraysII.intersect(new int[]{4, 9, 5}, new int[]{9, 4, 9, 8, 4})));
        Random r = new Random(350);
        for (int t = 0; t < 1000; t++) {
            int[] a = r.ints(r.nextInt(12), 0, 5).toArray(), b = r.ints(r.nextInt(12), 0, 5).toArray();
            assertArrayEquals(IntersectionOfTwoArraysII.intersectSorted(a, b), sorted(IntersectionOfTwoArraysII.intersect(a, b)));
        }
    }
}
