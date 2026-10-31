package TrappingRainWater;

import java.util.ArrayDeque;
import java.util.Deque;

/*
 * Link: https://leetcode.com/problems/trapping-rain-water/
 * Given n non-negative integers representing an elevation map where the width of each bar is 1,
 * compute how much water it can trap after raining.
 * Input: height = [0,1,0,2,1,0,1,3,2,1,2,1] -> Output: 6
 * Input: height = [4,2,0,3,2,5]             -> Output: 9
 * Constraints: 1 <= n <= 2 * 10^4, 0 <= height[i] <= 10^5
 * Key idea: water above bar i = min(highest bar on its left, highest bar on its right) - height[i]  (never negative).
 * Explanation: README.md
 */
public class TrappingRainWater {
    /*
     * Just to test
     */
    public static void main(String[] args) {
        int[] height = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println(trapBruteForce(height));    // 6
        System.out.println(trapPrefixMax(height));     // 6
        System.out.println(trapStack(height));         // 6
        System.out.println(trapTwoPointers(height));   // 6
        System.out.println(trapTwoPointers(new int[]{4, 2, 0, 3, 2, 5}));   // 9
    }

    /*
     * Approach 1 - brute force: for every bar, scan left and right for the highest walls.
     * Time O(n^2), space O(1).
     */
    public static int trapBruteForce(int[] height) {
        int water = 0;
        for (int i = 0; i < height.length; i++) {
            int leftMax = 0, rightMax = 0;
            for (int l = 0; l <= i; l++) {
                leftMax = Math.max(leftMax, height[l]);
            }
            for (int r = i; r < height.length; r++) {
                rightMax = Math.max(rightMax, height[r]);
            }
            water += Math.min(leftMax, rightMax) - height[i];
        }
        return water;
    }

    /*
     * Approach 2 - precompute the walls (DP): leftMax[i] and rightMax[i] in two passes, then sum.
     * Time O(n), space O(n).
     */
    public static int trapPrefixMax(int[] height) {
        int n = height.length;
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];
        leftMax[0] = height[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }
        rightMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }
        int water = 0;
        for (int i = 0; i < n; i++) {
            water += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        return water;
    }

    /*
     * Approach 3 - monotonic stack: fill water layer by layer (horizontally). The stack keeps indexes of
     * decreasing bars; a taller bar closes a basin: bottom = popped bar, walls = new top and current bar.
     * Time O(n) (each index pushed and popped once), space O(n).
     */
    public static int trapStack(int[] height) {
        Deque<Integer> stack = new ArrayDeque<>();
        int water = 0;
        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int bottom = stack.pop();
                if (stack.isEmpty()) {
                    break;                      // no left wall: water would spill
                }
                int left = stack.peek();
                int width = i - left - 1;
                int depth = Math.min(height[left], height[i]) - height[bottom];
                water += width * depth;
            }
            stack.push(i);
        }
        return water;
    }

    /*
     * Approach 4 - two pointers: walk inwards from both ends, always moving the side with the lower wall.
     * If leftMax < rightMax, the left bar's water is decided by leftMax alone (there is a taller wall on the right),
     * so we can settle it without knowing the exact right maximum. Time O(n), space O(1). The interview answer.
     */
    public static int trapTwoPointers(int[] height) {
        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0;
        int water = 0;
        while (left < right) {
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);
            if (leftMax < rightMax) {
                water += leftMax - height[left++];
            } else {
                water += rightMax - height[right--];
            }
        }
        return water;
    }
}
