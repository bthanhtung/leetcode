package MaxValueOfEquation;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaxValueOfEquationTest {

    @Test
    void examplesAndRandom() {
        assertEquals(4, MaxValueOfEquation.findMaxValueOfEquation(new int[][]{{1, 3}, {2, 0}, {5, 10}, {6, -10}}, 1));
        assertEquals(3, MaxValueOfEquation.findMaxValueOfEquation(new int[][]{{0, 0}, {3, 0}, {9, 2}}, 3));
        Random r = new Random(1499);
        for (int t = 0; t < 2000; t++) {
            int[] xs = r.ints(0, 30).distinct().limit(2 + r.nextInt(10)).sorted().toArray();
            int[][] pts = new int[xs.length][];
            for (int i = 0; i < xs.length; i++) pts[i] = new int[]{xs[i], r.nextInt(21) - 10};
            int k = xs[1] - xs[0] + r.nextInt(10);                     // guarantees at least one valid pair
            int expected = Integer.MIN_VALUE;
            for (int i = 0; i < pts.length; i++)
                for (int j = i + 1; j < pts.length; j++)
                    if (pts[j][0] - pts[i][0] <= k) expected = Math.max(expected, pts[i][1] + pts[j][1] + pts[j][0] - pts[i][0]);
            assertEquals(expected, MaxValueOfEquation.findMaxValueOfEquation(pts, k));
            assertEquals(expected, MaxValueOfEquation.findMaxValueOfEquationHeap(pts, k));
        }
    }
}
