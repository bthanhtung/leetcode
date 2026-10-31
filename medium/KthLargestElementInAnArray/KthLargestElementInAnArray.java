package KthLargestElementInAnArray;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

/*
 * 215. Kth Largest Element in an Array — https://leetcode.com/problems/kth-largest-element-in-an-array/
 * Return the k-th largest element of nums (in sorted order, not the k-th distinct). Can you do it without sorting?
 * Input: [3,2,1,5,6,4], k = 2 -> 5 · [3,2,3,1,2,4,5,5,6], k = 4 -> 4
 * Explanation: README.md
 */
public class KthLargestElementInAnArray {
    public static void main(String[] args) {
        System.out.println(findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2));                    // 5
        System.out.println(findKthLargestQuickselect(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4)); // 4
    }

    /* Approach 1 - sort, index n - k. Time O(n log n). */
    public static int findKthLargestSort(int[] nums, int k) {
        int[] a = nums.clone();
        Arrays.sort(a);
        return a[a.length - k];
    }

    /* Approach 2 - min-heap of the k largest; its top is the answer. Time O(n log k), space O(k). */
    public static int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int x : nums) {
            heap.add(x);
            if (heap.size() > k) heap.poll();
        }
        return heap.peek();
    }

    /*
     * Approach 3 - quickselect with three-way partition (handles many duplicates): split into > pivot, == pivot,
     * < pivot and continue only in the part that contains the k-th largest. Average O(n), space O(1).
     */
    public static int findKthLargestQuickselect(int[] nums, int k) {
        int[] a = nums.clone();
        Random random = new Random(0);
        int lo = 0, hi = a.length - 1, target = k - 1;        // index of the answer if a were sorted descending
        while (true) {
            int pivot = a[lo + random.nextInt(hi - lo + 1)];
            int lt = lo, i = lo, gt = hi;                     // [lo,lt) > pivot, [lt,i) == pivot, (gt,hi] < pivot
            while (i <= gt) {
                if (a[i] > pivot) swap(a, lt++, i++);
                else if (a[i] < pivot) swap(a, i, gt--);
                else i++;
            }
            if (target < lt) hi = lt - 1;
            else if (target > gt) lo = gt + 1;
            else return pivot;
        }
    }

    /* Approach 4 - values are in [-10^4, 10^4]: count them, walk from the largest value down. Time O(n + range). */
    public static int findKthLargestCounting(int[] nums, int k) {
        int offset = 10_000;
        int[] count = new int[2 * offset + 1];
        for (int x : nums) count[x + offset]++;
        for (int v = count.length - 1; v >= 0; v--) {
            k -= count[v];
            if (k <= 0) return v - offset;
        }
        throw new IllegalArgumentException("k is larger than the array");
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
