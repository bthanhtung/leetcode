package FrogJump;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * 403. Frog Jump — https://leetcode.com/problems/frog-jump/
 * Stones at increasing positions; the frog starts on the first stone and its first jump is 1 unit. If the last jump was
 * k units, the next must be k - 1, k or k + 1 (forward only). Can it reach the last stone?
 * Input: [0,1,3,5,6,8,12,17] -> true · [0,1,2,3,4,8,9,11] -> false
 * Explanation: README.md
 */
public class FrogJump {
    public static void main(String[] args) {
        System.out.println(canCross(new int[]{0, 1, 3, 5, 6, 8, 12, 17}));        // true
        System.out.println(canCrossMemo(new int[]{0, 1, 2, 3, 4, 8, 9, 11}));     // false
    }

    /* Approach 1 - for every stone, the set of jump sizes that can land on it; push k-1, k, k+1 forward. O(n^2). */
    public static boolean canCross(int[] stones) {
        Map<Integer, Set<Integer>> jumps = new HashMap<>();
        for (int s : stones) jumps.put(s, new HashSet<>());
        jumps.get(stones[0]).add(0);
        for (int s : stones) {
            for (int k : jumps.get(s)) {
                for (int step = k - 1; step <= k + 1; step++) {
                    if (step > 0 && jumps.containsKey(s + step)) jumps.get(s + step).add(step);
                }
            }
        }
        return !jumps.get(stones[stones.length - 1]).isEmpty();
    }

    /* Approach 2 - DFS on (stone index, last jump) with memoization of failures. */
    public static boolean canCrossMemo(int[] stones) {
        Map<Integer, Integer> index = new HashMap<>();
        for (int i = 0; i < stones.length; i++) index.put(stones[i], i);
        return dfs(stones, index, 0, 0, new HashSet<>());
    }

    private static boolean dfs(int[] stones, Map<Integer, Integer> index, int i, int k, Set<Long> failed) {
        if (i == stones.length - 1) return true;
        long key = (long) i * 4096 + k;
        if (failed.contains(key)) return false;
        for (int step = k + 1; step >= k - 1; step--) {               // try longer jumps first: they finish sooner
            if (step <= 0) continue;
            Integer next = index.get(stones[i] + step);
            if (next != null && dfs(stones, index, next, step, failed)) return true;
        }
        failed.add(key);
        return false;
    }
}
