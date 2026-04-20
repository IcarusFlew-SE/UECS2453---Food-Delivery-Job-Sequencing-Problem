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
                if (parts.length != 8) throw new IOException("Invalid format at line: " + line);
                    String id = parts[0].trim();
                    int deadline = Integer.parseInt(parts[1].trim());
                    double profit = Double.parseDouble(parts[2].trim());
                    String orderName = parts[3].trim();
                    String restaurant = parts[4].trim();
                    double distance = Double.parseDouble(parts[5].trim());
                    int quantity = Integer.parseInt(parts[6].trim());
                    jobs.add(new Jobs(id, deadline, profit, orderName, restaurant, distance, quantity));
            }
        }
        return jobs;
    }}