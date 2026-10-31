package LengthOfLastWord;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LengthOfLastWordTest {

    private static void all(int expected, String s) {
        assertEquals(expected, LengthOfLastWord.lengthOfLastWordSplit(s), s);
        assertEquals(expected, LengthOfLastWord.lengthOfLastWordTrim(s), s);
        assertEquals(expected, LengthOfLastWord.lengthOfLastWord(s), s);
    }

    @Test
    void examples() {
        all(5, "Hello World");
        all(4, "   fly me   to   the moon  ");
        all(6, "luffy is still joyboy");
        all(1, "a");
        all(1, "a ");
        all(3, "   abc");
    }
}
