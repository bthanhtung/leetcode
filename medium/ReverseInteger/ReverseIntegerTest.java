package ReverseInteger;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReverseIntegerTest {

    private static void all(int expected, int x) {
        assertEquals(expected, ReverseInteger.reverse(x), "int " + x);
        assertEquals(expected, ReverseInteger.reverseLong(x), "long " + x);
        assertEquals(expected, ReverseInteger.reverseString(x), "string " + x);
    }

    @Test
    void examples() {
        all(321, 123);
        all(-321, -123);
        all(21, 120);
        all(0, 0);
        all(0, 1534236469);
        all(0, Integer.MAX_VALUE);
        all(0, Integer.MIN_VALUE);
        all(-2147483641, -1463847412);     // reversal fits exactly
    }

    @Test
    void randomAgree() {
        Random r = new Random(7);
        for (int t = 0; t < 5000; t++) {
            int x = r.nextInt();
            all(ReverseInteger.reverseLong(x), x);
        }
    }
}
