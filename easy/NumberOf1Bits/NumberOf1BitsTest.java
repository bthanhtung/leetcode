package NumberOf1Bits;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumberOf1BitsTest {

    @Test
    void matchesBitCount() {
        Random r = new Random(191);
        for (int t = 0; t < 20000; t++) {
            int n = t < 100 ? t : r.nextInt();
            assertEquals(Integer.bitCount(n), NumberOf1Bits.hammingWeight(n));
            assertEquals(Integer.bitCount(n), NumberOf1Bits.hammingWeightShift(n));
            assertEquals(Integer.bitCount(n), NumberOf1Bits.hammingWeightSwar(n));
        }
        assertEquals(32, NumberOf1Bits.hammingWeight(-1));
    }
}
