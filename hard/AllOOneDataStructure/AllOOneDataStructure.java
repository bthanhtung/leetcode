package AllOOneDataStructure;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/*
 * 432. All O`one Data Structure — https://leetcode.com/problems/all-oone-data-structure/
 * AllOne: inc(key) / dec(key) change a key's count by one (a key at 0 disappears); getMaxKey / getMinKey return any key
 * with the maximal / minimal count, or "". Every operation must run in O(1) average time.
 * Explanation: README.md
 */
public class AllOOneDataStructure {
    public static void main(String[] args) {
        AllOneBuckets a = new AllOneBuckets();
        a.inc("hello");
        a.inc("hello");
        System.out.println(a.getMaxKey() + " " + a.getMinKey());   // hello hello
        a.inc("leet");
        System.out.println(a.getMaxKey() + " " + a.getMinKey());   // hello leet
    }

    /*
     * Approach 1 - a doubly linked list of buckets sorted by count, each holding the keys with that count, plus a map
     * key -> bucket. inc/dec move a key to the neighbouring bucket (creating it if needed). All O(1).
     */
    public static class AllOneBuckets {
        private static final class Bucket {
            final int count;
            final Set<String> keys = new HashSet<>();
            Bucket prev, next;

            Bucket(int count) {
                this.count = count;
            }
        }

        private final Bucket head = new Bucket(0), tail = new Bucket(0);      // sentinels
        private final Map<String, Bucket> where = new HashMap<>();

        public AllOneBuckets() {
            head.next = tail;
            tail.prev = head;
        }

        public void inc(String key) {
            Bucket cur = where.getOrDefault(key, head);
            Bucket next = cur.next;
            if (next == tail || next.count != cur.count + 1) next = insertAfter(cur, cur.count + 1);
            next.keys.add(key);
            where.put(key, next);
            if (cur != head) removeKey(cur, key);
        }

        public void dec(String key) {
            Bucket cur = where.get(key);
            if (cur == null) return;
            if (cur.count == 1) {
                where.remove(key);
            } else {
                Bucket prev = cur.prev;
                if (prev == head || prev.count != cur.count - 1) prev = insertAfter(cur.prev, cur.count - 1);
                prev.keys.add(key);
                where.put(key, prev);
            }
            removeKey(cur, key);
        }

        public String getMaxKey() {
            return tail.prev == head ? "" : tail.prev.keys.iterator().next();
        }

        public String getMinKey() {
            return head.next == tail ? "" : head.next.keys.iterator().next();
        }

        private Bucket insertAfter(Bucket b, int count) {
            Bucket nb = new Bucket(count);
            nb.prev = b;
            nb.next = b.next;
            b.next.prev = nb;
            b.next = nb;
            return nb;
        }

        private void removeKey(Bucket b, String key) {
            b.keys.remove(key);
            if (b.keys.isEmpty()) {
                b.prev.next = b.next;
                b.next.prev = b.prev;
            }
        }
    }

    /* Approach 2 - counts in a map plus a TreeMap count -> keys. O(log n) per operation, much shorter. */
    public static class AllOneTree {
        private final Map<String, Integer> count = new HashMap<>();
        private final TreeMap<Integer, Set<String>> byCount = new TreeMap<>();

        public void inc(String key) {
            int c = count.getOrDefault(key, 0);
            move(key, c, c + 1);
        }

        public void dec(String key) {
            Integer c = count.get(key);
            if (c != null) move(key, c, c - 1);
        }

        private void move(String key, int from, int to) {
            if (from > 0) {
                Set<String> s = byCount.get(from);
                s.remove(key);
                if (s.isEmpty()) byCount.remove(from);
            }
            if (to > 0) {
                count.put(key, to);
                byCount.computeIfAbsent(to, k -> new HashSet<>()).add(key);
            } else {
                count.remove(key);
            }
        }

        public String getMaxKey() {
            return byCount.isEmpty() ? "" : byCount.lastEntry().getValue().iterator().next();
        }

        public String getMinKey() {
            return byCount.isEmpty() ? "" : byCount.firstEntry().getValue().iterator().next();
        }
    }
}
