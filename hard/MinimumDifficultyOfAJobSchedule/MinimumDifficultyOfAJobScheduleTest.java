package MinimumDifficultyOfAJobSchedule;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinimumDifficultyOfAJobScheduleTest {

    @Test
    void examplesAndRandom() {
        assertEquals(7, MinimumDifficultyOfAJobSchedule.minDifficulty(new int[]{6, 5, 4, 3, 2, 1}, 2));
        assertEquals(-1, MinimumDifficultyOfAJobSchedule.minDifficulty(new int[]{9, 9, 9}, 4));
        assertEquals(3, MinimumDifficultyOfAJobSchedule.minDifficulty(new int[]{1, 1, 1}, 3));
        assertEquals(843, MinimumDifficultyOfAJobSchedule.minDifficulty(new int[]{11, 111, 22, 222, 33, 333, 44, 444}, 6));
        Random r = new Random(1335);
        for (int t = 0; t < 2000; t++) {
            int[] jobs = r.ints(1 + r.nextInt(12), 0, 20).toArray();
            int d = 1 + r.nextInt(6);
            int expected = MinimumDifficultyOfAJobSchedule.minDifficultyTable(jobs, d);
            assertEquals(expected, MinimumDifficultyOfAJobSchedule.minDifficultyMemo(jobs, d));
            assertEquals(expected, MinimumDifficultyOfAJobSchedule.minDifficulty(jobs, d));
        }
    }
}
