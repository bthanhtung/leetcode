package MinimumDifficultyOfAJobSchedule;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/*
 * 1335. Minimum Difficulty of a Job Schedule — https://leetcode.com/problems/minimum-difficulty-of-a-job-schedule/
 * Jobs must be done in order over d days, at least one job per day. A day's difficulty is its hardest job; the
 * schedule's difficulty is the sum over days. Return the minimum, or -1 if fewer jobs than days.
 * Input: [6,5,4,3,2,1], d = 2 -> 7 · [9,9,9], d = 4 -> -1 · [1,1,1], d = 3 -> 3
 * Explanation: README.md
 */
public class MinimumDifficultyOfAJobSchedule {
    public static void main(String[] args) {
        System.out.println(minDifficulty(new int[]{6, 5, 4, 3, 2, 1}, 2));        // 7
        System.out.println(minDifficultyMemo(new int[]{9, 9, 9}, 4));            // -1
    }

    /* Approach 1 - best(i, days): jobs i.. in days days; try every length for today. Time O(n^2 d). */
    public static int minDifficultyMemo(int[] jobs, int d) {
        if (jobs.length < d) return -1;
        return best(jobs, 0, d, new Integer[jobs.length][d + 1]);
    }

    private static int best(int[] jobs, int i, int days, Integer[][] memo) {
        if (days == 1) {
            int max = 0;
            for (int k = i; k < jobs.length; k++) max = Math.max(max, jobs[k]);
            return max;
        }
        if (memo[i][days] != null) return memo[i][days];
        int result = Integer.MAX_VALUE, today = 0;
        for (int end = i; end <= jobs.length - days; end++) {       // leave at least one job for each remaining day
            today = Math.max(today, jobs[end]);
            result = Math.min(result, today + best(jobs, end + 1, days - 1, memo));
        }
        return memo[i][days] = result;
    }

    /* Approach 2 - dp[day][i] = best for the first i jobs in day days. Time O(n^2 d), space O(n d). */
    public static int minDifficultyTable(int[] jobs, int d) {
        int n = jobs.length;
        if (n < d) return -1;
        int inf = Integer.MAX_VALUE / 2;
        int[][] dp = new int[d + 1][n + 1];
        for (int[] row : dp) Arrays.fill(row, inf);
        dp[0][0] = 0;
        for (int day = 1; day <= d; day++) {
            for (int i = day; i <= n; i++) {
                int today = 0;
                for (int j = i; j >= day; j--) {                     // today does jobs j-1 .. i-1
                    today = Math.max(today, jobs[j - 1]);
                    dp[day][i] = Math.min(dp[day][i], dp[day - 1][j - 1] + today);
                }
            }
        }
        return dp[d][n];
    }

    /*
     * Approach 3 - same table, but each row in O(n) with a monotonic stack: when job i ends today, all earlier jobs
     * smaller than jobs[i] would be dominated by it, so their best "previous day" values merge. Time O(n d).
     */
    public static int minDifficulty(int[] jobs, int d) {
        int n = jobs.length;
        if (n < d) return -1;
        int inf = Integer.MAX_VALUE / 2;
        int[] prev = new int[n], cur = new int[n];
        prev[0] = jobs[0];
        for (int i = 1; i < n; i++) prev[i] = Math.max(prev[i - 1], jobs[i]);     // day 1: max of the prefix
        for (int day = 2; day <= d; day++) {
            Arrays.fill(cur, inf);
            Deque<int[]> stack = new ArrayDeque<>();                 // {job value, best dp[day-1] before the segment}
            for (int i = day - 1; i < n; i++) {
                int bestBefore = prev[i - 1];
                while (!stack.isEmpty() && stack.peek()[0] <= jobs[i]) bestBefore = Math.min(bestBefore, stack.pop()[1]);
                int candidate = bestBefore + jobs[i];
                if (!stack.isEmpty()) candidate = Math.min(candidate, cur[stack.peek()[2]]);
                cur[i] = candidate;
                stack.push(new int[]{jobs[i], bestBefore, i});
            }
            int[] t = prev;
            prev = cur;
            cur = t;
        }
        return prev[n - 1];
    }
}
