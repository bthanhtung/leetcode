package ContainsDuplicate;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContainsDuplicateTest {

    private static void all(boolean expected, int... nums) {
        assertEquals(expected, ContainsDuplicate.containsDuplicateBruteForce(nums));
        assertEquals(expected, ContainsDuplicate.containsDuplicateSort(nums));
        assertEquals(expected, ContainsDuplicate.containsDuplicate(nums));
    }

    @Test
    void examples() {
        all(true, 1, 2, 3, 1);
        all(false, 1, 2, 3, 4);
        all(true, 1, 1, 1, 3, 3, 4, 3, 2, 4, 2);
        all(false, 7);
        all(false);
        all(true, Integer.MIN_VALUE, 0, Integer.MIN_VALUE);
    }

    @Test
    void randomAgree() {
        Random r = new Random(217);
        for (int t = 0; t < 500; t++) {
            int[] nums = r.ints(r.nextInt(20), -30, 30).toArray();
            all(ContainsDuplicate.containsDuplicateBruteForce(nums), nums);
        }
    }
}
