package LetterCombinationsOfAPhoneNumber;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LetterCombinationsOfAPhoneNumberTest {

    @Test
    void examples() {
        assertEquals(List.of("ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"), LetterCombinationsOfAPhoneNumber.letterCombinations("23"));
        assertEquals(List.of(), LetterCombinationsOfAPhoneNumber.letterCombinations(""));
        assertEquals(List.of("a", "b", "c"), LetterCombinationsOfAPhoneNumber.letterCombinationsQueue("2"));
    }

    @Test
    void sizesAndAgreement() {
        for (String digits : new String[]{"79", "234", "7979", "2345"}) {
            List<String> a = LetterCombinationsOfAPhoneNumber.letterCombinations(digits);
            List<String> b = LetterCombinationsOfAPhoneNumber.letterCombinationsQueue(digits);
            int expected = digits.chars().map(d -> d == '7' || d == '9' ? 4 : 3).reduce(1, (x, y) -> x * y);
            assertEquals(expected, a.size());
            assertEquals(expected, new HashSet<>(a).size());
            assertEquals(new HashSet<>(a), new HashSet<>(b));
        }
    }
}
