package ParallelCoursesIII;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/*
 * 2050. Parallel Courses III — https://leetcode.com/problems/parallel-courses-iii/
 * n courses, time[i] months for course i + 1, relations [prev, next] (prev before next). Any number of courses may run
 * at once. Return the minimum months to finish all courses (the graph is a DAG).
 * Input: n = 3, [[1,3],[2,3]], time = [3,2,5] -> 8 · n = 5, [[1,5],[2,5],[3,5],[3,4],[4,5]], time = [1,2,3,4,5] -> 12
 * Explanation: README.md
 */
public class ParallelCoursesIII {
    public static void main(String[] args) {
        System.out.println(minimumTime(3, new int[][]{{1, 3}, {2, 3}}, new int[]{3, 2, 5}));                               // 8
        System.out.println(minimumTimeDfs(5, new int[][]{{1, 5}, {2, 5}, {3, 5}, {3, 4}, {4, 5}}, new int[]{1, 2, 3, 4, 5})); // 12
    }

    /*
     * Approach 1 - Kahn's topological sort carrying finish[c] = time[c] + latest finish among its prerequisites.
     * The answer is the largest finish time (the critical path). Time O(V + E).
     */
    public static int minimumTime(int n, int[][] relations, int[] time) {
        List<List<Integer>> next = new ArrayList<>();
        for (int i = 0; i <= n; i++) next.add(new ArrayList<>());
        int[] indegree = new int[n + 1];
        for (int[] r : relations) {
            next.get(r[0]).add(r[1]);
            indegree[r[1]]++;
        }
        int[] start = new int[n + 1];                         // earliest start = latest prerequisite finish
        Queue<Integer> ready = new ArrayDeque<>();
        for (int c = 1; c <= n; c++) if (indegree[c] == 0) ready.add(c);
        int answer = 0;
        while (!ready.isEmpty()) {
            int c = ready.poll();
            int finish = start[c] + time[c - 1];
            answer = Math.max(answer, finish);
            for (int d : next.get(c)) {
                start[d] = Math.max(start[d], finish);
                if (--indegree[d] == 0) ready.add(d);
            }
        }
        return answer;
    }

    /* Approach 2 - finish(c) = time[c] + max finish(prerequisite), computed by DFS on the reversed graph with memo. */
    public static int minimumTimeDfs(int n, int[][] relations, int[] time) {
        List<List<Integer>> prereq = new ArrayList<>();
        for (int i = 0; i <= n; i++) prereq.add(new ArrayList<>());
        for (int[] r : relations) prereq.get(r[1]).add(r[0]);
        int[] memo = new int[n + 1];
        int answer = 0;
        for (int c = 1; c <= n; c++) answer = Math.max(answer, finish(c, prereq, time, memo));
        return answer;
    }

    private static int finish(int c, List<List<Integer>> prereq, int[] time, int[] memo) {
        if (memo[c] > 0) return memo[c];
        int start = 0;
        for (int p : prereq.get(c)) start = Math.max(start, finish(p, prereq, time, memo));
        return memo[c] = start + time[c - 1];
    }
}
