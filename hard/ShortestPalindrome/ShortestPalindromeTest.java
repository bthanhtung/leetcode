package ShortestPalindrome;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShortestPalindromeTest {

    @Test
    void examplesAndRandom() {
        assertEquals("aaacecaaa", ShortestPalindrome.shortestPalindrome("aacecaaa"));
        assertEquals("dcbabcd", ShortestPalindrome.shortestPalindrome("abcd"));
        assertEquals("", ShortestPalindrome.shortestPalindrome(""));
        Random r = new Random(214);
        for (int t = 0; t < 2000; t++) {
            StringBuilder sb = new StringBuilder();
            for (int k = r.nextInt(12); k > 0; k--) sb.append((char) ('a' + r.nextInt(3)));
            String expected = ShortestPalindrome.shortestPalindromeBrute(sb.toString());
            assertEquals(expected, ShortestPalindrome.shortestPalindrome(sb.toString()));
            assertEquals(expected, ShortestPalindrome.shortestPalindromeHash(sb.toString()));
        }
    }
}
