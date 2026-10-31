package NumberComplement;

/*
 * 476. Number Complement — https://leetcode.com/problems/number-complement/
 * The complement of a positive integer flips all bits of its binary representation (no leading zeros). Return it.
 * Input: 5 (101) -> 2 (010) · 1 -> 0
 * Explanation: README.md
 */
public class NumberComplement {
    public static void main(String[] args) {
        System.out.println(findComplement(5));        // 2
        System.out.println(findComplementLoop(1));    // 0
    }

    /* Approach 1 - walk the bits, set the flipped bit in the result. Time O(log n). */
    public static int findComplementLoop(int num) {
        int result = 0;
        for (int bit = 0; num > 0; bit++, num >>= 1) {
            if ((num & 1) == 0) result |= 1 << bit;
        }
        return result;
    }

    /* Approach 2 - mask with as many 1s as num has bits; num ^ mask flips exactly those bits. Time O(1). */
    public static int findComplement(int num) {
        int mask = (Integer.highestOneBit(num) << 1) - 1;    // 101 -> highest 100 -> 1000 - 1 = 111
        return num ^ mask;
    }
}
