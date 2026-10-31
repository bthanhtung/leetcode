package TwentyFourGame;

import java.util.ArrayList;
import java.util.List;

/*
 * 679. 24 Game — https://leetcode.com/problems/24-game/
 * Given 4 cards with numbers 1-9, can you combine them with + - * / and parentheses to get exactly 24?
 * Division is real division; no unary minus; no concatenating digits.
 * Input: [4,1,8,7] -> true ((8-4)*(7-1)) · [1,2,1,2] -> false
 * Explanation: README.md
 */
public class TwentyFourGame {
    public static void main(String[] args) {
        System.out.println(judgePoint24(new int[]{4, 1, 8, 7}));          // true
        System.out.println(judgePoint24Double(new int[]{1, 2, 1, 2}));    // false
    }

    /** Exact fraction (numerator, denominator) so 8 / (3 - 8 / 3) = 24 is recognized without rounding errors. */
    private record Frac(long num, long den) {
        static Frac of(long num, long den) {
            if (den < 0) {
                num = -num;
                den = -den;
            }
            long g = gcd(Math.abs(num), den);
            return new Frac(num / g, den / g);
        }

        private static long gcd(long a, long b) {
            return b == 0 ? Math.max(a, 1) : gcd(b, a % b);
        }
    }

    /*
     * Approach 1 - pick any two numbers, replace them with each possible result (a+b, a-b, b-a, a*b, a/b, b/a), recurse
     * on the shorter list. Every bracketing is covered because any expression's last operation combines two results.
     */
    public static boolean judgePoint24(int[] cards) {
        List<Frac> nums = new ArrayList<>();
        for (int c : cards) nums.add(Frac.of(c, 1));
        return solve(nums);
    }

    private static boolean solve(List<Frac> nums) {
        if (nums.size() == 1) return nums.getFirst().num() == 24 && nums.getFirst().den() == 1;
        for (int i = 0; i < nums.size(); i++) {
            for (int j = i + 1; j < nums.size(); j++) {
                List<Frac> rest = new ArrayList<>();
                for (int k = 0; k < nums.size(); k++) if (k != i && k != j) rest.add(nums.get(k));
                Frac a = nums.get(i), b = nums.get(j);
                List<Frac> options = new ArrayList<>(List.of(
                        Frac.of(a.num() * b.den() + b.num() * a.den(), a.den() * b.den()),
                        Frac.of(a.num() * b.den() - b.num() * a.den(), a.den() * b.den()),
                        Frac.of(b.num() * a.den() - a.num() * b.den(), a.den() * b.den()),
                        Frac.of(a.num() * b.num(), a.den() * b.den())));
                if (b.num() != 0) options.add(Frac.of(a.num() * b.den(), a.den() * b.num()));
                if (a.num() != 0) options.add(Frac.of(b.num() * a.den(), b.den() * a.num()));
                for (Frac f : options) {
                    rest.add(f);
                    if (solve(rest)) return true;
                    rest.removeLast();
                }
            }
        }
        return false;
    }

    /* Approach 2 - the same search with doubles; compare with a small epsilon. Shorter, and fine for 4 cards. */
    public static boolean judgePoint24Double(int[] cards) {
        List<Double> nums = new ArrayList<>();
        for (int c : cards) nums.add((double) c);
        return solveDouble(nums);
    }

    private static boolean solveDouble(List<Double> nums) {
        if (nums.size() == 1) return Math.abs(nums.getFirst() - 24) < 1e-6;
        for (int i = 0; i < nums.size(); i++) {
            for (int j = 0; j < nums.size(); j++) {
                if (i == j) continue;                                   // ordered pairs cover a-b and b-a, a/b and b/a
                List<Double> rest = new ArrayList<>();
                for (int k = 0; k < nums.size(); k++) if (k != i && k != j) rest.add(nums.get(k));
                double a = nums.get(i), b = nums.get(j);
                double[] options = {a + b, a - b, a * b, Math.abs(b) > 1e-9 ? a / b : Double.NaN};
                for (double v : options) {
                    if (Double.isNaN(v)) continue;
                    rest.add(v);
                    if (solveDouble(rest)) return true;
                    rest.removeLast();
                }
            }
        }
        return false;
    }
}
