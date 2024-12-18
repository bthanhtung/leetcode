package com.tungdadev.medium;

/**
 * Link: https://leetcode.com/problems/reverse-integer/description/
 * Given a signed 32-bit integer x, return x with its digits reversed.
 * If reversing x causes the value to go outside the signed 32-bit integer range [-231, 231 - 1], then return 0.
 */
public class ReverseInteger {
    /*
     * Just to test
     */
    public static void main(String[] args) {
        System.out.println(reverse(123));
    }

    /*
     * Solution implement here
     */
    public static int reverse(int x) {
        long rev = 0;
        while(x != 0){
            int digit = x % 10;
            rev = 10 * rev + digit;
            if(rev > Integer.MAX_VALUE || rev < Integer.MIN_VALUE) return 0;
            x /= 10;
        }
        return (int) rev;
    }
}
