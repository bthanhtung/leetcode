package SmallestRangeCoveringElementsFromKLists;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/*
 * 632. Smallest Range Covering Elements from K Lists — https://leetcode.com/problems/smallest-range-covering-elements-from-k-lists/
 * k sorted lists. Find the smallest range [a, b] that includes at least one number from each list
 * (smaller = shorter, ties broken by smaller a).
 * Input: [[4,10,15,24,26],[0,9,12,20],[5,18,22,30]] -> [20,24] · [[1,2,3],[1,2,3],[1,2,3]] -> [1,1]
 * Explanation: README.md
 */
public class SmallestRangeCoveringElementsFromKLists {
    public static void main(String[] args) {
        List<List<Integer>> nums = List.of(List.of(4, 10, 15, 24, 26), List.of(0, 9, 12, 20), List.of(5, 18, 22, 30));
        System.out.println(java.util.Arrays.toString(smallestRange(nums)));            // [20, 24]
        System.out.println(java.util.Arrays.toString(smallestRangeWindow(nums)));      // [20, 24]
    }

    /*
     * Approach 1 - keep one pointer per list in a min-heap and track the current max. The range [heap min, max] covers
     * every list; advance the list that holds the min (the only way to possibly shrink the range). Time O(N log k).
     */
    public static int[] smallestRange(List<List<Integer>> nums) {
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));   // {value, list, index}
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.size(); i++) {
            int v = nums.get(i).getFirst();
            heap.add(new int[]{v, i, 0});
            max = Math.max(max, v);
        }
        int[] best = {heap.peek()[0], max};
        while (true) {
            int[] top = heap.poll();
            if (max - top[0] < best[1] - best[0]) best = new int[]{top[0], max};
            int next = top[2] + 1;
            if (next == nums.get(top[1]).size()) return best;          // this list is exhausted
            int v = nums.get(top[1]).get(next);
            heap.add(new int[]{v, top[1], next});
            max = Math.max(max, v);
        }
    }

    /* Approach 2 - merge everything as (value, list), sort, then the minimum window covering all k lists. O(N log N). */
    public static int[] smallestRangeWindow(List<List<Integer>> nums) {
        List<int[]> all = new ArrayList<>();
        for (int i = 0; i < nums.size(); i++) for (int v : nums.get(i)) all.add(new int[]{v, i});
        all.sort((a, b) -> Integer.compare(a[0], b[0]));
        int k = nums.size(), covered = 0, left = 0;
        int[] count = new int[k], best = null;
        for (int[] cur : all) {
            if (count[cur[1]]++ == 0) covered++;
            while (covered == k) {
                int lo = all.get(left)[0], hi = cur[0];
                if (best == null || hi - lo < best[1] - best[0]) best = new int[]{lo, hi};
                if (--count[all.get(left)[1]] == 0) covered--;
                left++;
            }
        }
        return best;
    }
}
