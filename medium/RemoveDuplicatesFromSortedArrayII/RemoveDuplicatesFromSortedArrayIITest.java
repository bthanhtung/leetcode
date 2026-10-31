package RemoveDuplicatesFromSortedArrayII;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class RemoveDuplicatesFromSortedArrayIITest {

    private static int[] expected(int[] sorted) {
        List<Integer> out = new ArrayList<>();
        for (int i = 0; i < sorted.length; i++) {
            int n = out.size();
            if (n < 2 || out.get(n - 1) != sorted[i] || out.get(n - 2) != sorted[i]) out.add(sorted[i]);
        }
        return out.stream().mapToInt(Integer::intValue).toArray();
    }

    @Test
    void random() {
        Random r = new Random(80);
        for (int t = 0; t < 2000; t++) {
            int[] nums = r.ints(r.nextInt(15), 0, 4).sorted().toArray();
            int[] want = expected(nums), a = nums.clone(), b = nums.clone();
            assertArrayEquals(want, Arrays.copyOf(a, RemoveDuplicatesFromSortedArrayII.removeDuplicates(a)));
            assertArrayEquals(want, Arrays.copyOf(b, RemoveDuplicatesFromSortedArrayII.removeDuplicatesCount(b)));
        }
    }
}
