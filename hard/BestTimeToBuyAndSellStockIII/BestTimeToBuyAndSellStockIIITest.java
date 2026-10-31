package BestTimeToBuyAndSellStockIII;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BestTimeToBuyAndSellStockIIITest {

    private static int brute(int[] p, int day, int left, boolean holding) {
        if (day == p.length) return 0;
        int skip = brute(p, day + 1, left, holding);
        if (holding) return Math.max(skip, p[day] + brute(p, day + 1, left - 1, false));
        return left > 0 ? Math.max(skip, -p[day] + brute(p, day + 1, left, true)) : skip;
    }

    @Test
    void examplesAndRandom() {
        assertEquals(6, BestTimeToBuyAndSellStockIII.maxProfit(new int[]{3, 3, 5, 0, 0, 3, 1, 4}));
        assertEquals(4, BestTimeToBuyAndSellStockIII.maxProfit(new int[]{1, 2, 3, 4, 5}));
        assertEquals(0, BestTimeToBuyAndSellStockIII.maxProfit(new int[]{7, 6, 4, 3, 1}));
        Random r = new Random(123);
        for (int t = 0; t < 1000; t++) {
            int[] p = r.ints(1 + r.nextInt(12), 0, 15).toArray();
            int expected = brute(p, 0, 2, false);
            assertEquals(expected, BestTimeToBuyAndSellStockIII.maxProfit(p));
            assertEquals(expected, BestTimeToBuyAndSellStockIII.maxProfitSplit(p));
        }
    }
}
