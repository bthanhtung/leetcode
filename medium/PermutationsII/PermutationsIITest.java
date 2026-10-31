package PermutationsII;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PermutationsIITest {

    private static void allPermutations(int[] a, int k, Set<List<Integer>> out) {
        if (k == a.length) {
            List<Integer> p = new ArrayList<>();
            for (int x : a) p.add(x);
            out.add(p);
            return;
        }
        for (int i = k; i < a.length; i++) {
            int t = a[k]; a[k] = a[i]; a[i] = t;
            allPermutations(a, k + 1, out);
            t = a[k]; a[k] = a[i]; a[i] = t;
        }
    }

    @Test
    void examplesAndRandom() {
        assertEquals(List.of(List.of(1, 1, 2), List.of(1, 2, 1), List.of(2, 1, 1)), PermutationsII.permuteUnique(new int[]{1, 1, 2}));
        Random r = new Random(47);
        for (int t = 0; t < 300; t++) {
            int[] nums = r.ints(1 + r.nextInt(6), 0, 3).toArray();
            Set<List<Integer>> expected = new HashSet<>();
            allPermutations(nums.clone(), 0, expected);
            List<List<Integer>> a = PermutationsII.permuteUnique(nums), b = PermutationsII.permuteUniqueCounts(nums);
            assertEquals(expected.size(), a.size(), "no duplicates");
            assertEquals(expected, new HashSet<>(a));
            assertEquals(a, b);                     // both produce lexicographic order
        }
    }
}
