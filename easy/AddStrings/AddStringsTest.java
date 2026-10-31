package AddStrings;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddStringsTest {

    @Test
    void examplesAndRandom() {
        assertEquals("134", AddStrings.addStrings("11", "123"));
        assertEquals("533", AddStrings.addStrings("456", "77"));
        assertEquals("0", AddStrings.addStrings("0", "0"));
        Random r = new Random(415);
        for (int t = 0; t < 1000; t++) {
            BigInteger a = new BigInteger(1 + r.nextInt(150), r), b = new BigInteger(1 + r.nextInt(150), r);
            assertEquals(a.add(b).toString(), AddStrings.addStrings(a.toString(), b.toString()));
        }
    }
}
