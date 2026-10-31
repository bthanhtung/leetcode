package PalindromePartitioningII;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PalindromePartitioningIITest {

    private static boolean pal(String s) {
        return new StringBuilder(s).reverse().toString().equals(s);
    }

    private static int brute(String s) {
        if (pal(s)) return 0;
        int best = s.length() - 1;
        for (int i = 1; i < s.length(); i++) if (pal(s.substring(0, i))) best = Math.min(best, 1 + brute(s.substring(i)));
        return best;
    }

    @Test
    void examplesAndRandom() {
        assertEquals(1, PalindromePartitioningII.minCut("aab"));
        assertEquals(0, PalindromePartitioningII.minCut("a"));
        assertEquals(1, PalindromePartitioningII.minCut("ab"));
        assertEquals(2, PalindromePartitioningII.minCut("abccbc"));
        Random r = new Random(132);
        for (int t = 0; t < 1000; t++) {
            StringBuilder sb = new StringBuilder();
            for (int k = 1 + r.nextInt(10); k > 0; k--) sb.append((char) ('a' + r.nextInt(3)));
            int expected = brute(sb.toString());
            assertEquals(expected, PalindromePartitioningII.minCut(sb.toString()));
            assertEquals(expected, PalindromePartitioningII.minCutCenters(sb.toString()));
        }
    }
}
