package FindPivotIndex;

/*
 * 724. Find Pivot Index — https://leetcode.com/problems/find-pivot-index/
 * The pivot index is where the sum of all numbers strictly to the left equals the sum strictly to the right.
 * Return the leftmost pivot index, or -1.
 * Input: [1,7,3,6,5,6] -> 3 (1+7+3 = 5+6 = 11) · [1,2,3] -> -1 · [2,1,-1] -> 0
 * Explanation: README.md
 */
public class FindPivotIndex {
    public static void main(String[] args) {
        System.out.println(pivotIndex(new int[]{1, 7, 3, 6, 5, 6}));          // 3
        System.out.println(pivotIndexPrefix(new int[]{2, 1, -1}));            // 0
    }

    /* Approach 1 - prefix sums: left = prefix[i], right = prefix[n] - prefix[i + 1]. Time O(n), space O(n). */
    public static int pivotIndexPrefix(int[] nums) {
        int n = nums.length;
        int[] prefix = new int[n + 1];
        for (int i = 0; i < n; i++) prefix[i + 1] = prefix[i] + nums[i];
        for (int i = 0; i < n; i++) {
            if (prefix[i] == prefix[n] - prefix[i + 1]) return i;
        }
        return -1;
    }

    /* Approach 2 - total once, then a running left sum: right = total - left - nums[i]. Time O(n), space O(1). */
    public static int pivotIndex(int[] nums) {
        int total = 0;
        for (int x : nums) total += x;
        int left = 0;
        for (int i = 0; i < nums.length; i++) {
            if (left == total - left - nums[i]) return i;
            left += nums[i];
        }
        return -1;
    }
}
