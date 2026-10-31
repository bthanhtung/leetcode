package ConcatenatedWords;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConcatenatedWordsTest {

    @Test
    void examplesAndRandom() {
        String[] words = {"cat", "cats", "catsdogcats", "dog", "dogcatsdog", "hippopotamuses", "rat", "ratcatdogcat"};
        Set<String> expected = Set.of("catsdogcats", "dogcatsdog", "ratcatdogcat");
        assertEquals(expected, new HashSet<>(ConcatenatedWords.findAllConcatenatedWordsInADict(words)));
        assertEquals(expected, new HashSet<>(ConcatenatedWords.findAllConcatenatedWordsTrie(words)));
        assertEquals(List.of("catdog"), ConcatenatedWords.findAllConcatenatedWordsInADict(new String[]{"cat", "dog", "catdog"}));
        Random r = new Random(472);
        for (int t = 0; t < 500; t++) {
            Set<String> set = new HashSet<>();
            for (int k = 1 + r.nextInt(8); k > 0; k--) {
                StringBuilder w = new StringBuilder();
                for (int c = 1 + r.nextInt(5); c > 0; c--) w.append((char) ('a' + r.nextInt(2)));
                set.add(w.toString());
            }
            String[] arr = set.toArray(new String[0]);
            assertEquals(new HashSet<>(ConcatenatedWords.findAllConcatenatedWordsInADict(arr)), new HashSet<>(ConcatenatedWords.findAllConcatenatedWordsTrie(arr)));
        }
    }
}
