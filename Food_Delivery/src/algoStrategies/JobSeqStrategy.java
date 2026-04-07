package algoStrategies;
import java.util.*;
import jobModel.Jobs;

public interface JobSeqStrategy<T> {
	Result<T> schedule(List<Jobs> jobs);
}
