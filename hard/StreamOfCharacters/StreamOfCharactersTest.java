package StreamOfCharacters;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StreamOfCharactersTest {

    @Test
    void exampleAndRandom() {
        StreamOfCharacters.StreamChecker checker = new StreamOfCharacters.StreamChecker(new String[]{"cd", "f", "kl"});
        StringBuilder hits = new StringBuilder();
        for (char c = 'a'; c <= 'l'; c++) if (checker.query(c)) hits.append(c);
        assertEquals("dfl", hits.toString());
        Random r = new Random(1032);
        for (int t = 0; t < 100; t++) {
            String[] words = new String[1 + r.nextInt(5)];
            for (int i = 0; i < words.length; i++) {
                StringBuilder w = new StringBuilder();
                for (int k = 1 + r.nextInt(4); k > 0; k--) w.append((char) ('a' + r.nextInt(3)));
                words[i] = w.toString();
            }
            StreamOfCharacters.StreamChecker a = new StreamOfCharacters.StreamChecker(words);
            StreamOfCharacters.StreamCheckerBrute b = new StreamOfCharacters.StreamCheckerBrute(words);
            for (int q = 0; q < 100; q++) {
                char c = (char) ('a' + r.nextInt(3));
                assertEquals(b.query(c), a.query(c));
            }
        }
    }
}
