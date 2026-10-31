package GuessNumberHigherOrLower;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GuessNumberHigherOrLowerTest {

    @Test
    void random() {
        assertEquals(6, GuessNumberHigherOrLower.guessNumber(10, GuessNumberHigherOrLower.api(6)));
        assertEquals(Integer.MAX_VALUE, GuessNumberHigherOrLower.guessNumber(Integer.MAX_VALUE, GuessNumberHigherOrLower.api(Integer.MAX_VALUE)));
        Random r = new Random(374);
        for (int t = 0; t < 2000; t++) {
            int n = 1 + r.nextInt(t < 1000 ? 200 : Integer.MAX_VALUE), pick = 1 + r.nextInt(n);
            assertEquals(pick, GuessNumberHigherOrLower.guessNumber(n, GuessNumberHigherOrLower.api(pick)));
            if (n <= 200) assertEquals(pick, GuessNumberHigherOrLower.guessNumberLinear(n, GuessNumberHigherOrLower.api(pick)));
        }
    }
}
