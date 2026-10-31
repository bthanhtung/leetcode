package IntegerToEnglishWords;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class IntegerToEnglishWordsTest {

    @Test
    void examples() {
        assertEquals("Zero", IntegerToEnglishWords.numberToWords(0));
        assertEquals("One Hundred Twenty Three", IntegerToEnglishWords.numberToWords(123));
        assertEquals("Twelve Thousand Three Hundred Forty Five", IntegerToEnglishWords.numberToWords(12345));
        assertEquals("One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven", IntegerToEnglishWords.numberToWords(1234567));
        assertEquals("One Million", IntegerToEnglishWords.numberToWords(1_000_000));
        assertEquals("One Billion One", IntegerToEnglishWords.numberToWords(1_000_000_001));
        assertEquals("Twenty", IntegerToEnglishWords.numberToWords(20));
        assertEquals("One Hundred Thousand Ten", IntegerToEnglishWords.numberToWords(100_010));
        assertEquals("Two Billion One Hundred Forty Seven Million Four Hundred Eighty Three Thousand Six Hundred Forty Seven",
                IntegerToEnglishWords.numberToWords(Integer.MAX_VALUE));
        for (int n = 0; n < 2_000_000; n += 7) {
            String s = IntegerToEnglishWords.numberToWords(n);
            assertFalse(s.contains("  ") || s.startsWith(" ") || s.endsWith(" "), s);
        }
    }
}
