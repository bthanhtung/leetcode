package SingleNumberII;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SingleNumberIITest {

    @Test
    void examplesAndRandom() {
        assertEquals(3, SingleNumberII.singleNumber(new int[]{2, 2, 3, 2}));
        assertEquals(99, SingleNumberII.singleNumber(new int[]{0, 1, 0, 1, 0, 1, 99}));
        Random r = new Random(137);
        for (int t = 0; t < 1000; t++) {
            List<Integer> values = new ArrayList<>(r.ints(1 + r.nextInt(8)).distinct().boxed().toList());
            int single = values.removeFirst();
            List<Integer> all = new ArrayList<>();
            for (int v : values) for (int k = 0; k < 3; k++) all.add(v);
            all.add(single);
            Collections.shuffle(all, r);
            int[] nums = all.stream().mapToInt(Integer::intValue).toArray();
            assertEquals(single, SingleNumberII.singleNumber(nums));
            assertEquals(single, SingleNumberII.singleNumberBits(nums));
            assertEquals(single, SingleNumberII.singleNumberMap(nums));
        }
    }
}
