package BackspaceStringCompare;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BackspaceStringCompareTest {

    @Test
    void examplesAndRandom() {
        assertEquals(true, BackspaceStringCompare.backspaceCompare("ab#c", "ad#c"));
        assertEquals(true, BackspaceStringCompare.backspaceCompare("ab##", "c#d#"));
        assertEquals(false, BackspaceStringCompare.backspaceCompare("a#c", "b"));
        assertEquals(true, BackspaceStringCompare.backspaceCompare("bxj##tw", "bxo#j##tw"));
        Random r = new Random(844);
        for (int t = 0; t < 5000; t++) {
            String a = rnd(r), b = rnd(r);
            assertEquals(BackspaceStringCompare.backspaceCompareBuild(a, b), BackspaceStringCompare.backspaceCompare(a, b), a + " vs " + b);
        }
    }

    private static String rnd(Random r) {
        StringBuilder sb = new StringBuilder();
        for (int k = r.nextInt(8); k > 0; k--) sb.append("ab##".charAt(r.nextInt(4)));
        return sb.toString();
    }
}
