package SlidingPuzzle;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/*
 * 773. Sliding Puzzle — https://leetcode.com/problems/sliding-puzzle/
 * A 2x3 board holds tiles 1-5 and an empty square 0. A move swaps 0 with a 4-directionally adjacent tile.
 * Return the fewest moves to reach [[1,2,3],[4,5,0]], or -1 if impossible.
 * Input: [[1,2,3],[4,0,5]] -> 1 · [[1,2,3],[5,4,0]] -> -1 · [[4,1,2],[5,0,3]] -> 5
 * Explanation: README.md
 */
public class SlidingPuzzle {
    /** For each index of the flattened 2x3 board, the indexes it can swap with. */
    private static final int[][] NEIGHBOURS = {{1, 3}, {0, 2, 4}, {1, 5}, {0, 4}, {1, 3, 5}, {2, 4}};
    private static final String GOAL = "123450";

    public static void main(String[] args) {
        System.out.println(slidingPuzzle(new int[][]{{4, 1, 2}, {5, 0, 3}}));       // 5
        System.out.println(slidingPuzzleAStar(new int[][]{{1, 2, 3}, {5, 4, 0}}));  // -1
    }

    private static String encode(int[][] board) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : board) for (int v : row) sb.append(v);
        return sb.toString();
    }

    private static String swap(String s, int i, int j) {
        char[] c = s.toCharArray();
        char t = c[i];
        c[i] = c[j];
        c[j] = t;
        return new String(c);
    }

    /* Approach 1 - BFS: each board is a node, each move an edge. At most 6! = 720 states. */
    public static int slidingPuzzle(int[][] board) {
        String start = encode(board);
        Queue<String> queue = new ArrayDeque<>();
        Set<String> seen = new HashSet<>();
        queue.add(start);
        seen.add(start);
        for (int moves = 0; !queue.isEmpty(); moves++) {
            for (int size = queue.size(); size > 0; size--) {
                String s = queue.poll();
                if (s.equals(GOAL)) return moves;
                int zero = s.indexOf('0');
                for (int nb : NEIGHBOURS[zero]) {
                    String next = swap(s, zero, nb);
                    if (seen.add(next)) queue.add(next);
                }
            }
        }
        return -1;
    }

    /* Approach 2 - A*: explore states by moves + Manhattan distance of tiles to their goal (never overestimates). */
    public static int slidingPuzzleAStar(int[][] board) {
        String start = encode(board);
        Map<String, Integer> best = new HashMap<>();
        PriorityQueue<Object[]> open = new PriorityQueue<>((a, b) -> Integer.compare((int) a[1], (int) b[1]));
        best.put(start, 0);
        open.add(new Object[]{start, heuristic(start)});
        while (!open.isEmpty()) {
            Object[] top = open.poll();
            String s = (String) top[0];
            int g = best.get(s);
            if ((int) top[1] > g + heuristic(s)) continue;           // stale entry
            if (s.equals(GOAL)) return g;
            int zero = s.indexOf('0');
            for (int nb : NEIGHBOURS[zero]) {
                String next = swap(s, zero, nb);
                if (g + 1 < best.getOrDefault(next, Integer.MAX_VALUE)) {
                    best.put(next, g + 1);
                    open.add(new Object[]{next, g + 1 + heuristic(next)});
                }
            }
        }
        return -1;
    }

    private static int heuristic(String s) {
        int h = 0;
        for (int i = 0; i < 6; i++) {
            int v = s.charAt(i) - '0';
            if (v == 0) continue;
            int goal = v - 1;
            h += Math.abs(i / 3 - goal / 3) + Math.abs(i % 3 - goal % 3);
        }
        return h;
    }
}
