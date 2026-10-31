package MaxConsecutiveOnes;

/*
 * 485. Max Consecutive Ones — https://leetcode.com/problems/max-consecutive-ones/
 * Given a binary array nums, return the maximum number of consecutive 1s.
 * Input: [1,1,0,1,1,1] -> 3 · [1,0,1,1,0,1] -> 2
 * Explanation: README.md
 */
public class MaxConsecutiveOnes {
    public static void main(String[] args) {
        System.out.println(findMaxConsecutiveOnes(new int[]{1, 1, 0, 1, 1, 1}));         // 3
        System.out.println(findMaxConsecutiveOnesStart(new int[]{1, 0, 1, 1, 0, 1}));    // 2
    }

    /* Approach 1 - running counter: +1 on a one, reset on a zero. Time O(n), space O(1). */
    public static int findMaxConsecutiveOnes(int[] nums) {
        int best = 0, run = 0;
        for (int x : nums) {
            run = x == 1 ? run + 1 : 0;
            best = Math.max(best, run);
        }
        return best;
    }

    /* Approach 2 - remember where the current run started; length = i - start. Same idea, the sliding-window shape. */
    public static int findMaxConsecutiveOnesStart(int[] nums) {
        int best = 0, start = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) start = i + 1;
            else best = Math.max(best, i - start + 1);
        }
        return best;
    }
}
