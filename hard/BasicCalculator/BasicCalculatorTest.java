package BasicCalculator;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasicCalculatorTest {

    /** Random expression and its value, built together. */
    private static long[] gen(Random r, int depth, StringBuilder sb) {
        long value = term(r, depth, sb, true);
        for (int k = r.nextInt(3); k > 0; k--) {
            boolean plus = r.nextBoolean();
            sb.append(r.nextBoolean() ? " " : "").append(plus ? '+' : '-').append(r.nextBoolean() ? " " : "");
            long rhs = term(r, depth, sb, false);
            value = plus ? value + rhs : value - rhs;
        }
        return new long[]{value};
    }

    /** Unary minus only at the start of an expression: the statement never has two operators in a row. */
    private static long term(Random r, int depth, StringBuilder sb, boolean first) {
        int pick = depth == 0 ? 0 : r.nextInt(first ? 3 : 2);
        if (pick == 0) {
            int v = r.nextInt(100);
            sb.append(v);
            return v;
        }
        if (pick == 1) {
            sb.append('(');
            long v = gen(r, depth - 1, sb)[0];
            sb.append(')');
            return v;
        }
        sb.append("-(");
        long v = gen(r, depth - 1, sb)[0];
        sb.append(')');
        return -v;
    }

    @Test
    void examplesAndRandom() {
        assertEquals(2, BasicCalculator.calculate("1 + 1"));
        assertEquals(3, BasicCalculator.calculate(" 2-1 + 2 "));
        assertEquals(23, BasicCalculator.calculate("(1+(4+5+2)-3)+(6+8)"));
        assertEquals(-12, BasicCalculator.calculate("- (3 + (4 + 5))"));
        assertEquals(-1, BasicCalculator.calculate("-1"));
        Random r = new Random(224);
        for (int t = 0; t < 2000; t++) {
            StringBuilder sb = new StringBuilder();
            long expected = gen(r, 3, sb)[0];
            assertEquals(expected, BasicCalculator.calculate(sb.toString()), sb.toString());
            assertEquals(expected, BasicCalculator.calculateRecursive(sb.toString()), sb.toString());
        }
    }
}
