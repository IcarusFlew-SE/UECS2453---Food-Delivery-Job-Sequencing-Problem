package service;

import jobModel.Jobs;
import java.util.*;

import algoStrategies.Result;

public abstract class AbstractScheduler implements JobScheduler {
    protected List<Jobs> jobs;
    protected AbstractScheduler(List<Jobs> jobs) {
    	this.jobs = jobs;
    }
    @Override
	public abstract Result<Jobs> schedule(List<Jobs> jobs);
    
    @Override
    public abstract String getAlgorithm();
    
    // Sort by profit (descending)
    protected void sortByProfit(List<Jobs> jobs) {
        jobs.sort((a, b) -> Double.compare(b.getProfit(), a.getProfit()));
    }
    
    // Get maximum deadline 
    protected int getMaxDeadline(List<Jobs> jobs) {
        return jobs.stream()
                   .mapToInt(Jobs::getDeadline)  // Now works if deadline is int
                   .max()
                   .orElse(0);
    }
    
    // Calculates total profit from list of scheduled (selected) jobs
    protected double totalProfit(List<Jobs> scheduled) {
    	return scheduled.stream().mapToDouble(Jobs::getProfit).sum();
    }
    
    //Returns jobs in 'all' that are not 'selected'
    protected List<Jobs> getUnselectedJobs(List<Jobs> all, List<Jobs> selected) {
    	List<Jobs> unselected = new ArrayList<>(all);
    	unselected.removeAll(selected);
    	return unselected;
    }
}
