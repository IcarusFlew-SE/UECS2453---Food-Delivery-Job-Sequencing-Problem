package main;

import java.util.*;
import java.io.*;
import java.time.format.DateTimeFormatter;

import algoStrategies.*;
import jobModel.Jobs;
import jobModel.Result;
import service.*;
import util.FileHandler;

public class MainApp {
    private static final String LINE = "---------------------------------------------------------";
    private static final String DOUBLE_LINE = "=========================================================";

    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            printHeader();
            List<Jobs> jobs = loadJobs(input);
            printJobs(jobs);
            runMenu(input, jobs);
        }
    }

    private static void printHeader() {
        System.out.println("\n+-------------------------------------------------------+");
        System.out.println("|          FOOD DELIVERY JOB SEQUENCING SYSTEM          |");
        System.out.println("+-------------------------------------------------------+");
    }

    private static List<Jobs> loadJobs(Scanner input) {
        while (true) {
            System.out.println("\n[ STEP 1: DATA INPUT ]");
            System.out.println(" 1. Load from File");
            System.out.println(" 2. Generate Random Job Data");
            System.out.print("» Your Choice: ");

            String inputChoice = input.nextLine().trim();

            try {
                switch (inputChoice) {
                    case "1":
                        System.out.print("Enter file path (Enter for 'jobs_data.txt'): ");
                        String path = input.nextLine().trim();
                        if (path.isEmpty()) path = "jobs_data.txt";
                        return FileHandler.loadFromFile(path);

                    case "2":
                        System.out.println("\n[ CONFIGURING RANDOM GENERATION ]");
                        int n = positiveInput(input, "  > Number of jobs  : ");
                        int maxD = positiveInput(input, "  > Max Deadline    : ");
                        double maxP = positiveDouble(input, "  > Max Profit (RM) : ");
                        return FileHandler.generateRandomData(n, maxD, maxP, 42L);

                    default:
                        System.out.println("(!) Invalid Choice. Please enter 1 or 2.");
                        System.out.println("-----------------------------------------");
                }
            } catch (Exception err) {
                System.out.println("(!) Error: " + err.getMessage());
            }
        }
    }

    private static void printJobs(List<Jobs> jobs) {
        System.out.println("\n" + DOUBLE_LINE);
        System.out.println("                   LIST OF ALL JOBS                      ");
        System.out.println(DOUBLE_LINE);
        System.out.printf("|  %-10s |  %-12s |  %-18s |%n", "Job ID", "Deadline (H)", "Profit (RM)");
        System.out.println("+-------------+----------------+------------------+");
        for (Jobs job : jobs) {
            System.out.printf("|  %-10s |       %-8d |      RM %8.2f |%n", 
                job.getId(), job.getDeadline(), job.getProfit());
        }
        System.out.println("+-------------+----------------+------------------+");
    }

    private static void runMenu(Scanner input, List<Jobs> jobs) {
        boolean running = true;
        while (running) {
            System.out.println("\n[ STEP 2: SELECT ALGORITHM ]");
            System.out.println("  1. Greedy Algorithm (Profit-Based)");
            System.out.println("  2. Genetic Algorithm (Heuristic)");
            System.out.println("  3. Backtracking Algorithm (Optimal Search)");
            System.out.println("  4. Dynamic Programming (Tabulation)");
            System.out.println("  5. COMPARE ALL ALGORITHMS");
            System.out.println("  0. Exit Program");
            System.out.print("\n> Option: ");

            String choice = input.nextLine().trim();
            if (choice.equals("0")) {
                running = false;
                System.out.println("\nExiting System. Goodbye!");
                continue;
            }

            switch(choice) {
                case "1": printResult(new GreedyStrategy(jobs), jobs, input); break;
                case "2": printResult(new GeneticAlgo(jobs), jobs, input); break;
                case "3": printResult(new Backtracking(jobs), jobs, input); break;
                case "4": printResult(new DynamicProgramming(jobs), jobs, input); break;
                case "5":
                    printResult(new GreedyStrategy(jobs), jobs, null);
                    printResult(new GeneticAlgo(jobs), jobs, null);
                    printResult(new Backtracking(jobs), jobs, null);
                    printResult(new DynamicProgramming(jobs), jobs, input); // Pause on last one
                    break;
                default: System.out.println("(!) Invalid Option.");
            }
        }
    }

    private static void printResult(JobScheduler scheduler, List<Jobs> jobs, Scanner input) {
        System.out.println("\n" + LINE);
        System.out.println(" EXECUTION: " + scheduler.getAlgorithm());
        System.out.println(LINE);
        
        Result<Jobs> result = scheduler.schedule(jobs);
        List<Jobs> selected = result.getSelectedJobs();
        List<Jobs> rejected = result.getRejectedJobs();

        System.out.println("\n[RESULTS: SELECTED SCHEDULE]");
        System.out.printf("|  %-10s |  %-12s |  %-18s |%n", "Job ID", "Delivery Time", "Profit (RM)");
        System.out.println("+-------------+----------------+------------------+");
        for (Jobs job : selected) {
            System.out.printf("|  %-10s |       %-8d |      RM %8.2f |%n", 
                job.getId(), job.getDeadline(), job.getProfit());
        }
        System.out.println("+-------------+----------------+------------------+");
        
        System.out.println("\n SUMMARY STATS:");
        System.out.printf(" > Total Profit Earned : RM %.2f%n", result.getTotalProfit());
        System.out.printf(" > Efficiency Rate     : %.0f%% (%d/%d jobs)%n", 
            ((double)selected.size()/jobs.size())*100, selected.size(), jobs.size());

        if (rejected.isEmpty()) {
            System.out.println("\n[!] SUCCESS: All jobs were successfully scheduled.");
        }

        if (input != null) {
            System.out.println("\nPress ENTER to return to menu...");
            input.nextLine();
        }
    }

    private static int positiveInput(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = Integer.parseInt(input.nextLine().trim());
                if (val >= 0) return val;
            } catch (Exception e) { System.out.println("(!) Enter a positive integer."); }
        }
    }

    private static double positiveDouble(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = Double.parseDouble(input.nextLine().trim());
                if (val >= 0) return val;
            } catch (Exception e) { System.out.println("(!) Enter a positive number."); }
        }
    }
}