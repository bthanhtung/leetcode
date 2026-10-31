package MissingNumber;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
 * 268. Missing Number — https://leetcode.com/problems/missing-number/
 * nums contains n distinct numbers from [0, n]. Return the only number in the range that is missing.
 * Input: [3,0,1] -> 2 · [0,1] -> 2 · [9,6,4,2,3,5,7,0,1] -> 8. Follow up: O(1) extra space, O(n) time.
 * Explanation: README.md
 */
public class MissingNumber {
    public static void main(String[] args) {
        System.out.println(missingNumber(new int[]{3, 0, 1}));                  // 2
        System.out.println(missingNumberXor(new int[]{9, 6, 4, 2, 3, 5, 7, 0, 1})); // 8
    }

    /* Approach 1 - sort; the first index with nums[i] != i is the answer (or n). Time O(n log n). */
    public static int missingNumberSort(int[] nums) {
        int[] a = nums.clone();
        Arrays.sort(a);
        for (int i = 0; i < a.length; i++) {
            if (a[i] != i) return i;
        }
        return a.length;
    }

    /* Approach 2 - put all in a set, look for the one not there. Time O(n), space O(n). */
    public static int missingNumberSet(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int x : nums) set.add(x);
        for (int i = 0; ; i++) {
            if (!set.contains(i)) return i;
        }
    }

    /* Approach 3 - 0 + 1 + ... + n = n(n+1)/2; subtract what we have. Time O(n), space O(1). */
    public static int missingNumber(int[] nums) {
        int n = nums.length;
        int sum = n * (n + 1) / 2;
        for (int x : nums) sum -= x;
        return sum;
    }

    /* Approach 4 - XOR all indexes 0..n and all values; every present number cancels. No overflow risk. */
    public static int missingNumberXor(int[] nums) {
        int x = nums.length;
        for (int i = 0; i < nums.length; i++) {
            x ^= i ^ nums[i];
        }
        return x;
    }
}
