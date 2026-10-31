package EvaluateReversePolishNotation;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EvaluateReversePolishNotationTest {

    /** Random expression tree written in RPN, with its value computed directly. */
    private static int build(Random r, int depth, List<String> out) {
        if (depth == 0 || r.nextInt(3) == 0) {
            int v = r.nextInt(41) - 20;
            out.add(String.valueOf(v));
            return v;
        }
        int a = build(r, depth - 1, out), b = build(r, depth - 1, out);
        String[] ops = b == 0 ? new String[]{"+", "-", "*"} : new String[]{"+", "-", "*", "/"};
        String op = ops[r.nextInt(ops.length)];
        out.add(op);
        return switch (op) { case "+" -> a + b; case "-" -> a - b; case "*" -> a * b; default -> a / b; };
    }

    @Test
    void examplesAndRandom() {
        assertEquals(9, EvaluateReversePolishNotation.evalRPN(new String[]{"2", "1", "+", "3", "*"}));
        assertEquals(6, EvaluateReversePolishNotation.evalRPN(new String[]{"4", "13", "5", "/", "+"}));
        assertEquals(22, EvaluateReversePolishNotation.evalRPN(new String[]{"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"}));
        Random r = new Random(150);
        for (int t = 0; t < 2000; t++) {
            List<String> tokens = new ArrayList<>();
            int expected = build(r, 4, tokens);
            String[] arr = tokens.toArray(new String[0]);
            assertEquals(expected, EvaluateReversePolishNotation.evalRPN(arr));
            assertEquals(expected, EvaluateReversePolishNotation.evalRPNRecursive(arr));
        }
    }
}
