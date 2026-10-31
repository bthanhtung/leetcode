package CarFleet;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarFleetTest {

    /** Step-by-step simulation with exact rationals is overkill; simulate in small time steps with integer speeds. */
    private static int simulate(int target, int[] pos, int[] speed) {
        int n = pos.length;
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) order[i] = i;
        java.util.Arrays.sort(order, (a, b) -> pos[b] - pos[a]);
        // fleets: arrival time of a car is max(own time, time of the fleet ahead) if it catches up before target
        int fleets = 0;
        long aheadNum = -1, aheadDen = 1;                     // slowest arrival time ahead as a fraction
        for (int i : order) {
            long num = target - pos[i], den = speed[i];
            if (aheadNum < 0 || num * aheadDen > aheadNum * den) {
                fleets++;
                aheadNum = num;
                aheadDen = den;
            }
        }
        return fleets;
    }

    @Test
    void examplesAndRandom() {
        assertEquals(3, CarFleet.carFleet(12, new int[]{10, 8, 0, 5, 3}, new int[]{2, 4, 1, 1, 3}));
        assertEquals(1, CarFleet.carFleet(10, new int[]{3}, new int[]{3}));
        assertEquals(1, CarFleet.carFleet(100, new int[]{0, 2, 4}, new int[]{4, 2, 1}));
        Random r = new Random(853);
        for (int t = 0; t < 1000; t++) {
            int target = 10 + r.nextInt(50);
            int[] pos = r.ints(0, target).distinct().limit(1 + r.nextInt(8)).toArray();
            int[] speed = r.ints(pos.length, 1, 10).toArray();
            int expected = simulate(target, pos, speed);
            assertEquals(expected, CarFleet.carFleet(target, pos, speed));
            assertEquals(expected, CarFleet.carFleetStack(target, pos, speed));
        }
    }
}
