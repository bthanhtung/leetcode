package CombinationSum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * 39. Combination Sum — https://leetcode.com/problems/combination-sum/
 * Given distinct positive candidates and a target, return all unique combinations that sum to target. A number may be
 * used any number of times. Combinations differ by the count of at least one number.
 * Input: [2,3,6,7], 7 -> [[2,2,3],[7]] · [2,3,5], 8 -> [[2,2,2,2],[2,3,3],[3,5]] · [2], 1 -> []
 * Explanation: README.md
 */
public class CombinationSum {
    public static void main(String[] args) {
        System.out.println(combinationSum(new int[]{2, 3, 6, 7}, 7));     // [[2, 2, 3], [7]]
        System.out.println(combinationSumDp(new int[]{2, 3, 5}, 8));      // [[2, 2, 2, 2], [2, 3, 3], [3, 5]]
    }

    /*
     * Approach 1 - backtracking; after choosing candidates[i], recurse from i again (reuse allowed) but never from
     * an earlier index, so [2,3] and [3,2] are not both produced. Sorting lets us stop once a candidate is too big.
     */
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        int[] c = candidates.clone();
        Arrays.sort(c);
        List<List<Integer>> out = new ArrayList<>();
        backtrack(c, target, 0, new ArrayList<>(), out);
        return out;
    }

    private static void backtrack(int[] c, int remaining, int start, List<Integer> path, List<List<Integer>> out) {
        if (remaining == 0) {
            out.add(new ArrayList<>(path));
            return;
        }
        for (int i = start; i < c.length && c[i] <= remaining; i++) {     // sorted: later ones are even bigger
            path.add(c[i]);
            backtrack(c, remaining - c[i], i, path, out);
            path.removeLast();
        }
    }

    /*
     * Approach 2 - DP over the target like coin change: for each candidate (outer loop, to avoid permutations),
     * combos[t] += every combo of t - candidate extended with candidate.
     */
    public static List<List<Integer>> combinationSumDp(int[] candidates, int target) {
        List<List<List<Integer>>> combos = new ArrayList<>();
        for (int t = 0; t <= target; t++) combos.add(new ArrayList<>());
        combos.get(0).add(new ArrayList<>());
        int[] c = candidates.clone();
        Arrays.sort(c);
        for (int x : c) {
            for (int t = x; t <= target; t++) {
                for (List<Integer> prev : combos.get(t - x)) {
                    List<Integer> next = new ArrayList<>(prev);
                    next.add(x);
                    combos.get(t).add(next);
                }
            }
        }
        return combos.get(target);
    }
}
