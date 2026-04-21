package algoStrategies;

import jobModel.Jobs;
import jobModel.Result;
import service.AbstractScheduler;
import java.util.*;

public class Backtracking extends AbstractScheduler {
    
    private double maxProfitFound = 0.0;
    private List<Jobs> optimalSchedule = new ArrayList<>();
    private int nodesVisited = 0;

    public Backtracking(List<Jobs> jobs) {
        super(jobs);
        
    }

    @Override
    public String getAlgorithm() {
        return "BackTracking Algorithm";
        
    }

    @Override
    public Result<Jobs> schedule(List<Jobs> jobs) {
       
        this.maxProfitFound = 0.0;
        this.optimalSchedule = new ArrayList<>();
        this.nodesVisited = 0;

        // Sorting by profit (descending) ensures high-value jobs first
        sortByProfit(jobs);
        
        int highestDeadline = getMaxDeadline(jobs);
        Jobs[] timeline = new Jobs[highestDeadline + 1];
        generateExecutionPlan(jobs, 0, timeline, 0.0);

        //which job rejected
        List<Jobs> rejected = getUnselectedJobs(jobs, optimalSchedule);
        
        System.out.println("Search Completed. The Combinations checked: " + nodesVisited);
        
        return new Result<>(optimalSchedule, rejected, maxProfitFound);
    }

    //recursive branch-and-bound search
    private void generateExecutionPlan(List<Jobs> allJobs, int currentJobIndex, Jobs[] timeline, double currentProfitSum) {
    
        nodesVisited++;

        //base case: all jobs decided - check if this is a new record
        if (currentJobIndex == allJobs.size()) {
            checkIfNewRecord(timeline, currentProfitSum);
            return;
        }

        //get specific job we are looking at now
        Jobs jobToLookAt = allJobs.get(currentJobIndex);

        // Run backwards from deadline to find the latest available slot
        for (int hour = jobToLookAt.getDeadline(); hour >= 1; hour--) {
            //if slot at this hour is empty
            if (timeline[hour] == null) {
                timeline[hour] = jobToLookAt;
                double newProfit = currentProfitSum + jobToLookAt.getProfit();
                
                // move to the NEXT job
                generateExecutionPlan(allJobs, currentJobIndex + 1, timeline, newProfit);
                
                //Backtrack: take the job back out for the next branch to try.
                timeline[hour] = null;
                
                break; 
            }
        }

        //attempt to skip job
        generateExecutionPlan(allJobs, currentJobIndex + 1, timeline, currentProfitSum);
    }

    //helper method to update the record
    private void checkIfNewRecord(Jobs[] currentTimeline, double currentProfitSum) {
        if (currentProfitSum > this.maxProfitFound) {
            this.maxProfitFound = currentProfitSum;
            
            //Wipe the old best schedule and copy the new one over
            this.optimalSchedule = new ArrayList<>();
            for (int i = 0; i < currentTimeline.length; i++) {
                Jobs jobInSlot = currentTimeline[i];
                if (jobInSlot != null) {
                    this.optimalSchedule.add(jobInSlot);
                    
                }
            }
        }
    }
}
