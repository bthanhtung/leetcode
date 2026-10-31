package SlidingWindowMedian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/*
 * 480. Sliding Window Median — https://leetcode.com/problems/sliding-window-median/
 * Return the median of every window of size k sliding over nums.
 * Input: [1,3,-1,-3,5,3,6,7], k = 3 -> [1,-1,-1,3,5,6] · [1,2,3,4,2,3,1,4,2], k = 3 -> [2,3,3,3,2,3,2]
 * Explanation: README.md
 */
public class SlidingWindowMedian {
    public static void main(String[] args) {
        System.out.println(java.util.Arrays.toString(medianSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3)));
    }

    /* Approach 1 - keep the window sorted in a list (binary search insert/remove). O(n * k). */
    public static double[] medianSlidingWindowSorted(int[] nums, int k) {
        List<Integer> window = new ArrayList<>();
        double[] out = new double[nums.length - k + 1];
        for (int i = 0; i < nums.length; i++) {
            int pos = Collections.binarySearch(window, nums[i]);
            window.add(pos >= 0 ? pos : -pos - 1, nums[i]);
            if (i >= k) window.remove(Collections.binarySearch(window, nums[i - k]));
            if (i >= k - 1) out[i - k + 1] = k % 2 == 1 ? window.get(k / 2) : ((long) window.get(k / 2 - 1) + window.get(k / 2)) / 2.0;
        }
        return out;
    }

    /*
     * Approach 2 - two heaps like problem 295 (low = max-heap of the smaller half, high = min-heap). Elements leaving the
     * window are only recorded in a "to delete" map and removed when they reach a heap top. balance tracks the real
     * size difference. O(n log n).
     */
    public static double[] medianSlidingWindow(int[] nums, int k) {
        PriorityQueue<Integer> low = new PriorityQueue<>(Collections.reverseOrder()), high = new PriorityQueue<>();
        Map<Integer, Integer> toDelete = new HashMap<>();
        double[] out = new double[nums.length - k + 1];
        for (int i = 0; i < k; i++) low.add(nums[i]);
        for (int i = 0; i < k / 2; i++) high.add(low.poll());
        for (int i = k; ; i++) {
            out[i - k] = k % 2 == 1 ? low.peek() : ((long) low.peek() + high.peek()) / 2.0;
            if (i == nums.length) break;
            int outgoing = nums[i - k], incoming = nums[i];
            int balance = outgoing <= low.peek() ? -1 : 1;          // which side loses a real element
            toDelete.merge(outgoing, 1, Integer::sum);
            if (!low.isEmpty() && incoming <= low.peek()) {
                low.add(incoming);
                balance++;
            } else {
                high.add(incoming);
                balance--;
            }
            // Both tops were real before this step and the outgoing element is on the side that lost, so the top we
            // move from the other side is always a real element.
            if (balance < 0) low.add(high.poll());
            else if (balance > 0) high.add(low.poll());
            while (!low.isEmpty() && toDelete.getOrDefault(low.peek(), 0) > 0) toDelete.merge(low.poll(), -1, Integer::sum);
            while (!high.isEmpty() && toDelete.getOrDefault(high.peek(), 0) > 0) toDelete.merge(high.poll(), -1, Integer::sum);
        }
        return out;
    }
}
