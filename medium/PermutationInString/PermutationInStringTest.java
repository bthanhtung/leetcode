package PermutationInString;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PermutationInStringTest {

    @Test
    void examplesAndRandom() {
        assertEquals(true, PermutationInString.checkInclusion("ab", "eidbaooo"));
        assertEquals(false, PermutationInString.checkInclusion("ab", "eidboaoo"));
        assertEquals(false, PermutationInString.checkInclusion("abc", "ab"));
        Random r = new Random(567);
        for (int t = 0; t < 3000; t++) {
            String a = rnd(r, 1 + r.nextInt(4)), b = rnd(r, 1 + r.nextInt(10));
            char[] key = a.toCharArray();
            Arrays.sort(key);
            boolean expected = false;
            for (int i = 0; i + a.length() <= b.length() && !expected; i++) {
                char[] w = b.substring(i, i + a.length()).toCharArray();
                Arrays.sort(w);
                expected = Arrays.equals(key, w);
            }
            assertEquals(expected, PermutationInString.checkInclusion(a, b), a + " in " + b);
        }
    }

    private static String rnd(Random r, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) sb.append((char) ('a' + r.nextInt(3)));
        return sb.toString();
    }
}
