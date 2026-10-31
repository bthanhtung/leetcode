package MergeStringsAlternately;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MergeStringsAlternatelyTest {

    @Test
    void examples() {
        String[][] cases = {{"abc", "pqr", "apbqcr"}, {"ab", "pqrs", "apbqrs"}, {"abcd", "pq", "apbqcd"}, {"a", "", "a"}};
        for (String[] c : cases) {
            assertEquals(c[2], MergeStringsAlternately.mergeAlternately(c[0], c[1]));
            assertEquals(c[2], MergeStringsAlternately.mergeAlternatelyIndex(c[0], c[1]));
        }
    }
}
