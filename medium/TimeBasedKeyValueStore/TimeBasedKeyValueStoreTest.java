package TimeBasedKeyValueStore;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimeBasedKeyValueStoreTest {

    @Test
    void exampleAndRandom() {
        TimeBasedKeyValueStore.TimeMap m = new TimeBasedKeyValueStore.TimeMap();
        m.set("foo", "bar", 1);
        assertEquals("bar", m.get("foo", 1));
        assertEquals("bar", m.get("foo", 3));
        m.set("foo", "bar2", 4);
        assertEquals("bar2", m.get("foo", 4));
        assertEquals("bar2", m.get("foo", 5));
        assertEquals("", m.get("foo", 0));
        assertEquals("", m.get("x", 9));

        Random r = new Random(981);
        TimeBasedKeyValueStore.TimeMap a = new TimeBasedKeyValueStore.TimeMap();
        TimeBasedKeyValueStore.TimeMapTree b = new TimeBasedKeyValueStore.TimeMapTree();
        int now = 1;
        for (int op = 0; op < 20000; op++) {
            String key = "k" + r.nextInt(5);
            if (r.nextBoolean()) {
                now += 1 + r.nextInt(3);
                String v = "v" + r.nextInt(100);
                a.set(key, v, now);
                b.set(key, v, now);
            } else {
                int ts = r.nextInt(now + 5);
                assertEquals(b.get(key, ts), a.get(key, ts));
            }
        }
    }
}
