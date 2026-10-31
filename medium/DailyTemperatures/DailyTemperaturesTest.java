package DailyTemperatures;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class DailyTemperaturesTest {

    @Test
    void examplesAndRandom() {
        assertArrayEquals(new int[]{1, 1, 4, 2, 1, 1, 0, 0}, DailyTemperatures.dailyTemperatures(new int[]{73, 74, 75, 71, 69, 72, 76, 73}));
        assertArrayEquals(new int[]{1, 1, 1, 0}, DailyTemperatures.dailyTemperatures(new int[]{30, 40, 50, 60}));
        assertArrayEquals(new int[]{1, 1, 0}, DailyTemperatures.dailyTemperaturesJumps(new int[]{30, 60, 90}));
        Random r = new Random(739);
        for (int t = 0; t < 2000; t++) {
            int[] temps = r.ints(1 + r.nextInt(15), 30, 40).toArray();
            int[] expected = DailyTemperatures.dailyTemperaturesBruteForce(temps);
            assertArrayEquals(expected, DailyTemperatures.dailyTemperatures(temps));
            assertArrayEquals(expected, DailyTemperatures.dailyTemperaturesJumps(temps));
        }
    }
}
