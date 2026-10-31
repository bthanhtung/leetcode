package CoinChangeII;

/*
 * 518. Coin Change II — https://leetcode.com/problems/coin-change-ii/
 * Return the number of combinations of coins (unlimited supply of each) that make up amount. Order does not matter.
 * Input: amount = 5, coins = [1,2,5] -> 4 (5, 2+2+1, 2+1+1+1, 1+1+1+1+1) · amount = 3, coins = [2] -> 0
 * Explanation: README.md
 */
public class CoinChangeII {
    public static void main(String[] args) {
        System.out.println(change(5, new int[]{1, 2, 5}));        // 4
        System.out.println(changeMemo(3, new int[]{2}));          // 0
    }

    /* Approach 1 - ways(i, amount): use coin i again, or move on to coin i + 1. Memoized. Time O(n * amount). */
    public static int changeMemo(int amount, int[] coins) {
        return ways(coins, 0, amount, new Integer[coins.length][amount + 1]);
    }

    private static int ways(int[] coins, int i, int amount, Integer[][] memo) {
        if (amount == 0) return 1;
        if (i == coins.length) return 0;
        if (memo[i][amount] == null) {
            int use = coins[i] <= amount ? ways(coins, i, amount - coins[i], memo) : 0;
            memo[i][amount] = use + ways(coins, i + 1, amount, memo);
        }
        return memo[i][amount];
    }

    /* Approach 2 - dp[i][a] = ways to make a with the first i coins. Time O(n * amount), space O(n * amount). */
    public static int changeTable(int amount, int[] coins) {
        int n = coins.length;
        int[][] dp = new int[n + 1][amount + 1];
        for (int i = 0; i <= n; i++) dp[i][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int a = 1; a <= amount; a++) {
                dp[i][a] = dp[i - 1][a] + (coins[i - 1] <= a ? dp[i][a - coins[i - 1]] : 0);
            }
        }
        return dp[n][amount];
    }

    /*
     * Approach 3 - one array; coins in the OUTER loop so each combination is counted once, in coin order.
     * (Swapping the loops would count permutations instead - problem 377.) Space O(amount).
     */
    public static int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int coin : coins) {
            for (int a = coin; a <= amount; a++) {
                dp[a] += dp[a - coin];
            }
        }
        return dp[amount];
    }
}
