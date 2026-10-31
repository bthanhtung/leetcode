package ThirdMaximumNumber;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ThirdMaximumNumberTest {

    @Test
    void examplesAndRandom() {
        assertEquals(1, ThirdMaximumNumber.thirdMax(new int[]{3, 2, 1}));
        assertEquals(2, ThirdMaximumNumber.thirdMax(new int[]{1, 2}));
        assertEquals(1, ThirdMaximumNumber.thirdMax(new int[]{2, 2, 3, 1}));
        assertEquals(Integer.MIN_VALUE, ThirdMaximumNumber.thirdMax(new int[]{1, 2, Integer.MIN_VALUE}));
        Random r = new Random(414);
        for (int t = 0; t < 3000; t++) {
            int[] nums = r.ints(1 + r.nextInt(8), -5, 5).toArray();
            int[] d = Arrays.stream(nums).distinct().sorted().toArray();
            int expected = d.length >= 3 ? d[d.length - 3] : d[d.length - 1];
            assertEquals(expected, ThirdMaximumNumber.thirdMax(nums));
            assertEquals(expected, ThirdMaximumNumber.thirdMaxTreeSet(nums));
        }
    }
}
