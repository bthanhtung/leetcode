package SqrtX;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SqrtXTest {

    private static void all(int x) {
        int expected = (int) Math.floor(Math.sqrt(x));
        assertEquals(expected, SqrtX.mySqrt(x), "binary " + x);
        assertEquals(expected, SqrtX.mySqrtNewton(x), "newton " + x);
        if (x < 10_000_000) {
            assertEquals(expected, SqrtX.mySqrtLinear(x), "linear " + x);
        }
    }

    @Test
    void examplesAndEdges() {
        for (int x : new int[]{0, 1, 2, 3, 4, 8, 9, 15, 16, 2147395599, 2147395600, Integer.MAX_VALUE}) {
            all(x);
        }
    }

    @Test
    void random() {
        Random r = new Random(69);
        for (int t = 0; t < 3000; t++) {
            all(r.nextInt(Integer.MAX_VALUE));
            all(r.nextInt(1000));
        }
    }
}
