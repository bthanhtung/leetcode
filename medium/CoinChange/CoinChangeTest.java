package CoinChange;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CoinChangeTest {

    @Test
    void examplesAndRandom() {
        assertEquals(3, CoinChange.coinChange(new int[]{1, 2, 5}, 11));
        assertEquals(-1, CoinChange.coinChange(new int[]{2}, 3));
        assertEquals(0, CoinChange.coinChange(new int[]{1}, 0));
        assertEquals(20, CoinChange.coinChange(new int[]{186, 419, 83, 408}, 6249));
        assertEquals(2, CoinChange.coinChange(new int[]{1, 3, 4}, 6));      // greedy would give 4 + 1 + 1
        Random r = new Random(322);
        for (int t = 0; t < 1000; t++) {
            int[] coins = r.ints(1 + r.nextInt(4), 1, 20).distinct().toArray();
            int amount = r.nextInt(200);
            assertEquals(CoinChange.coinChangeBfs(coins, amount), CoinChange.coinChange(coins, amount));
        }
        assertEquals(-1, CoinChange.coinChangeBfs(new int[]{Integer.MAX_VALUE}, 2));
    }
}
