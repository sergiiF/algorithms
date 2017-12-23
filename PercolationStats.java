import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private double[] thresholds;
    private final int trials;
    
    public PercolationStats(int n, int trials) {
        // perform trials independent experiments on an n-by-n grid
        if (n <= 0) throw new java.lang.IllegalArgumentException("N is out of bounds");
        if (trials <= 0) throw new java.lang.IllegalArgumentException("T is out of bounds");
        
        thresholds = new double[trials];
        this.trials = trials;
        
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                p.open(StdRandom.uniform(n), StdRandom.uniform(n)); 
            }
            thresholds[i] = (double) p.numberOfOpenSites()/(n*n);
        }
    }
    public double mean() {
        // sample mean of percolation threshold
        return StdStats.mean(thresholds);
        
    }
    public double stddev() {
        // sample standard deviation of percolation threshold
        if (trials == 1) return Double.NaN;
        return StdStats.stddev(thresholds);
    }
    public double confidenceLo() {
        // low  endpoint of 95% confidence interval
        return mean() - 1.96*stddev()/Math.sqrt(trials);
    }
    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        return mean() + 1.96*stddev()/Math.sqrt(trials);
        
    }
    
    public static void main(String[] args) {
        // test client (described below)
        
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]),
                                                      Integer.parseInt(args[1]));
        
        System.out.printf("mean = %f\n", stats.mean());
        System.out.printf("stddev = %f\n", stats.stddev());
        System.out.printf("95%% confidence interval = [%f, %f]\n", stats.confidenceLo(), stats.confidenceHi());
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
