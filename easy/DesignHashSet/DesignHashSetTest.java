package DesignHashSet;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DesignHashSetTest {

    @Test
    void randomOperationsMatchHashSet() {
        Random r = new Random(705);
        DesignHashSet.MyHashSet a = new DesignHashSet.MyHashSet();
        DesignHashSet.MyHashSetArray b = new DesignHashSet.MyHashSetArray();
        Set<Integer> ref = new HashSet<>();
        for (int t = 0; t < 50_000; t++) {
            int key = r.nextBoolean() ? r.nextInt(200) : r.nextInt(1_000_001);
            switch (r.nextInt(3)) {
                case 0 -> { a.add(key); b.add(key); ref.add(key); }
                case 1 -> { a.remove(key); b.remove(key); ref.remove(key); }
                default -> {
                    assertEquals(ref.contains(key), a.contains(key));
                    assertEquals(ref.contains(key), b.contains(key));
                }
            }
        }
    }
}
