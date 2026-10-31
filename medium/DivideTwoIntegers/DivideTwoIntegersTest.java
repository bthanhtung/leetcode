package DivideTwoIntegers;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DivideTwoIntegersTest {

    private static int expected(int a, int b) {
        long q = (long) a / b;
        return q > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) q;
    }

    @Test
    void examplesEdgesAndRandom() {
        int[] edge = {0, 1, -1, 2, -2, 3, 7, -7, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE - 1, Integer.MIN_VALUE + 1};
        for (int a : edge) for (int b : edge) if (b != 0) {
            assertEquals(expected(a, b), DivideTwoIntegers.divide(a, b), a + "/" + b);
            assertEquals(expected(a, b), DivideTwoIntegers.divideShift(a, b), a + "/" + b);
        }
        Random r = new Random(29);
        for (int t = 0; t < 100000; t++) {
            int a = r.nextInt(), b = r.nextBoolean() ? r.nextInt() : r.nextInt(2001) - 1000;
            if (b == 0) continue;
            assertEquals(expected(a, b), DivideTwoIntegers.divide(a, b));
            assertEquals(expected(a, b), DivideTwoIntegers.divideShift(a, b));
        }
    }
}
