package CarFleet;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/*
 * 853. Car Fleet — https://leetcode.com/problems/car-fleet/
 * n cars drive to target on a one-lane road. A car catching up with a slower car ahead joins it and drives at its
 * speed (a fleet). How many fleets arrive at the target?
 * Input: target = 12, position = [10,8,0,5,3], speed = [2,4,1,1,3] -> 3
 * Explanation: README.md
 */
public class CarFleet {
    public static void main(String[] args) {
        System.out.println(carFleet(12, new int[]{10, 8, 0, 5, 3}, new int[]{2, 4, 1, 1, 3}));          // 3
        System.out.println(carFleetStack(100, new int[]{0, 2, 4}, new int[]{4, 2, 1}));               // 1
    }

    private static double[][] carsByPositionDesc(int target, int[] position, int[] speed) {
        double[][] cars = new double[position.length][];
        for (int i = 0; i < position.length; i++) {
            cars[i] = new double[]{position[i], (double) (target - position[i]) / speed[i]};   // {position, time to target}
        }
        Arrays.sort(cars, (a, b) -> Double.compare(b[0], a[0]));                             // closest to target first
        return cars;
    }

    /* Approach 1 - stack of fleet arrival times, from the car closest to the target backwards. Time O(n log n). */
    public static int carFleetStack(int target, int[] position, int[] speed) {
        Deque<Double> fleets = new ArrayDeque<>();
        for (double[] car : carsByPositionDesc(target, position, speed)) {
            if (fleets.isEmpty() || car[1] > fleets.peek()) fleets.push(car[1]);      // slower than the fleet ahead: new fleet
        }
        return fleets.size();
    }

    /* Approach 2 - only the slowest arrival time ahead matters, so a single variable replaces the stack. */
    public static int carFleet(int target, int[] position, int[] speed) {
        int fleets = 0;
        double slowestAhead = 0;
        for (double[] car : carsByPositionDesc(target, position, speed)) {
            if (car[1] > slowestAhead) {
                fleets++;
                slowestAhead = car[1];
            }
        }
        return fleets;
    }
}
