package FirstUniqueCharacterInAString;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FirstUniqueCharacterInAStringTest {

    private static void all(int expected, String s) {
        assertEquals(expected, FirstUniqueCharacterInAString.firstUniqChar(s), s);
        assertEquals(expected, FirstUniqueCharacterInAString.firstUniqCharIndex(s), s);
        assertEquals(expected, FirstUniqueCharacterInAString.firstUniqCharQueue(s), s);
    }

    @Test
    void examplesAndRandom() {
        all(0, "leetcode");
        all(2, "loveleetcode");
        all(-1, "aabb");
        Random r = new Random(387);
        for (int t = 0; t < 2000; t++) {
            StringBuilder sb = new StringBuilder();
            for (int k = 1 + r.nextInt(10); k > 0; k--) sb.append((char) ('a' + r.nextInt(4)));
            all(FirstUniqueCharacterInAString.firstUniqChar(sb.toString()), sb.toString());
        }
    }
}
