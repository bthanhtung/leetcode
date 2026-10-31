package MissingNumber;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MissingNumberTest {

    @Test
    void random() {
        Random r = new Random(268);
        for (int t = 0; t < 500; t++) {
            int n = 1 + r.nextInt(50), missing = r.nextInt(n + 1);
            List<Integer> values = new ArrayList<>();
            for (int v = 0; v <= n; v++) if (v != missing) values.add(v);
            Collections.shuffle(values, r);
            int[] nums = values.stream().mapToInt(Integer::intValue).toArray();
            assertEquals(missing, MissingNumber.missingNumber(nums));
            assertEquals(missing, MissingNumber.missingNumberXor(nums));
            assertEquals(missing, MissingNumber.missingNumberSet(nums));
            assertEquals(missing, MissingNumber.missingNumberSort(nums));
        }
    }
}
