package com.tungdadev.easy;

/**
 * @author TungDaDev
 * @since 24/06/2025 - 23:39
 */
public class PlusOne {
    public static void main(String[] args) {
        int[] digits = {1, 2, 9};
        int[] result = plusOne(digits);
        for (int digit : result) {
            System.out.print(digit + " ");
        }
    }

    public static int[] plusOne(int[] digits) {
        if(digits == null || digits.length == 0) {
            return new int[]{1};
        }

        if(digits[digits.length - 1] < 9) {
            digits[digits.length - 1]++;
            return digits;
        }
        for (int i = digits.length - 1; i >= 0; i--) {
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }
            digits[i] = 0;
        }
        int[] newDigits = new int[digits.length + 1];
        newDigits[0] = 1;
        for (int i = 1; i < newDigits.length; i++) {
            newDigits[i] = digits[i - 1];
        }
        return newDigits;
    }
}
