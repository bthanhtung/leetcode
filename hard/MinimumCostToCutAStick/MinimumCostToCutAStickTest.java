package MinimumCostToCutAStick;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinimumCostToCutAStickTest {

    /** Try every cut order (tiny inputs). */
    private static int brute(int lo, int hi, List<Integer> cuts) {
        int best = Integer.MAX_VALUE;
        boolean any = false;
        for (int c : cuts) {
            if (c <= lo || c >= hi) continue;
            any = true;
            best = Math.min(best, (hi - lo) + brute(lo, c, cuts) + brute(c, hi, cuts));
        }
        return any ? best : 0;
    }

    @Test
    void examplesAndRandom() {
        assertEquals(16, MinimumCostToCutAStick.minCost(7, new int[]{1, 3, 4, 5}));
        assertEquals(22, MinimumCostToCutAStick.minCost(9, new int[]{5, 6, 1, 4, 2}));
        Random r = new Random(1547);
        for (int t = 0; t < 300; t++) {
            int n = 2 + r.nextInt(20);
            int[] cuts = r.ints(1, n).distinct().limit(1 + r.nextInt(Math.min(6, n - 1))).toArray();
            List<Integer> list = new ArrayList<>();
            for (int c : cuts) list.add(c);
            int expected = brute(0, n, list);
            assertEquals(expected, MinimumCostToCutAStick.minCost(n, cuts));
            assertEquals(expected, MinimumCostToCutAStick.minCostMemo(n, cuts));
        }
    }
}
