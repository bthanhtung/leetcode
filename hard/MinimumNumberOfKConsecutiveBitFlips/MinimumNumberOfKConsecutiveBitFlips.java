package MinimumNumberOfKConsecutiveBitFlips;

/*
 * 995. Minimum Number of K Consecutive Bit Flips — https://leetcode.com/problems/minimum-number-of-k-consecutive-bit-flips/
 * A k-bit flip inverts k consecutive bits of the binary array. Return the minimum number of flips to make every bit 1,
 * or -1 if impossible.
 * Input: [0,1,0], k = 1 -> 2 · [1,1,0], k = 2 -> -1 · [0,0,0,1,0,1,1,0], k = 3 -> 3
 * Explanation: README.md
 */
public class MinimumNumberOfKConsecutiveBitFlips {
    public static void main(String[] args) {
        System.out.println(minKBitFlips(new int[]{0, 0, 0, 1, 0, 1, 1, 0}, 3));          // 3
        System.out.println(minKBitFlipsSimulate(new int[]{1, 1, 0}, 2));               // -1
    }

    /* Approach 1 - greedy: the leftmost 0 can only be fixed by a flip starting there; apply it literally. Time O(n * k). */
    public static int minKBitFlipsSimulate(int[] nums, int k) {
        int[] a = nums.clone();
        int flips = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] == 1) continue;
            if (i + k > a.length) return -1;
            for (int j = i; j < i + k; j++) a[j] ^= 1;
            flips++;
        }
        return flips;
    }

    /*
     * Approach 2 - the same greedy without touching k bits: track how many active flips cover position i (parity is
     * what matters) with a difference array marking where each flip stops. Time O(n), space O(n).
     */
    public static int minKBitFlips(int[] nums, int k) {
        int n = nums.length, active = 0, flips = 0;
        int[] endsAt = new int[n + 1];
        for (int i = 0; i < n; i++) {
            active -= endsAt[i];
            if ((nums[i] + active) % 2 == 0) {            // the bit currently reads 0
                if (i + k > n) return -1;
                flips++;
                active++;
                endsAt[i + k]++;
            }
        }
        return flips;
    }

    /* Approach 3 - O(1) extra space: mark a flip's start by adding 2 to nums[i]; remove its effect k steps later. */
    public static int minKBitFlipsInPlace(int[] nums, int k) {
        int[] a = nums.clone();
        int n = a.length, active = 0, flips = 0;
        for (int i = 0; i < n; i++) {
            if (i >= k && a[i - k] > 1) active--;         // the flip that started at i - k has ended
            if ((a[i] + active) % 2 == 0) {
                if (i + k > n) return -1;
                a[i] += 2;
                active++;
                flips++;
            }
        }
        return flips;
    }
}
