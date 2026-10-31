package SortColors;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SortColorsTest {

    @Test
    void random() {
        Random r = new Random(75);
        for (int t = 0; t < 2000; t++) {
            int[] nums = r.ints(r.nextInt(15), 0, 3).toArray();
            int[] expected = nums.clone(), a = nums.clone(), b = nums.clone();
            Arrays.sort(expected);
            SortColors.sortColors(a);
            SortColors.sortColorsCounting(b);
            assertArrayEquals(expected, a);
            assertArrayEquals(expected, b);
        }
    }
}
