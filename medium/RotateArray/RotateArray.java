package RotateArray;

import java.util.Arrays;

/*
 * 189. Rotate Array — https://leetcode.com/problems/rotate-array/
 * Rotate the array to the right by k steps, in place. Follow up: O(1) extra space.
 * Input: [1,2,3,4,5,6,7], k = 3 -> [5,6,7,1,2,3,4] · [-1,-100,3,99], k = 2 -> [3,99,-1,-100]
 * Explanation: README.md
 */
public class RotateArray {
    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5, 6, 7};
        rotate(a, 3);
        System.out.println(Arrays.toString(a));     // [5, 6, 7, 1, 2, 3, 4]
    }

    /* Approach 1 - reverse all, then reverse the first k and the last n - k. O(n), O(1). */
    public static void rotate(int[] nums, int k) {
        int n = nums.length;
        k %= n;
        reverse(nums, 0, n - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, n - 1);
    }

    private static void reverse(int[] a, int i, int j) {
        for (; i < j; i++, j--) {
            int t = a[i];
            a[i] = a[j];
            a[j] = t;
        }
    }

    /* Approach 2 - follow each cycle i -> i + k -> i + 2k ...; gcd(n, k) cycles in total. O(n), O(1). */
    public static void rotateCycles(int[] nums, int k) {
        int n = nums.length;
        k %= n;
        int moved = 0;
        for (int start = 0; moved < n; start++) {
            int cur = start, carry = nums[start];
            do {
                int next = (cur + k) % n;
                int tmp = nums[next];
                nums[next] = carry;
                carry = tmp;
                cur = next;
                moved++;
            } while (cur != start);
        }
    }

    /* Approach 3 - extra array: a[(i + k) % n] = nums[i]. O(n) space. */
    public static void rotateCopy(int[] nums, int k) {
        int n = nums.length;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[(i + k) % n] = nums[i];
        System.arraycopy(a, 0, nums, 0, n);
    }
}
