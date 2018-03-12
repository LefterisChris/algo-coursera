/**
 * Name: Lefteris Christoforidis
 * 
 * Description: examines 4 points at a time and checks 
 * whether they all lie on the same line segment, returning all such line segments.
 */

import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class FastCollinearPoints {
    private int num = 0;
    private LineSegment[] segs = new LineSegment[2];

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) 
            throw new IllegalArgumentException("null value in constructor");
        validate_null(points);

        Point[] myPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(myPoints);
        validate_repeat(myPoints);

        for (int i = 0; i < myPoints.length; i++) {
            Point p0 = myPoints[i];
            Point[] tmp = copyArray(myPoints, i);
            // sort by slope relative to p0
            Arrays.sort(tmp, p0.slopeOrder());

            // StdOut.println("Origin p0 "+p0.toString());
            // for (int k = 0; k < tmp.length; k++) {
            //     StdOut.print(tmp[k]+" ");
            // }
            // StdOut.println();
            
            int j = 0, col = 1;
            double prev_slope = 0.0;
            Point last = null;

            while (j < tmp.length && p0.compareTo(tmp[j]) == 1) {
                j++;
            }
            if (j < tmp.length) {
                last = tmp[j];
                prev_slope = p0.slopeTo(tmp[j++]);
            }
            
            while (j < tmp.length) {
                // ommit all the points for which the
                // current point is not the origin
                if (p0.compareTo(tmp[j]) == -1) {
                    // check slope
                    if (prev_slope == p0.slopeTo(tmp[j])) {
                        col++;
                        // StdOut.println("Current point "+tmp[j].toString()+" col "+col);
                    }
                    else {
                        if (col >= 3) {
                            if (num == segs.length)
                                resize(2*segs.length);
                            segs[num++] = new LineSegment(p0, last);
                        }
                        col = 1;
                    }
                    last = tmp[j];
                    prev_slope = p0.slopeTo(tmp[j]);
                }
                else {
                    if (last != null && col >= 3) {
                        if (num == segs.length)
                            resize(2*segs.length);
                        segs[num++] = new LineSegment(p0, last);
                        last = null;
                        col = 1;
                    }
                }
                j++;
            }
            if (last != null && col >= 3) {
                if (num == segs.length)
                    resize(2*segs.length);
                segs[num++] = new LineSegment(p0, last);
            }
        }
        resize(num);
    }

    // copy array excluding element at position i
    private Point[] copyArray(Point[] a, int i) {
        Point[] temp = new Point[a.length-1];
        for (int j = 0; j < temp.length; j++) {
            if (j < i)
                temp[j] = a[j];
            else
                temp[j] = a[j+1];
        }
        return temp;
    }

    private void resize(int capacity) {
        assert capacity >= num;
        LineSegment[] temp = new LineSegment[capacity];
        for (int i = 0; i < num; i++) {
            temp[i] = segs[i];
        }
        segs = temp;
    }

    private void validate_null(Point[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null)
                throw new IllegalArgumentException("null value in point array");
        }
    }

    private void validate_repeat (Point[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].compareTo(arr[i-1]) == 0)
                throw new IllegalArgumentException("repeated point");
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return num;
    }
    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(segs, segs.length);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        StdOut.println(collinear.segments().length);
    }
}