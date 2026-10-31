package MergeIntervals;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MergeIntervalsTest {

    /** Paints integer points (and the gaps between them, doubled coordinates) to find covered blocks. */
    private static int[][] brute(int[][] in) {
        boolean[] covered = new boolean[64];
        for (int[] x : in) for (int p = 2 * x[0]; p <= 2 * x[1]; p++) covered[p] = true;
        java.util.List<int[]> out = new java.util.ArrayList<>();
        for (int p = 0; p < 64; p++) {
            if (covered[p] && (p == 0 || !covered[p - 1])) {
                int q = p;
                while (q + 1 < 64 && covered[q + 1]) q++;
                out.add(new int[]{p / 2, q / 2});
            }
        }
        return out.toArray(new int[0][]);
    }

    @Test
    void examplesAndRandom() {
        assertArrayEquals(new int[][]{{1, 6}, {8, 10}, {15, 18}}, MergeIntervals.merge(new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}}));
        assertArrayEquals(new int[][]{{1, 5}}, MergeIntervals.merge(new int[][]{{1, 4}, {4, 5}}));
        Random r = new Random(56);
        for (int t = 0; t < 2000; t++) {
            int[][] in = new int[1 + r.nextInt(8)][];
            for (int i = 0; i < in.length; i++) {
                int s = r.nextInt(25);
                in[i] = new int[]{s, s + r.nextInt(6)};
            }
            int[][] expected = brute(in);
            assertArrayEquals(expected, MergeIntervals.merge(in));
            assertArrayEquals(expected, MergeIntervals.mergeStartsEnds(in));
        }
    }
}
