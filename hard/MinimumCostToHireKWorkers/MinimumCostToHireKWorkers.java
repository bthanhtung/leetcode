package MinimumCostToHireKWorkers;

import java.util.Arrays;
import java.util.PriorityQueue;

/*
 * 857. Minimum Cost to Hire K Workers — https://leetcode.com/problems/minimum-cost-to-hire-k-workers/
 * Hire exactly k workers. Each paid worker gets at least wage[i], and pay must be proportional to quality within the
 * group. Return the least total money needed.
 * Input: quality = [10,20,5], wage = [70,50,30], k = 2 -> 105.0 · quality = [3,1,10,10,1], wage = [4,8,2,2,7], k = 3 -> 30.66667
 * Explanation: README.md
 */
public class MinimumCostToHireKWorkers {
    public static void main(String[] args) {
        System.out.println(mincostToHireWorkers(new int[]{10, 20, 5}, new int[]{70, 50, 30}, 2));                 // 105.0
        System.out.println(mincostToHireWorkersBrute(new int[]{3, 1, 10, 10, 1}, new int[]{4, 8, 2, 2, 7}, 3));    // 30.666...
    }

    /*
     * Approach 1 - in any group, the pay per unit of quality is set by the worker with the highest wage/quality ratio
     * (the "captain"). Try each worker as captain, take the k - 1 cheapest-quality workers with a lower ratio. O(n^2 log n).
     */
    public static double mincostToHireWorkersBrute(int[] quality, int[] wage, int k) {
        int n = quality.length;
        double best = Double.MAX_VALUE;
        for (int captain = 0; captain < n; captain++) {
            double ratio = (double) wage[captain] / quality[captain];
            int[] others = new int[n];
            int m = 0;
            for (int i = 0; i < n; i++) {
                if (i != captain && (double) wage[i] / quality[i] <= ratio) others[m++] = quality[i];
            }
            if (m < k - 1) continue;
            int[] q = Arrays.copyOf(others, m);
            Arrays.sort(q);
            long sum = quality[captain];
            for (int i = 0; i < k - 1; i++) sum += q[i];
            best = Math.min(best, sum * ratio);
        }
        return best;
    }

    /*
     * Approach 2 - sort workers by ratio. Walking in that order, the current worker is the captain of any group drawn
     * from the workers seen so far; keep the k smallest qualities in a max-heap with their running sum. O(n log n).
     */
    public static double mincostToHireWorkers(int[] quality, int[] wage, int k) {
        int n = quality.length;
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) order[i] = i;
        Arrays.sort(order, (a, b) -> Double.compare((double) wage[a] / quality[a], (double) wage[b] / quality[b]));
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        long qualitySum = 0;
        double best = Double.MAX_VALUE;
        for (int i : order) {
            maxHeap.add(quality[i]);
            qualitySum += quality[i];
            if (maxHeap.size() > k) qualitySum -= maxHeap.poll();      // drop the largest quality
            if (maxHeap.size() == k) best = Math.min(best, qualitySum * (double) wage[i] / quality[i]);
        }
        return best;
    }
}
