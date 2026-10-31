package KthSmallestNumberInMultiplicationTable;

import java.util.PriorityQueue;

/*
 * 668. Kth Smallest Number in Multiplication Table — https://leetcode.com/problems/kth-smallest-number-in-multiplication-table/
 * The m x n multiplication table has mat[i][j] = i * j (1-indexed). Return its k-th smallest element.
 * Input: m = 3, n = 3, k = 5 -> 3 · m = 2, n = 3, k = 6 -> 6
 * Explanation: README.md
 */
public class KthSmallestNumberInMultiplicationTable {
    public static void main(String[] args) {
        System.out.println(findKthNumber(3, 3, 5));          // 3
        System.out.println(findKthNumberHeap(2, 3, 6));      // 6
    }

    /*
     * Approach 1 - binary search the answer x: how many entries are <= x? Row i holds i, 2i, ..., ni, so it contributes
     * min(x / i, n). The smallest x with count >= k is the answer. Time O(m log(mn)).
     */
    public static int findKthNumber(int m, int n, int k) {
        int lo = 1, hi = m * n;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            int count = 0;
            for (int i = 1; i <= m; i++) count += Math.min(mid / i, n);
            if (count >= k) hi = mid;
            else lo = mid + 1;
        }
        return lo;
    }

    /* Approach 2 - min-heap with one pointer per row; pop k times. Time O(k log m) - too slow when k is huge. */
    public static int findKthNumberHeap(int m, int n, int k) {
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));     // {value, row, col}
        for (int i = 1; i <= m; i++) heap.add(new int[]{i, i, 1});
        int[] top = null;
        for (int c = 0; c < k; c++) {
            top = heap.poll();
            if (top[2] < n) heap.add(new int[]{top[1] * (top[2] + 1), top[1], top[2] + 1});
        }
        return top[0];
    }
}
