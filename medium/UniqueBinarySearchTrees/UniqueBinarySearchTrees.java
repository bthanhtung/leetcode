package UniqueBinarySearchTrees;

/*
 * 96. Unique Binary Search Trees — https://leetcode.com/problems/unique-binary-search-trees/
 * Return the number of structurally unique BSTs that store exactly the values 1 .. n.
 * Input: 3 -> 5 · 1 -> 1
 * Explanation: README.md
 */
public class UniqueBinarySearchTrees {
    public static void main(String[] args) {
        System.out.println(numTrees(3));            // 5
        System.out.println(numTreesCatalan(19));    // 1767263190
    }

    /* Approach 1 - pick each root i; left gets i - 1 values, right gets n - i: G(n) = sum G(i-1) * G(n-i). Memoized. */
    public static int numTreesMemo(int n) {
        return count(n, new int[n + 1]);
    }

    private static int count(int n, int[] memo) {
        if (n <= 1) return 1;
        if (memo[n] == 0) {
            for (int root = 1; root <= n; root++) memo[n] += count(root - 1, memo) * count(n - root, memo);
        }
        return memo[n];
    }

    /* Approach 2 - bottom-up over sizes. Time O(n^2), space O(n). */
    public static int numTrees(int n) {
        int[] g = new int[n + 1];
        g[0] = 1;
        for (int size = 1; size <= n; size++) {
            for (int root = 1; root <= size; root++) {
                g[size] += g[root - 1] * g[size - root];
            }
        }
        return g[n];
    }

    /* Approach 3 - G(n) is the n-th Catalan number: C(n+1) = C(n) * 2(2n+1) / (n+2). Time O(n). */
    public static int numTreesCatalan(int n) {
        long c = 1;
        for (int i = 0; i < n; i++) {
            c = c * 2 * (2 * i + 1) / (i + 2);
        }
        return (int) c;
    }
}
