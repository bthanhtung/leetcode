package FindTheDuplicateNumber;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
 * 287. Find the Duplicate Number — https://leetcode.com/problems/find-the-duplicate-number/
 * nums has n + 1 integers in [1, n]; exactly one value is repeated (possibly many times). Find it without modifying
 * nums and with O(1) extra space.
 * Input: [1,3,4,2,2] -> 2 · [3,1,3,4,2] -> 3 · [3,3,3,3,3] -> 3
 * Explanation: README.md
 */
public class FindTheDuplicateNumber {
    public static void main(String[] args) {
        System.out.println(findDuplicate(new int[]{1, 3, 4, 2, 2}));                 // 2
        System.out.println(findDuplicateBinarySearch(new int[]{3, 1, 3, 4, 2}));     // 3
    }

    /* Approach 1 - sort a copy; neighbours reveal the duplicate. Time O(n log n), space O(n) (it must not modify nums). */
    public static int findDuplicateSort(int[] nums) {
        int[] a = nums.clone();
        Arrays.sort(a);
        for (int i = 1; i < a.length; i++) if (a[i] == a[i - 1]) return a[i];
        throw new IllegalArgumentException();
    }

    /* Approach 2 - hash set. Time O(n), space O(n). */
    public static int findDuplicateSet(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int x : nums) if (!seen.add(x)) return x;
        throw new IllegalArgumentException();
    }

    /*
     * Approach 3 - binary search on the value: count how many numbers are <= mid. Without a duplicate at or below mid
     * the count would be at most mid (pigeonhole). Time O(n log n), space O(1).
     */
    public static int findDuplicateBinarySearch(int[] nums) {
        int lo = 1, hi = nums.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2, count = 0;
            for (int x : nums) if (x <= mid) count++;
            if (count > mid) hi = mid;
            else lo = mid + 1;
        }
        return lo;
    }

    /*
     * Approach 4 - treat i -> nums[i] as a linked list. Two indexes point to the duplicate value, so the list has a cycle
     * whose entrance is the duplicate. Floyd: meet inside the cycle, then walk from the start and the meeting point
     * at the same speed. Time O(n), space O(1).
     */
    public static int findDuplicate(int[] nums) {
        int slow = nums[0], fast = nums[0];
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);
        slow = nums[0];
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }
}
