package CourseSchedule;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/*
 * 207. Course Schedule — https://leetcode.com/problems/course-schedule/
 * numCourses courses; prerequisites[i] = [a, b] means take b before a. Can you finish every course?
 * Input: 2, [[1,0]] -> true · 2, [[1,0],[0,1]] -> false
 * Explanation: README.md
 */
public class CourseSchedule {
    public static void main(String[] args) {
        System.out.println(canFinish(2, new int[][]{{1, 0}}));              // true
        System.out.println(canFinishDfs(2, new int[][]{{1, 0}, {0, 1}}));   // false
    }

    /* Approach 1 - Kahn: repeatedly take courses with no remaining prerequisite. All taken iff no cycle. O(V + E). */
    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> next = graph(numCourses, prerequisites);
        int[] indegree = new int[numCourses];
        for (int[] p : prerequisites) indegree[p[0]]++;
        Queue<Integer> ready = new ArrayDeque<>();
        for (int c = 0; c < numCourses; c++) if (indegree[c] == 0) ready.add(c);
        int taken = 0;
        while (!ready.isEmpty()) {
            int c = ready.poll();
            taken++;
            for (int d : next.get(c)) if (--indegree[d] == 0) ready.add(d);
        }
        return taken == numCourses;
    }

    /* Approach 2 - DFS with colors: 0 unvisited, 1 on the current path, 2 done. Reaching a 1 means a cycle. */
    public static boolean canFinishDfs(int numCourses, int[][] prerequisites) {
        List<List<Integer>> next = graph(numCourses, prerequisites);
        int[] color = new int[numCourses];
        for (int c = 0; c < numCourses; c++) if (hasCycle(c, next, color)) return false;
        return true;
    }

    private static boolean hasCycle(int c, List<List<Integer>> next, int[] color) {
        if (color[c] != 0) return color[c] == 1;
        color[c] = 1;
        for (int d : next.get(c)) if (hasCycle(d, next, color)) return true;
        color[c] = 2;
        return false;
    }

    private static List<List<Integer>> graph(int n, int[][] prerequisites) {
        List<List<Integer>> next = new ArrayList<>();
        for (int i = 0; i < n; i++) next.add(new ArrayList<>());
        for (int[] p : prerequisites) next.get(p[1]).add(p[0]);      // edge b -> a
        return next;
    }
}
