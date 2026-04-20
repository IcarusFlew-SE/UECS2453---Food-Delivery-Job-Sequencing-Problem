package algoStrategies;

import jobModel.Jobs;
import jobModel.Result;
import service.AbstractScheduler;
import java.util.*;

public class GreedyStrategy extends AbstractScheduler {

	public GreedyStrategy(List<Jobs> jobs) {
		super(jobs);
	}

	@Override
	public String getAlgorithm() {
		return "Greedy Algorithm (Profit-based)";
	}

	@Override
    public Result<Jobs> schedule(List<Jobs> jobs) {
        List<Jobs> sortedJobs = new ArrayList<>(jobs);
        sortByProfit(sortedJobs);
        
        int maxDeadline = getMaxDeadline(sortedJobs);
        Jobs[] slots = new Jobs[maxDeadline + 1];
        
        List<Jobs> selected = new ArrayList<>();
        List<Jobs> rejected = new ArrayList<>();
        
        // Display sorted jobs
        System.out.println("\nJobs sorted by profit (highest to lowest):");
        System.out.println(" Job ID   | Profit (RM) | Deadline");
        System.out.println("--------+-------------+---------");
        for (Jobs job : sortedJobs) {
            System.out.printf(" %-7s  | RM %-9.2f | %d days%n\n", 
                job.getId(), job.getProfit(), job.getDeadline());
        }
        System.out.println();
        
        // Greedy selection process
        System.out.println("\n Selection process:");
        System.out.println(" " + "-".repeat(50));
        
        for (Jobs job : sortedJobs) {
            int slot = findFreeSlot(slots, job.getDeadline());
            
            if (slot > 0) {
                slots[slot] = job;
                selected.add(job);
                System.out.printf("  [SELECTED] %-6s | Profit: RM%-8.2f | Slot: %d\n",
                    job.getId(), job.getProfit(), slot);
            } else {
                rejected.add(job);
                System.out.printf("  [REJECTED] %-6s | Profit: RM%-8.2f | Deadline: %d (no free slot)\n",
                    job.getId(), job.getProfit(), job.getDeadline());
            }
        }
        
        System.out.println(" " + "-".repeat(50));
        
        // Sort selected by slot order
        selected.sort((a, b) -> Integer.compare(
        	findJobSlot(slots, a),
            findJobSlot(slots, b)
        ));
        
        double totalProfit = totalProfit(selected);
        
        return new Result<>(selected, rejected, totalProfit);
    }
    
	// Returns latest free slot at or before deadline: -1 if none
    private int findFreeSlot(Jobs[] slots, int deadline) {
        for (int i = deadline; i >= 1; i--) {
            if (slots[i] == null) {
                return i;
            }
        }
        return -1;
    }
    
    // Returns slot index a job was assigned to
    private int findJobSlot(Jobs[] slots, Jobs job) {
        for (int i = 1; i < slots.length; i++) {
            if (slots[i] == job) {
                return i;
            }
        }
        return -1;
    }
}