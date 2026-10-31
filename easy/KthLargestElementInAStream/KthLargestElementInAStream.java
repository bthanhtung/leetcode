package KthLargestElementInAStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/*
 * 703. Kth Largest Element in a Stream — https://leetcode.com/problems/kth-largest-element-in-a-stream/
 * Design KthLargest(k, nums) whose add(val) adds a score to the stream and returns the k-th largest score so far.
 * Example: k = 3, nums = [4,5,8,2]; add(3) -> 4, add(5) -> 5, add(10) -> 5, add(9) -> 8, add(4) -> 8
 * Explanation: README.md
 */
public class KthLargestElementInAStream {
    public static void main(String[] args) {
        KthLargest kth = new KthLargest(3, new int[]{4, 5, 8, 2});
        for (int v : new int[]{3, 5, 10, 9, 4}) System.out.print(kth.add(v) + " ");   // 4 5 5 8 8
    }

    /*
     * Approach 1 - min-heap holding only the k largest values; its top is the k-th largest.
     * add O(log k), memory O(k) no matter how long the stream is.
     */
    public static class KthLargest {
        private final int k;
        private final PriorityQueue<Integer> heap = new PriorityQueue<>();

        public KthLargest(int k, int[] nums) {
            this.k = k;
            for (int x : nums) add(x);
        }

        public int add(int val) {
            heap.add(val);
            if (heap.size() > k) heap.poll();        // drop the smallest: it can never be the k-th largest again
            return heap.peek();
        }
    }

    /* Approach 2 - keep every value in a sorted list (binary-search insertion). add O(n) due to shifting. */
    public static class KthLargestSorted {
        private final int k;
        private final List<Integer> sorted = new ArrayList<>();

        public KthLargestSorted(int k, int[] nums) {
            this.k = k;
            for (int x : nums) insert(x);
        }

        private void insert(int x) {
            int i = Collections.binarySearch(sorted, x);
            sorted.add(i >= 0 ? i : -i - 1, x);
        }

        public int add(int val) {
            insert(val);
            return sorted.get(sorted.size() - k);
        }
    }
}
