package KokoEatingBananas;

/*
 * 875. Koko Eating Bananas — https://leetcode.com/problems/koko-eating-bananas/
 * piles[i] bananas; each hour Koko picks one pile and eats k bananas from it (less if the pile has fewer).
 * Find the minimum integer k so she finishes within h hours.
 * Input: piles = [3,6,7,11], h = 8 -> 4 · [30,11,23,4,20], h = 5 -> 30 · [30,11,23,4,20], h = 6 -> 23
 * Explanation: README.md
 */
public class KokoEatingBananas {
    public static void main(String[] args) {
        System.out.println(minEatingSpeed(new int[]{3, 6, 7, 11}, 8));      // 4
    }

    /*
     * Hours needed at speed k is sum(ceil(p / k)), which only decreases as k grows - binary search the smallest k
     * with hours(k) <= h in [1, max(piles)]. Time O(n log max).
     */
    public static int minEatingSpeed(int[] piles, int h) {
        int lo = 1, hi = 0;
        for (int p : piles) hi = Math.max(hi, p);
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (hours(piles, mid) <= h) hi = mid;
            else lo = mid + 1;
        }
        return lo;
    }

    private static long hours(int[] piles, int k) {
        long total = 0;
        for (int p : piles) total += (p + (long) k - 1) / k;       // ceil without floating point
        return total;
    }
}
