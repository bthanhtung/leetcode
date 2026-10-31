package CourseScheduleIII;

import java.util.Arrays;
import java.util.PriorityQueue;

/*
 * 630. Course Schedule III — https://leetcode.com/problems/course-schedule-iii/
 * courses[i] = [duration, lastDay]: a course takes duration days and must finish by lastDay. Starting on day 1,
 * take courses one at a time. Return the maximum number of courses you can take.
 * Input: [[100,200],[200,1300],[1000,1250],[2000,3200]] -> 3 · [[1,2]] -> 1 · [[3,2],[4,3]] -> 0
 * Explanation: README.md
 */
public class CourseScheduleIII {
    public static void main(String[] args) {
        System.out.println(scheduleCourse(new int[][]{{100, 200}, {200, 1300}, {1000, 1250}, {2000, 3200}}));    // 3
        System.out.println(scheduleCourseDp(new int[][]{{3, 2}, {4, 3}}));                                     // 0
    }

    /*
     * Approach 1 - greedy: process courses by deadline, always add the course; if time now exceeds its deadline, drop the
     * longest course taken so far (max-heap). Dropping the longest frees the most time while losing only one course.
     * Time O(n log n).
     */
    public static int scheduleCourse(int[][] courses) {
        int[][] cs = courses.clone();
        Arrays.sort(cs, (a, b) -> Integer.compare(a[1], b[1]));
        PriorityQueue<Integer> taken = new PriorityQueue<>((a, b) -> b - a);
        int time = 0;
        for (int[] c : cs) {
            time += c[0];
            taken.add(c[0]);
            if (time > c[1]) time -= taken.poll();
        }
        return taken.size();
    }

    /* Approach 2 - DP over end time: dp[t] = most courses finishing exactly by day t, knapsack-style by deadline. */
    public static int scheduleCourseDp(int[][] courses) {
        int[][] cs = courses.clone();
        Arrays.sort(cs, (a, b) -> Integer.compare(a[1], b[1]));
        int maxDay = 0;
        for (int[] c : cs) maxDay = Math.max(maxDay, c[1]);
        int[] dp = new int[maxDay + 1];                          // dp[t] = most courses using exactly t days (or -1)
        Arrays.fill(dp, -1);
        dp[0] = 0;
        for (int[] c : cs) {
            for (int t = c[1]; t >= c[0]; t--) {
                if (dp[t - c[0]] >= 0) dp[t] = Math.max(dp[t], dp[t - c[0]] + 1);
            }
        }
        int best = 0;
        for (int v : dp) best = Math.max(best, v);
        return best;
    }
}
