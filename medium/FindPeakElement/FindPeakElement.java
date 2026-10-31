package FindPeakElement;

/*
 * 162. Find Peak Element — https://leetcode.com/problems/find-peak-element/
 * A peak is strictly greater than its neighbours; nums[-1] = nums[n] = -infinity and nums[i] != nums[i + 1].
 * Return the index of any peak in O(log n).
 * Input: [1,2,3,1] -> 2 · [1,2,1,3,5,6,4] -> 1 or 5
 * Explanation: README.md
 */
public class FindPeakElement {
    public static void main(String[] args) {
        System.out.println(findPeakElement(new int[]{1, 2, 1, 3, 5, 6, 4}));     // 5 (1 is also valid)
    }

    /*
     * Approach 1 - if nums[mid] < nums[mid + 1] we are on an upward slope, and since the far end is -infinity a peak
     * must exist to the right; otherwise one exists at mid or to the left. O(log n).
     */
    public static int findPeakElement(int[] nums) {
        int lo = 0, hi = nums.length - 1;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (nums[mid] < nums[mid + 1]) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }

    /* Approach 2 - the first i with nums[i] > nums[i + 1] is a peak (everything before it was rising). O(n). */
    public static int findPeakElementLinear(int[] nums) {
        for (int i = 0; i + 1 < nums.length; i++) if (nums[i] > nums[i + 1]) return i;
        return nums.length - 1;
    }
}
