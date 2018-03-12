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

public class BruteCollinearPoints {
    private int num = 0;
    private LineSegment[] segs = new LineSegment[2];

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) 
            throw new IllegalArgumentException("null value in constructor");
        validate_null(points);
        Point[] myPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(myPoints);
        validate_repeat(myPoints);

        for (int i = 0; i < myPoints.length-3; i++)
            for (int j = i+1; j < myPoints.length-2; j++)
                for (int k = j+1; k < myPoints.length-1; k++) {
                    Point p = myPoints[i];
                    Point q = myPoints[j];
                    Point r = myPoints[k];
                    double s1 = p.slopeTo(q);
                    double s2 = p.slopeTo(r);
                    if (s1 == s2) {
                        for (int l = k+1; l < myPoints.length; l++) {
                            Point s = myPoints[l];
                            double s3 = p.slopeTo(s);
                            if (s1 == s3) {
                                if (num == segs.length)
                                    resize(2*segs.length);
                                segs[num++] = new LineSegment(p, s);
                            }
                        }
                    }
                }
        resize(num);
    }
    // the number of line segments 
    public int numberOfSegments() {
        return num;
    }
    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(segs, segs.length);
    }

    private void validate_null(Point[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null)
                throw new IllegalArgumentException("null value in point array");
        }
    }

    private void validate_repeat(Point[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].compareTo(arr[i-1]) == 0) 
                throw new IllegalArgumentException("repeated point");
        }
    }

    private void resize(int capacity) {
        assert capacity >= num;
        LineSegment[] temp = new LineSegment[capacity];
        for (int i = 0; i < num; i++) {
            temp[i] = segs[i];
        }
        segs = temp;
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

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
 }