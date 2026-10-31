package SingleNumber;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
 * 136. Single Number — https://leetcode.com/problems/single-number/
 * Every element appears twice except for one. Find that single one, in linear time and constant extra space.
 * Input: [2,2,1] -> 1 · [4,1,2,1,2] -> 4 · [1] -> 1
 * Explanation: README.md
 */
public class SingleNumber {
    public static void main(String[] args) {
        System.out.println(singleNumber(new int[]{4, 1, 2, 1, 2}));      // 4
        System.out.println(singleNumberSet(new int[]{2, 2, 1}));         // 1
        System.out.println(singleNumberMath(new int[]{1}));              // 1
    }

    /* Approach 1 - toggle values in a set: pairs cancel out, the single one remains. Time O(n), space O(n). */
    public static int singleNumberSet(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int x : nums) {
            if (!set.remove(x)) {
                set.add(x);
            }
        }
        return set.iterator().next();
    }

    /* Approach 2 - 2 * sum(distinct) - sum(all) = single. Time O(n), space O(n). Uses long to avoid overflow. */
    public static int singleNumberMath(int[] nums) {
        long all = 0, distinct = 0;
        for (int x : nums) {
            all += x;
        }
        for (int x : Arrays.stream(nums).distinct().toArray()) {
            distinct += x;
        }
        return (int) (2 * distinct - all);
    }

    /* Approach 3 - XOR everything: a ^ a = 0 and a ^ 0 = a, so pairs vanish. Time O(n), space O(1). */
    public static int singleNumber(int[] nums) {
        int result = 0;
        for (int x : nums) {
            result ^= x;
        }
        return result;
    }
}
