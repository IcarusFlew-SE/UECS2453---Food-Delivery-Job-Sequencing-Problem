package algoStrategies;

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
    public Result<Jobs> schedule(List<Jobs> jobsToSchedule) {
       
        this.maxProfitFound = 0.0;
        this.optimalSchedule = new ArrayList<>();
        this.nodesVisited = 0;

        //Put the high pay jobs at top of list
        sortByProfit(jobsToSchedule);
        
        int highestDeadline = getMaxDeadline(jobsToSchedule);
        
        Jobs[] timeline = new Jobs[highestDeadline + 1];

        generateExecutionPlan(jobsToSchedule, 0, timeline, 0.0);

        //which job rejected
        List<Jobs> rejected = getUnselectedJobs(jobsToSchedule, optimalSchedule);
        
        System.out.println("Search Completed. The Combinations checked: " + nodesVisited);
        
        Result<Jobs> finalResult = new Result<>(optimalSchedule, rejected, maxProfitFound);
        return finalResult;
    }

    //recursive method
    private void generateExecutionPlan(List<Jobs> allJobs, int currentJobIndex, Jobs[] timeline, double currentProfitSum) {
    
        nodesVisited = nodesVisited + 1;

        //base case
        if (currentJobIndex == allJobs.size()) {
            checkIfNewRecord(timeline, currentProfitSum);
            return;
        }

        //get pecific job we are looking at now
        Jobs jobToLookAt = allJobs.get(currentJobIndex);

        //attempt to add job
        //look through every hour from the deadline back to 1
        for (int hour = jobToLookAt.getDeadline(); hour >= 1; hour--) {
            
            //if slot at this hour is empty
            if (timeline[hour] == null) {
                
            
                timeline[hour] = jobToLookAt;
                double newProfit = currentProfitSum + jobToLookAt.getProfit();
                
                // move to the NEXT job
                generateExecutionPlan(allJobs, currentJobIndex + 1, timeline, newProfit);
                
                //take the job back out for the next branch to try.
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