package CountOfRangeSum;

import java.util.Arrays;

/*
 * 327. Count of Range Sum — https://leetcode.com/problems/count-of-range-sum/
 * Count the subarrays whose sum lies in [lower, upper] (inclusive).
 * Input: [-2,5,-1], lower = -2, upper = 2 -> 3 ([-2], [-1], [-2,5,-1]) · [0], 0, 0 -> 1
 * Explanation: README.md
 */
public class CountOfRangeSum {
    public static void main(String[] args) {
        System.out.println(countRangeSum(new int[]{-2, 5, -1}, -2, 2));          // 3
        System.out.println(countRangeSumFenwick(new int[]{-2, 5, -1}, -2, 2));   // 3
    }

    private static long[] prefix(int[] nums) {
        long[] p = new long[nums.length + 1];
        for (int i = 0; i < nums.length; i++) p[i + 1] = p[i] + nums[i];
        return p;
    }

    /* Approach 1 - every pair of prefix sums. O(n^2). */
    public static int countRangeSumBrute(int[] nums, int lower, int upper) {
        long[] p = prefix(nums);
        int count = 0;
        for (int i = 0; i < p.length; i++)
            for (int j = i + 1; j < p.length; j++)
                if (p[j] - p[i] >= lower && p[j] - p[i] <= upper) count++;
        return count;
    }

    /*
     * Approach 2 - merge sort on prefix sums. Before merging two sorted halves, for each left prefix p[i] count right
     * prefixes p[j] with lower <= p[j] - p[i] <= upper using two pointers that only move forward. O(n log n).
     */
    public static int countRangeSum(int[] nums, int lower, int upper) {
        long[] p = prefix(nums);
        return sort(p, new long[p.length], 0, p.length - 1, lower, upper);
    }

    private static int sort(long[] p, long[] tmp, int lo, int hi, int lower, int upper) {
        if (lo >= hi) return 0;
        int mid = (lo + hi) >>> 1;
        int count = sort(p, tmp, lo, mid, lower, upper) + sort(p, tmp, mid + 1, hi, lower, upper);
        int from = mid + 1, to = mid + 1;
        for (int i = lo; i <= mid; i++) {
            while (from <= hi && p[from] - p[i] < lower) from++;
            while (to <= hi && p[to] - p[i] <= upper) to++;
            count += to - from;
        }
        int i = lo, j = mid + 1, k = lo;
        while (i <= mid || j <= hi) tmp[k++] = (j > hi || (i <= mid && p[i] <= p[j])) ? p[i++] : p[j++];
        System.arraycopy(tmp, lo, p, lo, hi - lo + 1);
        return count;
    }

    /*
     * Approach 3 - walk prefixes left to right; for p[j] we need earlier p[i] in [p[j] - upper, p[j] - lower].
     * Compress all prefix values and count with a Fenwick tree. O(n log n).
     */
    public static int countRangeSumFenwick(int[] nums, int lower, int upper) {
        long[] p = prefix(nums);
        long[] sorted = Arrays.stream(p).distinct().sorted().toArray();
        int[] tree = new int[sorted.length + 1];
        int count = 0;
        for (long x : p) {
            int hiIdx = upperBound(sorted, x - lower), loIdx = lowerBound(sorted, x - upper);   // ranks in [loIdx, hiIdx)
            count += query(tree, hiIdx) - query(tree, loIdx);
            for (int i = lowerBound(sorted, x) + 1; i < tree.length; i += i & -i) tree[i]++;
        }
        return count;
    }

    private static int query(int[] tree, int i) {
        int s = 0;
        for (; i > 0; i -= i & -i) s += tree[i];
        return s;
    }

    private static int lowerBound(long[] a, long key) {
        int lo = 0, hi = a.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (a[mid] < key) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }

    private static int upperBound(long[] a, long key) {
        int lo = 0, hi = a.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (a[mid] <= key) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }
}
