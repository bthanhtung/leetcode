package FrogJump;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FrogJumpTest {

    @Test
    void examplesAndRandom() {
        assertEquals(true, FrogJump.canCross(new int[]{0, 1, 3, 5, 6, 8, 12, 17}));
        assertEquals(false, FrogJump.canCross(new int[]{0, 1, 2, 3, 4, 8, 9, 11}));
        assertEquals(false, FrogJump.canCross(new int[]{0, 2}));
        Random r = new Random(403);
        for (int t = 0; t < 2000; t++) {
            TreeSet<Integer> set = new TreeSet<>();
            set.add(0);
            int pos = 0;
            for (int k = 1 + r.nextInt(10); k > 0; k--) {
                pos += 1 + r.nextInt(4);
                set.add(pos);
            }
            int[] stones = set.stream().mapToInt(Integer::intValue).toArray();
            assertEquals(FrogJump.canCross(stones), FrogJump.canCrossMemo(stones));
        }
    }
}
