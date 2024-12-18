package com.tungdadev.easy;

/*
 * https://leetcode.com/problems/sqrtx
 * Given a non-negative integer x, return the square root of x rounded down to the nearest integer.
 * The returned integer should be non-negative as well.
 * You must not use any built-in exponent function or operator.
 * For example, do not use pow(x, 0.5) in c++ or x ** 0.5 in python.
 */
public class SqrtX {
    /*
     * Just to test
     */
    public static void main(String[] args) {
        System.out.println(mySqrt(4));
    }

    /*
     * Solution implement here
     */
    public static int mySqrt(int x) {
        if(x <= 1)
            return x;
        return binarySearch(x);
    }

    static int binarySearch(int x) {
        int left = 1 , right = x / 2 , mid , ans = 0;
        long temp;
        while(left <= right) {
            mid = (left + (right - left) / 2);
            temp = (long)mid * (long)mid;
            // mid * mid can be large, so use long
            if(temp == x)
                return mid;
            if(temp < x) {
                ans = mid;
                left = mid + 1;
            }
            else
                right = mid - 1;
        }
        return ans;
    }
}
