package RemoveInvalidParentheses;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RemoveInvalidParenthesesTest {

    @Test
    void examplesAndRandom() {
        assertEquals(Set.of("(())()", "()()()"), new HashSet<>(RemoveInvalidParentheses.removeInvalidParentheses("()())()")));
        assertEquals(Set.of("(a())()", "(a)()()"), new HashSet<>(RemoveInvalidParentheses.removeInvalidParentheses("(a)())()")));
        assertEquals(List.of(""), RemoveInvalidParentheses.removeInvalidParentheses(")("));
        Random r = new Random(301);
        for (int t = 0; t < 500; t++) {
            StringBuilder sb = new StringBuilder();
            for (int k = r.nextInt(12); k > 0; k--) sb.append("()(x)".charAt(r.nextInt(5)));
            List<String> a = RemoveInvalidParentheses.removeInvalidParentheses(sb.toString()), b = RemoveInvalidParentheses.removeInvalidParenthesesBfs(sb.toString());
            assertEquals(new HashSet<>(b), new HashSet<>(a), sb.toString());
            assertEquals(a.size(), new HashSet<>(a).size());
        }
    }
}
