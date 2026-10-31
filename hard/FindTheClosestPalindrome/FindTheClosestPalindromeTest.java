package FindTheClosestPalindrome;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FindTheClosestPalindromeTest {

    private static boolean isPal(long x) {
        String s = String.valueOf(x);
        return new StringBuilder(s).reverse().toString().equals(s);
    }

    private static long brute(long n) {
        for (long d = 1; ; d++) {
            if (n - d >= 0 && isPal(n - d)) return n - d;
            if (isPal(n + d)) return n + d;
        }
    }

    @Test
    void examplesAndBruteForce() {
        assertEquals("121", FindTheClosestPalindrome.nearestPalindromic("123"));
        assertEquals("0", FindTheClosestPalindrome.nearestPalindromic("1"));
        assertEquals("9", FindTheClosestPalindrome.nearestPalindromic("10"));
        assertEquals("101", FindTheClosestPalindrome.nearestPalindromic("99"));
        assertEquals("9", FindTheClosestPalindrome.nearestPalindromic("11"));
        assertEquals("1000000000000000001", FindTheClosestPalindrome.nearestPalindromic("999999999999999999"));
        assertEquals("999999999999999999", FindTheClosestPalindrome.nearestPalindromic("1000000000000000000"));
        assertEquals("807045053350540708", FindTheClosestPalindrome.nearestPalindromic("807045053224792883"));
        for (long n = 1; n <= 30_000; n++) assertEquals(String.valueOf(brute(n)), FindTheClosestPalindrome.nearestPalindromic(String.valueOf(n)), "n=" + n);
    }
}
