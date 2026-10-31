package IntersectionOfTwoArrays;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
 * 349. Intersection of Two Arrays — https://leetcode.com/problems/intersection-of-two-arrays/
 * Return the intersection of two arrays: each element of the result is unique. Any order.
 * Input: [1,2,2,1] & [2,2] -> [2] · [4,9,5] & [9,4,9,8,4] -> [9,4]
 * Explanation: README.md
 */
public class IntersectionOfTwoArrays {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(intersection(new int[]{1, 2, 2, 1}, new int[]{2, 2})));               // [2]
        System.out.println(Arrays.toString(intersectionTwoPointers(new int[]{4, 9, 5}, new int[]{9, 4, 9, 8, 4}))); // [4, 9]
    }

    /* Approach 1 - set of nums1; walk nums2 and remove matches so each value is reported once. Time O(m + n). */
    public static int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set = new HashSet<>();
        for (int x : nums1) set.add(x);
        int[] out = new int[Math.min(nums1.length, nums2.length)];
        int k = 0;
        for (int x : nums2) {
            if (set.remove(x)) out[k++] = x;
        }
        return Arrays.copyOf(out, k);
    }

    /* Approach 2 - sort both, walk with two pointers, skip repeats. Time O(m log m + n log n), space O(1) besides sorting. */
    public static int[] intersectionTwoPointers(int[] nums1, int[] nums2) {
        int[] a = nums1.clone(), b = nums2.clone();
        Arrays.sort(a);
        Arrays.sort(b);
        int[] out = new int[Math.min(a.length, b.length)];
        int i = 0, j = 0, k = 0;
        while (i < a.length && j < b.length) {
            if (a[i] < b[j]) i++;
            else if (a[i] > b[j]) j++;
            else {
                if (k == 0 || out[k - 1] != a[i]) out[k++] = a[i];
                i++;
                j++;
            }
        }
        return Arrays.copyOf(out, k);
    }

    /* Approach 3 - sort the shorter array, binary-search each distinct value of the other. Time O((m + n) log m). */
    public static int[] intersectionBinarySearch(int[] nums1, int[] nums2) {
        int[] small = nums1.length <= nums2.length ? nums1.clone() : nums2.clone();
        int[] big = nums1.length <= nums2.length ? nums2 : nums1;
        Arrays.sort(small);
        Set<Integer> found = new HashSet<>();
        for (int x : big) {
            if (Arrays.binarySearch(small, x) >= 0) found.add(x);
        }
        return found.stream().mapToInt(Integer::intValue).toArray();
    }
}
