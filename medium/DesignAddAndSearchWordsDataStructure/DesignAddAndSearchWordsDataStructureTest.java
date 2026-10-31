package DesignAddAndSearchWordsDataStructure;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DesignAddAndSearchWordsDataStructureTest {

    @Test
    void exampleAndRandom() {
        DesignAddAndSearchWordsDataStructure.WordDictionary d = new DesignAddAndSearchWordsDataStructure.WordDictionary();
        d.addWord("bad");
        d.addWord("dad");
        d.addWord("mad");
        assertEquals(false, d.search("pad"));
        assertEquals(true, d.search("bad"));
        assertEquals(true, d.search(".ad"));
        assertEquals(true, d.search("b.."));
        assertEquals(false, d.search("b."));
        assertEquals(false, d.search("ba"));

        Random r = new Random(211);
        DesignAddAndSearchWordsDataStructure.WordDictionary w = new DesignAddAndSearchWordsDataStructure.WordDictionary();
        Set<String> words = new HashSet<>();
        for (int op = 0; op < 5000; op++) {
            StringBuilder sb = new StringBuilder();
            for (int k = 1 + r.nextInt(4); k > 0; k--) sb.append((char) ('a' + r.nextInt(3)));
            if (r.nextInt(3) == 0) {
                w.addWord(sb.toString());
                words.add(sb.toString());
            } else {
                for (int i = 0; i < sb.length(); i++) if (r.nextInt(3) == 0) sb.setCharAt(i, '.');
                String regex = sb.toString();
                boolean expected = words.stream().anyMatch(x -> x.matches(regex));
                assertEquals(expected, w.search(regex), regex);
            }
        }
    }
}
