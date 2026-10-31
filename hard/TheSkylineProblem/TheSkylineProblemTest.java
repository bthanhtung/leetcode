package TheSkylineProblem;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TheSkylineProblemTest {

    /** Height at every half-integer x on a small grid, then turn it into key points. */
    private static List<List<Integer>> paint(int[][] b) {
        int[] h = new int[60];
        for (int[] x : b) for (int p = x[0]; p < x[1]; p++) h[p] = Math.max(h[p], x[2]);
        List<List<Integer>> out = new ArrayList<>();
        int prev = 0;
        for (int p = 0; p < 60; p++) {
            if (h[p] != prev) {
                out.add(List.of(p, h[p]));
                prev = h[p];
            }
        }
        return out;
    }

    @Test
    void examplesAndRandom() {
        int[][] b = {{2, 9, 10}, {3, 7, 15}, {5, 12, 12}, {15, 20, 10}, {19, 24, 8}};
        List<List<Integer>> expected = List.of(List.of(2, 10), List.of(3, 15), List.of(7, 12), List.of(12, 0), List.of(15, 10), List.of(20, 8), List.of(24, 0));
        assertEquals(expected, TheSkylineProblem.getSkyline(b));
        assertEquals(List.of(List.of(0, 3), List.of(5, 0)), TheSkylineProblem.getSkyline(new int[][]{{0, 2, 3}, {2, 5, 3}}));
        Random r = new Random(218);
        for (int t = 0; t < 1000; t++) {
            int[][] bs = new int[1 + r.nextInt(6)][];
            for (int i = 0; i < bs.length; i++) {
                int l = r.nextInt(40);
                bs[i] = new int[]{l, l + 1 + r.nextInt(15), 1 + r.nextInt(8)};
            }
            Arrays.sort(bs, (x, y) -> x[0] - y[0]);
            List<List<Integer>> want = paint(bs);
            assertEquals(want, TheSkylineProblem.getSkyline(bs));
            assertEquals(want, TheSkylineProblem.getSkylineTreeMap(bs));
        }
    }
}
