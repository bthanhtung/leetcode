package MinimumNumberOfRefuelingStops;

import java.util.PriorityQueue;

/*
 * 871. Minimum Number of Refueling Stops — https://leetcode.com/problems/minimum-number-of-refueling-stops/
 * A car starts with startFuel litres and burns one litre per mile. stations[i] = [position, fuel]. Return the minimum
 * number of refuelling stops to reach target, or -1.
 * Input: target = 100, startFuel = 10, [[10,60],[20,30],[30,30],[60,40]] -> 2 · target = 1, startFuel = 1, [] -> 0
 * Explanation: README.md
 */
public class MinimumNumberOfRefuelingStops {
    public static void main(String[] args) {
        int[][] s = {{10, 60}, {20, 30}, {30, 30}, {60, 40}};
        System.out.println(minRefuelStops(100, 10, s));        // 2
        System.out.println(minRefuelStopsDp(100, 10, s));      // 2
    }

    /*
     * Approach 1 - drive as far as the fuel allows, remembering every station passed in a max-heap. When stuck,
     * pretend we had stopped at the biggest station passed so far (a "retroactive" stop). Time O(n log n).
     */
    public static int minRefuelStops(int target, int startFuel, int[][] stations) {
        PriorityQueue<Integer> passed = new PriorityQueue<>((a, b) -> b - a);
        long reach = startFuel;
        int stops = 0, i = 0;
        while (reach < target) {
            while (i < stations.length && stations[i][0] <= reach) passed.add(stations[i++][1]);
            if (passed.isEmpty()) return -1;
            reach += passed.poll();
            stops++;
        }
        return stops;
    }

    /* Approach 2 - dp[s] = farthest reachable distance using s stops; each station can raise dp[s+1] from dp[s]. O(n^2). */
    public static int minRefuelStopsDp(int target, int startFuel, int[][] stations) {
        int n = stations.length;
        long[] dp = new long[n + 1];
        dp[0] = startFuel;
        for (int i = 0; i < n; i++) {
            for (int s = i; s >= 0; s--) {                       // backwards: each station used at most once
                if (dp[s] >= stations[i][0]) dp[s + 1] = Math.max(dp[s + 1], dp[s] + stations[i][1]);
            }
        }
        for (int s = 0; s <= n; s++) if (dp[s] >= target) return s;
        return -1;
    }
}
