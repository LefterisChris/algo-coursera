/**
 * Name: Lefteris Christoforidis
 * 
 * Description: An immutable data type Point that represents a point
 * in the plane
 */

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


/**
 * Point
 */
public class Point implements Comparable<Point> {
    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point
    public Comparator<Point> slopeOrder() {
        return new BySlope();
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x,y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
    
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /** compare two points by y-coordinates, 
     * breaking ties by x-coordinates
     */
    public int compareTo(Point that) {
        if (this.y < that.y) return -1;
        if (this.y > that.y) return +1;
        if (this.y == that.y) {
            if (this.x < that.x) return -1;
            if (this.x > that.x) return +1;
        }
        return 0;
    }

    /**
     * the slope between this point and that point
     */
    public double slopeTo(Point that) {
        if (this.x == that.x && this.y == that.y)
            return Double.NEGATIVE_INFINITY;
        else if (this.x == that.x)
            return Double.POSITIVE_INFINITY;
        else if (this.y == that.y)
            return +0.0;
        else
            return (double)(that.y - this.y)/(that.x - this.x);
    }

    private class BySlope implements Comparator<Point> {
        public int compare(Point v, Point w) {
            double s1 = slopeTo(v);
            double s2 = slopeTo(w); 
            if (s1 < s2) return -1;
            if (s1 > s2) return +1;
            return 0;
        }
    }
        

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        int x,y;
        x = StdIn.readInt();
        y = StdIn.readInt();
        Point p = new Point(x,y);
        Point p0 = new Point(1,2);
        StdOut.println("slope is " + p0.slopeTo(p));

    }
}