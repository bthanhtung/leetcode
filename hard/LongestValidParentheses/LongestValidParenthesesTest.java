package LongestValidParentheses;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LongestValidParenthesesTest {

    private static boolean valid(String s, int i, int j) {
        int bal = 0;
        for (int k = i; k < j; k++) {
            bal += s.charAt(k) == '(' ? 1 : -1;
            if (bal < 0) return false;
        }
        return bal == 0;
    }

    @Test
    void examplesAndRandom() {
        assertEquals(2, LongestValidParentheses.longestValidParentheses("(()"));
        assertEquals(4, LongestValidParentheses.longestValidParentheses(")()())"));
        assertEquals(0, LongestValidParentheses.longestValidParentheses(""));
        assertEquals(6, LongestValidParentheses.longestValidParentheses("()(())"));
        Random r = new Random(32);
        for (int t = 0; t < 2000; t++) {
            StringBuilder sb = new StringBuilder();
            for (int k = r.nextInt(14); k > 0; k--) sb.append(r.nextBoolean() ? '(' : ')');
            String s = sb.toString();
            int expected = 0;
            for (int i = 0; i < s.length(); i++) for (int j = i + 2; j <= s.length(); j += 2) if (valid(s, i, j)) expected = Math.max(expected, j - i);
            assertEquals(expected, LongestValidParentheses.longestValidParentheses(s), s);
            assertEquals(expected, LongestValidParentheses.longestValidParenthesesDp(s), s);
            assertEquals(expected, LongestValidParentheses.longestValidParenthesesCounters(s), s);
        }
    }
}
