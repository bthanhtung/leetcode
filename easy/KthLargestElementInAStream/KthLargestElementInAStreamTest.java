package KthLargestElementInAStream;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KthLargestElementInAStreamTest {

    @Test
    void exampleAndRandom() {
        KthLargestElementInAStream.KthLargest kth = new KthLargestElementInAStream.KthLargest(3, new int[]{4, 5, 8, 2});
        int[] expected = {4, 5, 5, 8, 8};
        int[] adds = {3, 5, 10, 9, 4};
        for (int i = 0; i < adds.length; i++) assertEquals(expected[i], kth.add(adds[i]));
        Random r = new Random(703);
        for (int t = 0; t < 100; t++) {
            int k = 1 + r.nextInt(5);
            int[] nums = r.ints(k - 1 + r.nextInt(3), -50, 50).toArray();
            KthLargestElementInAStream.KthLargest a = new KthLargestElementInAStream.KthLargest(k, nums);
            KthLargestElementInAStream.KthLargestSorted b = new KthLargestElementInAStream.KthLargestSorted(k, nums);
            for (int s = 0; s < 50; s++) {
                int v = r.nextInt(100) - 50;
                assertEquals(b.add(v), a.add(v));
            }
        }
    }
}
