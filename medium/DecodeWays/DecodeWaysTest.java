package DecodeWays;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DecodeWaysTest {

    private static int brute(String s, int i) {
        if (i == s.length()) return 1;
        if (s.charAt(i) == '0') return 0;
        int ways = brute(s, i + 1);
        if (i + 1 < s.length() && Integer.parseInt(s.substring(i, i + 2)) <= 26) ways += brute(s, i + 2);
        return ways;
    }

    @Test
    void examplesAndBruteForce() {
        assertEquals(2, DecodeWays.numDecodings("12"));
        assertEquals(3, DecodeWays.numDecodings("226"));
        assertEquals(0, DecodeWays.numDecodings("06"));
        assertEquals(1, DecodeWays.numDecodings("10"));
        assertEquals(0, DecodeWays.numDecodings("100"));
        assertEquals(1836311903, DecodeWays.numDecodings("1".repeat(45)));
        Random r = new Random(91);
        for (int t = 0; t < 5000; t++) {
            StringBuilder sb = new StringBuilder();
            for (int k = 1 + r.nextInt(12); k > 0; k--) sb.append("0123456789".charAt(r.nextInt(r.nextBoolean() ? 3 : 10)));
            assertEquals(brute(sb.toString(), 0), DecodeWays.numDecodings(sb.toString()), sb.toString());
        }
    }
}
