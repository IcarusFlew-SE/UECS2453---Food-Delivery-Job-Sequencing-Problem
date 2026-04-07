package service;
import jobModel.Jobs;
import java.util.*;
import algoStrategies.JobSeqStrategy;

public abstract class AbstractScheduler<T extends Jobs> implements JobSeqStrategy<T>{
	//Sort by profit
	protected void sortByProfit(List<T> jobs) {
		jobs.sort((a, b) -> Double.compare(b.getProfit(), a.getProfit()));
	}
	// By Deadline
	protected int getMaxDeadline(List<T> jobs) {
		return 0;
	}
	
}
