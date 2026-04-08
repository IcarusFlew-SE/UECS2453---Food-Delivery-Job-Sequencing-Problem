package jobModel;

public class Jobs {
	private int id;
	private double profit;
	private int deadline; // Changed String to int easier to handle
	
	// Getters
	public int getId() { return id; }
	public double getProfit() { return profit; }
	public int getDeadline() { return deadline; } // Changed String to int easier to handle
	
	//Setters
	public void setId(int id) { this.id = id;}
	public void setProfit(double profit) { this.profit = profit; }
	public void setDeadline(int deadline) { this.deadline = deadline; } // Changed String to int easier to handle
	
	//Constructor
	public Jobs(int id, double profit, int deadline) { // Changed String to int easier to handle
		this.id = id;
		this.profit = profit;
		this.deadline = deadline;
	}
}