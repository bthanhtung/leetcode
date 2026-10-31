package AllOOneDataStructure;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AllOOneDataStructureTest {

    private static void check(Map<String, Integer> ref, String max, String min) {
        if (ref.isEmpty()) {
            assertEquals("", max);
            assertEquals("", min);
            return;
        }
        int hi = ref.values().stream().max(Integer::compare).get(), lo = ref.values().stream().min(Integer::compare).get();
        assertTrue(ref.containsKey(max) && ref.get(max) == hi);
        assertTrue(ref.containsKey(min) && ref.get(min) == lo);
    }

    @Test
    void exampleAndRandom() {
        AllOOneDataStructure.AllOneBuckets x = new AllOOneDataStructure.AllOneBuckets();
        x.inc("hello");
        x.inc("hello");
        assertEquals("hello", x.getMaxKey());
        x.inc("leet");
        assertEquals("leet", x.getMinKey());
        Random r = new Random(432);
        for (int t = 0; t < 100; t++) {
            AllOOneDataStructure.AllOneBuckets a = new AllOOneDataStructure.AllOneBuckets();
            AllOOneDataStructure.AllOneTree b = new AllOOneDataStructure.AllOneTree();
            Map<String, Integer> ref = new HashMap<>();
            for (int op = 0; op < 300; op++) {
                String key = "k" + r.nextInt(5);
                if (r.nextInt(3) > 0 || !ref.containsKey(key)) {
                    a.inc(key);
                    b.inc(key);
                    ref.merge(key, 1, Integer::sum);
                } else {
                    a.dec(key);
                    b.dec(key);
                    if (ref.merge(key, -1, Integer::sum) == 0) ref.remove(key);
                }
                check(ref, a.getMaxKey(), a.getMinKey());
                check(ref, b.getMaxKey(), b.getMinKey());
            }
        }
    }
}
