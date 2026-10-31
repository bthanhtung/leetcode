package BestTimeToBuyAndSellStockIV;

import java.util.Arrays;

/*
 * 188. Best Time to Buy and Sell Stock IV — https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/
 * Maximum profit with at most k transactions (sell before buying again).
 * Input: k = 2, [2,4,1] -> 2 · k = 2, [3,2,6,5,0,3] -> 7
 * Explanation: README.md
 */
public class BestTimeToBuyAndSellStockIV {
    public static void main(String[] args) {
        System.out.println(maxProfit(2, new int[]{2, 4, 1}));             // 2
        System.out.println(maxProfitTable(2, new int[]{3, 2, 6, 5, 0, 3})); // 7
    }

    /*
     * Approach 1 - buy[j] / sell[j] = best cash after the j-th buy / sell, updated every day like problem 123 but for
     * k pairs. If k >= n/2 the limit never binds: take every rise (problem 122). Time O(n * min(k, n)), space O(k).
     */
    public static int maxProfit(int k, int[] prices) {
        int n = prices.length;
        if (k >= n / 2) {
            int profit = 0;
            for (int i = 1; i < n; i++) profit += Math.max(0, prices[i] - prices[i - 1]);
            return profit;
        }
        int[] buy = new int[k + 1], sell = new int[k + 1];
        Arrays.fill(buy, Integer.MIN_VALUE);
        for (int p : prices) {
            for (int j = 1; j <= k; j++) {
                buy[j] = Math.max(buy[j], sell[j - 1] - p);
                sell[j] = Math.max(sell[j], buy[j] + p);
            }
        }
        return sell[k];
    }

    /*
     * Approach 2 - dp[j][i] = best profit with at most j transactions in days 0..i:
     * dp[j][i] = max(dp[j][i-1], prices[i] + max over m < i of (dp[j-1][m] - prices[m])); the inner max is a running
     * variable. Time O(k * n), space O(k * n).
     */
    public static int maxProfitTable(int k, int[] prices) {
        int n = prices.length;
        if (n == 0) return 0;
        int[][] dp = new int[k + 1][n];
        for (int j = 1; j <= k; j++) {
            int bestBuy = -prices[0];                       // max of dp[j-1][m] - prices[m]
            for (int i = 1; i < n; i++) {
                dp[j][i] = Math.max(dp[j][i - 1], prices[i] + bestBuy);
                bestBuy = Math.max(bestBuy, dp[j - 1][i] - prices[i]);
            }
        }
        return dp[k][n - 1];
    }
}
