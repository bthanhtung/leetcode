package CourseScheduleII;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CourseScheduleIITest {

    private static boolean validOrder(int n, int[][] pre, int[] order) {
        if (order.length != n) return false;
        int[] pos = new int[n];
        boolean[] seen = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (seen[order[i]]) return false;
            seen[order[i]] = true;
            pos[order[i]] = i;
        }
        for (int[] p : pre) if (pos[p[1]] > pos[p[0]]) return false;
        return true;
    }

    @Test
    void examplesAndRandom() {
        assertTrue(validOrder(4, new int[][]{{1, 0}, {2, 0}, {3, 1}, {3, 2}}, CourseScheduleII.findOrder(4, new int[][]{{1, 0}, {2, 0}, {3, 1}, {3, 2}})));
        assertEquals(0, CourseScheduleII.findOrder(2, new int[][]{{1, 0}, {0, 1}}).length);
        Random r = new Random(210);
        for (int t = 0; t < 1000; t++) {
            int n = 1 + r.nextInt(8), m = r.nextInt(12);
            int[][] pre = new int[m][];
            for (int i = 0; i < m; i++) {
                int a = r.nextInt(n), b = r.nextInt(n);
                pre[i] = new int[]{a, b};
            }
            int[] a = CourseScheduleII.findOrder(n, pre), b = CourseScheduleII.findOrderDfs(n, pre);
            assertEquals(a.length == 0, b.length == 0, "both must agree on whether a schedule exists");
            if (a.length > 0) {
                assertTrue(validOrder(n, pre, a));
                assertTrue(validOrder(n, pre, b));
            }
        }
    }
}
