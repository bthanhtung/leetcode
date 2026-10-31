package DetectCapital;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DetectCapitalTest {

    @Test
    void examplesAndRandom() {
        assertEquals(true, DetectCapital.detectCapitalUse("USA"));
        assertEquals(true, DetectCapital.detectCapitalUse("leetcode"));
        assertEquals(true, DetectCapital.detectCapitalUse("Google"));
        assertEquals(false, DetectCapital.detectCapitalUse("FlaG"));
        assertEquals(false, DetectCapital.detectCapitalUse("mL"));
        Random r = new Random(520);
        for (int t = 0; t < 2000; t++) {
            StringBuilder sb = new StringBuilder();
            for (int k = 1 + r.nextInt(5); k > 0; k--) sb.append(r.nextBoolean() ? 'a' : 'B');
            assertEquals(DetectCapital.detectCapitalUseRegex(sb.toString()), DetectCapital.detectCapitalUse(sb.toString()));
        }
    }
}
