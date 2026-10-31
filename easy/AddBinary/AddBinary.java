package AddBinary;

import java.math.BigInteger;

/*
 * 67. Add Binary — https://leetcode.com/problems/add-binary/
 * Given two binary strings a and b, return their sum as a binary string.
 * Input: ("11","1") -> "100" · ("1010","1011") -> "10101"
 * Explanation: README.md
 */
public class AddBinary {
    public static void main(String[] args) {
        System.out.println(addBinary("11", "1"));                 // 100
        System.out.println(addBinaryBigInteger("1010", "1011")); // 10101
    }

    /* Approach 1 - add from the right with a carry, like school addition in base 2. Time O(max(m, n)). */
    public static String addBinary(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int i = a.length() - 1, j = b.length() - 1, carry = 0;
        while (i >= 0 || j >= 0 || carry != 0) {
            int sum = carry;
            if (i >= 0) sum += a.charAt(i--) - '0';
            if (j >= 0) sum += b.charAt(j--) - '0';
            sb.append(sum % 2);
            carry = sum / 2;
        }
        return sb.reverse().toString();
    }

    /* Approach 2 - parse as base-2 BigIntegers (strings can be 10^4 bits long, too big for long). */
    public static String addBinaryBigInteger(String a, String b) {
        return new BigInteger(a, 2).add(new BigInteger(b, 2)).toString(2);
    }
}
