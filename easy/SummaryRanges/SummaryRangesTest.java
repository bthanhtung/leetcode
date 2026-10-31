package SummaryRanges;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SummaryRangesTest {

    @Test
    void examples() {
        assertEquals(List.of("0->2", "4->5", "7"), SummaryRanges.summaryRanges(new int[]{0, 1, 2, 4, 5, 7}));
        assertEquals(List.of("0", "2->4", "6", "8->9"), SummaryRanges.summaryRanges(new int[]{0, 2, 3, 4, 6, 8, 9}));
        assertEquals(List.of(), SummaryRanges.summaryRanges(new int[]{}));
        assertEquals(List.of("2147483646->2147483647"), SummaryRanges.summaryRanges(new int[]{2147483646, 2147483647}));
        assertEquals(List.of("-2147483648", "2147483647"), SummaryRanges.summaryRanges(new int[]{-2147483648, 2147483647}));
    }
}
