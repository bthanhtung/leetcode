package MedianOfTwoSortedArrays;

/*
 * 4. Median of Two Sorted Arrays — https://leetcode.com/problems/median-of-two-sorted-arrays/
 * Given two sorted arrays nums1 (size m) and nums2 (size n), return the median of the two arrays. Run in O(log(m + n)).
 * Input: [1,3] + [2] -> 2.0 · [1,2] + [3,4] -> 2.5
 * Explanation: README.md
 */
public class MedianOfTwoSortedArrays {
    public static void main(String[] args) {
        System.out.println(findMedianSortedArrays(new int[]{1, 3}, new int[]{2}));        // 2.0
        System.out.println(findMedianMerge(new int[]{1, 2}, new int[]{3, 4}));            // 2.5
        System.out.println(findMedianKth(new int[]{1, 2}, new int[]{3, 4}));              // 2.5
    }

    /* Approach 1 - merge like merge sort, stop at the middle. Time O(m + n), space O(1). */
    public static double findMedianMerge(int[] a, int[] b) {
        int total = a.length + b.length;
        int i = 0, j = 0, prev = 0, curr = 0;
        for (int step = 0; step <= total / 2; step++) {
            prev = curr;
            if (i < a.length && (j >= b.length || a[i] <= b[j])) {
                curr = a[i++];
            } else {
                curr = b[j++];
            }
        }
        return total % 2 == 1 ? curr : (prev + curr) / 2.0;
    }

    /*
     * Approach 2 - k-th smallest: compare the k/2-th elements of both arrays and discard k/2 elements that cannot be
     * the answer. Time O(log(m + n)).
     */
    public static double findMedianKth(int[] a, int[] b) {
        int total = a.length + b.length;
        if (total % 2 == 1) {
            return kth(a, 0, b, 0, total / 2 + 1);
        }
        return (kth(a, 0, b, 0, total / 2) + kth(a, 0, b, 0, total / 2 + 1)) / 2.0;
    }

    private static int kth(int[] a, int i, int[] b, int j, int k) {
        if (i >= a.length) return b[j + k - 1];
        if (j >= b.length) return a[i + k - 1];
        if (k == 1) return Math.min(a[i], b[j]);
        int half = k / 2;
        int aVal = i + half - 1 < a.length ? a[i + half - 1] : Integer.MAX_VALUE;
        int bVal = j + half - 1 < b.length ? b[j + half - 1] : Integer.MAX_VALUE;
        return aVal < bVal ? kth(a, i + half, b, j, k - half) : kth(a, i, b, j + half, k - half);
    }

    /*
     * Approach 3 - binary search a partition of the shorter array: take i from a and j = half - i from b so that
     * everything on the left is <= everything on the right. Time O(log(min(m, n))), space O(1).
     */
    public static double findMedianSortedArrays(int[] a, int[] b) {
        if (a.length > b.length) {
            return findMedianSortedArrays(b, a);
        }
        int m = a.length, n = b.length, half = (m + n + 1) / 2;
        int lo = 0, hi = m;
        while (lo <= hi) {
            int i = (lo + hi) / 2;          // elements taken from a
            int j = half - i;               // elements taken from b
            int aLeft = i == 0 ? Integer.MIN_VALUE : a[i - 1];
            int aRight = i == m ? Integer.MAX_VALUE : a[i];
            int bLeft = j == 0 ? Integer.MIN_VALUE : b[j - 1];
            int bRight = j == n ? Integer.MAX_VALUE : b[j];
            if (aLeft <= bRight && bLeft <= aRight) {
                int leftMax = Math.max(aLeft, bLeft);
                if ((m + n) % 2 == 1) {
                    return leftMax;
                }
                return (leftMax + (double) Math.min(aRight, bRight)) / 2;
            } else if (aLeft > bRight) {
                hi = i - 1;                 // took too many from a
            } else {
                lo = i + 1;                 // took too few from a
            }
        }
        throw new IllegalArgumentException("arrays must be sorted");
    }
}
