package RangeSumQueryImmutable;

/*
 * 303. Range Sum Query - Immutable — https://leetcode.com/problems/range-sum-query-immutable/
 * Design NumArray: constructed with nums, answers sumRange(left, right) = nums[left] + ... + nums[right] many times.
 * Input: nums = [-2,0,3,-5,2,-1]; sumRange(0,2) -> 1, sumRange(2,5) -> -1, sumRange(0,5) -> -3
 * Explanation: README.md
 */
public class RangeSumQueryImmutable {
    public static void main(String[] args) {
        NumArray a = new NumArray(new int[]{-2, 0, 3, -5, 2, -1});
        System.out.println(a.sumRange(0, 2));    // 1
        System.out.println(a.sumRange(2, 5));    // -1
        System.out.println(a.sumRange(0, 5));    // -3
    }

    /* Approach 1 - loop over the range on every query. Query O(n). */
    public static class NumArrayLoop {
        private final int[] nums;

        public NumArrayLoop(int[] nums) {
            this.nums = nums.clone();
        }

        public int sumRange(int left, int right) {
            int sum = 0;
            for (int i = left; i <= right; i++) sum += nums[i];
            return sum;
        }
    }

    /*
     * Approach 2 - prefix[i] = sum of the first i numbers (prefix[0] = 0). Then sum(left..right) = prefix[right+1] - prefix[left].
     * Build O(n), query O(1).
     */
    public static class NumArray {
        private final int[] prefix;

        public NumArray(int[] nums) {
            prefix = new int[nums.length + 1];
            for (int i = 0; i < nums.length; i++) {
                prefix[i + 1] = prefix[i] + nums[i];
            }
        }

        public int sumRange(int left, int right) {
            return prefix[right + 1] - prefix[left];
        }
    }
}
