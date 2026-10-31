package MinimumWindowSubstring;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinimumWindowSubstringTest {

    @Test
    void examplesAndRandom() {
        assertEquals("BANC", MinimumWindowSubstring.minWindow("ADOBECODEBANC", "ABC"));
        assertEquals("a", MinimumWindowSubstring.minWindow("a", "a"));
        assertEquals("", MinimumWindowSubstring.minWindow("a", "aa"));
        Random r = new Random(76);
        for (int t = 0; t < 2000; t++) {
            String s = rnd(r, 1 + r.nextInt(15)), tt = rnd(r, 1 + r.nextInt(4));
            String expected = MinimumWindowSubstring.minWindowBrute(s, tt), got = MinimumWindowSubstring.minWindow(s, tt);
            assertEquals(expected.length(), got.length(), s + " / " + tt);
        }
    }

    private static String rnd(Random r, int n) {
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < n; k++) sb.append((char) ('a' + r.nextInt(3)));
        return sb.toString();
    }
}
