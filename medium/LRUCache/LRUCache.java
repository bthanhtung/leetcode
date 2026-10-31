package LRUCache;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 * 146. LRU Cache — https://leetcode.com/problems/lru-cache/
 * LRUCache(capacity): get(key) returns the value or -1; put(key, value) inserts/updates and, when over capacity,
 * evicts the least recently used key. Both in O(1).
 * Input: cap 2; put(1,1), put(2,2), get(1) -> 1, put(3,3) evicts 2, get(2) -> -1, put(4,4) evicts 1,
 *        get(1) -> -1, get(3) -> 3, get(4) -> 4
 * Explanation: README.md
 */
public class LRUCache {
    public static void main(String[] args) {
        Cache c = new Cache(2);
        c.put(1, 1);
        c.put(2, 2);
        System.out.println(c.get(1));    // 1
        c.put(3, 3);                     // evicts 2
        System.out.println(c.get(2));    // -1
    }

    /*
     * Approach 1 - HashMap key -> node, plus a doubly linked list ordered by recency (most recent right after head).
     * get/put move the node to the front; eviction removes the node before tail. All O(1).
     */
    public static class Cache {
        private static final class Node {
            int key, value;
            Node prev, next;
        }

        private final int capacity;
        private final Map<Integer, Node> map = new HashMap<>();
        private final Node head = new Node(), tail = new Node();      // sentinels

        public Cache(int capacity) {
            this.capacity = capacity;
            head.next = tail;
            tail.prev = head;
        }

        public int get(int key) {
            Node n = map.get(key);
            if (n == null) return -1;
            unlink(n);
            pushFront(n);
            return n.value;
        }

        public void put(int key, int value) {
            Node n = map.get(key);
            if (n != null) {
                n.value = value;
                unlink(n);
                pushFront(n);
                return;
            }
            if (map.size() == capacity) {
                Node lru = tail.prev;
                unlink(lru);
                map.remove(lru.key);
            }
            n = new Node();
            n.key = key;
            n.value = value;
            map.put(key, n);
            pushFront(n);
        }

        private void unlink(Node n) {
            n.prev.next = n.next;
            n.next.prev = n.prev;
        }

        private void pushFront(Node n) {
            n.next = head.next;
            n.prev = head;
            head.next.prev = n;
            head.next = n;
        }
    }

    /* Approach 2 - LinkedHashMap with accessOrder = true already keeps recency order; evict in removeEldestEntry. */
    public static class LinkedCache extends LinkedHashMap<Integer, Integer> {
        private final int capacity;

        public LinkedCache(int capacity) {
            super(16, 0.75f, true);
            this.capacity = capacity;
        }

        public int get(int key) {
            return getOrDefault(key, -1);
        }

        public void put(int key, int value) {
            super.put(key, value);
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
            return size() > capacity;
        }
    }
}
