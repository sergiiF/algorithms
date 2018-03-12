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
    
    private Node root;
    private int size = 0;

    private static class Node {
        private final Point2D p;      // the point
        private final RectHV rect;    // the axis-aligned rectangle corresponding to this node
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
        
        RectHV initRect = new RectHV(0, 0, 1, 1);
        root = insert(root, p, VERTICAL, initRect);
    }
    
    private Node insert(Node x,
                        Point2D p,
                        boolean orientation, RectHV rect) {
        if (x == null) {
            size++;
            return new Node(p, rect);
        }
        
        if (x.p.equals(p)) return x;
        
        if (orientation == VERTICAL) {
            // check x-coord
            if (p.x() < x.p.x()) {
                RectHV newRect = new RectHV(x.rect.xmin(), x.rect.ymin(), x.p.x(), x.rect.ymax());
                x.lb = insert(x.lb, p, !orientation, newRect);
            } else {
                RectHV newRect = new RectHV(x.p.x(), x.rect.ymin(),x.rect.xmax(), x.rect.ymax());
                x.rt = insert(x.rt, p, !orientation, newRect);
            }
        }
        else {
            // check y-coord
            if (p.y() < x.p.y()) {
                RectHV newRect = new RectHV(x.rect.xmin(), x.rect.ymin(), x.rect.xmax(), x.p.y());
                x.lb = insert(x.lb, p, !orientation, newRect);
            } else { 
                RectHV newRect = new RectHV(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.rect.ymax());
                x.rt = insert(x.rt, p, !orientation, newRect);
            }
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
            orientation = !orientation;
        }
        return false;
    }
    
    private static class NodeExt {
        private final Node n;
        boolean orient;
        
        public NodeExt(Node n, boolean orient) {
            this.n = n;
            this.orient = orient;
        }
        
        public void draw() {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            Point2D p = n.p;
            p.draw();
            if (orient == VERTICAL) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius();
                StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());
            }
            else { //NORIZONTAL
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius();
                StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());
            }
        }
        
    } 
    
    
    public void draw() {                         // draw all points to standard draw 
        LinkedList<NodeExt> q = new LinkedList<>();
        q.add(new NodeExt(root, VERTICAL));
        while (! q.isEmpty()){
            NodeExt node = q.remove();
            if (node.n == null) {
                continue;
            } else {
                node.draw();
            }
            q.add(new NodeExt(node.n.lb, !node.orient));
            q.add(new NodeExt(node.n.rt, !node.orient));
        }
    }
    
    
    
    public Iterable<Point2D> range(RectHV rect) {             // all points that are inside the rectangle (or on the boundary)
        ArrayList<Point2D> arr = new ArrayList<>();
        
        if (rect == null) throw new java.lang.IllegalArgumentException();
        
        range(root, rect, arr);
        return arr;
    }
    
    private void range(Node n, RectHV rect, ArrayList <Point2D> arr) {
        if (n == null) return;
        
        // if n.rect intersects with rect - check this point and continue serch in childs. 
        // else - do not serch in childs, return
        
        if (rect.intersects(n.rect)) {
            if (rect.contains(n.p)) {
                arr.add(n.p);
            }
            range(n.lb, rect, arr);
            range(n.rt, rect, arr);
        }
    }
    
    public Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty 
        if (p == null) throw new java.lang.IllegalArgumentException();
        if (root == null) return null;
        
        Point2D closest = nearest(root, p, root.p);
        
        return closest;
    }
    
    private Point2D nearest (Node node, Point2D query, Point2D closest) {
        if (node == null) return closest;
        
        double distance = closest.distanceSquaredTo(query);
        
        double tmpDist = node.p.distanceSquaredTo(query);
        if (tmpDist < distance) {
            closest = node.p;
            distance = tmpDist;
        }
        
        // find which rect we query belongs to. Store it as primary, other as secondary
        Node primary, secondary;
        double primaryDist, secondaryDist;
        
        double distLB = (node.lb == null) ? Double.MAX_VALUE : node.lb.rect.distanceSquaredTo(query);
        double distRT = (node.rt == null) ? Double.MAX_VALUE : node.rt.rect.distanceSquaredTo(query);
        
        if (distLB < distRT) {
            primary = node.lb;
            primaryDist = distLB;
            secondary = node.rt;
            secondaryDist = distRT;
        } else {
            primary = node.rt;
            primaryDist = distRT;
            secondary = node.lb;
            secondaryDist = distLB;
        }
        
        // Find if node.point is closest now, if yes, update closest and distance
        
        // Recurse primary, getting nearest point.
        if (primaryDist < distance) {
            closest = nearest(primary, query, closest);
            distance = closest.distanceSquaredTo(query);
        }
        // Update distance
        // If it is more than distance to secondary rect, recurse again to secondary 
        if (secondaryDist < distance) {
            closest = nearest(secondary, query, closest);
        }

        return closest;
    }
    
    private void printIt() {
        LinkedList<Node> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            Node node = q.remove();
            if (node == null) {
                StdOut.print(" (null)");
                continue;
            } else {
                StdOut.printf("\n (%f, %f), rect (%f, %f, %f, %f)", node.p.x(), node.p.y(), node.rect.xmin(), node.rect.ymin(), node.rect.xmax(), node.rect.ymax());
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
            StdOut.println("contain: " + kd.contains(p));
        }
        Point2D p = new Point2D(0, 0.1);
        StdOut.println("should not contain: " + kd.contains(p));
        
        
        // draw the points
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        kd.draw();
        StdDraw.show();
        
    }
}