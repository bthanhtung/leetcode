package CouplesHoldingHands;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CouplesHoldingHandsTest {

    /** BFS over seatings (tiny inputs) to get the true minimum. */
    private static int bfs(int[] start) {
        Map<String, Integer> dist = new HashMap<>();
        Queue<int[]> q = new ArrayDeque<>();
        dist.put(Arrays.toString(start), 0);
        q.add(start);
        while (!q.isEmpty()) {
            int[] s = q.poll();
            int d = dist.get(Arrays.toString(s));
            boolean done = true;
            for (int i = 0; i < s.length; i += 2) if ((s[i] ^ 1) != s[i + 1]) done = false;
            if (done) return d;
            for (int i = 0; i < s.length; i++)
                for (int j = i + 1; j < s.length; j++) {
                    int[] t = s.clone();
                    int tmp = t[i]; t[i] = t[j]; t[j] = tmp;
                    if (dist.putIfAbsent(Arrays.toString(t), d + 1) == null) q.add(t);
                }
        }
        return -1;
    }

    @Test
    void examplesAndRandom() {
        assertEquals(1, CouplesHoldingHands.minSwapsCouples(new int[]{0, 2, 1, 3}));
        assertEquals(0, CouplesHoldingHands.minSwapsCouples(new int[]{3, 2, 0, 1}));
        Random r = new Random(765);
        for (int t = 0; t < 200; t++) {
            int n = 1 + r.nextInt(4);
            List<Integer> people = new ArrayList<>();
            for (int p = 0; p < 2 * n; p++) people.add(p);
            Collections.shuffle(people, r);
            int[] row = people.stream().mapToInt(Integer::intValue).toArray();
            int expected = bfs(row);
            assertEquals(expected, CouplesHoldingHands.minSwapsCouples(row));
            assertEquals(expected, CouplesHoldingHands.minSwapsCouplesUnionFind(row));
        }
    }
}
