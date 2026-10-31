package ZigzagConversion;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ZigzagConversionTest {

    @Test
    void examplesAndRandom() {
        assertEquals("PAHNAPLSIIGYIR", ZigzagConversion.convert("PAYPALISHIRING", 3));
        assertEquals("PINALSIGYAHRPI", ZigzagConversion.convert("PAYPALISHIRING", 4));
        assertEquals("A", ZigzagConversion.convert("A", 1));
        Random r = new Random(6);
        for (int t = 0; t < 2000; t++) {
            StringBuilder sb = new StringBuilder();
            for (int k = 1 + r.nextInt(30); k > 0; k--) sb.append((char) ('a' + r.nextInt(26)));
            int rows = 1 + r.nextInt(8);
            assertEquals(ZigzagConversion.convert(sb.toString(), rows), ZigzagConversion.convertFormula(sb.toString(), rows));
        }
    }
}
