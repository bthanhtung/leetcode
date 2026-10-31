package ScrambleString;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScrambleStringTest {

    /** All scrambles of s (tiny strings). */
    private static Set<String> all(String s) {
        Set<String> out = new HashSet<>();
        if (s.length() == 1) {
            out.add(s);
            return out;
        }
        for (int i = 1; i < s.length(); i++) {
            for (String x : all(s.substring(0, i)))
                for (String y : all(s.substring(i))) {
                    out.add(x + y);
                    out.add(y + x);
                }
        }
        return out;
    }

    @Test
    void examplesAndRandom() {
        assertEquals(true, ScrambleString.isScramble("great", "rgeat"));
        assertEquals(false, ScrambleString.isScramble("abcde", "caebd"));
        assertEquals(true, ScrambleString.isScramble("a", "a"));
        Random r = new Random(87);
        for (int t = 0; t < 300; t++) {
            StringBuilder a = new StringBuilder(), b = new StringBuilder();
            int n = 1 + r.nextInt(5);
            for (int i = 0; i < n; i++) a.append((char) ('a' + r.nextInt(3)));
            for (int i = 0; i < n; i++) b.append((char) ('a' + r.nextInt(3)));
            String s2 = r.nextBoolean() ? b.toString() : all(a.toString()).iterator().next();
            boolean expected = all(a.toString()).contains(s2);
            assertEquals(expected, ScrambleString.isScramble(a.toString(), s2));
            assertEquals(expected, ScrambleString.isScrambleDp(a.toString(), s2));
        }
    }
}
