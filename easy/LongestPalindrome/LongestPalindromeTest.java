package LongestPalindrome;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LongestPalindromeTest {

    @Test
    void examplesAndRandom() {
        assertEquals(7, LongestPalindrome.longestPalindrome("abccccdd"));
        assertEquals(1, LongestPalindrome.longestPalindrome("a"));
        assertEquals(3, LongestPalindrome.longestPalindrome("Aaa"));        // "aAa": the pair of a, A in the middle
        Random r = new Random(409);
        for (int t = 0; t < 2000; t++) {
            StringBuilder sb = new StringBuilder();
            for (int k = 1 + r.nextInt(10); k > 0; k--) sb.append("abAB".charAt(r.nextInt(4)));
            assertEquals(LongestPalindrome.longestPalindromeSet(sb.toString()), LongestPalindrome.longestPalindrome(sb.toString()));
        }
    }
}
