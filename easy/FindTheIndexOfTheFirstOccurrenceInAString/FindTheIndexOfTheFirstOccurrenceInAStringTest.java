package FindTheIndexOfTheFirstOccurrenceInAString;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FindTheIndexOfTheFirstOccurrenceInAStringTest {

    @Test
    void examplesAndRandom() {
        assertEquals(0, FindTheIndexOfTheFirstOccurrenceInAString.strStr("sadbutsad", "sad"));
        assertEquals(-1, FindTheIndexOfTheFirstOccurrenceInAString.strStr("leetcode", "leeto"));
        assertEquals(4, FindTheIndexOfTheFirstOccurrenceInAString.strStrKmp("aabaaabaaac", "aabaaac"));
        Random r = new Random(28);
        for (int t = 0; t < 5000; t++) {
            String h = rnd(r, 1 + r.nextInt(20)), nd = rnd(r, 1 + r.nextInt(4));
            int expected = h.indexOf(nd);
            assertEquals(expected, FindTheIndexOfTheFirstOccurrenceInAString.strStr(h, nd));
            assertEquals(expected, FindTheIndexOfTheFirstOccurrenceInAString.strStrKmp(h, nd));
            assertEquals(expected, FindTheIndexOfTheFirstOccurrenceInAString.strStrRabinKarp(h, nd));
        }
    }

    private static String rnd(Random r, int n) {
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < n; k++) sb.append((char) ('a' + r.nextInt(2)));
        return sb.toString();
    }
}
