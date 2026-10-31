package CreateMaximumNumber;

import java.util.Arrays;

/*
 * 321. Create Maximum Number — https://leetcode.com/problems/create-maximum-number/
 * Create the maximum number of length k from digits of nums1 and nums2, keeping the relative order of digits
 * taken from the same array.
 * Input: nums1 = [3,4,6,5], nums2 = [9,1,2,5,8,3], k = 5 -> [9,8,6,5,3]
 *        nums1 = [6,7], nums2 = [6,0,4], k = 5 -> [6,7,6,0,4] · nums1 = [3,9], nums2 = [8,9], k = 3 -> [9,8,9]
 * Explanation: README.md
 */
public class CreateMaximumNumber {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(maxNumber(new int[]{3, 4, 6, 5}, new int[]{9, 1, 2, 5, 8, 3}, 5)));
    }

    /*
     * Take i digits from nums1 and k - i from nums2 for every feasible i:
     *  - best(nums, t): the largest subsequence of length t, with a monotonic stack (drop smaller digits while we can).
     *  - merge: always take from the array whose remaining suffix is lexicographically larger (ties need lookahead).
     * Keep the best result. Time O(k * (m + n + k^2)) in the worst case.
     */
    public static int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int[] best = new int[k];
        for (int i = Math.max(0, k - nums2.length); i <= Math.min(k, nums1.length); i++) {
            int[] candidate = merge(best(nums1, i), best(nums2, k - i));
            if (greater(candidate, 0, best, 0)) best = candidate;
        }
        return best;
    }

    private static int[] best(int[] nums, int t) {
        int[] stack = new int[t];
        int top = 0, drop = nums.length - t;
        for (int x : nums) {
            while (top > 0 && drop > 0 && stack[top - 1] < x) {
                top--;
                drop--;
            }
            if (top < t) stack[top++] = x;
            else drop--;
        }
        return stack;
    }

    private static int[] merge(int[] a, int[] b) {
        int[] out = new int[a.length + b.length];
        for (int i = 0, j = 0, k = 0; k < out.length; k++) {
            out[k] = greater(a, i, b, j) ? a[i++] : b[j++];
        }
        return out;
    }

    /** a[i..] > b[j..] lexicographically (a longer equal prefix counts as greater). */
    private static boolean greater(int[] a, int i, int[] b, int j) {
        while (i < a.length && j < b.length && a[i] == b[j]) {
            i++;
            j++;
        }
        return j == b.length || (i < a.length && a[i] > b[j]);
    }
}
