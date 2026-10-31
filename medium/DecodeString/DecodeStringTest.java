package DecodeString;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DecodeStringTest {

    /** Generates a random encoded string and its decoding together. */
    private static String[] random(Random r, int depth) {
        StringBuilder enc = new StringBuilder(), dec = new StringBuilder();
        for (int parts = 1 + r.nextInt(3); parts > 0; parts--) {
            if (depth > 0 && r.nextBoolean()) {
                int k = 1 + r.nextInt(12);
                String[] inner = random(r, depth - 1);
                enc.append(k).append('[').append(inner[0]).append(']');
                dec.append(inner[1].repeat(k));
            } else {
                char c = (char) ('a' + r.nextInt(3));
                enc.append(c);
                dec.append(c);
            }
        }
        return new String[]{enc.toString(), dec.toString()};
    }

    @Test
    void examplesAndRandom() {
        assertEquals("aaabcbc", DecodeString.decodeString("3[a]2[bc]"));
        assertEquals("accaccacc", DecodeString.decodeString("3[a2[c]]"));
        assertEquals("abcabccdcdcdef", DecodeString.decodeString("2[abc]3[cd]ef"));
        assertEquals("aaaaaaaaaaaa", DecodeString.decodeString("12[a]"));
        Random r = new Random(394);
        for (int t = 0; t < 1000; t++) {
            String[] c = random(r, 3);
            assertEquals(c[1], DecodeString.decodeString(c[0]));
            assertEquals(c[1], DecodeString.decodeStringRecursive(c[0]));
        }
    }
}
