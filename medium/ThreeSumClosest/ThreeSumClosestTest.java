package ThreeSumClosest;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ThreeSumClosestTest {

    @Test
    void examples() {
        assertEquals(2, ThreeSumClosest.threeSumClosest(new int[]{-1, 2, 1, -4}, 1));
        assertEquals(0, ThreeSumClosest.threeSumClosest(new int[]{0, 0, 0}, 1));
    }

    @Test
    void randomSameDistanceAsBruteForce() {
        Random r = new Random(16);
        for (int t = 0; t < 1000; t++) {
            int[] nums = r.ints(3 + r.nextInt(10), -20, 20).toArray();
            int target = r.nextInt(60) - 30;
            int expected = ThreeSumClosest.threeSumClosestBruteForce(nums, target);
            int got = ThreeSumClosest.threeSumClosest(nums, target);
            assertEquals(Math.abs(expected - target), Math.abs(got - target));
        }
    }
}
