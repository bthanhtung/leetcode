package FizzBuzz;

import java.util.ArrayList;
import java.util.List;

/*
 * 412. Fizz Buzz — https://leetcode.com/problems/fizz-buzz/
 * For i in 1..n output "FizzBuzz" if i is divisible by 3 and 5, "Fizz" if by 3, "Buzz" if by 5, else i.
 * Input: 5 -> ["1","2","Fizz","4","Buzz"]
 * Explanation: README.md
 */
public class FizzBuzz {
    public static void main(String[] args) {
        System.out.println(fizzBuzz(15));
    }

    /* Approach 1 - check 15 first, then 3, then 5. Time O(n). */
    public static List<String> fizzBuzz(int n) {
        List<String> out = new ArrayList<>(n);
        for (int i = 1; i <= n; i++) {
            if (i % 15 == 0) out.add("FizzBuzz");
            else if (i % 3 == 0) out.add("Fizz");
            else if (i % 5 == 0) out.add("Buzz");
            else out.add(Integer.toString(i));
        }
        return out;
    }

    /* Approach 2 - concatenate the words that apply; easy to extend with "7 -> Jazz". Time O(n). */
    public static List<String> fizzBuzzConcat(int n) {
        List<String> out = new ArrayList<>(n);
        for (int i = 1; i <= n; i++) {
            String s = (i % 3 == 0 ? "Fizz" : "") + (i % 5 == 0 ? "Buzz" : "");
            out.add(s.isEmpty() ? Integer.toString(i) : s);
        }
        return out;
    }

    /* Approach 3 - two countdown counters instead of division. Time O(n). */
    public static List<String> fizzBuzzCounters(int n) {
        List<String> out = new ArrayList<>(n);
        int fizz = 3, buzz = 5;
        for (int i = 1; i <= n; i++) {
            fizz--;
            buzz--;
            if (fizz == 0 && buzz == 0) {
                out.add("FizzBuzz");
                fizz = 3;
                buzz = 5;
            } else if (fizz == 0) {
                out.add("Fizz");
                fizz = 3;
            } else if (buzz == 0) {
                out.add("Buzz");
                buzz = 5;
            } else {
                out.add(Integer.toString(i));
            }
        }
        return out;
    }
}
