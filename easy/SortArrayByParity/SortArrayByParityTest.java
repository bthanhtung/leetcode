package SortArrayByParity;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SortArrayByParityTest {

    private static void check(int[] original, int[] result) {
        int[] a = original.clone(), b = result.clone();
        Arrays.sort(a);
        Arrays.sort(b);
        assertArrayEquals(a, b, "must be a permutation");
        boolean seenOdd = false;
        for (int x : result) {
            if (x % 2 != 0) seenOdd = true;
            else assertTrue(!seenOdd, "even after odd: " + Arrays.toString(result));
        }
    }

    @Test
    void random() {
        Random r = new Random(905);
        for (int t = 0; t < 1000; t++) {
            int[] nums = r.ints(r.nextInt(12), 0, 20).toArray();
            check(nums, SortArrayByParity.sortArrayByParityTwoPass(nums.clone()));
            check(nums, SortArrayByParity.sortArrayByParity(nums.clone()));
            check(nums, SortArrayByParity.sortArrayByParityWrite(nums.clone()));
        }
    }
}
