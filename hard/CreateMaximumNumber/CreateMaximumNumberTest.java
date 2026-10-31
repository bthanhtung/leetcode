package CreateMaximumNumber;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class CreateMaximumNumberTest {

    private static int[] bestSeen;

    /** Brute force: every way to pick and interleave. */
    private static void brute(int[] a, int i, int[] b, int j, int[] cur, int len) {
        if (len == cur.length) {
            if (bestSeen == null || Arrays.compare(cur, bestSeen) > 0) bestSeen = cur.clone();
            return;
        }
        for (int x = i; x < a.length; x++) { cur[len] = a[x]; brute(a, x + 1, b, j, cur, len + 1); }
        for (int y = j; y < b.length; y++) { cur[len] = b[y]; brute(a, i, b, y + 1, cur, len + 1); }
    }

    @Test
    void examplesAndBruteForce() {
        assertArrayEquals(new int[]{9, 8, 6, 5, 3}, CreateMaximumNumber.maxNumber(new int[]{3, 4, 6, 5}, new int[]{9, 1, 2, 5, 8, 3}, 5));
        assertArrayEquals(new int[]{6, 7, 6, 0, 4}, CreateMaximumNumber.maxNumber(new int[]{6, 7}, new int[]{6, 0, 4}, 5));
        assertArrayEquals(new int[]{9, 8, 9}, CreateMaximumNumber.maxNumber(new int[]{3, 9}, new int[]{8, 9}, 3));
        Random r = new Random(321);
        for (int t = 0; t < 800; t++) {
            int[] a = r.ints(r.nextInt(5), 0, 4).toArray(), b = r.ints(r.nextInt(5), 0, 4).toArray();
            if (a.length + b.length == 0) continue;
            int k = 1 + r.nextInt(a.length + b.length);
            bestSeen = null;
            brute(a, 0, b, 0, new int[k], 0);
            assertArrayEquals(bestSeen, CreateMaximumNumber.maxNumber(a, b, k));
        }
    }
}
