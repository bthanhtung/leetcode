package DesignHashMap;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DesignHashMapTest {

    @Test
    void randomOperationsMatchHashMap() {
        Random r = new Random(706);
        DesignHashMap.MyHashMap a = new DesignHashMap.MyHashMap();
        DesignHashMap.MyHashMapArray b = new DesignHashMap.MyHashMapArray();
        Map<Integer, Integer> ref = new HashMap<>();
        for (int t = 0; t < 50_000; t++) {
            int key = r.nextBoolean() ? r.nextInt(300) : r.nextInt(1_000_001);
            switch (r.nextInt(3)) {
                case 0 -> {
                    int v = r.nextInt(1_000_001);
                    a.put(key, v);
                    b.put(key, v);
                    ref.put(key, v);
                }
                case 1 -> { a.remove(key); b.remove(key); ref.remove(key); }
                default -> {
                    assertEquals(ref.getOrDefault(key, -1), a.get(key));
                    assertEquals(ref.getOrDefault(key, -1), b.get(key));
                }
            }
        }
    }
}
