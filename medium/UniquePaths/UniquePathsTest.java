package UniquePaths;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UniquePathsTest {

    @Test
    void examplesAndAgreement() {
        assertEquals(28, UniquePaths.uniquePaths(3, 7));
        assertEquals(3, UniquePaths.uniquePaths(3, 2));
        for (int m = 1; m <= 17; m++) {
            for (int n = 1; n <= 17; n++) {
                int expected = UniquePaths.uniquePathsMemo(m, n);
                assertEquals(expected, UniquePaths.uniquePaths(m, n));
                assertEquals(expected, UniquePaths.uniquePathsMath(m, n));
            }
        }
        assertEquals(UniquePaths.uniquePaths(23, 12), UniquePaths.uniquePathsMath(23, 12));
    }
}
