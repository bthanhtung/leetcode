package PascalsTriangleII;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PascalsTriangleIITest {

    @Test
    void allRowsUpTo33() {
        assertEquals(List.of(1, 3, 3, 1), PascalsTriangleII.getRow(3));
        assertEquals(List.of(1), PascalsTriangleII.getRow(0));
        for (int k = 0; k <= 33; k++) {
            List<Integer> expected = PascalsTriangleII.getRowAllRows(k);
            assertEquals(expected, PascalsTriangleII.getRow(k));
            assertEquals(expected, PascalsTriangleII.getRowBinomial(k));
        }
    }
}
