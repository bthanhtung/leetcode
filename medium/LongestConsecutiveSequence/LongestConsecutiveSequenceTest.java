package LongestConsecutiveSequence;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LongestConsecutiveSequenceTest {

    @Test
    void examplesAndRandom() {
        assertEquals(4, LongestConsecutiveSequence.longestConsecutive(new int[]{100, 4, 200, 1, 3, 2}));
        assertEquals(9, LongestConsecutiveSequence.longestConsecutive(new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1}));
        assertEquals(0, LongestConsecutiveSequence.longestConsecutive(new int[]{}));
        assertEquals(2, LongestConsecutiveSequence.longestConsecutive(new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE - 1, Integer.MIN_VALUE}));
        Random r = new Random(128);
        for (int t = 0; t < 3000; t++) {
            int[] nums = r.ints(r.nextInt(15), -10, 10).toArray();
            assertEquals(LongestConsecutiveSequence.longestConsecutiveSort(nums), LongestConsecutiveSequence.longestConsecutive(nums));
        }
    }
}
