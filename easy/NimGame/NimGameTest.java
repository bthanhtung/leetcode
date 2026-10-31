package NimGame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NimGameTest {

    @Test
    void formulaMatchesGameTree() {
        assertEquals(false, NimGame.canWinNim(4));
        assertEquals(true, NimGame.canWinNim(1));
        for (int n = 1; n <= 10_000; n++) {
            assertEquals(NimGame.canWinNimDp(n), NimGame.canWinNim(n), "n = " + n);
        }
    }
}
