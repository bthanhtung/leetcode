package ConvertANumberToHexadecimal;

/*
 * 405. Convert a Number to Hexadecimal — https://leetcode.com/problems/convert-a-number-to-hexadecimal/
 * Convert a 32-bit integer to hexadecimal (lowercase, no leading zeros). Negative numbers use two's complement.
 * Input: 26 -> "1a" · -1 -> "ffffffff" · 0 -> "0"
 * Explanation: README.md
 */
public class ConvertANumberToHexadecimal {
    public static void main(String[] args) {
        System.out.println(toHex(26));     // 1a
        System.out.println(toHex(-1));     // ffffffff
    }

    /* Approach 1 - take the low 4 bits, map to a hex digit, shift right without sign (>>>) until nothing is left. O(8). */
    public static String toHex(int num) {
        if (num == 0) return "0";
        char[] digits = "0123456789abcdef".toCharArray();
        StringBuilder sb = new StringBuilder();
        while (num != 0) {
            sb.append(digits[num & 0xf]);
            num >>>= 4;                      // >>> so negative numbers terminate after 8 nibbles
        }
        return sb.reverse().toString();
    }

    /* Approach 2 - the library does exactly this. */
    public static String toHexLibrary(int num) {
        return Integer.toHexString(num);
    }
}
