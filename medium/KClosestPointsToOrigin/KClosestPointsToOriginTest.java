package KClosestPointsToOrigin;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class KClosestPointsToOriginTest {

    private static int[] distances(int[][] pts) {
        return Arrays.stream(pts).mapToInt(p -> p[0] * p[0] + p[1] * p[1]).sorted().toArray();
    }

    @Test
    void random() {
        Random r = new Random(973);
        for (int t = 0; t < 1000; t++) {
            int n = 1 + r.nextInt(15), k = 1 + r.nextInt(n);
            int[][] pts = new int[n][];
            for (int i = 0; i < n; i++) pts[i] = new int[]{r.nextInt(21) - 10, r.nextInt(21) - 10};
            int[] expected = distances(KClosestPointsToOrigin.kClosestSort(pts, k));
            assertEquals(k, expected.length);
            assertArrayEquals(expected, distances(KClosestPointsToOrigin.kClosest(pts, k)));
            assertArrayEquals(expected, distances(KClosestPointsToOrigin.kClosestQuickselect(pts, k)));
        }
    }
}
