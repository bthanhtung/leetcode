package RotateArray;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class RotateArrayTest {

    @Test
    void examplesAndRandom() {
        int[] a = {-1, -100, 3, 99};
        RotateArray.rotate(a, 2);
        assertArrayEquals(new int[]{3, 99, -1, -100}, a);
        Random r = new Random(189);
        for (int t = 0; t < 3000; t++) {
            int[] base = r.ints(1 + r.nextInt(12), -50, 50).toArray();
            int k = r.nextInt(30);
            int[] x = base.clone(), y = base.clone(), z = base.clone();
            RotateArray.rotate(x, k);
            RotateArray.rotateCycles(y, k);
            RotateArray.rotateCopy(z, k);
            assertArrayEquals(z, x);
            assertArrayEquals(z, y);
        }
    }
}
