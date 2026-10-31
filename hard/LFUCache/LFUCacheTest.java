package LFUCache;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LFUCacheTest {

    /** Slow reference: a list of {key, value, freq, lastUsed}. */
    static final class Reference {
        final int capacity;
        final List<int[]> items = new ArrayList<>();
        int clock;

        Reference(int capacity) {
            this.capacity = capacity;
        }

        int get(int key) {
            for (int[] it : items) if (it[0] == key) { it[2]++; it[3] = ++clock; return it[1]; }
            return -1;
        }

        void put(int key, int value) {
            if (capacity == 0) return;
            for (int[] it : items) if (it[0] == key) { it[1] = value; it[2]++; it[3] = ++clock; return; }
            if (items.size() == capacity) {
                int[] victim = items.getFirst();
                for (int[] it : items) if (it[2] < victim[2] || (it[2] == victim[2] && it[3] < victim[3])) victim = it;
                items.remove(victim);
            }
            items.add(new int[]{key, value, 1, ++clock});
        }
    }

    @Test
    void exampleAndRandom() {
        LFUCache.LFUCacheBuckets c = new LFUCache.LFUCacheBuckets(2);
        c.put(1, 1);
        c.put(2, 2);
        assertEquals(1, c.get(1));
        c.put(3, 3);
        assertEquals(-1, c.get(2));
        assertEquals(3, c.get(3));
        c.put(4, 4);
        assertEquals(-1, c.get(1));
        assertEquals(3, c.get(3));
        assertEquals(4, c.get(4));
        Random r = new Random(460);
        for (int t = 0; t < 200; t++) {
            int cap = r.nextInt(4);
            LFUCache.LFUCacheBuckets a = new LFUCache.LFUCacheBuckets(cap);
            LFUCache.LFUCacheTree b = new LFUCache.LFUCacheTree(cap);
            Reference ref = new Reference(cap);
            for (int op = 0; op < 200; op++) {
                int key = r.nextInt(6);
                if (r.nextBoolean()) {
                    int expected = ref.get(key);
                    assertEquals(expected, a.get(key));
                    assertEquals(expected, b.get(key));
                } else {
                    int v = r.nextInt(100);
                    ref.put(key, v);
                    a.put(key, v);
                    b.put(key, v);
                }
            }
        }
    }
}
