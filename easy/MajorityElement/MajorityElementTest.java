package MajorityElement;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MajorityElementTest {

    @Test
    void examplesAndRandom() {
        assertEquals(3, MajorityElement.majorityElement(new int[]{3, 2, 3}));
        assertEquals(2, MajorityElement.majorityElement(new int[]{2, 2, 1, 1, 1, 2, 2}));
        Random r = new Random(169);
        for (int t = 0; t < 1000; t++) {
            int n = 1 + r.nextInt(30), major = r.nextInt(10) - 5, k = n / 2 + 1 + r.nextInt(n - n / 2);
            List<Integer> values = new ArrayList<>();
            for (int i = 0; i < k; i++) values.add(major);
            while (values.size() < n) values.add(r.nextInt(10) - 5);
            Collections.shuffle(values, r);
            int[] nums = values.stream().mapToInt(Integer::intValue).toArray();
            assertEquals(major, MajorityElement.majorityElement(nums));
            assertEquals(major, MajorityElement.majorityElementMap(nums));
            assertEquals(major, MajorityElement.majorityElementSort(nums));
        }
    }
}
