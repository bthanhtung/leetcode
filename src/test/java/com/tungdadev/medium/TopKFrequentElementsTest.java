package com.tungdadev.medium;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TopKFrequentElementsTest {

    private static final List<BiFunction<int[], Integer, int[]>> APPROACHES = List.of(
            TopKFrequentElements::topKSort, TopKFrequentElements::topKHeap,
            TopKFrequentElements::topKBucket, TopKFrequentElements::topKQuickselect);

    private static Set<Integer> set(int[] a) {
        return Arrays.stream(a).boxed().collect(Collectors.toSet());
    }

    @Test
    void examples() {
        for (var f : APPROACHES) {
            assertEquals(Set.of(1, 2), set(f.apply(new int[]{1, 1, 1, 2, 2, 3}, 2)));
            assertEquals(Set.of(1), set(f.apply(new int[]{1}, 1)));
            assertEquals(Set.of(-1, 2), set(f.apply(new int[]{4, -1, -1, 2, 2, 2, -1, 3}, 2)));
        }
    }

    @Test
    void allApproachesAgreeOnRandomInputs() {
        Random r = new Random(11);
        int checked = 0;
        while (checked < 500) {
            int[] nums = r.ints(1 + r.nextInt(60), -6, 7).toArray();
            Map<Integer, Integer> freq = TopKFrequentElements.count(nums);
            List<Integer> counts = freq.values().stream().sorted(Comparator.reverseOrder()).toList();
            int k = 1 + r.nextInt(freq.size());
            // the problem guarantees a unique answer: skip ties at the k-th position
            if (k < counts.size() && counts.get(k - 1).equals(counts.get(k))) {
                continue;
            }
            Set<Integer> expected = set(TopKFrequentElements.topKSort(nums, k));
            for (var f : APPROACHES) {
                int[] got = f.apply(nums, k);
                assertEquals(k, got.length);
                assertEquals(expected, set(got));
            }
            checked++;
        }
    }
}
