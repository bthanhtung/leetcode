package FibonacciNumber;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FibonacciNumberTest {

    @Test
    void allApproachesUpTo30() {
        int[] first = {0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55};
        for (int n = 0; n <= 30; n++) {
            int expected = FibonacciNumber.fib(n);
            if (n < first.length) assertEquals(first[n], expected);
            assertEquals(expected, FibonacciNumber.fibMemo(n));
            assertEquals(expected, FibonacciNumber.fibMatrix(n));
            if (n <= 25) assertEquals(expected, FibonacciNumber.fibRecursive(n));
        }
        assertEquals(832040, FibonacciNumber.fib(30));
    }
}
