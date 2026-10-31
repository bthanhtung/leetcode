package ConsecutiveNumbersSum;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConsecutiveNumbersSumTest {

    private static int brute(int n) {
        int ways = 0;
        for (int start = 1; start <= n; start++) {
            int sum = 0;
            for (int x = start; sum < n; x++) sum += x;
            if (sum == n) ways++;
        }
        return ways;
    }

    @Test
    void examplesAndBruteForce() {
        assertEquals(2, ConsecutiveNumbersSum.consecutiveNumbersSum(5));
        assertEquals(3, ConsecutiveNumbersSum.consecutiveNumbersSum(9));
        assertEquals(4, ConsecutiveNumbersSum.consecutiveNumbersSum(15));
        for (int n = 1; n <= 3000; n++) {
            int expected = brute(n);
            assertEquals(expected, ConsecutiveNumbersSum.consecutiveNumbersSum(n), "n=" + n);
            assertEquals(expected, ConsecutiveNumbersSum.consecutiveNumbersSumDivisors(n), "n=" + n);
        }
        assertEquals(ConsecutiveNumbersSum.consecutiveNumbersSum(1_000_000_000), ConsecutiveNumbersSum.consecutiveNumbersSumDivisors(1_000_000_000));
    }
}
