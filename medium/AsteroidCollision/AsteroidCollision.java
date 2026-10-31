package AsteroidCollision;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/*
 * 735. Asteroid Collision — https://leetcode.com/problems/asteroid-collision/
 * Asteroids move along a line: the sign is the direction (+ right, - left), the absolute value the size.
 * When two meet, the smaller explodes; equal sizes both explode. Return the state after all collisions.
 * Input: [5,10,-5] -> [5,10] · [8,-8] -> [] · [10,2,-5] -> [10] · [-2,-1,1,2] -> [-2,-1,1,2]
 * Explanation: README.md
 */
public class AsteroidCollision {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(asteroidCollision(new int[]{10, 2, -5})));     // [10]
    }

    /*
     * Only a right-mover on the stack followed by a new left-mover can collide. Pop smaller right-movers; stop when
     * the top is bigger (new one dies) or equal (both die). Each asteroid is pushed and popped once: O(n).
     */
    public static int[] asteroidCollision(int[] asteroids) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (int a : asteroids) {
            boolean alive = true;
            while (alive && a < 0 && !stack.isEmpty() && stack.peekLast() > 0) {
                int top = stack.peekLast();
                if (top < -a) stack.pollLast();               // top explodes, keep checking
                else {
                    if (top == -a) stack.pollLast();          // both explode
                    alive = false;
                }
            }
            if (alive) stack.addLast(a);
        }
        return stack.stream().mapToInt(Integer::intValue).toArray();
    }
}
