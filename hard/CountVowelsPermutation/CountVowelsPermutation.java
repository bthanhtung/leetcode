package CountVowelsPermutation;

/*
 * 1220. Count Vowels Permutation — https://leetcode.com/problems/count-vowels-permutation/
 * Count strings of length n made of vowels where 'a' may only be followed by 'e'; 'e' by 'a' or 'i'; 'i' by anything
 * except 'i'; 'o' by 'i' or 'u'; 'u' by 'a'. Return the count modulo 10^9 + 7.
 * Input: 1 -> 5 · 2 -> 10 · 5 -> 68
 * Explanation: README.md
 */
public class CountVowelsPermutation {
    private static final long MOD = 1_000_000_007L;

    public static void main(String[] args) {
        System.out.println(countVowelPermutation(5));          // 68
        System.out.println(countVowelPermutationMatrix(144));  // 18208803
    }

    /*
     * Approach 1 - track how many valid strings end with each vowel; reverse the rules to see who can come before:
     * a <- e, i, u;  e <- a, i;  i <- e, o;  o <- i;  u <- i, o.  Time O(n), space O(1).
     */
    public static int countVowelPermutation(int n) {
        long a = 1, e = 1, i = 1, o = 1, u = 1;
        for (int len = 2; len <= n; len++) {
            long na = (e + i + u) % MOD, ne = (a + i) % MOD, ni = (e + o) % MOD, no = i % MOD, nu = (i + o) % MOD;
            a = na;
            e = ne;
            i = ni;
            o = no;
            u = nu;
        }
        return (int) ((a + e + i + o + u) % MOD);
    }

    /* Approach 2 - the step is a fixed 5x5 matrix; raise it to the (n-1)-th power by squaring. Time O(125 log n). */
    public static int countVowelPermutationMatrix(int n) {
        long[][] step = {                     // step[x][y] = 1 if vowel y can be followed by vowel x
                {0, 1, 1, 0, 1},              // a <- e, i, u
                {1, 0, 1, 0, 0},              // e <- a, i
                {0, 1, 0, 1, 0},              // i <- e, o
                {0, 0, 1, 0, 0},              // o <- i
                {0, 0, 1, 1, 0}};             // u <- i, o
        long[][] power = identity();
        for (int p = n - 1; p > 0; p >>= 1) {
            if ((p & 1) == 1) power = multiply(power, step);
            step = multiply(step, step);
        }
        long total = 0;
        for (long[] row : power) for (long v : row) total += v;    // start vector is all ones
        return (int) (total % MOD);
    }

    private static long[][] identity() {
        long[][] m = new long[5][5];
        for (int i = 0; i < 5; i++) m[i][i] = 1;
        return m;
    }

    private static long[][] multiply(long[][] x, long[][] y) {
        long[][] r = new long[5][5];
        for (int i = 0; i < 5; i++)
            for (int k = 0; k < 5; k++)
                if (x[i][k] != 0)
                    for (int j = 0; j < 5; j++) r[i][j] = (r[i][j] + x[i][k] * y[k][j]) % MOD;
        return r;
    }
}
