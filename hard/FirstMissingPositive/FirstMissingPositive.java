package FirstMissingPositive;

import java.util.HashSet;
import java.util.Set;

/*
 * 41. First Missing Positive — https://leetcode.com/problems/first-missing-positive/
 * Return the smallest positive integer missing from an unsorted array, in O(n) time and O(1) extra space.
 * Input: [1,2,0] -> 3 · [3,4,-1,1] -> 2 · [7,8,9,11,12] -> 1
 * Explanation: README.md
 */
public class FirstMissingPositive {
    public static void main(String[] args) {
        System.out.println(firstMissingPositive(new int[]{3, 4, -1, 1}));         // 2
        System.out.println(firstMissingPositiveMark(new int[]{7, 8, 9, 11, 12})); // 1
    }

    /* Approach 1 - set of values, then test 1, 2, 3, ... Time O(n), space O(n). */
    public static int firstMissingPositiveSet(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int x : nums) set.add(x);
        int k = 1;
        while (set.contains(k)) k++;
        return k;
    }

    /*
     * Approach 2 - the answer is in 1..n+1, so only values 1..n matter. Swap each such value v to index v - 1 until every
     * slot holds its own value or junk; the first slot i with nums[i] != i + 1 gives the answer. O(n), O(1).
     */
    public static int firstMissingPositive(int[] nums) {
        int[] a = nums.clone();
        int n = a.length;
        for (int i = 0; i < n; i++) {
            while (a[i] > 0 && a[i] <= n && a[a[i] - 1] != a[i]) {
                int target = a[i] - 1;
                int t = a[target];
                a[target] = a[i];
                a[i] = t;
            }
        }
        for (int i = 0; i < n; i++) if (a[i] != i + 1) return i + 1;
        return n + 1;
    }

    /* Approach 3 - replace junk (<= 0 or > n) with n + 1, then mark value v as present by making a[v-1] negative. */
    public static int firstMissingPositiveMark(int[] nums) {
        int[] a = nums.clone();
        int n = a.length;
        for (int i = 0; i < n; i++) if (a[i] <= 0 || a[i] > n) a[i] = n + 1;
        for (int i = 0; i < n; i++) {
            int v = Math.abs(a[i]);
            if (v <= n && a[v - 1] > 0) a[v - 1] = -a[v - 1];
        }
        for (int i = 0; i < n; i++) if (a[i] > 0) return i + 1;
        return n + 1;
    }
}
