package ExpressionAddOperators;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExpressionAddOperatorsTest {

    /** Every way to fill the n-1 gaps with nothing / + / - / * (4^(n-1)), keeping valid, matching expressions. */
    private static Set<String> brute(String num, int target) {
        Set<String> out = new HashSet<>();
        int gaps = num.length() - 1;
        for (int code = 0; code < Math.pow(4, gaps); code++) {
            StringBuilder sb = new StringBuilder().append(num.charAt(0));
            int c = code;
            for (int g = 0; g < gaps; g++, c /= 4) {
                int op = c % 4;
                if (op > 0) sb.append("+-*".charAt(op - 1));
                sb.append(num.charAt(g + 1));
            }
            String e = sb.toString();
            boolean leadingZero = false;
            for (String operand : e.split("[+*-]")) if (operand.length() > 1 && operand.charAt(0) == '0') leadingZero = true;
            if (!leadingZero && ExpressionAddOperators.evaluate(e) == target) out.add(e);
        }
        return out;
    }

    @Test
    void examplesAndRandom() {
        assertEquals(Set.of("1*2*3", "1+2+3"), new HashSet<>(ExpressionAddOperators.addOperators("123", 6)));
        assertEquals(Set.of("2*3+2", "2+3*2"), new HashSet<>(ExpressionAddOperators.addOperators("232", 8)));
        assertEquals(Set.of("1*0+5", "10-5"), new HashSet<>(ExpressionAddOperators.addOperators("105", 5)));
        assertEquals(List.of(), ExpressionAddOperators.addOperators("3456237490", 9191));
        Random r = new Random(282);
        for (int t = 0; t < 300; t++) {
            StringBuilder sb = new StringBuilder();
            for (int k = 1 + r.nextInt(6); k > 0; k--) sb.append((char) ('0' + r.nextInt(4)));
            int target = r.nextInt(21) - 5;
            List<String> got = ExpressionAddOperators.addOperators(sb.toString(), target);
            assertEquals(brute(sb.toString(), target), new HashSet<>(got));
            for (String e : got) assertTrue(ExpressionAddOperators.evaluate(e) == target);
        }
    }
}
