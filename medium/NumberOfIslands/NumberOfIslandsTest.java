package NumberOfIslands;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumberOfIslandsTest {

    @Test
    void examplesAndRandom() {
        char[][] a = {"11110".toCharArray(), "11010".toCharArray(), "11000".toCharArray(), "00000".toCharArray()};
        char[][] b = {"11000".toCharArray(), "11000".toCharArray(), "00100".toCharArray(), "00011".toCharArray()};
        assertEquals(1, NumberOfIslands.numIslands(a));
        assertEquals(3, NumberOfIslands.numIslands(b));
        Random r = new Random(200);
        for (int t = 0; t < 500; t++) {
            int m = 1 + r.nextInt(8), n = 1 + r.nextInt(8);
            char[][] g = new char[m][n];
            for (char[] row : g) for (int j = 0; j < n; j++) row[j] = r.nextInt(100) < 45 ? '1' : '0';
            int expected = NumberOfIslands.numIslands(g);
            assertEquals(expected, NumberOfIslands.numIslandsBfs(g));
            assertEquals(expected, NumberOfIslands.numIslandsUnionFind(g));
        }
    }
}
