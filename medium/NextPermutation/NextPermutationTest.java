package NextPermutation;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class NextPermutationTest {

    @Test
    void walksAllPermutationsInOrder() {
        int[] a = {1, 1, 5};
        NextPermutation.nextPermutation(a);
        assertArrayEquals(new int[]{1, 5, 1}, a);
        a = new int[]{3, 2, 1};
        NextPermutation.nextPermutation(a);
        assertArrayEquals(new int[]{1, 2, 3}, a);

        // starting from sorted, repeated calls must produce strictly increasing arrays and come back after n!/dups
        int[] cur = {1, 2, 2, 3, 4};
        int[] start = cur.clone();
        int steps = 0;
        do {
            int[] prev = cur.clone();
            NextPermutation.nextPermutation(cur);
            steps++;
            if (!Arrays.equals(cur, start)) assertArrayEquals(new int[]{1}, new int[]{Arrays.compare(cur, prev) > 0 ? 1 : 0});
        } while (!Arrays.equals(cur, start));
        assertArrayEquals(new int[]{60}, new int[]{steps});       // 5! / 2!
    }
}
