package AddBinary;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddBinaryTest {

    @Test
    void examplesAndRandom() {
        assertEquals("100", AddBinary.addBinary("11", "1"));
        assertEquals("10101", AddBinary.addBinary("1010", "1011"));
        assertEquals("0", AddBinary.addBinary("0", "0"));
        Random r = new Random(67);
        for (int t = 0; t < 1000; t++) {
            String a = new BigInteger(1 + r.nextInt(200), r).toString(2), b = new BigInteger(1 + r.nextInt(200), r).toString(2);
            assertEquals(AddBinary.addBinaryBigInteger(a, b), AddBinary.addBinary(a, b));
        }
    }
}
