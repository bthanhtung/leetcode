package Combinations;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CombinationsTest {

    private static long binom(int n, int k) {
        long c = 1;
        for (int i = 0; i < k; i++) c = c * (n - i) / (i + 1);
        return c;
    }

    @Test
    void countsAndAgreement() {
        assertEquals(List.of(List.of(1, 2), List.of(1, 3), List.of(1, 4), List.of(2, 3), List.of(2, 4), List.of(3, 4)), Combinations.combine(4, 2));
        for (int n = 1; n <= 10; n++) {
            for (int k = 1; k <= n; k++) {
                List<List<Integer>> a = Combinations.combine(n, k);
                assertEquals(binom(n, k), a.size());
                assertEquals(a.size(), new HashSet<>(a).size());
                assertEquals(a, Combinations.combineIterative(n, k));          // both lexicographic
            }
        }
    }
}
