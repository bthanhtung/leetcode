package MaximumProfitInJobScheduling;

import java.util.Arrays;
import java.util.TreeMap;

/*
 * 1235. Maximum Profit in Job Scheduling — https://leetcode.com/problems/maximum-profit-in-job-scheduling/
 * Job i runs from startTime[i] to endTime[i] with profit[i]. Choose non-overlapping jobs (a job may start exactly when
 * another ends) to maximize total profit.
 * Input: start = [1,2,3,3], end = [3,4,5,6], profit = [50,10,40,70] -> 120 · start = [1,2,3,4,6], end = [3,5,10,6,9],
 *        profit = [20,20,100,70,60] -> 150
 * Explanation: README.md
 */
public class MaximumProfitInJobScheduling {
    public static void main(String[] args) {
        System.out.println(jobScheduling(new int[]{1, 2, 3, 3}, new int[]{3, 4, 5, 6}, new int[]{50, 10, 40, 70}));   // 120
        System.out.println(jobSchedulingMemo(new int[]{1, 2, 3, 4, 6}, new int[]{3, 5, 10, 6, 9}, new int[]{20, 20, 100, 70, 60}));   // 150
    }

    /*
     * Approach 1 - sort by end time; dp[i] = best using the first i jobs = max(skip job i, profit[i] + dp[j]) where j is
     * the number of jobs that end by job i's start (binary search). Time O(n log n).
     */
    public static int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        int[][] jobs = new int[n][];
        for (int i = 0; i < n; i++) jobs[i] = new int[]{startTime[i], endTime[i], profit[i]};
        Arrays.sort(jobs, (a, b) -> Integer.compare(a[1], b[1]));
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int start = jobs[i - 1][0];
            int lo = 0, hi = i - 1;                          // count of jobs among the first i-1 ending <= start
            while (lo < hi) {
                int mid = (lo + hi + 1) >>> 1;
                if (jobs[mid - 1][1] <= start) lo = mid;
                else hi = mid - 1;
            }
            dp[i] = Math.max(dp[i - 1], dp[lo] + jobs[i - 1][2]);
        }
        return dp[n];
    }

    /* Approach 2 - sort by start; best(i) = max(skip i, take i + best(first job starting at or after end i)). */
    public static int jobSchedulingMemo(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) order[i] = i;
        Arrays.sort(order, (a, b) -> Integer.compare(startTime[a], startTime[b]));
        int[] starts = new int[n];
        for (int i = 0; i < n; i++) starts[i] = startTime[order[i]];
        int[] best = new int[n + 1];                             // filled from the back: best[i] for jobs order[i..]
        for (int i = n - 1; i >= 0; i--) {
            int job = order[i];
            int next = lowerBound(starts, endTime[job]);
            best[i] = Math.max(best[i + 1], profit[job] + best[next]);
        }
        return best[0];
    }

    private static int lowerBound(int[] a, int key) {
        int lo = 0, hi = a.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (a[mid] < key) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }

    /** Reference for tests: TreeMap of end time -> best profit, same recurrence in a different shape. */
    static int treeMap(int[] s, int[] e, int[] p) {
        int n = s.length;
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) order[i] = i;
        Arrays.sort(order, (a, b) -> Integer.compare(e[a], e[b]));
        TreeMap<Integer, Integer> bestByEnd = new TreeMap<>();
        bestByEnd.put(0, 0);
        int best = 0;
        for (int i : order) {
            int take = bestByEnd.floorEntry(s[i]).getValue() + p[i];
            best = Math.max(best, take);
            bestByEnd.put(e[i], best);
        }
        return best;
    }
}
