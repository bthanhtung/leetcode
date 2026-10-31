package FindAllAnagramsInAString;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FindAllAnagramsInAStringTest {

    @Test
    void examplesAndRandom() {
        assertEquals(List.of(0, 6), FindAllAnagramsInAString.findAnagrams("cbaebabacd", "abc"));
        assertEquals(List.of(0, 1, 2), FindAllAnagramsInAString.findAnagrams("abab", "ab"));
        assertEquals(List.of(), FindAllAnagramsInAString.findAnagrams("a", "ab"));
        Random r = new Random(438);
        for (int t = 0; t < 2000; t++) {
            String s = rnd(r, 1 + r.nextInt(15)), p = rnd(r, 1 + r.nextInt(4));
            List<Integer> expected = FindAllAnagramsInAString.findAnagramsSort(s, p);
            assertEquals(expected, FindAllAnagramsInAString.findAnagrams(s, p));
            assertEquals(expected, FindAllAnagramsInAString.findAnagramsMatches(s, p));
        }
    }

    private static String rnd(Random r, int n) {
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < n; k++) sb.append((char) ('a' + r.nextInt(3)));
        return sb.toString();
    }
}
