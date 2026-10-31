package RangeModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/*
 * 715. Range Module — https://leetcode.com/problems/range-module/
 * Track half-open ranges [left, right): addRange adds them, removeRange removes them, queryRange tells whether every
 * number in [left, right) is currently tracked.
 * Example: add [10,20), remove [14,16), query [10,14) -> true, query [13,15) -> false, query [16,17) -> true
 * Explanation: README.md
 */
public class RangeModule {
    public static void main(String[] args) {
        RangeModuleTree m = new RangeModuleTree();
        m.addRange(10, 20);
        m.removeRange(14, 16);
        System.out.println(m.queryRange(10, 14) + " " + m.queryRange(13, 15) + " " + m.queryRange(16, 17));   // true false true
    }

    /* Approach 1 - TreeMap start -> end of disjoint, non-touching intervals. Each operation merges or splits neighbours. */
    public static class RangeModuleTree {
        private final TreeMap<Integer, Integer> map = new TreeMap<>();

        public void addRange(int left, int right) {
            Map.Entry<Integer, Integer> before = map.floorEntry(left);
            if (before != null && before.getValue() >= left) {          // overlaps or touches on the left
                left = before.getKey();
                right = Math.max(right, before.getValue());
            }
            Map.Entry<Integer, Integer> e;
            while ((e = map.ceilingEntry(left)) != null && e.getKey() <= right) {
                right = Math.max(right, e.getValue());
                map.remove(e.getKey());
            }
            map.put(left, right);
        }

        public boolean queryRange(int left, int right) {
            Map.Entry<Integer, Integer> e = map.floorEntry(left);
            return e != null && e.getValue() >= right;
        }

        public void removeRange(int left, int right) {
            Map.Entry<Integer, Integer> before = map.lowerEntry(left);
            if (before != null && before.getValue() > left) {            // cut the interval that starts before left
                int end = before.getValue();
                map.put(before.getKey(), left);
                if (end > right) map.put(right, end);
            }
            Map.Entry<Integer, Integer> e;
            while ((e = map.ceilingEntry(left)) != null && e.getKey() < right) {
                map.remove(e.getKey());
                if (e.getValue() > right) map.put(right, e.getValue());
            }
        }
    }

    /* Approach 2 - a sorted list of interval boundaries [s0, e0, s1, e1, ...]; operations splice it. O(n) per operation. */
    public static class RangeModuleList {
        private final List<Integer> bounds = new ArrayList<>();

        private int lowerBound(int x) {
            int lo = 0, hi = bounds.size();
            while (lo < hi) {
                int mid = (lo + hi) >>> 1;
                if (bounds.get(mid) < x) lo = mid + 1;
                else hi = mid;
            }
            return lo;
        }

        private int upperBound(int x) {
            int lo = 0, hi = bounds.size();
            while (lo < hi) {
                int mid = (lo + hi) >>> 1;
                if (bounds.get(mid) <= x) lo = mid + 1;
                else hi = mid;
            }
            return lo;
        }

        private void replace(int left, int right, boolean add) {
            int i = lowerBound(left), j = upperBound(right);
            List<Integer> middle = new ArrayList<>();
            if (i % 2 == (add ? 0 : 1)) middle.add(left);                 // left is outside (add) / inside (remove)
            if (j % 2 == (add ? 0 : 1)) middle.add(right);
            bounds.subList(i, j).clear();
            bounds.addAll(i, middle);
        }

        public void addRange(int left, int right) {
            replace(left, right, true);
        }

        public void removeRange(int left, int right) {
            replace(left, right, false);
        }

        public boolean queryRange(int left, int right) {
            int i = upperBound(left), j = lowerBound(right);
            return i == j && i % 2 == 1;                                 // no boundary inside, and left is inside a range
        }
    }
}
