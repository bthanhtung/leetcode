package NextGreaterElementI;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class NextGreaterElementITest {

    @Test
    void examplesAndRandom() {
        assertArrayEquals(new int[]{-1, 3, -1}, NextGreaterElementI.nextGreaterElement(new int[]{4, 1, 2}, new int[]{1, 3, 4, 2}));
        assertArrayEquals(new int[]{3, -1}, NextGreaterElementI.nextGreaterElement(new int[]{2, 4}, new int[]{1, 2, 3, 4}));
        Random r = new Random(496);
        for (int t = 0; t < 500; t++) {
            List<Integer> values = new ArrayList<>();
            for (int v = 0; v < 1 + r.nextInt(12); v++) values.add(v);
            Collections.shuffle(values, r);
            int[] nums2 = values.stream().mapToInt(Integer::intValue).toArray();
            Collections.shuffle(values, r);
            int[] nums1 = values.subList(0, 1 + r.nextInt(values.size())).stream().mapToInt(Integer::intValue).toArray();
            assertArrayEquals(NextGreaterElementI.nextGreaterElementBruteForce(nums1, nums2), NextGreaterElementI.nextGreaterElement(nums1, nums2));
        }
    }
}
