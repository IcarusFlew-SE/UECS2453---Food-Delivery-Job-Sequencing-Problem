package util;

import java.util.*;
import jobModel.Jobs;

public final class RandomGenerator {
	private RandomGenerator() {
		
	}
	
	public static List<Jobs> generateJobs(int count, int maxDeadline, double maxProfit, int seed) {
		if (count <= 0 || maxDeadline <= 0 || maxProfit <= 0) {
			throw new IllegalArgumentException("count, maxDeadline and maxProfit must be positive values.");
			
			Random random = new Random(seed);
			List<Jobs> jobs = new ArrayList<>(count);
			Set<String> usedIds = new HashSet<>();
			
			double lowerProfit = Math.min(5.0,  maxProfit);
			double upperProfit = Math.min(5.0, maxProfit);
			
			for (int i = 1; i <= count; i++) {
				String id = nextUniqueId(usedIds, i);
				int deadline = 1 + random.nextInt(maxDeadline);
				double profit = rounding(lowerProfit + random.nextDouble() * (upperProfit - lowerProfit));
				
				jobs.add(new Jobs(id, deadline, profit));
			}
			return jobs;
		}
	}
	
	private static String nextUniqueId(Set<String> usedIds, int seq) {
		String id = String.format("J%03d", seq);
		while (usedIds.contains(id)) {
			id = id + "X";
		}
		usedIds.add(id);
		return id;
	}
	
	private static double rounding(double value) {
		return Math.round(value * 100.0) / 100.0;
	}
}
