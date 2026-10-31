package IntegerToRoman;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntegerToRomanTest {

    private static int toInt(String s) {
        Map<Character, Integer> v = Map.of('I', 1, 'V', 5, 'X', 10, 'L', 50, 'C', 100, 'D', 500, 'M', 1000);
        int total = 0;
        for (int i = 0; i < s.length(); i++) {
            int x = v.get(s.charAt(i));
            total += i + 1 < s.length() && x < v.get(s.charAt(i + 1)) ? -x : x;
        }
        return total;
    }

    @Test
    void examples() {
        assertEquals("MMMDCCXLIX", IntegerToRoman.intToRoman(3749));
        assertEquals("LVIII", IntegerToRoman.intToRoman(58));
        assertEquals("MCMXCIV", IntegerToRoman.intToRomanDigits(1994));
    }

    @Test
    void everyNumberRoundTrips() {
        for (int n = 1; n <= 3999; n++) {
            String roman = IntegerToRoman.intToRoman(n);
            assertEquals(n, toInt(roman));
            assertEquals(roman, IntegerToRoman.intToRomanDigits(n));
        }
    }
}
