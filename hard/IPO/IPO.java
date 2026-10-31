package IPO;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

/*
 * 502. IPO — https://leetcode.com/problems/ipo/
 * Start with capital w; pick at most k distinct projects. Project i needs capital[i] to start and adds profits[i] to
 * your capital when done. Return the maximum final capital.
 * Input: k = 2, w = 0, profits = [1,2,3], capital = [0,1,1] -> 4 · k = 3, w = 0, profits = [1,2,3], capital = [0,1,2] -> 6
 * Explanation: README.md
 */
public class IPO {
    public static void main(String[] args) {
        System.out.println(findMaximizedCapital(2, 0, new int[]{1, 2, 3}, new int[]{0, 1, 1}));       // 4
        System.out.println(findMaximizedCapitalBrute(3, 0, new int[]{1, 2, 3}, new int[]{0, 1, 2}));  // 6
    }

    /*
     * Approach 1 - greedy: each round, among the projects you can afford, do the most profitable one (profit only
     * increases capital, so taking the biggest never hurts later). Projects are sorted by capital and moved into a
     * max-heap of profits as soon as they become affordable. Time O(n log n + k log n).
     */
    public static int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        int n = profits.length;
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) order[i] = i;
        Arrays.sort(order, (a, b) -> Integer.compare(capital[a], capital[b]));
        PriorityQueue<Integer> affordable = new PriorityQueue<>(Collections.reverseOrder());
        int next = 0;
        for (int round = 0; round < k; round++) {
            while (next < n && capital[order[next]] <= w) affordable.add(profits[order[next++]]);
            if (affordable.isEmpty()) break;
            w += affordable.poll();
        }
        return w;
    }

    /* Approach 2 - the same greedy without data structures: scan all projects each round. Time O(k * n). */
    public static int findMaximizedCapitalBrute(int k, int w, int[] profits, int[] capital) {
        boolean[] done = new boolean[profits.length];
        for (int round = 0; round < k; round++) {
            int best = -1;
            for (int i = 0; i < profits.length; i++) {
                if (!done[i] && capital[i] <= w && (best < 0 || profits[i] > profits[best])) best = i;
            }
            if (best < 0) break;
            done[best] = true;
            w += profits[best];
        }
        return w;
    }
}
