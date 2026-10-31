package MaximalRectangle;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaximalRectangleTest {

    @Test
    void examplesAndRandom() {
        char[][] m = {"10100".toCharArray(), "10111".toCharArray(), "11111".toCharArray(), "10010".toCharArray()};
        assertEquals(6, MaximalRectangle.maximalRectangle(m));
        assertEquals(0, MaximalRectangle.maximalRectangle(new char[][]{{'0'}}));
        assertEquals(1, MaximalRectangle.maximalRectangle(new char[][]{{'1'}}));
        Random r = new Random(85);
        for (int t = 0; t < 500; t++) {
            int rows = 1 + r.nextInt(6), cols = 1 + r.nextInt(6);
            char[][] g = new char[rows][cols];
            for (char[] row : g) for (int j = 0; j < cols; j++) row[j] = r.nextInt(100) < 65 ? '1' : '0';
            int expected = MaximalRectangle.bruteForce(g);
            assertEquals(expected, MaximalRectangle.maximalRectangle(g));
            assertEquals(expected, MaximalRectangle.maximalRectangleDp(g));
        }
    }
}
