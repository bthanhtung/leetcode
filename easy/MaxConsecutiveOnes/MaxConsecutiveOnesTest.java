package MaxConsecutiveOnes;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaxConsecutiveOnesTest {

    @Test
    void examplesAndRandom() {
        assertEquals(3, MaxConsecutiveOnes.findMaxConsecutiveOnes(new int[]{1, 1, 0, 1, 1, 1}));
        assertEquals(2, MaxConsecutiveOnes.findMaxConsecutiveOnes(new int[]{1, 0, 1, 1, 0, 1}));
        assertEquals(0, MaxConsecutiveOnes.findMaxConsecutiveOnes(new int[]{0}));
        Random r = new Random(485);
        for (int t = 0; t < 1000; t++) {
            int[] nums = r.ints(1 + r.nextInt(20), 0, 2).toArray();
            assertEquals(MaxConsecutiveOnes.findMaxConsecutiveOnes(nums), MaxConsecutiveOnes.findMaxConsecutiveOnesStart(nums));
        }
    }
}
