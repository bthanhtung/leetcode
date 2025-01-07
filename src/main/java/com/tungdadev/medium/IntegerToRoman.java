package com.tungdadev.medium;

/**
 * Link: https://leetcode.com/problems/integer-to-roman/
 */
public class IntegerToRoman {
    /*
     * Just to test
     */
    public static void main(String[] args) {
        System.out.println(intToRoman(3749)); // MMMDCCXLIX
        System.out.println(intToRoman(58)); // LVIII
        System.out.println(intToRoman(1994)); // MCMXCIV
    }

    /*
     * Solution implement here
     */
    public static String intToRoman(int num) {
        int[] intValue = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanValue = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < intValue.length; i++) {
            while (num >= intValue[i]) {
                result.append(romanValue[i]);
                num -= intValue[i];
            }
        }
        return result.toString();
    }
}
