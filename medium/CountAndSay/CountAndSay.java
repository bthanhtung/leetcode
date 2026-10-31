package CountAndSay;

/*
 * 38. Count and Say — https://leetcode.com/problems/count-and-say/
 * countAndSay(1) = "1"; countAndSay(n) is the run-length encoding of countAndSay(n - 1), read aloud:
 * "1" -> one 1 -> "11" -> two 1s -> "21" -> one 2, one 1 -> "1211" -> "111221" ...
 * Input: 4 -> "1211" · 1 -> "1"
 * Explanation: README.md
 */
public class CountAndSay {
    public static void main(String[] args) {
        System.out.println(countAndSay(4));              // 1211
        System.out.println(countAndSayRecursive(6));     // 312211
    }

    /* Approach 1 - build each term from the previous by counting runs of equal digits. */
    public static String countAndSay(int n) {
        String s = "1";
        for (int i = 1; i < n; i++) s = say(s);
        return s;
    }

    private static String say(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); ) {
            int j = i;
            while (j < s.length() && s.charAt(j) == s.charAt(i)) j++;
            sb.append(j - i).append(s.charAt(i));         // count, then the digit
            i = j;
        }
        return sb.toString();
    }

    /* Approach 2 - the definition written recursively. */
    public static String countAndSayRecursive(int n) {
        return n == 1 ? "1" : say(countAndSayRecursive(n - 1));
    }
}
