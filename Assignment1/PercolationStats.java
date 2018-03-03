/******************************************************************************
 *  Name:    Lefteris Christoforidis
 * 
 *  Description:  Monte Carlo Simulation of n-by-n Percolation system 
 *           
 ******************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

    private int numberOfTrials;
    private double[] percolationProbs;
    private double mean;
    private double stddev;

    public PercolationStats(int n, int trials) {
        validate(n, trials);
        numberOfTrials = trials;
        percolationProbs = new double[numberOfTrials];
        
        for (int i = 0; i < numberOfTrials; i++) {
            percolationProbs[i] = runPercolationExperiment(n);
        }
        mean = StdStats.mean(percolationProbs);

        if (numberOfTrials == 1)
            stddev = Double.NaN;
        else
            stddev = StdStats.stddev(percolationProbs);
    }    

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }
    public double confidenceLo() {
        return (mean() - 1.96*stddev()/Math.sqrt(numberOfTrials));
    }
    public double confidenceHi() {
        return (mean() + 1.96*stddev()/Math.sqrt(numberOfTrials));
    }

    private double runPercolationExperiment(int n) {
        Percolation experiment = new Percolation(n);
        int x, y;
        while (!experiment.percolates()) {
            x = StdRandom.uniform(1, n+1);
            y = StdRandom.uniform(1, n+1);
            while (experiment.isOpen(x, y)) {
                x = StdRandom.uniform(1, n+1);
                y = StdRandom.uniform(1, n+1);   
            }
            experiment.open(x, y);
        }
        double prob = (double) experiment.numberOfOpenSites()/(n*n);
        return prob;
    }

    private void validate(int n, int trials) {
        if (n <= 0 || trials <= 0) 
            throw new java.lang.IllegalArgumentException("Grid size n "+ n + " or trials "+ trials+"are not >0.");
    }

    public static void main(String[] args) {
        int n, trials;
        n = Integer.parseInt(args[0]);
        trials = Integer.parseInt(args[1]);

        PercolationStats myStats = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + myStats.mean());
        StdOut.println("stddev                  = " + myStats.stddev());
        StdOut.println("95% confidence interval = [" + myStats.confidenceLo() + "," + myStats.confidenceHi() + "]");

    }

}