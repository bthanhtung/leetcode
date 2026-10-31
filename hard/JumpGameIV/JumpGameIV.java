package JumpGameIV;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/*
 * 1345. Jump Game IV — https://leetcode.com/problems/jump-game-iv/
 * From index i you can jump to i + 1, i - 1, or any j with arr[j] == arr[i]. Return the minimum number of jumps from
 * index 0 to the last index.
 * Input: [100,-23,-23,404,100,23,23,23,3,404] -> 3 (0 → 4 → 3 → 9) · [7] -> 0 · [7,6,9,6,9,6,9,7] -> 1
 * Explanation: README.md
 */
public class JumpGameIV {
    public static void main(String[] args) {
        System.out.println(minJumps(new int[]{100, -23, -23, 404, 100, 23, 23, 23, 3, 404}));     // 3
        System.out.println(minJumpsBidirectional(new int[]{7, 6, 9, 6, 9, 6, 9, 7}));            // 1
    }

    private static Map<Integer, List<Integer>> groups(int[] arr) {
        Map<Integer, List<Integer>> g = new HashMap<>();
        for (int i = 0; i < arr.length; i++) g.computeIfAbsent(arr[i], k -> new ArrayList<>()).add(i);
        return g;
    }

    /*
     * Approach 1 - BFS. The trick: after visiting the "same value" edges of a value once, delete that group - every
     * index in it is already queued, so scanning it again would only cost time (it would make BFS O(n^2)). Time O(n).
     */
    public static int minJumps(int[] arr) {
        int n = arr.length;
        if (n == 1) return 0;
        Map<Integer, List<Integer>> same = groups(arr);
        boolean[] seen = new boolean[n];
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(0);
        seen[0] = true;
        for (int steps = 0; !queue.isEmpty(); steps++) {
            for (int size = queue.size(); size > 0; size--) {
                int i = queue.poll();
                if (i == n - 1) return steps;
                List<Integer> next = new ArrayList<>(same.getOrDefault(arr[i], List.of()));
                same.remove(arr[i]);
                next.add(i - 1);
                next.add(i + 1);
                for (int j : next) {
                    if (j >= 0 && j < n && !seen[j]) {
                        seen[j] = true;
                        queue.add(j);
                    }
                }
            }
        }
        return -1;
    }

    /* Approach 2 - bidirectional BFS: grow the smaller frontier from either end until they meet. Same O(n), fewer visits. */
    public static int minJumpsBidirectional(int[] arr) {
        int n = arr.length;
        if (n == 1) return 0;
        Map<Integer, List<Integer>> same = groups(arr);
        Set<Integer> front = new HashSet<>(List.of(0)), back = new HashSet<>(List.of(n - 1)), seen = new HashSet<>(List.of(0, n - 1));
        for (int steps = 0; !front.isEmpty(); steps++) {
            if (front.size() > back.size()) {
                Set<Integer> t = front;
                front = back;
                back = t;
            }
            Set<Integer> next = new HashSet<>();
            for (int i : front) {
                List<Integer> candidates = new ArrayList<>(same.getOrDefault(arr[i], List.of()));
                same.remove(arr[i]);
                candidates.add(i - 1);
                candidates.add(i + 1);
                for (int j : candidates) {
                    if (back.contains(j)) return steps + 1;
                    if (j >= 0 && j < n && seen.add(j)) next.add(j);
                }
            }
            front = next;
        }
        return -1;
    }
}
