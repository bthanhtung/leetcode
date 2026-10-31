package LongestSubstringWithoutRepeatingCharacters;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LongestSubstringWithoutRepeatingCharactersTest {

    private static void all(int expected, String s) {
        assertEquals(expected, LongestSubstringWithoutRepeatingCharacters.lengthOfLongestSubstringBruteForce(s), s);
        assertEquals(expected, LongestSubstringWithoutRepeatingCharacters.lengthOfLongestSubstringSet(s), s);
        assertEquals(expected, LongestSubstringWithoutRepeatingCharacters.lengthOfLongestSubstring(s), s);
    }

    @Test
    void examples() {
        all(3, "abcabcbb");
        all(1, "bbbbb");
        all(3, "pwwkew");
        all(0, "");
        all(1, " ");
        all(3, "dvdf");
        all(2, "abba");      // left must not move backwards
    }

    @Test
    void randomAgree() {
        Random r = new Random(3);
        for (int t = 0; t < 2000; t++) {
            StringBuilder sb = new StringBuilder();
            for (int k = r.nextInt(15); k > 0; k--) sb.append((char) ('a' + r.nextInt(5)));
            all(LongestSubstringWithoutRepeatingCharacters.lengthOfLongestSubstringBruteForce(sb.toString()), sb.toString());
        }
    }
}
