package JumpGameIV;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JumpGameIVTest {

    /** Plain BFS that scans every index for equal values (O(n^2)). */
    private static int plainBfs(int[] arr) {
        int n = arr.length;
        int[] dist = new int[n];
        Arrays.fill(dist, -1);
        dist[0] = 0;
        Queue<Integer> q = new ArrayDeque<>();
        q.add(0);
        while (!q.isEmpty()) {
            int i = q.poll();
            for (int j = 0; j < n; j++) {
                boolean edge = j == i - 1 || j == i + 1 || (j != i && arr[j] == arr[i]);
                if (edge && dist[j] < 0) {
                    dist[j] = dist[i] + 1;
                    q.add(j);
                }
            }
        }
        return dist[n - 1];
    }

    @Test
    void examplesAndRandom() {
        assertEquals(3, JumpGameIV.minJumps(new int[]{100, -23, -23, 404, 100, 23, 23, 23, 3, 404}));
        assertEquals(0, JumpGameIV.minJumps(new int[]{7}));
        assertEquals(1, JumpGameIV.minJumps(new int[]{7, 6, 9, 6, 9, 6, 9, 7}));
        Random r = new Random(1345);
        for (int t = 0; t < 1000; t++) {
            int[] arr = r.ints(1 + r.nextInt(20), 0, 6).toArray();
            int expected = plainBfs(arr);
            assertEquals(expected, JumpGameIV.minJumps(arr));
            assertEquals(expected, JumpGameIV.minJumpsBidirectional(arr));
        }
    }
}
