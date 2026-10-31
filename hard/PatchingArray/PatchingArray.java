package PatchingArray;

/*
 * 330. Patching Array — https://leetcode.com/problems/patching-array/
 * Given a sorted array nums and an integer n, add (patch) as few numbers as possible so that every number in [1, n]
 * can be formed as the sum of some elements. Return the number of patches.
 * Input: [1,3], n = 6 -> 1 (add 2) · [1,5,10], n = 20 -> 2 (add 2, 4) · [1,2,2], n = 5 -> 0
 * Explanation: README.md
 */
public class PatchingArray {
    public static void main(String[] args) {
        System.out.println(minPatches(new int[]{1, 3}, 6));          // 1
        System.out.println(minPatches(new int[]{1, 5, 10}, 20));     // 2
    }

    /*
     * Greedy - keep miss = the smallest sum we cannot make yet (everything in [1, miss) is reachable).
     * If the next number x <= miss, using it extends the range to [1, miss + x). Otherwise patch with miss itself,
     * the largest number that keeps the range contiguous, doubling it. Time O(len + log n).
     */
    public static int minPatches(int[] nums, int n) {
        long miss = 1;
        int patches = 0, i = 0;
        while (miss <= n) {
            if (i < nums.length && nums[i] <= miss) {
                miss += nums[i++];
            } else {
                miss += miss;
                patches++;
            }
        }
        return patches;
    }

    /** Reference for tests: smallest set of extra numbers found by trying patch counts in order (tiny inputs). */
    static int bruteForce(int[] nums, int n) {
        for (int k = 0; ; k++) {
            if (search(nums, n, k, 1)) return k;
        }
    }

    private static boolean search(int[] nums, int n, int k, int from) {
        if (coversAll(nums, n)) return true;
        if (k == 0) return false;
        for (int v = from; v <= n; v++) {
            int[] more = java.util.Arrays.copyOf(nums, nums.length + 1);
            more[nums.length] = v;
            if (search(more, n, k - 1, v)) return true;
        }
        return false;
    }

    private static boolean coversAll(int[] nums, int n) {
        boolean[] can = new boolean[n + 1];
        can[0] = true;
        for (int x : nums)
            for (int s = n; s >= x; s--) if (can[s - x]) can[s] = true;
        for (int s = 1; s <= n; s++) if (!can[s]) return false;
        return true;
    }
}
