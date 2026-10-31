package PowerOfThree;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PowerOfThreeTest {

    @Test
    void examplesAndExhaustiveChecks() {
        Set<Integer> powers = new HashSet<>();
        for (long p = 1; p <= Integer.MAX_VALUE; p *= 3) powers.add((int) p);
        int[] edge = {0, -1, -3, 1, 2, 3, 27, 45, Integer.MAX_VALUE, Integer.MIN_VALUE, 1162261467, 1162261466};
        for (int n : edge) {
            boolean expected = powers.contains(n);
            assertEquals(expected, PowerOfThree.isPowerOfThree(n), "n=" + n);
            assertEquals(expected, PowerOfThree.isPowerOfThreeLoop(n), "n=" + n);
            assertEquals(expected, PowerOfThree.isPowerOfThreeLog(n), "n=" + n);
        }
        for (int p : powers) {
            assertEquals(true, PowerOfThree.isPowerOfThreeLog(p));
            if (p > 1) assertEquals(false, PowerOfThree.isPowerOfThree(p + 1));
        }
        Random r = new Random(326);
        for (int t = 0; t < 100000; t++) {
            int n = r.nextInt();
            assertEquals(powers.contains(n), PowerOfThree.isPowerOfThree(n));
            assertEquals(powers.contains(n), PowerOfThree.isPowerOfThreeLoop(n));
        }
    }
}
