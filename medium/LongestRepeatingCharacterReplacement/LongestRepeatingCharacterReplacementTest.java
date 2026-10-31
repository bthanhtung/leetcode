package LongestRepeatingCharacterReplacement;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LongestRepeatingCharacterReplacementTest {

    @Test
    void examplesAndRandom() {
        assertEquals(4, LongestRepeatingCharacterReplacement.characterReplacement("ABAB", 2));
        assertEquals(4, LongestRepeatingCharacterReplacement.characterReplacement("AABABBA", 1));
        Random r = new Random(424);
        for (int t = 0; t < 3000; t++) {
            StringBuilder sb = new StringBuilder();
            for (int i = 1 + r.nextInt(15); i > 0; i--) sb.append((char) ('A' + r.nextInt(3)));
            int k = r.nextInt(4);
            assertEquals(LongestRepeatingCharacterReplacement.characterReplacementBruteForce(sb.toString(), k), LongestRepeatingCharacterReplacement.characterReplacement(sb.toString(), k));
        }
    }
}
