package InsertDeleteGetRandomO1DuplicatesAllowed;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InsertDeleteGetRandomO1DuplicatesAllowedTest {

    @Test
    void exampleAgainstMultisetAndDistribution() {
        InsertDeleteGetRandomO1DuplicatesAllowed.RandomizedCollection c = new InsertDeleteGetRandomO1DuplicatesAllowed.RandomizedCollection(new Random(1));
        assertEquals(true, c.insert(1));
        assertEquals(false, c.insert(1));
        assertEquals(true, c.insert(2));
        int ones = 0;
        for (int i = 0; i < 30000; i++) if (c.getRandom() == 1) ones++;
        assertTrue(Math.abs(ones / 30000.0 - 2.0 / 3) < 0.02, "P(1) ~ 2/3, got " + ones / 30000.0);
        assertEquals(true, c.remove(1));
        assertEquals(false, c.remove(3));

        Random r = new Random(381);
        InsertDeleteGetRandomO1DuplicatesAllowed.RandomizedCollection x = new InsertDeleteGetRandomO1DuplicatesAllowed.RandomizedCollection(new Random(2));
        Map<Integer, Integer> counts = new HashMap<>();
        int total = 0;
        for (int op = 0; op < 50000; op++) {
            int v = r.nextInt(6);
            int kind = r.nextInt(3);
            if (kind == 0) {
                assertEquals(counts.getOrDefault(v, 0) == 0, x.insert(v));
                counts.merge(v, 1, Integer::sum);
                total++;
            } else if (kind == 1) {
                boolean present = counts.getOrDefault(v, 0) > 0;
                assertEquals(present, x.remove(v));
                if (present) { counts.merge(v, -1, Integer::sum); total--; }
            } else if (total > 0) {
                assertTrue(counts.getOrDefault(x.getRandom(), 0) > 0);
            }
            assertEquals(total, x.size());
        }
    }
}
