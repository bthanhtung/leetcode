package DataStreamAsDisjointIntervals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/*
 * 352. Data Stream as Disjoint Intervals — https://leetcode.com/problems/data-stream-as-disjoint-intervals/
 * SummaryRanges: addNum(value) adds a non-negative integer from a stream; getIntervals() summarizes the numbers seen
 * so far as sorted disjoint intervals.
 * Example: add 1 -> [[1,1]]; add 3 -> [[1,1],[3,3]]; add 7; add 2 -> [[1,3],[7,7]]; add 6 -> [[1,3],[6,7]]
 * Explanation: README.md
 */
public class DataStreamAsDisjointIntervals {
    public static void main(String[] args) {
        SummaryRanges s = new SummaryRanges();
        for (int v : new int[]{1, 3, 7, 2, 6}) s.addNum(v);
        for (int[] in : s.getIntervals()) System.out.print(java.util.Arrays.toString(in) + " ");   // [1, 3] [6, 7]
    }

    /*
     * Approach 1 - keep the intervals themselves in a TreeMap start -> end. A new value either falls inside the interval
     * before it, extends it, extends the next one, bridges both, or starts a new interval. addNum O(log k), get O(k).
     */
    public static class SummaryRanges {
        private final TreeMap<Integer, Integer> intervals = new TreeMap<>();

        public void addNum(int value) {
            Map.Entry<Integer, Integer> left = intervals.floorEntry(value);
            Map.Entry<Integer, Integer> right = intervals.ceilingEntry(value + 1);
            if (left != null && left.getValue() >= value) return;                        // already covered
            boolean joinLeft = left != null && left.getValue() == value - 1;
            boolean joinRight = right != null && right.getKey() == value + 1;
            if (joinLeft && joinRight) {
                intervals.put(left.getKey(), right.getValue());
                intervals.remove(right.getKey());
            } else if (joinLeft) {
                intervals.put(left.getKey(), value);
            } else if (joinRight) {
                intervals.remove(right.getKey());
                intervals.put(value, right.getValue());
            } else {
                intervals.put(value, value);
            }
        }

        public int[][] getIntervals() {
            int[][] out = new int[intervals.size()][];
            int i = 0;
            for (Map.Entry<Integer, Integer> e : intervals.entrySet()) out[i++] = new int[]{e.getKey(), e.getValue()};
            return out;
        }
    }

    /* Approach 2 - store the values only; build the intervals by scanning them in order when asked. add O(log n), get O(n). */
    public static class SummaryRangesSet {
        private final TreeSet<Integer> values = new TreeSet<>();

        public void addNum(int value) {
            values.add(value);
        }

        public int[][] getIntervals() {
            List<int[]> out = new ArrayList<>();
            for (int v : values) {
                if (!out.isEmpty() && out.getLast()[1] == v - 1) out.getLast()[1] = v;
                else out.add(new int[]{v, v});
            }
            return out.toArray(new int[0][]);
        }
    }
}
