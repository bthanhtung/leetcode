package ProductOfArrayExceptSelf;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ProductOfArrayExceptSelfTest {

    @Test
    void examplesAndRandom() {
        assertArrayEquals(new int[]{24, 12, 8, 6}, ProductOfArrayExceptSelf.productExceptSelf(new int[]{1, 2, 3, 4}));
        assertArrayEquals(new int[]{0, 0, 9, 0, 0}, ProductOfArrayExceptSelf.productExceptSelf(new int[]{-1, 1, 0, -3, 3}));
        Random r = new Random(238);
        for (int t = 0; t < 2000; t++) {
            int[] nums = r.ints(2 + r.nextInt(8), -3, 4).toArray();
            int[] expected = new int[nums.length];
            for (int i = 0; i < nums.length; i++) {
                int p = 1;
                for (int j = 0; j < nums.length; j++) if (j != i) p *= nums[j];
                expected[i] = p;
            }
            assertArrayEquals(expected, ProductOfArrayExceptSelf.productExceptSelf(nums));
            assertArrayEquals(expected, ProductOfArrayExceptSelf.productExceptSelfArrays(nums));
            assertArrayEquals(expected, ProductOfArrayExceptSelf.productExceptSelfDivision(nums));
        }
    }
}
