package GroupAnagrams;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GroupAnagramsTest {

    private static Set<Set<String>> normalize(List<List<String>> groups) {
        return groups.stream().map(HashSet::new).collect(Collectors.toSet());
    }

    @Test
    void examples() {
        String[] words = {"eat", "tea", "tan", "ate", "nat", "bat"};
        Set<Set<String>> expected = Set.of(Set.of("bat"), Set.of("nat", "tan"), Set.of("ate", "eat", "tea"));
        assertEquals(expected, normalize(GroupAnagrams.groupAnagrams(words)));
        assertEquals(expected, normalize(GroupAnagrams.groupAnagramsCount(words)));
        assertEquals(Set.of(Set.of("")), normalize(GroupAnagrams.groupAnagrams(new String[]{""})));
    }

    @Test
    void randomAgree() {
        Random r = new Random(49);
        for (int t = 0; t < 300; t++) {
            String[] words = new String[1 + r.nextInt(10)];
            for (int i = 0; i < words.length; i++) {
                StringBuilder sb = new StringBuilder();
                for (int k = r.nextInt(4); k > 0; k--) sb.append((char) ('a' + r.nextInt(3)));
                words[i] = sb.toString();
            }
            assertEquals(GroupAnagrams.groupAnagrams(words).stream().mapToInt(List::size).sum(), words.length);
            assertEquals(new HashSet<>(GroupAnagrams.groupAnagrams(words).stream().map(l -> l.stream().sorted().toList()).toList()),
                    new HashSet<>(GroupAnagrams.groupAnagramsCount(words).stream().map(l -> l.stream().sorted().toList()).toList()));
        }
    }
}
