package MaxValueOfEquation;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;

/*
 * 1499. Max Value of Equation — https://leetcode.com/problems/max-value-of-equation/
 * points sorted by x. Return the maximum of yi + yj + |xi - xj| over pairs i < j with |xi - xj| <= k (one exists).
 * Input: [[1,3],[2,0],[5,10],[6,-10]], k = 1 -> 4 · [[0,0],[3,0],[9,2]], k = 3 -> 3
 * Explanation: README.md
 */
public class MaxValueOfEquation {
    public static void main(String[] args) {
        System.out.println(findMaxValueOfEquation(new int[][]{{1, 3}, {2, 0}, {5, 10}, {6, -10}}, 1));     // 4
        System.out.println(findMaxValueOfEquationHeap(new int[][]{{0, 0}, {3, 0}, {9, 2}}, 3));            // 3
    }

    /*
     * Approach 1 - since xj > xi: yi + yj + xj - xi = (yi - xi) + (yj + xj). For each j we want the largest yi - xi
     * among earlier points with xj - xi <= k: a sliding-window maximum with a monotonic deque. Time O(n).
     */
    public static int findMaxValueOfEquation(int[][] points, int k) {
        Deque<int[]> dq = new ArrayDeque<>();                       // {yi - xi, xi}, decreasing in yi - xi
        int best = Integer.MIN_VALUE;
        for (int[] p : points) {
            while (!dq.isEmpty() && p[0] - dq.peekFirst()[1] > k) dq.pollFirst();
            if (!dq.isEmpty()) best = Math.max(best, dq.peekFirst()[0] + p[1] + p[0]);
            int value = p[1] - p[0];
            while (!dq.isEmpty() && dq.peekLast()[0] <= value) dq.pollLast();
            dq.addLast(new int[]{value, p[0]});
        }
        return best;
    }

    /* Approach 2 - max-heap of (yi - xi, xi); pop tops whose x is too far behind (they never come back). O(n log n). */
    public static int findMaxValueOfEquationHeap(int[][] points, int k) {
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(b[0], a[0]));
        int best = Integer.MIN_VALUE;
        for (int[] p : points) {
            while (!heap.isEmpty() && p[0] - heap.peek()[1] > k) heap.poll();
            if (!heap.isEmpty()) best = Math.max(best, heap.peek()[0] + p[1] + p[0]);
            heap.add(new int[]{p[1] - p[0], p[0]});
        }
        return best;
    }
}
