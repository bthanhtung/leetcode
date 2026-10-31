package FindPeakElement;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FindPeakElementTest {

    private static boolean isPeak(int[] a, int i) {
        return (i == 0 || a[i] > a[i - 1]) && (i == a.length - 1 || a[i] > a[i + 1]);
    }

    @Test
    void returnsAPeak() {
        assertTrue(isPeak(new int[]{1, 2, 3, 1}, FindPeakElement.findPeakElement(new int[]{1, 2, 3, 1})));
        Random r = new Random(162);
        for (int t = 0; t < 5000; t++) {
            int[] a = new int[1 + r.nextInt(12)];
            a[0] = r.nextInt(10);
            for (int i = 1; i < a.length; i++) a[i] = a[i - 1] + (r.nextBoolean() ? 1 : -1) * (1 + r.nextInt(3));
            assertTrue(isPeak(a, FindPeakElement.findPeakElement(a)));
            assertTrue(isPeak(a, FindPeakElement.findPeakElementLinear(a)));
        }
        int[] edge = {Integer.MIN_VALUE, Integer.MAX_VALUE};
        assertTrue(isPeak(edge, FindPeakElement.findPeakElement(edge)));
    }
}
