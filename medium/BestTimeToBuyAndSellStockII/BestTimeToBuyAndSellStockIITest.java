package BestTimeToBuyAndSellStockII;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BestTimeToBuyAndSellStockIITest {

    private static int brute(int[] p, int day, boolean holding) {
        if (day == p.length) return 0;
        int skip = brute(p, day + 1, holding);
        int act = holding ? p[day] + brute(p, day + 1, false) : -p[day] + brute(p, day + 1, true);
        return Math.max(skip, act);
    }

    @Test
    void examplesAndRandom() {
        assertEquals(7, BestTimeToBuyAndSellStockII.maxProfit(new int[]{7, 1, 5, 3, 6, 4}));
        assertEquals(4, BestTimeToBuyAndSellStockII.maxProfit(new int[]{1, 2, 3, 4, 5}));
        assertEquals(0, BestTimeToBuyAndSellStockII.maxProfit(new int[]{7, 6, 4, 3, 1}));
        Random r = new Random(122);
        for (int t = 0; t < 1000; t++) {
            int[] p = r.ints(1 + r.nextInt(12), 0, 20).toArray();
            int expected = brute(p, 0, false);
            assertEquals(expected, BestTimeToBuyAndSellStockII.maxProfit(p));
            assertEquals(expected, BestTimeToBuyAndSellStockII.maxProfitValleyPeak(p));
            assertEquals(expected, BestTimeToBuyAndSellStockII.maxProfitDp(p));
        }
    }
}
