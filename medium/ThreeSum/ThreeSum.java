package ThreeSum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * 15. 3Sum — https://leetcode.com/problems/3sum/
 * Return all unique triplets [nums[i], nums[j], nums[k]] with distinct indices whose sum is 0. No duplicate triplets.
 * Input: [-1,0,1,2,-1,-4] -> [[-1,-1,2],[-1,0,1]] · [0,1,1] -> [] · [0,0,0] -> [[0,0,0]]
 * Explanation: README.md
 */
public class ThreeSum {
    public static void main(String[] args) {
        System.out.println(threeSum(new int[]{-1, 0, 1, 2, -1, -4}));        // [[-1, -1, 2], [-1, 0, 1]]
        System.out.println(threeSumHashSet(new int[]{0, 0, 0}));             // [[0, 0, 0]]
    }

    /* Approach 1 - all triples, deduplicate with a set of sorted triplets. Time O(n^3). Reference only. */
    public static List<List<Integer>> threeSumBruteForce(int[] nums) {
        Set<List<Integer>> found = new HashSet<>();
        for (int i = 0; i < nums.length; i++)
            for (int j = i + 1; j < nums.length; j++)
                for (int k = j + 1; k < nums.length; k++)
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        int[] t = {nums[i], nums[j], nums[k]};
                        Arrays.sort(t);
                        found.add(List.of(t[0], t[1], t[2]));
                    }
        return new ArrayList<>(found);
    }

    /* Approach 2 - sort, fix nums[i], then Two Sum with a hash set on the rest. Time O(n^2), space O(n). */
    public static List<List<Integer>> threeSumHashSet(int[] nums) {
        int[] a = nums.clone();
        Arrays.sort(a);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < a.length && a[i] <= 0; i++) {
            if (i > 0 && a[i] == a[i - 1]) {
                continue;
            }
            Set<Integer> seen = new HashSet<>();
            for (int j = i + 1; j < a.length; j++) {
                int need = -a[i] - a[j];
                if (seen.contains(need)) {
                    result.add(List.of(a[i], need, a[j]));
                    while (j + 1 < a.length && a[j] == a[j + 1]) {
                        j++;                      // same a[j] would give the same triplet
                    }
                }
                seen.add(a[j]);
            }
        }
        return result;
    }

    /*
     * Approach 3 - sort, fix nums[i], two pointers on the rest; skip equal values to avoid duplicates.
     * Time O(n^2), space O(1) besides sorting.
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        int[] a = nums.clone();
        Arrays.sort(a);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < a.length - 2; i++) {
            if (a[i] > 0) {
                break;                            // smallest is positive: no more zero sums
            }
            if (i > 0 && a[i] == a[i - 1]) {
                continue;
            }
            int lo = i + 1, hi = a.length - 1;
            while (lo < hi) {
                int sum = a[i] + a[lo] + a[hi];
                if (sum < 0) {
                    lo++;
                } else if (sum > 0) {
                    hi--;
                } else {
                    result.add(List.of(a[i], a[lo], a[hi]));
                    while (lo < hi && a[lo] == a[lo + 1]) lo++;
                    while (lo < hi && a[hi] == a[hi - 1]) hi--;
                    lo++;
                    hi--;
                }
            }
        }
        return result;
    }
}
