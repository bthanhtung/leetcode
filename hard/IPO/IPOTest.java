package IPO;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IPOTest {

    /** Exhaustive search over project orders (tiny inputs) to confirm the greedy is optimal. */
    private static int exhaustive(int k, int w, int[] p, int[] c, boolean[] used) {
        int best = w;
        if (k == 0) return best;
        for (int i = 0; i < p.length; i++) {
            if (used[i] || c[i] > w) continue;
            used[i] = true;
            best = Math.max(best, exhaustive(k - 1, w + p[i], p, c, used));
            used[i] = false;
        }
        return best;
    }

    @Test
    void examplesAndRandom() {
        assertEquals(4, IPO.findMaximizedCapital(2, 0, new int[]{1, 2, 3}, new int[]{0, 1, 1}));
        assertEquals(6, IPO.findMaximizedCapital(3, 0, new int[]{1, 2, 3}, new int[]{0, 1, 2}));
        Random r = new Random(502);
        for (int t = 0; t < 500; t++) {
            int n = 1 + r.nextInt(6), k = 1 + r.nextInt(n + 1), w = r.nextInt(5);
            int[] p = r.ints(n, 0, 10).toArray(), c = r.ints(n, 0, 12).toArray();
            int expected = exhaustive(k, w, p, c, new boolean[n]);
            assertEquals(expected, IPO.findMaximizedCapital(k, w, p, c));
            assertEquals(expected, IPO.findMaximizedCapitalBrute(k, w, p, c));
        }
    }
}
