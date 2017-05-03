package com.company;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

    private double mean, stddev, l_conf, u_conf;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats (int n, int trials) {

        double[] fracOpenSites = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation system = new Percolation(n);
            while (!system.percolates()) {
                int rand_idx = StdRandom.uniform(n*n);
                int r_idx = rand_idx/n + 1;
                int c_idx = rand_idx % n + 1;
                system.open(r_idx, c_idx);
            }
            fracOpenSites[i] = system.numberOfOpenSites();
            //StdOut.println(system.numberOfOpenSites());
        }

        mean = StdStats.mean(fracOpenSites)/(n*n);
        stddev = StdStats.stddev(fracOpenSites)/(n*n);
        l_conf = mean - 1.96*stddev/trials;
        u_conf = mean + 1.96*stddev/trials;
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return l_conf;
    }

    public double confidenceHi() {
        return u_conf;
    }

    public static void main(String[] args) {
        PercolationStats percolstat = new PercolationStats(20,1000);
        StdOut.printf("mean                    = %1.7f\n", percolstat.mean());
        StdOut.printf("stddev                  = %1.18f\n", percolstat.stddev());
        StdOut.printf("95%% confidence interval = [%1.18f, %1.18f]\n", percolstat.confidenceLo(), percolstat.confidenceHi());
    }
}
