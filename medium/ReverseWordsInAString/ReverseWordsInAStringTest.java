package ReverseWordsInAString;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReverseWordsInAStringTest {

    private static void all(String expected, String s) {
        assertEquals(expected, ReverseWordsInAString.reverseWords(s), s);
        assertEquals(expected, ReverseWordsInAString.reverseWordsInPlace(s), s);
        assertEquals(expected, ReverseWordsInAString.reverseWordsDeque(s), s);
    }

    @Test
    void examplesAndRandom() {
        all("blue is sky the", "the sky is blue");
        all("world hello", "  hello world  ");
        all("example good a", "a good   example");
        all("x", "x");
        Random r = new Random(151);
        for (int t = 0; t < 2000; t++) {
            StringBuilder sb = new StringBuilder();
            for (int k = 1 + r.nextInt(12); k > 0; k--) sb.append(r.nextInt(3) == 0 ? ' ' : (char) ('a' + r.nextInt(3)));
            if (sb.toString().isBlank()) sb.append('z');
            all(ReverseWordsInAString.reverseWords(sb.toString()), sb.toString());
        }
    }
}
