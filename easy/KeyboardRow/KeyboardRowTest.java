package KeyboardRow;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KeyboardRowTest {

    @Test
    void examples() {
        String[] words = {"Hello", "Alaska", "Dad", "Peace", "omk", "adsdf", "sfd", "QWE", "zXc"};
        assertEquals(List.of("Alaska", "Dad", "adsdf", "sfd", "QWE", "zXc"), KeyboardRow.findWords(words));
        assertEquals(KeyboardRow.findWords(words), KeyboardRow.findWordsRegex(words));
    }
}
