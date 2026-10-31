package CombinationSumII;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/*
 * 40. Combination Sum II — https://leetcode.com/problems/combination-sum-ii/
 * Find all unique combinations of candidates (may contain duplicates) that sum to target; each number may be used once.
 * Input: [10,1,2,7,6,1,5], 8 -> [[1,1,6],[1,2,5],[1,7],[2,6]] · [2,5,2,1,2], 5 -> [[1,2,2],[5]]
 * Explanation: README.md
 */
public class CombinationSumII {
    public static void main(String[] args) {
        System.out.println(combinationSum2(new int[]{10, 1, 2, 7, 6, 1, 5}, 8));      // [[1, 1, 6], [1, 2, 5], [1, 7], [2, 6]]
        System.out.println(combinationSum2Counts(new int[]{2, 5, 2, 1, 2}, 5));      // [[1, 2, 2], [5]]
    }

    /* Approach 1 - sort; recurse with i + 1 (no reuse) and skip equal values at the same depth (no duplicate combos). */
    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
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
        for (int i = start; i < c.length && c[i] <= remaining; i++) {
            if (i > start && c[i] == c[i - 1]) continue;
            path.add(c[i]);
            backtrack(c, remaining - c[i], i + 1, path, out);
            path.removeLast();
        }
    }

    /* Approach 2 - work on distinct values with counts: for each value decide how many copies (0..count) to take. */
    public static List<List<Integer>> combinationSum2Counts(int[] candidates, int target) {
        TreeMap<Integer, Integer> count = new TreeMap<>();
        for (int x : candidates) count.merge(x, 1, Integer::sum);
        List<Map.Entry<Integer, Integer>> entries = new ArrayList<>(count.entrySet());
        List<List<Integer>> out = new ArrayList<>();
        choose(entries, 0, target, new ArrayList<>(), out);
        return out;
    }

    private static void choose(List<Map.Entry<Integer, Integer>> entries, int idx, int remaining, List<Integer> path, List<List<Integer>> out) {
        if (remaining == 0) {
            out.add(new ArrayList<>(path));
            return;
        }
        if (idx == entries.size() || entries.get(idx).getKey() > remaining) return;
        int value = entries.get(idx).getKey(), max = entries.get(idx).getValue();
        int added = 0;
        for (int take = 1; take <= max && take * value <= remaining; take++) {     // take 1, 2, ... copies first
            path.add(value);
            added++;
            choose(entries, idx + 1, remaining - take * value, path, out);
        }
        for (int i = 0; i < added; i++) path.removeLast();
        choose(entries, idx + 1, remaining, path, out);                            // take none of this value
    }
}
