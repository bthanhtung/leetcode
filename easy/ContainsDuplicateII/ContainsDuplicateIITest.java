package ContainsDuplicateII;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContainsDuplicateIITest {

    private static void all(boolean expected, int[] nums, int k) {
        assertEquals(expected, ContainsDuplicateII.containsNearbyDuplicateBruteForce(nums, k));
        assertEquals(expected, ContainsDuplicateII.containsNearbyDuplicateLastIndex(nums, k));
        assertEquals(expected, ContainsDuplicateII.containsNearbyDuplicateWindow(nums, k));
    }

    @Test
    void examples() {
        all(true, new int[]{1, 2, 3, 1}, 3);
        all(true, new int[]{1, 0, 1, 1}, 1);
        all(false, new int[]{1, 2, 3, 1, 2, 3}, 2);
        all(false, new int[]{1, 1}, 0);
        all(false, new int[]{}, 5);
    }

    @Test
    void randomAgree() {
        Random r = new Random(219);
        for (int t = 0; t < 1000; t++) {
            int[] nums = r.ints(r.nextInt(15), 0, 6).toArray();
            int k = r.nextInt(6);
            all(ContainsDuplicateII.containsNearbyDuplicateBruteForce(nums, k), nums, k);
        }
    }
}
