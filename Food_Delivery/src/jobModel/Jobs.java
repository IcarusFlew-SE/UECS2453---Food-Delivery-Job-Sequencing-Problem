package jobModel;

public class Jobs {
	private String id;
	private double profit;
	private int deadline; // Changed String to int easier to handle
	
	// Getters
	public String getId() { return id; }
	public double getProfit() { return profit; }
	public int getDeadline() { return deadline; } // Changed String to int easier to handle

	//Constructor
	public Jobs(String id, int deadline, double profit) { // Changed String to int easier to handle
		this.id = id;
		this.profit = profit;
		this.deadline = deadline;
	}
	
	@Override
	public String toString() {
		return String.format("[%s | Deadline: %d | Profit: %d]", id, deadline, profit);
	}
}