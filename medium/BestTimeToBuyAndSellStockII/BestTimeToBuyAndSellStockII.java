package BestTimeToBuyAndSellStockII;

/*
 * 122. Best Time to Buy and Sell Stock II — https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/
 * prices[i] is a stock price on day i. You may hold at most one share at a time but buy and sell any number of times
 * (even on the same day). Return the maximum profit.
 * Input: [7,1,5,3,6,4] -> 7 (buy 1 sell 5, buy 3 sell 6) · [1,2,3,4,5] -> 4 · [7,6,4,3,1] -> 0
 * Explanation: README.md
 */
public class BestTimeToBuyAndSellStockII {
    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{7, 1, 5, 3, 6, 4}));            // 7
        System.out.println(maxProfitDp(new int[]{1, 2, 3, 4, 5}));             // 4
    }

    /* Approach 1 - collect every positive day-to-day difference. Time O(n), space O(1). */
    public static int maxProfit(int[] prices) {
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            profit += Math.max(0, prices[i] - prices[i - 1]);
        }
        return profit;
    }

    /* Approach 2 - buy at each local minimum (valley), sell at the next local maximum (peak). Time O(n). */
    public static int maxProfitValleyPeak(int[] prices) {
        int i = 0, n = prices.length, profit = 0;
        while (i < n - 1) {
            while (i < n - 1 && prices[i] >= prices[i + 1]) i++;       // walk down to a valley
            int valley = prices[i];
            while (i < n - 1 && prices[i] <= prices[i + 1]) i++;       // walk up to a peak
            profit += prices[i] - valley;
        }
        return profit;
    }

    /* Approach 3 - DP with two states: best cash when holding / not holding a share at the end of the day. Time O(n). */
    public static int maxProfitDp(int[] prices) {
        int hold = -prices[0], free = 0;
        for (int i = 1; i < prices.length; i++) {
            int newHold = Math.max(hold, free - prices[i]);            // keep holding, or buy today
            int newFree = Math.max(free, hold + prices[i]);            // stay out, or sell today
            hold = newHold;
            free = newFree;
        }
        return free;
    }
}
