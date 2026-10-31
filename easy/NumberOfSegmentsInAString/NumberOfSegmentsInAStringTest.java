package NumberOfSegmentsInAString;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumberOfSegmentsInAStringTest {

    @Test
    void examplesAndRandom() {
        assertEquals(5, NumberOfSegmentsInAString.countSegments("Hello, my name is John"));
        assertEquals(1, NumberOfSegmentsInAString.countSegments("Hello"));
        assertEquals(0, NumberOfSegmentsInAString.countSegments("   "));
        assertEquals(0, NumberOfSegmentsInAString.countSegments(""));
        Random r = new Random(434);
        for (int t = 0; t < 3000; t++) {
            StringBuilder sb = new StringBuilder();
            for (int k = r.nextInt(12); k > 0; k--) sb.append(r.nextInt(3) == 0 ? ' ' : 'x');
            assertEquals(NumberOfSegmentsInAString.countSegmentsSplit(sb.toString()), NumberOfSegmentsInAString.countSegments(sb.toString()));
        }
    }
}
