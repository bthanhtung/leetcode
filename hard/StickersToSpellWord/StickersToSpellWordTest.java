package StickersToSpellWord;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StickersToSpellWordTest {

    @Test
    void examplesAndRandom() {
        assertEquals(3, StickersToSpellWord.minStickers(new String[]{"with", "example", "science"}, "thehat"));
        assertEquals(-1, StickersToSpellWord.minStickers(new String[]{"notice", "possible"}, "basicbasic"));
        Random r = new Random(691);
        for (int t = 0; t < 300; t++) {
            String[] stickers = new String[1 + r.nextInt(4)];
            for (int i = 0; i < stickers.length; i++) stickers[i] = rnd(r, 1 + r.nextInt(4));
            String target = rnd(r, 1 + r.nextInt(8));
            assertEquals(StickersToSpellWord.minStickersBfs(stickers, target), StickersToSpellWord.minStickers(stickers, target));
        }
    }

    private static String rnd(Random r, int n) {
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < n; k++) sb.append((char) ('a' + r.nextInt(5)));
        return sb.toString();
    }
}
