public class Percolation {

    // dimension of the grid
    private int n; 
    private int openSites;

    // create n-by-n grid, with all sites blocked (false = blocked)
    public Percolation(int n) {
        boolean grid[] = new boolean[n*n];
    }

    // open site(row,col) if it's not already open
    public void open(int row, int col) {
        int idx = n*row + col;
        if (!grid[idx]) grid[idx] = true;
        openSites++;

        // corner sites
        if (idx == 0) {
            if (grid[idx+1]) union(idx, idx+1);
            if (grid[idx+n]) union(idx, idx+n);
        }

        else if (idx == n-1) {
            if (grid[idx-1]) union(idx, idx-1);
            if (grid[idx+n]) union(idx, idx+n);
        }

        else if (idx == n*(n-1)) {
            if (grid[idx-n]) union(idx, idx-n);
            if (grid[idx+1]) union(idx, idx+1);
        }

        else if (idx == n*n - 1) {
            if (grid[idx-1]) union(idx, idx-1);
            if (grid[idx-n]) union(idx, idx-n);
        }

        // edge sites
        else if (idx > 0 && idx < n) {
            if (grid[idx-1]) union(idx, idx-1);
            if (grid[idx+1]) union(idx, idx+1);
            if (grid[idx+n]) union(idx, idx+n);
        }

        else if (idx > n*(n-1) && idx < n*n - 1) {
            if (grid[idx+1]) union(idx, idx+1);
            if (grid[idx-1]) union(idx, idx-1);
            if ()
        }

        else if ((idx+1) % n == 0) {
            if (grid[idx-n]) union(idx, idx-n);
            if (grid[idx+n]) union(idx, idx+n);
            if (grid[idx-1]) union(idx, idx-1);
        }

        else if (idx % n == 0) {
            if (grid[idx-n]) union(idx, idx-n);
            if (grid[idx+n]) union(idx, idx+n);
            if (grid[idx+1]) union(idx, idx+1);
        }
        
        // remaining sites
        else {
            if (grid[idx-n]) union(idx, idx-n);
            if (grid[idx+n]) union(idx, idx+n);
            if (grid[idx+1]) union(idx, idx+1);
            if (grid[idx-1]) union(idx, idx-1);

        }

        
    }

    // is site (row,col) open?
    public boolean isOpen(int row, int col) {
        return (grid[n*row+col] == true)
    }

    // is site (row,col) full?
    /*
    A full site is an open site that can be connected to an open site in the
    top row via a chain of neighboring open sites
    */
    public boolean isFull(int row, int col) {
        for (int i = 0; i < n; i++) {
            if (isOpen(0,i) && connected(i,n*row+col))
                return true;
        }
        return false;
    }

    //number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    /*
    The system percolates if there is a full site in the bottom row
    */
    public boolean percolates() {
        for (int i = 0; i < n; i++) {
            if (isFull(n-1,i)) return true;
        }
        return false;
    }

    // test client
    public static void main(String[] args) {

    }

}