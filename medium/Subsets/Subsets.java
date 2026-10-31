package Subsets;

import java.util.ArrayList;
import java.util.List;

/*
 * 78. Subsets — https://leetcode.com/problems/subsets/
 * Given an array of distinct integers, return all possible subsets (the power set), without duplicates, in any order.
 * Input: [1,2,3] -> [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]] · [0] -> [[],[0]]
 * Explanation: README.md
 */
public class Subsets {
    public static void main(String[] args) {
        System.out.println(subsets(new int[]{1, 2, 3}));
        System.out.println(subsetsBitmask(new int[]{0}));
    }

    /* Approach 1 - backtracking: every node of the recursion tree is a subset; extend it with later elements only. */
    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> out = new ArrayList<>();
        backtrack(nums, 0, new ArrayList<>(), out);
        return out;
    }

    private static void backtrack(int[] nums, int start, List<Integer> path, List<List<Integer>> out) {
        out.add(new ArrayList<>(path));                 // copy: path keeps changing
        for (int i = start; i < nums.length; i++) {
            path.add(nums[i]);
            backtrack(nums, i + 1, path, out);
            path.removeLast();
        }
    }

    /* Approach 2 - start with [[]]; for each number, add it to a copy of every subset so far (the count doubles). */
    public static List<List<Integer>> subsetsIterative(int[] nums) {
        List<List<Integer>> out = new ArrayList<>();
        out.add(new ArrayList<>());
        for (int x : nums) {
            for (int i = 0, size = out.size(); i < size; i++) {
                List<Integer> next = new ArrayList<>(out.get(i));
                next.add(x);
                out.add(next);
            }
        }
        return out;
    }

    /* Approach 3 - masks 0 .. 2^n - 1: bit i set means nums[i] is in the subset. Time O(n * 2^n). */
    public static List<List<Integer>> subsetsBitmask(int[] nums) {
        int n = nums.length;
        List<List<Integer>> out = new ArrayList<>();
        for (int mask = 0; mask < 1 << n; mask++) {
            List<Integer> subset = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if ((mask >> i & 1) == 1) subset.add(nums[i]);
            }
            out.add(subset);
        }
        return out;
    }
}
