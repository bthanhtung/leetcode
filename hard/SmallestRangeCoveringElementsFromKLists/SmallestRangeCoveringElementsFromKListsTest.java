package SmallestRangeCoveringElementsFromKLists;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SmallestRangeCoveringElementsFromKListsTest {

    @Test
    void examplesAndRandom() {
        assertArrayEquals(new int[]{20, 24}, SmallestRangeCoveringElementsFromKLists.smallestRange(List.of(List.of(4, 10, 15, 24, 26), List.of(0, 9, 12, 20), List.of(5, 18, 22, 30))));
        assertArrayEquals(new int[]{1, 1}, SmallestRangeCoveringElementsFromKLists.smallestRange(List.of(List.of(1, 2, 3), List.of(1, 2, 3), List.of(1, 2, 3))));
        Random r = new Random(632);
        for (int t = 0; t < 1000; t++) {
            List<List<Integer>> nums = new ArrayList<>();
            for (int i = 0; i < 1 + r.nextInt(4); i++) {
                List<Integer> list = new ArrayList<>();
                for (int v : r.ints(1 + r.nextInt(5), -20, 20).sorted().toArray()) list.add(v);
                nums.add(list);
            }
            assertArrayEquals(SmallestRangeCoveringElementsFromKLists.smallestRangeWindow(nums), SmallestRangeCoveringElementsFromKLists.smallestRange(nums));
        }
    }
}
