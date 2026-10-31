package LargestRectangleInHistogram;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LargestRectangleInHistogramTest {

    @Test
    void examplesAndRandom() {
        assertEquals(10, LargestRectangleInHistogram.largestRectangleArea(new int[]{2, 1, 5, 6, 2, 3}));
        assertEquals(4, LargestRectangleInHistogram.largestRectangleArea(new int[]{2, 4}));
        assertEquals(9, LargestRectangleInHistogram.largestRectangleArea(new int[]{3, 3, 3}));
        Random r = new Random(84);
        for (int t = 0; t < 3000; t++) {
            int[] h = r.ints(1 + r.nextInt(15), 0, 10).toArray();
            int expected = LargestRectangleInHistogram.largestRectangleAreaBrute(h);
            assertEquals(expected, LargestRectangleInHistogram.largestRectangleArea(h));
            assertEquals(expected, LargestRectangleInHistogram.largestRectangleAreaBounds(h));
        }
    }
}
