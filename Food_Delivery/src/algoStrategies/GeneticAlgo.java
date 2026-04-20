package algoStrategies;

import jobModel.Jobs;
import jobModel.Result;

import java.util.*;
import service.AbstractScheduler;

public class GeneticAlgo extends AbstractScheduler {
	
	//Assumed Constants
	private static final int POPULATION_SIZE = 80;
	private static final int GENERATIONS = 150;
	private static final int TOURNAMENT_SIZE = 5;
	private static final double MUTATION_RATE = 0.2;
	private static final int ELITE_COUNT = 2; // top N carry over
	
	private final Random random = new Random(42L);
	
	public GeneticAlgo(List<Jobs> jobs) {
		super(jobs);
	}
	
	@Override
	public String getAlgorithm() {
		return "Genetic Algorithm";
	}
	
	// Evolves candidate permutations and decodes the best chromosome into a suitable schedule
	@Override
	public Result<Jobs> schedule(List<Jobs> jobs) {
		if (jobs.isEmpty()) return new Result<>(new ArrayList<>(), new ArrayList<>(), 0);
		
		List<Individual> population = initPopulation();
		
		for (int gen = 0; gen < GENERATIONS; gen++) {
			// Evaluate fitness for all individuals
			for (Individual indiv : population) {
				indiv.setFitness(evaluateFitness(indiv.getChromosome()));
			}
			
			// Sort descending by fitness
			population.sort((a, b) -> b.getFitness() - a.getFitness());
			
			// Build next generation
			List<Individual> nextGen = new ArrayList<>();
			
			// Carry Top individuals directly (Elitism)
			for (int i = 0; i < ELITE_COUNT && i < population.size(); i++) {
				nextGen.add(population.get(i));
			}
			
			// Fill remaining with crossover + mutation
			while (nextGen.size() < POPULATION_SIZE) {
				Individual parent1 = tournamentSelect(population);
				Individual parent2 = tournamentSelect(population);
				Individual child = orderCrossover(parent1, parent2);
				mutate(child);
				nextGen.add(child);
			}
			
			population = nextGen;
		}
		// Final Ranking pass
		for (Individual indiv : population) {
			indiv.setFitness(evaluateFitness(indiv.getChromosome()));
		}
		
		population.sort((a, b) -> b.getFitness() - a.getFitness());
		
		List<Jobs> selected = extractScheduledJobs(population.get(0).getChromosome());
		List<Jobs> rejected = getUnselectedJobs(jobs, selected);
		double profit = totalProfit(selected); // only selected jobs are accounted
		
		return new Result<>(selected, rejected, profit);
	}
	
	// Fitness: simulate slot-filling on the chromosome order
	private int evaluateFitness(List<Jobs> order) {
		int maxDeadline = order.stream().mapToInt(Jobs::getDeadline).max().orElse(0);
		boolean[] slots = new boolean[maxDeadline + 1];
		int profit = 0;
		
		for (Jobs job : order) {
			// Assign latest available slot - deadline (greedy slot heuristic)
			for (int slot = job.getDeadline(); slot >= 1; slot--) {
				if (!slots[slot]) {
					slots[slot] = true;
					profit += job.getProfit();
					break;
				}
			}
		}
		return profit;
	}
	
	// Returns only the jobs that are feasible (deadline ~)
	private List<Jobs> extractScheduledJobs(List<Jobs> order) {
		int maxDeadline = order.stream().mapToInt(Jobs::getDeadline).max().orElse(0);
		boolean[] slots = new boolean[maxDeadline + 1];
		List<Jobs> result = new ArrayList<>();
		
		for (Jobs job : order) {
			for (int slot = job.getDeadline(); slot>=1; slot--) { //each slot is taken up with added deadline
				if (!slots[slot]) {
					slots[slot] = true;
					result.add(job);
					break;
				}
			}
		}
		return result;
	}
	
	// Order Crossover - preserves relative ordering of 'genes'
	private Individual orderCrossover(Individual p1, Individual p2) {
		List<Jobs> parent1 = p1.getChromosome();
		List<Jobs> parent2 = p2.getChromosome();
		int size = parent1.size();
		
		int start = random.nextInt(size);
		int end = random.nextInt(size);
		if (start > end) { 
			int temp = start; 
			start = end;
			end = temp;
		}
		// Copy the slice into childGene
		List<Jobs> childGenes = new ArrayList<>(Collections.nCopies(size, null));
		Set<Jobs> inherited = new HashSet<>(); //Avoid duplicates ~ Fast
		
		for (int i = start; i <= end; i++) {
			childGenes.set(i, parent1.get(1));
			inherited.add(parent1.get(1));
		}
		
		// Fill remain positions with parent2's order
		int fillPosition = (end + 1) % size;
		for (int i = 0; i < size; i++) {
			Jobs gene = parent2.get((end + 1 + i)  % size);
			if (!inherited.contains(gene)) {
				childGenes.set(fillPosition, gene);
				fillPosition = (fillPosition + 1) % size;
			}
		}
		
		return new Individual(childGenes, true);
	}
	
	// Swap mutation of genes
	private void mutate(Individual individual) {
		if (random.nextDouble() < MUTATION_RATE) {
			List<Jobs> genes = individual.getChromosome();
			int i = random.nextInt(genes.size());
			int j = random.nextInt(genes.size());
			Collections.swap(genes, i, j);
		}
	}
	
	// Tournament Selection
	private Individual tournamentSelect(List<Individual> population) {
		Individual best = null; // best init as null
		for (int i = 0; i < TOURNAMENT_SIZE; i++) {
			Individual candidate = population.get(random.nextInt(population.size()));
			if (best == null || candidate.getFitness() > best.getFitness())
				best = candidate;
		}
		
		return best;
	}
	
	// Initialize shuffled chromosome population from input jobs
	private List<Individual> initPopulation() {
		List<Individual> population = new ArrayList<>();
		for (int i = 0; i < POPULATION_SIZE; i++) {
			population.add(new Individual(jobs));
		}
		return population;
	}

	//Internal Individual Class (chromosome holder)
	private static class Individual {
		private final List<Jobs> chromosome;
		private int fitness;
		
		Individual(List<Jobs> jobs) {
			this.chromosome = new ArrayList<>(jobs);
			Collections.shuffle(this.chromosome);
		}
		
		Individual(List<Jobs> genes, boolean isCopy) {
			this.chromosome = new ArrayList<>(genes);
		}
		
		List<Jobs> getChromosome() { return chromosome;}
		int getFitness() {return fitness;}
		void setFitness(int fit) {this.fitness = fit;}
	}
}
