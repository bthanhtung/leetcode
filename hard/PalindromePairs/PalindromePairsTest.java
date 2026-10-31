package PalindromePairs;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PalindromePairsTest {

    @Test
    void examplesAndRandom() {
        assertEquals(Set.of(List.of(0, 1), List.of(1, 0), List.of(3, 2), List.of(2, 4)),
                new HashSet<>(PalindromePairs.palindromePairs(new String[]{"abcd", "dcba", "lls", "s", "sssll"})));
        assertEquals(Set.of(List.of(0, 1), List.of(1, 0)), new HashSet<>(PalindromePairs.palindromePairs(new String[]{"a", ""})));
        Random r = new Random(336);
        for (int t = 0; t < 1000; t++) {
            Set<String> set = new HashSet<>();
            for (int k = 1 + r.nextInt(7); k > 0; k--) {
                StringBuilder w = new StringBuilder();
                for (int c = r.nextInt(4); c > 0; c--) w.append((char) ('a' + r.nextInt(2)));
                set.add(w.toString());
            }
            String[] words = new ArrayList<>(set).toArray(new String[0]);
            List<List<Integer>> got = PalindromePairs.palindromePairs(words);
            assertEquals(new HashSet<>(PalindromePairs.palindromePairsBrute(words)), new HashSet<>(got));
            assertEquals(got.size(), new HashSet<>(got).size(), "no duplicate pairs");
        }
    }
}
