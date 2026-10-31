package CoinChange;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/*
 * 322. Coin Change — https://leetcode.com/problems/coin-change/
 * Fewest coins (unlimited supply of each denomination) that make up amount, or -1 if impossible.
 * Input: coins = [1,2,5], amount = 11 -> 3 (5+5+1) · [2], 3 -> -1 · [1], 0 -> 0
 * Explanation: README.md
 */
public class CoinChange {
    public static void main(String[] args) {
        System.out.println(coinChange(new int[]{1, 2, 5}, 11));     // 3
    }

    /* Approach 1 - dp[a] = min coins for a = 1 + min(dp[a - c]). Time O(amount * coins). */
    public static int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);                 // "infinity": no answer uses more than amount coins
        dp[0] = 0;
        for (int a = 1; a <= amount; a++) {
            for (int c : coins) if (c <= a) dp[a] = Math.min(dp[a], dp[a - c] + 1);
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

    /* Approach 2 - BFS from 0: level = number of coins; the first time we reach amount is the minimum. */
    public static int coinChangeBfs(int[] coins, int amount) {
        if (amount == 0) return 0;
        boolean[] seen = new boolean[amount + 1];
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(0);
        for (int level = 1; !queue.isEmpty(); level++) {
            for (int size = queue.size(); size > 0; size--) {
                int cur = queue.poll();
                for (int c : coins) {
                    long next = (long) cur + c;
                    if (next == amount) return level;
                    if (next < amount && !seen[(int) next]) {
                        seen[(int) next] = true;
                        queue.add((int) next);
                    }
                }
            }
        }
        return -1;
    }
}
