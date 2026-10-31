package FindInMountainArray;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FindInMountainArrayTest {

    @Test
    void examplesAndRandomMountains() {
        assertEquals(2, FindInMountainArray.findInMountainArray(3, new FindInMountainArray.MountainArray(1, 2, 3, 4, 5, 3, 1)));
        assertEquals(-1, FindInMountainArray.findInMountainArray(3, new FindInMountainArray.MountainArray(0, 1, 2, 4, 2, 1)));
        Random r = new Random(1095);
        for (int t = 0; t < 1000; t++) {
            int up = 1 + r.nextInt(8), down = 1 + r.nextInt(8);
            int[] a = new int[up + down + 1];
            a[0] = r.nextInt(5);
            for (int i = 1; i <= up; i++) a[i] = a[i - 1] + 1 + r.nextInt(3);
            for (int i = up + 1; i < a.length; i++) a[i] = a[i - 1] - 1 - r.nextInt(3);
            int target = r.nextInt(a[up] + 3) - 1;
            int expected = -1;
            for (int i = 0; i < a.length; i++) if (a[i] == target) { expected = i; break; }
            FindInMountainArray.MountainArray m = new FindInMountainArray.MountainArray(a);
            assertEquals(expected, FindInMountainArray.findInMountainArray(target, m));
            assertTrue(m.calls <= 100);
        }
        int[] big = new int[10_000];
        for (int i = 0; i < 6000; i++) big[i] = i;
        for (int i = 6000; i < 10_000; i++) big[i] = 6000 - (i - 6000) - 1;
        FindInMountainArray.MountainArray m = new FindInMountainArray.MountainArray(big);
        assertEquals(1234, FindInMountainArray.findInMountainArray(1234, m));
        assertTrue(m.calls <= 100, "calls: " + m.calls);
    }
}
