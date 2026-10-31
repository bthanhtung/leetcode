package JewelsAndStones;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JewelsAndStonesTest {

    private static void all(int expected, String jewels, String stones) {
        assertEquals(expected, JewelsAndStones.numJewelsInStonesScan(jewels, stones));
        assertEquals(expected, JewelsAndStones.numJewelsInStonesSet(jewels, stones));
        assertEquals(expected, JewelsAndStones.numJewelsInStones(jewels, stones));
    }

    @Test
    void examples() {
        all(3, "aA", "aAAbbbb");
        all(0, "z", "ZZ");
        all(4, "abc", "aabbz");
    }
}
