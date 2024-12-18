package com.tungdadev.medium;

/**
 * Link: https://leetcode.com/problems/string-to-integer-atoi/description/
 * @since 18/12/2024 - 13:56
 */
public class StringToInteger {
    /*
     * Just to test
     */
    public static void main(String[] args) {
        System.out.println(myAtoi("42"));
    }

    /*
     * Solution implement here
     */
    public static int myAtoi(String string) {
        if(string.isEmpty()) {
            return 0;
        }

        int i = 0;
        while (i < string.length() && string.charAt(i) == ' ') {
            i++;
        }

        if(i >= string.length()) {
            return 0;
        }

        int signCheck = 1, temp = 0;
        if(string.charAt(i) == '-' || string.charAt(i) == '+') {
            signCheck = string.charAt(i++) == '-' ? -1 : 1;
        }

        while (i < string.length() && string.charAt(i) >= '0' && string.charAt(i) <= '9') {
            if (temp > Integer.MAX_VALUE / 10 ||
                    (temp == Integer.MAX_VALUE / 10 && string.charAt(i) - '0' > Integer.MAX_VALUE % 10)) {
                return (signCheck == 1) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            temp = 10 * temp + (string.charAt(i) - '0');
            i++;
        }
        return temp * signCheck;
    }
}
