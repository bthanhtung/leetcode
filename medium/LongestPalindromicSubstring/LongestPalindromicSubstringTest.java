package LongestPalindromicSubstring;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LongestPalindromicSubstringTest {

    private static boolean isPalindrome(String s) {
        return new StringBuilder(s).reverse().toString().equals(s);
    }

    private static int bruteLength(String s) {
        int best = 0;
        for (int i = 0; i < s.length(); i++)
            for (int j = i; j < s.length(); j++)
                if (isPalindrome(s.substring(i, j + 1))) best = Math.max(best, j - i + 1);
        return best;
    }

    private static void check(String s, String answer, int expectedLength) {
        assertTrue(s.contains(answer), s);
        assertTrue(isPalindrome(answer), answer);
        assertEquals(expectedLength, answer.length(), s);
    }

    @Test
    void examples() {
        assertEquals("bab", LongestPalindromicSubstring.longestPalindrome("babad"));
        assertEquals("bb", LongestPalindromicSubstring.longestPalindromeDp("cbbd"));
        assertEquals("a", LongestPalindromicSubstring.longestPalindromeManacher("a"));
        assertEquals("geeksskeeg", LongestPalindromicSubstring.longestPalindromeManacher("forgeeksskeegfor"));
    }

    @Test
    void randomAgainstBruteForce() {
        Random r = new Random(5);
        for (int t = 0; t < 1000; t++) {
            StringBuilder sb = new StringBuilder();
            for (int k = 1 + r.nextInt(14); k > 0; k--) sb.append((char) ('a' + r.nextInt(3)));
            String s = sb.toString();
            int len = bruteLength(s);
            check(s, LongestPalindromicSubstring.longestPalindrome(s), len);
            check(s, LongestPalindromicSubstring.longestPalindromeDp(s), len);
            check(s, LongestPalindromicSubstring.longestPalindromeManacher(s), len);
        }
    }
}
