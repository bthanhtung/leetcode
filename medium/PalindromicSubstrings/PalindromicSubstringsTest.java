package PalindromicSubstrings;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PalindromicSubstringsTest {

    @Test
    void examplesAndRandom() {
        assertEquals(3, PalindromicSubstrings.countSubstrings("abc"));
        assertEquals(6, PalindromicSubstrings.countSubstrings("aaa"));
        Random r = new Random(647);
        for (int t = 0; t < 1000; t++) {
            StringBuilder sb = new StringBuilder();
            for (int k = 1 + r.nextInt(15); k > 0; k--) sb.append((char) ('a' + r.nextInt(3)));
            int expected = PalindromicSubstrings.countSubstringsBruteForce(sb.toString());
            assertEquals(expected, PalindromicSubstrings.countSubstrings(sb.toString()));
            assertEquals(expected, PalindromicSubstrings.countSubstringsDp(sb.toString()));
        }
    }
}
