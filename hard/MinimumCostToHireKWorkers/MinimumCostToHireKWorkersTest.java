package MinimumCostToHireKWorkers;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinimumCostToHireKWorkersTest {

    @Test
    void examplesAndRandom() {
        assertEquals(105.0, MinimumCostToHireKWorkers.mincostToHireWorkers(new int[]{10, 20, 5}, new int[]{70, 50, 30}, 2), 1e-5);
        assertEquals(30.66667, MinimumCostToHireKWorkers.mincostToHireWorkers(new int[]{3, 1, 10, 10, 1}, new int[]{4, 8, 2, 2, 7}, 3), 1e-5);
        Random r = new Random(857);
        for (int t = 0; t < 1000; t++) {
            int n = 1 + r.nextInt(8), k = 1 + r.nextInt(n);
            int[] q = r.ints(n, 1, 20).toArray(), w = r.ints(n, 1, 50).toArray();
            assertEquals(MinimumCostToHireKWorkers.mincostToHireWorkersBrute(q, w, k), MinimumCostToHireKWorkers.mincostToHireWorkers(q, w, k), 1e-6);
        }
    }
}
