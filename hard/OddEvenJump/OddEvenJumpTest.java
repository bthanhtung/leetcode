package OddEvenJump;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OddEvenJumpTest {

    private static int simulate(int[] a) {
        int n = a.length, good = 0;
        for (int s = 0; s < n; s++) {
            int i = s;
            for (int jump = 1; i != n - 1 && i >= 0; jump++) {
                int best = -1;
                for (int j = i + 1; j < n; j++) {
                    boolean ok = jump % 2 == 1 ? a[j] >= a[i] : a[j] <= a[i];
                    if (!ok) continue;
                    if (best < 0 || (jump % 2 == 1 ? a[j] < a[best] : a[j] > a[best])) best = j;
                }
                i = best;
            }
            if (i == n - 1) good++;
        }
        return good;
    }

    @Test
    void examplesAndRandom() {
        assertEquals(2, OddEvenJump.oddEvenJumps(new int[]{10, 13, 12, 14, 15}));
        assertEquals(3, OddEvenJump.oddEvenJumps(new int[]{2, 3, 1, 1, 4}));
        assertEquals(3, OddEvenJump.oddEvenJumps(new int[]{5, 1, 3, 4, 2}));
        Random r = new Random(975);
        for (int t = 0; t < 2000; t++) {
            int[] a = r.ints(1 + r.nextInt(12), 0, 6).toArray();
            int expected = simulate(a);
            assertEquals(expected, OddEvenJump.oddEvenJumps(a));
            assertEquals(expected, OddEvenJump.oddEvenJumpsStack(a));
        }
    }
}
