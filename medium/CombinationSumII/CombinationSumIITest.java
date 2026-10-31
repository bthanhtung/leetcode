package CombinationSumII;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CombinationSumIITest {

    private static Set<List<Integer>> brute(int[] c, int target) {
        Set<List<Integer>> out = new HashSet<>();
        for (int mask = 0; mask < 1 << c.length; mask++) {
            List<Integer> s = new ArrayList<>();
            int sum = 0;
            for (int i = 0; i < c.length; i++) if ((mask >> i & 1) == 1) { s.add(c[i]); sum += c[i]; }
            if (sum == target) { Collections.sort(s); out.add(s); }
        }
        return out;
    }

    @Test
    void examplesAndRandom() {
        assertEquals(List.of(List.of(1, 1, 6), List.of(1, 2, 5), List.of(1, 7), List.of(2, 6)), CombinationSumII.combinationSum2(new int[]{10, 1, 2, 7, 6, 1, 5}, 8));
        Random r = new Random(40);
        for (int t = 0; t < 500; t++) {
            int[] c = r.ints(r.nextInt(10), 1, 6).toArray();
            int target = 1 + r.nextInt(12);
            Set<List<Integer>> expected = brute(c, target);
            List<List<Integer>> a = CombinationSumII.combinationSum2(c, target), b = CombinationSumII.combinationSum2Counts(c, target);
            assertEquals(expected.size(), a.size());
            assertEquals(expected, new HashSet<>(a));
            assertEquals(expected.size(), b.size());
            assertEquals(expected, new HashSet<>(b));
        }
    }
}
