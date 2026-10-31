package ParallelCoursesIII;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParallelCoursesIIITest {

    @Test
    void examplesAndRandomDags() {
        assertEquals(8, ParallelCoursesIII.minimumTime(3, new int[][]{{1, 3}, {2, 3}}, new int[]{3, 2, 5}));
        assertEquals(12, ParallelCoursesIII.minimumTime(5, new int[][]{{1, 5}, {2, 5}, {3, 5}, {3, 4}, {4, 5}}, new int[]{1, 2, 3, 4, 5}));
        Random r = new Random(2050);
        for (int t = 0; t < 500; t++) {
            int n = 1 + r.nextInt(10);
            List<int[]> rel = new ArrayList<>();
            for (int a = 1; a <= n; a++)
                for (int b = a + 1; b <= n; b++)
                    if (r.nextInt(4) == 0) rel.add(new int[]{a, b});       // edges only forward: a DAG
            int[] time = r.ints(n, 1, 10).toArray();
            int[][] relations = rel.toArray(new int[0][]);
            assertEquals(ParallelCoursesIII.minimumTimeDfs(n, relations, time), ParallelCoursesIII.minimumTime(n, relations, time));
        }
    }
}
