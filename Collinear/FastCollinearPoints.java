import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;



public class FastCollinearPoints {
    private final Point[] points;
    private final ArrayList<LineSegment> segments = new ArrayList<>();
    
    public FastCollinearPoints(Point[] points) {    // finds all line segments containing 4 or more points
        if (points == null)
            throw new NullPointerException();
        
        this.points = points = points.clone();
        
        Arrays.sort(this.points);
        if (hasDuplicates())
            throw new IllegalArgumentException("Cannot have duplicate points.");
        
        // for each point calculate slopes with all other points.
        // sort slopes
        // if >3 equal slopes -> add to segments[]
        for (int j = 0; j < points.length; j++) {
            Point p = points[j];
            Point[] pointsCopy = Arrays.copyOfRange(points, j, points.length);
            Arrays.sort(pointsCopy, p.slopeOrder());

            double lastSlope = Double.NEGATIVE_INFINITY;
            int lastIndex = 0;            
            for (int i = 1; i < pointsCopy.length; i++) {
                if (Double.compare(p.slopeTo(pointsCopy[i]), lastSlope) != 0) {
                    if (i-lastIndex >= 3) {
                        segments.add(new LineSegment(p, pointsCopy[i-1]));
                    }
                    lastIndex = i;
                    lastSlope = p.slopeTo(pointsCopy[i]);
                }
            } // for
            if (pointsCopy.length-lastIndex >= 3) {
                segments.add(new LineSegment(p, pointsCopy[pointsCopy.length-1]));
            }
        }
        
    }
    
    private boolean hasDuplicates() {
        for (int i = 1; i < points.length; i++) {
            if (points[i-1].compareTo(points[i]) == 0)
                return true;
        }
        return false;
    }
    
    
    public LineSegment[] segments() {               // the line segments
        return segments.toArray(new LineSegment[segments.size()]);
    }
    
    public int numberOfSegments() {       // the number of line segments
        return segments.size();
    }
    
    
    public static void main(String[] args) {
        
        // read the n points from a file
        In in = new In(args[0]);
        // In in = new In("collinear/input8.txt");
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
        // FastCollinearPoints collinear = new FastCollinearPoints(points);
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
    
}