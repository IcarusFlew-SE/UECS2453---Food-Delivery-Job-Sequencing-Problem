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
		
		List<Jobs> sortedJobs = new ArrayList<>(jobs);
		sortedJobs.sort(Comparator.comparingInt(Jobs::getDeadline));
		
		//Create dynamic programming  table
		double[][] dp = new double[n + 1][maxDeadline + 1];
		
		//Build table
		for(int i = 1; i <= n; i++) {
			Jobs job = sortedJobs.get(i - 1);
			for(int t = 1; t <= maxDeadline; t++) { 
				
				if(t <= job.getDeadline()) {
					dp[i][t] = Math.max(dp[i - 1][t], dp[i - 1][t - 1] + job.getProfit());
				}else {
					dp[i][t] = Math.max(dp[i - 1][t], dp[i][t - 1]);
				}
			} 
		}	
		
		//Find selected jobs
		List<Jobs> selected = new ArrayList<>();
		int t = maxDeadline;
		
		for(int i = n; i > 0; i--) {
			if(dp[i][t] != dp[i - 1][t]) {
				Jobs job = sortedJobs.get(i - 1);
				selected.add(job);
				t = Math.min(t, job.getDeadline()) - 1;
			}
		}
		
		Collections.reverse(selected);
		
		//Get rejected jobs
		List<Jobs> rejected = getUnselectedJobs(jobs, selected);
		double totalProfit = totalProfit(selected);
		
		return new Result<>(selected, rejected, totalProfit);
		
		}
 		
	}
