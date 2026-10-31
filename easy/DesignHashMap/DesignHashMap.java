package DesignHashMap;

import java.util.Arrays;

/*
 * 706. Design HashMap — https://leetcode.com/problems/design-hashmap/
 * Design a HashMap without built-in hash table libraries: put(key, value), get(key) (-1 if absent), remove(key).
 * 0 <= key, value <= 10^6.
 * Example: put(1,1), put(2,2), get(1) -> 1, get(3) -> -1, put(2,1), get(2) -> 1, remove(2), get(2) -> -1
 * Explanation: README.md
 */
public class DesignHashMap {
    public static void main(String[] args) {
        MyHashMap map = new MyHashMap();
        map.put(1, 1);
        map.put(2, 2);
        System.out.println(map.get(1));    // 1
        System.out.println(map.get(3));    // -1
        map.put(2, 1);
        System.out.println(map.get(2));    // 1
        map.remove(2);
        System.out.println(map.get(2));    // -1
    }

    /* Approach 1 - one slot per possible key, -1 meaning absent. O(1), ~4 MB. */
    public static class MyHashMapArray {
        private final int[] values = new int[1_000_001];

        public MyHashMapArray() {
            Arrays.fill(values, -1);
        }

        public void put(int key, int value) {
            values[key] = value;
        }

        public int get(int key) {
            return values[key];
        }

        public void remove(int key) {
            values[key] = -1;
        }
    }

    /* Approach 2 - array of buckets, each a singly linked list of entries; doubles when load factor > 0.75. Expected O(1). */
    public static class MyHashMap {
        private static final class Entry {
            final int key;
            int value;
            Entry next;

            Entry(int key, int value, Entry next) {
                this.key = key;
                this.value = value;
                this.next = next;
            }
        }

        private Entry[] table = new Entry[16];
        private int size;

        private static int index(int key, int capacity) {
            int h = key * 0x9E3779B1;
            return (h ^ (h >>> 16)) & (capacity - 1);
        }

        public void put(int key, int value) {
            int i = index(key, table.length);
            for (Entry e = table[i]; e != null; e = e.next) {
                if (e.key == key) {
                    e.value = value;            // update in place
                    return;
                }
            }
            table[i] = new Entry(key, value, table[i]);   // insert at the head of the chain
            if (++size > table.length * 3 / 4) resize();
        }

        public int get(int key) {
            for (Entry e = table[index(key, table.length)]; e != null; e = e.next) {
                if (e.key == key) return e.value;
            }
            return -1;
        }

        public void remove(int key) {
            int i = index(key, table.length);
            Entry dummy = new Entry(0, 0, table[i]);
            for (Entry prev = dummy; prev.next != null; prev = prev.next) {
                if (prev.next.key == key) {
                    prev.next = prev.next.next;
                    size--;
                    break;
                }
            }
            table[i] = dummy.next;
        }

        private void resize() {
            Entry[] bigger = new Entry[table.length * 2];
            for (Entry head : table) {
                for (Entry e = head; e != null; ) {
                    Entry next = e.next;
                    int i = index(e.key, bigger.length);
                    e.next = bigger[i];
                    bigger[i] = e;
                    e = next;
                }
            }
            table = bigger;
        }
    }
}
