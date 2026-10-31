package ReversePairs;

import java.util.Arrays;

/*
 * 493. Reverse Pairs — https://leetcode.com/problems/reverse-pairs/
 * Count important reverse pairs: i < j and nums[i] > 2 * nums[j].
 * Input: [1,3,2,3,1] -> 2 · [2,4,3,5,1] -> 3
 * Explanation: README.md
 */
public class ReversePairs {
    public static void main(String[] args) {
        System.out.println(reversePairs(new int[]{2, 4, 3, 5, 1}));     // 3
    }

    /*
     * Approach 1 - merge sort. After sorting both halves, count pairs (i in left, j in right) with left[i] > 2*right[j]
     * using a moving pointer, then merge. Time O(n log n).
     */
    public static int reversePairs(int[] nums) {
        int[] a = nums.clone();
        return sort(a, new int[a.length], 0, a.length - 1);
    }

    private static int sort(int[] a, int[] tmp, int lo, int hi) {
        if (lo >= hi) return 0;
        int mid = (lo + hi) >>> 1;
        int count = sort(a, tmp, lo, mid) + sort(a, tmp, mid + 1, hi);
        for (int i = lo, j = mid + 1; i <= mid; i++) {
            while (j <= hi && a[i] > 2L * a[j]) j++;
            count += j - (mid + 1);
        }
        int i = lo, j = mid + 1, k = lo;
        while (i <= mid || j <= hi) tmp[k++] = j > hi || (i <= mid && a[i] <= a[j]) ? a[i++] : a[j++];
        System.arraycopy(tmp, lo, a, lo, hi - lo + 1);
        return count;
    }

    /*
     * Approach 2 - Fenwick tree over the sorted distinct values. Scan left to right; for each x count earlier values
     * greater than 2x, then add x. Time O(n log n).
     */
    public static int reversePairsBit(int[] nums) {
        long[] sorted = Arrays.stream(nums).asLongStream().sorted().distinct().toArray();
        int[] tree = new int[sorted.length + 1];
        int count = 0, seen = 0;
        for (int x : nums) {
            int notGreater = upperBound(sorted, 2L * x);          // values <= 2x among the compressed keys
            count += seen - query(tree, notGreater);
            int idx = Arrays.binarySearch(sorted, x) + 1;
            for (; idx < tree.length; idx += idx & -idx) tree[idx]++;
            seen++;
        }
        return count;
    }

    private static int upperBound(long[] a, long x) {
        int lo = 0, hi = a.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (a[mid] <= x) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }

    private static int query(int[] tree, int i) {
        int s = 0;
        for (; i > 0; i -= i & -i) s += tree[i];
        return s;
    }
}
