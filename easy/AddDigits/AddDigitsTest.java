package AddDigits;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddDigitsTest {

    @Test
    void agreeOnFirstMillion() {
        assertEquals(2, AddDigits.addDigits(38));
        for (int n = 0; n <= 1_000_000; n++) {
            assertEquals(AddDigits.addDigitsLoop(n), AddDigits.addDigits(n));
        }
        assertEquals(AddDigits.addDigitsLoop(Integer.MAX_VALUE), AddDigits.addDigits(Integer.MAX_VALUE));
    }
}
