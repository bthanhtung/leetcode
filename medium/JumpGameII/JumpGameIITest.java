package JumpGameII;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JumpGameIITest {

    @Test
    void examplesAndRandom() {
        assertEquals(2, JumpGameII.jump(new int[]{2, 3, 1, 1, 4}));
        assertEquals(2, JumpGameII.jump(new int[]{2, 3, 0, 1, 4}));
        assertEquals(0, JumpGameII.jump(new int[]{0}));
        Random r = new Random(45);
        for (int t = 0; t < 3000; t++) {
            int[] nums = r.ints(1 + r.nextInt(12), 1, 4).toArray();      // all >= 1: always reachable
            if (r.nextBoolean() && nums.length > 2) nums[nums.length - 2] = 0;
            if (JumpGameII.jumpDp(nums) == Integer.MAX_VALUE) continue;
            assertEquals(JumpGameII.jumpDp(nums), JumpGameII.jump(nums));
        }
    }
}
