package RichestCustomerWealth;

import java.util.Arrays;

/*
 * 1672. Richest Customer Wealth — https://leetcode.com/problems/richest-customer-wealth/
 * accounts[i][j] is the money customer i has in bank j. A customer's wealth is the sum of their accounts.
 * Return the wealth of the richest customer.
 * Input: [[1,2,3],[3,2,1]] -> 6 · [[1,5],[7,3],[3,5]] -> 10
 * Explanation: README.md
 */
public class RichestCustomerWealth {
    public static void main(String[] args) {
        System.out.println(maximumWealth(new int[][]{{1, 5}, {7, 3}, {3, 5}}));           // 10
        System.out.println(maximumWealthStream(new int[][]{{1, 2, 3}, {3, 2, 1}}));      // 6
    }

    /* Approach 1 - sum each row, keep the maximum. Time O(m * n), space O(1). */
    public static int maximumWealth(int[][] accounts) {
        int best = 0;
        for (int[] customer : accounts) {
            int wealth = 0;
            for (int money : customer) {
                wealth += money;
            }
            best = Math.max(best, wealth);
        }
        return best;
    }

    /* Approach 2 - the same with streams. */
    public static int maximumWealthStream(int[][] accounts) {
        return Arrays.stream(accounts).mapToInt(row -> Arrays.stream(row).sum()).max().orElse(0);
    }
}
