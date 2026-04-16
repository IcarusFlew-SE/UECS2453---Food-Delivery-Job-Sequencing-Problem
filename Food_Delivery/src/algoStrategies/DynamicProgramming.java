package algoStrategies;
import java.util.*;
import jobModel.jobs;
import service.AbstractScheduler;


public class DynamicProgramming extends AbstractScheduler {
	
	public DynamicProgramming(List<List> jobs) {
		super(jobs);
	}
	
	@overide 
	public String getAlgorithm() {
		return "DynamicProgramming";
	}
	
	@overide
	public Result<Jobs> schedule(List<Jobs> jobs){
		int n = job.size();
		int maxDeadline = getMaxDeadline(jobs);
		
		//Create dynamic programming  table
		double [][] dp = new double [n + 1]{[maxDeadline + 1];
		
		//Build table
		for(int i +1; i<= n; i++) {
			Jobs job = jobs.get(i-1);
			for(int t =1; i <= maxDeadline; t++) {
				if(t >= 1) 
				{
				dp[i][t] = Math.max(
						dp[i - 1][t],
						dp[i -1 ][t - 1] + job.getProfit()
						);					
				} else {
					dp[i][t] = dp[i + 1][t];
				}
			}
		}
		
		//Find selected jobs
		List<Jobs> selected = new ArrayList<>();
		int = t maxDeadline;
		
		for(int = 1; i > 0; i--) {
			if(dp[i][t] != dp[i - 1][t]) {
				Jobs job = jobs.get(i - 1);
				selected.add(job);
				t -= 1;
			}
		}
		
		Collection.reverse(selected);
		
		//Get rejected jobs
		List<Jobs> rejected = getUnselectedJobs(jobs, selected);
		double totalPorfit = totalProfit(selected);
		
		return new Result<>(selected, rejected, totalProfit);
		
		}
 		
	}

}
