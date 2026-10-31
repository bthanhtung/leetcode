package PalindromePartitioning;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PalindromePartitioningTest {

    @Test
    void examplesAndRandom() {
        assertEquals(List.of(List.of("a", "a", "b"), List.of("aa", "b")), PalindromePartitioning.partition("aab"));
        assertEquals(List.of(List.of("a")), PalindromePartitioning.partition("a"));
        assertEquals(1 << 7, PalindromePartitioning.partition("aaaaaaaa").size());      // every cut position is free: 2^(n-1)
        Random r = new Random(131);
        for (int t = 0; t < 300; t++) {
            StringBuilder sb = new StringBuilder();
            for (int k = 1 + r.nextInt(10); k > 0; k--) sb.append((char) ('a' + r.nextInt(2)));
            assertEquals(PalindromePartitioning.partition(sb.toString()), PalindromePartitioning.partitionDp(sb.toString()));
        }
    }
}
