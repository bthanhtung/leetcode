package PacificAtlanticWaterFlow;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/*
 * 417. Pacific Atlantic Water Flow — https://leetcode.com/problems/pacific-atlantic-water-flow/
 * heights is an island; the Pacific touches the top and left edges, the Atlantic the bottom and right edges. Water flows
 * to neighbours of equal or lower height. Return the cells from which water can reach both oceans.
 * Input: [[1,2,2,3,5],[3,2,3,4,4],[2,4,5,3,1],[6,7,1,4,5],[5,1,1,2,4]]
 *     -> [[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]
 * Explanation: README.md
 */
public class PacificAtlanticWaterFlow {
    public static void main(String[] args) {
        int[][] h = {{1, 2, 2, 3, 5}, {3, 2, 3, 4, 4}, {2, 4, 5, 3, 1}, {6, 7, 1, 4, 5}, {5, 1, 1, 2, 4}};
        System.out.println(pacificAtlantic(h));
    }

    /*
     * Approach 1 - think backwards: from each ocean's border, climb to neighbours that are equal or higher. A cell reached
     * from both oceans drains into both. Time O(m * n).
     */
    public static List<List<Integer>> pacificAtlantic(int[][] heights) {
        int m = heights.length, n = heights[0].length;
        boolean[][] pacific = new boolean[m][n], atlantic = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            climb(heights, i, 0, pacific);
            climb(heights, i, n - 1, atlantic);
        }
        for (int j = 0; j < n; j++) {
            climb(heights, 0, j, pacific);
            climb(heights, m - 1, j, atlantic);
        }
        return both(pacific, atlantic);
    }

    private static void climb(int[][] h, int i, int j, boolean[][] reach) {
        if (reach[i][j]) return;
        reach[i][j] = true;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] d : dirs) {
            int r = i + d[0], c = j + d[1];
            if (r >= 0 && r < h.length && c >= 0 && c < h[0].length && h[r][c] >= h[i][j]) climb(h, r, c, reach);
        }
    }

    /* Approach 2 - the same reverse search as a multi-source BFS starting from every border cell of an ocean. */
    public static List<List<Integer>> pacificAtlanticBfs(int[][] heights) {
        int m = heights.length, n = heights[0].length;
        Queue<int[]> pq = new ArrayDeque<>(), aq = new ArrayDeque<>();
        for (int i = 0; i < m; i++) {
            pq.add(new int[]{i, 0});
            aq.add(new int[]{i, n - 1});
        }
        for (int j = 0; j < n; j++) {
            pq.add(new int[]{0, j});
            aq.add(new int[]{m - 1, j});
        }
        return both(bfs(heights, pq), bfs(heights, aq));
    }

    private static boolean[][] bfs(int[][] h, Queue<int[]> queue) {
        int m = h.length, n = h[0].length;
        boolean[][] reach = new boolean[m][n];
        for (int[] c : queue) reach[c[0]][c[1]] = true;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        while (!queue.isEmpty()) {
            int[] c = queue.poll();
            for (int[] d : dirs) {
                int r = c[0] + d[0], col = c[1] + d[1];
                if (r >= 0 && r < m && col >= 0 && col < n && !reach[r][col] && h[r][col] >= h[c[0]][c[1]]) {
                    reach[r][col] = true;
                    queue.add(new int[]{r, col});
                }
            }
        }
        return reach;
    }

    private static List<List<Integer>> both(boolean[][] a, boolean[][] b) {
        List<List<Integer>> out = new ArrayList<>();
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < a[0].length; j++)
                if (a[i][j] && b[i][j]) out.add(List.of(i, j));
        return out;
    }
}
