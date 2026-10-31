package RemoveElement;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RemoveElementTest {

    private static void check(int[] original, int val, int[] after, int k) {
        int[] expected = Arrays.stream(original).filter(x -> x != val).sorted().toArray();
        assertEquals(expected.length, k);
        int[] kept = Arrays.copyOf(after, k);
        Arrays.sort(kept);
        assertArrayEquals(expected, kept);
    }

    @Test
    void examplesAndRandom() {
        Random r = new Random(27);
        for (int t = 0; t < 500; t++) {
            int[] nums = t == 0 ? new int[]{3, 2, 2, 3} : r.ints(r.nextInt(12), 0, 4).toArray();
            int val = t == 0 ? 3 : r.nextInt(4);
            int[] a = nums.clone(), b = nums.clone();
            check(nums, val, a, RemoveElement.removeElement(a, val));
            check(nums, val, b, RemoveElement.removeElementSwap(b, val));
        }
    }

    @Test
    void firstApproachKeepsOrder() {
        int[] nums = {0, 1, 2, 2, 3, 0, 4, 2};
        int k = RemoveElement.removeElement(nums, 2);
        assertArrayEquals(new int[]{0, 1, 3, 0, 4}, Arrays.copyOf(nums, k));
    }
}
