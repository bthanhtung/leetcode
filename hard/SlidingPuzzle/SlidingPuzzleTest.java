package SlidingPuzzle;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SlidingPuzzleTest {

    @Test
    void examplesAndRandom() {
        assertEquals(1, SlidingPuzzle.slidingPuzzle(new int[][]{{1, 2, 3}, {4, 0, 5}}));
        assertEquals(-1, SlidingPuzzle.slidingPuzzle(new int[][]{{1, 2, 3}, {5, 4, 0}}));
        assertEquals(5, SlidingPuzzle.slidingPuzzle(new int[][]{{4, 1, 2}, {5, 0, 3}}));
        assertEquals(14, SlidingPuzzle.slidingPuzzle(new int[][]{{3, 2, 4}, {1, 5, 0}}));
        Random r = new Random(773);
        List<Integer> tiles = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5));
        for (int t = 0; t < 200; t++) {
            Collections.shuffle(tiles, r);
            int[][] b = {{tiles.get(0), tiles.get(1), tiles.get(2)}, {tiles.get(3), tiles.get(4), tiles.get(5)}};
            assertEquals(SlidingPuzzle.slidingPuzzle(b), SlidingPuzzle.slidingPuzzleAStar(b));
        }
    }
}
