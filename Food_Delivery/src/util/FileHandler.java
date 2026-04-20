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
        
                    jobs.add(new Jobs(id, deadline, profit));
            }
        }
        return jobs;
    }}