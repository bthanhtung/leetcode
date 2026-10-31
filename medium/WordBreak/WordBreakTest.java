package WordBreak;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordBreakTest {

    @Test
    void examplesAndRandom() {
        assertEquals(true, WordBreak.wordBreak("leetcode", List.of("leet", "code")));
        assertEquals(true, WordBreak.wordBreak("applepenapple", List.of("apple", "pen")));
        assertEquals(false, WordBreak.wordBreak("catsandog", List.of("cats", "dog", "sand", "and", "cat")));
        String hard = "a".repeat(150) + "b";
        assertEquals(false, WordBreak.wordBreak(hard, List.of("a", "aa", "aaa", "aaaa", "aaaaa")));
        assertEquals(false, WordBreak.wordBreakBfs(hard, List.of("a", "aa", "aaa", "aaaa", "aaaaa")));
        Random r = new Random(139);
        for (int t = 0; t < 2000; t++) {
            List<String> dict = new ArrayList<>();
            for (int k = 1 + r.nextInt(4); k > 0; k--) dict.add(rnd(r, 1 + r.nextInt(3)));
            String s = rnd(r, 1 + r.nextInt(10));
            assertEquals(WordBreak.wordBreakBfs(s, dict), WordBreak.wordBreak(s, dict), s + " " + dict);
        }
    }

    private static String rnd(Random r, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) sb.append((char) ('a' + r.nextInt(2)));
        return sb.toString();
    }
}
