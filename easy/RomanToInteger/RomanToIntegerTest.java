package RomanToInteger;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RomanToIntegerTest {

    private static String toRoman(int n) {
        int[] v = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] s = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < v.length; i++) {
            while (n >= v[i]) {
                sb.append(s[i]);
                n -= v[i];
            }
        }
        return sb.toString();
    }

    @Test
    void examples() {
        assertEquals(3, RomanToInteger.romanToIntLookAhead("III"));
        assertEquals(58, RomanToInteger.romanToIntBackward("LVIII"));
        assertEquals(1994, RomanToInteger.romanToIntPairs("MCMXCIV"));
    }

    @Test
    void everyNumberFrom1To3999() {
        for (int n = 1; n <= 3999; n++) {
            String r = toRoman(n);
            assertEquals(n, RomanToInteger.romanToIntLookAhead(r), r);
            assertEquals(n, RomanToInteger.romanToIntBackward(r), r);
            assertEquals(n, RomanToInteger.romanToIntPairs(r), r);
        }
    }
}
