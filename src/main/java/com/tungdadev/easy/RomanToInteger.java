package com.tungdadev.easy;

/*
 * https://leetcode.com/problems/roman-to-integer
 */
public class RomanToInteger {
    /*
     * Just to test
     */
    public static void main(String[] args) {
        System.out.println(romanToInt("MCMXCIV"));
    }

    /*
     * Solution implement here
     */
    public static int romanToInt(String s) {
        int result = 0;
        for(int i = 0; i+1 < s.length(); i++) {
            if(DEFINITION[s.charAt(i)] < DEFINITION[s.charAt(i+1)]) {
                result -= DEFINITION[s.charAt(i)];
            } else {
                result += DEFINITION[s.charAt(i)];
            }
        }
        return result + DEFINITION[s.charAt(s.length() - 1)];
    }

    private static final int[] DEFINITION = new int[128];

    static {
        DEFINITION['I'] = 1;
        DEFINITION['V'] = 5;
        DEFINITION['X'] = 10;
        DEFINITION['L'] = 50;
        DEFINITION['C'] = 100;
        DEFINITION['D'] = 500;
        DEFINITION['M'] = 1000;
    }
}
