package PermutationsII;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/*
 * 47. Permutations II — https://leetcode.com/problems/permutations-ii/
 * Given numbers that may contain duplicates, return all unique permutations in any order.
 * Input: [1,1,2] -> [[1,1,2],[1,2,1],[2,1,1]] · [1,2,3] -> all 6 permutations
 * Explanation: README.md
 */
public class PermutationsII {
    public static void main(String[] args) {
        System.out.println(permuteUnique(new int[]{1, 1, 2}));          // [[1, 1, 2], [1, 2, 1], [2, 1, 1]]
        System.out.println(permuteUniqueCounts(new int[]{1, 1, 2}));
    }

    /*
     * Approach 1 - sort, then backtrack with used[]. Skip nums[i] if it equals nums[i-1] and nums[i-1] is not in use:
     * among equal values we always take them left to right, so each arrangement is built once.
     */
    public static List<List<Integer>> permuteUnique(int[] nums) {
        int[] a = nums.clone();
        Arrays.sort(a);
        List<List<Integer>> out = new ArrayList<>();
        backtrack(a, new boolean[a.length], new ArrayList<>(), out);
        return out;
    }

    private static void backtrack(int[] a, boolean[] used, List<Integer> path, List<List<Integer>> out) {
        if (path.size() == a.length) {
            out.add(new ArrayList<>(path));
            return;
        }
        for (int i = 0; i < a.length; i++) {
            if (used[i] || (i > 0 && a[i] == a[i - 1] && !used[i - 1])) continue;
            used[i] = true;
            path.add(a[i]);
            backtrack(a, used, path, out);
            path.removeLast();
            used[i] = false;
        }
    }

    /* Approach 2 - choose from distinct values with remaining counts; no duplicates can arise. */
    public static List<List<Integer>> permuteUniqueCounts(int[] nums) {
        Map<Integer, Integer> count = new TreeMap<>();
        for (int x : nums) count.merge(x, 1, Integer::sum);
        List<List<Integer>> out = new ArrayList<>();
        fromCounts(count, nums.length, new ArrayList<>(), out);
        return out;
    }

    private static void fromCounts(Map<Integer, Integer> count, int n, List<Integer> path, List<List<Integer>> out) {
        if (path.size() == n) {
            out.add(new ArrayList<>(path));
            return;
        }
        for (Map.Entry<Integer, Integer> e : count.entrySet()) {
            if (e.getValue() == 0) continue;
            e.setValue(e.getValue() - 1);
            path.add(e.getKey());
            fromCounts(count, n, path, out);
            path.removeLast();
            e.setValue(e.getValue() + 1);
        }
    }
}
