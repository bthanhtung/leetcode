package DataStreamAsDisjointIntervals;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class DataStreamAsDisjointIntervalsTest {

    @Test
    void exampleAndRandom() {
        DataStreamAsDisjointIntervals.SummaryRanges s = new DataStreamAsDisjointIntervals.SummaryRanges();
        s.addNum(1);
        assertArrayEquals(new int[][]{{1, 1}}, s.getIntervals());
        s.addNum(3);
        s.addNum(7);
        s.addNum(2);
        assertArrayEquals(new int[][]{{1, 3}, {7, 7}}, s.getIntervals());
        s.addNum(6);
        assertArrayEquals(new int[][]{{1, 3}, {6, 7}}, s.getIntervals());
        Random r = new Random(352);
        for (int t = 0; t < 100; t++) {
            DataStreamAsDisjointIntervals.SummaryRanges a = new DataStreamAsDisjointIntervals.SummaryRanges();
            DataStreamAsDisjointIntervals.SummaryRangesSet b = new DataStreamAsDisjointIntervals.SummaryRangesSet();
            for (int k = 0; k < 60; k++) {
                int v = r.nextInt(40);
                a.addNum(v);
                b.addNum(v);
                assertArrayEquals(b.getIntervals(), a.getIntervals());
            }
        }
    }
}
