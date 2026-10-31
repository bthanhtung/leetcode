package RunningSumOf1dArray;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class RunningSumOf1dArrayTest {

    @Test
    void examples() {
        assertArrayEquals(new int[]{1, 3, 6, 10}, RunningSumOf1dArray.runningSum(new int[]{1, 2, 3, 4}));
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, RunningSumOf1dArray.runningSumInPlace(new int[]{1, 1, 1, 1, 1}));
        assertArrayEquals(new int[]{3, 4, 6, 16, 17}, RunningSumOf1dArray.runningSum(new int[]{3, 1, 2, 10, 1}));
        assertArrayEquals(new int[]{}, RunningSumOf1dArray.runningSumInPlace(new int[]{}));
    }
}
