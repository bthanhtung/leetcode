package NumberComplement;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumberComplementTest {

    @Test
    void examplesAndRandom() {
        assertEquals(2, NumberComplement.findComplement(5));
        assertEquals(0, NumberComplement.findComplement(1));
        assertEquals(0, NumberComplement.findComplement(Integer.MAX_VALUE));
        Random r = new Random(476);
        for (int t = 0; t < 5000; t++) {
            int num = 1 + r.nextInt(Integer.MAX_VALUE);
            assertEquals(NumberComplement.findComplementLoop(num), NumberComplement.findComplement(num));
        }
    }
}
