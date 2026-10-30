package com.tungdadev.easy;

/*
 * Link: https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
 * You are given an array prices where prices[i] is the price of a given stock on the ith day.
 * You want to maximize your profit by choosing a single day to buy one stock and choosing a different day
 * in the future to sell that stock. Return the maximum profit you can achieve from this transaction.
 * If you cannot achieve any profit, return 0.
 * Input: prices = [7,1,5,3,6,4]  -> Output: 5 (buy on day 2 at 1, sell on day 5 at 6)
 * Input: prices = [7,6,4,3,1]    -> Output: 0 (prices only go down)
 * Constraints: 1 <= prices.length <= 10^5, 0 <= prices[i] <= 10^4
 * Explanation: BestTimeToBuyAndSellStock.md
 */
public class BestTimeToBuyAndSellStock {
    /*
     * Just to test
     */
    public static void main(String[] args) {
        int[] prices = {7, 1, 5, 3, 6, 4};
        System.out.println(maxProfitBruteForce(prices));   // 5
        System.out.println(maxProfitOnePass(prices));      // 5
        System.out.println(maxProfitDp(prices));           // 5
        System.out.println(maxProfitOnePass(new int[]{7, 6, 4, 3, 1}));   // 0
    }

    /*
     * Approach 1 - brute force: try every (buy, sell) pair with buy < sell.
     * Time O(n^2), space O(1). Too slow for n = 10^5, but it is the reference the others are checked against.
     */
    public static int maxProfitBruteForce(int[] prices) {
        int best = 0;
        for (int buy = 0; buy < prices.length; buy++) {
            for (int sell = buy + 1; sell < prices.length; sell++) {
                best = Math.max(best, prices[sell] - prices[buy]);
            }
        }
        return best;
    }

    /*
     * Approach 2 - one pass: the best sale on day i buys at the lowest price seen before day i.
     * Keep that minimum while walking once. Time O(n), space O(1).
     */
    public static int maxProfitOnePass(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int best = 0;
        for (int price : prices) {
            if (price < minPrice) {
                minPrice = price;                       // a cheaper day to buy
            } else {
                best = Math.max(best, price - minPrice); // sell today?
            }
        }
        return best;
    }

    /*
     * Approach 3 - state machine DP: after each day we either hold a share or not.
     *   hold = max(hold, -price)        keep holding, or buy today (only one buy allowed, so from 0)
     *   free = max(free, hold + price)  stay out, or sell what we hold today
     * Time O(n), space O(1). Same answer as approach 2, but it generalises to the other
     * stock problems (II: unlimited trades, III/IV: k trades, with cooldown, with fee).
     */
    public static int maxProfitDp(int[] prices) {
        int hold = Integer.MIN_VALUE;   // best cash while holding a share
        int free = 0;                   // best cash while holding nothing
        for (int price : prices) {
            hold = Math.max(hold, -price);
            free = Math.max(free, hold + price);
        }
        return free;
    }
}
