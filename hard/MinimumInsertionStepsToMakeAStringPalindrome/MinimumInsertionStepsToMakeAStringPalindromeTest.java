package MinimumInsertionStepsToMakeAStringPalindrome;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinimumInsertionStepsToMakeAStringPalindromeTest {

    private static int brute(String s) {
        if (s.length() <= 1) return 0;
        if (s.charAt(0) == s.charAt(s.length() - 1)) return brute(s.substring(1, s.length() - 1));
        return 1 + Math.min(brute(s.substring(1)), brute(s.substring(0, s.length() - 1)));
    }

    @Test
    void examplesAndRandom() {
        assertEquals(0, MinimumInsertionStepsToMakeAStringPalindrome.minInsertions("zzazz"));
        assertEquals(2, MinimumInsertionStepsToMakeAStringPalindrome.minInsertions("mbadm"));
        assertEquals(5, MinimumInsertionStepsToMakeAStringPalindrome.minInsertions("leetcode"));
        Random r = new Random(1312);
        for (int t = 0; t < 1000; t++) {
            StringBuilder sb = new StringBuilder();
            for (int k = 1 + r.nextInt(10); k > 0; k--) sb.append((char) ('a' + r.nextInt(3)));
            int expected = brute(sb.toString());
            assertEquals(expected, MinimumInsertionStepsToMakeAStringPalindrome.minInsertions(sb.toString()));
            assertEquals(expected, MinimumInsertionStepsToMakeAStringPalindrome.minInsertionsLps(sb.toString()));
        }
    }
}
