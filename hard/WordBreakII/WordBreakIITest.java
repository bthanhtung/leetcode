package WordBreakII;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordBreakIITest {

    @Test
    void examplesAndRandom() {
        assertEquals(Set.of("cats and dog", "cat sand dog"), new HashSet<>(WordBreakII.wordBreak("catsanddog", List.of("cat", "cats", "and", "sand", "dog"))));
        assertEquals(Set.of("pine apple pen apple", "pineapple pen apple", "pine applepen apple"),
                new HashSet<>(WordBreakII.wordBreak("pineapplepenapple", List.of("apple", "pen", "applepen", "pine", "pineapple"))));
        assertEquals(List.of(), WordBreakII.wordBreak("catsandog", List.of("cats", "dog", "sand", "and", "cat")));
        Random r = new Random(140);
        for (int t = 0; t < 500; t++) {
            StringBuilder s = new StringBuilder();
            for (int k = 1 + r.nextInt(10); k > 0; k--) s.append((char) ('a' + r.nextInt(2)));
            Set<String> dict = new HashSet<>();
            for (int k = 1 + r.nextInt(5); k > 0; k--) {
                StringBuilder w = new StringBuilder();
                for (int c = 1 + r.nextInt(3); c > 0; c--) w.append((char) ('a' + r.nextInt(2)));
                dict.add(w.toString());
            }
            List<String> words = List.copyOf(dict);
            List<String> a = WordBreakII.wordBreak(s.toString(), words), b = WordBreakII.wordBreakDp(s.toString(), words);
            assertEquals(new HashSet<>(a), new HashSet<>(b));
            assertEquals(a.size(), new HashSet<>(a).size());
            for (String sentence : a) {
                assertEquals(s.toString(), sentence.replace(" ", ""));
                for (String w : sentence.split(" ")) assertEquals(true, dict.contains(w));
            }
        }
    }
}
