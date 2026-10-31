package PalindromeNumber;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PalindromeNumberTest {

    private static void all(boolean expected, int x) {
        assertEquals(expected, PalindromeNumber.isPalindromeString(x), "string " + x);
        assertEquals(expected, PalindromeNumber.isPalindromeReverse(x), "reverse " + x);
        assertEquals(expected, PalindromeNumber.isPalindromeHalf(x), "half " + x);
    }

    @Test
    void examples() {
        all(true, 121);
        all(false, -121);
        all(false, 10);
        all(true, 0);
        all(true, 1221);
        all(true, 12321);
        all(false, Integer.MAX_VALUE);      // 2147483647
        all(true, 2147447412);              // largest int palindrome: reversing it in an int would overflow nothing, still checked
    }

    @Test
    void randomAgreesWithString() {
        Random r = new Random(9);
        for (int t = 0; t < 2000; t++) {
            int x = r.nextBoolean() ? r.nextInt() : r.nextInt(100_000);
            all(new StringBuilder(Integer.toString(x)).reverse().toString().equals(Integer.toString(x)), x);
        }
    }
}
