package jobModel;

public class Jobs {
	// Prioritizing By Profit & Deadlines
	private int id;
	private double profit;
	private String deadline;
	
	// Getters
	public int getId() { return id; }
	public double getProfit() { return profit; }
	public String getDeadline() { return deadline; }
	
	//Setters
	public void setId(int id) { this.id = id;}
	public void setProfit(double profit) { this.profit = profit; }
	public void setDeadline(String deadline) { this.deadline = deadline; }
	
	//Constructor
	public Jobs(int id, double profit, String deadline) {
		this.id = id;
		this.profit = profit;
		this.deadline = deadline;
	}
}
