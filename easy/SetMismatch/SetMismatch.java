package SetMismatch;

import java.util.Arrays;

/*
 * 645. Set Mismatch — https://leetcode.com/problems/set-mismatch/
 * nums should contain 1..n once each, but one number got duplicated and replaced another. Return [duplicate, missing].
 * Input: [1,2,2,4] -> [2,3] · [1,1] -> [1,2]
 * Explanation: README.md
 */
public class SetMismatch {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(findErrorNums(new int[]{1, 2, 2, 4})));      // [2, 3]
    }

    /* Approach 1 - count each value. O(n), O(n). */
    public static int[] findErrorNumsCount(int[] nums) {
        int[] count = new int[nums.length + 1];
        for (int x : nums) count[x]++;
        int dup = 0, missing = 0;
        for (int v = 1; v <= nums.length; v++) {
            if (count[v] == 2) dup = v;
            if (count[v] == 0) missing = v;
        }
        return new int[]{dup, missing};
    }

    /* Approach 2 - math: dup - missing = sum - n(n+1)/2 and dup^2 - missing^2 = sumSq - n(n+1)(2n+1)/6. O(n), O(1). */
    public static int[] findErrorNumsMath(int[] nums) {
        long n = nums.length, sum = 0, sumSq = 0;
        for (int x : nums) {
            sum += x;
            sumSq += (long) x * x;
        }
        long diff = sum - n * (n + 1) / 2;                       // dup - missing
        long sumBoth = (sumSq - n * (n + 1) * (2 * n + 1) / 6) / diff;   // dup + missing
        return new int[]{(int) ((diff + sumBoth) / 2), (int) ((sumBoth - diff) / 2)};
    }

    /* Approach 3 - flip the sign at index |x| - 1; meeting a negative there means x is the duplicate. O(n), O(1) extra. */
    public static int[] findErrorNums(int[] nums) {
        int[] a = nums.clone();
        int dup = 0, missing = 0;
        for (int x : a) {
            int i = Math.abs(x) - 1;
            if (a[i] < 0) dup = Math.abs(x);
            else a[i] = -a[i];
        }
        for (int i = 0; i < a.length; i++) if (a[i] > 0) missing = i + 1;
        return new int[]{dup, missing};
    }
}
