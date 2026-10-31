package ReachingPoints;

/*
 * 780. Reaching Points — https://leetcode.com/problems/reaching-points/
 * From (x, y) you may move to (x, x + y) or (x + y, y). Can (sx, sy) reach (tx, ty)? All values are >= 1.
 * Input: (1,1,3,5) -> true · (1,1,2,2) -> false · (1,1,1,1) -> true
 * Explanation: README.md
 */
public class ReachingPoints {
    public static void main(String[] args) {
        System.out.println(reachingPoints(1, 1, 3, 5));     // true
    }

    /*
     * Going backwards each point has exactly one parent: subtract the smaller coordinate from the larger. Repeated
     * subtraction is a modulo. Stop once one coordinate reaches its start and check the other by divisibility.
     * Time O(log max).
     */
    public static boolean reachingPoints(int sx, int sy, int tx, int ty) {
        while (tx > sx && ty > sy && tx != ty) {
            if (tx > ty) tx %= ty;
            else ty %= tx;
        }
        if (tx == sx && ty == sy) return true;
        if (tx == sx) return ty > sy && (ty - sy) % tx == 0;
        if (ty == sy) return tx > sx && (tx - sx) % ty == 0;
        return false;
    }

    /* Forward search reference for small inputs. */
    public static boolean reachingPointsBrute(int sx, int sy, int tx, int ty) {
        if (sx > tx || sy > ty) return false;
        if (sx == tx && sy == ty) return true;
        return reachingPointsBrute(sx, sx + sy, tx, ty) || reachingPointsBrute(sx + sy, sy, tx, ty);
    }
}
