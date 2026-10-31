package CourseScheduleII;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/*
 * 210. Course Schedule II — https://leetcode.com/problems/course-schedule-ii/
 * numCourses courses 0..n-1; prerequisites[i] = [a, b] means b must be taken before a. Return an order to take all
 * courses, or an empty array if impossible (cycle).
 * Input: 2, [[1,0]] -> [0,1] · 4, [[1,0],[2,0],[3,1],[3,2]] -> [0,1,2,3] or [0,2,1,3] · 2, [[1,0],[0,1]] -> []
 * Explanation: README.md
 */
public class CourseScheduleII {
    public static void main(String[] args) {
        System.out.println(java.util.Arrays.toString(findOrder(4, new int[][]{{1, 0}, {2, 0}, {3, 1}, {3, 2}})));   // [0, 1, 2, 3]
        System.out.println(java.util.Arrays.toString(findOrderDfs(2, new int[][]{{1, 0}, {0, 1}})));             // []
    }

    /* Approach 1 - Kahn: repeatedly take a course with no remaining prerequisites. Time O(V + E). */
    public static int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> next = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) next.add(new ArrayList<>());
        int[] indegree = new int[numCourses];
        for (int[] p : prerequisites) {
            next.get(p[1]).add(p[0]);
            indegree[p[0]]++;
        }
        Queue<Integer> ready = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++) if (indegree[i] == 0) ready.add(i);
        int[] order = new int[numCourses];
        int k = 0;
        while (!ready.isEmpty()) {
            int c = ready.poll();
            order[k++] = c;
            for (int d : next.get(c)) {
                if (--indegree[d] == 0) ready.add(d);
            }
        }
        return k == numCourses ? order : new int[0];            // leftover courses sit on a cycle
    }

    /*
     * Approach 2 - DFS; a course is finished after all courses that depend on it are finished, so the reverse finish
     * order is a valid schedule. A back edge to a node still "in progress" means a cycle. Time O(V + E).
     */
    public static int[] findOrderDfs(int numCourses, int[][] prerequisites) {
        List<List<Integer>> next = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) next.add(new ArrayList<>());
        for (int[] p : prerequisites) next.get(p[1]).add(p[0]);
        int[] state = new int[numCourses];                     // 0 = new, 1 = in progress, 2 = done
        int[] order = new int[numCourses];
        int[] k = {numCourses - 1};
        for (int i = 0; i < numCourses; i++) {
            if (state[i] == 0 && !dfs(i, next, state, order, k)) return new int[0];
        }
        return order;
    }

    private static boolean dfs(int c, List<List<Integer>> next, int[] state, int[] order, int[] k) {
        state[c] = 1;
        for (int d : next.get(c)) {
            if (state[d] == 1) return false;
            if (state[d] == 0 && !dfs(d, next, state, order, k)) return false;
        }
        state[c] = 2;
        order[k[0]--] = c;                                      // fill from the back: reverse postorder
        return true;
    }
}
