package SwimInRisingWater;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SwimInRisingWaterTest {

    @Test
    void examplesAndRandom() {
        assertEquals(3, SwimInRisingWater.swimInWater(new int[][]{{0, 2}, {1, 3}}));
        int[][] spiral = {{0, 1, 2, 3, 4}, {24, 23, 22, 21, 5}, {12, 13, 14, 15, 16}, {11, 17, 18, 19, 20}, {10, 9, 8, 7, 6}};
        assertEquals(16, SwimInRisingWater.swimInWater(spiral));
        Random r = new Random(778);
        for (int t = 0; t < 300; t++) {
            int n = 1 + r.nextInt(7);
            List<Integer> values = new ArrayList<>();
            for (int v = 0; v < n * n; v++) values.add(v);
            Collections.shuffle(values, r);
            int[][] g = new int[n][n];
            for (int k = 0; k < n * n; k++) g[k / n][k % n] = values.get(k);
            int expected = SwimInRisingWater.swimInWaterBinarySearch(g);
            assertEquals(expected, SwimInRisingWater.swimInWater(g));
            assertEquals(expected, SwimInRisingWater.swimInWaterUnionFind(g));
        }
    }
}
