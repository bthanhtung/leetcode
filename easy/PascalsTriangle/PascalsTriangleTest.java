package PascalsTriangle;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PascalsTriangleTest {

    @Test
    void examples() {
        assertEquals(List.of(List.of(1), List.of(1, 1), List.of(1, 2, 1), List.of(1, 3, 3, 1), List.of(1, 4, 6, 4, 1)),
                PascalsTriangle.generate(5));
        assertEquals(List.of(List.of(1)), PascalsTriangle.generate(1));
    }

    @Test
    void approachesAgreeUpTo30Rows() {
        assertEquals(PascalsTriangle.generate(30), PascalsTriangle.generateBinomial(30));
    }
}
