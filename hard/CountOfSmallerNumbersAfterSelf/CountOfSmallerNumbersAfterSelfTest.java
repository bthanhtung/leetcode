package CountOfSmallerNumbersAfterSelf;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountOfSmallerNumbersAfterSelfTest {

    @Test
    void examplesAndRandom() {
        assertEquals(List.of(2, 1, 1, 0), CountOfSmallerNumbersAfterSelf.countSmaller(new int[]{5, 2, 6, 1}));
        assertEquals(List.of(0), CountOfSmallerNumbersAfterSelf.countSmaller(new int[]{-1}));
        assertEquals(List.of(0, 0), CountOfSmallerNumbersAfterSelf.countSmaller(new int[]{-1, -1}));
        Random r = new Random(315);
        for (int t = 0; t < 2000; t++) {
            int[] nums = r.ints(1 + r.nextInt(20), -10, 10).toArray();
            List<Integer> expected = CountOfSmallerNumbersAfterSelf.countSmallerBrute(nums);
            assertEquals(expected, CountOfSmallerNumbersAfterSelf.countSmaller(nums));
            assertEquals(expected, CountOfSmallerNumbersAfterSelf.countSmallerFenwick(nums));
        }
    }
}
