package WordPattern;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordPatternTest {

    @Test
    void examplesAndRandom() {
        assertEquals(true, WordPattern.wordPattern("abba", "dog cat cat dog"));
        assertEquals(false, WordPattern.wordPattern("abba", "dog cat cat fish"));
        assertEquals(false, WordPattern.wordPattern("aaaa", "dog cat cat dog"));
        assertEquals(false, WordPattern.wordPattern("abba", "dog dog dog dog"));
        assertEquals(false, WordPattern.wordPattern("aaa", "aa aa aa aa"));
        String[] vocab = {"dog", "cat", "fish"};
        Random r = new Random(290);
        for (int t = 0; t < 3000; t++) {
            int n = 1 + r.nextInt(5);
            StringBuilder p = new StringBuilder();
            String[] words = new String[n + (r.nextInt(10) == 0 ? 1 : 0)];
            for (int i = 0; i < n; i++) p.append((char) ('a' + r.nextInt(3)));
            for (int i = 0; i < words.length; i++) words[i] = vocab[r.nextInt(3)];
            String s = String.join(" ", words);
            assertEquals(WordPattern.wordPattern(p.toString(), s), WordPattern.wordPatternIndex(p.toString(), s));
        }
    }
}
