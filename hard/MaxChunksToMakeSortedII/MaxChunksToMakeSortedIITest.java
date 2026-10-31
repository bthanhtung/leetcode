package MaxChunksToMakeSortedII;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaxChunksToMakeSortedIITest {

    @Test
    void examplesAndRandom() {
        assertEquals(1, MaxChunksToMakeSortedII.maxChunksToSorted(new int[]{5, 4, 3, 2, 1}));
        assertEquals(4, MaxChunksToMakeSortedII.maxChunksToSorted(new int[]{2, 1, 3, 4, 4}));
        Random r = new Random(768);
        for (int t = 0; t < 3000; t++) {
            int[] arr = r.ints(1 + r.nextInt(12), 0, 6).toArray();
            int expected = MaxChunksToMakeSortedII.maxChunksToSorted(arr);
            assertEquals(expected, MaxChunksToMakeSortedII.maxChunksToSortedStack(arr));
            assertEquals(expected, MaxChunksToMakeSortedII.maxChunksToSortedSums(arr));
        }
    }
}
