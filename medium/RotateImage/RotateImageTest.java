package RotateImage;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class RotateImageTest {

    @Test
    void examplesAndRandom() {
        int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RotateImage.rotate(m);
        assertArrayEquals(new int[][]{{7, 4, 1}, {8, 5, 2}, {9, 6, 3}}, m);
        Random r = new Random(48);
        for (int n = 1; n <= 12; n++) {
            int[][] a = new int[n][n];
            for (int[] row : a) for (int j = 0; j < n; j++) row[j] = r.nextInt(100);
            int[][] b = deepCopy(a), c = deepCopy(a), d = deepCopy(a);
            RotateImage.rotateCopy(b);
            RotateImage.rotate(c);
            RotateImage.rotateLayers(d);
            assertArrayEquals(b, c);
            assertArrayEquals(b, d);
            for (int k = 0; k < 3; k++) RotateImage.rotate(c);           // four rotations = identity
            assertArrayEquals(a, c);
        }
    }

    private static int[][] deepCopy(int[][] a) {
        int[][] c = new int[a.length][];
        for (int i = 0; i < a.length; i++) c[i] = a[i].clone();
        return c;
    }
}
