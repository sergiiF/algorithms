import java.util.TreeSet;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;


public class PointSET {
    
    private final TreeSet <Point2D> pointsSet = new TreeSet<>();
    
    public PointSET() {                              // construct an empty set of points 
    }
    
    public boolean isEmpty() {                      // is the set empty? 
        return pointsSet.isEmpty();
    }
    
    public int size() {                        // number of points in the set 
        return pointsSet.size();
    }
    
    public void insert(Point2D p) {              // add the point to the set (if it is not already in the set)
        if (p == null) throw new java.lang.IllegalArgumentException();
        pointsSet.add(p);
    }
    
    public boolean contains(Point2D p) {            // does the set contain point p? 
        if (p == null) throw new java.lang.IllegalArgumentException();
        return pointsSet.contains(p);
        
    }
    
    public void draw() {                         // draw all points to standard draw 
        for(Point2D p : pointsSet) {
            p.draw();
        }
    }
    
    public Iterable<Point2D> range(RectHV rect) {             // all points that are inside the rectangle (or on the boundary) 
        if (rect == null) throw new java.lang.IllegalArgumentException();
        //for all points in set call rect.contains and add to some array
        ArrayList<Point2D> arr = new ArrayList<>();
        for(Point2D p : pointsSet) {
            if (rect.contains(p)) {
                arr.add(p);
            }
        }
        return arr;
    }
    
    public Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty 
        if (p == null) throw new java.lang.IllegalArgumentException();
        if (pointsSet.isEmpty()) return null;
        Point2D closest = null;
        double minDist = Double.MAX_VALUE;
        for (Point2D x : pointsSet) {
            double curDist  = p.distanceSquaredTo(x);
            if (minDist > curDist) {
                minDist = curDist;
                closest = x;
            }
        }
        return closest;
    }
    
    public static void main(String[] args) {                  // unit testing of the methods (optional) 
        // initialize the data structures from file
        String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            brute.insert(p);
        }
       
        // draw the points
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        brute.draw();
        StdDraw.show();
        
        
        //nearest
        Point2D p = new Point2D(0.25,0.0);
//        Point2D p = new Point2D(0,1);
        StdOut.println("Neighbor:" + brute.nearest(p) + "\n");
         
        RectHV rect = new RectHV(0.25,0,1,0.75);
//        RectHV rect = new RectHV(0.25,0.25,0.75,0.75);
        ArrayList<Point2D> a = (ArrayList<Point2D>) brute.range(rect);
        
        StdOut.println("Points in rect:\t");
        for (Point2D x : a) {
            StdOut.println(x + ", ");
        }
         
    }
    
    
    
}