import java.util.ArrayList;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    
    private int manhattan;
    private int hamming;
    private final int dimension;
    
    private final int[][] blocks;
    private final int[][] goal;
    
    public Board(int[][] blocks) {           // construct a board from an n-by-n array of blocks // (where blocks[i][j] = block in row i, column j)
        dimension = blocks.length;
        
        if (dimension != blocks[0].length)
            throw new IllegalArgumentException("Wrong dimensions"); // should we check all inner arrays?
        
        this.blocks = new int[dimension][dimension];
        
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
        
        // construct goal board
        goal = new int[dimension][dimension];
        
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                goal[i][j] = i * dimension + j + 1;
            }
        }
        goal[dimension - 1][dimension - 1] = 0;
        
        // calculate manhattan
        refresh();
    }
    
    private void refresh() {
        manhattan = calcManhattan();
        
        hamming = calcHumming();
        
    }
    private int calcManhattan() {
        int m = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] == 0) continue;
                if (goal[i][j] != blocks[i][j]) {
                    int curr = blocks[i][j] - 1;
                    int row = curr / dimension;
                    int col = curr % dimension;
                    m += Math.abs(i - row) + Math.abs(j-col);
                }
            }
        }
        return m;
    }
    
    private int calcHumming() {
        int h = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] == 0) continue;
                if (goal[i][j] != blocks[i][j]) 
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
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] != goal[i][j])
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
        // find 0
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                if (blocks[row][col] == 0) {
                    // check possible move and construct new Board
                    int[][] linkTo = new int[][] { { row - 1, col }, { row, col + 1 }, { row + 1, col }, { row, col - 1 } };
                    for (int[] item : linkTo) {
                        int newRow = item[0];
                        int newCol = item[1];
                        if ((newRow >= 0 && newRow < dimension) && (newCol >= 0 && newCol < dimension)) {
                            // exch [row][col] ->>> [newRow][newCol]
                            Board newBoard = new Board(blocks);
                            newBoard.blocks[row][col] = newBoard.blocks[newRow][newCol];
                            newBoard.blocks[newRow][newCol] = 0;
                            newBoard.refresh();
                            neighbors.add(newBoard);
                        }
                        
                    }
                    
                    return neighbors;
                }
            }
        }
        return neighbors;
    }

    
    
    
    public String toString() {              // string representation of this board (in the output format specified below)
/*
        String boardString = "";
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                boardString = boardString+ " " + Integer.toString(blocks[i][j]);
            }
            boardString += "\n";
        }
        return boardString;
*/
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
        
        int[][] testBoard = {{0, 2, 3},{1,5,6},{4,7,8}};
        Board board = new Board(testBoard);
        
        StdOut.println(board);
        
        Board twinBoard = board.twin();
        
        StdOut.println(twinBoard);
        
        
        StdOut.println("Manhattan = "+ board.manhattan());
        StdOut.println("Hamming = " + board.hamming());
        
        
        
        for (Board item : board.neighbors()) {
            StdOut.println("Neighbor: \n" + item.manhattan() + "\n" + item);
        }
        
    }
}

