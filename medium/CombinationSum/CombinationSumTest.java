package CombinationSum;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CombinationSumTest {

    @Test
    void examplesAndRandom() {
        assertEquals(List.of(List.of(2, 2, 3), List.of(7)), CombinationSum.combinationSum(new int[]{2, 3, 6, 7}, 7));
        assertEquals(List.of(), CombinationSum.combinationSum(new int[]{2}, 1));
        Random r = new Random(39);
        for (int t = 0; t < 300; t++) {
            int[] candidates = r.ints(1 + r.nextInt(5), 2, 12).distinct().toArray();
            int target = 1 + r.nextInt(25);
            List<List<Integer>> a = CombinationSum.combinationSum(candidates, target);
            Set<List<Integer>> set = new HashSet<>(a);
            assertEquals(a.size(), set.size(), "duplicates");
            for (List<Integer> combo : a) assertTrue(combo.stream().mapToInt(Integer::intValue).sum() == target);
            assertEquals(set, new HashSet<>(CombinationSum.combinationSumDp(candidates, target)));
        }
    }
}
