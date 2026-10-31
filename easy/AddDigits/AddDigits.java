package AddDigits;

/*
 * 258. Add Digits — https://leetcode.com/problems/add-digits/
 * Repeatedly add all digits of num until the result has only one digit, and return it.
 * Input: 38 -> 2 (3 + 8 = 11, 1 + 1 = 2) · 0 -> 0. Follow up: O(1) without loops.
 * Explanation: README.md
 */
public class AddDigits {
    public static void main(String[] args) {
        System.out.println(addDigits(38));           // 2
        System.out.println(addDigitsLoop(0));        // 0
    }

    /* Approach 1 - simulate. Time O(log n) - the number shrinks very fast. */
    public static int addDigitsLoop(int num) {
        while (num >= 10) {
            int sum = 0;
            for (int x = num; x > 0; x /= 10) sum += x % 10;
            num = sum;
        }
        return num;
    }

    /* Approach 2 - digital root: a number and its digit sum leave the same remainder mod 9. Time O(1). */
    public static int addDigits(int num) {
        return num == 0 ? 0 : 1 + (num - 1) % 9;
    }
}
