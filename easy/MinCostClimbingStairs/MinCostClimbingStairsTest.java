package MinCostClimbingStairs;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinCostClimbingStairsTest {

    private static int brute(int[] cost, int i) {
        if (i >= cost.length) return 0;
        return cost[i] + Math.min(brute(cost, i + 1), brute(cost, i + 2));
    }

    @Test
    void examplesAndRandom() {
        assertEquals(15, MinCostClimbingStairs.minCostClimbingStairs(new int[]{10, 15, 20}));
        assertEquals(6, MinCostClimbingStairs.minCostClimbingStairs(new int[]{1, 100, 1, 1, 1, 100, 1, 1, 100, 1}));
        Random r = new Random(746);
        for (int t = 0; t < 500; t++) {
            int[] cost = r.ints(2 + r.nextInt(15), 0, 50).toArray();
            int expected = Math.min(brute(cost, 0), brute(cost, 1));
            assertEquals(expected, MinCostClimbingStairs.minCostClimbingStairs(cost));
            assertEquals(expected, MinCostClimbingStairs.minCostClimbingStairsDp(cost));
            assertEquals(expected, MinCostClimbingStairs.minCostClimbingStairsMemo(cost));
        }
    }
}
