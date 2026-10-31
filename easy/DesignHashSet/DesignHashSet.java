package DesignHashSet;

import java.util.LinkedList;

/*
 * 705. Design HashSet — https://leetcode.com/problems/design-hashset/
 * Design a HashSet without built-in hash table libraries: add(key), remove(key), contains(key). 0 <= key <= 10^6.
 * Example: add(1), add(2), contains(1) -> true, contains(3) -> false, add(2), contains(2) -> true, remove(2), contains(2) -> false
 * Explanation: README.md
 */
public class DesignHashSet {
    public static void main(String[] args) {
        MyHashSet set = new MyHashSet();
        set.add(1);
        set.add(2);
        System.out.println(set.contains(1));   // true
        set.remove(2);
        System.out.println(set.contains(2));   // false
    }

    /* Approach 1 - keys are small (<= 10^6): one boolean per possible key. O(1) per operation, ~1 MB memory. */
    public static class MyHashSetArray {
        private final boolean[] present = new boolean[1_000_001];

        public void add(int key) {
            present[key] = true;
        }

        public void remove(int key) {
            present[key] = false;
        }

        public boolean contains(int key) {
            return present[key];
        }
    }

    /*
     * Approach 2 - a real hash table: an array of buckets, each a linked list (separate chaining). Grows (rehashes) when
     * the load factor passes 0.75. Expected O(1) per operation, memory proportional to the number of keys.
     */
    public static class MyHashSet {
        private LinkedList<Integer>[] buckets = newBuckets(16);
        private int size;

        @SuppressWarnings("unchecked")
        private static LinkedList<Integer>[] newBuckets(int n) {
            LinkedList<Integer>[] b = new LinkedList[n];
            for (int i = 0; i < n; i++) b[i] = new LinkedList<>();
            return b;
        }

        private int index(int key, int capacity) {
            int h = key * 0x9E3779B1;          // multiplicative hash spreads nearby keys
            return (h ^ (h >>> 16)) & (capacity - 1);
        }

        public void add(int key) {
            if (contains(key)) return;
            buckets[index(key, buckets.length)].add(key);
            if (++size > buckets.length * 3 / 4) resize();
        }

        public void remove(int key) {
            if (buckets[index(key, buckets.length)].remove((Integer) key)) size--;
        }

        public boolean contains(int key) {
            return buckets[index(key, buckets.length)].contains(key);
        }

        private void resize() {
            LinkedList<Integer>[] bigger = newBuckets(buckets.length * 2);
            for (LinkedList<Integer> bucket : buckets) {
                for (int key : bucket) bigger[index(key, bigger.length)].add(key);
            }
            buckets = bigger;
        }
    }
}
