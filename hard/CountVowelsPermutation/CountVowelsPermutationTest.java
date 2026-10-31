package CountVowelsPermutation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountVowelsPermutationTest {

    @Test
    void examplesAndAgreement() {
        assertEquals(5, CountVowelsPermutation.countVowelPermutation(1));
        assertEquals(10, CountVowelsPermutation.countVowelPermutation(2));
        assertEquals(68, CountVowelsPermutation.countVowelPermutation(5));
        assertEquals(18208803, CountVowelsPermutation.countVowelPermutation(144));
        for (int n = 1; n <= 2000; n++) {
            assertEquals(CountVowelsPermutation.countVowelPermutation(n), CountVowelsPermutation.countVowelPermutationMatrix(n));
        }
    }
}
