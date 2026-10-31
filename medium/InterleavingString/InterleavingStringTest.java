package InterleavingString;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InterleavingStringTest {

    private static boolean brute(String a, String b, String c) {
        if (c.isEmpty()) return a.isEmpty() && b.isEmpty();
        return (!a.isEmpty() && a.charAt(0) == c.charAt(0) && brute(a.substring(1), b, c.substring(1)))
                || (!b.isEmpty() && b.charAt(0) == c.charAt(0) && brute(a, b.substring(1), c.substring(1)));
    }

    @Test
    void examplesAndRandom() {
        assertEquals(true, InterleavingString.isInterleave("aabcc", "dbbca", "aadbbcbcac"));
        assertEquals(false, InterleavingString.isInterleave("aabcc", "dbbca", "aadbbbaccc"));
        assertEquals(true, InterleavingString.isInterleave("", "", ""));
        Random r = new Random(97);
        for (int t = 0; t < 3000; t++) {
            String a = rnd(r, r.nextInt(5)), b = rnd(r, r.nextInt(5)), c = rnd(r, a.length() + b.length() + (r.nextInt(8) == 0 ? 1 : 0));
            boolean expected = brute(a, b, c);
            assertEquals(expected, InterleavingString.isInterleave(a, b, c));
            assertEquals(expected, InterleavingString.isInterleaveRow(a, b, c));
        }
    }

    private static String rnd(Random r, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) sb.append((char) ('a' + r.nextInt(2)));
        return sb.toString();
    }
}
