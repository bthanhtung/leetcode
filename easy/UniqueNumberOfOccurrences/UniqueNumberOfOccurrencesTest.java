package UniqueNumberOfOccurrences;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UniqueNumberOfOccurrencesTest {

    @Test
    void examplesAndRandom() {
        assertEquals(true, UniqueNumberOfOccurrences.uniqueOccurrences(new int[]{1, 2, 2, 1, 1, 3}));
        assertEquals(false, UniqueNumberOfOccurrences.uniqueOccurrences(new int[]{1, 2}));
        assertEquals(true, UniqueNumberOfOccurrences.uniqueOccurrencesArray(new int[]{-3, 0, 1, -3, 1, 1, 1, -3, 10, 0}));
        Random r = new Random(1207);
        for (int t = 0; t < 1000; t++) {
            int[] arr = r.ints(1 + r.nextInt(12), -3, 4).toArray();
            assertEquals(UniqueNumberOfOccurrences.uniqueOccurrences(arr), UniqueNumberOfOccurrences.uniqueOccurrencesArray(arr));
        }
    }
}
