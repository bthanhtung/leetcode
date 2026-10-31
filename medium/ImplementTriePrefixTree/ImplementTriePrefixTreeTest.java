package ImplementTriePrefixTree;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImplementTriePrefixTreeTest {

    private static String word(Random r) {
        StringBuilder sb = new StringBuilder();
        for (int k = 1 + r.nextInt(5); k > 0; k--) sb.append((char) ('a' + r.nextInt(3)));
        return sb.toString();
    }

    @Test
    void randomOperationsMatchSet() {
        Random r = new Random(208);
        ImplementTriePrefixTree.Trie a = new ImplementTriePrefixTree.Trie();
        ImplementTriePrefixTree.TrieMap b = new ImplementTriePrefixTree.TrieMap();
        Set<String> words = new HashSet<>();
        for (int t = 0; t < 5000; t++) {
            String w = word(r);
            switch (r.nextInt(3)) {
                case 0 -> { a.insert(w); b.insert(w); words.add(w); }
                case 1 -> {
                    assertEquals(words.contains(w), a.search(w));
                    assertEquals(words.contains(w), b.search(w));
                }
                default -> {
                    boolean expected = words.stream().anyMatch(x -> x.startsWith(w));
                    assertEquals(expected, a.startsWith(w));
                    assertEquals(expected, b.startsWith(w));
                }
            }
        }
    }
}
