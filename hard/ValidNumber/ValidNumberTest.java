package ValidNumber;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidNumberTest {

    private static final String REGEX = "[+-]?(\\d+\\.?\\d*|\\.\\d+)([eE][+-]?\\d+)?";

    @Test
    void examplesAndRegexReference() {
        for (String s : new String[]{"2", "0089", "-0.1", "+3.14", "4.", "-.9", "2e10", "-90E3", "3e+7", "+6e-1", "53.5e93", "-123.456e789"}) {
            assertEquals(true, ValidNumber.isNumber(s), s);
            assertEquals(true, ValidNumber.isNumberDfa(s), s);
        }
        for (String s : new String[]{"abc", "1a", "1e", "e3", "99e2.5", "--6", "-+3", "95a54e53", ".", "+", "e", ".e1", "4e+", "+.", "1e1e1"}) {
            assertEquals(false, ValidNumber.isNumber(s), s);
            assertEquals(false, ValidNumber.isNumberDfa(s), s);
        }
        Random r = new Random(65);
        String alphabet = "01.e+-E";
        for (int t = 0; t < 50000; t++) {
            StringBuilder sb = new StringBuilder();
            for (int k = 1 + r.nextInt(7); k > 0; k--) sb.append(alphabet.charAt(r.nextInt(alphabet.length())));
            String s = sb.toString();
            boolean expected = s.matches(REGEX);
            assertEquals(expected, ValidNumber.isNumber(s), s);
            assertEquals(expected, ValidNumber.isNumberDfa(s), s);
        }
    }
}
