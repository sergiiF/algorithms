import java.util.ArrayList;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    
    private int manhattan;
    private int hamming;
    private final int dimension;
    
    private int zeroRow;
    private int zeroCol;
    
    private final int[][] blocks;
    
    public Board(int[][] blocks) {           // construct a board from an n-by-n array of blocks // (where blocks[i][j] = block in row i, column j)
        dimension = blocks.length;
        
        if (dimension != blocks[0].length)
            throw new IllegalArgumentException("Wrong dimensions"); // should we check all inner arrays?
        
        this.blocks = new int[dimension][dimension];
        
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.blocks[i][j] = blocks[i][j];
                if(this.blocks[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                }
            }
        }
        manhattan = calcManhattan();
        hamming = calcHumming();
    }

    private Board(Board other, int exchRow, int exchCol) {           // construct a board from an n-by-n array of blocks // (where blocks[i][j] = block in row i, column j)
        dimension = other.blocks.length;
        
        this.blocks = new int[dimension][dimension];
        
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.blocks[i][j] = other.blocks[i][j];
                if(this.blocks[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                }
            }
        }
        blocks[zeroRow][zeroCol] = blocks[exchRow][exchCol];
        blocks[exchRow][exchCol] = 0;
        zeroRow = exchRow;
        zeroCol = exchCol;
        manhattan = calcManhattan();
        hamming = calcHumming();
    }
    
    private int calcManhattan() {
        int m = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] == 0) {
                    continue;
                }
                int curr = blocks[i][j] - 1;
                int row = curr / dimension;
                int col = curr % dimension;
                m += Math.abs(i - row) + Math.abs(j-col);
            }
        }
        return m;
    }
    
    private int calcHumming() {
        int h = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] == 0) continue;
                if ((i * dimension + j + 1) != blocks[i][j]) 
                    ++h;
            }
        }
        return h;
    }
    
    public int dimension() {                // board dimension n
        return dimension;
    }
    
    public int hamming() {                  // number of blocks out of place
        return hamming;
    }
    
    public int manhattan() {                // sum of Manhattan distances between blocks and goal
        return manhattan;
    }
    
    public boolean isGoal() {               // is this board the goal board?
        if (blocks[dimension-1][dimension-1] != 0)
        {
            return false;
        }
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] == 0) continue;
                if (blocks[i][j] != (i * dimension + j + 1))
                    return false;
            }
        }
        return true;
    }
    
    public Board twin() {                   // a board that is obtained by exchanging any pair of blocks
        Board twin = new Board(this.blocks);
        for (int i = 0; ; i++) {
            if ((twin.blocks[i][0] != 0) && (twin.blocks[i][1] != 0)) {
                int tmp = twin.blocks[i][0];
                twin.blocks[i][0] = twin.blocks[i][1];
                twin.blocks[i][1] = tmp;
                break;
            }
        }
        return twin;
    }
    
    public boolean equals(Object y) {       // does this board equal y?
        if (y == null) return false;
        
        if (!(y instanceof Board)) {
            return false;
        }
        
        if (dimension != ((Board) y).dimension())
            return false;
        
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] != ((Board) y).blocks[i][j])
                    return false;
            }
        }
        return true;
    }
    
    public Iterable<Board> neighbors() {    // all neighboring boards
        
        ArrayList<Board> neighbors = new ArrayList<>(); 
        int[][] linkTo = new int[][] { { zeroRow - 1, zeroCol }, { zeroRow, zeroCol + 1 }, { zeroRow + 1, zeroCol }, { zeroRow, zeroCol - 1 } };
        for (int[] item : linkTo) {
            int newRow = item[0];
            int newCol = item[1];
            if ((newRow >= 0 && newRow < dimension) && (newCol >= 0 && newCol < dimension)) {
                // exch [row][col] ->>> [newRow][newCol]
                Board newBoard = new Board(this, newRow, newCol);
                neighbors.add(newBoard);
            }
            
        }
        
        return neighbors;

    }
    
    public String toString() {              // string representation of this board (in the output format specified below)
        StringBuilder sb = new StringBuilder();
        
        sb.append(dimension);
        sb.append("\n");
        
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                sb.append(String.format("%2d ", blocks[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
        
    }
    
    public static void main(String[] args) {// unit tests (not graded)
        // int[][] testBoard = {{8,1,3},{4,0,2},{7,6,5}};
        // int[][] testBoard = {{3,5,2},{6,8,1},{9,7,0}};
        
        int[][] testBoard = {{1, 2, 3},{4,5,6},{7,8,0}};
        Board board = new Board(testBoard);
        
        StdOut.println(board);
        StdOut.println("Is goal? : "+ board.isGoal());
        
        Board twinBoard = board.twin();
        
        StdOut.println(twinBoard);
        
        
        StdOut.println("Manhattan = "+ board.manhattan());
        StdOut.println("Hamming = " + board.hamming());
        
        
        
        for (Board item : board.neighbors()) {
            StdOut.println("Neighbor: \n" + item.manhattan() + "\n" + item);
        }
                
    }
}

