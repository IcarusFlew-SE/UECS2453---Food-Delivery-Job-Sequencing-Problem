package main;

import java.util.*;
import java.io.*;
import algoStrategies.*;
import jobModel.Jobs;
import service.*;
import util.FileHandler;

public class MainApp {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		List<Jobs> jobs = null;
		
		// 1. Data Input 
		boolean dataLoaded = false;
		while (!dataLoaded) {
			System.out.println("Select data input method: ");
			System.out.println(" 1. Read from file.");
			System.out.println(" 2. Random Generation. ");
			System.out.print(" Your Choice: ");
			
			String inputChoice = input.nextLine().trim();
			
			try {
				if (inputChoice.equals("1")) {
					System.out.print("Enter file path (~ Press Enter for default 'jobs_data.txt'): ");
					String path = input.nextLine().trim();
					if (path.isEmpty()) 
						path = "jobs_data.txt";
					jobs = FileHandler.loadFromFile(path);
					System.out.println("File Loaded successfully. ");
					System.out.println("Loaded " + jobs.size() + " jobs from file. ");
					dataLoaded = true;
				}
				else if (inputChoice.equals("2")) {
					System.out.print("Number of jobs to be generated: ");
					int n = Integer.parseInt(input.nextLine().trim());
					System.out.print("Max deadline value: ");
					int maxDeadline = Integer.parseInt(input.nextLine().trim());
					System.out.print("Max profit value: ");
					double maxProfit = Double.parseDouble(input.nextLine().trim());
					jobs = FileHandler.generateRandomData(n, maxDeadline, maxProfit, 42L);
					System.out.println("Generated " + n + " random jobs.");
					dataLoaded = true;
				}
				else {
					System.out.println("Invalid Choice. Please enter 1 or 2.");
				}
			} catch (IOException err) {
				System.out.println("Error reading file: " + err.getMessage());
			} catch (NumberFormatException err) {
				System.out.println("Invalid number entered. Try Again. " + err.getMessage());
			}
		}
		
		// 2. Display all jobs
		System.out.println("\n===ALL JOBS===");
		System.out.printf("%-8s %-12s %-10s%n", "Job ID", "Deadline", "Profit");
		System.out.println("-".repeat(32));
		for (Jobs job : jobs) {
			System.out.printf("%-8s %-12s %-10s%n", job.getId(), job.getDeadline(), job.getProfit());
		}
		
		// 3. Algorithm Selection
		boolean running = true;
		while (running) {
			System.out.println("\n=========================");
			System.out.println(" SELECT ALGORITHM: ");
			System.out.println("===========================");
			System.out.println(" 1. Greedy Algorithm (Profit-Based)");
			System.out.println(" 2. Genetic Algoritm");
			System.out.println(" 4. Dynamic Programming"");
			System.out.println(" 5. Run ALL ALGO");
			System.out.println(" 0. Exit Program");
			System.out.println(" Choose An Option: ");
			
			String choice = input.nextLine().trim();
			
			switch(choice) {
			case "1":
				printResult(new GreedyStrategy(jobs), jobs);
				break;
			case "2":
				printResult(new GeneticAlgo(jobs), jobs);
				break;
			case "4":
				printResult(new DynamicProgramming(jobs), jobs);
				break;
			case "0":
				running = false;
				break;
			default:
				System.out.println(" Invalid Option. Please Try Again.");
			}
		}
		input.close();
	}
		
		// Result Printer
		private static void printResult(JobScheduler scheduler, List<Jobs> jobs) {
			System.out.println("\n===============");
			System.out.println(" " + scheduler.getAlgorithm());
			System.out.println("=================");
			
			Result<Jobs> result = scheduler.schedule(jobs);
			
			List<Jobs> selected = result.getSelectedJobs();
			List<Jobs> rejected = result.getRejectedJobs();
			
			System.out.println("\n Selected Jobs: ");
			System.out.printf(" %-10s %-12s %-12s%n", "Job ID", "Delivery_Time", "Profit (RM)");
			System.out.println(" " + "-".repeat(35));
			for (Jobs job : selected) {
				System.out.printf(" %-10s %-12d RM %.2f%n", job.getId(), job.getDeadline(), job.getProfit());
			}
			
			System.out.println(" " + "-".repeat(35));
			System.out.printf(" Total Profit : RM %.2f%n", result.getTotalProfit());
			System.out.printf(" Jobs Selected : %d / %d%n", selected.size(), jobs.size());
			
			if (!rejected.isEmpty()) {
				System.out.println("\n Unselected Jobs: ");
				System.out.printf(" %-10s %-12s %-12s%n", "Job ID", "Delivery_Time", "Profit (RM)");
				System.out.println(" " + "-".repeat(35));
				for (Jobs job : rejected) {
					System.out.printf(" %-10s %-12d RM %.2f%n", job.getId(), job.getDeadline(), job.getProfit());
				}
			} else {
				System.out.println("\n All Jobs Were Selected");
			}
			
	}
}
