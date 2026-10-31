package InsertInterval;

import java.util.ArrayList;
import java.util.List;

/*
 * 57. Insert Interval — https://leetcode.com/problems/insert-interval/
 * intervals are sorted by start and non-overlapping. Insert newInterval, merging where needed; keep it sorted.
 * Input: [[1,3],[6,9]], [2,5] -> [[1,5],[6,9]] · [[1,2],[3,5],[6,7],[8,10],[12,16]], [4,8] -> [[1,2],[3,10],[12,16]]
 * Explanation: README.md
 */
public class InsertInterval {
    public static void main(String[] args) {
        int[][] r = insert(new int[][]{{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}}, new int[]{4, 8});
        System.out.println(java.util.Arrays.deepToString(r));     // [[1, 2], [3, 10], [12, 16]]
    }

    /* Copy intervals ending before the new one, merge all that overlap it, copy the rest. O(n). */
    public static int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> out = new ArrayList<>();
        int i = 0, n = intervals.length, start = newInterval[0], end = newInterval[1];
        while (i < n && intervals[i][1] < start) out.add(intervals[i++]);
        while (i < n && intervals[i][0] <= end) {
            start = Math.min(start, intervals[i][0]);
            end = Math.max(end, intervals[i][1]);
            i++;
        }
        out.add(new int[]{start, end});
        while (i < n) out.add(intervals[i++]);
        return out.toArray(new int[0][]);
    }
}
