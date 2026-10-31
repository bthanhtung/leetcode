package ValidPerfectSquare;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidPerfectSquareTest {

    @Test
    void examplesAndRandom() {
        assertEquals(true, ValidPerfectSquare.isPerfectSquare(16));
        assertEquals(false, ValidPerfectSquare.isPerfectSquare(14));
        assertEquals(true, ValidPerfectSquare.isPerfectSquare(2147395600));
        assertEquals(false, ValidPerfectSquare.isPerfectSquare(Integer.MAX_VALUE));
        Random r = new Random(367);
        for (int t = 0; t < 20000; t++) {
            int num = 1 + (r.nextBoolean() ? r.nextInt(Integer.MAX_VALUE) : (int) Math.pow(1 + r.nextInt(46340), 2) + r.nextInt(2));
            long s = (long) Math.sqrt(num);
            boolean expected = s * s == num;
            assertEquals(expected, ValidPerfectSquare.isPerfectSquare(num), "num=" + num);
            assertEquals(expected, ValidPerfectSquare.isPerfectSquareNewton(num), "num=" + num);
            if (num < 1_000_000) assertEquals(expected, ValidPerfectSquare.isPerfectSquareOdd(num));
        }
    }
}
