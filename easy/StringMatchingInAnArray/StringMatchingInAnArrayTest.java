package StringMatchingInAnArray;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringMatchingInAnArrayTest {

    private static void all(Set<String> expected, String... words) {
        assertEquals(expected, new HashSet<>(StringMatchingInAnArray.stringMatching(words)));
        assertEquals(expected, new HashSet<>(StringMatchingInAnArray.stringMatchingSorted(words)));
        assertEquals(expected, new HashSet<>(StringMatchingInAnArray.stringMatchingJoined(words)));
    }

    @Test
    void examples() {
        all(Set.of("as", "hero"), "mass", "as", "hero", "superhero");
        all(Set.of("et", "code"), "leetcode", "et", "code");
        all(Set.of(), "blue", "green", "bu");
    }

    @Test
    void randomAgree() {
        Random r = new Random(1408);
        for (int t = 0; t < 500; t++) {
            Set<String> words = new HashSet<>();
            for (int k = 1 + r.nextInt(6); k > 0; k--) {
                StringBuilder sb = new StringBuilder();
                for (int c = 1 + r.nextInt(4); c > 0; c--) sb.append((char) ('a' + r.nextInt(3)));
                words.add(sb.toString());
            }
            String[] arr = words.toArray(new String[0]);
            all(new HashSet<>(StringMatchingInAnArray.stringMatching(arr)), arr);
        }
    }
}
