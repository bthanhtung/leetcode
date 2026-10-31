package SudokuSolver;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SudokuSolverTest {

    private static void assertSolved(char[][] puzzle, char[][] solved) {
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                if (puzzle[r][c] != '.') assertEquals(puzzle[r][c], solved[r][c], "givens must stay");
        for (int i = 0; i < 9; i++) {
            int row = 0, col = 0, box = 0;
            for (int j = 0; j < 9; j++) {
                row |= 1 << (solved[i][j] - '1');
                col |= 1 << (solved[j][i] - '1');
                box |= 1 << (solved[i / 3 * 3 + j / 3][i % 3 * 3 + j % 3] - '1');
            }
            assertTrue(row == 0x1FF && col == 0x1FF && box == 0x1FF);
        }
    }

    private static char[][] copy(char[][] b) {
        char[][] c = new char[9][];
        for (int i = 0; i < 9; i++) c[i] = b[i].clone();
        return c;
    }

    @Test
    void solvesPuzzles() {
        String[][] puzzles = {
                {"53..7....", "6..195...", ".98....6.", "8...6...3", "4..8.3..1", "7...2...6", ".6....28.", "...419..5", "....8..79"},
                {"..9748...", "7........", ".2.1.9...", "..7...24.", ".64.1.59.", ".98...3..", "...8.3.2.", "........6", "...2759.."},
                {"8........", "..36.....", ".7..9.2..", ".5...7...", "....457..", "...1...3.", "..1....68", "..85...1.", ".9....4.."}};
        for (String[] p : puzzles) {
            char[][] puzzle = SudokuSolver.parse(p), a = copy(puzzle), b = copy(puzzle);
            SudokuSolver.solveSudoku(a);
            SudokuSolver.solveSudokuSimple(b);
            assertSolved(puzzle, a);
            assertArrayEquals(a, b, "the solution is unique");
        }
    }
}
