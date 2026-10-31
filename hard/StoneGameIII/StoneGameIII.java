package StoneGameIII;

/*
 * 1406. Stone Game III — https://leetcode.com/problems/stone-game-iii/
 * Stones in a row with values (possibly negative). Alice and Bob alternate, each taking 1, 2 or 3 stones from the front.
 * Both play optimally to maximize their own total. Return "Alice", "Bob" or "Tie".
 * Input: [1,2,3,7] -> "Bob" · [1,2,3,-9] -> "Alice" · [1,2,3,6] -> "Tie"
 * Explanation: README.md
 */
public class StoneGameIII {
    public static void main(String[] args) {
        System.out.println(stoneGameIII(new int[]{1, 2, 3, 7}));        // Bob
        System.out.println(stoneGameIIIMemo(new int[]{1, 2, 3, -9}));   // Alice
    }

    private static String verdict(int diff) {
        return diff > 0 ? "Alice" : diff < 0 ? "Bob" : "Tie";
    }

    /*
     * Approach 1 - best(i) = the best (my score - opponent's score) the player to move can get from stone i on:
     * take k stones worth sum, then the opponent gets best(i + k), so: max over k of sum - best(i + k). Memoized.
     */
    public static String stoneGameIIIMemo(int[] values) {
        return verdict(best(values, 0, new Integer[values.length]));
    }

    private static int best(int[] v, int i, Integer[] memo) {
        if (i >= v.length) return 0;
        if (memo[i] == null) {
            int result = Integer.MIN_VALUE, sum = 0;
            for (int k = 0; k < 3 && i + k < v.length; k++) {
                sum += v[i + k];
                result = Math.max(result, sum - best(v, i + k + 1, memo));
            }
            memo[i] = result;
        }
        return memo[i];
    }

    /* Approach 2 - the same recurrence filled from the back; only dp[i+1..i+3] are needed. Time O(n), space O(1). */
    public static String stoneGameIII(int[] values) {
        int[] dp = new int[4];                         // dp[(i) % 4] rolling over the last three positions
        for (int i = values.length - 1; i >= 0; i--) {
            int result = Integer.MIN_VALUE, sum = 0;
            for (int k = 0; k < 3 && i + k < values.length; k++) {
                sum += values[i + k];
                result = Math.max(result, sum - dp[(i + k + 1) % 4]);
            }
            dp[i % 4] = result;
        }
        return verdict(dp[0]);
    }
}
