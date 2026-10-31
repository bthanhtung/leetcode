package PermutationSequence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PermutationSequenceTest {

    @Test
    void everyRankForSmallN() {
        assertEquals("213", PermutationSequence.getPermutation(3, 3));
        assertEquals("2314", PermutationSequence.getPermutation(4, 9));
        assertEquals("123", PermutationSequence.getPermutation(3, 1));
        for (int n = 1; n <= 7; n++) {
            int total = 1;
            for (int i = 2; i <= n; i++) total *= i;
            String prev = "";
            for (int k = 1; k <= total; k++) {
                String p = PermutationSequence.getPermutation(n, k);
                assertEquals(PermutationSequence.getPermutationNextPermutation(n, k), p);
                assertEquals(true, p.compareTo(prev) > 0, "lexicographic order");
                prev = p;
            }
        }
        assertEquals("987654321", PermutationSequence.getPermutation(9, 362880));
    }
}
