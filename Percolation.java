import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF union;
    private final int gridSize;
    private boolean[] openItems;
    private int openCount;
    private final int firstRowItemIndex;
    private final int lastRowItemIndex;

    public Percolation(int n) {
        // create n-by-n grid, with all sites blocked
        gridSize = n;
        union = new WeightedQuickUnionUF(n * n + 2);
        openItems = new boolean[n * n];
        openCount = 0;
        firstRowItemIndex = n * n;
        lastRowItemIndex = n * n + 1;
        for (int i = 0; i <n; i++) {
            union.union(i, firstRowItemIndex);
            union.union(i + (n - 1) * n, lastRowItemIndex);
        }
    }

    public void open(int row, int col) {
        // open site (row, col) if it is not open already
        if (!isOpen(row, col)) {
            int index = pos2index(row, col);
            int[][] link_to = new int[][] { { row - 1, col }, { row, col + 1 }, { row + 1, col + 1 },
                    { row, col - 1 } };
            for (int[] item : link_to) {
                try {
                    if (isOpen(item[0], item[1])) {
                        union.union(index, pos2index(item[0], item[1]));
                    }
                } catch (IllegalArgumentException e) {
                    continue;
                }
                ;
            }
            openCount += 1;
            openItems[index] = true;
        }
    }

    public boolean isOpen(int row, int col) { // is site (row, col) open?
        return openItems[pos2index(row, col)];
    }

    public boolean isFull(int row, int col) {
        // is site (row, col) full?
        return union.connected(firstRowItemIndex, pos2index(row, col));
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
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            throw new IllegalArgumentException();
        }
        return row * gridSize + col;
    }

}