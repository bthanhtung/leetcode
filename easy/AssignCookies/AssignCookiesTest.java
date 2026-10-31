package AssignCookies;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AssignCookiesTest {

    private static int brute(int[] g, int[] s, int child, boolean[] used) {
        if (child == g.length) return 0;
        int best = brute(g, s, child + 1, used);              // this child gets nothing
        for (int j = 0; j < s.length; j++) {
            if (!used[j] && s[j] >= g[child]) {
                used[j] = true;
                best = Math.max(best, 1 + brute(g, s, child + 1, used));
                used[j] = false;
            }
        }
        return best;
    }

    @Test
    void examplesAndRandom() {
        assertEquals(1, AssignCookies.findContentChildren(new int[]{1, 2, 3}, new int[]{1, 1}));
        assertEquals(2, AssignCookies.findContentChildren(new int[]{1, 2}, new int[]{1, 2, 3}));
        Random r = new Random(455);
        for (int t = 0; t < 500; t++) {
            int[] g = r.ints(r.nextInt(6), 1, 6).toArray(), s = r.ints(r.nextInt(6), 1, 6).toArray();
            int expected = brute(g, s, 0, new boolean[s.length]);
            assertEquals(expected, AssignCookies.findContentChildren(g, s));
            assertEquals(expected, AssignCookies.findContentChildrenBig(g, s));
        }
    }
}
