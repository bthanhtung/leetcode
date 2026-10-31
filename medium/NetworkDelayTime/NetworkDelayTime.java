package NetworkDelayTime;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/*
 * 743. Network Delay Time — https://leetcode.com/problems/network-delay-time/
 * times[i] = (u, v, w): a signal from u reaches v after w. Send a signal from k; return the time until all n nodes
 * receive it, or -1 if some never do.
 * Input: [[2,1,1],[2,3,1],[3,4,1]], n = 4, k = 2 -> 2 · [[1,2,1]], n = 2, k = 2 -> -1
 * Explanation: README.md
 */
public class NetworkDelayTime {
    public static void main(String[] args) {
        System.out.println(networkDelayTime(new int[][]{{2, 1, 1}, {2, 3, 1}, {3, 4, 1}}, 4, 2));     // 2
        System.out.println(networkDelayTimeBellmanFord(new int[][]{{1, 2, 1}}, 2, 2));             // -1
    }

    /* Approach 1 - Dijkstra: always settle the closest unsettled node next. Time O(E log E). */
    public static int networkDelayTime(int[][] times, int n, int k) {
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) graph.add(new ArrayList<>());
        for (int[] t : times) graph.get(t[0]).add(new int[]{t[1], t[2]});
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        heap.add(new int[]{k, 0});
        int settled = 0, last = 0;
        while (!heap.isEmpty()) {
            int[] top = heap.poll();
            int node = top[0], d = top[1];
            if (dist[node] != Integer.MAX_VALUE) continue;      // already settled with a shorter distance
            dist[node] = d;
            settled++;
            last = d;
            for (int[] e : graph.get(node)) {
                if (dist[e[0]] == Integer.MAX_VALUE) heap.add(new int[]{e[0], d + e[1]});
            }
        }
        return settled == n ? last : -1;
    }

    /* Approach 2 - Bellman-Ford: relax every edge n - 1 times. Time O(n * E). Also works with negative weights. */
    public static int networkDelayTimeBellmanFord(int[][] times, int n, int k) {
        long[] dist = new long[n + 1];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[k] = 0;
        for (int round = 1; round < n; round++) {
            boolean changed = false;
            for (int[] t : times) {
                if (dist[t[0]] != Long.MAX_VALUE && dist[t[0]] + t[2] < dist[t[1]]) {
                    dist[t[1]] = dist[t[0]] + t[2];
                    changed = true;
                }
            }
            if (!changed) break;
        }
        long max = 0;
        for (int i = 1; i <= n; i++) max = Math.max(max, dist[i]);
        return max == Long.MAX_VALUE ? -1 : (int) max;
    }

    /* Approach 3 - SPFA: queue-based Bellman-Ford, only re-relax edges out of nodes whose distance just improved. */
    public static int networkDelayTimeSpfa(int[][] times, int n, int k) {
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) graph.add(new ArrayList<>());
        for (int[] t : times) graph.get(t[0]).add(new int[]{t[1], t[2]});
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0;
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(k);
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int[] e : graph.get(u)) {
                if (dist[u] + e[1] < dist[e[0]]) {
                    dist[e[0]] = dist[u] + e[1];
                    queue.add(e[0]);
                }
            }
        }
        int max = 0;
        for (int i = 1; i <= n; i++) max = Math.max(max, dist[i]);
        return max == Integer.MAX_VALUE ? -1 : max;
    }
}
