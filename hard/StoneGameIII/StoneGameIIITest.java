package StoneGameIII;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StoneGameIIITest {

    /** Plain minimax on actual scores (exponential). */
    private static int[] play(int[] v, int i) {
        if (i >= v.length) return new int[]{0, 0};          // {player to move, other}
        int[] best = null;
        int sum = 0;
        for (int k = 0; k < 3 && i + k < v.length; k++) {
            sum += v[i + k];
            int[] rest = play(v, i + k + 1);
            int[] mine = {sum + rest[1], rest[0]};
            if (best == null || mine[0] - mine[1] > best[0] - best[1]) best = mine;
        }
        return best;
    }

    @Test
    void examplesAndRandom() {
        assertEquals("Bob", StoneGameIII.stoneGameIII(new int[]{1, 2, 3, 7}));
        assertEquals("Alice", StoneGameIII.stoneGameIII(new int[]{1, 2, 3, -9}));
        assertEquals("Tie", StoneGameIII.stoneGameIII(new int[]{1, 2, 3, 6}));
        Random r = new Random(1406);
        for (int t = 0; t < 1000; t++) {
            int[] v = r.ints(1 + r.nextInt(10), -10, 10).toArray();
            int[] s = play(v, 0);
            String expected = s[0] > s[1] ? "Alice" : s[0] < s[1] ? "Bob" : "Tie";
            assertEquals(expected, StoneGameIII.stoneGameIII(v));
            assertEquals(expected, StoneGameIII.stoneGameIIIMemo(v));
        }
    }
}
