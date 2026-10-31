package GenerateParentheses;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GenerateParenthesesTest {

    @Test
    void catalanCountsAndAgreement() {
        int[] catalan = {1, 1, 2, 5, 14, 42, 132, 429, 1430};
        assertEquals(List.of("((()))", "(()())", "(())()", "()(())", "()()()"), GenerateParentheses.generateParenthesis(3));
        for (int n = 1; n <= 8; n++) {
            List<String> a = GenerateParentheses.generateParenthesis(n);
            assertEquals(catalan[n], a.size());
            assertEquals(new HashSet<>(a), new HashSet<>(GenerateParentheses.generateParenthesisDp(n)));
            if (n <= 6) assertEquals(new HashSet<>(a), new HashSet<>(GenerateParentheses.generateParenthesisBruteForce(n)));
        }
    }
}
