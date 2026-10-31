package NQueens;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NQueensTest {

    private static boolean valid(List<String> board) {
        int n = board.size();
        int[] q = new int[n];
        for (int r = 0; r < n; r++) {
            q[r] = board.get(r).indexOf('Q');
            if (q[r] < 0 || board.get(r).lastIndexOf('Q') != q[r]) return false;
        }
        for (int a = 0; a < n; a++)
            for (int b = a + 1; b < n; b++)
                if (q[a] == q[b] || Math.abs(q[a] - q[b]) == b - a) return false;
        return true;
    }

    @Test
    void examplesAndCounts() {
        assertEquals(List.of(List.of(".Q..", "...Q", "Q...", "..Q."), List.of("..Q.", "Q...", "...Q", ".Q..")), NQueens.solveNQueens(4));
        assertEquals(List.of(List.of("Q")), NQueens.solveNQueens(1));
        int[] counts = {0, 1, 0, 0, 2, 10, 4, 40, 92};
        for (int n = 1; n < counts.length; n++) {
            List<List<String>> a = NQueens.solveNQueens(n), b = NQueens.solveNQueensBitmask(n);
            assertEquals(counts[n], a.size());
            assertEquals(new HashSet<>(a), new HashSet<>(b));
            for (List<String> board : a) assertTrue(valid(board));
        }
    }
}
