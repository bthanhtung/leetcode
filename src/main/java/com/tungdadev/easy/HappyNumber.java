package com.tungdadev.easy;

/**
 * @author TungDaDev
 * @since 24/06/2025 - 23:49
 */
public class HappyNumber {
    public static void main(String[] args) {
        int n = 19; // Example input
        HappyNumber happyNumber = new HappyNumber();
        boolean result = happyNumber.isHappy(n);
        System.out.println("Is " + n + " a happy number? " + result);
    }

    public boolean isHappy(int n) {
        if (n <= 0) {
            return false; // Happy numbers are positive integers
        }

        int slow = n;
        int fast = n;

        do {
            slow = getNext(slow); // Move slow pointer by one step
            fast = getNext(getNext(fast)); // Move fast pointer by two steps
        } while (slow != fast);

        return slow == 1; // If they meet at 1, it's a happy number
    }
    private int getNext(int n) {
        int sum = 0;
        while (n > 0) {
            int digit = n % 10; // Get the last digit
            sum += digit * digit; // Square it and add to sum
            n /= 10; // Remove the last digit
        }
        return sum; // Return the sum of squares of digits
    }
}
