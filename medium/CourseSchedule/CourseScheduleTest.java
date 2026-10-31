package CourseSchedule;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourseScheduleTest {

    @Test
    void examplesAndRandom() {
        assertEquals(true, CourseSchedule.canFinish(2, new int[][]{{1, 0}}));
        assertEquals(false, CourseSchedule.canFinish(2, new int[][]{{1, 0}, {0, 1}}));
        assertEquals(false, CourseSchedule.canFinish(1, new int[][]{{0, 0}}));
        Random r = new Random(207);
        for (int t = 0; t < 2000; t++) {
            int n = 1 + r.nextInt(7);
            boolean forward = r.nextBoolean();          // only edges b -> a with b < a: always acyclic
            int[][] pre = new int[r.nextInt(9)][];
            for (int i = 0; i < pre.length; i++) {
                int a = r.nextInt(n), b = r.nextInt(n);
                if (forward && a <= b) { a = n; b = 0; }
                pre[i] = a == n ? new int[]{n - 1, 0} : new int[]{a, b};
                if (forward && n == 1) pre[i] = null;
            }
            pre = java.util.Arrays.stream(pre).filter(java.util.Objects::nonNull).toArray(int[][]::new);
            boolean expected = CourseSchedule.canFinishDfs(n, pre);
            if (forward) assertEquals(true, expected);
            assertEquals(expected, CourseSchedule.canFinish(n, pre));
        }
        // a long chain is fine, closing it is not
        int n = 1000;
        int[][] chain = new int[n - 1][];
        for (int i = 0; i + 1 < n; i++) chain[i] = new int[]{i + 1, i};
        assertEquals(true, CourseSchedule.canFinish(n, chain));
        int[][] cyc = java.util.Arrays.copyOf(chain, n);
        cyc[n - 1] = new int[]{0, n - 1};
        assertEquals(false, CourseSchedule.canFinish(n, cyc));
        assertEquals(false, CourseSchedule.canFinishDfs(n, cyc));
    }
}
