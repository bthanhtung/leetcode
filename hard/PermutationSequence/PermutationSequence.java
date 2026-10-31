package PermutationSequence;

import java.util.ArrayList;
import java.util.List;

/*
 * 60. Permutation Sequence — https://leetcode.com/problems/permutation-sequence/
 * The n! permutations of 1..n listed in lexicographic order; return the k-th (1-indexed).
 * Input: n = 3, k = 3 -> "213" · n = 4, k = 9 -> "2314" · n = 3, k = 1 -> "123"
 * Explanation: README.md
 */
public class PermutationSequence {
    public static void main(String[] args) {
        System.out.println(getPermutation(4, 9));                // 2314
        System.out.println(getPermutationNextPermutation(3, 3)); // 213
    }

    /*
     * Approach 1 - each choice of the first digit covers (n-1)! permutations, so the first digit is the
     * ((k-1) / (n-1)!)-th unused digit; repeat with the remainder. Time O(n^2) (list removals).
     */
    public static String getPermutation(int n, int k) {
        List<Integer> digits = new ArrayList<>();
        int[] fact = new int[n + 1];
        fact[0] = 1;
        for (int i = 1; i <= n; i++) {
            digits.add(i);
            fact[i] = fact[i - 1] * i;
        }
        StringBuilder sb = new StringBuilder();
        k--;                                                    // 0-indexed rank
        for (int i = n; i >= 1; i--) {
            int index = k / fact[i - 1];
            sb.append(digits.remove(index));
            k %= fact[i - 1];
        }
        return sb.toString();
    }

    /* Approach 2 - start from 1..n and apply "next permutation" (problem 31) k - 1 times. Time O(n * k). */
    public static String getPermutationNextPermutation(int n, int k) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = i + 1;
        for (int step = 1; step < k; step++) nextPermutation(a);
        StringBuilder sb = new StringBuilder();
        for (int x : a) sb.append(x);
        return sb.toString();
    }

    private static void nextPermutation(int[] a) {
        int i = a.length - 2;
        while (i >= 0 && a[i] >= a[i + 1]) i--;
        if (i >= 0) {
            int j = a.length - 1;
            while (a[j] <= a[i]) j--;
            swap(a, i, j);
        }
        for (int l = i + 1, r = a.length - 1; l < r; l++, r--) swap(a, l, r);
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
