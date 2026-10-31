package Combinations;

import java.util.ArrayList;
import java.util.List;

/*
 * 77. Combinations — https://leetcode.com/problems/combinations/
 * Return all combinations of k numbers chosen from 1 .. n, in any order.
 * Input: n = 4, k = 2 -> [[1,2],[1,3],[1,4],[2,3],[2,4],[3,4]] · n = 1, k = 1 -> [[1]]
 * Explanation: README.md
 */
public class Combinations {
    public static void main(String[] args) {
        System.out.println(combine(4, 2));                // [[1, 2], [1, 3], [1, 4], [2, 3], [2, 4], [3, 4]]
        System.out.println(combineIterative(4, 3));
    }

    /* Approach 1 - backtracking; prune when not enough numbers remain to fill the combination. Time O(k * C(n, k)). */
    public static List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> out = new ArrayList<>();
        backtrack(n, k, 1, new ArrayList<>(), out);
        return out;
    }

    private static void backtrack(int n, int k, int start, List<Integer> path, List<List<Integer>> out) {
        if (path.size() == k) {
            out.add(new ArrayList<>(path));
            return;
        }
        int need = k - path.size();
        for (int i = start; i <= n - need + 1; i++) {        // without pruning: i <= n
            path.add(i);
            backtrack(n, k, i + 1, path, out);
            path.removeLast();
        }
    }

    /* Approach 2 - lexicographic generation: find the rightmost position that can still grow, bump it, reset the rest. */
    public static List<List<Integer>> combineIterative(int n, int k) {
        List<List<Integer>> out = new ArrayList<>();
        int[] c = new int[k];
        for (int i = 0; i < k; i++) c[i] = i + 1;              // first combination: 1, 2, ..., k
        while (true) {
            List<Integer> combo = new ArrayList<>();
            for (int x : c) combo.add(x);
            out.add(combo);
            int i = k - 1;
            while (i >= 0 && c[i] == n - k + 1 + i) i--;       // position i is at its maximum value
            if (i < 0) return out;
            c[i]++;
            for (int j = i + 1; j < k; j++) c[j] = c[j - 1] + 1;
        }
    }
}
