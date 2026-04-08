package algoStrategies;

import jobModel.Jobs;
import java.util.*;

public class GreedyStrategy extends service.AbstractScheduler<Jobs> {
    
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
        System.out.println(" ID     | Profit (RM) | Deadline");
        System.out.println("--------+-------------+---------");
        for (Jobs job : sortedJobs) {
            System.out.printf(" J%-4d  | %-10.2f | %d days\n", 
                job.getId(), job.getProfit(), job.getDeadline());
        }
        System.out.println();
        
        // Greedy selection process
        System.out.println("Selection process:");
        System.out.println("-".repeat(50));
        
        for (Jobs job : sortedJobs) {
            int deadline = job.getDeadline();
            int slot = findFreeSlot(slots, deadline);
            
            if (slot > 0) {
                slots[slot] = job;
                selected.add(job);
                System.out.printf("  [SELECTED] J%d | Profit: RM%-8.2f | Slot: %d\n",
                    job.getId(), job.getProfit(), slot);
            } else {
                rejected.add(job);
                System.out.printf("  [REJECTED] J%d | Profit: RM%-8.2f | Deadline: %d (no free slot)\n",
                    job.getId(), job.getProfit(), deadline);
            }
        }
        
        System.out.println("-".repeat(50));
        
        // Sort selected by slot order
        selected.sort((a, b) -> {
            int slotA = findJobSlot(slots, a);
            int slotB = findJobSlot(slots, b);
            return Integer.compare(slotA, slotB);
        });
        
        double totalProfit = selected.stream().mapToDouble(Jobs::getProfit).sum();
        
        return new Result<>(selected, rejected, totalProfit);
    }
    
    private int findFreeSlot(Jobs[] slots, int deadline) {
        for (int i = deadline; i >= 1; i--) {
            if (slots[i] == null) {
                return i;
            }
        }
        return -1;
    }
    
    private int findJobSlot(Jobs[] slots, Jobs job) {
        for (int i = 1; i < slots.length; i++) {
            if (slots[i] == job) {
                return i;
            }
        }
        return -1;
    }
}