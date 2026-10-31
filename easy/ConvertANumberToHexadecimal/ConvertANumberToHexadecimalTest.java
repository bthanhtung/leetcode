package ConvertANumberToHexadecimal;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConvertANumberToHexadecimalTest {

    @Test
    void examplesAndRandom() {
        assertEquals("1a", ConvertANumberToHexadecimal.toHex(26));
        assertEquals("ffffffff", ConvertANumberToHexadecimal.toHex(-1));
        assertEquals("0", ConvertANumberToHexadecimal.toHex(0));
        assertEquals("80000000", ConvertANumberToHexadecimal.toHex(Integer.MIN_VALUE));
        Random r = new Random(405);
        for (int t = 0; t < 10000; t++) {
            int n = r.nextInt();
            assertEquals(Integer.toHexString(n), ConvertANumberToHexadecimal.toHex(n));
        }
    }
}
