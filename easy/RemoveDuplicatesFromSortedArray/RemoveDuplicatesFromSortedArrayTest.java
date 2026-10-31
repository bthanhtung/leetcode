package RemoveDuplicatesFromSortedArray;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RemoveDuplicatesFromSortedArrayTest {

    @Test
    void examplesAndRandom() {
        int[] a = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        assertEquals(5, RemoveDuplicatesFromSortedArray.removeDuplicates(a));
        assertArrayEquals(new int[]{0, 1, 2, 3, 4}, Arrays.copyOf(a, 5));
        Random r = new Random(26);
        for (int t = 0; t < 1000; t++) {
            int[] nums = r.ints(r.nextInt(15), -3, 4).sorted().toArray();
            int[] expected = Arrays.stream(nums).distinct().toArray();
            int[] b = nums.clone(), c = nums.clone();
            int kb = RemoveDuplicatesFromSortedArray.removeDuplicates(b), kc = RemoveDuplicatesFromSortedArray.removeDuplicatesKeepK(c, 1);
            assertArrayEquals(expected, Arrays.copyOf(b, kb));
            assertArrayEquals(expected, Arrays.copyOf(c, kc));
        }
    }
}
