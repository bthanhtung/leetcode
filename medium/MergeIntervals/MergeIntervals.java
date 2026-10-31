package MergeIntervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * 56. Merge Intervals — https://leetcode.com/problems/merge-intervals/
 * Merge all overlapping intervals and return the non-overlapping intervals that cover the input. Touching intervals
 * ([1,4] and [4,5]) are merged.
 * Input: [[1,3],[2,6],[8,10],[15,18]] -> [[1,6],[8,10],[15,18]] · [[1,4],[4,5]] -> [[1,5]]
 * Explanation: README.md
 */
public class MergeIntervals {
    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(merge(new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}})));     // [[1, 6], [8, 10], [15, 18]]
        System.out.println(Arrays.deepToString(mergeStartsEnds(new int[][]{{1, 4}, {4, 5}})));             // [[1, 5]]
    }

    /* Approach 1 - sort by start; extend the last merged interval while the next one starts inside it. O(n log n). */
    public static int[][] merge(int[][] intervals) {
        int[][] a = intervals.clone();
        Arrays.sort(a, (x, y) -> Integer.compare(x[0], y[0]));
        List<int[]> out = new ArrayList<>();
        for (int[] in : a) {
            if (out.isEmpty() || out.getLast()[1] < in[0]) {
                out.add(new int[]{in[0], in[1]});
            } else {
                out.getLast()[1] = Math.max(out.getLast()[1], in[1]);
            }
        }
        return out.toArray(new int[0][]);
    }

    /*
     * Approach 2 - sort starts and ends independently. A merged block ends at ends[i] when the next start is beyond it:
     * starts[i + 1] > ends[i]. Time O(n log n), and simple loops on primitive arrays.
     */
    public static int[][] mergeStartsEnds(int[][] intervals) {
        int n = intervals.length;
        int[] starts = new int[n], ends = new int[n];
        for (int i = 0; i < n; i++) {
            starts[i] = intervals[i][0];
            ends[i] = intervals[i][1];
        }
        Arrays.sort(starts);
        Arrays.sort(ends);
        List<int[]> out = new ArrayList<>();
        for (int i = 0, blockStart = 0; i < n; i++) {
            if (i == n - 1 || starts[i + 1] > ends[i]) {
                out.add(new int[]{starts[blockStart], ends[i]});
                blockStart = i + 1;
            }
        }
        return out.toArray(new int[0][]);
    }
}
