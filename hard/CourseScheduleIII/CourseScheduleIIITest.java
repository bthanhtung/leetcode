package CourseScheduleIII;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourseScheduleIIITest {

    @Test
    void examplesAndRandom() {
        assertEquals(3, CourseScheduleIII.scheduleCourse(new int[][]{{100, 200}, {200, 1300}, {1000, 1250}, {2000, 3200}}));
        assertEquals(1, CourseScheduleIII.scheduleCourse(new int[][]{{1, 2}}));
        assertEquals(0, CourseScheduleIII.scheduleCourse(new int[][]{{3, 2}, {4, 3}}));
        Random r = new Random(630);
        for (int t = 0; t < 2000; t++) {
            int[][] cs = new int[1 + r.nextInt(8)][];
            for (int i = 0; i < cs.length; i++) cs[i] = new int[]{1 + r.nextInt(8), 1 + r.nextInt(20)};
            assertEquals(CourseScheduleIII.scheduleCourseDp(cs), CourseScheduleIII.scheduleCourse(cs));
        }
    }
}
