package FreedomTrail;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FreedomTrailTest {

    @Test
    void examplesAndRandom() {
        assertEquals(4, FreedomTrail.findRotateSteps("godding", "gd"));
        assertEquals(13, FreedomTrail.findRotateSteps("godding", "godding"));
        Random r = new Random(514);
        for (int t = 0; t < 500; t++) {
            StringBuilder ring = new StringBuilder(), key = new StringBuilder();
            for (int i = 1 + r.nextInt(10); i > 0; i--) ring.append((char) ('a' + r.nextInt(3)));
            for (int i = 1 + r.nextInt(8); i > 0; i--) key.append(ring.charAt(r.nextInt(ring.length())));
            assertEquals(FreedomTrail.findRotateStepsMemo(ring.toString(), key.toString()), FreedomTrail.findRotateSteps(ring.toString(), key.toString()));
        }
    }
}
