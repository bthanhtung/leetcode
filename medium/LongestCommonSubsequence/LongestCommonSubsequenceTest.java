package LongestCommonSubsequence;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LongestCommonSubsequenceTest {

    @Test
    void examplesAndRandom() {
        assertEquals(3, LongestCommonSubsequence.longestCommonSubsequence("abcde", "ace"));
        assertEquals(3, LongestCommonSubsequence.longestCommonSubsequence("abc", "abc"));
        assertEquals(0, LongestCommonSubsequence.longestCommonSubsequence("abc", "def"));
        Random r = new Random(1143);
        for (int t = 0; t < 1000; t++) {
            String a = rnd(r), b = rnd(r);
            int expected = LongestCommonSubsequence.longestCommonSubsequenceTable(a, b);
            assertEquals(expected, LongestCommonSubsequence.longestCommonSubsequence(a, b));
            assertEquals(expected, LongestCommonSubsequence.longestCommonSubsequenceMemo(a, b));
        }
    }

    private static String rnd(Random r) {
        StringBuilder sb = new StringBuilder();
        for (int k = 1 + r.nextInt(10); k > 0; k--) sb.append((char) ('a' + r.nextInt(3)));
        return sb.toString();
    }
}
