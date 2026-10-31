package LongestConsecutiveSequence;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
 * 128. Longest Consecutive Sequence — https://leetcode.com/problems/longest-consecutive-sequence/
 * Length of the longest run of consecutive integers in an unsorted array. Must run in O(n).
 * Input: [100,4,200,1,3,2] -> 4 ([1,2,3,4]) · [0,3,7,2,5,8,4,6,0,1] -> 9
 * Explanation: README.md
 */
public class LongestConsecutiveSequence {
    public static void main(String[] args) {
        System.out.println(longestConsecutive(new int[]{100, 4, 200, 1, 3, 2}));     // 4
    }

    /* Approach 1 - only start counting at x when x - 1 is absent; each number is visited at most twice. O(n). */
    public static int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int x : nums) set.add(x);
        int best = 0;
        for (int x : set) {
            if (set.contains(x - 1)) continue;          // not the start of a run
            int y = x;
            while (y != Integer.MAX_VALUE && set.contains(y + 1)) y++;
            best = Math.max(best, y - x + 1);
        }
        return best;
    }

    /* Approach 2 - sort and scan, skipping duplicates. O(n log n). */
    public static int longestConsecutiveSort(int[] nums) {
        if (nums.length == 0) return 0;
        int[] a = nums.clone();
        Arrays.sort(a);
        int best = 1, run = 1;
        for (int i = 1; i < a.length; i++) {
            if (a[i] == a[i - 1]) continue;
            run = (long) a[i] == (long) a[i - 1] + 1 ? run + 1 : 1;
            best = Math.max(best, run);
        }
        return best;
    }
}
