package algoStrategies;
import java.util.*;

public class Result<T> {
	List<T> selectedJobs;
	List<T> rejectedJobs;
	double totalProfit;
	
	public Result(List<T> selected, List<T> rejected, double totalProfit) {
		this.selectedJobs = selected;
		this.rejectedJobs = rejected;
		this.totalProfit = totalProfit;
	}
}
