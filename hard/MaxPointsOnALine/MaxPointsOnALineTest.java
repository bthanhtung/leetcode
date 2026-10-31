package MaxPointsOnALine;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaxPointsOnALineTest {

    private static int brute(int[][] p) {
        int n = p.length, best = Math.min(n, 2);
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++) {
                int c = 0;
                for (int[] q : p)
                    if ((long) (p[j][0] - p[i][0]) * (q[1] - p[i][1]) == (long) (p[j][1] - p[i][1]) * (q[0] - p[i][0])) c++;
                best = Math.max(best, c);
            }
        return best;
    }

    @Test
    void examplesAndBruteForce() {
        assertEquals(3, MaxPointsOnALine.maxPoints(new int[][]{{1, 1}, {2, 2}, {3, 3}}));
        assertEquals(4, MaxPointsOnALine.maxPoints(new int[][]{{1, 1}, {3, 2}, {5, 3}, {4, 1}, {2, 3}, {1, 4}}));
        assertEquals(1, MaxPointsOnALine.maxPoints(new int[][]{{0, 0}}));
        assertEquals(2, MaxPointsOnALine.maxPoints(new int[][]{{0, 0}, {94911151, 94911150}, {94911152, 94911151}}) );
        Random r = new Random(149);
        for (int t = 0; t < 1000; t++) {
            Set<Long> used = new HashSet<>();
            int n = 1 + r.nextInt(12);
            int[][] p = new int[n][];
            for (int i = 0; i < n; i++) {
                int x, y;
                do { x = r.nextInt(9) - 4; y = r.nextInt(9) - 4; } while (!used.add(x * 100L + y));
                p[i] = new int[]{x, y};
            }
            assertEquals(brute(p), MaxPointsOnALine.maxPoints(p));
        }
    }
}
