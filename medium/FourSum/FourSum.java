package FourSum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * 18. 4Sum — https://leetcode.com/problems/4sum/
 * Return all unique quadruplets [a, b, c, d] of distinct indices with a + b + c + d == target.
 * Input: [1,0,-1,0,-2,2], target = 0 -> [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]] · [2,2,2,2,2], 8 -> [[2,2,2,2]]
 * Explanation: README.md
 */
public class FourSum {
    public static void main(String[] args) {
        System.out.println(fourSum(new int[]{1, 0, -1, 0, -2, 2}, 0));
        System.out.println(fourSumKSum(new int[]{2, 2, 2, 2, 2}, 8));
    }

    /* Approach 1 - all quadruples, deduplicated. Time O(n^4). Reference for tests. */
    public static List<List<Integer>> fourSumBruteForce(int[] nums, int target) {
        Set<List<Integer>> found = new HashSet<>();
        int n = nums.length;
        for (int a = 0; a < n; a++)
            for (int b = a + 1; b < n; b++)
                for (int c = b + 1; c < n; c++)
                    for (int d = c + 1; d < n; d++)
                        if ((long) nums[a] + nums[b] + nums[c] + nums[d] == target) {
                            int[] q = {nums[a], nums[b], nums[c], nums[d]};
                            Arrays.sort(q);
                            found.add(List.of(q[0], q[1], q[2], q[3]));
                        }
        return new ArrayList<>(found);
    }

    /* Approach 2 - sort, two fixed loops + two pointers, skipping duplicates. Sums in long. Time O(n^3), space O(1). */
    public static List<List<Integer>> fourSum(int[] nums, int target) {
        int[] a = nums.clone();
        Arrays.sort(a);
        int n = a.length;
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < n - 3; i++) {
            if (i > 0 && a[i] == a[i - 1]) continue;
            for (int j = i + 1; j < n - 2; j++) {
                if (j > i + 1 && a[j] == a[j - 1]) continue;
                int lo = j + 1, hi = n - 1;
                while (lo < hi) {
                    long sum = (long) a[i] + a[j] + a[lo] + a[hi];
                    if (sum < target) {
                        lo++;
                    } else if (sum > target) {
                        hi--;
                    } else {
                        result.add(List.of(a[i], a[j], a[lo], a[hi]));
                        while (lo < hi && a[lo] == a[lo + 1]) lo++;
                        while (lo < hi && a[hi] == a[hi - 1]) hi--;
                        lo++;
                        hi--;
                    }
                }
            }
        }
        return result;
    }

    /* Approach 3 - generic k-Sum: recursion reduces k to 2, then two pointers. Same O(n^(k-1)), works for any k. */
    public static List<List<Integer>> fourSumKSum(int[] nums, int target) {
        int[] a = nums.clone();
        Arrays.sort(a);
        return kSum(a, target, 0, 4);
    }

    private static List<List<Integer>> kSum(int[] a, long target, int start, int k) {
        List<List<Integer>> result = new ArrayList<>();
        if (start >= a.length) {
            return result;
        }
        if (k == 2) {
            int lo = start, hi = a.length - 1;
            while (lo < hi) {
                long sum = (long) a[lo] + a[hi];
                if (sum < target || (lo > start && a[lo] == a[lo - 1])) {
                    lo++;
                } else if (sum > target) {
                    hi--;
                } else {
                    result.add(new ArrayList<>(List.of(a[lo++], a[hi--])));
                }
            }
            return result;
        }
        for (int i = start; i < a.length; i++) {
            if (i > start && a[i] == a[i - 1]) continue;
            for (List<Integer> rest : kSum(a, target - a[i], i + 1, k - 1)) {
                rest.addFirst(a[i]);
                result.add(rest);
            }
        }
        return result;
    }
}
