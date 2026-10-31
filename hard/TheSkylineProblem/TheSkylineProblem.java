package TheSkylineProblem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;

/*
 * 218. The Skyline Problem — https://leetcode.com/problems/the-skyline-problem/
 * buildings[i] = [left, right, height]. Return the skyline: key points [x, height] where the outline's height changes,
 * sorted by x, without consecutive points of equal height.
 * Input: [[2,9,10],[3,7,15],[5,12,12],[15,20,10],[19,24,8]] -> [[2,10],[3,15],[7,12],[12,0],[15,10],[20,8],[24,0]]
 * Explanation: README.md
 */
public class TheSkylineProblem {
    public static void main(String[] args) {
        int[][] b = {{2, 9, 10}, {3, 7, 15}, {5, 12, 12}, {15, 20, 10}, {19, 24, 8}};
        System.out.println(getSkyline(b));
    }

    /*
     * Approach 1 - visit every distinct x (all building edges). Push buildings starting at or before x into a max-heap
     * by height; lazily drop heap tops that already ended. The heap top is the outline height just right of x. O(n log n).
     */
    public static List<List<Integer>> getSkyline(int[][] buildings) {
        List<Integer> xs = new ArrayList<>();
        for (int[] b : buildings) {
            xs.add(b[0]);
            xs.add(b[1]);
        }
        Collections.sort(xs);
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> b[0] - a[0]);       // {height, right}
        List<List<Integer>> out = new ArrayList<>();
        int i = 0, n = buildings.length;                                              // buildings arrive sorted by left
        for (int idx = 0; idx < xs.size(); idx++) {
            int x = xs.get(idx);
            if (idx > 0 && x == xs.get(idx - 1)) continue;
            while (i < n && buildings[i][0] <= x) {
                heap.add(new int[]{buildings[i][2], buildings[i][1]});
                i++;
            }
            while (!heap.isEmpty() && heap.peek()[1] <= x) heap.poll();             // ended at or before x
            int h = heap.isEmpty() ? 0 : heap.peek()[0];
            if (out.isEmpty() || out.getLast().get(1) != h) out.add(List.of(x, h));
        }
        return out;
    }

    /*
     * Approach 2 - events (x, -height) for starts and (x, height) for ends, sorted so starts come before ends and taller
     * starts first; a TreeMap counts active heights. Emit a point whenever the maximum height changes. O(n log n).
     */
    public static List<List<Integer>> getSkylineTreeMap(int[][] buildings) {
        List<int[]> events = new ArrayList<>();
        for (int[] b : buildings) {
            events.add(new int[]{b[0], -b[2]});
            events.add(new int[]{b[1], b[2]});
        }
        events.sort((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        TreeMap<Integer, Integer> active = new TreeMap<>();
        active.put(0, 1);
        List<List<Integer>> out = new ArrayList<>();
        int prev = 0;
        for (int[] e : events) {
            if (e[1] < 0) active.merge(-e[1], 1, Integer::sum);
            else if (active.merge(e[1], -1, Integer::sum) == 0) active.remove(e[1]);
            int cur = active.lastKey();
            if (cur != prev) {
                out.add(List.of(e[0], cur));
                prev = cur;
            }
        }
        return out;
    }
}
