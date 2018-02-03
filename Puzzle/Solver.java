import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    
    private pqElement finalElement = null;
    
    
    private class pqElement implements Comparable <pqElement>
    {
        private final int moves;         
        private final Board b;
        private final pqElement parent;
        
        public pqElement(Board b, pqElement parent) {
            this.b = b;
            this.parent = parent;
            
            moves = (parent == null) ? 0 : parent.moves + 1;
        }
        
        public int compareTo(pqElement that) {
            return this.b.manhattan() + this.moves - that.b.manhattan() - that.moves;   
        }
        
        public pqElement getParent() {
            return parent;
        }
        
        public Board getBoard() {
            return b;
        }
        
        public int getMoves() { return moves; }
        
    }
    
    public Solver(Board initial) {          // find a solution to the initial board (using the A* algorithm)
        
        if (initial == null) 
            throw new java.lang.IllegalArgumentException();
        
        MinPQ <pqElement> pq = new MinPQ <pqElement>();
        pq.insert (new pqElement(initial, null));
        
        MinPQ <pqElement> pqTwin = new MinPQ <pqElement>();
        pqTwin.insert (new pqElement(initial.twin(), null));
        
        while (!pq.isEmpty()) {
            pqElement item = pq.delMin();
            if (item.getBoard().isGoal()) {
                finalElement = item;
                break;
            } 
            
            Board parentBoard = (item.getParent() == null) ? null : item.getParent().getBoard();
            for (Board child : item.getBoard().neighbors()) {
                if (!child.equals(parentBoard)) {
                    pq.insert(new pqElement(child, item));
                }
            }
            if (!pqTwin.isEmpty()) {
                pqElement itemTwin = pqTwin.delMin();
                if (itemTwin.getBoard().isGoal()) {
                    break;
                } 
                
                Board parentBoardTwin = (itemTwin.getParent() == null) ? null : itemTwin.getParent().getBoard();
                for (Board child : itemTwin.getBoard().neighbors()) {
                    if (!child.equals(parentBoardTwin)) {
                        pqTwin.insert(new pqElement(child, itemTwin));
                    }
                }
            }          
        }
    }
    
    public boolean isSolvable() {           // is the initial board solvable?
        return (finalElement != null);
    }
    
    public int moves() {                    // min number of moves to solve initial board; -1 if unsolvable
        return (finalElement == null) ? -1 : finalElement.getMoves();
    }
    
    public Iterable<Board> solution() {     // sequence of boards in a shortest solution; null if unsolvable
        
        if (!isSolvable())
            return null;
        
        Stack<Board> result = new Stack<>();
        pqElement temp = finalElement;
        while (temp != null) {
            result.push(temp.getBoard());
            temp = temp.getParent();
        }
        return result;
        
    }
    
    public static void main(String[] args) { // solve a slider puzzle (given below)
        
        /*
         int[][] testBoard = {{2,1,3},{4,5,6},{8,7,0}};
         
         Board board = new Board(testBoard);
         
         StdOut.println(board);
         Solver solver = new Solver(board);
         
         // print solution to standard output
         if (!solver.isSolvable())
         StdOut.println("No solution possible");
         else {
         StdOut.println("Minimum number of moves = " + solver.moves());
         for (Board item : solver.solution())
         StdOut.println(item);
         }
         
         */ 
        
// create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
        
    }
}