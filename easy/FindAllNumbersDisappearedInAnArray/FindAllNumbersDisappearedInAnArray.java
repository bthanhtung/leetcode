package FindAllNumbersDisappearedInAnArray;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * 448. Find All Numbers Disappeared in an Array — https://leetcode.com/problems/find-all-numbers-disappeared-in-an-array/
 * nums has n integers in [1, n]. Return all integers in [1, n] that do not appear. Follow up: no extra space
 * (the output does not count), O(n) time.
 * Input: [4,3,2,7,8,2,3,1] -> [5,6] · [1,1] -> [2]
 * Explanation: README.md
 */
public class FindAllNumbersDisappearedInAnArray {
    public static void main(String[] args) {
        System.out.println(findDisappearedNumbers(new int[]{4, 3, 2, 7, 8, 2, 3, 1}));       // [5, 6]
        System.out.println(findDisappearedNumbersCyclic(new int[]{1, 1}));                  // [2]
    }

    /* Approach 1 - set of present values. Time O(n), space O(n). */
    public static List<Integer> findDisappearedNumbersSet(int[] nums) {
        Set<Integer> present = new HashSet<>();
        for (int x : nums) present.add(x);
        List<Integer> out = new ArrayList<>();
        for (int v = 1; v <= nums.length; v++) {
            if (!present.contains(v)) out.add(v);
        }
        return out;
    }

    /* Approach 2 - use the array as the set: for value v, make nums[v - 1] negative. Positive slots are missing. O(1) space. */
    public static List<Integer> findDisappearedNumbers(int[] nums) {
        for (int x : nums) {
            int i = Math.abs(x) - 1;
            if (nums[i] > 0) nums[i] = -nums[i];
        }
        List<Integer> out = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) out.add(i + 1);
        }
        return out;
    }

    /* Approach 3 - cyclic sort: swap each value v to index v - 1; afterwards nums[i] != i + 1 means i + 1 is missing. */
    public static List<Integer> findDisappearedNumbersCyclic(int[] nums) {
        int i = 0;
        while (i < nums.length) {
            int target = nums[i] - 1;
            if (nums[target] != nums[i]) {
                int tmp = nums[target];
                nums[target] = nums[i];
                nums[i] = tmp;
            } else {
                i++;
            }
        }
        List<Integer> out = new ArrayList<>();
        for (i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) out.add(i + 1);
        }
        return out;
    }
}
