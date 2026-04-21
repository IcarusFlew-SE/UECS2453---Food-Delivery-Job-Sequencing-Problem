package algoStrategies;

import java.util.*;
import jobModel.Jobs;
import jobModel.Result;
import service.AbstractScheduler;


public class DynamicProgramming extends AbstractScheduler {
	
	public DynamicProgramming(List<Jobs> jobs) {
		super(jobs);
	}
	
	@Override 
	public String getAlgorithm() {
		return "Dynamic Programming";
	}
	
	@Override
	public Result<Jobs> schedule(List<Jobs> jobs){
		int n = jobs.size();
		int maxDeadline = getMaxDeadline(jobs);
		
		// Work on a deadline-sorted copy, algorithms are independent of input order
		List<Jobs> sortedJobs = new ArrayList<>(jobs);
		sortedJobs.sort(Comparator.comparingInt(Jobs::getDeadline));
		
		//dp[i][t] = best profit using first i jobs with scheduling horizon t
		double[][] dp = new double[n + 1][maxDeadline + 1];
		
		//Build table (bottom-up)
		for(int i = 1; i <= n; i++) {
			Jobs job = sortedJobs.get(i - 1);
			for(int t = 1; t <= maxDeadline; t++) { 
				// Option A: skip this job - carry forward the value without it
				dp[i][t] = dp[i - 1][t];
				// Option B: include this job - only valid if it fits before its deadline
				if(t <= job.getDeadline()) {
					double withJob = dp[i - 1][t - 1] + job.getProfit();
					if (withJob > dp[i][t]) {
						dp[i][t] = withJob;
					}
				}
			} 
		}	
		
		//Find selected jobs (Traceback: reconstruct selected jobs)
		List<Jobs> selected = new ArrayList<>();
		int t = maxDeadline;
		
		for(int i = n; i > 0; i--) {
			if(dp[i][t] != dp[i - 1][t]) {
				Jobs job = sortedJobs.get(i - 1);
				selected.add(job);
				t = Math.min(t, job.getDeadline()) - 1;
				if (t <= 0) break;
			}
		}
		
		Collections.reverse(selected);
		
		//Get rejected jobs
		List<Jobs> rejected = getUnselectedJobs(jobs, selected);
		double totalProfit = totalProfit(selected);
		
		return new Result<>(selected, rejected, totalProfit);
		
		}
 		
	}
