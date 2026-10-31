package MaximumProfitInJobScheduling;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaximumProfitInJobSchedulingTest {

    private static int brute(int[] s, int[] e, int[] p) {
        int n = s.length, best = 0;
        for (int mask = 0; mask < 1 << n; mask++) {
            boolean ok = true;
            int sum = 0;
            for (int i = 0; i < n && ok; i++) {
                if ((mask >> i & 1) == 0) continue;
                sum += p[i];
                for (int j = i + 1; j < n; j++)
                    if ((mask >> j & 1) == 1 && s[i] < e[j] && s[j] < e[i]) { ok = false; break; }
            }
            if (ok) best = Math.max(best, sum);
        }
        return best;
    }

    @Test
    void examplesAndRandom() {
        assertEquals(120, MaximumProfitInJobScheduling.jobScheduling(new int[]{1, 2, 3, 3}, new int[]{3, 4, 5, 6}, new int[]{50, 10, 40, 70}));
        assertEquals(150, MaximumProfitInJobScheduling.jobScheduling(new int[]{1, 2, 3, 4, 6}, new int[]{3, 5, 10, 6, 9}, new int[]{20, 20, 100, 70, 60}));
        assertEquals(6, MaximumProfitInJobScheduling.jobScheduling(new int[]{1, 1, 1}, new int[]{2, 3, 4}, new int[]{5, 6, 4}));
        Random r = new Random(1235);
        for (int t = 0; t < 1000; t++) {
            int n = 1 + r.nextInt(10);
            int[] s = new int[n], e = new int[n], p = new int[n];
            for (int i = 0; i < n; i++) {
                s[i] = 1 + r.nextInt(15);
                e[i] = s[i] + 1 + r.nextInt(6);
                p[i] = 1 + r.nextInt(30);
            }
            int expected = brute(s, e, p);
            assertEquals(expected, MaximumProfitInJobScheduling.jobScheduling(s, e, p));
            assertEquals(expected, MaximumProfitInJobScheduling.jobSchedulingMemo(s, e, p));
            assertEquals(expected, MaximumProfitInJobScheduling.treeMap(s, e, p));
        }
    }
}
