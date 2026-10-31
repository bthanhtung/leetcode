package Triangle;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TriangleTest {

    private static int brute(List<List<Integer>> t, int i, int j) {
        if (i == t.size()) return 0;
        return t.get(i).get(j) + Math.min(brute(t, i + 1, j), brute(t, i + 1, j + 1));
    }

    @Test
    void examplesAndRandom() {
        assertEquals(11, Triangle.minimumTotal(List.of(List.of(2), List.of(3, 4), List.of(6, 5, 7), List.of(4, 1, 8, 3))));
        assertEquals(-10, Triangle.minimumTotal(List.of(List.of(-10))));
        Random r = new Random(120);
        for (int t = 0; t < 500; t++) {
            List<List<Integer>> tri = new ArrayList<>();
            for (int i = 0; i < 1 + r.nextInt(9); i++) {
                List<Integer> row = new ArrayList<>();
                for (int j = 0; j <= i; j++) row.add(r.nextInt(21) - 10);
                tri.add(row);
            }
            int expected = brute(tri, 0, 0);
            assertEquals(expected, Triangle.minimumTotal(tri));
            assertEquals(expected, Triangle.minimumTotalTopDown(tri));
        }
    }
}
