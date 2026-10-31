package PowXN;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PowXNTest {

    @Test
    void examplesAndRandom() {
        assertEquals(1024.0, PowXN.myPow(2.0, 10), 1e-9);
        assertEquals(9.261, PowXN.myPow(2.1, 3), 1e-9);
        assertEquals(0.25, PowXN.myPow(2.0, -2), 1e-12);
        assertEquals(0.0, PowXN.myPow(2.0, Integer.MIN_VALUE), 1e-12);
        assertEquals(1.0, PowXN.myPow(1.0, Integer.MIN_VALUE), 1e-12);
        assertEquals(1.0, PowXN.myPow(-1.0, Integer.MIN_VALUE), 1e-12);
        Random r = new Random(50);
        for (int t = 0; t < 5000; t++) {
            double x = -3 + 6 * r.nextDouble();
            int n = r.nextInt(41) - 20;
            double expected = Math.pow(x, n);
            assertEquals(expected, PowXN.myPow(x, n), Math.abs(expected) * 1e-9 + 1e-9);
            assertEquals(expected, PowXN.myPowRecursive(x, n), Math.abs(expected) * 1e-9 + 1e-9);
        }
    }
}
