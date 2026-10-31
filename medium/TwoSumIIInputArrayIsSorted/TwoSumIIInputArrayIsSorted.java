package TwoSumIIInputArrayIsSorted;

/*
 * 167. Two Sum II - Input Array Is Sorted — https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/
 * numbers is sorted ascending. Return the 1-indexed positions [i, j] (i < j) of the two numbers adding up to target.
 * Exactly one solution exists. Use only constant extra space.
 * Input: [2,7,11,15], 9 -> [1,2] · [2,3,4], 6 -> [1,3] · [-1,0], -1 -> [1,2]
 * Explanation: README.md
 */
public class TwoSumIIInputArrayIsSorted {
    public static void main(String[] args) {
        System.out.println(java.util.Arrays.toString(twoSum(new int[]{2, 7, 11, 15}, 9)));            // [1, 2]
        System.out.println(java.util.Arrays.toString(twoSumBinarySearch(new int[]{2, 3, 4}, 6)));     // [1, 3]
    }

    /* Approach 1 - for each i, binary-search target - numbers[i] to the right of i. Time O(n log n), space O(1). */
    public static int[] twoSumBinarySearch(int[] numbers, int target) {
        for (int i = 0; i < numbers.length; i++) {
            int need = target - numbers[i];
            int lo = i + 1, hi = numbers.length - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                if (numbers[mid] == need) return new int[]{i + 1, mid + 1};
                if (numbers[mid] < need) lo = mid + 1;
                else hi = mid - 1;
            }
        }
        return new int[]{-1, -1};
    }

    /*
     * Approach 2 - two pointers from both ends: too small -> move left up, too big -> move right down.
     * Each step rules out one element for good. Time O(n), space O(1).
     */
    public static int[] twoSum(int[] numbers, int target) {
        int lo = 0, hi = numbers.length - 1;
        while (lo < hi) {
            int sum = numbers[lo] + numbers[hi];
            if (sum == target) return new int[]{lo + 1, hi + 1};
            if (sum < target) lo++;
            else hi--;
        }
        return new int[]{-1, -1};
    }
}
