package jobModel;
import java.util.*;

public class Result<T> {
    private List<T> selectedJobs;
    private List<T> rejectedJobs;
    private double totalProfit;
    
    public Result(List<T> selected, List<T> rejected, double totalProfit) {
        this.selectedJobs = selected;
        this.rejectedJobs = rejected;
        this.totalProfit = totalProfit;
    }
    
    // Add GETTERS
    public List<T> getSelectedJobs() { return selectedJobs; }
    public List<T> getRejectedJobs() { return rejectedJobs; }
    public double getTotalProfit() { return totalProfit; }
}