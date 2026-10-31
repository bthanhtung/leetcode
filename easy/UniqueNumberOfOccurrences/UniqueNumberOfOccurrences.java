package UniqueNumberOfOccurrences;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/*
 * 1207. Unique Number of Occurrences — https://leetcode.com/problems/unique-number-of-occurrences/
 * Return true if the number of occurrences of each value in arr is unique.
 * Input: [1,2,2,1,1,3] -> true (1:3, 2:2, 3:1) · [1,2] -> false · [-3,0,1,-3,1,1,1,-3,10,0] -> true
 * Explanation: README.md
 */
public class UniqueNumberOfOccurrences {
    public static void main(String[] args) {
        System.out.println(uniqueOccurrences(new int[]{1, 2, 2, 1, 1, 3}));     // true
        System.out.println(uniqueOccurrencesArray(new int[]{1, 2}));           // false
    }

    /* Approach 1 - count with a map, then check the counts are distinct with a set. Time O(n), space O(n). */
    public static boolean uniqueOccurrences(int[] arr) {
        Map<Integer, Integer> count = new HashMap<>();
        for (int x : arr) count.merge(x, 1, Integer::sum);
        return new HashSet<>(count.values()).size() == count.size();
    }

    /* Approach 2 - values are in [-1000, 1000]: counting array, then a boolean table of counts already used. */
    public static boolean uniqueOccurrencesArray(int[] arr) {
        int[] count = new int[2001];
        for (int x : arr) count[x + 1000]++;
        boolean[] used = new boolean[arr.length + 1];
        for (int c : count) {
            if (c == 0) continue;
            if (used[c]) return false;
            used[c] = true;
        }
        return true;
    }
}
