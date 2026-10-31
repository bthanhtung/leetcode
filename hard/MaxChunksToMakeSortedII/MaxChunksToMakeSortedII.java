package MaxChunksToMakeSortedII;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/*
 * 768. Max Chunks To Make Sorted II — https://leetcode.com/problems/max-chunks-to-make-sorted-ii/
 * Split arr into as many chunks as possible so that sorting each chunk separately and concatenating them gives the
 * sorted array. Values may repeat. Return the number of chunks.
 * Input: [5,4,3,2,1] -> 1 · [2,1,3,4,4] -> 4 ([2,1],[3],[4],[4])
 * Explanation: README.md
 */
public class MaxChunksToMakeSortedII {
    public static void main(String[] args) {
        System.out.println(maxChunksToSorted(new int[]{2, 1, 3, 4, 4}));          // 4
        System.out.println(maxChunksToSortedStack(new int[]{5, 4, 3, 2, 1}));     // 1
    }

    /* Approach 1 - a cut after i is valid iff max(arr[0..i]) <= min(arr[i+1..]). Count valid cuts. Time O(n). */
    public static int maxChunksToSorted(int[] arr) {
        int n = arr.length;
        int[] suffixMin = new int[n + 1];
        suffixMin[n] = Integer.MAX_VALUE;
        for (int i = n - 1; i >= 0; i--) suffixMin[i] = Math.min(suffixMin[i + 1], arr[i]);
        int chunks = 0, prefixMax = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            prefixMax = Math.max(prefixMax, arr[i]);
            if (prefixMax <= suffixMin[i + 1]) chunks++;
        }
        return chunks;
    }

    /*
     * Approach 2 - stack holding the max of each chunk (increasing). A smaller number must merge with every chunk whose
     * max is bigger than it; the merged chunk keeps the largest max. Time O(n).
     */
    public static int maxChunksToSortedStack(int[] arr) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (int x : arr) {
            int chunkMax = x;
            while (!stack.isEmpty() && stack.peek() > x) chunkMax = Math.max(chunkMax, stack.pop());
            stack.push(chunkMax);
        }
        return stack.size();
    }

    /* Approach 3 - compare running sums with the sorted copy: equal prefix sums (of equal multisets) mark a cut. */
    public static int maxChunksToSortedSums(int[] arr) {
        int[] sorted = arr.clone();
        Arrays.sort(sorted);
        long a = 0, b = 0;
        int chunks = 0;
        for (int i = 0; i < arr.length; i++) {
            a += arr[i];
            b += sorted[i];
            if (a == b) chunks++;
        }
        return chunks;
    }
}
