package Permutations;

import java.util.ArrayList;
import java.util.List;

/*
 * 46. Permutations — https://leetcode.com/problems/permutations/
 * Given an array of distinct integers, return all possible permutations in any order.
 * Input: [1,2,3] -> [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]] · [0,1] -> [[0,1],[1,0]]
 * Explanation: README.md
 */
public class Permutations {
    public static void main(String[] args) {
        System.out.println(permute(new int[]{1, 2, 3}));
        System.out.println(permuteSwap(new int[]{0, 1}));
    }

    /* Approach 1 - choose any unused number for the next position; used[] marks what's taken. Time O(n * n!). */
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> out = new ArrayList<>();
        backtrack(nums, new boolean[nums.length], new ArrayList<>(), out);
        return out;
    }

    private static void backtrack(int[] nums, boolean[] used, List<Integer> path, List<List<Integer>> out) {
        if (path.size() == nums.length) {
            out.add(new ArrayList<>(path));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue;
            used[i] = true;
            path.add(nums[i]);
            backtrack(nums, used, path, out);
            path.removeLast();
            used[i] = false;
        }
    }

    /* Approach 2 - fix position k by swapping each remaining element into it; no extra used[] or path. */
    public static List<List<Integer>> permuteSwap(int[] nums) {
        List<List<Integer>> out = new ArrayList<>();
        swapFrom(nums.clone(), 0, out);
        return out;
    }

    private static void swapFrom(int[] a, int k, List<List<Integer>> out) {
        if (k == a.length) {
            List<Integer> perm = new ArrayList<>();
            for (int x : a) perm.add(x);
            out.add(perm);
            return;
        }
        for (int i = k; i < a.length; i++) {
            swap(a, k, i);
            swapFrom(a, k + 1, out);
            swap(a, k, i);                     // undo
        }
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    /* Approach 3 - iterative: insert each new number into every gap of every permutation built so far. */
    public static List<List<Integer>> permuteInsert(int[] nums) {
        List<List<Integer>> out = new ArrayList<>();
        out.add(new ArrayList<>());
        for (int x : nums) {
            List<List<Integer>> next = new ArrayList<>();
            for (List<Integer> perm : out) {
                for (int pos = 0; pos <= perm.size(); pos++) {
                    List<Integer> p = new ArrayList<>(perm);
                    p.add(pos, x);
                    next.add(p);
                }
            }
            out = next;
        }
        return out;
    }
}
