package BestTimeToBuyAndSellStock;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static BestTimeToBuyAndSellStock.BestTimeToBuyAndSellStock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BestTimeToBuyAndSellStockTest {

    @Test
    void examples() {
        for (int[] prices : new int[][]{{7, 1, 5, 3, 6, 4}}) {
            assertEquals(5, maxProfitBruteForce(prices));
            assertEquals(5, maxProfitOnePass(prices));
            assertEquals(5, maxProfitDp(prices));
        }
        assertEquals(0, maxProfitOnePass(new int[]{7, 6, 4, 3, 1}));
        assertEquals(0, maxProfitDp(new int[]{7, 6, 4, 3, 1}));
    }

    @Test
    void edgeCases() {
        assertEquals(0, maxProfitOnePass(new int[]{5}));            // one day: no trade
        assertEquals(0, maxProfitDp(new int[]{3, 3, 3}));           // flat
        assertEquals(4, maxProfitOnePass(new int[]{1, 2, 3, 4, 5})); // buy first, sell last
        assertEquals(7, maxProfitDp(new int[]{2, 9, 1, 3}));         // the lowest price (1) comes too late to matter
    }

    @Test
    void allApproachesAgreeOnRandomInputs() {
        Random r = new Random(7);
        for (int t = 0; t < 500; t++) {
            int[] prices = r.ints(1 + r.nextInt(40), 0, 100).toArray();
            int expected = maxProfitBruteForce(prices);
            assertEquals(expected, maxProfitOnePass(prices));
            assertEquals(expected, maxProfitDp(prices));
        }
    }
}
