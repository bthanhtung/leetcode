package MajorityElement;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * 169. Majority Element — https://leetcode.com/problems/majority-element/
 * Return the majority element: the value that appears more than n / 2 times (it always exists).
 * Input: [3,2,3] -> 3 · [2,2,1,1,1,2,2] -> 2. Follow up: O(n) time and O(1) space.
 * Explanation: README.md
 */
public class MajorityElement {
    public static void main(String[] args) {
        System.out.println(majorityElement(new int[]{2, 2, 1, 1, 1, 2, 2}));    // 2
        System.out.println(majorityElementSort(new int[]{3, 2, 3}));           // 3
    }

    /* Approach 1 - count, return as soon as a count passes n / 2. Time O(n), space O(n). */
    public static int majorityElementMap(int[] nums) {
        Map<Integer, Integer> count = new HashMap<>();
        for (int x : nums) {
            if (count.merge(x, 1, Integer::sum) > nums.length / 2) return x;
        }
        throw new IllegalArgumentException("no majority element");
    }

    /* Approach 2 - after sorting, the majority element always covers the middle index. Time O(n log n). */
    public static int majorityElementSort(int[] nums) {
        int[] a = nums.clone();
        Arrays.sort(a);
        return a[a.length / 2];
    }

    /*
     * Approach 3 - Boyer-Moore voting: pair off different values and cancel them. The majority has more than all the
     * others together, so it survives as the candidate. Time O(n), space O(1).
     */
    public static int majorityElement(int[] nums) {
        int candidate = 0, votes = 0;
        for (int x : nums) {
            if (votes == 0) candidate = x;
            votes += x == candidate ? 1 : -1;
        }
        return candidate;
    }
}
