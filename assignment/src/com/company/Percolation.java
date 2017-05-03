package com.company;


import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;
public class Percolation {
    private boolean[][] grid;
    // dimension of the grid
    private WeightedQuickUnionUF gridUF;
    private int openSites;                    // number of open sites

    // create n-by-n grid, with all sites blocked (false = blocked)
    public Percolation(int n) {
        grid = new boolean[n+2][n];
        gridUF = new WeightedQuickUnionUF(n*n+n);
    }

    // validate that p is a valid index
    private void validateCol(int p) {
        int n = grid[0].length;
        if (p < 1 || p > n)
            throw new IndexOutOfBoundsException("Index " + p + " is not between 1 and " + n);
    }

    private void validateRow(int p) {
        int n = grid.length;
        if (p < 1 || p > n)
            throw new IndexOutOfBoundsException("Index " + p + " is not between 1 and " + n);
    }

    // open site(row,col) if it's not already open
    public void open(int row, int col) {
        int n = grid[0].length;
        int idx = n*(row-1) + col - 1;
        if (!grid[row][col-1]) {
            grid[row][col-1] = true;
            openSites++;
        }

        // corner sites
        if (idx == 0) {
            if (grid[row+1][col-1]) gridUF.union(idx, idx+n);
            if (grid[row][col]) gridUF.union(idx, idx+1);
        }

        else if (idx == n-1) {
            if (grid[row][col-2]) gridUF.union(idx, idx-1);
            if (grid[row+1][col-1]) gridUF.union(idx, idx+n);
        }

        else if (idx == n*(n-1)) {
            if (grid[row][col]) gridUF.union(idx, idx+1);
            if (grid[row-1][col-1]) gridUF.union(idx, idx-n);
        }

        else if (idx == n*n - 1) {
            if (grid[row][col-2]) gridUF.union(idx, idx-1);
            if (grid[row-1][col-1]) gridUF.union(idx, idx-n);
        }

        // edge sites
        else if (idx > 0 && idx < n) {
            if (grid[row][col-2]) gridUF.union(idx, idx-1);
            if (grid[row][col]) gridUF.union(idx, idx+1);
            if (grid[row-1][col-1]) gridUF.union(idx, idx+n);
        }

        else if (idx > n*(n-1) && idx < n*n - 1) {
            if (grid[row][col]) gridUF.union(idx, idx+1);
            if (grid[row][col-2]) gridUF.union(idx, idx-1);
            if (grid[row-1][col-1]) gridUF.union(idx, idx-n);
        }

        else if ((idx+1) % n == 0) {
            if (grid[row-1][col-1]) gridUF.union(idx, idx-n);
            if (grid[row+1][col-1]) gridUF.union(idx, idx+n);
            if (grid[row][col-2]) gridUF.union(idx, idx-1);
        }

        else if (idx % n == 0) {
            if (grid[row-1][col-1]) gridUF.union(idx, idx-n);
            if (grid[row+1][col-1]) gridUF.union(idx, idx+n);
            if (grid[row][col]) gridUF.union(idx, idx+1);
        }

        // remaining sites
        else {
            if (grid[row-1][col-1]) gridUF.union(idx, idx-n);
            if (grid[row+1][col-1]) gridUF.union(idx, idx+n);
            if (grid[row][col-2]) gridUF.union(idx, idx-1);
            if (grid[row][col]) gridUF.union(idx, idx+1);

        }


    }

    // is site (row,col) open?
    public boolean isOpen(int row, int col) {
        return grid[row][col-1];
    }

    // is site (row,col) full?
    /*
    A full site is an open site that can be connected to an open site in the
    top row via a chain of neighboring open sites
    */

/*
        public boolean isFull(int row, int col) {
            validate(row);
            validate(col);
            int n = grid[0].length;
            for (int i = 1; i <= n; i++) {
                if (isOpen(1,i) && gridUF.connected(i-1, n*(row-1)+col-1))
                    return true;
            }
            return false;
        }
        */

    public boolean isFull(int row, int col) {
        validateRow(row);
        validateCol(col);
        int n = grid[0].length;
        //StdOut.println(row);
        int gridIdx = n*(row-1) + col-1;
        if (row == 1) return true;

        //if (col < n)

        if (col == 1 || col == n)
            return (isFull(row-1,col) && gridUF.connected(gridIdx-n,gridIdx));
        return (isFull(row,col+1) && gridUF.connected(gridIdx+1,gridIdx));
    }


    //number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    /*
    The system percolates if there is a full site in the bottom row
    */
    /*
    public boolean percolates() {
        int n = grid[0].length;
        for (int i = n*(n-1); i < n*n; i++)
            gridUF.union(i,n*n);
        if (isFull(n+2,1 )) return true;

        return false;
    }
    */

    public boolean percolates() {
        int n = grid[0].length;
        for (int i = 1; i <= n; i++)
            if (isFull(n,i)) return true;
        return false;
    }



    // test client
    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);
        int size = 3;
        while (!percolation.percolates()){
            int rand_idx = StdRandom.uniform(size*size);
            int r_idx = rand_idx/size + 1;
            int c_idx = rand_idx % size + 1;
            percolation.open(r_idx, c_idx);
            StdOut.printf("%d %d\n", r_idx, c_idx);
        }
        StdOut.println(percolation.numberOfOpenSites());
    }
}
