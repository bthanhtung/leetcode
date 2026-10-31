package TwentyFourGame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TwentyFourGameTest {

    @Test
    void examplesAndAllHands() {
        assertEquals(true, TwentyFourGame.judgePoint24(new int[]{4, 1, 8, 7}));
        assertEquals(false, TwentyFourGame.judgePoint24(new int[]{1, 2, 1, 2}));
        assertEquals(true, TwentyFourGame.judgePoint24(new int[]{3, 3, 8, 8}));       // 8 / (3 - 8 / 3) needs fractions
        assertEquals(true, TwentyFourGame.judgePoint24(new int[]{1, 5, 5, 5}));       // 5 * (5 - 1 / 5)
        int solvable = 0;
        for (int a = 1; a <= 9; a++)
            for (int b = a; b <= 9; b++)
                for (int c = b; c <= 9; c++)
                    for (int d = c; d <= 9; d++) {
                        int[] hand = {a, b, c, d};
                        boolean exact = TwentyFourGame.judgePoint24(hand);
                        assertEquals(exact, TwentyFourGame.judgePoint24Double(hand), java.util.Arrays.toString(hand));
                        if (exact) solvable++;
                    }
        assertEquals(404, solvable);                                        // known count of solvable hands with cards 1-9
    }
}
