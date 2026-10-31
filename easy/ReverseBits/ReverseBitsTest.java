package ReverseBits;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReverseBitsTest {

    @Test
    void matchesIntegerReverse() {
        assertEquals(964176192, ReverseBits.reverseBits(43261596));
        Random r = new Random(190);
        for (int t = 0; t < 10000; t++) {
            int n = r.nextInt();
            assertEquals(Integer.reverse(n), ReverseBits.reverseBits(n));
            assertEquals(Integer.reverse(n), ReverseBits.reverseBitsSwap(n));
        }
    }
}
