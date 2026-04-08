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
                if (parts.length >= 3) {
                    int id = Integer.parseInt(parts[0].trim());
                    double profit = Double.parseDouble(parts[1].trim());
                    int deadline = Integer.parseInt(parts[2].trim());
                    
                    jobs.add(new Jobs(id, profit, deadline));
                }
            }
        }
        return jobs;
    }
    
    public static List<Jobs> generateRandomData(int count, int maxDeadline, double maxProfit) {
        List<Jobs> jobs = new ArrayList<>();
        Random rand = new Random();
        
        for (int i = 1; i <= count; i++) {
            double profit = 20 + rand.nextDouble() * (maxProfit - 20);
            int deadline = 1 + rand.nextInt(maxDeadline);
            
            // Round profit to 2 decimal places
            profit = Math.round(profit * 100.0) / 100.0;
            
            jobs.add(new Jobs(i, profit, deadline));
        }
        return jobs;
    }
}