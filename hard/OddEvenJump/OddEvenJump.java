package OddEvenJump;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Map;
import java.util.TreeMap;

/*
 * 975. Odd Even Jump — https://leetcode.com/problems/odd-even-jump/
 * From index i, odd-numbered jumps (1st, 3rd, ...) go to the smallest j > i with arr[j] >= arr[i] (the smallest such
 * value, lowest index on ties); even jumps go to the largest arr[j] <= arr[i]. A start is good if you can reach the end.
 * Count the good starting indexes.
 * Input: [10,13,12,14,15] -> 2 · [2,3,1,1,4] -> 3 · [5,1,3,4,2] -> 3
 * Explanation: README.md
 */
public class OddEvenJump {
    public static void main(String[] args) {
        System.out.println(oddEvenJumps(new int[]{2, 3, 1, 1, 4}));        // 3
        System.out.println(oddEvenJumpsStack(new int[]{5, 1, 3, 4, 2}));   // 3
    }

    /*
     * Approach 1 - walk from the right with a TreeMap value -> index of what lies to the right; ceilingEntry gives the
     * odd jump, floorEntry the even jump. odd[i] = even[next], even[i] = odd[next]. Time O(n log n).
     */
    public static int oddEvenJumps(int[] arr) {
        int n = arr.length;
        boolean[] odd = new boolean[n], even = new boolean[n];
        odd[n - 1] = even[n - 1] = true;
        TreeMap<Integer, Integer> right = new TreeMap<>();
        right.put(arr[n - 1], n - 1);
        int good = 1;
        for (int i = n - 2; i >= 0; i--) {
            Map.Entry<Integer, Integer> up = right.ceilingEntry(arr[i]), down = right.floorEntry(arr[i]);
            if (up != null) odd[i] = even[up.getValue()];
            if (down != null) even[i] = odd[down.getValue()];
            if (odd[i]) good++;
            right.put(arr[i], i);                          // overwrite: the lower index wins ties
        }
        return good;
    }

    /*
     * Approach 2 - compute every jump target first: sort indexes by (value, index); in that order, a monotonic stack
     * of indexes yields "next index to the right that appears later in sorted order" = odd jump. Even jumps use
     * (value descending, index). Then the same DP. Time O(n log n).
     */
    public static int oddEvenJumpsStack(int[] arr) {
        int n = arr.length;
        Integer[] byValue = new Integer[n];
        for (int i = 0; i < n; i++) byValue[i] = i;
        Arrays.sort(byValue, (a, b) -> arr[a] != arr[b] ? arr[a] - arr[b] : a - b);
        int[] oddNext = nextGreaterIndex(byValue);
        Arrays.sort(byValue, (a, b) -> arr[a] != arr[b] ? arr[b] - arr[a] : a - b);
        int[] evenNext = nextGreaterIndex(byValue);
        boolean[] odd = new boolean[n], even = new boolean[n];
        odd[n - 1] = even[n - 1] = true;
        int good = 1;
        for (int i = n - 2; i >= 0; i--) {
            if (oddNext[i] >= 0) odd[i] = even[oddNext[i]];
            if (evenNext[i] >= 0) even[i] = odd[evenNext[i]];
            if (odd[i]) good++;
        }
        return good;
    }

    private static int[] nextGreaterIndex(Integer[] order) {
        int[] next = new int[order.length];
        Arrays.fill(next, -1);
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i : order) {
            while (!stack.isEmpty() && stack.peek() < i) next[stack.pop()] = i;
            stack.push(i);
        }
        return next;
    }
}
