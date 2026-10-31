package DistinctSubsequences;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DistinctSubsequencesTest {

    private static int brute(String s, String t) {
        int count = 0;
        for (int mask = 0; mask < 1 << s.length(); mask++) {
            if (Integer.bitCount(mask) != t.length()) continue;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length(); i++) if ((mask >> i & 1) == 1) sb.append(s.charAt(i));
            if (sb.toString().equals(t)) count++;
        }
        return count;
    }

    @Test
    void examplesAndRandom() {
        assertEquals(3, DistinctSubsequences.numDistinct("rabbbit", "rabbit"));
        assertEquals(5, DistinctSubsequences.numDistinct("babgbag", "bag"));
        Random r = new Random(115);
        for (int t = 0; t < 1000; t++) {
            String s = rnd(r, 1 + r.nextInt(12)), tt = rnd(r, 1 + r.nextInt(4));
            int expected = brute(s, tt);
            assertEquals(expected, DistinctSubsequences.numDistinct(s, tt));
            assertEquals(expected, DistinctSubsequences.numDistinctTable(s, tt));
            assertEquals(expected, DistinctSubsequences.numDistinctMemo(s, tt));
        }
    }

    private static String rnd(Random r, int n) {
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < n; k++) sb.append((char) ('a' + r.nextInt(2)));
        return sb.toString();
    }
}
