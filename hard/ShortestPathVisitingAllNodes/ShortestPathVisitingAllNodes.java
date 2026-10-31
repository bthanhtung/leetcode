package ShortestPathVisitingAllNodes;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/*
 * 847. Shortest Path Visiting All Nodes — https://leetcode.com/problems/shortest-path-visiting-all-nodes/
 * Undirected connected graph with n <= 12 nodes. Return the length of the shortest walk that visits every node.
 * You may start and stop anywhere, revisit nodes and reuse edges.
 * Input: [[1,2,3],[0],[0],[0]] -> 4 · [[1],[0,2,4],[1,3,4],[2],[1,2]] -> 4
 * Explanation: README.md
 */
public class ShortestPathVisitingAllNodes {
    public static void main(String[] args) {
        System.out.println(shortestPathLength(new int[][]{{1, 2, 3}, {0}, {0}, {0}}));               // 4
        System.out.println(shortestPathLengthDp(new int[][]{{1}, {0, 2, 4}, {1, 3, 4}, {2}, {1, 2}})); // 4
    }

    /*
     * Approach 1 - BFS on states (current node, set of visited nodes), starting from every node at once.
     * The first state whose set is full gives the answer. States: n * 2^n. Time O(2^n * n^2).
     */
    public static int shortestPathLength(int[][] graph) {
        int n = graph.length, full = (1 << n) - 1;
        if (n == 1) return 0;
        boolean[][] seen = new boolean[n][1 << n];
        Queue<int[]> queue = new ArrayDeque<>();
        for (int v = 0; v < n; v++) {
            queue.add(new int[]{v, 1 << v});
            seen[v][1 << v] = true;
        }
        for (int steps = 1; ; steps++) {
            for (int size = queue.size(); size > 0; size--) {
                int[] s = queue.poll();
                for (int next : graph[s[0]]) {
                    int mask = s[1] | 1 << next;
                    if (mask == full) return steps;
                    if (!seen[next][mask]) {
                        seen[next][mask] = true;
                        queue.add(new int[]{next, mask});
                    }
                }
            }
        }
    }

    /*
     * Approach 2 - all-pairs shortest distances (Floyd-Warshall), then the travelling-salesman DP:
     * dp[mask][v] = shortest walk that visits exactly the nodes of mask and ends at v. Time O(2^n * n^2).
     */
    public static int shortestPathLengthDp(int[][] graph) {
        int n = graph.length, inf = Integer.MAX_VALUE / 2;
        int[][] dist = new int[n][n];
        for (int[] row : dist) Arrays.fill(row, inf);
        for (int v = 0; v < n; v++) {
            dist[v][v] = 0;
            for (int u : graph[v]) dist[v][u] = 1;
        }
        for (int k = 0; k < n; k++)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
        int[][] dp = new int[1 << n][n];
        for (int[] row : dp) Arrays.fill(row, inf);
        for (int v = 0; v < n; v++) dp[1 << v][v] = 0;
        for (int mask = 1; mask < 1 << n; mask++) {
            for (int v = 0; v < n; v++) {
                if (dp[mask][v] >= inf || (mask >> v & 1) == 0) continue;
                for (int u = 0; u < n; u++) {
                    if ((mask >> u & 1) == 1) continue;
                    int next = mask | 1 << u;
                    dp[next][u] = Math.min(dp[next][u], dp[mask][v] + dist[v][u]);
                }
            }
        }
        int best = inf;
        for (int v = 0; v < n; v++) best = Math.min(best, dp[(1 << n) - 1][v]);
        return best;
    }
}
