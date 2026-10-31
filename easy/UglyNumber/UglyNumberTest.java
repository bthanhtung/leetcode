package UglyNumber;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UglyNumberTest {

    private static boolean brute(int n) {
        if (n <= 0) return false;
        for (int f = 2; (long) f * f <= n; f++) {
            if (n % f == 0) {
                if (f != 2 && f != 3 && f != 5) return false;
                while (n % f == 0) n /= f;
            }
        }
        return n == 1 || n == 2 || n == 3 || n == 5;
    }

    @Test
    void examplesAndBruteForce() {
        assertEquals(true, UglyNumber.isUgly(6));
        assertEquals(true, UglyNumber.isUgly(1));
        assertEquals(false, UglyNumber.isUgly(14));
        assertEquals(false, UglyNumber.isUgly(0));
        assertEquals(false, UglyNumber.isUgly(Integer.MIN_VALUE));
        for (int n = -50; n <= 200_000; n++) assertEquals(brute(n), UglyNumber.isUgly(n), "n=" + n);
    }
}
