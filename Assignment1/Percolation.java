/******************************************************************************
 *  Name:    Lefteris Christoforidis
 * 
 *  Description:  Model an n-by-n percolation system using the union-find
 *                data structure.
 ******************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Percolation
{
    private int dim;
    private byte[][] grid;
    private WeightedQuickUnionUF ufA;
    private WeightedQuickUnionUF ufB;
    private int openSites;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        dim = n;
        grid = new byte[dim][dim]; // it is initialized to 0 -> blocked
        openSites = 0;

        // create the UF structure by adding two virtual sites - top and bottom
        ufA = new WeightedQuickUnionUF(dim*dim+2);
        ufB = new WeightedQuickUnionUF(dim*dim+1);
        // connect the top and bottom virtual sites 
        // with the first and the last row respectively
        for (int i = 1; i <= dim; i++) {
            ufA.union(0, i);
            ufA.union(dim*dim+1, dim*dim-i+1);
            ufB.union(0,i);
        }
    }

    public void open(int row, int col) {
        validate(row, col);

        if (grid[row-1][col-1] == 0) {
            grid[row-1][col-1] = 1; 
            openSites++;
            int idx = xyTo1D(row, col);

            if (col-1 >= 1 && isOpen(row, col-1)) {
                ufA.union(idx, idx-1);
                ufB.union(idx, idx-1);
            }
            if (col+1 <= dim && isOpen(row, col+1)) {
                ufA.union(idx, idx+1);
                ufB.union(idx, idx+1);
            }
            if (row-1 >= 1 && isOpen(row-1, col)) {
                ufA.union(idx, idx-dim);
                ufB.union(idx, idx-dim);
            }
            if (row+1 <= dim && isOpen(row+1, col)) {
                ufA.union(idx, idx+dim);
                ufB.union(idx, idx+dim);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return (grid[row-1][col-1] == 1) ? true : false;
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            return false;
        }
        int idx = xyTo1D(row, col);

        return ufB.connected(idx, 0);
    }
    
    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        if (dim == 1)
           return (ufA.connected(0, dim*dim+1) && isOpen(1, 1));
        else
            return ufA.connected(0, dim*dim+1);
    }

    private void validate(int row, int col) {
        if (row > dim || row < 1)
            throw new IllegalArgumentException("row " + row + " is not between 1 and " + dim);
        if (col > dim || col < 1)
            throw new IllegalArgumentException("col " + col + " is not between 1 and " + dim);
    }

    private int xyTo1D(int row, int col) {
        return ((row-1)*dim + (col-1) +1);
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        Percolation myPerc = new Percolation(n);
        // myPerc.open(1,2);
        // myPerc.open(2,2);
        // myPerc.open(2,3);
        // myPerc.open(3,3);
        // myPerc.open(3,4);
        // myPerc.open(1,4);
        // myPerc.open(3,4);

        // StdOut.println("Is 3,4 full? " + myPerc.isFull(3,4));
        StdOut.println("Number of open sites " + myPerc.numberOfOpenSites());
        StdOut.println("Is it percolating? " + myPerc.percolates());
    }
}