package LargestRectangleInHistogram;

import java.util.ArrayDeque;
import java.util.Deque;

/*
 * 84. Largest Rectangle in Histogram — https://leetcode.com/problems/largest-rectangle-in-histogram/
 * Given bar heights of a histogram (each bar width 1), return the area of the largest rectangle inside it.
 * Input: [2,1,5,6,2,3] -> 10 (bars 5 and 6, height 5 * width 2) · [2,4] -> 4
 * Explanation: README.md
 */
public class LargestRectangleInHistogram {
    public static void main(String[] args) {
        System.out.println(largestRectangleArea(new int[]{2, 1, 5, 6, 2, 3}));      // 10
        System.out.println(largestRectangleAreaBounds(new int[]{2, 4}));          // 4
    }

    /* Approach 1 - for each bar as the shortest, expand left and right while bars are at least as tall. Time O(n^2). */
    public static int largestRectangleAreaBrute(int[] heights) {
        int best = 0;
        for (int i = 0; i < heights.length; i++) {
            int l = i, r = i;
            while (l > 0 && heights[l - 1] >= heights[i]) l--;
            while (r + 1 < heights.length && heights[r + 1] >= heights[i]) r++;
            best = Math.max(best, heights[i] * (r - l + 1));
        }
        return best;
    }

    /*
     * Approach 2 - monotonic stack of increasing heights. When a lower bar arrives, the popped bar cannot extend
     * further right; its left limit is the bar now below it on the stack. Time O(n).
     */
    public static int largestRectangleArea(int[] heights) {
        Deque<Integer> stack = new ArrayDeque<>();
        int best = 0, n = heights.length;
        for (int i = 0; i <= n; i++) {
            int h = i == n ? 0 : heights[i];                  // a sentinel 0 at the end flushes the stack
            while (!stack.isEmpty() && heights[stack.peek()] >= h) {
                int height = heights[stack.pop()];
                int left = stack.isEmpty() ? -1 : stack.peek();
                best = Math.max(best, height * (i - left - 1));
            }
            stack.push(i);
        }
        return best;
    }

    /* Approach 3 - precompute the nearest smaller bar on each side (two monotonic passes), then one formula per bar. */
    public static int largestRectangleAreaBounds(int[] heights) {
        int n = heights.length;
        int[] left = new int[n], right = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) stack.pop();
            left[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        stack.clear();
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) stack.pop();
            right[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }
        int best = 0;
        for (int i = 0; i < n; i++) best = Math.max(best, heights[i] * (right[i] - left[i] - 1));
        return best;
    }
}
