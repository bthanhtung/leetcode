package SlidingWindowMaximum;

import java.util.ArrayDeque;
import java.util.Deque;

/*
 * 239. Sliding Window Maximum — https://leetcode.com/problems/sliding-window-maximum/
 * Return the maximum of every window of size k sliding from left to right over nums.
 * Input: [1,3,-1,-3,5,3,6,7], k = 3 -> [3,3,5,5,6,7] · [1], k = 1 -> [1]
 * Explanation: README.md
 */
public class SlidingWindowMaximum {
    public static void main(String[] args) {
        System.out.println(java.util.Arrays.toString(maxSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3)));   // [3, 3, 5, 5, 6, 7]
    }

    /* Approach 1 - scan each window. Time O(n * k). */
    public static int[] maxSlidingWindowBrute(int[] nums, int k) {
        int[] out = new int[nums.length - k + 1];
        for (int i = 0; i < out.length; i++) {
            int max = Integer.MIN_VALUE;
            for (int j = i; j < i + k; j++) max = Math.max(max, nums[j]);
            out[i] = max;
        }
        return out;
    }

    /*
     * Approach 2 - deque of indexes with decreasing values. A new value removes smaller ones from the back (they can
     * never be a maximum again); the front leaves when it slides out. The front is always the window max. O(n).
     */
    public static int[] maxSlidingWindow(int[] nums, int k) {
        int[] out = new int[nums.length - k + 1];
        Deque<Integer> dq = new ArrayDeque<>();
        for (int i = 0; i < nums.length; i++) {
            if (!dq.isEmpty() && dq.peekFirst() <= i - k) dq.pollFirst();
            while (!dq.isEmpty() && nums[dq.peekLast()] <= nums[i]) dq.pollLast();
            dq.addLast(i);
            if (i >= k - 1) out[i - k + 1] = nums[dq.peekFirst()];
        }
        return out;
    }

    /*
     * Approach 3 - split nums into blocks of size k. Every window covers the end of one block and the start of the
     * next: max = max(suffixMax[i], prefixMax[i + k - 1]). O(n), no deque.
     */
    public static int[] maxSlidingWindowBlocks(int[] nums, int k) {
        int n = nums.length;
        int[] prefix = new int[n], suffix = new int[n];
        for (int i = 0; i < n; i++) prefix[i] = i % k == 0 ? nums[i] : Math.max(prefix[i - 1], nums[i]);
        for (int i = n - 1; i >= 0; i--) suffix[i] = (i == n - 1 || (i + 1) % k == 0) ? nums[i] : Math.max(suffix[i + 1], nums[i]);
        int[] out = new int[n - k + 1];
        for (int i = 0; i + k - 1 < n; i++) out[i] = Math.max(suffix[i], prefix[i + k - 1]);
        return out;
    }
}
