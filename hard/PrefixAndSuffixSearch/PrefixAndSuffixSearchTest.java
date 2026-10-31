package PrefixAndSuffixSearch;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrefixAndSuffixSearchTest {

    @Test
    void exampleAndRandom() {
        assertEquals(0, new PrefixAndSuffixSearch.WordFilterTrie(new String[]{"apple"}).f("a", "e"));
        Random r = new Random(745);
        for (int t = 0; t < 200; t++) {
            String[] words = new String[1 + r.nextInt(6)];
            for (int i = 0; i < words.length; i++) words[i] = rnd(r, 1 + r.nextInt(5));
            PrefixAndSuffixSearch.WordFilterTrie a = new PrefixAndSuffixSearch.WordFilterTrie(words);
            PrefixAndSuffixSearch.WordFilterMap b = new PrefixAndSuffixSearch.WordFilterMap(words);
            for (int q = 0; q < 30; q++) {
                String pref = rnd(r, r.nextInt(3)), suff = rnd(r, r.nextInt(3));
                int expected = -1;
                for (int i = 0; i < words.length; i++) if (words[i].startsWith(pref) && words[i].endsWith(suff)) expected = i;
                assertEquals(expected, a.f(pref, suff));
                assertEquals(expected, b.f(pref, suff));
            }
        }
    }

    private static String rnd(Random r, int n) {
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < n; k++) sb.append((char) ('a' + r.nextInt(3)));
        return sb.toString();
    }
}
