package PerfectSquares;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PerfectSquaresTest {

    @Test
    void agreeUpTo3000() {
        assertEquals(3, PerfectSquares.numSquares(12));
        assertEquals(2, PerfectSquares.numSquares(13));
        for (int n = 1; n <= 3000; n++) {
            int expected = PerfectSquares.numSquares(n);
            assertEquals(expected, PerfectSquares.numSquaresBfs(n), "bfs " + n);
            assertEquals(expected, PerfectSquares.numSquaresMath(n), "math " + n);
        }
    }
}
