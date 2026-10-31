package JumpGame;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JumpGameTest {

    @Test
    void examplesAndRandom() {
        assertEquals(true, JumpGame.canJump(new int[]{2, 3, 1, 1, 4}));
        assertEquals(false, JumpGame.canJump(new int[]{3, 2, 1, 0, 4}));
        assertEquals(true, JumpGame.canJump(new int[]{0}));
        Random r = new Random(55);
        for (int t = 0; t < 5000; t++) {
            int[] nums = r.ints(1 + r.nextInt(10), 0, 3).toArray();
            boolean[] ok = new boolean[nums.length];
            ok[0] = true;
            for (int i = 0; i < nums.length; i++)
                if (ok[i]) for (int j = i + 1; j <= Math.min(nums.length - 1, i + nums[i]); j++) ok[j] = true;
            assertEquals(ok[nums.length - 1], JumpGame.canJump(nums));
            assertEquals(ok[nums.length - 1], JumpGame.canJumpBackward(nums));
        }
    }
}
