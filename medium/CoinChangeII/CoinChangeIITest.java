package CoinChangeII;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CoinChangeIITest {

    @Test
    void examplesAndRandom() {
        assertEquals(4, CoinChangeII.change(5, new int[]{1, 2, 5}));
        assertEquals(0, CoinChangeII.change(3, new int[]{2}));
        assertEquals(1, CoinChangeII.change(10, new int[]{10}));
        assertEquals(1, CoinChangeII.change(0, new int[]{7}));
        Random r = new Random(518);
        for (int t = 0; t < 500; t++) {
            int[] coins = r.ints(1 + r.nextInt(5), 1, 15).distinct().toArray();
            int amount = r.nextInt(60);
            int expected = CoinChangeII.changeTable(amount, coins);
            assertEquals(expected, CoinChangeII.change(amount, coins));
            assertEquals(expected, CoinChangeII.changeMemo(amount, coins));
        }
    }
}
