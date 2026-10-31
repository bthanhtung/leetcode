package IntegerToEnglishWords;

/*
 * 273. Integer to English Words — https://leetcode.com/problems/integer-to-english-words/
 * Convert a non-negative integer to its English words representation.
 * Input: 123 -> "One Hundred Twenty Three" · 12345 -> "Twelve Thousand Three Hundred Forty Five"
 *        1234567 -> "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven" · 0 -> "Zero"
 * Explanation: README.md
 */
public class IntegerToEnglishWords {
    private static final String[] BELOW_20 = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
            "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    private static final String[] TENS = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    private static final String[] SCALES = {"", "Thousand", "Million", "Billion"};

    public static void main(String[] args) {
        System.out.println(numberToWords(1234567));
    }

    /* Split into groups of three digits from the right; spell each non-zero group and append its scale. */
    public static String numberToWords(int num) {
        if (num == 0) return "Zero";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; num > 0; i++, num /= 1000) {
            int chunk = num % 1000;
            if (chunk != 0) sb.insert(0, three(chunk) + (SCALES[i].isEmpty() ? "" : " " + SCALES[i]) + " ");
        }
        return sb.toString().trim();
    }

    /** Words for 1..999. */
    private static String three(int n) {
        StringBuilder sb = new StringBuilder();
        if (n >= 100) {
            sb.append(BELOW_20[n / 100]).append(" Hundred ");
            n %= 100;
        }
        if (n >= 20) {
            sb.append(TENS[n / 10]).append(' ');
            n %= 10;
        }
        if (n > 0) sb.append(BELOW_20[n]);
        return sb.toString().trim();
    }
}
