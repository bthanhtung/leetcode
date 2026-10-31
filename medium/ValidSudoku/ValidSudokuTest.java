package ValidSudoku;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidSudokuTest {

    private static char[][] board(String... rows) {
        char[][] b = new char[9][];
        for (int i = 0; i < 9; i++) b[i] = rows[i].toCharArray();
        return b;
    }

    @Test
    void examplesAndRandom() {
        char[][] valid = board("53..7....", "6..195...", ".98....6.", "8...6...3", "4..8.3..1", "7...2...6", ".6....28.", "...419..5", "....8..79");
        char[][] invalid = board("83..7....", "6..195...", ".98....6.", "8...6...3", "4..8.3..1", "7...2...6", ".6....28.", "...419..5", "....8..79");
        assertEquals(true, ValidSudoku.isValidSudoku(valid));
        assertEquals(false, ValidSudoku.isValidSudoku(invalid));
        Random r = new Random(36);
        for (int t = 0; t < 2000; t++) {
            char[][] b = new char[9][9];
            for (char[] row : b) for (int j = 0; j < 9; j++) row[j] = r.nextInt(100) < 12 ? (char) ('1' + r.nextInt(9)) : '.';
            assertEquals(ValidSudoku.isValidSudokuSet(b), ValidSudoku.isValidSudoku(b));
        }
    }
}
