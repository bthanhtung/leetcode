package RansomNote;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RansomNoteTest {

    private static void all(boolean expected, String note, String magazine) {
        assertEquals(expected, RansomNote.canConstructRemove(note, magazine));
        assertEquals(expected, RansomNote.canConstructMap(note, magazine));
        assertEquals(expected, RansomNote.canConstruct(note, magazine));
    }

    private static String random(Random r, int maxLen) {
        StringBuilder sb = new StringBuilder();
        for (int k = r.nextInt(maxLen); k > 0; k--) sb.append((char) ('a' + r.nextInt(4)));
        return sb.toString();
    }

    @Test
    void examples() {
        all(false, "a", "b");
        all(false, "aa", "ab");
        all(true, "aa", "aab");
        all(true, "", "abc");
    }

    @Test
    void randomAgree() {
        Random r = new Random(383);
        for (int t = 0; t < 1000; t++) {
            String note = random(r, 6), magazine = random(r, 10);
            all(RansomNote.canConstructRemove(note, magazine), note, magazine);
        }
    }
}
