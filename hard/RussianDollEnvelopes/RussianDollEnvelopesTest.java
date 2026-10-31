package RussianDollEnvelopes;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RussianDollEnvelopesTest {

    @Test
    void examplesAndRandom() {
        assertEquals(3, RussianDollEnvelopes.maxEnvelopes(new int[][]{{5, 4}, {6, 4}, {6, 7}, {2, 3}}));
        assertEquals(1, RussianDollEnvelopes.maxEnvelopes(new int[][]{{1, 1}, {1, 1}, {1, 1}}));
        assertEquals(1, RussianDollEnvelopes.maxEnvelopes(new int[][]{{2, 3}, {2, 4}, {2, 5}}));      // same width never nests
        Random r = new Random(354);
        for (int t = 0; t < 2000; t++) {
            int[][] e = new int[1 + r.nextInt(12)][];
            for (int i = 0; i < e.length; i++) e[i] = new int[]{1 + r.nextInt(6), 1 + r.nextInt(6)};
            assertEquals(RussianDollEnvelopes.maxEnvelopesDp(e), RussianDollEnvelopes.maxEnvelopes(e));
        }
    }
}
