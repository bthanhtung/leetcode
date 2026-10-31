package FindAllNumbersDisappearedInAnArray;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FindAllNumbersDisappearedInAnArrayTest {

    @Test
    void examplesAndRandom() {
        assertEquals(List.of(5, 6), FindAllNumbersDisappearedInAnArray.findDisappearedNumbers(new int[]{4, 3, 2, 7, 8, 2, 3, 1}));
        assertEquals(List.of(2), FindAllNumbersDisappearedInAnArray.findDisappearedNumbers(new int[]{1, 1}));
        Random r = new Random(448);
        for (int t = 0; t < 1000; t++) {
            int n = 1 + r.nextInt(15);
            int[] nums = r.ints(n, 1, n + 1).toArray();
            List<Integer> expected = FindAllNumbersDisappearedInAnArray.findDisappearedNumbersSet(nums);
            assertEquals(expected, FindAllNumbersDisappearedInAnArray.findDisappearedNumbers(nums.clone()));
            assertEquals(expected, FindAllNumbersDisappearedInAnArray.findDisappearedNumbersCyclic(nums.clone()));
        }
    }
}
