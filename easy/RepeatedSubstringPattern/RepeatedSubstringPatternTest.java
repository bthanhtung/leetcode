package RepeatedSubstringPattern;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RepeatedSubstringPatternTest {

    @Test
    void examplesAndRandom() {
        assertEquals(true, RepeatedSubstringPattern.repeatedSubstringPattern("abab"));
        assertEquals(false, RepeatedSubstringPattern.repeatedSubstringPattern("aba"));
        assertEquals(true, RepeatedSubstringPattern.repeatedSubstringPattern("abcabcabcabc"));
        assertEquals(false, RepeatedSubstringPattern.repeatedSubstringPattern("a"));
        Random r = new Random(459);
        for (int t = 0; t < 3000; t++) {
            String s;
            if (r.nextBoolean()) {
                StringBuilder block = new StringBuilder();
                for (int k = 1 + r.nextInt(3); k > 0; k--) block.append((char) ('a' + r.nextInt(2)));
                s = block.toString().repeat(1 + r.nextInt(4));
            } else {
                StringBuilder sb = new StringBuilder();
                for (int k = 1 + r.nextInt(8); k > 0; k--) sb.append((char) ('a' + r.nextInt(2)));
                s = sb.toString();
            }
            boolean expected = RepeatedSubstringPattern.repeatedSubstringPatternBrute(s);
            assertEquals(expected, RepeatedSubstringPattern.repeatedSubstringPattern(s), s);
            assertEquals(expected, RepeatedSubstringPattern.repeatedSubstringPatternKmp(s), s);
        }
    }
}
