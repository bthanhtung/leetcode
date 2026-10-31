package RegularExpressionMatching;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegularExpressionMatchingTest {

    private static void all(boolean expected, String s, String p) {
        assertEquals(expected, RegularExpressionMatching.isMatch(s, p), s + " ~ " + p);
        assertEquals(expected, RegularExpressionMatching.isMatchMemo(s, p), s + " ~ " + p);
        assertEquals(expected, RegularExpressionMatching.isMatchRecursive(s, p), s + " ~ " + p);
    }

    @Test
    void examples() {
        all(false, "aa", "a");
        all(true, "aa", "a*");
        all(true, "ab", ".*");
        all(true, "aab", "c*a*b");
        all(false, "mississippi", "mis*is*p*.");
        all(true, "", "a*b*");
        all(false, "ab", ".*c");
    }

    @Test
    void randomAgainstJavaRegex() {
        Random r = new Random(10);
        for (int t = 0; t < 5000; t++) {
            StringBuilder s = new StringBuilder(), p = new StringBuilder();
            for (int k = r.nextInt(7); k > 0; k--) s.append((char) ('a' + r.nextInt(2)));
            for (int k = r.nextInt(5); k > 0; k--) {
                p.append(r.nextInt(4) == 0 ? '.' : (char) ('a' + r.nextInt(2)));
                if (r.nextBoolean()) p.append('*');
            }
            all(s.toString().matches(p.toString()), s.toString(), p.toString());
        }
    }
}
