import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int TOP = 0;
    private final boolean[][] opened;
    private final int size;
    private final int bottom;
    private int openSites;
    private final WeightedQuickUnionUF wqf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        } else {
            this.size = n;
        }
        this.opened = new boolean[size][size];
        this.bottom = size * size + 1;
        this.openSites = 0;
        this.wqf = new WeightedQuickUnionUF(size * size + 2);
    }



    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkException(row, col);
        opened[row - 1][ col - 1] = true;
        openSites++;
        if (row == 1) {
            wqf.union(getQuickFindIndex(row, col), TOP);
        }
        if (row == size) {
            wqf.union(getQuickFindIndex(row, col), bottom);
        }
        if (row > 1 && isOpen(row - 1, col)) {
            wqf.union(getQuickFindIndex(row, col), getQuickFindIndex(row - 1, col));
        }
        if (row < size && isOpen(row + 1, col)) {
            wqf.union(getQuickFindIndex(row, col), getQuickFindIndex(row + 1, col));
        }
        if (col > 1 && isOpen(row, col - 1)) {
            wqf.union(getQuickFindIndex(row, col), getQuickFindIndex(row, col - 1));
        }
        if (col < size && isOpen(row, col + 1)) {
            wqf.union(getQuickFindIndex(row, col), getQuickFindIndex(row, col + 1));
        }

    }
    private int getQuickFindIndex(int row, int col) {
        return size * (row - 1) + col;
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkException(row, col);
        return opened[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if ((row > 0 && row <= size) && (col > 0 && col <= size)) {
            return wqf.find(TOP) == wqf.find(getQuickFindIndex(row, col));
        } else throw new IllegalArgumentException();
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return  openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return wqf.find(TOP) == wqf.find(bottom);
    }
    private void checkException(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException();
        }
    }
}
