package ValidPalindromeII;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidPalindromeIITest {

    private static boolean pal(String s) {
        return new StringBuilder(s).reverse().toString().equals(s);
    }

    @Test
    void examplesAndRandom() {
        assertEquals(true, ValidPalindromeII.validPalindrome("aba"));
        assertEquals(true, ValidPalindromeII.validPalindrome("abca"));
        assertEquals(false, ValidPalindromeII.validPalindrome("abc"));
        assertEquals(true, ValidPalindromeII.validPalindrome("aguokepatgbnvfqmgmlcupuufxoohdfpgjdmysgvhmvffcnqxjjxqncffvmhvgsymdjgpfdhooxfuupuculmgmqfvnbgtapekouga"));
        Random r = new Random(680);
        for (int t = 0; t < 3000; t++) {
            StringBuilder sb = new StringBuilder();
            for (int k = 1 + r.nextInt(8); k > 0; k--) sb.append((char) ('a' + r.nextInt(3)));
            String s = sb.toString();
            boolean expected = pal(s);
            for (int i = 0; i < s.length() && !expected; i++) expected = pal(s.substring(0, i) + s.substring(i + 1));
            assertEquals(expected, ValidPalindromeII.validPalindrome(s), s);
            assertEquals(expected, ValidPalindromeII.validPalindromeBudget(s), s);
        }
    }
}
