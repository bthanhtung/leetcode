package NQueensII;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NQueensIITest {

    @Test
    void knownCounts() {
        int[] expected = {0, 1, 0, 0, 2, 10, 4, 40, 92, 352, 724};
        for (int n = 1; n < expected.length; n++) {
            assertEquals(expected[n], NQueensII.totalNQueens(n), "bitmask n=" + n);
            assertEquals(expected[n], NQueensII.totalNQueensArrays(n), "arrays n=" + n);
        }
    }
}
