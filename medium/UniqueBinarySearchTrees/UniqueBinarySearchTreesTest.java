package UniqueBinarySearchTrees;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UniqueBinarySearchTreesTest {

    @Test
    void catalanNumbers() {
        int[] catalan = {1, 1, 2, 5, 14, 42, 132, 429, 1430, 4862};
        for (int n = 1; n < catalan.length; n++) assertEquals(catalan[n], UniqueBinarySearchTrees.numTrees(n));
        for (int n = 1; n <= 19; n++) {
            int expected = UniqueBinarySearchTrees.numTrees(n);
            assertEquals(expected, UniqueBinarySearchTrees.numTreesMemo(n));
            assertEquals(expected, UniqueBinarySearchTrees.numTreesCatalan(n));
        }
        assertEquals(1767263190, UniqueBinarySearchTrees.numTrees(19));
    }
}
