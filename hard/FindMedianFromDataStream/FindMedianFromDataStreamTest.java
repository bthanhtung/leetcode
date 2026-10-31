package FindMedianFromDataStream;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FindMedianFromDataStreamTest {

    @Test
    void exampleAndRandomStream() {
        FindMedianFromDataStream.MedianFinder m = new FindMedianFromDataStream.MedianFinder();
        m.addNum(1);
        m.addNum(2);
        assertEquals(1.5, m.findMedian(), 1e-9);
        m.addNum(3);
        assertEquals(2.0, m.findMedian(), 1e-9);
        Random r = new Random(295);
        FindMedianFromDataStream.MedianFinder a = new FindMedianFromDataStream.MedianFinder();
        FindMedianFromDataStream.MedianFinderSorted b = new FindMedianFromDataStream.MedianFinderSorted();
        for (int t = 0; t < 5000; t++) {
            int v = r.nextInt(2_000_001) - 1_000_000;
            a.addNum(v);
            b.addNum(v);
            assertEquals(b.findMedian(), a.findMedian(), 1e-9);
        }
    }
}
