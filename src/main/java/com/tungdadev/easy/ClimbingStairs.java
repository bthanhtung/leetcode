package com.tungdadev.easy;

/**
 * @author TungDaDev
 * @since 09/01/2026 - 00:42
 */
/**
 * Link: https://leetcode.com/problems/climbing-stairs/description/
 */
public class ClimbingStairs {
    public static void main(String[] args) {
        System.out.println(climbStairs(2)); // 2
        System.out.println(climbStairs(3)); // 3
        System.out.println(climbStairs(4)); // 5
        System.out.println(climbStairs(5)); // 8

    }

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
