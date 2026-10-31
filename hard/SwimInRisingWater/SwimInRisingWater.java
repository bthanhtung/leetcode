package SwimInRisingWater;

import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;

/*
 * 778. Swim in Rising Water — https://leetcode.com/problems/swim-in-rising-water/
 * grid[i][j] is an elevation (a permutation of 0..n²-1). At time t the water is at depth t and you can swim between
 * adjacent squares whose elevations are both <= t. Return the least time to go from (0,0) to (n-1,n-1).
 * Input: [[0,2],[1,3]] -> 3 · the 5x5 spiral example -> 16
 * Explanation: README.md
 */
public class SwimInRisingWater {
    private static final int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public static void main(String[] args) {
        System.out.println(swimInWater(new int[][]{{0, 2}, {1, 3}}));          // 3
    }

    /* Approach 1 - Dijkstra where a path's cost is its highest cell; always expand the lowest frontier cell. O(n² log n). */
    public static int swimInWater(int[][] grid) {
        int n = grid.length;
        boolean[][] seen = new boolean[n][n];
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));    // {time, r, c}
        heap.add(new int[]{grid[0][0], 0, 0});
        seen[0][0] = true;
        while (true) {
            int[] cur = heap.poll();
            if (cur[1] == n - 1 && cur[2] == n - 1) return cur[0];
            for (int[] d : DIRS) {
                int r = cur[1] + d[0], c = cur[2] + d[1];
                if (r < 0 || r >= n || c < 0 || c >= n || seen[r][c]) continue;
                seen[r][c] = true;
                heap.add(new int[]{Math.max(cur[0], grid[r][c]), r, c});
            }
        }
    }

    /* Approach 2 - binary search the answer t; check reachability using only cells <= t with BFS. O(n² log n²). */
    public static int swimInWaterBinarySearch(int[][] grid) {
        int n = grid.length, lo = grid[0][0], hi = n * n - 1;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (reachable(grid, mid)) hi = mid;
            else lo = mid + 1;
        }
        return lo;
    }

    private static boolean reachable(int[][] grid, int t) {
        int n = grid.length;
        if (grid[0][0] > t) return false;
        boolean[][] seen = new boolean[n][n];
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{0, 0});
        seen[0][0] = true;
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            if (cur[0] == n - 1 && cur[1] == n - 1) return true;
            for (int[] d : DIRS) {
                int r = cur[0] + d[0], c = cur[1] + d[1];
                if (r >= 0 && r < n && c >= 0 && c < n && !seen[r][c] && grid[r][c] <= t) {
                    seen[r][c] = true;
                    queue.add(new int[]{r, c});
                }
            }
        }
        return false;
    }

    /* Approach 3 - raise the water one level at a time, uniting the newly flooded cell with flooded neighbours. O(n² α). */
    public static int swimInWaterUnionFind(int[][] grid) {
        int n = grid.length;
        int[][] where = new int[n * n][];
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) where[grid[i][j]] = new int[]{i, j};
        int[] parent = new int[n * n];
        for (int i = 0; i < n * n; i++) parent[i] = i;
        for (int t = 0; t < n * n; t++) {
            int r = where[t][0], c = where[t][1];
            for (int[] d : DIRS) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < n && nc >= 0 && nc < n && grid[nr][nc] <= t) {
                    parent[find(parent, r * n + c)] = find(parent, nr * n + nc);
                }
            }
            if (find(parent, 0) == find(parent, n * n - 1)) return t;
        }
        return n * n - 1;
    }

    private static int find(int[] p, int x) {
        while (p[x] != x) {
            p[x] = p[p[x]];
            x = p[x];
        }
        return x;
    }
}
