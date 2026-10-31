package IsSubsequence;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IsSubsequenceTest {

    @Test
    void examplesAndRandom() {
        assertEquals(true, IsSubsequence.isSubsequence("abc", "ahbgdc"));
        assertEquals(false, IsSubsequence.isSubsequence("axc", "ahbgdc"));
        assertEquals(true, IsSubsequence.isSubsequence("", "abc"));
        Random r = new Random(392);
        for (int t = 0; t < 3000; t++) {
            String s = rnd(r, r.nextInt(5)), tt = rnd(r, r.nextInt(12));
            assertEquals(IsSubsequence.isSubsequence(s, tt), IsSubsequence.isSubsequenceIndexed(s, tt));
        }
    }

    private static String rnd(Random r, int n) {
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < n; k++) sb.append((char) ('a' + r.nextInt(3)));
        return sb.toString();
    }
}
