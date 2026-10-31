package PatchingArray;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PatchingArrayTest {

    @Test
    void examplesAndRandom() {
        assertEquals(1, PatchingArray.minPatches(new int[]{1, 3}, 6));
        assertEquals(2, PatchingArray.minPatches(new int[]{1, 5, 10}, 20));
        assertEquals(0, PatchingArray.minPatches(new int[]{1, 2, 2}, 5));
        assertEquals(28, PatchingArray.minPatches(new int[]{1, 2, 31, 33}, Integer.MAX_VALUE));
        Random r = new Random(330);
        for (int t = 0; t < 150; t++) {
            int[] nums = r.ints(r.nextInt(4), 1, 12).sorted().toArray();
            int n = 1 + r.nextInt(14);
            assertEquals(PatchingArray.bruteForce(nums, n), PatchingArray.minPatches(nums, n));
        }
    }
}
