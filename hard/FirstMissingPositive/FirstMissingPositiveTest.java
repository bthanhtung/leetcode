package FirstMissingPositive;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FirstMissingPositiveTest {

    @Test
    void examplesAndRandom() {
        assertEquals(3, FirstMissingPositive.firstMissingPositive(new int[]{1, 2, 0}));
        assertEquals(2, FirstMissingPositive.firstMissingPositive(new int[]{3, 4, -1, 1}));
        assertEquals(1, FirstMissingPositive.firstMissingPositive(new int[]{7, 8, 9, 11, 12}));
        assertEquals(2, FirstMissingPositive.firstMissingPositive(new int[]{1, 1}));
        Random r = new Random(41);
        for (int t = 0; t < 3000; t++) {
            int[] nums = r.ints(1 + r.nextInt(12), -3, 12).toArray();
            int expected = FirstMissingPositive.firstMissingPositiveSet(nums);
            assertEquals(expected, FirstMissingPositive.firstMissingPositive(nums));
            assertEquals(expected, FirstMissingPositive.firstMissingPositiveMark(nums));
        }
    }
}
