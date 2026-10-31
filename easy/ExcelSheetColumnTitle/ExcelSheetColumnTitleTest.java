package ExcelSheetColumnTitle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExcelSheetColumnTitleTest {

    private static int toNumber(String s) {
        int n = 0;
        for (char c : s.toCharArray()) n = n * 26 + (c - 'A' + 1);
        return n;
    }

    @Test
    void roundTrip() {
        assertEquals("A", ExcelSheetColumnTitle.convertToTitle(1));
        assertEquals("Z", ExcelSheetColumnTitle.convertToTitle(26));
        assertEquals("AA", ExcelSheetColumnTitle.convertToTitle(27));
        assertEquals("ZY", ExcelSheetColumnTitle.convertToTitle(701));
        assertEquals("FXSHRXW", ExcelSheetColumnTitle.convertToTitle(Integer.MAX_VALUE));
        for (int n = 1; n <= 50_000; n++) {
            String title = ExcelSheetColumnTitle.convertToTitle(n);
            assertEquals(n, toNumber(title));
            assertEquals(title, ExcelSheetColumnTitle.convertToTitleRecursive(n));
        }
    }
}
