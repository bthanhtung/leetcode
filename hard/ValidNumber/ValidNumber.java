package ValidNumber;

/*
 * 65. Valid Number — https://leetcode.com/problems/valid-number/
 * Is s a valid number? An integer or decimal (optional sign; digits with an optional '.'; at least one digit),
 * optionally followed by 'e'/'E' and an integer (optional sign, at least one digit). No spaces.
 * Valid: "2", "0089", "-0.1", "+3.14", "4.", "-.9", "2e10", "-90E3", "3e+7", "+6e-1", "53.5e93", "-123.456e789"
 * Invalid: "abc", "1a", "1e", "e3", "99e2.5", "--6", "-+3", "95a54e53", "."
 * Explanation: README.md
 */
public class ValidNumber {
    public static void main(String[] args) {
        System.out.println(isNumber("-123.456e789"));     // true
        System.out.println(isNumber("99e2.5"));           // false
    }

    /* Approach 1 - one pass remembering whether we have seen a digit, a dot and an exponent. O(n). */
    public static boolean isNumber(String s) {
        boolean digit = false, dot = false, exp = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                digit = true;
            } else if (c == '+' || c == '-') {
                if (i > 0 && s.charAt(i - 1) != 'e' && s.charAt(i - 1) != 'E') return false;   // sign only at a start
            } else if (c == 'e' || c == 'E') {
                if (exp || !digit) return false;
                exp = true;
                digit = false;                    // the exponent needs its own digits
            } else if (c == '.') {
                if (dot || exp) return false;
                dot = true;
            } else {
                return false;
            }
        }
        return digit;
    }

    /*
     * Approach 2 - DFA. States: 0 start, 1 sign, 2 integer digits, 3 dot after digits, 4 dot without digits,
     * 5 fraction digits, 6 'e', 7 exponent sign, 8 exponent digits. Accepting: 2, 3, 5, 8.
     */
    private static final int[][] NEXT = {
            // digit sign  e   dot
            {2, 1, -1, 4},      // 0
            {2, -1, -1, 4},     // 1
            {2, -1, 6, 3},      // 2
            {5, -1, 6, -1},     // 3
            {5, -1, -1, -1},    // 4
            {5, -1, 6, -1},     // 5
            {8, 7, -1, -1},     // 6
            {8, -1, -1, -1},    // 7
            {8, -1, -1, -1},    // 8
    };

    public static boolean isNumberDfa(String s) {
        int state = 0;
        for (char c : s.toCharArray()) {
            int kind = Character.isDigit(c) ? 0 : c == '+' || c == '-' ? 1 : c == 'e' || c == 'E' ? 2 : c == '.' ? 3 : -1;
            if (kind < 0 || (state = NEXT[state][kind]) < 0) return false;
        }
        return state == 2 || state == 3 || state == 5 || state == 8;
    }
}
