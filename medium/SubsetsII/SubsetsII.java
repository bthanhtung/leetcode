package SubsetsII;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * 90. Subsets II — https://leetcode.com/problems/subsets-ii/
 * Given numbers that may contain duplicates, return all possible subsets without duplicate subsets, in any order.
 * Input: [1,2,2] -> [[],[1],[1,2],[1,2,2],[2],[2,2]] · [0] -> [[],[0]]
 * Explanation: README.md
 */
public class SubsetsII {
    public static void main(String[] args) {
        System.out.println(subsetsWithDup(new int[]{1, 2, 2}));          // [[], [1], [1, 2], [1, 2, 2], [2], [2, 2]]
        System.out.println(subsetsWithDupIterative(new int[]{1, 2, 2}));
    }

    /* Approach 1 - sort; at each level skip a value equal to the previous one tried at the same level. */
    public static List<List<Integer>> subsetsWithDup(int[] nums) {
        int[] a = nums.clone();
        Arrays.sort(a);
        List<List<Integer>> out = new ArrayList<>();
        backtrack(a, 0, new ArrayList<>(), out);
        return out;
    }

    private static void backtrack(int[] a, int start, List<Integer> path, List<List<Integer>> out) {
        out.add(new ArrayList<>(path));
        for (int i = start; i < a.length; i++) {
            if (i > start && a[i] == a[i - 1]) continue;      // same value already tried at this position
            path.add(a[i]);
            backtrack(a, i + 1, path, out);
            path.removeLast();
        }
    }

    /* Approach 2 - like Subsets' doubling, but a repeated value only extends the subsets created in the previous step. */
    public static List<List<Integer>> subsetsWithDupIterative(int[] nums) {
        int[] a = nums.clone();
        Arrays.sort(a);
        List<List<Integer>> out = new ArrayList<>();
        out.add(new ArrayList<>());
        int lastBatchStart = 0;
        for (int i = 0; i < a.length; i++) {
            int from = i > 0 && a[i] == a[i - 1] ? lastBatchStart : 0;
            int size = out.size();
            for (int j = from; j < size; j++) {
                List<Integer> next = new ArrayList<>(out.get(j));
                next.add(a[i]);
                out.add(next);
            }
            lastBatchStart = size;
        }
        return out;
    }
}
