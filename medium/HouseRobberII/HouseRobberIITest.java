package HouseRobberII;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HouseRobberIITest {

    @Test
    void examplesAndBruteForce() {
        assertEquals(3, HouseRobberII.rob(new int[]{2, 3, 2}));
        assertEquals(4, HouseRobberII.rob(new int[]{1, 2, 3, 1}));
        assertEquals(3, HouseRobberII.rob(new int[]{1, 2, 3}));
        assertEquals(7, HouseRobberII.rob(new int[]{7}));
        Random r = new Random(213);
        for (int t = 0; t < 3000; t++) {
            int[] a = r.ints(1 + r.nextInt(12), 0, 20).toArray();
            int n = a.length, best = 0;
            for (int mask = 0; mask < 1 << n; mask++) {
                boolean ok = true;
                for (int i = 0; i < n && ok; i++)
                    if ((mask >> i & 1) == 1 && (mask >> ((i + 1) % n) & 1) == 1 && n > 1) ok = false;
                if (!ok) continue;
                int s = 0;
                for (int i = 0; i < n; i++) if ((mask >> i & 1) == 1) s += a[i];
                best = Math.max(best, s);
            }
            assertEquals(best, HouseRobberII.rob(a));
        }
    }
}
