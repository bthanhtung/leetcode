package LargestNumber;

import java.util.Arrays;

/*
 * 179. Largest Number — https://leetcode.com/problems/largest-number/
 * Arrange non-negative integers to form the largest number; return it as a string.
 * Input: [10,2] -> "210" · [3,30,34,5,9] -> "9534330" · [0,0] -> "0"
 * Explanation: README.md
 */
public class LargestNumber {
    public static void main(String[] args) {
        System.out.println(largestNumber(new int[]{3, 30, 34, 5, 9}));     // 9534330
    }

    /*
     * a goes before b iff a + b > b + a as strings. This order is transitive, so sorting with it is valid.
     * Time O(n log n * L).
     */
    public static String largestNumber(int[] nums) {
        String[] s = Arrays.stream(nums).mapToObj(String::valueOf).toArray(String[]::new);
        Arrays.sort(s, (a, b) -> (b + a).compareTo(a + b));
        if (s[0].equals("0")) return "0";                      // all zeros
        return String.join("", s);
    }
}
