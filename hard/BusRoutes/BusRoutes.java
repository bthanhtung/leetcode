package BusRoutes;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/*
 * 815. Bus Routes — https://leetcode.com/problems/bus-routes/
 * routes[i] is the loop of stops of bus i. Starting at stop source (not on a bus), return the least number of buses to
 * take to reach stop target, or -1.
 * Input: [[1,2,7],[3,6,7]], source = 1, target = 6 -> 2 · [[7,12],[4,5,15],[6],[15,19],[9,12,13]], 15 -> 12 -> -1
 * Explanation: README.md
 */
public class BusRoutes {
    public static void main(String[] args) {
        System.out.println(numBusesToDestination(new int[][]{{1, 2, 7}, {3, 6, 7}}, 1, 6));                      // 2
        System.out.println(numBusesToDestinationStops(new int[][]{{7, 12}, {4, 5, 15}, {6}, {15, 19}, {9, 12, 13}}, 15, 12)); // -1
    }

    private static Map<Integer, List<Integer>> busesAtStop(int[][] routes) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int b = 0; b < routes.length; b++) for (int s : routes[b]) map.computeIfAbsent(s, k -> new ArrayList<>()).add(b);
        return map;
    }

    /*
     * Approach 1 - BFS where each level is "one more bus": start with all buses through source; from a bus, every stop
     * on it gives access to the buses through that stop. Each bus and each stop is expanded once. Time O(total stops).
     */
    public static int numBusesToDestination(int[][] routes, int source, int target) {
        if (source == target) return 0;
        Map<Integer, List<Integer>> atStop = busesAtStop(routes);
        boolean[] busSeen = new boolean[routes.length];
        Set<Integer> stopSeen = new HashSet<>(List.of(source));
        Queue<Integer> queue = new ArrayDeque<>();
        for (int b : atStop.getOrDefault(source, List.of())) {
            busSeen[b] = true;
            queue.add(b);
        }
        for (int buses = 1; !queue.isEmpty(); buses++) {
            for (int size = queue.size(); size > 0; size--) {
                int bus = queue.poll();
                for (int stop : routes[bus]) {
                    if (stop == target) return buses;
                    if (!stopSeen.add(stop)) continue;
                    for (int next : atStop.get(stop)) {
                        if (!busSeen[next]) {
                            busSeen[next] = true;
                            queue.add(next);
                        }
                    }
                }
            }
        }
        return -1;
    }

    /* Approach 2 - BFS over stops; riding a bus once marks the whole route as used so it is never scanned again. */
    public static int numBusesToDestinationStops(int[][] routes, int source, int target) {
        if (source == target) return 0;
        Map<Integer, List<Integer>> atStop = busesAtStop(routes);
        Map<Integer, Integer> dist = new HashMap<>(Map.of(source, 0));
        boolean[] ridden = new boolean[routes.length];
        Queue<Integer> queue = new ArrayDeque<>(List.of(source));
        while (!queue.isEmpty()) {
            int stop = queue.poll();
            for (int bus : atStop.getOrDefault(stop, List.of())) {
                if (ridden[bus]) continue;
                ridden[bus] = true;
                for (int next : routes[bus]) {
                    if (dist.containsKey(next)) continue;
                    dist.put(next, dist.get(stop) + 1);
                    if (next == target) return dist.get(next);
                    queue.add(next);
                }
            }
        }
        return -1;
    }
}
