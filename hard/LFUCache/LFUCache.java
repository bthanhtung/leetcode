package LFUCache;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeMap;

/*
 * 460. LFU Cache — https://leetcode.com/problems/lfu-cache/
 * Design an LFU cache: get(key) and put(key, value) in O(1) on average. When full, evict the least frequently used key;
 * among keys with the same frequency, evict the least recently used one.
 * Example (capacity 2): put(1,1), put(2,2), get(1) -> 1, put(3,3) evicts 2, get(2) -> -1, get(3) -> 3, put(4,4) evicts 1
 * Explanation: README.md
 */
public class LFUCache {
    public static void main(String[] args) {
        LFUCacheBuckets c = new LFUCacheBuckets(2);
        c.put(1, 1);
        c.put(2, 2);
        System.out.println(c.get(1));    // 1
        c.put(3, 3);                     // evicts 2
        System.out.println(c.get(2));    // -1
        System.out.println(c.get(3));    // 3
        c.put(4, 4);                     // 1 and 3 both used twice; 1 is older -> evicts 1
        System.out.println(c.get(1));    // -1
    }

    /*
     * Approach 1 - value and frequency per key, plus frequency -> keys in LRU order (LinkedHashSet), plus the current
     * minimum frequency. A touched key moves to the next bucket; minFreq only ever resets to 1 on insert. O(1).
     */
    public static class LFUCacheBuckets {
        private final int capacity;
        private final Map<Integer, Integer> values = new HashMap<>(), freq = new HashMap<>();
        private final Map<Integer, LinkedHashSet<Integer>> buckets = new HashMap<>();
        private int minFreq;

        public LFUCacheBuckets(int capacity) {
            this.capacity = capacity;
        }

        public int get(int key) {
            if (!values.containsKey(key)) return -1;
            touch(key);
            return values.get(key);
        }

        public void put(int key, int value) {
            if (capacity == 0) return;
            if (values.containsKey(key)) {
                values.put(key, value);
                touch(key);
                return;
            }
            if (values.size() == capacity) {
                LinkedHashSet<Integer> lowest = buckets.get(minFreq);
                int victim = lowest.iterator().next();                 // least recently used among least frequent
                lowest.remove(victim);
                values.remove(victim);
                freq.remove(victim);
            }
            values.put(key, value);
            freq.put(key, 1);
            buckets.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
            minFreq = 1;
        }

        private void touch(int key) {
            int f = freq.get(key);
            LinkedHashSet<Integer> bucket = buckets.get(f);
            bucket.remove(key);
            if (bucket.isEmpty() && f == minFreq) minFreq++;
            freq.put(key, f + 1);
            buckets.computeIfAbsent(f + 1, k -> new LinkedHashSet<>()).add(key);
        }
    }

    /* Approach 2 - TreeMap frequency -> LRU-ordered keys; the first entry is the eviction bucket. O(log F) per op. */
    public static class LFUCacheTree {
        private final int capacity;
        private final Map<Integer, int[]> entries = new HashMap<>();             // key -> {value, freq}
        private final TreeMap<Integer, LinkedHashSet<Integer>> byFreq = new TreeMap<>();

        public LFUCacheTree(int capacity) {
            this.capacity = capacity;
        }

        public int get(int key) {
            int[] e = entries.get(key);
            if (e == null) return -1;
            bump(key, e);
            return e[0];
        }

        public void put(int key, int value) {
            if (capacity == 0) return;
            int[] e = entries.get(key);
            if (e != null) {
                e[0] = value;
                bump(key, e);
                return;
            }
            if (entries.size() == capacity) {
                Map.Entry<Integer, LinkedHashSet<Integer>> lowest = byFreq.firstEntry();
                int victim = lowest.getValue().iterator().next();
                lowest.getValue().remove(victim);
                if (lowest.getValue().isEmpty()) byFreq.remove(lowest.getKey());
                entries.remove(victim);
            }
            entries.put(key, new int[]{value, 1});
            byFreq.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
        }

        private void bump(int key, int[] e) {
            LinkedHashSet<Integer> set = byFreq.get(e[1]);
            set.remove(key);
            if (set.isEmpty()) byFreq.remove(e[1]);
            e[1]++;
            byFreq.computeIfAbsent(e[1], k -> new LinkedHashSet<>()).add(key);
        }
    }
}
