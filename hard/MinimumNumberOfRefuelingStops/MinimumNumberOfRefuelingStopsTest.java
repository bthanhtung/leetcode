package MinimumNumberOfRefuelingStops;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinimumNumberOfRefuelingStopsTest {

    @Test
    void examplesAndRandom() {
        assertEquals(2, MinimumNumberOfRefuelingStops.minRefuelStops(100, 10, new int[][]{{10, 60}, {20, 30}, {30, 30}, {60, 40}}));
        assertEquals(0, MinimumNumberOfRefuelingStops.minRefuelStops(1, 1, new int[0][]));
        assertEquals(-1, MinimumNumberOfRefuelingStops.minRefuelStops(100, 1, new int[][]{{10, 100}}));
        Random r = new Random(871);
        for (int t = 0; t < 2000; t++) {
            int target = 10 + r.nextInt(60);
            int[] pos = r.ints(1, target).distinct().limit(r.nextInt(7)).sorted().toArray();
            int[][] st = new int[pos.length][];
            for (int i = 0; i < pos.length; i++) st[i] = new int[]{pos[i], 1 + r.nextInt(30)};
            int fuel = 1 + r.nextInt(30);
            assertEquals(MinimumNumberOfRefuelingStops.minRefuelStopsDp(target, fuel, st), MinimumNumberOfRefuelingStops.minRefuelStops(target, fuel, st));
        }
    }
}
