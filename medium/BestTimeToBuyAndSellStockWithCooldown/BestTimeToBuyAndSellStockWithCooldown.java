package BestTimeToBuyAndSellStockWithCooldown;

/*
 * 309. Best Time to Buy and Sell Stock with Cooldown — https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/
 * Buy and sell as many times as you like (one share at a time), but after selling you must wait one day before buying.
 * Return the maximum profit.
 * Input: [1,2,3,0,2] -> 3 (buy, sell, cooldown, buy, sell) · [1] -> 0
 * Explanation: README.md
 */
public class BestTimeToBuyAndSellStockWithCooldown {
    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{1, 2, 3, 0, 2}));         // 3
        System.out.println(maxProfitMemo(new int[]{1, 2, 3, 0, 2}));     // 3
    }

    /* Approach 1 - best(day, holding): skip, or act (selling jumps two days ahead because of the cooldown). Memoized. */
    public static int maxProfitMemo(int[] prices) {
        return best(prices, 0, 0, new Integer[prices.length][2]);
    }

    private static int best(int[] p, int day, int holding, Integer[][] memo) {
        if (day >= p.length) return 0;
        if (memo[day][holding] == null) {
            int skip = best(p, day + 1, holding, memo);
            int act = holding == 1 ? p[day] + best(p, day + 2, 0, memo) : -p[day] + best(p, day + 1, 1, memo);
            memo[day][holding] = Math.max(skip, act);
        }
        return memo[day][holding];
    }

    /*
     * Approach 2 - three states at the end of each day: hold (own a share), sold (sold today), rest (no share, free to buy).
     * Time O(n), space O(1).
     */
    public static int maxProfit(int[] prices) {
        int hold = Integer.MIN_VALUE / 2, sold = 0, rest = 0;
        for (int p : prices) {
            int prevSold = sold;
            sold = hold + p;                        // sell the share we held
            hold = Math.max(hold, rest - p);        // keep holding, or buy (only from rest: not right after selling)
            rest = Math.max(rest, prevSold);        // stay out, or finish the cooldown
        }
        return Math.max(sold, rest);
    }
}
