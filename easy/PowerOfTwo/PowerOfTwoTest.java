package PowerOfTwo;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PowerOfTwoTest {

    @Test
    void examplesAndRandom() {
        int[] edge = {0, 1, 2, 3, 16, 6, -16, Integer.MIN_VALUE, 1 << 30, Integer.MAX_VALUE};
        Random r = new Random(231);
        for (int t = 0; t < 100000; t++) {
            int n = t < edge.length ? edge[t] : (r.nextBoolean() ? r.nextInt() : 1 << r.nextInt(31));
            boolean expected = n > 0 && Integer.bitCount(n) == 1;
            assertEquals(expected, PowerOfTwo.isPowerOfTwo(n), "n=" + n);
            assertEquals(expected, PowerOfTwo.isPowerOfTwoLoop(n), "n=" + n);
            assertEquals(expected, PowerOfTwo.isPowerOfTwoLowestBit(n), "n=" + n);
        }
    }
}
