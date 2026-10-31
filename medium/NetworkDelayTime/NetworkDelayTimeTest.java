package NetworkDelayTime;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NetworkDelayTimeTest {

    @Test
    void examplesAndRandom() {
        assertEquals(2, NetworkDelayTime.networkDelayTime(new int[][]{{2, 1, 1}, {2, 3, 1}, {3, 4, 1}}, 4, 2));
        assertEquals(1, NetworkDelayTime.networkDelayTime(new int[][]{{1, 2, 1}}, 2, 1));
        assertEquals(-1, NetworkDelayTime.networkDelayTime(new int[][]{{1, 2, 1}}, 2, 2));
        Random r = new Random(743);
        for (int t = 0; t < 500; t++) {
            int n = 1 + r.nextInt(8), m = r.nextInt(20), k = 1 + r.nextInt(n);
            int[][] times = new int[m][];
            for (int i = 0; i < m; i++) times[i] = new int[]{1 + r.nextInt(n), 1 + r.nextInt(n), r.nextInt(10)};
            int expected = NetworkDelayTime.networkDelayTimeBellmanFord(times, n, k);
            assertEquals(expected, NetworkDelayTime.networkDelayTime(times, n, k));
            assertEquals(expected, NetworkDelayTime.networkDelayTimeSpfa(times, n, k));
        }
    }
}
