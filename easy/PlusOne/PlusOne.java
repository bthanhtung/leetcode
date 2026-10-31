package PlusOne;

import java.math.BigInteger;

/*
 * 66. Plus One — https://leetcode.com/problems/plus-one/
 * A large integer is given as an array of digits, most significant first, without leading zeros. Add one and return the digits.
 * Input: [1,2,3] -> [1,2,4] · [4,3,2,1] -> [4,3,2,2] · [9] -> [1,0]
 * Explanation: README.md
 */
public class PlusOne {
    public static void main(String[] args) {
        System.out.println(java.util.Arrays.toString(plusOne(new int[]{1, 2, 9})));            // [1, 3, 0]
        System.out.println(java.util.Arrays.toString(plusOneCarry(new int[]{9, 9})));         // [1, 0, 0]
        System.out.println(java.util.Arrays.toString(plusOneBigInteger(new int[]{4, 3, 2})));  // [4, 3, 3]
    }

    /*
     * Approach 1 - from the last digit: a digit below 9 just grows and we are done; a 9 becomes 0 and the carry moves left.
     * If every digit was 9 the answer is 1 followed by zeros. Time O(n), space O(1) except the all-9 case.
     */
    public static int[] plusOne(int[] digits) {
        for (int i = digits.length - 1; i >= 0; i--) {
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }
            digits[i] = 0;
        }
        int[] result = new int[digits.length + 1];
        result[0] = 1;                          // the rest is already 0
        return result;
    }

    /* Approach 2 - general carry addition (works for "plus k" too). Time O(n), space O(n). */
    public static int[] plusOneCarry(int[] digits) {
        int[] out = new int[digits.length + 1];
        int carry = 1;
        for (int i = digits.length - 1; i >= 0; i--) {
            int sum = digits[i] + carry;
            out[i + 1] = sum % 10;
            carry = sum / 10;
        }
        out[0] = carry;
        return carry == 1 ? out : java.util.Arrays.copyOfRange(out, 1, out.length);
    }

    /* Approach 3 - let BigInteger do it. Fine in real code, but interviewers want approach 1. Time O(n). */
    public static int[] plusOneBigInteger(int[] digits) {
        StringBuilder sb = new StringBuilder();
        for (int d : digits) {
            sb.append(d);
        }
        return new BigInteger(sb.toString()).add(BigInteger.ONE).toString().chars().map(c -> c - '0').toArray();
    }
}
