package CountingBits;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CountingBitsTest {

    @Test
    void matchesBitCount() {
        assertArrayEquals(new int[]{0, 1, 1}, CountingBits.countBits(2));
        int n = 100_000;
        int[] a = CountingBits.countBits(n), b = CountingBits.countBitsLowestBit(n), c = CountingBits.countBitsNaive(n);
        for (int i = 0; i <= n; i++) {
            assertEquals(Integer.bitCount(i), a[i]);
            assertEquals(Integer.bitCount(i), b[i]);
            assertEquals(Integer.bitCount(i), c[i]);
        }
    }
}
