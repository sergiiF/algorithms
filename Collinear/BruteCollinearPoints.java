import java.util.ArrayList;
import java.util.Arrays;


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {
    private final Point[] points;
    private final ArrayList<LineSegment> segments = new ArrayList<>();
    
    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        if (points == null)
            throw new NullPointerException();
        
        this.points = points = points.clone();
        
        if (hasDuplicates())
            throw new IllegalArgumentException("Cannot have duplicate points.");
        
        // check 4 points in a row. If a->b->c->d, add a->d to segments
        int N = points.length;
        for (int a = 0; a < N-3; a++) {
            for (int b = a+1; b < N-2; b++) {
                double slopeB = points[a].slopeTo(points[b]);
                for (int c = b+1; c < N-1; c++) {
                    double slopeC = points[a].slopeTo(points[c]);
                    if (slopeB == slopeC) {
                        for (int d = c+1; d < N; d++) {
                            if (slopeB == points[a].slopeTo(points[d])) {
                                segments.add(new LineSegment(points[a], points[d])); // points are sorted, so a is min and d is max
                            }
                        }
                    }
                }
            }
        }
    }
    
    private boolean hasDuplicates() {
        Arrays.sort(this.points);
        for (int i = 1; i < points.length; i++) {
            if (points[i-1].compareTo(points[i]) == 0)
                return true;
        }
        return false;
    }
    
    public int numberOfSegments()        // the number of line segments
    {
        return segments.size();
    }
    public LineSegment[] segments()                // the line segments
    {
        return segments.toArray(new LineSegment[segments.size()]);
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }    
}
