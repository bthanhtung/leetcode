package ClimbingStairs;

import java.util.HashMap;
import java.util.Map;

/*
 * 70. Climbing Stairs — https://leetcode.com/problems/climbing-stairs/
 * You are climbing a staircase with n steps. Each time you can climb 1 or 2 steps. In how many distinct ways can you reach the top?
 * Input: n = 2 -> 2 (1+1, 2) · n = 3 -> 3 (1+1+1, 1+2, 2+1)      Constraints: 1 <= n <= 45
 * Explanation: README.md
 */
public class ClimbingStairs {
    public static void main(String[] args) {
        System.out.println(climbStairsRecursive(5));   // 8
        System.out.println(climbStairsMemo(10));       // 89
        System.out.println(climbStairsTable(4));       // 5
        System.out.println(climbStairs(45));           // 1836311903
    }

    /* Approach 1 - plain recursion: ways(n) = ways(n-1) + ways(n-2). Time O(2^n), space O(n). Too slow past n ~ 40. */
    public static int climbStairsRecursive(int n) {
        if (n <= 2) {
            return n;
        }
        return climbStairsRecursive(n - 1) + climbStairsRecursive(n - 2);
    }

    /* Approach 2 - the same recursion, remembering answers (top-down DP). Time O(n), space O(n). */
    public static int climbStairsMemo(int n) {
        return memo(n, new HashMap<>());
    }

    private static int memo(int n, Map<Integer, Integer> cache) {
        if (n <= 2) {
            return n;
        }
        Integer known = cache.get(n);
        if (known != null) {
            return known;
        }
        int ways = memo(n - 1, cache) + memo(n - 2, cache);
        cache.put(n, ways);
        return ways;
    }

    /* Approach 3 - bottom-up table dp[i] = dp[i-1] + dp[i-2]. Time O(n), space O(n). */
    public static int climbStairsTable(int n) {
        if (n <= 2) {
            return n;
        }
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    /* Approach 4 - only the last two values matter: two variables (Fibonacci). Time O(n), space O(1). */
    public static int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }
        int first = 1;
        int second = 2;
        for (int i = 3; i <= n; i++) {
            int third = first + second;
            first = second;
            second = third;
        }
        return second;
    }
}
