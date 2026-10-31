package PlusOne;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class PlusOneTest {

    @Test
    void examples() {
        assertArrayEquals(new int[]{1, 2, 4}, PlusOne.plusOne(new int[]{1, 2, 3}));
        assertArrayEquals(new int[]{4, 3, 2, 2}, PlusOne.plusOne(new int[]{4, 3, 2, 1}));
        assertArrayEquals(new int[]{1, 0}, PlusOne.plusOne(new int[]{9}));
        assertArrayEquals(new int[]{1, 0, 0, 0}, PlusOne.plusOneCarry(new int[]{9, 9, 9}));
        assertArrayEquals(new int[]{1}, PlusOne.plusOneCarry(new int[]{0}));
    }

    @Test
    void randomAgree() {
        Random r = new Random(66);
        for (int t = 0; t < 500; t++) {
            String n = new BigInteger(1 + r.nextInt(60), r).toString();
            int[] digits = n.chars().map(c -> c - '0').toArray();
            int[] expected = PlusOne.plusOneBigInteger(digits.clone());
            assertArrayEquals(expected, PlusOne.plusOne(digits.clone()));
            assertArrayEquals(expected, PlusOne.plusOneCarry(digits.clone()));
        }
    }
}
