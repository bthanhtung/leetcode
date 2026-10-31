package KClosestPointsToOrigin;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

/*
 * 973. K Closest Points to Origin — https://leetcode.com/problems/k-closest-points-to-origin/
 * Return the k points closest to the origin (Euclidean distance), in any order.
 * Input: [[1,3],[-2,2]], k = 1 -> [[-2,2]] · [[3,3],[5,-1],[-2,4]], k = 2 -> [[3,3],[-2,4]]
 * Explanation: README.md
 */
public class KClosestPointsToOrigin {
    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(kClosest(new int[][]{{1, 3}, {-2, 2}}, 1)));                    // [[-2, 2]]
        System.out.println(Arrays.deepToString(kClosestQuickselect(new int[][]{{3, 3}, {5, -1}, {-2, 4}}, 2)));
    }

    private static int dist(int[] p) {
        return p[0] * p[0] + p[1] * p[1];        // squared distance: same order, no sqrt
    }

    /* Approach 1 - sort by distance, take the first k. Time O(n log n). */
    public static int[][] kClosestSort(int[][] points, int k) {
        int[][] copy = points.clone();
        Arrays.sort(copy, (a, b) -> Integer.compare(dist(a), dist(b)));
        return Arrays.copyOf(copy, k);
    }

    /* Approach 2 - max-heap holding the k closest so far; a closer point evicts the farthest. Time O(n log k), space O(k). */
    public static int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(dist(b), dist(a)));
        for (int[] p : points) {
            heap.add(p);
            if (heap.size() > k) heap.poll();
        }
        return heap.toArray(new int[0][]);
    }

    /* Approach 3 - quickselect: partition around a random pivot until the k closest are in front. Average O(n). */
    public static int[][] kClosestQuickselect(int[][] points, int k) {
        int[][] a = points.clone();
        Random random = new Random(0);
        int lo = 0, hi = a.length - 1;
        while (lo < hi) {
            int p = partition(a, lo, hi, lo + random.nextInt(hi - lo + 1));
            if (p == k - 1) break;
            if (p < k - 1) lo = p + 1;
            else hi = p - 1;
        }
        return Arrays.copyOf(a, k);
    }

    private static int partition(int[][] a, int lo, int hi, int pivotIndex) {
        int pivot = dist(a[pivotIndex]);
        swap(a, pivotIndex, hi);
        int store = lo;
        for (int i = lo; i < hi; i++) {
            if (dist(a[i]) < pivot) swap(a, i, store++);
        }
        swap(a, store, hi);
        return store;
    }

    private static void swap(int[][] a, int i, int j) {
        int[] t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
