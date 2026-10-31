package BestTimeToBuyAndSellStockWithCooldown;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BestTimeToBuyAndSellStockWithCooldownTest {

    private static int brute(int[] p, int day, boolean holding) {
        if (day >= p.length) return 0;
        int skip = brute(p, day + 1, holding);
        int act = holding ? p[day] + brute(p, day + 2, false) : -p[day] + brute(p, day + 1, true);
        return Math.max(skip, act);
    }

    @Test
    void examplesAndRandom() {
        assertEquals(3, BestTimeToBuyAndSellStockWithCooldown.maxProfit(new int[]{1, 2, 3, 0, 2}));
        assertEquals(0, BestTimeToBuyAndSellStockWithCooldown.maxProfit(new int[]{1}));
        Random r = new Random(309);
        for (int t = 0; t < 1000; t++) {
            int[] p = r.ints(1 + r.nextInt(12), 0, 15).toArray();
            int expected = brute(p, 0, false);
            assertEquals(expected, BestTimeToBuyAndSellStockWithCooldown.maxProfit(p));
            assertEquals(expected, BestTimeToBuyAndSellStockWithCooldown.maxProfitMemo(p));
        }
    }
}
