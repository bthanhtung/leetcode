package ThirdMaximumNumber;

import java.util.TreeSet;

/*
 * 414. Third Maximum Number — https://leetcode.com/problems/third-maximum-number/
 * Return the third distinct maximum number; if it does not exist, return the maximum.
 * Input: [3,2,1] -> 1 · [1,2] -> 2 · [2,2,3,1] -> 1
 * Explanation: README.md
 */
public class ThirdMaximumNumber {
    public static void main(String[] args) {
        System.out.println(thirdMax(new int[]{2, 2, 3, 1}));       // 1
        System.out.println(thirdMaxTreeSet(new int[]{1, 2}));      // 2
    }

    /* Approach 1 - keep the top three distinct values (Long so Integer.MIN_VALUE can be a real value). O(n), O(1). */
    public static int thirdMax(int[] nums) {
        Long first = null, second = null, third = null;
        for (int v : nums) {
            long x = v;
            if ((first != null && x == first) || (second != null && x == second) || (third != null && x == third)) continue;
            if (first == null || x > first) { third = second; second = first; first = x; }
            else if (second == null || x > second) { third = second; second = x; }
            else if (third == null || x > third) third = x;
        }
        return (int) (long) (third != null ? third : first);
    }

    /* Approach 2 - a TreeSet trimmed to its three largest elements. O(n log 3). */
    public static int thirdMaxTreeSet(int[] nums) {
        TreeSet<Integer> top = new TreeSet<>();
        for (int x : nums) {
            top.add(x);
            if (top.size() > 3) top.pollFirst();
        }
        return top.size() == 3 ? top.first() : top.last();
    }
}
