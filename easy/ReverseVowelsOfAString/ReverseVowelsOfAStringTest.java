package ReverseVowelsOfAString;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReverseVowelsOfAStringTest {

    @Test
    void examplesAndRandom() {
        assertEquals("AceCreIm", ReverseVowelsOfAString.reverseVowels("IceCreAm"));
        assertEquals("leotcede", ReverseVowelsOfAString.reverseVowels("leetcode"));
        Random r = new Random(345);
        String alphabet = "aEbcIoxU .";
        for (int t = 0; t < 1000; t++) {
            StringBuilder sb = new StringBuilder();
            for (int k = r.nextInt(10); k > 0; k--) sb.append(alphabet.charAt(r.nextInt(alphabet.length())));
            assertEquals(ReverseVowelsOfAString.reverseVowelsCollect(sb.toString()), ReverseVowelsOfAString.reverseVowels(sb.toString()));
        }
    }
}
