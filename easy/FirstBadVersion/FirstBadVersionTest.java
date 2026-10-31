package FirstBadVersion;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FirstBadVersionTest {

    @Test
    void randomBoundaries() {
        Random r = new Random(278);
        for (int t = 0; t < 5000; t++) {
            int n = 1 + r.nextInt(t < 2500 ? 50 : Integer.MAX_VALUE);
            int bad = 1 + r.nextInt(n);
            int[] calls = {0};
            assertEquals(bad, FirstBadVersion.firstBadVersion(n, v -> { calls[0]++; return v >= bad; }));
            assertTrue(calls[0] <= 32);
        }
    }
}
