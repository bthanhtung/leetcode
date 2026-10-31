package ValidParentheses;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidParenthesesTest {

    private static void all(boolean expected, String s) {
        assertEquals(expected, ValidParentheses.isValidStack(s), s);
        assertEquals(expected, ValidParentheses.isValidExpected(s), s);
        assertEquals(expected, ValidParentheses.isValidReplace(s), s);
    }

    @Test
    void examples() {
        all(true, "()");
        all(true, "()[]{}");
        all(false, "(]");
        all(false, "([)]");
        all(true, "{[]}");
        all(false, "(");
        all(false, "]");
        all(false, "((");
        all(true, "");
    }

    @Test
    void randomAgree() {
        Random r = new Random(4);
        String alphabet = "()[]{}";
        for (int t = 0; t < 3000; t++) {
            StringBuilder sb = new StringBuilder();
            for (int k = r.nextInt(9); k > 0; k--) {
                sb.append(alphabet.charAt(r.nextInt(6)));
            }
            all(ValidParentheses.isValidReplace(sb.toString()), sb.toString());
        }
    }
}
