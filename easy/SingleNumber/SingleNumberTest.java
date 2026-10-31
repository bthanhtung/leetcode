package SingleNumber;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SingleNumberTest {

    @Test
    void examples() {
        assertEquals(1, SingleNumber.singleNumber(new int[]{2, 2, 1}));
        assertEquals(4, SingleNumber.singleNumber(new int[]{4, 1, 2, 1, 2}));
        assertEquals(1, SingleNumber.singleNumber(new int[]{1}));
        assertEquals(-7, SingleNumber.singleNumber(new int[]{-3, -7, -3}));
    }

    @Test
    void randomAgree() {
        Random r = new Random(136);
        for (int t = 0; t < 500; t++) {
            List<Integer> values = new ArrayList<>(r.ints(1 + r.nextInt(10), -1_000_000_000, 1_000_000_000).distinct().boxed().toList());
            int single = values.removeFirst();
            List<Integer> all = new ArrayList<>(values);
            all.addAll(values);
            all.add(single);
            Collections.shuffle(all, r);
            int[] nums = all.stream().mapToInt(Integer::intValue).toArray();
            assertEquals(single, SingleNumber.singleNumber(nums));
            assertEquals(single, SingleNumber.singleNumberSet(nums));
            assertEquals(single, SingleNumber.singleNumberMath(nums));
        }
    }
}
