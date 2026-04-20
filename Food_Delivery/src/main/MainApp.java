package main;

import java.util.*;
import java.io.*;
import algoStrategies.*;
import jobModel.Jobs;
import service.*;
import util.FileHandler;

public class MainApp {

	public static void main(String[] args) {
	try (Scanner input = new Scanner(System.in)) {
		List<Jobs> jobs = loadJobs(input);
		printJobs(jobs);
		runMenu(input, jobs);
		}
	}
		
	// 1. Data Input
	private static List<Jobs> loadJobs(Scanner input) {
		while (true) {
			System.out.println("Select data input method: ");
			System.out.println(" 1. Read from file.");
			System.out.println(" 2. Random Generation. ");
			System.out.print(" Your Choice: ");
			
			String inputChoice = input.nextLine().trim();
			
			try {
				switch (inputChoice) {
				
				case "1":
					System.out.print("Enter file path (Press Enter for default file: 'jobs_data.txt'): ");
					String path = input.nextLine().trim();
					if (path.isEmpty()) {
						path = "jobs_data.txt";
					}
					List<Jobs> loadedJobs = FileHandler.loadFromFile(path);
					System.out.printf("Loaded %d jobs from file.%n", loadedJobs.size());
					return loadedJobs;
					
				case "2":
					int n = positiveInput(input, "Number of jobs to generate randomly: ");
					int maxDeadline = positiveInput(input, "Max deadline value: ");
					double maxProfit = positiveDouble(input, "Max profit value: ");
					List<Jobs> generatedJobs = FileHandler.generateRandomData(n, maxDeadline, maxProfit, 42L);
					System.out.printf("Generated %d jobs. %n", generatedJobs.size());
					return generatedJobs;
				
				default:
					System.out.println("Invalid Choice. Please enter 1 or 2 for direct choice: ");
				}
			} catch (IOException err) {
				System.out.println("Error reading file: " + err.getMessage());
			} catch (IllegalArgumentException err) {
				System.out.println("Invalid input: " + err.getMessage());
			}
		}		
	}
	
		// Shows all data from appended jobs list
		private static void printJobs(List<Jobs> jobs) {
			System.out.println("\n====== ALL JOBS ======");
			System.out.printf("%-10s %-10s %-10s%n", "Job ID", "Deadline", "Profit");
			System.out.println("-".repeat(35));
			for (Jobs job : jobs) {
				System.out.printf("%-10s %-10d RM %-10.2f%n", job.getId(), job.getDeadline(), job.getProfit());
			}
		}
		
		// Runs Algorithm Selection
		private static void runMenu(Scanner input, List<Jobs> jobs) {
			boolean running = true;
			while (running) {
				System.out.println("\n========================");
				System.out.println("SELECT ALGORITHM");
				System.out.println(" 1. Greedy Method (Profit-Based)");
				System.out.println(" 2. Genetic Algorithm");
				System.out.println(" 3. Backtracking Algorithm");
				System.out.println(" 4. Dynamic Programming ");
				System.out.println(" 5. Run All Algorithms");
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
				case "3":
					printResult(new Backtracking(jobs), jobs);
					break;
				case "4":
					printResult(new DynamicProgramming(jobs), jobs);
					break;
				case "5":
					printResult(new GreedyStrategy(jobs), jobs);
					printResult(new GeneticAlgo(jobs), jobs);
					printResult(new Backtracking(jobs), jobs);
					printResult(new DynamicProgramming(jobs), jobs);
					break;
				case "0":
					running = false;
					break;
				default:
					System.out.println("Invalid Option. Please try again and make a valid choice.");
				}
			}
		}
		
		// Integer Input Handler
		private static int positiveInput(Scanner input, String prompt) {
			while (true) {
				System.out.print(prompt);
				try {
					int value = Integer.parseInt(input.nextLine().trim());
					if (value > 0) {
						return value;
					}
				} catch (NumberFormatException err) {
					System.out.println(err.getMessage() + "Please enter a positive integer");
				}
			}
		}
		
		// Profit Value Handler
		private static double positiveDouble(Scanner input, String prompt) {
			while (true) {
				System.out.print(prompt);
				try {
					double value = Double.parseDouble(input.nextLine().trim());
					if (value > 0.0) {
						return value;
					}
				} catch (NumberFormatException err) {
					System.out.println(err.getMessage() + "Please enter a positive number");
				}
			}
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
