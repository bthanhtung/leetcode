package MultiplyStrings;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MultiplyStringsTest {

    @Test
    void examplesAndRandom() {
        assertEquals("6", MultiplyStrings.multiply("2", "3"));
        assertEquals("56088", MultiplyStrings.multiply("123", "456"));
        assertEquals("0", MultiplyStrings.multiply("0", "9999"));
        Random r = new Random(43);
        for (int t = 0; t < 1000; t++) {
            BigInteger a = new BigInteger(1 + r.nextInt(200), r), b = new BigInteger(1 + r.nextInt(200), r);
            assertEquals(a.multiply(b).toString(), MultiplyStrings.multiply(a.toString(), b.toString()));
        }
    }
}
