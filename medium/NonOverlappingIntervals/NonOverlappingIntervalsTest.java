package NonOverlappingIntervals;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NonOverlappingIntervalsTest {

    @Test
    void examplesAndRandom() {
        assertEquals(1, NonOverlappingIntervals.eraseOverlapIntervals(new int[][]{{1, 2}, {2, 3}, {3, 4}, {1, 3}}));
        assertEquals(2, NonOverlappingIntervals.eraseOverlapIntervals(new int[][]{{1, 2}, {1, 2}, {1, 2}}));
        assertEquals(0, NonOverlappingIntervals.eraseOverlapIntervals(new int[][]{{1, 2}, {2, 3}}));
        Random r = new Random(435);
        for (int t = 0; t < 1000; t++) {
            int[][] in = new int[1 + r.nextInt(10)][];
            for (int i = 0; i < in.length; i++) {
                int s = r.nextInt(10);
                in[i] = new int[]{s, s + 1 + r.nextInt(5)};
            }
            assertEquals(NonOverlappingIntervals.eraseOverlapIntervalsDp(in), NonOverlappingIntervals.eraseOverlapIntervals(in));
        }
    }
}
