package InsertInterval;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class InsertIntervalTest {

    @Test
    void examplesAndRandom() {
        assertArrayEquals(new int[][]{{1, 5}, {6, 9}}, InsertInterval.insert(new int[][]{{1, 3}, {6, 9}}, new int[]{2, 5}));
        assertArrayEquals(new int[][]{{1, 2}, {3, 10}, {12, 16}},
                InsertInterval.insert(new int[][]{{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}}, new int[]{4, 8}));
        assertArrayEquals(new int[][]{{5, 7}}, InsertInterval.insert(new int[][]{}, new int[]{5, 7}));
        Random r = new Random(57);
        for (int t = 0; t < 3000; t++) {
            boolean[] covered = new boolean[50];          // points 0..24 doubled so touching ends merge
            int[][] intervals = randomSorted(r);
            int s = r.nextInt(20), e = s + r.nextInt(5);
            for (int[] iv : intervals) for (int x = 2 * iv[0]; x <= 2 * iv[1]; x++) covered[x] = true;
            for (int x = 2 * s; x <= 2 * e; x++) covered[x] = true;
            java.util.List<int[]> expected = new java.util.ArrayList<>();
            for (int x = 0; x < covered.length; x++) {
                if (covered[x] && (x == 0 || !covered[x - 1])) expected.add(new int[]{x / 2, 0});
                if (covered[x] && (x + 1 == covered.length || !covered[x + 1])) expected.get(expected.size() - 1)[1] = x / 2;
            }
            assertArrayEquals(expected.toArray(new int[0][]), InsertInterval.insert(intervals, new int[]{s, e}));
        }
    }

    private static int[][] randomSorted(Random r) {
        java.util.List<int[]> list = new java.util.ArrayList<>();
        int x = r.nextInt(3);
        while (x <= 20 && r.nextInt(5) != 0) {
            int end = Math.min(20, x + r.nextInt(3));
            list.add(new int[]{x, end});
            x = end + 1 + r.nextInt(3);
        }
        return list.toArray(new int[0][]);
    }
}
