package ShortestSubarrayWithSumAtLeastK;

import java.util.ArrayDeque;
import java.util.Deque;

/*
 * 862. Shortest Subarray with Sum at Least K — https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k/
 * Length of the shortest non-empty contiguous subarray with sum >= k (values can be negative), or -1.
 * Input: [1], 1 -> 1 · [1,2], 4 -> -1 · [2,-1,2], 3 -> 3
 * Explanation: README.md
 */
public class ShortestSubarrayWithSumAtLeastK {
    public static void main(String[] args) {
        System.out.println(shortestSubarray(new int[]{2, -1, 2}, 3));     // 3
    }

    /*
     * With prefix sums P, we want the closest i < j with P[j] - P[i] >= k. Keep a deque of indices with increasing P:
     *  - front: while P[j] - P[front] >= k, record and pop (a later j can only be longer);
     *  - back:  pop while P[back] >= P[j] (j is a later and smaller start, strictly better).
     * Every index enters and leaves once: O(n).
     */
    public static int shortestSubarray(int[] nums, int k) {
        int n = nums.length;
        long[] p = new long[n + 1];
        for (int i = 0; i < n; i++) p[i + 1] = p[i] + nums[i];
        Deque<Integer> dq = new ArrayDeque<>();
        int best = Integer.MAX_VALUE;
        for (int j = 0; j <= n; j++) {
            while (!dq.isEmpty() && p[j] - p[dq.peekFirst()] >= k) best = Math.min(best, j - dq.pollFirst());
            while (!dq.isEmpty() && p[dq.peekLast()] >= p[j]) dq.pollLast();
            dq.addLast(j);
        }
        return best == Integer.MAX_VALUE ? -1 : best;
    }
}
