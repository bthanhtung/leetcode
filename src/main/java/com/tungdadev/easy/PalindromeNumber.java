package com.tungdadev.easy;

/*
 * Link: https://leetcode.com/problems/palindrome-number/description/
 * Given an integer x, return true if x is a palindrome, and false otherwise.
 */
public class PalindromeNumber {
    /*
     * Just to test
     */
    public static void main(String[] args) {
        System.out.println(isPalindrome(121));
    }

    /*
     * Solution implement here
     */
    public static boolean isPalindrome(int x) {
        if(x < 0) return false;
        long reversedNumber = 0;
        int y = x;

        while (y > 0) {
            reversedNumber = reversedNumber * 10 + y % 10;
            y /= 10;
        }
        return reversedNumber == x;
    }
}
