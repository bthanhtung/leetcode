package CountOfSmallerNumbersAfterSelf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * 315. Count of Smaller Numbers After Self — https://leetcode.com/problems/count-of-smaller-numbers-after-self/
 * For each nums[i], count how many elements to its right are smaller.
 * Input: [5,2,6,1] -> [2,1,1,0] · [-1] -> [0] · [-1,-1] -> [0,0]
 * Explanation: README.md
 */
public class CountOfSmallerNumbersAfterSelf {
    public static void main(String[] args) {
        System.out.println(countSmaller(new int[]{5, 2, 6, 1}));              // [2, 1, 1, 0]
        System.out.println(countSmallerFenwick(new int[]{5, 2, 6, 1}));       // [2, 1, 1, 0]
    }

    /* Approach 1 - O(n^2) reference. */
    public static List<Integer> countSmallerBrute(int[] nums) {
        List<Integer> out = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int c = 0;
            for (int j = i + 1; j < nums.length; j++) if (nums[j] < nums[i]) c++;
            out.add(c);
        }
        return out;
    }

    /*
     * Approach 2 - merge sort on indexes. While merging, when a left element is placed, every right element already
     * placed before it is smaller and was originally to its right: add that count. Time O(n log n).
     */
    public static List<Integer> countSmaller(int[] nums) {
        int n = nums.length;
        int[] idx = new int[n], tmp = new int[n], count = new int[n];
        for (int i = 0; i < n; i++) idx[i] = i;
        sort(nums, idx, tmp, count, 0, n - 1);
        List<Integer> out = new ArrayList<>();
        for (int c : count) out.add(c);
        return out;
    }

    private static void sort(int[] nums, int[] idx, int[] tmp, int[] count, int lo, int hi) {
        if (lo >= hi) return;
        int mid = (lo + hi) >>> 1;
        sort(nums, idx, tmp, count, lo, mid);
        sort(nums, idx, tmp, count, mid + 1, hi);
        int i = lo, j = mid + 1, k = lo, rightTaken = 0;
        while (i <= mid || j <= hi) {
            if (j > hi || (i <= mid && nums[idx[i]] <= nums[idx[j]])) {
                count[idx[i]] += rightTaken;                     // right elements that jumped ahead are smaller
                tmp[k++] = idx[i++];
            } else {
                rightTaken++;
                tmp[k++] = idx[j++];
            }
        }
        System.arraycopy(tmp, lo, idx, lo, hi - lo + 1);
    }

    /*
     * Approach 3 - compress values to ranks and walk from the right, keeping a Fenwick tree of ranks seen so far;
     * the answer for nums[i] is the number of seen ranks below its rank. Time O(n log n).
     */
    public static List<Integer> countSmallerFenwick(int[] nums) {
        int n = nums.length;
        int[] sorted = Arrays.stream(nums).distinct().sorted().toArray();
        int[] tree = new int[sorted.length + 1];
        Integer[] out = new Integer[n];
        for (int i = n - 1; i >= 0; i--) {
            int rank = Arrays.binarySearch(sorted, nums[i]) + 1;          // 1-based
            int c = 0;
            for (int x = rank - 1; x > 0; x -= x & -x) c += tree[x];     // prefix sum of ranks < rank
            out[i] = c;
            for (int x = rank; x < tree.length; x += x & -x) tree[x]++;
        }
        return Arrays.asList(out);
    }
}
