package FibonacciNumber;

/*
 * 509. Fibonacci Number — https://leetcode.com/problems/fibonacci-number/
 * F(0) = 0, F(1) = 1, F(n) = F(n - 1) + F(n - 2). Given 0 <= n <= 30, compute F(n).
 * Input: 2 -> 1 · 3 -> 2 · 4 -> 3
 * Explanation: README.md
 */
public class FibonacciNumber {
    public static void main(String[] args) {
        System.out.println(fib(10));            // 55
        System.out.println(fibMatrix(30));      // 832040
    }

    /* Approach 1 - the definition. Time O(phi^n) ~ O(1.6^n). */
    public static int fibRecursive(int n) {
        return n < 2 ? n : fibRecursive(n - 1) + fibRecursive(n - 2);
    }

    /* Approach 2 - remember computed values. Time O(n), space O(n). */
    public static int fibMemo(int n) {
        return memo(n, new int[n + 1]);
    }

    private static int memo(int n, int[] cache) {
        if (n < 2) return n;
        if (cache[n] == 0) cache[n] = memo(n - 1, cache) + memo(n - 2, cache);
        return cache[n];
    }

    /* Approach 3 - keep only the last two values. Time O(n), space O(1). */
    public static int fib(int n) {
        int a = 0, b = 1;                 // F(i), F(i + 1)
        for (int i = 0; i < n; i++) {
            int next = a + b;
            a = b;
            b = next;
        }
        return a;
    }

    /* Approach 4 - [[1,1],[1,0]]^n = [[F(n+1),F(n)],[F(n),F(n-1)]]; fast exponentiation. Time O(log n). */
    public static int fibMatrix(int n) {
        long[][] result = {{1, 0}, {0, 1}}, base = {{1, 1}, {1, 0}};
        for (int p = n; p > 0; p >>= 1) {
            if ((p & 1) == 1) result = multiply(result, base);
            base = multiply(base, base);
        }
        return (int) result[0][1];
    }

    private static long[][] multiply(long[][] x, long[][] y) {
        return new long[][]{
                {x[0][0] * y[0][0] + x[0][1] * y[1][0], x[0][0] * y[0][1] + x[0][1] * y[1][1]},
                {x[1][0] * y[0][0] + x[1][1] * y[1][0], x[1][0] * y[0][1] + x[1][1] * y[1][1]}};
    }
}
