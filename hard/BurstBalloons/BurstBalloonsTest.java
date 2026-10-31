package BurstBalloons;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BurstBalloonsTest {

    /** Try every burst order (n! orders, tiny inputs only). */
    private static int brute(List<Integer> balloons) {
        if (balloons.isEmpty()) return 0;
        int best = 0;
        for (int i = 0; i < balloons.size(); i++) {
            int left = i > 0 ? balloons.get(i - 1) : 1, right = i + 1 < balloons.size() ? balloons.get(i + 1) : 1;
            List<Integer> rest = new ArrayList<>(balloons);
            rest.remove(i);
            best = Math.max(best, left * balloons.get(i) * right + brute(rest));
        }
        return best;
    }

    @Test
    void examplesAndRandom() {
        assertEquals(167, BurstBalloons.maxCoins(new int[]{3, 1, 5, 8}));
        assertEquals(10, BurstBalloons.maxCoins(new int[]{1, 5}));
        Random r = new Random(312);
        for (int t = 0; t < 300; t++) {
            int[] nums = r.ints(1 + r.nextInt(7), 0, 10).toArray();
            List<Integer> list = new ArrayList<>();
            for (int x : nums) list.add(x);
            int expected = brute(list);
            assertEquals(expected, BurstBalloons.maxCoins(nums));
            assertEquals(expected, BurstBalloons.maxCoinsMemo(nums));
        }
    }
}
