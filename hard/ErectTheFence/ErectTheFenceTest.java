package ErectTheFence;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErectTheFenceTest {

    private static Set<String> asSet(int[][] pts) {
        Set<String> s = new HashSet<>();
        for (int[] p : pts) s.add(p[0] + "," + p[1]);
        return s;
    }

    /** A point is on the hull iff some line through it has all points on one side (checked against every other point). */
    private static Set<String> brute(int[][] pts) {
        Set<String> out = new HashSet<>();
        for (int[] p : pts) {
            boolean onHull = false;
            for (int[] q : pts) {
                if (q == p || (q[0] == p[0] && q[1] == p[1])) continue;
                long pos = 0, neg = 0;
                for (int[] r : pts) {
                    long c = (long) (q[0] - p[0]) * (r[1] - p[1]) - (long) (q[1] - p[1]) * (r[0] - p[0]);
                    if (c > 0) pos++;
                    if (c < 0) neg++;
                }
                if (pos == 0 || neg == 0) onHull = true;
            }
            if (onHull || pts.length == 1) out.add(p[0] + "," + p[1]);
        }
        return out;
    }

    @Test
    void examplesAndRandom() {
        assertEquals(Set.of("1,1", "2,0", "4,2", "3,3", "2,4"), asSet(ErectTheFence.outerTrees(new int[][]{{1, 1}, {2, 2}, {2, 0}, {2, 4}, {3, 3}, {4, 2}})));
        assertEquals(Set.of("1,2", "2,2", "4,2"), asSet(ErectTheFence.outerTrees(new int[][]{{1, 2}, {2, 2}, {4, 2}})));
        Random r = new Random(587);
        for (int t = 0; t < 500; t++) {
            Set<String> used = new HashSet<>();
            java.util.List<int[]> list = new java.util.ArrayList<>();
            for (int k = 1 + r.nextInt(10); k > 0; k--) {
                int[] p = {r.nextInt(6), r.nextInt(6)};
                if (used.add(p[0] + "," + p[1])) list.add(p);
            }
            int[][] pts = list.toArray(new int[0][]);
            Set<String> expected = brute(pts);
            assertEquals(expected, asSet(ErectTheFence.outerTrees(pts)));
            assertEquals(expected, asSet(ErectTheFence.outerTreesJarvis(pts)));
        }
    }
}
