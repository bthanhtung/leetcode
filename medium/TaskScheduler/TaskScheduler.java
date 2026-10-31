package TaskScheduler;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;

/*
 * 621. Task Scheduler — https://leetcode.com/problems/task-scheduler/
 * CPU tasks are letters A-Z; each takes one interval. Two runs of the same task need at least n intervals between
 * them (idle allowed). Return the minimum number of intervals to finish all tasks.
 * Input: ["A","A","A","B","B","B"], n = 2 -> 8 (A B idle A B idle A B) · n = 0 -> 6
 * Explanation: README.md
 */
public class TaskScheduler {
    public static void main(String[] args) {
        char[] tasks = {'A', 'A', 'A', 'B', 'B', 'B'};
        System.out.println(leastInterval(tasks, 2));             // 8
        System.out.println(leastIntervalSimulate(tasks, 0));     // 6
    }

    /*
     * Approach 1 - simulate: each interval run the task with the most remaining copies (max-heap); a task just run waits
     * in a cooldown queue until time + n + 1. Time O(total * log 26).
     */
    public static int leastIntervalSimulate(char[] tasks, int n) {
        int[] count = new int[26];
        for (char t : tasks) count[t - 'A']++;
        PriorityQueue<Integer> ready = new PriorityQueue<>(Collections.reverseOrder());
        for (int c : count) if (c > 0) ready.add(c);
        Queue<int[]> cooling = new ArrayDeque<>();               // {remaining count, time it becomes ready}
        int time = 0;
        while (!ready.isEmpty() || !cooling.isEmpty()) {
            time++;
            if (!ready.isEmpty()) {
                int left = ready.poll() - 1;
                if (left > 0) cooling.add(new int[]{left, time + n});
            }
            if (!cooling.isEmpty() && cooling.peek()[1] == time) ready.add(cooling.poll()[0]);
        }
        return time;
    }

    /*
     * Approach 2 - the most frequent task (count max, shared by k tasks) forms (max - 1) frames of length n + 1 plus a
     * last frame of k tasks. Other tasks fill the gaps; if they overflow, no idle is needed at all.
     */
    public static int leastInterval(char[] tasks, int n) {
        int[] count = new int[26];
        for (char t : tasks) count[t - 'A']++;
        int max = 0, k = 0;
        for (int c : count) {
            if (c > max) {
                max = c;
                k = 1;
            } else if (c == max) {
                k++;
            }
        }
        return Math.max(tasks.length, (max - 1) * (n + 1) + k);
    }
}
