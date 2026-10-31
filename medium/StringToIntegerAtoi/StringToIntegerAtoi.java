package StringToIntegerAtoi;

/*
 * 8. String to Integer (atoi) — https://leetcode.com/problems/string-to-integer-atoi/
 * 1) skip leading spaces, 2) read an optional '+' or '-', 3) read digits until a non-digit, 4) clamp to the 32-bit range.
 * No digits read -> 0.
 * Input: "42" -> 42 · "   -042" -> -42 · "1337c0d3" -> 1337 · "0-1" -> 0 · "words and 987" -> 0 · "-91283472332" -> -2147483648
 * Explanation: README.md
 */
public class StringToIntegerAtoi {
    public static void main(String[] args) {
        System.out.println(myAtoi("   -042"));          // -42
        System.out.println(myAtoiStateMachine("1337c0d3"));   // 1337
        System.out.println(myAtoi("-91283472332"));     // -2147483648
    }

    /* Approach 1 - straight scan with an overflow guard before every multiply. Time O(n), space O(1). */
    public static int myAtoi(String s) {
        int i = 0, n = s.length();
        while (i < n && s.charAt(i) == ' ') {
            i++;
        }
        int sign = 1;
        if (i < n && (s.charAt(i) == '+' || s.charAt(i) == '-')) {
            sign = s.charAt(i++) == '-' ? -1 : 1;
        }
        int result = 0;
        while (i < n && Character.isDigit(s.charAt(i))) {
            int digit = s.charAt(i++) - '0';
            if (result > (Integer.MAX_VALUE - digit) / 10) {          // result * 10 + digit would exceed MAX
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            result = result * 10 + digit;
        }
        return sign * result;
    }

    /*
     * Approach 2 - deterministic finite automaton: states START -> SIGNED -> NUMBER -> END, one transition per character.
     * Longer, but every rule of the statement is a row in the table, so it is easy to extend. Time O(n).
     */
    public static int myAtoiStateMachine(String s) {
        final int start = 0, signed = 1, number = 2, end = 3;
        // columns: space, sign, digit, other
        int[][] next = {
                {start, signed, number, end},    // start
                {end, end, number, end},         // signed
                {end, end, number, end},         // number
                {end, end, end, end},            // end
        };
        int state = start, sign = 1;
        long value = 0;
        for (char c : s.toCharArray()) {
            int column = c == ' ' ? 0 : (c == '+' || c == '-') ? 1 : Character.isDigit(c) ? 2 : 3;
            state = next[state][column];
            if (state == signed) {
                sign = c == '-' ? -1 : 1;
            } else if (state == number) {
                value = Math.min(value * 10 + (c - '0'), (long) Integer.MAX_VALUE + 1);
            } else if (state == end) {
                break;
            }
        }
        return (int) Math.max(Integer.MIN_VALUE, Math.min(Integer.MAX_VALUE, sign * value));
    }
}
