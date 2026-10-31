package LRUCache;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LRUCacheTest {

    @Test
    void exampleAndRandom() {
        LRUCache.Cache c = new LRUCache.Cache(2);
        c.put(1, 1);
        c.put(2, 2);
        assertEquals(1, c.get(1));
        c.put(3, 3);
        assertEquals(-1, c.get(2));
        c.put(4, 4);
        assertEquals(-1, c.get(1));
        assertEquals(3, c.get(3));
        assertEquals(4, c.get(4));

        Random r = new Random(146);
        for (int round = 0; round < 50; round++) {
            int cap = 1 + r.nextInt(5);
            LRUCache.Cache a = new LRUCache.Cache(cap);
            LRUCache.LinkedCache b = new LRUCache.LinkedCache(cap);
            for (int op = 0; op < 2000; op++) {
                int key = r.nextInt(8);
                if (r.nextBoolean()) {
                    int v = r.nextInt(100);
                    a.put(key, v);
                    b.put(key, v);
                } else {
                    assertEquals(b.get(key), a.get(key));
                }
            }
        }
    }
}
