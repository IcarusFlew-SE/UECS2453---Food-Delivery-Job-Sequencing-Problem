package service;

import jobModel.Jobs;
import java.util.*;
import algoStrategies.JobSeqStrategy;

public abstract class AbstractScheduler<T extends Jobs> implements JobSeqStrategy<T> {
    
    // Sort by profit (descending)
    protected void sortByProfit(List<T> jobs) {
        jobs.sort((a, b) -> Double.compare(b.getProfit(), a.getProfit()));
    }
    
    // Get maximum deadline - FIX THIS METHOD
    protected int getMaxDeadline(List<T> jobs) {
        return jobs.stream()
                   .mapToInt(Jobs::getDeadline)  // Now works if deadline is int
                   .max()
                   .orElse(0);
    }
}
