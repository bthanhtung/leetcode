package SetMismatch;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SetMismatchTest {

    @Test
    void examplesAndRandom() {
        assertArrayEquals(new int[]{2, 3}, SetMismatch.findErrorNums(new int[]{1, 2, 2, 4}));
        assertArrayEquals(new int[]{1, 2}, SetMismatch.findErrorNums(new int[]{1, 1}));
        Random r = new Random(645);
        for (int t = 0; t < 3000; t++) {
            int n = 2 + r.nextInt(30), missing = 1 + r.nextInt(n), dup;
            do dup = 1 + r.nextInt(n); while (dup == missing);
            List<Integer> list = new ArrayList<>();
            for (int v = 1; v <= n; v++) list.add(v == missing ? dup : v);
            Collections.shuffle(list, r);
            int[] nums = list.stream().mapToInt(Integer::intValue).toArray();
            int[] expected = {dup, missing};
            assertArrayEquals(expected, SetMismatch.findErrorNums(nums));
            assertArrayEquals(expected, SetMismatch.findErrorNumsCount(nums));
            assertArrayEquals(expected, SetMismatch.findErrorNumsMath(nums));
        }
    }
}
