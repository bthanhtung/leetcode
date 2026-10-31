package MoveZeroes;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MoveZeroesTest {

    @Test
    void examplesAndRandom() {
        int[] a = {0, 1, 0, 3, 12};
        MoveZeroes.moveZeroes(a);
        assertArrayEquals(new int[]{1, 3, 12, 0, 0}, a);
        Random r = new Random(283);
        for (int t = 0; t < 1000; t++) {
            int[] nums = r.ints(r.nextInt(12), 0, 4).toArray();
            int[] expected = nums.clone(), b = nums.clone(), c = nums.clone();
            MoveZeroes.moveZeroesExtra(expected);
            MoveZeroes.moveZeroesFill(b);
            MoveZeroes.moveZeroes(c);
            assertArrayEquals(expected, b);
            assertArrayEquals(expected, c);
        }
    }
}
