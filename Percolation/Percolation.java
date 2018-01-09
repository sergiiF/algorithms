/* base header */
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF union;
    private final int gridSize;
    private boolean[] openItems;
    private int openCount;
    private final int firstRowItemIndex;
    private final int lastRowItemIndex;

    public Percolation(int n) {
        // create n-by-n grid, with all sites blocked
        if (n <= 0) throw new java.lang.IllegalArgumentException("N is out of bounds");
        gridSize = n;
        union = new WeightedQuickUnionUF(n * n + 2);
        openItems = new boolean[n * n];
        openCount = 0;
        firstRowItemIndex = n * n;
        lastRowItemIndex = n * n + 1;
    }

    public void open(int row, int col) {
        // open site (row, col) if it is not open already
        if (!isOpen(row, col)) {
            int index = pos2index(row, col);
            int[][] linkTo = new int[][] { { row - 1, col }, { row, col + 1 }, { row + 1, col }, { row, col - 1 } };
            for (int[] item : linkTo) {
                try {
                    if (isOpen(item[0], item[1])) {
                        union.union(index, pos2index(item[0], item[1]));
                    }
                } catch (IllegalArgumentException e) {
                    continue;
                }
            }
            openCount += 1;
            openItems[index] = true;
            // Add to input or output if either first or last line 
            if (row == 1) {
                union.union(index, firstRowItemIndex);
            }
            if (row == gridSize){
                union.union(index, lastRowItemIndex);
            }
        }
    }

    public boolean isOpen(int row, int col) { // is site (row, col) open?
        return openItems[pos2index(row, col)];
    }

    public boolean isFull(int row, int col) {
        // is site (row, col) full?
        return isOpen(row, col) && union.connected(firstRowItemIndex, pos2index(row, col));
    }

    public int numberOfOpenSites() {
        // number of open sites
        return openCount;
    }

    public boolean percolates() {
        // does the system percolate?
        return union.connected(firstRowItemIndex, lastRowItemIndex);
    }

    private int pos2index(int row, int col) {
        // System.out.printf("%d, %d\n", row, col);
        if (row < 1 || row > gridSize || col < 1 || col > gridSize) {
            throw new IllegalArgumentException();
        }
        return (row-1) * gridSize + (col-1);
    }

}
