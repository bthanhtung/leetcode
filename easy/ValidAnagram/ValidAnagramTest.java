package ValidAnagram;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidAnagramTest {

    @Test
    void examplesAndRandom() {
        assertEquals(true, ValidAnagram.isAnagram("anagram", "nagaram"));
        assertEquals(false, ValidAnagram.isAnagram("rat", "car"));
        assertEquals(true, ValidAnagram.isAnagramUnicode("tiếng việt", "việt tiếng"));
        assertEquals(false, ValidAnagram.isAnagramUnicode("😀a", "a😃"));
        Random r = new Random(242);
        for (int t = 0; t < 2000; t++) {
            StringBuilder a = new StringBuilder(), b = new StringBuilder();
            for (int k = r.nextInt(6); k > 0; k--) a.append((char) ('a' + r.nextInt(3)));
            for (int k = r.nextInt(6); k > 0; k--) b.append((char) ('a' + r.nextInt(3)));
            boolean expected = ValidAnagram.isAnagramSort(a.toString(), b.toString());
            assertEquals(expected, ValidAnagram.isAnagram(a.toString(), b.toString()));
            assertEquals(expected, ValidAnagram.isAnagramUnicode(a.toString(), b.toString()));
        }
    }
}
