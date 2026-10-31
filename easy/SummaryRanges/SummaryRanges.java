package SummaryRanges;

import java.util.ArrayList;
import java.util.List;

/*
 * 228. Summary Ranges — https://leetcode.com/problems/summary-ranges/
 * Given a sorted array of unique integers, return the smallest list of ranges that covers all numbers exactly:
 * "a->b" for a run, "a" for a single number.
 * Input: [0,1,2,4,5,7] -> ["0->2","4->5","7"] · [0,2,3,4,6,8,9] -> ["0","2->4","6","8->9"]
 * Explanation: README.md
 */
public class SummaryRanges {
    public static void main(String[] args) {
        System.out.println(summaryRanges(new int[]{0, 1, 2, 4, 5, 7}));     // [0->2, 4->5, 7]
    }

    /* Extend j while the next number is exactly one more; then emit nums[i] (and ->nums[j]). Time O(n). */
    public static List<String> summaryRanges(int[] nums) {
        List<String> out = new ArrayList<>();
        for (int i = 0; i < nums.length; ) {
            int j = i;
            while (j + 1 < nums.length && (long) nums[j + 1] == (long) nums[j] + 1) j++;
            out.add(i == j ? String.valueOf(nums[i]) : nums[i] + "->" + nums[j]);
            i = j + 1;
        }
        return out;
    }
}
