package GasStation;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GasStationTest {

    @Test
    void examplesAndBruteForce() {
        assertEquals(3, GasStation.canCompleteCircuit(new int[]{1, 2, 3, 4, 5}, new int[]{3, 4, 5, 1, 2}));
        assertEquals(-1, GasStation.canCompleteCircuit(new int[]{2, 3, 4}, new int[]{3, 4, 3}));
        Random r = new Random(134);
        for (int t = 0; t < 5000; t++) {
            int n = 1 + r.nextInt(8);
            int[] gas = r.ints(n, 0, 6).toArray(), cost = r.ints(n, 0, 6).toArray();
            int brute = GasStation.canCompleteCircuitBrute(gas, cost);
            int fast = GasStation.canCompleteCircuit(gas, cost);
            // when several starts work (ties with zero balance), any valid one is accepted
            if (brute == -1) assertEquals(-1, fast);
            else assertEquals(true, fast >= 0 && check(gas, cost, fast));
        }
    }

    private static boolean check(int[] gas, int[] cost, int s) {
        int tank = 0, n = gas.length;
        for (int k = 0; k < n; k++) {
            tank += gas[(s + k) % n] - cost[(s + k) % n];
            if (tank < 0) return false;
        }
        return true;
    }
}
