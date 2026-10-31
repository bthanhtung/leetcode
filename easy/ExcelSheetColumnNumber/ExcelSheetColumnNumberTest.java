package ExcelSheetColumnNumber;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExcelSheetColumnNumberTest {

    private static String title(int n) {
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            n--;
            sb.append((char) ('A' + n % 26));
            n /= 26;
        }
        return sb.reverse().toString();
    }

    @Test
    void roundTrip() {
        assertEquals(1, ExcelSheetColumnNumber.titleToNumber("A"));
        assertEquals(28, ExcelSheetColumnNumber.titleToNumber("AB"));
        assertEquals(701, ExcelSheetColumnNumber.titleToNumber("ZY"));
        assertEquals(Integer.MAX_VALUE, ExcelSheetColumnNumber.titleToNumber("FXSHRXW"));
        for (int n = 1; n <= 100_000; n++) {
            assertEquals(n, ExcelSheetColumnNumber.titleToNumber(title(n)));
            assertEquals(n, ExcelSheetColumnNumber.titleToNumberPowers(title(n)));
        }
    }
}
