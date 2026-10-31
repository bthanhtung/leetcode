package LargestNumber;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LargestNumberTest {

    private static String best;

    private static void permute(String[] a, int k) {
        if (k == a.length) {
            String s = String.join("", a).replaceFirst("^0+(?=.)", "");
            if (best == null || s.length() > best.length() || (s.length() == best.length() && s.compareTo(best) > 0)) best = s;
            return;
        }
        for (int i = k; i < a.length; i++) {
            String t = a[k]; a[k] = a[i]; a[i] = t;
            permute(a, k + 1);
            t = a[k]; a[k] = a[i]; a[i] = t;
        }
    }

    @Test
    void examplesAndBruteForce() {
        assertEquals("210", LargestNumber.largestNumber(new int[]{10, 2}));
        assertEquals("9534330", LargestNumber.largestNumber(new int[]{3, 30, 34, 5, 9}));
        assertEquals("0", LargestNumber.largestNumber(new int[]{0, 0}));
        Random r = new Random(179);
        for (int t = 0; t < 500; t++) {
            int[] nums = r.ints(1 + r.nextInt(6), 0, 130).toArray();
            best = null;
            permute(java.util.Arrays.stream(nums).mapToObj(String::valueOf).toArray(String[]::new), 0);
            assertEquals(best, LargestNumber.largestNumber(nums));
        }
    }
}
