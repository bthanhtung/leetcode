package WildcardMatching;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WildcardMatchingTest {

    @Test
    void examplesAndRegexReference() {
        assertEquals(false, WildcardMatching.isMatch("aa", "a"));
        assertEquals(true, WildcardMatching.isMatch("aa", "*"));
        assertEquals(false, WildcardMatching.isMatch("cb", "?a"));
        assertEquals(true, WildcardMatching.isMatch("adceb", "*a*b"));
        assertEquals(false, WildcardMatching.isMatch("acdcb", "a*c?b"));
        assertEquals(true, WildcardMatching.isMatch("", "***"));
        Random r = new Random(44);
        for (int t = 0; t < 5000; t++) {
            String s = rnd(r, r.nextInt(8), "ab"), p = rnd(r, r.nextInt(6), "ab?*");
            boolean expected = s.matches(p.replace("?", ".").replace("*", ".*"));
            assertEquals(expected, WildcardMatching.isMatch(s, p), s + " ~ " + p);
            assertEquals(expected, WildcardMatching.isMatchGreedy(s, p), s + " ~ " + p);
        }
    }

    private static String rnd(Random r, int n, String alphabet) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) sb.append(alphabet.charAt(r.nextInt(alphabet.length())));
        return sb.toString();
    }
}
