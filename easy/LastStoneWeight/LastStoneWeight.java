package LastStoneWeight;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

/*
 * 1046. Last Stone Weight — https://leetcode.com/problems/last-stone-weight/
 * Each turn smash the two heaviest stones x <= y: equal -> both destroyed, otherwise one stone of weight y - x remains.
 * Return the weight of the last stone, or 0. Weights are 1..1000.
 * Input: [2,7,4,1,8,1] -> 1 · [1] -> 1
 * Explanation: README.md
 */
public class LastStoneWeight {
    public static void main(String[] args) {
        System.out.println(lastStoneWeight(new int[]{2, 7, 4, 1, 8, 1}));       // 1
        System.out.println(lastStoneWeightCounting(new int[]{1}));             // 1
    }

    /* Approach 1 - sort, smash the last two, repeat. Time O(n^2 log n). */
    public static int lastStoneWeightSort(int[] stones) {
        int[] a = stones.clone();
        int n = a.length;
        while (n > 1) {
            Arrays.sort(a, 0, n);
            int y = a[n - 1], x = a[n - 2];
            n -= 2;
            if (y != x) a[n++] = y - x;
        }
        return n == 0 ? 0 : a[0];
    }

    /* Approach 2 - max-heap: poll the two largest, push the difference. Time O(n log n). */
    public static int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> heap = new PriorityQueue<>(Collections.reverseOrder());
        for (int s : stones) heap.add(s);
        while (heap.size() > 1) {
            int y = heap.poll(), x = heap.poll();
            if (y != x) heap.add(y - x);
        }
        return heap.isEmpty() ? 0 : heap.peek();
    }

    /* Approach 3 - weights are small: bucket counts, walk from the heaviest weight down. Time O(n + W). */
    public static int lastStoneWeightCounting(int[] stones) {
        int[] count = new int[1001];
        for (int s : stones) count[s]++;
        int w = 1000;
        while (true) {
            while (w > 0 && count[w] == 0) w--;
            if (w == 0) return 0;
            if (count[w] >= 2) {
                count[w] -= 2;                       // two equal heaviest stones destroy each other
                continue;
            }
            int x = w - 1;
            while (x > 0 && count[x] == 0) x--;
            if (x == 0) return w;                    // the heaviest stone is the only one left
            count[w]--;
            count[x]--;
            count[w - x]++;                          // w - x < w, so the walk never needs to go back up
        }
    }
}
