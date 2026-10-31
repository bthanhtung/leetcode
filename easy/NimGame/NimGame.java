package NimGame;

/*
 * 292. Nim Game — https://leetcode.com/problems/nim-game/
 * A heap of n stones; players alternate removing 1 to 3 stones; whoever takes the last stone wins. You go first and
 * both play optimally. Can you win?
 * Input: 4 -> false · 1 -> true · 2 -> true
 * Explanation: README.md
 */
public class NimGame {
    public static void main(String[] args) {
        System.out.println(canWinNim(4));          // false
        System.out.println(canWinNimDp(7));        // true
    }

    /* Approach 1 - win(n) is true if some move leaves the opponent in a losing position. Time O(n) - too slow for n = 2^31. */
    public static boolean canWinNimDp(int n) {
        boolean[] win = new boolean[Math.max(n + 1, 4)];
        win[1] = win[2] = win[3] = true;
        for (int i = 4; i <= n; i++) {
            win[i] = !win[i - 1] || !win[i - 2] || !win[i - 3];
        }
        return win[n];
    }

    /* Approach 2 - the table repeats W W W L: you lose exactly when n is a multiple of 4. Time O(1). */
    public static boolean canWinNim(int n) {
        return n % 4 != 0;
    }
}
