package EditDistance;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EditDistanceTest {

    @Test
    void examplesAndRandom() {
        assertEquals(3, EditDistance.minDistance("horse", "ros"));
        assertEquals(5, EditDistance.minDistance("intention", "execution"));
        assertEquals(3, EditDistance.minDistance("", "abc"));
        Random r = new Random(72);
        for (int t = 0; t < 1000; t++) {
            String a = rnd(r), b = rnd(r);
            int expected = EditDistance.minDistanceTable(a, b);
            assertEquals(expected, EditDistance.minDistance(a, b));
            assertEquals(expected, EditDistance.minDistanceMemo(a, b));
        }
    }

    private static String rnd(Random r) {
        StringBuilder sb = new StringBuilder();
        for (int k = r.nextInt(8); k > 0; k--) sb.append((char) ('a' + r.nextInt(3)));
        return sb.toString();
    }
}
