package RemoveBoxes;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RemoveBoxesTest {

    @Test
    void examplesAndRandom() {
        assertEquals(23, RemoveBoxes.removeBoxes(new int[]{1, 3, 2, 2, 2, 3, 4, 3, 1}));
        assertEquals(9, RemoveBoxes.removeBoxes(new int[]{1, 1, 1}));
        assertEquals(1, RemoveBoxes.removeBoxes(new int[]{1}));
        Random r = new Random(546);
        for (int t = 0; t < 300; t++) {
            int[] boxes = r.ints(1 + r.nextInt(9), 1, 4).toArray();
            List<Integer> list = new ArrayList<>();
            for (int b : boxes) list.add(b);
            assertEquals(RemoveBoxes.bruteForce(list), RemoveBoxes.removeBoxes(boxes));
        }
    }
}
