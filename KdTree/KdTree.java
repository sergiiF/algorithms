import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.LinkedList;

public class KdTree {
    
    private static final boolean HORIZONTAL = true;
    private static final boolean VERTICAL = false;
    
    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        
        
        
        public Node(Point2D p, RectHV rect, Node lb, Node rt) {
            this.p = p;
            this.rect = rect;
            this.lb = lb;
            this.rt = rt;
        }
        public Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
            this.lb = null;
            this.rt = null;
        }
        
    } 
    
    
    private Node root;
    private int size = 0;
    
    public KdTree() {                              // construct an empty set of points 
        root = null;
    }
    
    public boolean isEmpty() {                      // is the set empty? 
        return size == 0;
    }
    
    public int size() {                        // number of points in the set 
        return size;
    }
    
    public void insert(Point2D p) {              // add the point to the set (if it is not already in the set)
        if (p == null) throw new java.lang.IllegalArgumentException();
        
        root = insert(root, p, VERTICAL);
        if (null == root.rect) {
            root.rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        }
    }
    
    private Node insert(Node x,
                        Point2D p,
                        boolean orientation) {
        if (x == null) {
            size++;
            return new Node(p, null);
        }
        Node new_node = null.
        //equal points
        if (x.p.equals(p)) return x;
        
        if (orientation == VERTICAL) {
            // check x-coord
            if (p.x() < x.p.x()) 
                x.lb = insert(x.lb, p, !orientation);
            else { 
                x.rt = insert(x.rt, p, !orientation);
            }
        }
        else {
            // check y-coord
            if (p.y() < x.p.y()) 
                x.lb = insert(x.lb, p, !orientation);
            else { 
                x.rt = insert(x.rt, p, !orientation);
            }
        }
        if (null == root.rect) {
            root.rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        }
        return x;
    }
    
    
    public boolean contains(Point2D p) {            // does the set contain point p? 
        if (p == null) throw new java.lang.IllegalArgumentException();
        
        //return contains(root, p, VERTICAL);
        Node tmp = root;
        boolean orientation = VERTICAL;
        while (tmp != null){
            if (tmp.p.equals(p)) return true;
            // go left if vertical and point is left or hosrizontal and point above 
            if ((orientation == VERTICAL && p.x() < tmp.p.x()) ||
                (orientation == HORIZONTAL && p.y() < tmp.p.y())) {
                tmp = tmp.lb;
            } else { 
                tmp = tmp.rt;
            }
            orientation = ! orientation;
        }
        return false;
    }
    
    
    public void draw() {                         // draw all points to standard draw 
    }
    
    public Iterable<Point2D> range(RectHV rect) {             // all points that are inside the rectangle (or on the boundary)
        ArrayList<Point2D> arr = new ArrayList<>();
        
        if (rect == null) throw new java.lang.IllegalArgumentException();
        
        return arr;
    }
    
    public Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty 
        if (p == null) throw new java.lang.IllegalArgumentException();
        
        return p;
    }
    
    public void printIt(){
        LinkedList<Node> q = new LinkedList<>();
        q.add(root);
        while (q.size() != 0){
            Node node = q.remove();
            if (node == null) {
                 StdOut.print(" (null)");
                 continue;
            } else {
                 StdOut.printf(" (%f, %f)", node.p.x(), node.p.y());
            }
            q.add(node.lb);
            q.add(node.rt);
        }

    }
   
    public static void main(String[] args) {                  // unit testing of the methods (optional) 
        // initialize the data structures from file
        String filename = args[0];
        In in = new In(filename);
        KdTree kd = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kd.insert(p);
        }
        kd.printIt();
        In in1 = new In(filename);
        while (!in1.isEmpty()) {
            double x = in1.readDouble();
            double y = in1.readDouble();
            Point2D p = new Point2D(x, y);
            StdOut.println("contain: " + kd.contains(p) + "\n");
        }
            Point2D p = new Point2D(0, 0.1);
            StdOut.println("should not contain: " + kd.contains(p) + "\n");
    }
}