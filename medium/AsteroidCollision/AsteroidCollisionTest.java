package AsteroidCollision;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class AsteroidCollisionTest {

    /** Simulates collisions one adjacent pair at a time until nothing changes. */
    private static int[] simulate(int[] a) {
        List<Integer> list = new ArrayList<>();
        for (int x : a) list.add(x);
        boolean changed = true;
        while (changed) {
            changed = false;
            for (int i = 0; i + 1 < list.size(); i++) {
                int l = list.get(i), r = list.get(i + 1);
                if (l > 0 && r < 0) {
                    if (l > -r) list.remove(i + 1);
                    else if (l < -r) list.remove(i);
                    else { list.remove(i + 1); list.remove(i); }
                    changed = true;
                    break;
                }
            }
        }
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    @Test
    void examplesAndSimulation() {
        assertArrayEquals(new int[]{5, 10}, AsteroidCollision.asteroidCollision(new int[]{5, 10, -5}));
        assertArrayEquals(new int[]{}, AsteroidCollision.asteroidCollision(new int[]{8, -8}));
        assertArrayEquals(new int[]{10}, AsteroidCollision.asteroidCollision(new int[]{10, 2, -5}));
        assertArrayEquals(new int[]{-2, -1, 1, 2}, AsteroidCollision.asteroidCollision(new int[]{-2, -1, 1, 2}));
        Random r = new Random(735);
        for (int t = 0; t < 3000; t++) {
            int[] a = new int[1 + r.nextInt(10)];
            for (int i = 0; i < a.length; i++) a[i] = (1 + r.nextInt(4)) * (r.nextBoolean() ? 1 : -1);
            assertArrayEquals(simulate(a), AsteroidCollision.asteroidCollision(a));
        }
    }
}
