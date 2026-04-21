package util;

import jobModel.Jobs;
import java.io.*;
import java.util.*;

public class FileHandler {
    
    public static List<Jobs> loadFromFile(String filePath) throws IOException {
        List<Jobs> jobs = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip comments and empty lines
                if (line.trim().isEmpty() || line.startsWith("#")) {
                    continue;
                }
                String[] parts = line.split(",");
                
                if (parts.length == 3) {
                	jobs.add(new Jobs(
                			parts[0].trim(),
                			Integer.parseInt(parts[1].trim()),
                			Double.parseDouble(parts[2].trim())
                			));
                } else {
                	throw new IOException("Invalid File Formatting");
                }
            }
        }
        
        if (jobs.isEmpty()) {
        	throw new IOException("No valid jobs found in file:" + filePath);
        }
        return jobs;
    }
    
    // Generates n random jobs with seeded randomness
    public static List<Jobs> generateRandomData(int count, int maxDeadline, double maxProfit, long seed) {
    	if (count <= 0 || maxDeadline <= 0 || maxProfit <= 0) {
    		throw new IllegalArgumentException("count, maxDeadline, and maxProfit must be all positive.");
    	}
    	
    	Random random = new Random(seed);
    	List<Jobs> jobs = new ArrayList<>(count);
    	Set<String> usedIds = new HashSet<>();
    	
    	double lower = Math.min(5.0, maxProfit);
    	double upper = maxProfit;
    	
    	for (int i = 1; i <= count; i++) {
    		String id = nextUniqueId(usedIds, i);
    		int deadline = 1 + random.nextInt(maxDeadline);
    		double profit = rounding(lower + random.nextDouble() * (upper - lower));
    		jobs.add(new Jobs(id, deadline, profit));
    	}
    	
    	return jobs;
    }
    
    private static String nextUniqueId(Set<String> usedIds, int seq) {
    	String id = String.format("O%03d", seq);
    	while (usedIds.contains(id))
    		id = id + "X";
    	usedIds.add(id);
    	return id;
    }
    
    private static double rounding(double value) {
    	return Math.round(value * 100.0) / 100.0;
    }
}