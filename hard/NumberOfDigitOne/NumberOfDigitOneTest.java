package NumberOfDigitOne;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumberOfDigitOneTest {

    @Test
    void examplesAndBruteForce() {
        assertEquals(6, NumberOfDigitOne.countDigitOne(13));
        assertEquals(0, NumberOfDigitOne.countDigitOne(0));
        long running = 0;
        for (int n = 0; n <= 20_000; n++) {
            for (int x = n; x > 0; x /= 10) if (x % 10 == 1) running++;
            assertEquals(running, NumberOfDigitOne.countDigitOne(n), "n=" + n);
            assertEquals(running, NumberOfDigitOne.countDigitOneDp(n), "n=" + n);
        }
        assertEquals(NumberOfDigitOne.countDigitOne(1_000_000_000), NumberOfDigitOne.countDigitOneDp(1_000_000_000));
    }
}
