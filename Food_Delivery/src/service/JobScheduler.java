package service;

import jobModel.Jobs;
import java.util.*;
import algoStrategies.Result;

public interface JobScheduler {
	//Runs algorithm and return selected jobs in execution order
	Result<Jobs> schedule(List<Jobs> jobs);
	//Returns algorithm's display name for the menu
	String getAlgorithm();
}
