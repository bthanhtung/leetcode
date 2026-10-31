package BestTimeToBuyAndSellStockIII;

/*
 * 123. Best Time to Buy and Sell Stock III — https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/
 * Find the maximum profit with at most two transactions (sell before buying again).
 * Input: [3,3,5,0,0,3,1,4] -> 6 · [1,2,3,4,5] -> 4 · [7,6,4,3,1] -> 0
 * Explanation: README.md
 */
public class BestTimeToBuyAndSellStockIII {
    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{3, 3, 5, 0, 0, 3, 1, 4}));         // 6
        System.out.println(maxProfitSplit(new int[]{1, 2, 3, 4, 5}));            // 4
    }

    /*
     * Approach 1 - left[i] = best single transaction in days 0..i, right[i] = best in days i..n-1. Two transactions
     * that don't overlap split at some day: answer = max left[i] + right[i]. Time O(n), space O(n).
     */
    public static int maxProfitSplit(int[] prices) {
        int n = prices.length;
        int[] left = new int[n], right = new int[n];
        int low = prices[0];
        for (int i = 1; i < n; i++) {
            low = Math.min(low, prices[i]);
            left[i] = Math.max(left[i - 1], prices[i] - low);
        }
        int high = prices[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            high = Math.max(high, prices[i]);
            right[i] = Math.max(right[i + 1], high - prices[i]);
        }
        int best = 0;
        for (int i = 0; i < n; i++) best = Math.max(best, left[i] + right[i]);
        return best;
    }

    /*
     * Approach 2 - four running states: best cash after the first buy, first sell, second buy, second sell.
     * Each state builds on the previous one. Time O(n), space O(1).
     */
    public static int maxProfit(int[] prices) {
        int buy1 = Integer.MIN_VALUE, sell1 = 0, buy2 = Integer.MIN_VALUE, sell2 = 0;
        for (int p : prices) {
            buy1 = Math.max(buy1, -p);
            sell1 = Math.max(sell1, buy1 + p);
            buy2 = Math.max(buy2, sell1 - p);
            sell2 = Math.max(sell2, buy2 + p);
        }
        return sell2;
    }
}
