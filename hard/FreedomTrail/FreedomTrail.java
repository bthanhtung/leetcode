package FreedomTrail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * 514. Freedom Trail — https://leetcode.com/problems/freedom-trail/
 * A ring of letters starts with ring[0] at 12 o'clock. To spell key, rotate the ring one step clockwise or
 * counter-clockwise per move until the needed letter is at 12, then press the button (one more step).
 * Return the minimum number of steps.
 * Input: ring = "godding", key = "gd" -> 4 · ring = "godding", key = "godding" -> 13
 * Explanation: README.md
 */
public class FreedomTrail {
    public static void main(String[] args) {
        System.out.println(findRotateSteps("godding", "gd"));              // 4
        System.out.println(findRotateStepsMemo("godding", "godding"));     // 13
    }

    private static int distance(int a, int b, int n) {
        int d = Math.abs(a - b);
        return Math.min(d, n - d);                       // clockwise or the other way around
    }

    /* Approach 1 - best(pos, k): ring position pos is at 12, key[k..] remains. Try every position holding key[k]. */
    public static int findRotateStepsMemo(String ring, String key) {
        return best(ring, key, 0, 0, new Integer[ring.length()][key.length()]) + key.length();     // + one press per letter
    }

    private static int best(String ring, String key, int pos, int k, Integer[][] memo) {
        if (k == key.length()) return 0;
        if (memo[pos][k] != null) return memo[pos][k];
        int result = Integer.MAX_VALUE;
        for (int i = 0; i < ring.length(); i++) {
            if (ring.charAt(i) == key.charAt(k)) {
                result = Math.min(result, distance(pos, i, ring.length()) + best(ring, key, i, k + 1, memo));
            }
        }
        return memo[pos][k] = result;
    }

    /* Approach 2 - bottom-up over key letters, only tracking positions that hold the current letter. O(k * m^2) worst. */
    public static int findRotateSteps(String ring, String key) {
        int n = ring.length();
        List<List<Integer>> where = new ArrayList<>();
        for (int c = 0; c < 26; c++) where.add(new ArrayList<>());
        for (int i = 0; i < n; i++) where.get(ring.charAt(i) - 'a').add(i);
        int[] cost = new int[n];
        Arrays.fill(cost, Integer.MAX_VALUE);
        cost[0] = 0;                                      // before spelling, position 0 is at 12
        List<Integer> current = List.of(0);
        for (char c : key.toCharArray()) {
            List<Integer> next = where.get(c - 'a');
            int[] nextCost = new int[n];
            Arrays.fill(nextCost, Integer.MAX_VALUE);
            for (int to : next) {
                for (int from : current) {
                    nextCost[to] = Math.min(nextCost[to], cost[from] + distance(from, to, n));
                }
            }
            cost = nextCost;
            current = next;
        }
        int best = Integer.MAX_VALUE;
        for (int p : current) best = Math.min(best, cost[p]);
        return best + key.length();
    }
}
