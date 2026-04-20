package jobModel;

public class Jobs {
	private String id;
	private double profit;
	private int deadline; 
	private String orderName;
	private String restaurant;
	private double distance;
	private int quantity;
	
	// Getters
	public String getId() { return id; }
	public double getProfit() { return profit; }
	public int getDeadline() { return deadline; }
	public String getOrderName() { return orderName; }
	public String getRestaurant() { return restaurant;}
	public double getDistance() { return distance; }
	public int getQuantity() { return quantity; }

	//Constructor
	public Jobs(String id, int deadline, double profit, String orderName, String restaurant, double distance, int quantity) {
		this.id = id;
		this.profit = profit;
		this.deadline = deadline;
		this.orderName = orderName;
		this.restaurant = restaurant;
		this.distance = distance;
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		return String.format("[%s | Deadline: %d | Profit: %d]", id, deadline, profit);
	}
}