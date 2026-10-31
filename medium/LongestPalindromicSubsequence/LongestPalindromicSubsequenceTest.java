package LongestPalindromicSubsequence;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LongestPalindromicSubsequenceTest {

    private static int brute(String s) {
        int best = 0, n = s.length();
        for (int mask = 1; mask < 1 << n; mask++) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) if ((mask >> i & 1) == 1) sb.append(s.charAt(i));
            if (sb.toString().contentEquals(new StringBuilder(sb).reverse())) best = Math.max(best, sb.length());
        }
        return best;
    }

    @Test
    void examplesAndRandom() {
        assertEquals(4, LongestPalindromicSubsequence.longestPalindromeSubseq("bbbab"));
        assertEquals(2, LongestPalindromicSubsequence.longestPalindromeSubseq("cbbd"));
        Random r = new Random(516);
        for (int t = 0; t < 500; t++) {
            StringBuilder sb = new StringBuilder();
            for (int k = 1 + r.nextInt(12); k > 0; k--) sb.append((char) ('a' + r.nextInt(3)));
            int expected = brute(sb.toString());
            assertEquals(expected, LongestPalindromicSubsequence.longestPalindromeSubseq(sb.toString()));
            assertEquals(expected, LongestPalindromicSubsequence.longestPalindromeSubseqMemo(sb.toString()));
            assertEquals(expected, LongestPalindromicSubsequence.longestPalindromeSubseqLcs(sb.toString()));
        }
    }
}
