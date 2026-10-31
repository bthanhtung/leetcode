package IntersectionOfTwoArraysII;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * 350. Intersection of Two Arrays II — https://leetcode.com/problems/intersection-of-two-arrays-ii/
 * Return the intersection of two arrays, each element appearing as many times as it shows in both. Any order.
 * Input: [1,2,2,1] & [2,2] -> [2,2] · [4,9,5] & [9,4,9,8,4] -> [4,9]
 * Explanation: README.md
 */
public class IntersectionOfTwoArraysII {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(intersect(new int[]{1, 2, 2, 1}, new int[]{2, 2})));                 // [2, 2]
        System.out.println(Arrays.toString(intersectSorted(new int[]{4, 9, 5}, new int[]{9, 4, 9, 8, 4})));    // [4, 9]
    }

    /* Approach 1 - count the smaller array, walk the other and consume counts. Time O(m + n), space O(min(m, n)). */
    public static int[] intersect(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) return intersect(nums2, nums1);
        Map<Integer, Integer> count = new HashMap<>();
        for (int x : nums1) count.merge(x, 1, Integer::sum);
        int[] out = new int[nums1.length];
        int k = 0;
        for (int x : nums2) {
            int c = count.getOrDefault(x, 0);
            if (c > 0) {
                out[k++] = x;
                count.put(x, c - 1);
            }
        }
        return Arrays.copyOf(out, k);
    }

    /* Approach 2 - sort both and merge with two pointers. Time O(m log m + n log n), space O(1) besides sorting. */
    public static int[] intersectSorted(int[] nums1, int[] nums2) {
        int[] a = nums1.clone(), b = nums2.clone();
        Arrays.sort(a);
        Arrays.sort(b);
        int[] out = new int[Math.min(a.length, b.length)];
        int i = 0, j = 0, k = 0;
        while (i < a.length && j < b.length) {
            if (a[i] < b[j]) i++;
            else if (a[i] > b[j]) j++;
            else {
                out[k++] = a[i];
                i++;
                j++;
            }
        }
        return Arrays.copyOf(out, k);
    }
}
