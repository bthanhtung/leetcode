package HammingDistance;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HammingDistanceTest {

    @Test
    void examplesAndRandom() {
        assertEquals(2, HammingDistance.hammingDistance(1, 4));
        assertEquals(1, HammingDistance.hammingDistance(3, 1));
        Random r = new Random(461);
        for (int t = 0; t < 5000; t++) {
            int x = r.nextInt(), y = r.nextInt();
            int expected = HammingDistance.hammingDistanceLoop(x, y);
            assertEquals(expected, HammingDistance.hammingDistance(x, y));
            assertEquals(expected, HammingDistance.hammingDistanceKernighan(x, y));
        }
    }
}
