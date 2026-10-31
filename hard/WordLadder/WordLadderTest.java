package WordLadder;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordLadderTest {

    @Test
    void examplesAndRandom() {
        List<String> words = List.of("hot", "dot", "dog", "lot", "log", "cog");
        assertEquals(5, WordLadder.ladderLength("hit", "cog", words));
        assertEquals(0, WordLadder.ladderLength("hit", "cog", List.of("hot", "dot", "dog", "lot", "log")));
        Random r = new Random(127);
        for (int t = 0; t < 500; t++) {
            Set<String> set = new HashSet<>();
            for (int k = 1 + r.nextInt(15); k > 0; k--) set.add(rnd(r));
            List<String> list = new ArrayList<>(set);
            String begin = rnd(r), end = list.get(r.nextInt(list.size()));
            if (begin.equals(end)) continue;
            assertEquals(WordLadder.ladderLength(begin, end, list), WordLadder.ladderLengthBidirectional(begin, end, list), begin + "->" + end + " " + list);
        }
    }

    private static String rnd(Random r) {
        char[] c = new char[3];
        for (int i = 0; i < 3; i++) c[i] = (char) ('a' + r.nextInt(3));
        return new String(c);
    }
}
