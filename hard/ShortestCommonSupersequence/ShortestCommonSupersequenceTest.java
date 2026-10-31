package ShortestCommonSupersequence;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShortestCommonSupersequenceTest {

    private static boolean isSubsequence(String s, String t) {
        int i = 0;
        for (int j = 0; j < t.length() && i < s.length(); j++) if (s.charAt(i) == t.charAt(j)) i++;
        return i == s.length();
    }

    private static int lcs(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];
        for (int i = 1; i <= a.length(); i++)
            for (int j = 1; j <= b.length(); j++)
                dp[i][j] = a.charAt(i - 1) == b.charAt(j - 1) ? dp[i - 1][j - 1] + 1 : Math.max(dp[i - 1][j], dp[i][j - 1]);
        return dp[a.length()][b.length()];
    }

    @Test
    void examplesAndRandom() {
        assertEquals("cabac", ShortestCommonSupersequence.shortestCommonSupersequence("abac", "cab"));
        assertEquals("aaaaaaaa", ShortestCommonSupersequence.shortestCommonSupersequence("aaaaaaaa", "aaaaaaaa"));
        Random r = new Random(1092);
        for (int t = 0; t < 1000; t++) {
            String a = rnd(r), b = rnd(r);
            int expectedLength = a.length() + b.length() - lcs(a, b);
            for (String s : new String[]{ShortestCommonSupersequence.shortestCommonSupersequence(a, b), ShortestCommonSupersequence.shortestCommonSupersequenceStrings(a, b)}) {
                assertEquals(expectedLength, s.length());
                assertTrue(isSubsequence(a, s) && isSubsequence(b, s));
            }
        }
    }

    private static String rnd(Random r) {
        StringBuilder sb = new StringBuilder();
        for (int k = 1 + r.nextInt(8); k > 0; k--) sb.append((char) ('a' + r.nextInt(3)));
        return sb.toString();
    }
}
