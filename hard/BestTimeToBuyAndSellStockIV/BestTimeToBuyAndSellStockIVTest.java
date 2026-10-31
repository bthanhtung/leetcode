package BestTimeToBuyAndSellStockIV;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BestTimeToBuyAndSellStockIVTest {

    private static int brute(int[] p, int day, int left, boolean holding) {
        if (day == p.length) return 0;
        int skip = brute(p, day + 1, left, holding);
        if (holding) return Math.max(skip, p[day] + brute(p, day + 1, left - 1, false));
        return left > 0 ? Math.max(skip, -p[day] + brute(p, day + 1, left, true)) : skip;
    }

    @Test
    void examplesAndRandom() {
        assertEquals(2, BestTimeToBuyAndSellStockIV.maxProfit(2, new int[]{2, 4, 1}));
        assertEquals(7, BestTimeToBuyAndSellStockIV.maxProfit(2, new int[]{3, 2, 6, 5, 0, 3}));
        Random r = new Random(188);
        for (int t = 0; t < 1000; t++) {
            int[] p = r.ints(1 + r.nextInt(11), 0, 15).toArray();
            int k = r.nextInt(6);
            int expected = brute(p, 0, k, false);
            assertEquals(expected, BestTimeToBuyAndSellStockIV.maxProfit(k, p));
            assertEquals(expected, BestTimeToBuyAndSellStockIV.maxProfitTable(k, p));
        }
    }
}
