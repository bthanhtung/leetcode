package NextGreaterElementI;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/*
 * 496. Next Greater Element I — https://leetcode.com/problems/next-greater-element-i/
 * nums1 is a subset of nums2 (distinct values). For each x in nums1, find the first greater element to the right of x
 * in nums2, or -1.
 * Input: nums1 = [4,1,2], nums2 = [1,3,4,2] -> [-1,3,-1] · nums1 = [2,4], nums2 = [1,2,3,4] -> [3,-1]
 * Explanation: README.md
 */
public class NextGreaterElementI {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(nextGreaterElement(new int[]{4, 1, 2}, new int[]{1, 3, 4, 2})));          // [-1, 3, -1]
        System.out.println(Arrays.toString(nextGreaterElementBruteForce(new int[]{2, 4}, new int[]{1, 2, 3, 4}))); // [3, -1]
    }

    /* Approach 1 - for each x, find it in nums2 and scan right. Time O(m * n). */
    public static int[] nextGreaterElementBruteForce(int[] nums1, int[] nums2) {
        int[] out = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            out[i] = -1;
            int j = 0;
            while (nums2[j] != nums1[i]) j++;
            for (j++; j < nums2.length; j++) {
                if (nums2[j] > nums1[i]) {
                    out[i] = nums2[j];
                    break;
                }
            }
        }
        return out;
    }

    /*
     * Approach 2 - monotonic decreasing stack over nums2: when a bigger value arrives, it is the next greater element of
     * every smaller value waiting on the stack. Store answers in a map. Time O(m + n), space O(n).
     */
    public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> next = new HashMap<>();
        Deque<Integer> stack = new ArrayDeque<>();
        for (int x : nums2) {
            while (!stack.isEmpty() && stack.peek() < x) {
                next.put(stack.pop(), x);
            }
            stack.push(x);
        }
        int[] out = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            out[i] = next.getOrDefault(nums1[i], -1);
        }
        return out;
    }
}
