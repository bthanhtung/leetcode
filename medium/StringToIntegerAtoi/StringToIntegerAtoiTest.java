package StringToIntegerAtoi;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringToIntegerAtoiTest {

    private static void all(int expected, String s) {
        assertEquals(expected, StringToIntegerAtoi.myAtoi(s), s);
        assertEquals(expected, StringToIntegerAtoi.myAtoiStateMachine(s), s);
    }

    @Test
    void examples() {
        all(42, "42");
        all(-42, "   -042");
        all(1337, "1337c0d3");
        all(0, "0-1");
        all(0, "words and 987");
        all(-2147483648, "-91283472332");
        all(2147483647, "2147483648");
        all(2147483647, "2147483647");
        all(-2147483648, "-2147483648");
        all(0, "+-12");
        all(0, "");
        all(0, "   ");
        all(12, "+12 34");
    }

    @Test
    void randomAgree() {
        Random r = new Random(8);
        String alphabet = " +-0123456789a";
        for (int t = 0; t < 5000; t++) {
            StringBuilder sb = new StringBuilder();
            for (int k = r.nextInt(14); k > 0; k--) sb.append(alphabet.charAt(r.nextInt(alphabet.length())));
            all(StringToIntegerAtoi.myAtoiStateMachine(sb.toString()), sb.toString());
        }
    }
}
